package de.gaalop.segmenter;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;

/**
 * Merges the code segments together to create a new file.
 * @author thomaskanold
 *
 */
public class Merger {

	private List<CodeSegment> texts;

	public Merger(List<CodeSegment> texts) {
		this.texts = texts;
	}

	public Set<OutputFile> getOutputFile() {
		String output = new String();
		for(CodeSegment str: texts){
			output += str.getString();
		}
		
		OutputFile file = new OutputFile("compilerDriver.c", output, Charset.forName("UTF-8"));

		return 	Collections.singleton(file);
	}

}
