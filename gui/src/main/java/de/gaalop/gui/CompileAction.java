package de.gaalop.gui;

import de.gaalop.*;
import de.gaalop.gui.util.PluginIconUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 * This command object performs a compilation run.
 */
public class CompileAction extends AbstractAction {

	private static final long serialVersionUID = 2110643735874314468L;

	private Log log = LogFactory.getLog(CompileAction.class);

    private final CodeGeneratorPlugin codeGeneratorPlugin;

    private final SourceFilePanel sourcePanel;

	private final StatusBar statusBar;

    public CompileAction(SourceFilePanel sourcePanel, StatusBar statusBar, CodeGeneratorPlugin codeGeneratorPlugin) {
        super("To " + codeGeneratorPlugin.getName(), PluginIconUtil.getSmallIcon(codeGeneratorPlugin));
        this.codeGeneratorPlugin = codeGeneratorPlugin;
        this.sourcePanel = sourcePanel;
        this.statusBar = statusBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	statusBar.reset();

        VisualizerStrategyPlugin visualizerPlugin = getVisualizerStrategy();

        if (visualizerPlugin == null) {
            JOptionPane.showMessageDialog(null, "No visualizer strategy is available. Please install " +
                    "an appropiate plugin.", "No Visualizer Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AlgebraStrategyPlugin algebraPlugin = getAlgebraStrategy();

        if (algebraPlugin == null) {
            JOptionPane.showMessageDialog(null, "No algebra strategy is available. Please install " +
                    "an appropiate plugin.", "No Algebra Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OptimizationStrategyPlugin optimizationPlugin = getOptimizationStrategy();

        if (optimizationPlugin == null) {
            JOptionPane.showMessageDialog(null, "No optimization strategy is available. Please install " +
                    "an appropiate plugin.", "No Optimization Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CodeParserPlugin parserPlugin = sourcePanel.getParserPlugin();

        final CompilerFacade facade = new CompilerFacade(parserPlugin.createCodeParser(),
                visualizerPlugin.createVisualizerStrategy(),
                algebraPlugin.createAlgebraStrategy(),
                optimizationPlugin.createOptimizationStrategy(),
                codeGeneratorPlugin.createCodeGenerator());
        facade.addObserver(statusBar);

        // start new thread in order to see status changes in main thread (GUI)
		Thread compiler = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Set<OutputFile> output;
					output = facade.compile(sourcePanel.getInputFile());
                                        if (!output.isEmpty())
                                            displayOutput(output);
				} catch (CompilationException ex) {
					log.error("Compilation exception", ex);
					statusBar.displayError(ex);
					ex.printStackTrace();
					ErrorDialog.show(ex);
				}

			}
		});
		compiler.start();
    }

    private AlgebraStrategyPlugin getAlgebraStrategy() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String preferredAlgebraPlugin = prefs.get("preferredAlgebraPlugin", "");
        log.debug("Preferred algebra plugin is " + preferredAlgebraPlugin);

        for (AlgebraStrategyPlugin plugin : Plugins.getAlgebraStrategyPlugins()) {
            String name = plugin.getClass().getName();
            if (name.equals(preferredAlgebraPlugin)) {
                return plugin;
            }
        }

        if (Plugins.getAlgebraStrategyPlugins().size() > 0) 
            return Plugins.getAlgebraStrategyPlugins().iterator().next();
        

        return null;
    }

    private VisualizerStrategyPlugin getVisualizerStrategy() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String preferredVisualizerPlugin = prefs.get("preferredVisualizerPlugin", "");
        log.debug("Preferred visualizer plugin is " + preferredVisualizerPlugin);

        for (VisualizerStrategyPlugin plugin : Plugins.getVisualizerStrategyPlugins()) {
            String name = plugin.getClass().getName();
            if (name.equals(preferredVisualizerPlugin)) {
                return plugin;
            }
        }

        if (Plugins.getVisualizerStrategyPlugins().size() > 0)
            return Plugins.getVisualizerStrategyPlugins().iterator().next();


        return null;
    }

    private OptimizationStrategyPlugin getOptimizationStrategy() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String preferredOptimizationPlugin = prefs.get("preferredOptimizationPlugin", "");
        log.debug("Preferred optimization plugin is " + preferredOptimizationPlugin);

        for (OptimizationStrategyPlugin plugin : Plugins.getOptimizationStrategyPlugins()) {
            String name = plugin.getClass().getName();
            if (name.equals(preferredOptimizationPlugin)) {
                return plugin;
            }
        }
        return null;
    }

    private void displayOutput(Set<OutputFile> output) {
        ResultForm resultForm = new ResultForm(output);

        JFrame outputFrame = new JFrame("Compilation Result");
        outputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        outputFrame.setContentPane(resultForm.getContentPane());
        outputFrame.setPreferredSize(new Dimension(640, 480));
        outputFrame.setIconImage(getIcon());
        outputFrame.pack();
        outputFrame.setLocationRelativeTo(null);
        outputFrame.setVisible(true);
    }

    public Image getIcon() {
        URL iconUrl = getClass().getResource("icon.png");
        if (iconUrl == null) {
            log.warn("Couldn't find application icon.");
            return null;
        }

        try {
            return ImageIO.read(iconUrl);
        } catch (IOException e) {
            log.error("Unable to load application icon.", e);
            return null;
        }
    }
}
