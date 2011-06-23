package de.gaalop.gappImporting;

import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVariable;
import de.gaalop.gapp.variables.GAPPVariableBase;
import de.gaalop.gapp.variables.GAPPVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.GAPPInstancer;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.visitor.InstructionType;
import java.util.regex.Pattern;

public class GAPPProgramReader implements VariableGetter {

        private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");
	private HashMap<String,GAPPVariableBase> variables;

	public GAPP readFile(File f) throws FileNotFoundException, IOException {
		GAPP program = new GAPP();
		
		variables = new HashMap<String,GAPPVariableBase>();
                BufferedReader r = new BufferedReader(new FileReader(f));

                while (r.ready()) {
                        String line = r.readLine().trim();


                        String[] parts = WHITESPACE_PATTERN.split(line, 2);

                        if (parts.length == 2) {
                            //create instruction from string
                            InstructionType type = InstructionType.valueOf(parts[0]);
                            GAPPBaseInstruction gappInstruction = GAPPInstancer.instanciate(type, parts[1], this);
                            program.addInstruction(gappInstruction);
                        } else {
                            System.err.println("Instruction isn't in a valid format: "+line);
                        }

                }

                r.close();
		return program;
	}

	@Override
	public GAPPVector parseVector(String string) {
		if (variables.containsKey(string))
			return (GAPPVector) variables.get(string);
		else {
			GAPPVector result = new GAPPVector(string);
			variables.put(string, result);
			return result;
		}
	}
	
	@Override
	public GAPPMultivector parseMultivector(String string) {
		if (variables.containsKey(string))
			return (GAPPMultivector) variables.get(string);
		else {
			GAPPMultivector result = new GAPPMultivector(string);
			variables.put(string, result);
			return result;
		}
	}

	@Override
	public GAPPVariable parseVariable(String string) {
		if (variables.containsKey(string))
			return (GAPPVariable) variables.get(string);
		else {
			GAPPVariable result = new GAPPVariable(0);
			variables.put(string, result);
			return result;
		}
	}
	
}
