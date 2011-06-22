package de.gaalop.gappZwei;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OutputFile;
import de.gaalop.cfg.AlgebraSignature;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.clucalc.algebra.AlgebraN3;
import de.gaalop.clucalc.input.CluCalcCodeParser;
import de.gaalop.clucalc.output.CluCalcCodeGenerator;
import de.gaalop.cpp.Plugin;
import de.gaalop.dfg.Variable;
import de.gaalop.dot.DotCodeGenerator;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.visitor.GraphExporter;
import de.gaalop.gapp.visitor.PrettyPrint;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSignedMultivectorComponent;
import de.gaalop.gapp.variables.GAPPVariable;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gappZwei.cfgimport.CFGImporter2;
import de.gaalop.gappZwei.cfgimport.ConstantFolding;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPVariableBase;

public class GAPPProgram {


    public static void testWithCreateGAPPProgram() {
        GAPP createProgram = loadProgram();
        ControlFlowGraph graph = exportControlFlowGraph(createProgram);
        generateCLU(graph, 0);
        generateDot(graph, 0);
    }

    private static GAPP loadProgram() {
        GAPPProgramReader reader = new GAPPProgramReader();
        return reader.readFile(new File("/daten/testPC.gapp"));
    }

    public static void main(String[] args) {


        CFGImporter2 c = new CFGImporter2();

        CluCalcCodeParser instance = CluCalcCodeParser.INSTANCE;
        InputFile input = new InputFile("kreis.clu", readFile(new File("/daten/kreis.clu")));

        ControlFlowGraph graph;
        try {
            graph = instance.parseFile(input);
            generateDot(graph, 0);
            //GAPPProgram program = c.importGraph(graph);
            //ControlFlowGraph graphopt = program.exportControlFlowGraph();
            ControlFlowGraph graphopt = c.importGraph(graph);
            ConstantFolding con = new ConstantFolding();

            //Get StoreResultOutputNodes and eliminate unused assignments


            generateCLU(graphopt, 1);
            generateDot(graphopt, 1);
            generateCpp(graphopt, 1);
            generateJava(graphopt, 1);

            graphopt.accept(con);

            generateCLU(graphopt, 2);
            generateDot(graphopt, 2);
            generateCpp(graphopt, 2);
            generateJava(graphopt, 2);

        } catch (CodeParserException e) {
            e.printStackTrace();
        }

    }

    private static void generateCLU(ControlFlowGraph graph, int run) {
        CluCalcCodeGenerator c = new CluCalcCodeGenerator("_opt");
        Set<OutputFile> generate = c.generate(graph);
        writeFile(generate.iterator().next().getContent(), new File("/home/christian/opt" + run + ".clu"));


    }

    private static void generateCpp(ControlFlowGraph graph, int run) {
        Plugin p = new Plugin();
        CodeGenerator codeGen = p.createCodeGenerator();
        Set<OutputFile> generate;
        try {
            generate = codeGen.generate(graph);
            writeFile(generate.iterator().next().getContent(), new File("/home/christian/opt" + run + ".cpp"));

        } catch (CodeGeneratorException e) {
            e.printStackTrace();
        }


    }

    private static void generateJava(ControlFlowGraph graph, int run) {

        de.gaalop.java.Plugin p = new de.gaalop.java.Plugin();
        CodeGenerator codeGen = p.createCodeGenerator();
        Set<OutputFile> generate;
        try {
            generate = codeGen.generate(graph);
            writeFile(generate.iterator().next().getContent(), new File("/home/christian/opt" + run + ".java"));

        } catch (CodeGeneratorException e) {
            e.printStackTrace();
        }


    }

    private static void generateDot(ControlFlowGraph graph, int run) {
        DotCodeGenerator dotGen = DotCodeGenerator.INSTANCE;

        try {
            Set<OutputFile> generate = dotGen.generate(graph);

            writeFile(generate.iterator().next().getContent(), new File("/home/christian/opt" + run + ".dot"));

            ProcessBuilder p = new ProcessBuilder("dot", "-Tsvg", "-oopt" + run + ".svg", "opt" + run + ".dot");
            p.directory(new File("/home/christian/"));
            p.start();


        } catch (CodeGeneratorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String out, File file) {
        PrintWriter wrt;
        try {
            wrt = new PrintWriter(file);
            wrt.print(out);
            wrt.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(File file) {
        StringBuilder sb = new StringBuilder("");

        try {
            BufferedReader r = new BufferedReader(new FileReader(file));

            while (r.ready()) {
                sb.append(r.readLine() + "\n");
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private GAPP createProgram() {

        LinkedList<GAPPVariableBase> variables = new LinkedList<GAPPVariableBase>();
        
        GAPP program = new GAPP();

//		resetMv(t);
        GAPPMultivector t = new GAPPMultivector("t");
        variables.add(t);
        program.addInstruction(new GAPPResetMv(t));

//		assignMv(m,1,2,2,3,3,5);
        GAPPMultivector m = new GAPPMultivector("m");
        variables.add(m);
        program.addInstruction(new GAPPResetMv(m));

        GAPPAssignMv ass = new GAPPAssignMv(m);
        ass.add(1, new GAPPVariable(2));
        ass.add(2, new GAPPVariable(3));
        ass.add(3, new GAPPVariable(5));
        program.addInstruction(ass);

//		setVector(mP,m{1},m{2},m{3});
        GAPPVector mP = new GAPPVector("mP");
        variables.add(mP);
        GAPPSetVector set = new GAPPSetVector(mP);
        set.add(new GAPPSignedMultivectorComponent(m, 1));
        set.add(new GAPPSignedMultivectorComponent(m, 2));
        set.add(new GAPPSignedMultivectorComponent(m, 3));
        program.addInstruction(set);

//		resetMv(m1);
//		assignMv(m1,1,1);
        GAPPMultivector m1 = new GAPPMultivector("m1");
        variables.add(m1);
        program.addInstruction(new GAPPResetMv(m1));

        GAPPAssignMv ass1 = new GAPPAssignMv(m1);
        ass1.add(1, new GAPPVariable(1));
        program.addInstruction(ass1);

//		setVector(mP1,m1{1},m1{2},m1{3});
        GAPPVector mP1 = new GAPPVector("mP1");
        variables.add(mP1);
        GAPPSetVector set1 = new GAPPSetVector(mP1);
        set1.add(new GAPPSignedMultivectorComponent(m1, 1));
        set1.add(new GAPPSignedMultivectorComponent(m1, 2));
        set1.add(new GAPPSignedMultivectorComponent(m1, 3));
        program.addInstruction(set1);

//		dotVectors(t{1},mP,mP1);
        program.addInstruction(new GAPPDotVectors(t, 1, mP, mP1));

        return program;
    }

    public String prettyPrint(GAPP gapp) {

        PrettyPrint printer = new PrettyPrint();

        gapp.accept(printer, null);


        return printer.getResultString();
    }

    public static ControlFlowGraph exportControlFlowGraph(GAPP gapp) {

        HashMap<GAPPVariableBase, Variable> variablen = new HashMap<GAPPVariableBase, Variable>();

        ControlFlowGraph graph = new ControlFlowGraph();

        GraphExporter exporter = new GraphExporter(graph, variablen);

        gapp.accept(exporter, null);

        return graph;
    }
}
