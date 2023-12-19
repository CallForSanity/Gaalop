/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.gaalop;

/**
 *
 * @author Dustin Grünwald
 */
public interface SaveNearSourceCodeGeneratorPlugin extends CodeGeneratorPlugin {
    default boolean getsaveFileImmediatly() {
        return false; // Default value for the property
    }
    
    default String getFileExtension() {
        return "";
    }
    
     default String getSaveFileDirectory() {
        return "";
    }
}