/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.gaalop;

/**
 * Plugins that implement this can optimize their code when the Gaalop script is
 * saved.
 *
 * @author Dustin Grünwald
 */
public interface OptimizeOnSaveCodeGeneratorPlugin extends CodeGeneratorPlugin {

    default boolean getOptimizeOnSave() {
        return false;
    }

    default String getFileExtension() {
        return "";
    }

     default String getSaveFileDirectory() {
        return "";
    }
}