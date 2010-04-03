package de.gaalop.gui;

import javax.swing.JScrollPane;

import de.gaalop.OutputFile;

public class OutputFilePane extends JScrollPane {
	
	private static final long serialVersionUID = 1696862320889803973L;
	
	private OutputFile file;
	
	public OutputFilePane(OutputFile file) {
		this.file = file;
	}
	
	public OutputFile getFile() {
		return file;
	}

}
