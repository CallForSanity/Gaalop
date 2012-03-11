package de.gaalop.algebra;

import de.gaalop.AlgebraStrategy;
import de.gaalop.AlgebraStrategyPlugin;
import de.gaalop.ConfigurationProperty;
import de.gaalop.ConfigurationProperty.Type;
import de.gaalop.Notifications;
import java.awt.Image;
import java.util.Observable;

/**
 * Sets the algebra on the Control Flow Graph
 * @author Christian Steinmetz
 */
public class Plugin extends Observable implements AlgebraStrategyPlugin {

    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean usePrecalulatedTables = true;
    
    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean useBuiltInFiles = true;

    @ConfigurationProperty(type = Type.TEXT)
    public String baseDirectory = "algebra/5d/";

    @ConfigurationProperty(type = Type.TEXT)
    public String userMacroFilePath = "";


    @Override
    public AlgebraStrategy createAlgebraStrategy() {
        return new AlStrategy(this);
    }

    @Override
    public String getName() {
        return "Algebra";
    }

    @Override
    public String getDescription() {
        return "This plugin sets the algebra for the graph and replaces all functions that are algebra-depend.";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    void notifyError(Throwable error) {
        setChanged();
        notifyObservers(new Notifications.Error(error));
    }

    public boolean isUseBuiltInFiles() {
        return useBuiltInFiles;
    }

    public boolean isUsePrecalulatedTables() {
        return usePrecalulatedTables;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public String getUserMacroFilePath() {
        return userMacroFilePath;
    }

    public void setUserMacroFilePath(String userMacroFilePath) {
        this.userMacroFilePath = userMacroFilePath;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public void setUseBuiltInFiles(boolean useBuiltInFiles) {
        this.useBuiltInFiles = useBuiltInFiles;
    }

    public void setUsePrecalulatedTables(boolean usePrecalulatedTables) {
        this.usePrecalulatedTables = usePrecalulatedTables;
    }

}
