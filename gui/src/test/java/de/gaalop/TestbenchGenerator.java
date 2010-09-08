package de.gaalop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.FindStoreOutputNodes;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.clucalc.input.CluCalcCodeParser;
import de.gaalop.clucalc.output.ExtendedCLUCalcGenerator;
import de.gaalop.cpp.CppVisitor;
import de.gaalop.dfg.Variable;

public class TestbenchGenerator {

	private final int MAX_MV_LENGTH = 2048;
	private final String CLU_BLADES = "string CLU_BLADES[32] = {\n"
			+ "\t\"1\",\n"
			+ "\t\"e1\", \"e2\", \"e3\", \"e\", \"e0\",\n"
			+ "\t\"e12\", \"e13\", \"(e1^e)\", \"(e1^e0)\", \"e23\", \"(e2^e)\", \"(e2^e0)\", \"(e3^e)\", \"(e3^e0)\", \"E\",\n"
			+ "\t\"e123\", \"(e12^e)\", \"(e12^e0)\", \"(e13^e)\", \"(e13^e0)\", \"(e1^E)\", \"(e23^e)\", \"(e23^e0)\", \"(e2^E)\", \"(e3^E)\",\n"
			+ "\t\"(e123^e)\", \"(e123^e0)\", \"(e12^E)\", \"(e13^E)\", \"(e23^E)\",\n" + "\t\"I\" \n" + "};\n\n";

	private File path;
	private String fileName;
	private String originalFileName;
	private String optFileName;
	private InputFile inputFile;
	private final CluCalcCodeParser parser = CluCalcCodeParser.INSTANCE;
	private final OptimizationStrategy optimizer;
	private List<Variable> inputVariables;
	private List<StoreResultNode> outputNodes;
	private final Map<String, Float> inputValues;

	public TestbenchGenerator(String fileName, String path, Map<String, Float> inputValues) {
		optimizer = new de.gaalop.maple.Plugin().createOptimizationStrategy();
		this.path = new File(path);
		this.inputValues = inputValues;
		try {
			File file = new File(fileName);
			this.fileName = file.getName().substring(0, file.getName().indexOf(".clu"));
			inputFile = readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Must provide file name and path to scripts");
		}
		TestbenchGenerator generator = new TestbenchGenerator(args[0], args[1], null);
		generator.run();
	}

	public void run() {
		try {
			ControlFlowGraph graph = parser.parseFile(inputFile);
			originalFileName = generateExtendedCluFile(graph, false);
			optimizer.transform(graph);
			optFileName = generateExtendedCluFile(graph, true);

			generateTestbench(graph);
			System.out.println("Testbench has been generated successfully.");
		} catch (CodeParserException e) {
			e.printStackTrace();
		} catch (OptimizationException e) {
			e.printStackTrace();
		}
	}

	private InputFile readFile(File file) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(file));
		try {
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
				result.append('\n');
			}
		} finally {
			in.close();
		}
		return new InputFile(file.getName(), result.toString());
	}

	private String generateExtendedCluFile(ControlFlowGraph graph, boolean opt) {
		ExtendedCLUCalcGenerator generator = new ExtendedCLUCalcGenerator("_opt", opt);
		graph.accept(generator);
		String code = generator.getCode();
		String suffix = ".clu";
		if (opt) {
			suffix = "_opt" + suffix;
		}
		return writeFile(code, suffix);
	}

	private String writeFile(String code, String suffix) {
		String name = fileName + suffix;
		File file = new File(path.getAbsolutePath() + System.getProperty("file.separator") + name);
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(code);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("File " + file + " has been generated successfully.");
		return name;
	}

	private void generateTestbench(ControlFlowGraph graph) {
		StringBuilder code = new StringBuilder();
		// include directives
		code.append("#include \"cluviz/CLUVizDLL.h\"\n");
		code.append("#include <iostream>\n");
		code.append("#include <string>\n");
		
		code.append("\nusing namespace std;\n\n");
		
		code.append("const char* dir = \"");
		code.append(path.getAbsolutePath().replace(System.getProperty("file.separator"), "/"));
		code.append("/\";\n\n");
		
		code.append(CLU_BLADES);
		
		addPrint(code);
		
		addCppCalculate(code, graph);
		addCluCalculate(code, graph);
		
		addMain(code, graph);
		
		writeFile(code.toString(), ".cpp");
	}

	private void addPrint(StringBuilder code) {
		code.append("void printCluMV(float mv[32]) {\n");
		code.append("\tbool firstEntry = true;\n");
		code.append("\tfor (int i = 0; i < 32; i++) {\n");
		code.append("\t\tfloat value = mv[i];\n");
		code.append("\t\tif (value != 0) {\n");
		code.append("\t\t\tif (firstEntry) {\n");
		code.append("\t\t\t\tcout << value << \"^\" << CLU_BLADES[i];\n");
		code.append("\t\t\t\tfirstEntry = false;\n");
		code.append("\t\t\t} else {\n");
		code.append("\t\t\t\tif (value > 0) {\n");
		code.append("\t\t\t\t\tcout << \" + \"; \n");		
		code.append("\t\t\t\t} else {\n");
		code.append("\t\t\t\t\tcout << \" - \"; \n");		
		code.append("\t\t\t\t}\n");
		code.append("\t\t\t\tcout << fabs(value) << \"^\" << CLU_BLADES[i];\n");
		code.append("\t\t\t}\n");		
		code.append("\t\t}\n");		
		code.append("\t}\n");		
		code.append("\tif (firstEntry) {\n");		
		code.append("\t\tcout << 0;\n");		
		code.append("\t}\n");		
		code.append("\tcout << endl;\n");		
		code.append("}\n\n");		
	}

	private void addCppCalculate(StringBuilder code, ControlFlowGraph graph) {
		CppVisitor visitor = new CppVisitor(true);
		graph.accept(visitor);
		code.append(visitor.getCode());
		code.append("\n");
	}
	
	private List<Variable> sortVariables(Set<Variable> inputVariables) {
		List<Variable> variables = new ArrayList<Variable>(inputVariables);
		Comparator<Variable> comparator = new Comparator<Variable>() {

			@Override
			public int compare(Variable o1, Variable o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		};

		Collections.sort(variables, comparator);
		return variables;
	}
	
	private void addCluCalculate(StringBuilder code, ControlFlowGraph graph) {
		code.append("void calculateCLU(char* fileName");
		// create list to keep ordering consistent
		inputVariables = sortVariables(graph.getInputVariables());
		for (Variable v : inputVariables) {
			code.append(", float ");
			code.append(v.getName());
		}
		code.append(") {\n");
		
		code.append("\tchar pcDir[1024], *pcPos;\n");
		code.append("\tstrncpy_s( pcDir, (const char *) dir, 1023 );\n");
		code.append("\tpcPos = strrchr( pcDir, '/' );\n");
		code.append("\t*pcPos = 0; \n\n");
		
		code.append("\tint iVizHandle = 0;\n");		
		code.append("\tCLUViz::Start();\n");		
		code.append("\tCLUViz::SetScriptPath(pcDir);\n");		
		code.append("\tCLUViz::Create( iVizHandle, 0, 0, 0, 0, \"Gaalop Testbench\" );\n");		
		code.append("\tCLUViz::LoadScript( iVizHandle, fileName );\n\n");
		
		for (Variable v : inputVariables) {
			code.append("\tCLUViz::SetVarNumber(iVizHandle, ");
			code.append("\"");
			code.append(v.getName());
			code.append("\", ");
			code.append(v.getName());
			code.append(");\n");
		}
		
		code.append("\t\n");
		code.append("\tCLUViz::ExecUser(iVizHandle, \"calculate\");\n");
		code.append("\tCLUViz::ExecUser(iVizHandle, \"toString\");\n\n");
		
		FindStoreOutputNodes visitor = new FindStoreOutputNodes();
		graph.accept(visitor);
		outputNodes = new ArrayList<StoreResultNode>(visitor.getNodes());
		for (StoreResultNode mv : outputNodes) {
			code.append("\tchar ");		
			String name = mv.getValue().getName();
			code.append(name);
			code.append("[");
			code.append(MAX_MV_LENGTH + 1);
			code.append("];\n");
			code.append("\tCLUViz::GetVarString( iVizHandle, \"");
			code.append(name);
			code.append("\", ");
			code.append(name);
			code.append(", ");
			code.append(MAX_MV_LENGTH);
			code.append(");\n");
			code.append("\tprintf(\"%s\\n\", ");
			code.append(name);
			code.append(");\n");
		}
		
		code.append("\tCLUViz::Destroy(iVizHandle);\n");		
		code.append("}\n\n");
	}

	private void addMain(StringBuilder code, ControlFlowGraph graph) {
		code.append("int main(int argc, char* argv[]) {\n");
		for (Variable v : inputVariables) {
			code.append("\tfloat ");
			String name = v.getName();
			code.append(name);
			code.append(" = ");
			float value = 0.0f;
			if (inputValues != null && inputValues.get(name) != null) {
				value = inputValues.get(name);
			}
			code.append(value);
			code.append(";\n");
		}
		
		for (StoreResultNode mv : outputNodes) {
			code.append("\tfloat ");
			code.append(mv.getValue().getName());
			code.append("[32] = { 0.0f };\n");
		}
		
		code.append("\n");
		code.append("\tcout << \"C++ output:\" << endl;\n");
		code.append("\tcalculate(");
		for (Variable v : inputVariables) {
			code.append(v.getName());
			code.append(", ");
		}
		for (StoreResultNode mv : outputNodes) {
			code.append(mv.getValue().getName());
			code.append(", ");
		}
		code.delete(code.length() - 2, code.length());
		code.append(");\n");
		
		for (StoreResultNode mv : outputNodes) {
			code.append("\tprintCluMV(");
			code.append(mv.getValue().getName());
			code.append(");\n\n");
		}

		code.append("\tcout << \"Original file output:\" << endl;\n");
		code.append("\tcalculateCLU(\"");
		code.append(originalFileName);
		code.append("\"");
		for (Variable v : inputVariables) {
			code.append(", ");
			code.append(v.getName());
		}
		code.append(");\n");

		code.append("\tcout << \"Optimized CLUCalc file output:\" << endl;\n");
		code.append("\tcalculateCLU(\"");
		code.append(optFileName);
		code.append("\"");
		for (Variable v : inputVariables) {
			code.append(", ");
			code.append(v.getName());
		}
		code.append(");\n\n");
		
		code.append("\treturn 0;\n");
		code.append("}\n");
	}

}
