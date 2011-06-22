package de.gaalop.gappZwei;

import de.gaalop.gappZwei.GAPPProgram;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.instructionSet.EInstruction;
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

import de.gaalop.cfg.AlgebraSignature;
import de.gaalop.clucalc.algebra.AlgebraN3;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.VariableGetter;

public class GAPPProgramReader implements VariableGetter {
	
	private HashMap<String,GAPPVariableBase> variables;

	//TODO Syntax richtig parsen
	public GAPP readFile(File f) {
		GAPP program = new GAPP();
		
		variables = new HashMap<String,GAPPVariableBase>();
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			
			while (r.ready()) {
				String line = r.readLine().trim();
				
				int indexSpace = line.indexOf(' '); //TODO tabs too
			
				String instruction = line.substring(0,indexSpace);
				String arguments = line.substring(indexSpace+1,line.length()-1); //remove front instruction aand semicolon
				
				//create instruction from string
				EInstruction inst = EInstruction.valueOf(instruction);
				
				if (inst  == null) 
					System.err.println("instruction don't exists: "+instruction);
				
				GAPPBaseInstruction gappInstruction = null;

				switch (inst) {
				case resetMv:
					gappInstruction = new GAPPResetMv(arguments,this);
					break;
				case assignMv:
					gappInstruction = new GAPPAssignMv(arguments,this);
					break;
				case setMv:
					gappInstruction = new GAPPSetMv(arguments,this);
					break;
				case addMv:
					gappInstruction = new GAPPAddMv(arguments,this);
					break;
				case setVector:
					gappInstruction = new GAPPSetVector(arguments,this);
					break;
				case dotVectors:
					gappInstruction = new GAPPDotVectors(arguments,this);
					break;
				}
				
				program.addInstruction(gappInstruction);
				
			}
			
			r.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

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
