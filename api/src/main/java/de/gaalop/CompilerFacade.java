package de.gaalop;

import de.gaalop.segmenter.CodeSegment;
import de.gaalop.segmenter.Merger;
import de.gaalop.segmenter.Operator;
import de.gaalop.segmenter.Splitter;
import de.gaalop.cfg.ControlFlowGraph;

import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 * Represents the high level compilation process.
 */
public final class CompilerFacade extends Observable {

    private final CodeParser codeParser;
    
    private final GlobalSettingsStrategy globalSettingsStrategy;

    private final VisualCodeInserterStrategy visualizerStrategy;

    private final AlgebraStrategy algebraStrategy;

    private final OptimizationStrategy optimizationStrategy;

    private final CodeGenerator codeGenerator;
    
    private static boolean useCodeSegmenter;

    public static boolean isUseCodeSegmenter() {
		return useCodeSegmenter;
	}

	public static void setUseCodeSegmenter(boolean useCodeSegmenter) {
		CompilerFacade.useCodeSegmenter = useCodeSegmenter;
	}

	/**
     * Constructs a new compiler facade.
     *
     * @param codeParser The code parser used by this facade to construct a dataflow graph from an input file.
     * @param optimizationStrategy The optimization strategy used to process the graph before generating code.
     * @param codeGenerator The code generator used to generate code from the previously optimized graph.
     */
    public CompilerFacade(CodeParser codeParser, GlobalSettingsStrategy globalSettingsStrategy, VisualCodeInserterStrategy visualizerStrategy, AlgebraStrategy algebraStrategy, OptimizationStrategy optimizationStrategy, CodeGenerator codeGenerator) {
        this.codeParser = codeParser;
        this.globalSettingsStrategy = globalSettingsStrategy;
        this.visualizerStrategy = visualizerStrategy;
        this.algebraStrategy = algebraStrategy;
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
    	if (useCodeSegmenter)
    		return realCompileSegmenter(input);
    	return realCompile(input);
    }
    
    
    
    private Set<OutputFile> realCompile(InputFile input) throws CompilationException {
    	setChanged();
    	notifyObservers("Parsing...");
        ControlFlowGraph graph = codeParser.parseFile(input);
        setChanged();
        
        notifyObservers("Setting global settings...");
        globalSettingsStrategy.transform(graph);
        setChanged();

        notifyObservers("Inserting code for visualization...");
        visualizerStrategy.transform(graph);
        setChanged();

        notifyObservers("Algebra inserting...");  
        algebraStrategy.transform(graph);
        setChanged();
        
        notifyObservers("Optimizing...");  //FIXME thomas
        optimizationStrategy.transform(graph);
        setChanged();
        
        notifyObservers("Generating Code...");
        Set<OutputFile> output = codeGenerator.generate(graph);  
        setChanged();
        notifyObservers("Finished");        
        return output;   	
    }
    
    /**
     * Using code segmentation.
     * @param input
     * @return
     * @throws CompilationException
     */
    private Set<OutputFile> realCompileSegmenter(InputFile input) throws CompilationException {
    	setChanged();
    	notifyObservers("Parsing...");
    	
    	Splitter parts = new Splitter(input, codeParser, optimizationStrategy, codeGenerator);
        List <CodeSegment> splitted = parts.getCodeSegments();
        setChanged();
        
        Operator operate = new Operator (splitted);
        List <CodeSegment> texts = operate.getCodeSegments();

        Merger merge = new Merger(texts);
        Set<OutputFile> output = merge.getOutputFile();  
        setChanged();
        notifyObservers("Finished");        
        return output;   	
    }    
    
    
    
}
