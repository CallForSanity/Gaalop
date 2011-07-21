package de.gaalop.codegenGapp;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import java.awt.Image;

/**
 * Implements a CodegeneratorPlugin, which lists all GAPP members in a graph in a pretty print format
 * @author christian
 */
public class GAPPCodeGeneratorPlugin implements CodeGeneratorPlugin {

    @Override
    public CodeGenerator createCodeGenerator() {
        return new GAPPCodeGenerator();
    }

    @Override
    public String getName() {
        return "GAPP CodeGenerator";
    }

    @Override
    public String getDescription() {
        return "This plugin generates GAPP Code";
    }

    @Override
    public Image getIcon() {
        return null;
    }

}
