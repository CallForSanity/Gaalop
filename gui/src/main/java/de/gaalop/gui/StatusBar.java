package de.gaalop.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.CodeParserPlugin;
import de.gaalop.CompilerFacade;
import de.gaalop.Notifications;
import de.gaalop.OptimizationStrategyPlugin;

/**
 * This class models a model-view-control ready version of {@link JProgressBar}. This class can be registered as
 * observer of a {@link CodeParserPlugin} in order to visualize the compilation process. The
 * {@link #update(Observable, Object)} method is implemented to call {@link #setMaximum(int)}, so notifications about a
 * new maximum of steps are recorded accordingly.
 * 
 * @author Christian Schwinn
 * 
 */
public class StatusBar extends JPanel implements Observer {

	private static final long serialVersionUID = -1854594532112610893L;

	private final String spacer = "   ";
	private final JProgressBar progressBar;
	private final JLabel statusLabel;
	private Throwable ex;
	private List<Notifications.Warning> warnings;

	public StatusBar() {
		setLayout(new BorderLayout(10, 0));
		progressBar = new JProgressBar();
		statusLabel = new JLabel();
		statusLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ex != null) {
					ErrorDialog.show(ex);
				} 
				if (warnings != null) {
					displayWarnings();
				}
			}
		});
		setStatus("Ready");
		add(statusLabel, BorderLayout.WEST);
		add(progressBar, BorderLayout.CENTER);
		add(new JLabel(spacer), BorderLayout.EAST);
	}

	private void setStatus(String status) {
		statusLabel.setText(spacer + status);
	}

	@Override
	public void update(Observable o, Object arg) {
		// check if observable is a code parser plugin
		if (o instanceof CodeParserPlugin) {
			updateFromCodeParser(arg);
		}
		// check if observable is an optimization strategy plugin
		if (o instanceof OptimizationStrategyPlugin) {
			updateFromOptimizer(arg);
		}
		// check if observable is a compiler facade
		if (o instanceof CompilerFacade) {
			updateFromCompilerFacade(arg);
		}
		// check if observable is a code generator
		if (o instanceof CodeGeneratorPlugin) {
			updateFromCodeGenerator(arg);
		}
	}

	private void updateFromCodeParser(Object arg) {
		// currently no update
	}

	private void updateFromOptimizer(Object arg) {
		if (arg instanceof Notifications.Start) {
			progressBar.setValue(0);
		} else if (arg instanceof Notifications.Number) {
			progressBar.setMaximum(((Notifications.Number) arg).getValue());
		} else if (arg instanceof Notifications.Error) {
			Throwable ex = ((Notifications.Error) arg).getError();
			displayError(ex);
			ErrorDialog.show(ex);
		} else if (arg instanceof Notifications.Progress) {
			progressBar.setValue(progressBar.getValue() + 1);
		}
	}

	private void updateFromCompilerFacade(Object arg) {
		if (arg instanceof Notifications.Info) {
			setStatus(((Notifications.Info) arg).getMessage());
		} else if (arg instanceof Notifications.Finished) {
			setStatus("Finished");
			progressBar.setValue(progressBar.getMaximum());
			if (Notifications.hasWarnings()) {
				warnings = Notifications.getWarnings();
				setStatus("Finished (click here to see were warnings)");
			}
		}
	}
	
	private void updateFromCodeGenerator(Object arg) {
		if (arg instanceof Notifications.Error) {
			Throwable ex = ((Notifications.Error) arg).getError();
			displayError(ex);
			ErrorDialog.show(ex);
		}
	}

	private void displayWarnings() {
		if (warnings != null && warnings.size() > 0) {
			int n = warnings.size();
			int i = 1;
			StringBuilder messages = new StringBuilder();
			for (Notifications.Warning warning : warnings) {
				messages.append("Warning " + i++ + " of " + n + ":\n");
				messages.append(warning.getMessage());
				messages.append('\n');
			}
			JOptionPane.showMessageDialog(null, messages.toString(), "Warnings", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void reset() {
		ex = null;
		warnings = null;
		Notifications.clearWarnings();
		statusLabel.setForeground(Color.BLACK);
		setStatus("Ready");
		progressBar.setValue(0);
	}

	public void displayError(Throwable ex) {
		this.ex = ex;
		statusLabel.setForeground(Color.RED);
		setStatus("Error (click here to see error dialog)");
	}

}
