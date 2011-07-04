package de.gaalop.gcd;

import de.gaalop.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.Set;

/**
 * Main class of the Gaalop command line interface. Arguments are parsed using the args4j plugin.
 *
 * @author Patrick Charrier
 *
 */
public class Main {

  private Log log = LogFactory.getLog(Main.class);

  @Option(name = "-i", required = true, usage = "The input file.")
  private String inputFile;

  @Option(name = "-o", required = false, usage = "Sets the directory where the output files are created.")
  private String outputDirectory = "";

  @Option(name = "-m", required = false, usage = "Sets the Maple binary path.")
  private String mapleBinaryPath = "";

  @Option(name = "-parser", required = false, usage = "Sets the class name of the code parser plugin that should be used.")
  private String codeParserPlugin = "de.gaalop.clucalc.input.Plugin";

  @Option(name = "-generator", required = false, usage = "Sets the class name of the code generator plugin that should be used.")
  private String codeGeneratorPlugin = "de.gaalop.cpp.Plugin";

  @Option(name = "-optimizer", required = false, usage = "Sets the class name of the optimization strategy plugin that should be used.")
  private String optimizationStrategyPlugin = "de.gaalop.maple.Plugin";

  /**
   * Starts the command line interface of Gaalop.
   *
   * @param args -i to specify the input file (mandatory), -o to specify the output directory,
   * -parser to set the input parser, -generator to set the code generator plugin, -optimizer to
   * select the optimization strategy.
   */
  public static void main(String[] args) throws Exception {
    Main main = new Main();
    CmdLineParser parser = new CmdLineParser(main);
    try {
      parser.parseArgument(args);
      main.run();
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
      parser.printUsage(System.err);
    }
    catch(Throwable t) {
    	while(t.getCause() != null)
    		t = t.getCause();
    	
    	System.err.println(t.getMessage());
    }
  }

  public static void runGCD() {
	// parse command line
	String inputFilePath(argv[argc - 1]);
	if (inputFilePath.find('\\') == String::npos
			&& inputFilePath.find('/') == String::npos) {
		Stringstream inputFilePathStream;
		inputFilePathStream).append(runPath).append(PATH_SEP).append(inputFilePath;
		inputFilePath = inputFilePathStream.str();
	}

	// try to find temp file path and remove trailing extension
	String tempFilePath(inputFilePath + outputFileExtension);
	for (unsigned int counter = argc - 2; counter > 0; --counter) {
		String arg(argv[counter]);
		if (arg.rfind(outputFileExtension) != String::npos) {
			const int pos = arg.find_last_of('.');
			tempFilePath = arg.substr(0, pos);
			outputFilePath = arg;
			break;
		} else if (arg.rfind("-o") != String::npos || arg.rfind("/o")
				!= String::npos || arg.rfind(outputOption)
				!= String::npos) {
			arg = argv[++counter];
			const int pos = arg.find_last_of('.');
			tempFilePath = arg.substr(0, pos);
			outputFilePath = arg;
			break;
		}
	}

	// convert to absolute paths
	if (tempFilePath.find('\\') == String::npos && tempFilePath.find('/')
			== String::npos) {
		// gaalop output file
		{
			Stringstream tempFilePathStream;
			tempFilePathStream).append(runPath).append(PATH_SEP).append(tempFilePath;
			tempFilePath = tempFilePathStream.str();
		}

		// output file
		{
			Stringstream outputFilePathStream;
			outputFilePathStream).append(runPath).append(PATH_SEP).append(outputFilePath;
			outputFilePath = outputFilePathStream.str();
		}
	}

	// process input file
	// split into gaalop input files and save in memory
	java.util.List<String> gaalopInFileVector;
	String line;
	{
		std::ifstream inputFile(inputFilePath.c_str());
		while (inputFile.good()) {
			// read line
			getline(inputFile, line);

			// found gaalop line
			if (line.find("#pragma gcd begin") != String::npos) {
				Stringstream gaalopInFile;

				// read until end of optimized file
				while (inputFile.good()) {
					getline(inputFile, line);
					if (line.find("#pragma gcd end") != String::npos)
						break;
					else
						gaalopInFile).append(line).append(std::endl;
				}

				// add to vector
				gaalopInFileVector.push_back(gaalopInFile.str());
			}
		}
	}

	// process gaalop intermediate files - call gaalop
	Stringstream variables;
	for (unsigned int gaalopFileCount = 0; gaalopFileCount < gaalopInFileVector.size(); ++gaalopFileCount) {
		// retrieve multivectors from previous sections
		if (gaalopFileCount > 0) {
			std::vector<String> mvNames;
			std::multimap<String, MvComponent> mvComponents;

			Stringstream gaalopOutFilePath;
			gaalopOutFilePath).append(tempFilePath).append('.').append(gaalopFileCount - 1
					<< gaalopOutFileExtension;
			System.out.println().append(gaalopOutFilePath.str()).append(std::endl;
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
					const String
							mvSearchString("#pragma gcd multivector ");
					int statementPos = line.find(mvSearchString);
					if (statementPos != String::npos)
						mvNames.push_back(line.substr(statementPos
								+ mvSearchString.length()));
				}

				// retrieve multivector component decalrations
				{
					const String mvCompSearchString(
							"#pragma gcd multivector_component ");
					int statementPos = line.find(mvCompSearchString);
					if (statementPos != String::npos) {
						String mvName;
						MvComponent mvComp;
						Stringstream lineStream(line.substr(statementPos
								+ mvCompSearchString.length()));
						lineStream >> mvName;
						lineStream >> mvComp.bladeHandle;
						lineStream >> mvComp.bladeName;
						lineStream >> mvComp.bladeArrayIndex;
						mvComponents.insert(std::multimap<String,
								MvComponent>::value_type(mvName, mvComp));
					}
				}
			}

			for (std::vector<String>::const_iterator mvNamesIter =
					mvNames.begin(); mvNamesIter != mvNames.end(); ++mvNamesIter) {
				const String& mvName = *mvNamesIter;
				variables).append(mvName).append(" = 0";

				std::pair<
						std::multimap<String, MvComponent>::const_iterator,
						std::multimap<String, MvComponent>::const_iterator>
						mvComponentRange = mvComponents.equal_range(mvName);
				for (std::multimap<String, MvComponent>::const_iterator
						mvComponentIter = mvComponentRange.first; mvComponentIter
						!= mvComponentRange.second; ++mvComponentIter)
					variables).append(" +").append(mvComponentIter->second.bladeName
							<< '*').append(mvName).append('_'
							<< mvComponentIter->second.bladeHandle;

				variables).append(";\n";
			}
		}

		// compose gaalop input file
		{
			Stringstream gaalopInFilePath;
			gaalopInFilePath).append(tempFilePath).append('.').append(gaalopFileCount
					<< gaalopInFileExtension;
			std::ofstream gaalopInFile(gaalopInFilePath.str().c_str());
			gaalopInFile).append(variables.str()
					<< gaalopInFileVector[gaalopFileCount];
		}

		// run Gaalop
		{
			Stringstream gaalopCommand;
			gaalopCommand).append(gaalopPath;
			gaalopCommand).append(" -generator ").append(gaalopGenerator).append(" -i \""
					<< tempFilePath).append('.').append(gaalopFileCount
					<< gaalopInFileExtension).append('\"';
		}
	}

	// compose intermediate file
	{
		int pos = inputFilePath.find_last_of('/');
		if (pos == String::npos)
			pos = inputFilePath.find_last_of('\\');
		assert(pos != String::npos);
		const String inputFileDir(inputFilePath.substr(0, pos + 1));

		intermediateFilePath = tempFilePath + intermediateFileExtension;
		std::ofstream intermediateFile(intermediateFilePath.c_str());
		std::ifstream inputFile(inputFilePath.c_str());
		unsigned int gaalopFileCount = 0;
		unsigned int lineCount = 1; // think one line ahead
		while (inputFile.good()) {
			// read line
			getline(inputFile, line);
			++lineCount;

			if (line.find("#include") != String::npos && line.find('\"')
					!= String::npos) {
				const int pos = line.find_first_of('\"') + 1;
				intermediateFile).append(line.substr(0, pos)).append(inputFileDir;
				intermediateFile).append(line.substr(pos)).append(std::endl;
			}
			// found gaalop line - insert intermediate gaalop file
			else if (line.find("#pragma gcd begin") != String::npos) {
				// line pragma for compile errors
				intermediateFile).append("#line ").append(lineCount).append(" \""
						<< inputFilePath).append("\"\n";

				// merge optimized code
				Stringstream gaalopOutFilePath;
				gaalopOutFilePath).append(tempFilePath).append('.').append(gaalopFileCount++
						<< gaalopOutFileExtension;
				System.out.println(gaalopOutFilePath.str()).append(std::endl;
				std::ifstream gaalopOutFile(gaalopOutFilePath.str().c_str());
				if (!gaalopOutFile.good()) {
					std::cerr
							<< "fatal error: Gaalop-generated file not found. Check your Java installation. "
								"Also check your Maple directory and Cliffordlib using the Configuration Tool.\n";
					return -1;
				}
				while (gaalopOutFile.good()) {
					getline(gaalopOutFile, line);
					intermediateFile).append(line).append(std::endl);
				}

				// skip original code
				while (inputFile.good()) {
					getline(inputFile, line);
					++lineCount;
					if (line.find("#pragma gcd end") != String::npos)
						break;
				}

				// line pragma for compile errors
				intermediateFile.append("#line ").append(lineCount).append(" \"").
				append(inputFilePath).append("\"\n");
			} else
				intermediateFile.append(line).append('\n');
		}
	}

	return 0;
}

  public static void invokeCompiler(const String& compilerPath, const int argc,
		const String argv[], const String& outputFilePath,
		const String intermediateFilePath, const String outputOption) {
	// compose compiler command
	std::stringstream compilerCommand;
	compilerCommand).append(compilerPath;
	for (int counter = 1; counter < argc - 1; ++counter) {
		const String arg(argv[counter]);
		if (arg.find(outputOption) != String::npos) {
			compilerCommand.append(' ').append(arg).append(" \"").append(outputFilePath).append('\"');
			++counter;
		} else
			compilerCommand.append(" \"").append(arg).append('\"');
	}
	compilerCommand.append(" \"").append(intermediateFilePath).append('\"');

	// invoke regular C++ compiler
	//System.out.println().append(compilerCommand.str()).append(std::endl;
	system(compilerCommand);
}

  /**
   * Runs the command line interface. Should be invoked after setup.
   */
  public void run() throws Exception {
    log.debug("Starting up compilation process.");

    // Configure the compiler
    CompilerFacade compiler = createCompiler();

    // Perform compilation
    InputFile inputFile = getInputFile();
    Set<OutputFile> outputFiles = compiler.compile(inputFile);
    for (OutputFile output : outputFiles) {
      writeFile(output);
    }
  }

  private void writeFile(OutputFile output) throws FileNotFoundException,
      UnsupportedEncodingException {
    if (outputDirectory.equals("-")) {
      printFileToConsole(output);
    } else {
      File outFile;
      if(outputDirectory.length() == 0)
        outFile = new File(output.getName());
      else
      {
        // NOTE: output file does not return actual name, but the full path of the file
        File tempFile = new File(output.getName());
        outFile = new File(outputDirectory, tempFile.getName());
      }
      PrintWriter writer = new PrintWriter(outFile, output.getEncoding().name());
      writer.print(output.getContent());
      writer.close();
    }
  }

  private void printFileToConsole(OutputFile output) {
    System.out.println("----------------------------------------------------------");
    System.out.println("Output File: " + output.getName());
    System.out.println("----------------------------------------------------------");
    System.out.println(output.getContent());
    System.out.println("----------------------------------------------------------");
  }

  private CompilerFacade createCompiler() {
    CodeParser codeParser = createCodeParser();

    OptimizationStrategy optimizationStrategy = createOptimizationStrategy();

    CodeGenerator codeGenerator = createCodeGenerator();

    return new CompilerFacade(codeParser, optimizationStrategy, codeGenerator);
  }

  private CodeParser createCodeParser() {
    Set<CodeParserPlugin> plugins = Plugins.getCodeParserPlugins();
    for (CodeParserPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(codeParserPlugin)) {
        return plugin.createCodeParser();
      }
    }

    System.err.println("Unknown code parser plugin: " + codeParserPlugin);
    System.exit(-2);
    return null;
  }

  private OptimizationStrategy createOptimizationStrategy() {
    Set<OptimizationStrategyPlugin> plugins = Plugins.getOptimizationStrategyPlugins();
    for (OptimizationStrategyPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(optimizationStrategyPlugin)) {
        if(mapleBinaryPath.length() != 0)
          plugin.setMaplePathsByMapleBinaryPath(mapleBinaryPath);
        return plugin.createOptimizationStrategy();
      }
    }

    System.err.println("Unknown optimization strategy plugin: " + optimizationStrategyPlugin);
    System.exit(-3);
    return null;
  }

  private CodeGenerator createCodeGenerator() {
    Set<CodeGeneratorPlugin> plugins = Plugins.getCodeGeneratorPlugins();
    for (CodeGeneratorPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(codeGeneratorPlugin)) {
        return plugin.createCodeGenerator();
      }
    }

    System.err.println("Unknown code generator plugin: " + codeGeneratorPlugin);
    System.exit(-4);
    return null;
  }

  public InputFile getInputFile() throws Exception {
    final Reader reader;
    final String filename;

    if (inputFile.equals("-")) {
      reader = new InputStreamReader(System.in);
      filename = "stdin";
    } else {
      reader = new FileReader(inputFile);
      filename = inputFile;
    }

    try {
      BufferedReader bufReader = new BufferedReader(reader);
      String line;
      StringBuilder result = new StringBuilder();
      while ((line = bufReader.readLine()) != null) {
        result.append(line);
        result.append('\n');
      }

      return new InputFile(filename, result.toString());
    } finally {
      reader.close();
    }
  }
}
