package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;

import java.util.Observable;
import java.util.Set;

/**
 * Represents the high level compilation process.
 */
public final class CompilerFacade extends Observable {

    private final CodeParser codeParser;

    private final OptimizationStrategy optimizationStrategy;

    private final CodeGenerator codeGenerator;

    /**
     * Constructs a new compiler facade.
     *
     * @param codeParser The code parser used by this facade to construct a dataflow graph from an input file.
     * @param optimizationStrategy The optimization strategy used to process the graph before generating code.
     * @param codeGenerator The code generator used to generate code from the previously optimized graph.
     */
    public CompilerFacade(CodeParser codeParser, OptimizationStrategy optimizationStrategy, CodeGenerator codeGenerator) {
        this.codeParser = codeParser;
        this.optimizationStrategy = optimizationStrategy;
        this.codeGenerator = codeGenerator;
    }

    /**
     * Compiles an input file using the previously configured subsystems.
     *
     * @param input The input file that should be compiled.
     * @return A set of output files that represent the compilation result.
     * @throws CompilationException If any error occurs during compilation.
     */
    public Set<OutputFile> compile(InputFile input) throws CompilationException {
    	setChanged();
    	notifyObservers("Parsing...");
        ControlFlowGraph graph = codeParser.parseFile(input);
        setChanged();
        notifyObservers("Optimizing...");
        optimizationStrategy.transform(graph);
        setChanged();
        notifyObservers("Generating Code...");
        Set<OutputFile> output = codeGenerator.generate(graph);
        setChanged();
        notifyObservers("Finished");        
        return output;
    }
}
