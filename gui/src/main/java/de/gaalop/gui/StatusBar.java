package de.gaalop.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import de.gaalop.CodeParserPlugin;
import de.gaalop.CompilerFacade;
import de.gaalop.OptimizationStrategyPlugin;

/**
 * This class models a model-view-control ready version of {@link JProgressBar}. This class can be registered as observer of a
 * {@link CodeParserPlugin} in order to visualize the compilation process. The {@link #update(Observable, Object)} method is
 * implemented to call {@link #setMaximum(int)}, so notifications about a new maximum of steps are recorded accordingly.
 * 
 * @author Christian Schwinn
 * 
 */
public class StatusBar extends JPanel implements Observer {

	private static final long serialVersionUID = -1854594532112610893L;
	
	private final String spacer = "   ";
	private final JProgressBar progressBar;
	private final JLabel statusLabel;
	private Exception ex;
	
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
		if (o instanceof CodeParserPlugin && arg instanceof Integer) {
			progressBar.setMaximum(((Integer) arg).intValue());
		}
		// check if observable is an optimization strategy plugin
		if (o instanceof OptimizationStrategyPlugin) {
			if (arg instanceof Integer) {
				progressBar.setValue(((Integer) arg).intValue());
			} else if (arg instanceof String) {
				Exception ex = new IllegalArgumentException((String) arg);
				displayError(ex);
				ErrorDialog.show(ex);
			} else {
				progressBar.setValue(progressBar.getValue() + 1);
			}
		}
		// check if observable is a compiler facade
		if (o instanceof CompilerFacade) {
			if (arg instanceof String) {
				setStatus((String) arg);
			}
		}
	}
	
	public void reset() {
		ex = null;
		statusLabel.setForeground(Color.BLACK);
		setStatus("Ready");
		progressBar.setValue(0);
	}

	public void displayError(Exception ex) {
		this.ex = ex;
		statusLabel.setForeground(Color.RED);
		setStatus("Error (click here to see error dialog)");
	}

}
