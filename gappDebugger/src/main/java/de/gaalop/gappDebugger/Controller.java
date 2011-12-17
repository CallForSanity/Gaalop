package de.gaalop.gappDebugger;

import de.gaalop.algebra.BladeArrayRoutines;
import de.gaalop.algebra.TCBlade;
import de.gaalop.gapp.executer.Executer;
import de.gaalop.gapp.executer.MultivectorWithValues;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.visitor.InstructionType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.tba.Algebra;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * The controller for the ui
 * @author christian
 */
public class Controller {

    private UI ui;
    private HashMap<String, Float> inputValues = new HashMap<String, Float>();

    private DefaultListModel modelVars = new DefaultListModel();
    private DefaultListModel modelSrc = new DefaultListModel();

    private Algebra algebra;

    public Controller(UI ui1) {
        setAlgebraBlades("e1,e2,e3,einf,e0");
        this.ui = ui1;
        ui.jListVariables.setModel(modelVars);
        ui.jListSrc.setModel(modelSrc);
        ListSelectionListener listener = new ListSelectionListener() {

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
            while (model.getRowCount()>0)
                model.removeRow(0);

            MultivectorWithValues v = executer.getValues().get(varName);
            if (v.isMultivector()) {
                int bladeCount = v.getEntries().length;
                for (int blade=0;blade<bladeCount;blade++) {
                    Vector row = new Vector();
                    row.add(blade);
                    row.add(algebra.getBlade(blade).toString());

                    row.add(Float.toString(v.getEntry(blade)));
                    model.addRow(row);
                }
            } else {
                int entryCount = v.getEntries().length;
                for (int entry=0;entry<entryCount;entry++) {
                    Vector row = new Vector();
                    row.add(entry);
                    row.add("");
                    row.add(Float.toString(v.getEntry(entry)));
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

    public void setAlgebraBlades(String algebraBlades) {
        String[] base = algebraBlades.split(",");
        for (int i=0;i<base.length;i++)
            base[i] = base[i].trim();
        
        algebra = new Algebra(base, BladeArrayRoutines.createBlades(base));
    }

    /**
     * Loads the source from a file
     * @param file The file
     */
    public void loadSource(File file) {

        try {
            ANTLRReaderStream fileStream = new ANTLRReaderStream(new FileReader(file));
            GappLexer lexer = new GappLexer(fileStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            GappParser parser = new GappParser(tokenStream);
            GappParser.script_return parserResult = parser.script();
            if (!parser.getErrors().isEmpty()) {
                StringBuilder message = new StringBuilder();
                message.append("Unable to parse CluCalc file:\n");
                for (String error : parser.getErrors()) {
                    message.append(error);
                    message.append('\n');
                }
                System.err.println(message);
                return;
            }
            if (parserResult.getTree() == null) {
                System.out.println("The input file is empty.");
                return;
            }
            CommonTreeNodeStream treeNodeStream = new CommonTreeNodeStream(parserResult.getTree());
            GappTransformer transformer = new GappTransformer(treeNodeStream);
            GAPPBuilder graph = transformer.script();
            
            LinkedList<GAPPBaseInstruction> instructions = graph.getInstructions(algebra);
            if (!parser.getErrors().isEmpty()) {
                StringBuilder message = new StringBuilder();
                message.append("Unable to parse CluCalc file:\n");
                for (String error : parser.getErrors()) {
                    message.append(error);
                    message.append('\n');
                }
                return;
            }
            this.instructions = instructions.toArray(new GAPPBaseInstruction[0]);
            modelSrc.clear();
            for (GAPPBaseInstruction instruction : instructions) {
                modelSrc.addElement(instruction.toString());
            }
            ui.jListSrc.repaint();
        } catch (RecognitionException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
