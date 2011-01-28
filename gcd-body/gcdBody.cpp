#include <vector>
#include <map>
#include <assert.h>
#ifdef _MSC_VER
#include <direct.h>
#else
#include <unistd.h>
#endif
#include "../include/gcd.h"
#include "gcdBody.h"

#define PATH_LENGTH 4096
#ifdef WIN32
#define PATH_SEP '\\'
#else
#define PATH_SEP '/'
#endif

struct MvComponent {
	std::string bladeHandle;
	std::string bladeName;
	int bladeArrayIndex;
};

void readFile(std::stringstream& resultStream, const char* filePath);
void readFile(std::stringstream& resultStream, const std::ifstream& fileStream);

int body(std::string& intermediateFilePath, std::string& outputFilePath,
		const int argc, const char* argv[], const char* gaalopInFileExtension,
		const char* gaalopOutFileExtension, const char* gaalopGenerator,
		const char* intermediateFileExtension, const char* outputFileExtension,
		const char* outputOption) {
	if (argc <= 2) {
		std::cout << "usage: specify build parameters/files.\n";
		return -1;
	}

	// save working directory
	char runPath[PATH_LENGTH];
#ifdef _MSC_VER
	_getcwd(runPath, PATH_LENGTH);
#else
	getcwd(runPath, PATH_LENGTH);
#endif

	// change working directory to application directory
	std::string appPath(argv[0]);
	const size_t pos = appPath.find_last_of(PATH_SEP);
	appPath = appPath.substr(0, pos);
	std::cout << appPath << std::endl;
#ifdef _MSC_VER
	_chdir(appPath.c_str());
#else
	chdir(appPath.c_str());
#endif

	// parse command line
	std::string inputFilePath(argv[argc - 1]);
	if (inputFilePath.find('\\') == std::string::npos
			&& inputFilePath.find('/') == std::string::npos) {
		std::stringstream inputFilePathStream;
		inputFilePathStream << runPath << PATH_SEP << inputFilePath;
		inputFilePath = inputFilePathStream.str();
	}

	// try to find temp file path and remove trailing extension
	std::string tempFilePath(inputFilePath + outputFileExtension);
	for (unsigned int counter = argc - 2; counter > 0; --counter) {
		std::string arg(argv[counter]);
		if (arg.rfind(outputFileExtension) != std::string::npos) {
			const size_t pos = arg.find_last_of('.');
			tempFilePath = arg.substr(0, pos);
			outputFilePath = arg;
			break;
		} else if (arg.rfind("-o") != std::string::npos || arg.rfind("/o")
				!= std::string::npos || arg.rfind(outputOption)
				!= std::string::npos) {
			arg = argv[++counter];
			const size_t pos = arg.find_last_of('.');
			tempFilePath = arg.substr(0, pos);
			outputFilePath = arg;
			break;
		}
	}

	// convert to absolute paths
	if (tempFilePath.find('\\') == std::string::npos && tempFilePath.find('/')
			== std::string::npos) {
		// gaalop output file
		{
			std::stringstream tempFilePathStream;
			tempFilePathStream << runPath << PATH_SEP << tempFilePath;
			tempFilePath = tempFilePathStream.str();
		}

		// output file
		{
			std::stringstream outputFilePathStream;
			outputFilePathStream << runPath << PATH_SEP << outputFilePath;
			outputFilePath = outputFilePathStream.str();
		}
	}

	// process input file
	// split into gaalop input files and save in memory
	std::vector<std::string> gaalopInFileVector;
	std::string line;
	{
		std::ifstream inputFile(inputFilePath.c_str());
		while (inputFile.good()) {
			// read line
			getline(inputFile, line);

			// found gaalop line
			if (line.find("#pragma gcd begin") != std::string::npos) {
				std::stringstream gaalopInFile;

				// read until end of optimized file
				while (inputFile.good()) {
					getline(inputFile, line);
					if (line.find("#pragma gcd end") != std::string::npos)
						break;
					else
						gaalopInFile << line << std::endl;
				}

				// add to vector
				gaalopInFileVector.push_back(gaalopInFile.str());
			}
		}
	}

	// process gaalop intermediate files - call gaalop
	std::string gaalopPath;
#ifdef WIN32
#ifdef _MSC_VER
	_chdir("..\\share\\gcd\\gaalop");
#else
	chdir("..\\share\\gcd\\gaalop");
#endif
//#if defined _MSC_VER && defined DEBUG
	gaalopPath = "..\\..\\gaalop_settings.bat";
/*#else
	gaalopPath = "..\\gaalop_settings.bat";
#endif*/
#else
	chdir("../share/gcd/gaalop");
	gaalopPath = "../gaalop_settings.sh";
#endif
	std::stringstream variables;
	for (unsigned int gaalopFileCount = 0; gaalopFileCount < gaalopInFileVector.size(); ++gaalopFileCount) {
		// retrieve multivectors from previous sections
		if (gaalopFileCount > 0) {
			std::vector<std::string> mvNames;
			std::multimap<std::string, MvComponent> mvComponents;

			std::stringstream gaalopOutFilePath;
			gaalopOutFilePath << tempFilePath << '.' << gaalopFileCount - 1
					<< gaalopOutFileExtension;
			std::cout << gaalopOutFilePath.str() << std::endl;
			std::ifstream gaalopOutFile(gaalopOutFilePath.str().c_str());
			if (!gaalopOutFile.good()) {
				std::cerr
						<< "fatal error: Gaalop-generated file not found. Check your Java installation. "
							"Also check your Maple directory and Cliffordlib using the Configuration Tool.\n";
				return -1;
			}
			while (gaalopOutFile.good()) {
				getline(gaalopOutFile, line);

				// retrieve multivector declarations
				{
					const std::string
							mvSearchString("#pragma gcd multivector ");
					size_t statementPos = line.find(mvSearchString);
					if (statementPos != std::string::npos)
						mvNames.push_back(line.substr(statementPos
								+ mvSearchString.length()));
				}

				// retrieve multivector component decalrations
				{
					const std::string mvCompSearchString(
							"#pragma gcd multivector_component ");
					size_t statementPos = line.find(mvCompSearchString);
					if (statementPos != std::string::npos) {
						std::string mvName;
						MvComponent mvComp;
						std::stringstream lineStream(line.substr(statementPos
								+ mvCompSearchString.length()));
						lineStream >> mvName;
						lineStream >> mvComp.bladeHandle;
						lineStream >> mvComp.bladeName;
						lineStream >> mvComp.bladeArrayIndex;
						mvComponents.insert(std::multimap<std::string,
								MvComponent>::value_type(mvName, mvComp));
					}
				}
			}

			for (std::vector<std::string>::const_iterator mvNamesIter =
					mvNames.begin(); mvNamesIter != mvNames.end(); ++mvNamesIter) {
				const std::string& mvName = *mvNamesIter;
				variables << mvName << " = 0";

				std::pair<
						std::multimap<std::string, MvComponent>::const_iterator,
						std::multimap<std::string, MvComponent>::const_iterator>
						mvComponentRange = mvComponents.equal_range(mvName);
				for (std::multimap<std::string, MvComponent>::const_iterator
						mvComponentIter = mvComponentRange.first; mvComponentIter
						!= mvComponentRange.second; ++mvComponentIter)
					variables << " +" << mvComponentIter->second.bladeName
							<< '*' << mvName << '_'
							<< mvComponentIter->second.bladeHandle;

				variables << ";\n";
			}
		}

		// compose gaalop input file
		{
			std::stringstream gaalopInFilePath;
			gaalopInFilePath << tempFilePath << '.' << gaalopFileCount
					<< gaalopInFileExtension;
			std::ofstream gaalopInFile(gaalopInFilePath.str().c_str());
			gaalopInFile << variables.str()
					<< gaalopInFileVector[gaalopFileCount];
		}

		// run Gaalop
		{
			std::stringstream gaalopCommand;
			gaalopCommand << gaalopPath;
			gaalopCommand << " -generator " << gaalopGenerator << " -i \""
					<< tempFilePath << '.' << gaalopFileCount
					<< gaalopInFileExtension << '\"';
			system(gaalopCommand.str().c_str());
		}
	}
#ifdef WIN32
#ifdef _MSC_VER
	_chdir("..\\..\\..\\bin");
#else
	chdir("..\\..\\..\\bin");
#endif
#else
	chdir("../../../bin");
#endif

	// compose intermediate file
	{
		size_t pos = inputFilePath.find_last_of('/');
		if (pos == std::string::npos)
			pos = inputFilePath.find_last_of('\\');
		assert(pos != std::string::npos);
		const std::string inputFileDir(inputFilePath.substr(0, pos + 1));

		intermediateFilePath = tempFilePath + intermediateFileExtension;
		std::ofstream intermediateFile(intermediateFilePath.c_str());
		std::ifstream inputFile(inputFilePath.c_str());
		unsigned int gaalopFileCount = 0;
		unsigned int lineCount = 1; // think one line ahead
		while (inputFile.good()) {
			// read line
			getline(inputFile, line);
			++lineCount;

			if (line.find("#include") != std::string::npos && line.find('\"')
					!= std::string::npos) {
				const size_t pos = line.find_first_of('\"') + 1;
				intermediateFile << line.substr(0, pos) << inputFileDir;
				intermediateFile << line.substr(pos) << std::endl;
			}
			// found gaalop line - insert intermediate gaalop file
			else if (line.find("#pragma gcd begin") != std::string::npos) {
				// line pragma for compile errors
				intermediateFile << "#line " << lineCount << " \""
						<< inputFilePath << "\"\n";

				// merge optimized code
				std::stringstream gaalopOutFilePath;
				gaalopOutFilePath << tempFilePath << '.' << gaalopFileCount++
						<< gaalopOutFileExtension;
				std::cout << gaalopOutFilePath.str() << std::endl;
				std::ifstream gaalopOutFile(gaalopOutFilePath.str().c_str());
				if (!gaalopOutFile.good()) {
					std::cerr
							<< "fatal error: Gaalop-generated file not found. Check your Java installation. "
								"Also check your Maple directory and Cliffordlib using the Configuration Tool.\n";
					return -1;
				}
				while (gaalopOutFile.good()) {
					getline(gaalopOutFile, line);
					intermediateFile << line << std::endl;
				}

				// skip original code
				while (inputFile.good()) {
					getline(inputFile, line);
					++lineCount;
					if (line.find("#pragma gcd end") != std::string::npos)
						break;
				}

				// line pragma for compile errors
				intermediateFile << "#line " << lineCount << " \""
						<< inputFilePath << "\"\n";
			} else
				intermediateFile << line << std::endl;
		}
	}

	return 0;
}

void invokeCompiler(const std::string& compilerPath, const int argc,
		const char* argv[], const std::string& outputFilePath,
		const std::string& intermediateFilePath, const char* outputOption) {
	// compose compiler command
	std::stringstream compilerCommand;
	compilerCommand << compilerPath;
	for (int counter = 1; counter < argc - 1; ++counter) {
		const std::string arg(argv[counter]);
		if (arg.find(outputOption) != std::string::npos) {
			compilerCommand << ' ' << arg << " \"" << outputFilePath << '\"';
			++counter;
		} else
			compilerCommand << " \"" << arg << '\"';
	}
	compilerCommand << " \"" << intermediateFilePath << '\"';

	// invoke regular C++ compiler
	std::cout << compilerCommand.str() << std::endl;
	system(compilerCommand.str().c_str());
}

void readFile(std::string& resultString, const char* filePath) {
	std::stringstream resultStream;
	readFile(resultStream, filePath);
	resultString = resultStream.str();
}

void readFile(std::stringstream& resultStream, const char* filePath) {
	std::ifstream fileStream(filePath);
	readFile(resultStream, fileStream);
}

void readFile(std::stringstream& resultStream, std::ifstream& fileStream) {
	std::string line;
	while (fileStream.good()) {
		getline(fileStream, line);
		resultStream << line;
	}
}

void findAndReplaceString(std::string& dest, const std::string& source,
		const std::string& search, const std::string& replace) {
	std::string buffer;
	std::stringstream replaced("");
	std::stringstream original(source);
	while (original >> buffer) {
		if (buffer == search)
			replaced << replace << " ";
		else
			replaced << buffer << " ";
	}

	replaced >> dest;
}
