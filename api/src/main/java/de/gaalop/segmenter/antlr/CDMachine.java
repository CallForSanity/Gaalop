package de.gaalop.segmenter.antlr;

import java.util.LinkedList;
import java.util.List;

import de.gaalop.segmenter.*;

public class CDMachine {

	private List <CodeSegment> segments;
	private CodeSegment currentSegment;
	
	public CDMachine() {
		segments = new LinkedList<CodeSegment>();
	}
	
	/** 
	 * Add string to current segment.
	 * @param text
	 */
	public void addString(String text) {
		if (currentSegment == null) {
			changeState(new CodeRaw());
		}
		
		currentSegment.addString(text);
	}
	
	public void changeState(CodeSegment segment) {
		currentSegment = segment;
		segments.add(currentSegment);
		
		addHiddenCode(currentSegment);
	}
	
	/**
	 * What we want is to add all CodeHide segments to the current segment.
	 * However we first will test if it is an CodeOptimize. 
	 * @param segment
	 */
	private void addHiddenCode(CodeSegment segment) {
		if ( segment instanceof CodeOptimize)
			for (CodeSegment seg : segments) {
				if (seg instanceof CodeHide){
					String buffer = new String();
					buffer += segment.getString();
					segment.clear();
					segment.addString( seg.getString());
					segment.addString(buffer);
					
					System.err.println("code " +segment.getString());
				}
			}
	}
	
	public List<CodeSegment> getCodeSegments() {
		return segments;
	}	
}
