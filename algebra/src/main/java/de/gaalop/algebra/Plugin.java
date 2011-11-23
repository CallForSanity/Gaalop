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

    @ConfigurationProperty(type = Type.TEXT)
    public String productsFilePath = "algebra/5d/products.csv";

    @ConfigurationProperty(type = Type.TEXT)
    public String definitionFilePath = "algebra/5d/definition.csv";

    @ConfigurationProperty(type = Type.TEXT)
    public String macrosFilePath = "algebra/5d/macros.csv";

    @ConfigurationProperty(type = Type.BOOLEAN)
    public boolean useBuiltInFiles = true;

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

    public String getDefinitionFilePath() {
        return definitionFilePath;
    }

    public String getProductsFilePath() {
        return productsFilePath;
    }

    public String getMacrosFilePath() {
        return macrosFilePath;
    }

    public void setMacrosFilePath(String macrosFilePath) {
        this.macrosFilePath = macrosFilePath;
    }

    public void setDefinitionFilePath(String definitionFilePath) {
        this.definitionFilePath = definitionFilePath;
    }

    public void setProductsFilePath(String productsFilePath) {
        this.productsFilePath = productsFilePath;
    }

    public void setUseBuiltInFiles(boolean useBuiltInFiles) {
        this.useBuiltInFiles = useBuiltInFiles;
    }

    public void setUsePrecalulatedTables(boolean usePrecalulatedTables) {
        this.usePrecalulatedTables = usePrecalulatedTables;
    }

}
