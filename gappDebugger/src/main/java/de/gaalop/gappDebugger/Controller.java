package de.gaalop.gappDebugger;

import de.gaalop.gapp.executer.Executer;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.visitor.InstructionType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author christian
 */
public class Controller {

    private UI ui;
    private HashMap<String, Float> inputValues = new HashMap<String, Float>();

    private DefaultListModel modelVars = new DefaultListModel();
    private DefaultListModel modelSrc = new DefaultListModel();

    public Controller(UI ui1) {
        this.ui = ui1;
        ui.jListVariables.setModel(modelVars);
        ui.jListSrc.setModel(modelSrc);
        ListSelectionListener listener = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                String name = (String) ui.jListVariables.getSelectedValue();

                if (name != null && !name.contains("="))
                    ui.jTextFieldInfo.setText(executer.getValues().get(name).toString());
            }
        };

        ui.jListVariables.addListSelectionListener(listener);
    }

    private GAPPBaseInstruction[] instructions;
    private int curInstruction = 0;

    private Executer executer;

    public void restart() {
        executer = new Executer(inputValues);
        curInstruction = 0;
        repaint();
    }

    private void evaluateInstruction() {
        instructions[curInstruction].accept(executer, null);
    }

    public boolean nextInstruction() {
        if (curInstruction<instructions.length) {
            evaluateInstruction();
            repaint();
        }

        if (curInstruction<instructions.length) {
            curInstruction++;
            repaint();
        }

        return (curInstruction<instructions.length);
    }

    public void run() {
        if (curInstruction<instructions.length)
            while (nextInstruction()) {

            }
    }

    public void setVariableValue() {
        String input = JOptionPane.showInputDialog("Please type in the assignment of a scalar variable!\nMany assignments can be set using ; as separator.");
        setVariableValue(input);
    }

    public void setVariableValue(String input) {
        if (input != null) {
            boolean changed = false;
            for (String part: input.split(";"))
                if (part.contains("=")) {
                    String[] parts = part.split("=");
                    String varname = parts[0].trim();
                    Float value = Float.parseFloat(parts[1].trim());
                    inputValues.put(varname, value);
                    changed = true;
                }
            if (changed)
                restart();
        }
    }

    public GAPPBaseInstruction createBaseInstruction(String cmd) {
        InstructionType instr = InstructionType.valueOf(cmd);
        switch (instr) {
            case assignMv:
                return new GAPPAssignMv(null);
            case assignInputsVector:
                return new GAPPAssignInputsVector(null);
            case calculateMv:
                return new GAPPCalculateMv(null, null, null, null);
            case dotVectors:
                GAPPMultivector m = null;
                return new GAPPDotVectors(m, null, null);
            case resetMv:
                return new GAPPResetMv(null, 0);
            case setMv:
                return new GAPPSetMv(null, null, null, null);
            case setVector:
                return new GAPPSetVector(null, null);
        }
        return null;
    }

    public void loadSource(File file) {
        try {
                ArrayList<GAPPBaseInstruction> instructionsList = new ArrayList<GAPPBaseInstruction>();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        if (!line.startsWith("//")) {
                         //   Parser parser = new Parser();
                            //Parser parser = null;
                            String cmd = line.split(" ")[0];
                            GAPPBaseInstruction instruction = createBaseInstruction(cmd);
                            String trimmed = line.substring(line.indexOf(" ")).trim();
                            trimmed = trimmed.substring(0, trimmed.length()-1);
                            instruction.accept(null, trimmed);
                            instructionsList.add(instruction);
                        }
                    }
                }

                reader.close();

                instructions = instructionsList.toArray(new GAPPBaseInstruction[0]);
                modelSrc.clear();
                
                for (GAPPBaseInstruction instruction: instructions) {
                    modelSrc.addElement(instruction.toString());
                }
    

                ui.jListSrc.repaint();
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void repaint() {
        modelVars.clear();

        String[] keyArr = inputValues.keySet().toArray(new String[0]);
        Arrays.sort(keyArr);

        for (String var: keyArr)
            modelVars.addElement(var+" = "+inputValues.get(var).toString());

        String[] keyArr2 = executer.getValues().keySet().toArray(new String[0]);
        Arrays.sort(keyArr2);

        for (String var: keyArr2)
            modelVars.addElement(var);
        

        ui.jListSrc.repaint();
        ui.jListVariables.repaint();
        if (curInstruction<instructions.length)
            ui.jListSrc.setSelectedIndex(curInstruction);
        else
            ui.jListSrc.clearSelection();
    }

}
