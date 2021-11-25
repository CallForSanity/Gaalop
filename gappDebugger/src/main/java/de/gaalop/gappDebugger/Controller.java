package de.gaalop.gappDebugger;

import de.gaalop.algebra.BladeArrayRoutines;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.gapp.executer.Executer;
import de.gaalop.gapp.executer.MultivectorWithValues;
import de.gaalop.gapp.instructionSet.*;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.visitor.InstructionType;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import de.gaalop.tba.Algebra;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * The controller for the ui
 *
 * @author Christian Steinmetz
 */
public class Controller {

    private UI ui;
    private HashMap<String, Double> inputValues = new HashMap<String, Double>();
    private DefaultListModel modelVars = new DefaultListModel();
    private DefaultListModel modelSrc = new DefaultListModel();
    private Algebra algebra;

    public Controller(UI ui1) {
        this.ui = ui1;
        ui.jListVariables.setModel(modelVars);
        ui.jListSrc.setModel(modelSrc);
        ListSelectionListener listener = new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshValueTable();
            }
        };

        ui.jListVariables.addListSelectionListener(listener);
    }

    /**
     * Refreshes the value table
     */
    private void refreshValueTable() {
        String name = (String) ui.jListVariables.getSelectedValue();

        if (name != null && !name.contains("=")) {
            String varName = name.split("=")[0].trim();

            DefaultTableModel model = (DefaultTableModel) ui.jTable1.getModel();
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }

            MultivectorWithValues v = executer.getValues().get(varName);
            if (v.isMultivector()) {
                int bladeCount = v.getEntries().length;
                for (int blade = 0; blade < bladeCount; blade++) {
                    Vector row = new Vector();
                    row.add(blade);
                    row.add(algebra.getBlade(blade).toString());

                    row.add(Double.toString(v.getEntry(blade)));
                    model.addRow(row);
                }
            } else {
                int entryCount = v.getEntries().length;
                for (int entry = 0; entry < entryCount; entry++) {
                    Vector row = new Vector();
                    row.add(entry);
                    row.add("");
                    row.add(Double.toString(v.getEntry(entry)));
                    model.addRow(row);
                }
            }

            model.fireTableDataChanged();
            ui.jTable1.repaint();
        }



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
        if (curInstruction < instructions.length) {
            evaluateInstruction();
            repaint();
        }

        if (curInstruction < instructions.length) {
            curInstruction++;
            repaint();
        }

        return (curInstruction < instructions.length);
    }

    public void run() {
        if (curInstruction < instructions.length) {
            while (nextInstruction()) {
            }
        }
    }

    public void setVariableValue() {
        String input = JOptionPane.showInputDialog("Please type in the assignment of a scalar variable!\nMany assignments can be set using ; as separator.");
        setVariableValue(input);
    }

    public void setVariableValue(String input) {
        if (input != null) {
            boolean changed = false;
            for (String part : input.split(";")) {
                if (part.contains("=")) {
                    String[] parts = part.split("=");
                    String varname = parts[0].trim();
                    Double value = Double.parseDouble(parts[1].trim());
                    inputValues.put(varname, value);
                    changed = true;
                }
            }
            if (changed) {
                restart();
            }
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

    public void setAlgebraBlades(String[] base) {
        for (int i = 0; i < base.length; i++) {
            base[i] = base[i].trim();
        }

        algebra = new Algebra(base, BladeArrayRoutines.createBlades(base));
    }
    private LinkedList<GAPPBaseInstruction> instructionsLoc;

    /**
     * Loads the source from a file
     *
     * @param file The file
     */
    public void loadSource(ControlFlowGraph graph) {

        instructionsLoc = new LinkedList<GAPPBaseInstruction>();

        EmptyControlFlowVisitor visitor = new EmptyControlFlowVisitor() {

            @Override
            public void visit(AssignmentNode node) {
                instructionsLoc.addAll(node.getGAPP().getInstructions());
                super.visit(node);
            }
        };
        graph.accept(visitor);
        
        this.instructions = instructionsLoc.toArray(new GAPPBaseInstruction[0]);
        modelSrc.clear();
        for (GAPPBaseInstruction instruction : instructionsLoc) {
            modelSrc.addElement(instruction.toString());
        }
        ui.jListSrc.repaint();
    }

    public void repaint() {
        modelVars.clear();

        String[] keyArr = inputValues.keySet().toArray(new String[0]);
        Arrays.sort(keyArr);

        for (String var : keyArr) {
            modelVars.addElement(var + " = " + inputValues.get(var).toString());
        }

        String[] keyArr2 = executer.getValues().keySet().toArray(new String[0]);
        Arrays.sort(keyArr2);

        for (String var : keyArr2) {
            modelVars.addElement(var);
        }


        ui.jListSrc.repaint();
        ui.jListVariables.repaint();
        if (curInstruction < instructions.length) {
            ui.jListSrc.setSelectedIndex(curInstruction);
        } else {
            ui.jListSrc.clearSelection();
        }
    }
}
