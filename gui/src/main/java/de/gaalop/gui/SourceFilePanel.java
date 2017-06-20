package de.gaalop.gui;

import de.gaalop.CodeParserPlugin;
import de.gaalop.GlobalSettingsStrategyPlugin;
import de.gaalop.InputFile;
import de.gaalop.Plugins;
import de.gaalop.gui.util.PluginIconUtil;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a panel in a tabbed panel that contains a source file.
 */
public class SourceFilePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -662488304785792145L;

	private final static Map<String, SimpleAttributeSet> KEYWORDS;
	private final static Pattern PATTERN;

	static {
		KEYWORDS = new HashMap<String, SimpleAttributeSet>();
		SimpleAttributeSet keyword = new SimpleAttributeSet();
		StyleConstants.setBold(keyword, true);
		StyleConstants.setForeground(keyword, Color.BLUE);
		SimpleAttributeSet forbidden = new SimpleAttributeSet();
		StyleConstants.setBold(forbidden, true);
		StyleConstants.setForeground(forbidden, Color.RED);
/*
		KEYWORDS.put("DefVarsN3", keyword);
		KEYWORDS.put("DefVarsE3", keyword);

		KEYWORDS.put("IPNS", keyword);
		KEYWORDS.put("OPNS", keyword);

		KEYWORDS.put("VecN3", keyword);
		KEYWORDS.put("VecE3", keyword);
		KEYWORDS.put("SphereN3", keyword);
		KEYWORDS.put("RotorE3", keyword);
		KEYWORDS.put("RotorN3", keyword);
		KEYWORDS.put("TranslatorN3", keyword);
		
		KEYWORDS.put("if", keyword);
		KEYWORDS.put("else", keyword);
		KEYWORDS.put("loop", keyword);
		KEYWORDS.put("break", keyword);
		
		KEYWORDS.put("true", keyword);
		KEYWORDS.put("false", keyword);
				
		KEYWORDS.put("#pragma unroll", keyword);
		
		KEYWORDS.put("Slider", keyword);
		KEYWORDS.put("Color", keyword);
		KEYWORDS.put("_BGColor", keyword);
		KEYWORDS.put("Black", keyword);
		KEYWORDS.put("Blue", keyword);
		KEYWORDS.put("Cyan", keyword);
		KEYWORDS.put("Green", keyword);
		KEYWORDS.put("Magenta", keyword);
		KEYWORDS.put("Orange", keyword);
		KEYWORDS.put("Red", keyword);
		KEYWORDS.put("White", keyword);
		KEYWORDS.put("Yellow", keyword);
		
		KEYWORDS.put("norm ", forbidden);
		KEYWORDS.put("normal ", forbidden);
		KEYWORDS.put("length ", forbidden);
		KEYWORDS.put("point ", forbidden);
 */
		
		String regex = "";
		Iterator<String> it = KEYWORDS.keySet().iterator();
		while (it.hasNext()) {
			regex += it.next() + "+";
			if (it.hasNext()) {
				regex += "|";
			}
		}
		PATTERN = Pattern.compile(regex);
	}

	private final CodeParserPlugin parserPlugin;

	final JTextPane textPane;

	private final JLabel tabLabel;

	private File file;

	FileState fileState;

	String savedContent;

	public SourceFilePanel(CodeParserPlugin plugin) {
		this(plugin, new File("New File"), "");
                
		fileState = FileState.UNSAVED;
	}

	public SourceFilePanel(CodeParserPlugin plugin, File file, String content) {
		super(new BorderLayout(), true);
                

		this.parserPlugin = plugin;
		this.file = file;
		this.fileState = FileState.SAVED;
		this.savedContent = content;

		// The text editor for our source code
		textPane = new JTextPane();
                
                int fontSize = FontSize.getEditorFontSize();
                int guiFontSize = FontSize.getGuiFontSize();
                
                textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
                
                

		textPane.setText(content);
		formatCode(content);
		textPane.addKeyListener(new SetChangedStateListener());

		// Add the scroll pane
		JScrollPane scrollPane = new JScrollPane(textPane);
		add(scrollPane, BorderLayout.CENTER);

		// Create a component for the Tab Label
		if (plugin.getIcon() != null) {
			tabLabel = new JLabel("", PluginIconUtil.getSmallIcon(plugin), SwingConstants.LEFT);
		} else {
			tabLabel = new JLabel("", SwingConstants.LEFT);
		}
                tabLabel.setFont(new Font("Arial", Font.PLAIN, guiFontSize));

		updateTabLabel();
	}

	void formatCode(String content) {
		try {
			StyledDocument doc = textPane.getStyledDocument();
			Matcher matcher = PATTERN.matcher(content);
			while (matcher.find()) {
				int start = matcher.start();
				int end = matcher.end();
				String keyword = matcher.group();
				doc.setCharacterAttributes(start, end-start, KEYWORDS.get(keyword), false);
			}
		} catch (Exception e) {
			System.err.println(e);
			textPane.setText(content);
		}
	}

	/**
	 * Creates an input file that represents this tabs current content.
	 * 
	 * @return A new input file that represents this tabs content.
	 */
	public InputFile getInputFile() {
		return new InputFile(file.getName(), textPane.getText());
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		updateTabLabel();
	}

	void updateTabLabel() {
            
		if (fileState != FileState.SAVED) {
			tabLabel.setText(file.getName() + "*");
		} else {
			tabLabel.setText(file.getName());
		}
                final Font font = new Font("Arial", Font.PLAIN, FontSize.getGuiFontSize());
                tabLabel.setFont(font);
	}

	public CodeParserPlugin getParserPlugin() {
		return parserPlugin;
	}

	public JLabel getTabLabel() {
		return tabLabel;
	}

	public void setSaved() {
		fileState = FileState.SAVED;
		savedContent = textPane.getText();
		updateTabLabel();
	}

	public FileState getFileState() {
		return fileState;
	}

	private class SetChangedStateListener extends KeyAdapter {
		
		public SetChangedStateListener() {
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if (fileState != FileState.UNSAVED) {
				if (textPane.getText().equals(savedContent)) {
					fileState = FileState.SAVED;
					updateTabLabel();
				} else {
					fileState = FileState.CHANGED;
					updateTabLabel();
				}
			}
			formatCode(textPane.getText());
		}
	}
}
