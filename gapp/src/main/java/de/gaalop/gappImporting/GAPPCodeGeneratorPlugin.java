/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import java.awt.Image;

/**
 *
 * @author christian
 */
class GAPPCodeGeneratorPlugin implements CodeGeneratorPlugin {

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
