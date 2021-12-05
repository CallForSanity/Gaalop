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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;

/**
 * This command object performs a compilation run.
 */
public class CompileAction extends AbstractAction {

	private static final long serialVersionUID = 2110643735874314468L;

	private Log log = LogFactory.getLog(CompileAction.class);

    private final SourceFilePanel sourcePanel;

	private final StatusBar statusBar;
        
        private PanelPluginSelection panelPluginSelection;

    public CompileAction(SourceFilePanel sourcePanel, StatusBar statusBar, PanelPluginSelection panelPluginSelection) {
        super("To " + panelPluginSelection.getCodeGeneratorPlugin().getName(), PluginIconUtil.getSmallIcon(panelPluginSelection.getCodeGeneratorPlugin()));
        this.panelPluginSelection = panelPluginSelection;
        this.sourcePanel = sourcePanel;
        this.statusBar = statusBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AlgebraChooserItem algebraToUse = panelPluginSelection.getAlgebraToUse();
        PanelPluginSelection.lastUsedAlgebra = algebraToUse.algebraName;
        PanelPluginSelection.lastUsedAlgebraRessource = algebraToUse.ressource;
        
    	statusBar.reset();

        GlobalSettingsStrategyPlugin globalSettingsPlugin = panelPluginSelection.getGlobalSettingsStrategyPlugin();

        if (globalSettingsPlugin == null) {
            JOptionPane.showMessageDialog(null, "No GlobalSettings strategy is available. Please install " +
                    "an appropiate plugin.", "No GlobalSettings Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        VisualCodeInserterStrategyPlugin visualizerPlugin = panelPluginSelection.getVisualizerStrategyPlugin();

        if (visualizerPlugin == null) {
            JOptionPane.showMessageDialog(null, "No visualizer strategy is available. Please install " +
                    "an appropiate plugin.", "No Visualizer Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AlgebraStrategyPlugin algebraPlugin = panelPluginSelection.getAlgebraStrategyPlugin();

        if (algebraPlugin == null) {
            JOptionPane.showMessageDialog(null, "No algebra strategy is available. Please install " +
                    "an appropiate plugin.", "No Algebra Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OptimizationStrategyPlugin optimizationPlugin = panelPluginSelection.getOptimizationStrategyPlugin();

        if (optimizationPlugin == null) {
            JOptionPane.showMessageDialog(null, "No optimization strategy is available. Please install " +
                    "an appropiate plugin.", "No Optimization Strategy Available", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CodeParserPlugin parserPlugin = sourcePanel.getParserPlugin();
        
        AlgebraStrategyPlugin algebra = Plugins.getAlgebraStrategyPlugins().iterator().next();
        
        String algebraBaseDirectory = "";
        try {
             Field field = algebra.getClass().getField("additionalBaseDirectory");
             algebraBaseDirectory = BeanUtils.getProperty(algebra, field.getName()).trim();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        // create optimitzation strategy from plugin and add StatusBar as listener to be notified
        // when progress is made.
        OptimizationStrategy optStrategy = optimizationPlugin.createOptimizationStrategy();
  	  	optStrategy.addProgressListener(new LoggingListener() {
  	  		@Override
  	  		public void logNote(String topic, Object args) {
  	  			statusBar.setStatus(topic);
  	  			statusBar.setProgressPercent((double)args);
  	  		}
  	  	});

        
        final CompilerFacade facade = new CompilerFacade(parserPlugin.createCodeParser(),
                globalSettingsPlugin.createGlobalSettingsStrategy(),
                visualizerPlugin.createVisualCodeInserterStrategy(),
                algebraPlugin.createAlgebraStrategy(),
                optStrategy,
                panelPluginSelection.getCodeGeneratorPlugin().createCodeGenerator(),
                algebraToUse.algebraName,algebraToUse.ressource,algebraBaseDirectory
                );
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
