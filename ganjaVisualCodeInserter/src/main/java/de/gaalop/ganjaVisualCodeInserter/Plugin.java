package de.gaalop.ganjaVisualCodeInserter;

import de.gaalop.Notifications;
import de.gaalop.VisualCodeInserterStrategy;
import de.gaalop.VisualCodeInserterStrategyPlugin;
import java.awt.Image;
import java.util.Observable;

/**
 * Sets the algebra on the Control Flow Graph
 * @author Christian Steinmetz
 */
public class Plugin extends Observable implements VisualCodeInserterStrategyPlugin {

    @Override
    public VisualCodeInserterStrategy createVisualCodeInserterStrategy() {
        return new GanjaVisualizerCodeInserter(this);
    }

    @Override
    public String getName() {
        return "Ganja Visual Code Inserter";
    }

    @Override
    public String getDescription() {
        return "This plugin sets the visualizing commands for Ganja";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    void notifyError(Throwable error) {
        setChanged();
        notifyObservers(new Notifications.Error(error));
    }

}
