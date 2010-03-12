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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 * This command object performs a compilation run.
 */
public class CompileAction extends AbstractAction {

    private Log log = LogFactory.getLog(CompileAction.class);

    private final CodeGeneratorPlugin codeGeneratorPlugin;

    private final SourceFilePanel sourcePanel;

    public CompileAction(SourceFilePanel sourcePanel, CodeGeneratorPlugin codeGeneratorPlugin) {
        super("To " + codeGeneratorPlugin.getName(), PluginIconUtil.getSmallIcon(codeGeneratorPlugin));
        this.codeGeneratorPlugin = codeGeneratorPlugin;
        this.sourcePanel = sourcePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OptimizationStrategyPlugin optimizationPlugin = getOptimizationStrategy();

        if (optimizationPlugin == null) {
            JOptionPane.showMessageDialog(null, "No optimization strategy is available. Please install " +
                    "an appropiate plugin.", "No Optimization Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CodeParserPlugin parserPlugin = sourcePanel.getParserPlugin();

        CompilerFacade facade = new CompilerFacade(parserPlugin.createCodeParser(),
                optimizationPlugin.createOptimizationStrategy(),
                codeGeneratorPlugin.createCodeGenerator());

        try {
            Set<OutputFile> output = facade.compile(sourcePanel.getInputFile());
            displayOutput(output);
        } catch (CompilationException ex) {
            ErrorDialog.show(ex);
        }
    }

    private OptimizationStrategyPlugin getOptimizationStrategy() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String preferredOptimizationPlugin = prefs.get("preferredOptimizationPlugin", "");
        log.debug("Preferred optimization plugin is " + preferredOptimizationPlugin);

        OptimizationStrategyPlugin optimizationPlugin = null;
        for (OptimizationStrategyPlugin plugin : Plugins.getOptimizationStrategyPlugins()) {
            optimizationPlugin = plugin;
            if (plugin.getClass().getName().equals(preferredOptimizationPlugin)) {
                break;
            }
        }
        return optimizationPlugin;
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
