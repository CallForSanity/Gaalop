package de.gaalop.codegen_verilog.VerilogIR;

import datapath.graph.DeadTreeElimination;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import datapath.graph.Graph;
import datapath.graph.GreedySchedule;
import datapath.graph.ModlibWriter;
import datapath.graph.Schedule;
import datapath.graph.TestbenchCreator;
import datapath.graph.display.dot.DotDisplayFactory;
import datapath.graph.operations.*;

import datapath.graph.operations.UnaryOperation;
import datapath.graph.type.FixedPoint;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Negation;
import java.util.Map.Entry;
import wordlengthoptimization.*;
import wordlengthoptimization.WordlengthOptimization;
import datapath.graph.operations.constValue.*;

public class VerilogIRConverterVisitorCookies implements ExpressionVisitor,
		ControlFlowVisitor {

	private HashMap<AssignmentNode, Operation> assginmentToOperation = new HashMap<AssignmentNode, Operation>();
	private HashMap<String, AssignmentNode> stringToAssignmentNode = new HashMap<String, AssignmentNode>();
	private HashMap<Variable, TopLevelInput> variableToHWInput = new HashMap();
	// private HashSet<VerilogNode> allnodes = new HashSet<VerilogNode>();
	private Graph g = new Graph();
  private ControlFlowGraph formerGraph;
	String result;
	
	String lastcomponent;
	Operation toappend;
	VerilogDFG dfg;
	boolean isVariable = true;

	private static final int ADDITION = 1;
	private static final int SUBTRACTION = 2;
	private static final int MULTIPLICATION = 3;
	private static final int DIVISION = 4;
	private static final int EXPONENTIATION = 5;
	private static final int EXPONENTIATIONSquare = 6;
	private LoopInit l;

	public VerilogIRConverterVisitorCookies(VerilogDFG dfg) {
		super();
		this.dfg = dfg;
		// dfg.getCfg().accept(this);
		System.out.println("Starting VerilogIR Converter");

	}

	public String getResult() {
		return result;
	}

	@Override
	public void visit(Subtraction node) {
		System.out.println("MySubVisit");
		visitbinary(node, SUBTRACTION);

	}

        public void visit(Relation rel) {
	    System.err.println("Relation in VerilogIRConverterVisitorCookies not yet supported!");
	}

        public void visit(Inequality inequal) {
	    System.err.println("Inequality in VerilogIRConverterVisitorCookies not yet supported!");
	}

        public void visit(Equality equal) {
	    System.err.println("Equality in VerilogIRConverterVisitorCookies not yet supported!");
	}

        public void visit(LogicalAnd logand) {
	    System.err.println("LogicalAnd in VerilogIRConverterVisitorCookies not yet supported!");
	}

        public void visit(LogicalOr logand) {
	    System.err.println("LogicalOr in VerilogIRConverterVisitorCookies not yet supported!");
	}

        public void visit(BlockEndNode node) {
	    System.err.println("BlockEndNode in VerilogIRConverterVisitorCookies not yet supported!");
	}

        public void visit(IfThenElseNode node) {
	    System.err.println("IfThenElseNode in VerilogIRConverterVisitorCookies not yet supported!");
	}

	public void visitbinary(BinaryOperation node, int operation) {
		System.out.println("MyBinaryVisit");
		node.getLeft().accept(this);
		Operation lhs = toappend;
                node.getRight().accept(this);
		Operation rhs = toappend;

		switch (operation) {
		case ADDITION:
			toappend = new Add();
			break;
		case SUBTRACTION:
			System.out.println("subtraction");
                  toappend = new datapath.graph.operations.Subtraction();
			break;
		case MULTIPLICATION:
			toappend = new datapath.graph.operations.Multiplication();
			break;
		case DIVISION:
			toappend = new Divide();
      /* detect normalisation, is a hack atm, better would be search for abs() function */
      if (node.getRight() instanceof Variable) {
        Variable v = (Variable) node.getRight();
        if (v.getName().toLowerCase().contains("norm")) {
 //         ((Divide) toappend).setNormalization(true);
          System.out.println("Normalisation detected");
        }
      }
			break;
		default:
			System.err.println("not supported Binary Operation: " + operation);
                        assert false;
			break;
		}
		
		//cast Operation
		( (datapath.graph.operations.BinaryOperation) toappend).setLHS(lhs);
		((datapath.graph.operations.BinaryOperation) toappend).setRHS(rhs);
		addToGraph(toappend);

	}

	@Override
	public void visit(Addition node) {
		System.out.println("MyAddVisit");
		visitbinary(node, ADDITION);

	}

	@Override
	public void visit(Division node) {
            System.out.println("MyDivVisit");
            if (isPowerOf2(node.getRight())) {
            int toshift = Integer.numberOfTrailingZeros((int) ((FloatConstant) node.getRight()).getValue());
            node.getLeft().accept(this);
            Operation tobedivided = toappend;
            ConstantShift cs = new ConstantShift(toshift, ShiftMode.Right);
            cs.setData(tobedivided);
            toappend = cs;
            addToGraph(cs);
              }else{
	    visitbinary(node, DIVISION);

	}}

	@Override
	public void visit(InnerProduct node) {
		// TODO Auto-generated method stub

	}
	
	
	private boolean isSquare(Exponentiation exponentiation) {
	        final FloatConstant two = new FloatConstant(2.0f);
	        return two.equals(exponentiation.getRight());
	    }

        private boolean isSqrt(Exponentiation exp) {
            final FloatConstant one = new FloatConstant(1.0f);
            final FloatConstant two = new FloatConstant(2.0f);
            final FloatConstant half = new FloatConstant(0.5f);
            if(exp.getRight() instanceof FloatConstant) {
                return exp.getRight().equals(half);
            }
            if(exp.getRight() instanceof Division) {
                Division div = (Division)exp.getRight();
                if(div.getLeft().equals(one) && div.getRight().equals(two))
                    return true;
            }
            return false;
        }
	 
	@Override
	public void visit(Multiplication node) {
        System.out.println("MyMultVisit");
        if (isPowerOf2(node.getLeft())) {
            int toshift = Integer.numberOfTrailingZeros((int) ((FloatConstant) node.getLeft()).getValue());
            node.getRight().accept(this);
            Operation rhs = toappend;
            ConstantShift cs = new ConstantShift(toshift, ShiftMode.Left);
            cs.setData(rhs);
            toappend = cs;
            addToGraph(cs);
            //toappend = New ConstantShift();

        } else if (isPowerOf2(node.getRight())) {
            int toshift = Integer.numberOfTrailingZeros((int) ((FloatConstant) node.getRight()).getValue());
            node.getLeft().accept(this);
            Operation lhs = toappend;
            ConstantShift cs = new ConstantShift(toshift, ShiftMode.Left);
            cs.setData(lhs);
            toappend = cs;
            addToGraph(cs);

        } else {
           
            visitbinary(node, MULTIPLICATION);
        }

    }

        
//    private boolean isPowerOf2(Expression node) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    public boolean isPowerOf2(Expression node) {
        float f;
        int fi;
        if (node instanceof FloatConstant) {

            f = ((FloatConstant) node).getValue();
            fi = (int) ((FloatConstant) node).getValue();
            if ((fi >=2) &&(f == fi) && (Integer.bitCount(fi) == 1  )) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }









        private boolean mathfunctionHack;

	@Override
	public void visit(MathFunctionCall node) {
    System.out.println("MyMathFuncVisit");
    mathfunctionHack = true;
    node.getOperand().accept(this);
    mathfunctionHack = false;
    UnaryOperation newOp;
    switch (node.getFunction()) {
      case SQRT:
        newOp = new SquareRoot();
        break;
      case ABS:
        newOp = new Absolut();
        break;
      case ACOS:
        newOp = new ArcCos();
        break;
      case COS:
        newOp = new Cos();
        break;
      case SIN:
        newOp = new Sin();
        break;
      default:
        throw new UnsupportedOperationException("Math function " + node.toString() + " not supported.");
    }

    newOp.setData(toappend);
    toappend = newOp;
    addToGraph(toappend);
  }

	@Override
	public void visit(Variable exnode) {
		System.out.println("MyExpression/VariableNodeVisit Eingangswerte " + exnode.getName());
		
  //  HWInput hw = new HWInput(exnode);
   // top.setSource(hw);

                lastcomponent = exnode.getName();

                if(mathfunctionHack) {
                    lastcomponent = lastcomponent+"0";
                }
	
        if (variableToHWInput.containsKey(exnode)) {
            toappend = variableToHWInput.get(exnode);
        } else if(isVariable == false && (getAssignmentToOperationMap().get(
					getStringToAssignment().get(lastcomponent)) != null)) {
            toappend = getAssignmentToOperationMap().get(
					getStringToAssignment().get(lastcomponent));
        } else if(isVariable == true) {
            // lastcomponent is already set
        } else {
                    System.out.println(variableToHWInput);
                    System.out.println("");
                    System.out.println(getStringToAssignment().keySet());
                    assert false : "should not happen " + exnode + " " + lastcomponent;
//                HWInput hw = new HWInput(exnode);
//                TopLevelInput top = new TopLevelInput();
//                top.setSource(hw);
//                top.setName(exnode.getName());
//                if (exnode.getMinValue() != null) {
//                    double min = Double.parseDouble(exnode.getMinValue());
//                    double max = Double.parseDouble(exnode.getMaxValue());
//                    int prec = Math.max(wordlengthoptimization.Util.bitsRequiredForFraction(exnode.getMinValue()),
//                          wordlengthoptimization.Util.bitsRequiredForFraction(exnode.getMaxValue()));
//                    hw.setType(new FixedPoint(wordlengthoptimization.Util.bitsRequired(min, max) + prec, prec, min < 0));
//                    top.setType(hw.getType());
//                }
//                addToGraph(hw);
//                toappend = top;
//                addToGraph(toappend);
//                variableToHWInput.put(exnode, top);
        }

	}
		

	

	@Override
	public void visit(MultivectorComponent mc) {
		System.out.println("MyComponentVisit");
		StringBuilder temp = new StringBuilder();
		temp.append(mc.getName());
		temp.append(mc.getBladeIndex());

		// VerilogVariableNode newnode =new VerilogVariableNode(dfg);
		// newnode.setNode(mc);
		// newnode.setVarname(temp.toString());
		lastcomponent = temp.toString();
		System.out.println(lastcomponent);
		toappend = null;

		if (isVariable == false) {
			// getAssignmentToNodeMap().get(getStringToNode().get(lastcomponent)).appendDFGNode(newnode);
			toappend = getAssignmentToOperationMap().get(
					getStringToAssignment().get(lastcomponent));
		}

	}
	@Override
	public void visit(Exponentiation exnode) {
		System.out.println("MyExponentiationNodeVisit");
//		if (isSquare(exnode)) {
//		visitbinary(exnode, EXPONENTIATIONSquare);
//				}
                //else
                    if(isSqrt(exnode)) {
                    System.out.println("found stupid other sqrt");
                    UnaryOperation newOp;
                    newOp = new SquareRoot();
                    exnode.getLeft().accept(this);
                    assert toappend != null;
                    newOp.setData(toappend);
                    toappend = newOp;
                }
		else {
                    assert exnode.getRight() instanceof FloatConstant;
                    FloatConstant c = (FloatConstant)exnode.getRight();
                    assert (c.getValue() - (float)(new Float(c.getValue())).intValue() == 0f);
                    int exponent = (int)c.getValue();
                    assert exponent >= 2;

                    exnode.getLeft().accept(this);
                    assert toappend != null;
                    Operation op = toappend;

                    datapath.graph.operations.Multiplication lastM = new datapath.graph.operations.Multiplication();
                    lastM.setLHS(op);
                    lastM.setRHS(op);

                    exponent -= 2;

                    while(exponent > 0) {
                        datapath.graph.operations.Multiplication newM = new datapath.graph.operations.Multiplication();
                        newM.setRHS(lastM);
                        newM.setLHS(op);
                        lastM = newM;
                        addToGraph(newM);
                        exponent--;
                    }

                    toappend = lastM;
		}
                assert toappend != null;
                addToGraph(toappend);

	}

	@Override
	public void visit(FloatConstant fcnode) {
		System.out.println("MyFloatConstantVisit");
		// VerilogFloatNode newnode =new VerilogFloatNode(dfg);
		// FPValue fpv = new FPValue(fcnode.getValue(),32,16);
		// ConstantOperation co = new
		// ConstantOperation(Integer.toString((int)fpv.getBinaryvalue()));
		// VerilogFloatNode newnode =new VerilogFloatNode(dfg);
//		ConstantOperation co = new ConstantOperation(Float.toString(fcnode
//				.getValue()));
                FloatValue value = new FloatValue();
                value.setValue(new Float(fcnode.getValue()).floatValue());
                ConstantOperation co = new ConstantOperation(value, fcnode.toString());
		co.setOutputBitsize(32);
	

		// newnode.setValue(fcnode.getValue());

		toappend = co;
		addToGraph(toappend);
	}

	@Override
	public void visit(OuterProduct opnode) {
		throw new UnsupportedOperationException(
				"The Verilog backend does not support Outer Products");
	}

	@Override
	public void visit(BaseVector node) {
		throw new UnsupportedOperationException(
				"The Verilog backend does not support base vectors.");

	}

	@Override
	public void visit(Negation node) {
		System.out.println("MyNegationVisit");
		node.getOperand().accept(this);
		datapath.graph.operations.Negation n = new datapath.graph.operations.Negation();
		n.setData(toappend);
		toappend = n;
		addToGraph(toappend);

	}

	@Override
	public void visit(Reverse node) {
		throw new UnsupportedOperationException(
				"The Verilog backend does not support the reverse operation.");
	}

	@Override
	public void visit(StartNode node) {
		System.out.println("MyStartVisit");
    formerGraph = node.getGraph();

		l = new LoopInit();
		LoopEnd le = new LoopEnd();
		Predicate x = new Predicate(Predicate.TYPE.INIT);
		
		le.addPredicate(x);
		x.setData(l);
		
		g.addOperation(l);
		g.addOperation(x);
		g.addOperation(le);

                // Input Parameters
                for (Variable exnode : node.getGraph().getInputVariables()) {
                    HWInput hw = new HWInput(exnode);
                TopLevelInput top = new TopLevelInput();
                top.setSource(hw);
                top.setName(exnode.getName());
                if (exnode.getMinValue() != null) {
                    double min = Double.parseDouble(exnode.getMinValue());
                    double max = Double.parseDouble(exnode.getMaxValue());
                    int prec = Math.max(wordlengthoptimization.Util.bitsRequiredForFraction(exnode.getMinValue()),
                          wordlengthoptimization.Util.bitsRequiredForFraction(exnode.getMaxValue()));
                    hw.setType(new FixedPoint(wordlengthoptimization.Util.bitsRequired(min, max) + prec, prec, min < 0));
                    top.setType(hw.getType());
                }
                addToGraph(hw);
                addToGraph(top);
                    System.out.println("adding InputVariable " + exnode);
                variableToHWInput.put(exnode, top);
                }

                node.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		System.out.println("MyAssignmentVisit");
		isVariable = true;
		System.out.println("---------Getting Variable----------");
		node.getVariable().accept(this);
		getStringToAssignment().put(lastcomponent, node);
                String debug = lastcomponent;
		isVariable = false;
		System.out.println("---------Getting Value----------");
		node.getValue().accept(this);
		assert toappend != null : "toappend nicht null";
		getAssignmentToOperationMap().put(node, toappend);
                toappend.setDebugMessage(debug);

		// new VerilogNode(dfg);
		node.getSuccessor().accept(this);

	}

	@Override
	public void visit(StoreResultNode node) {
		System.out.println("MyStoreResultVisit");
	
		node.getSuccessor().accept(this);
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EndNode node) {
		System.out.println("Fertig mit Graph");

		// add ouptut Nodes

		for (Entry<AssignmentNode, Operation> iterable_element : assginmentToOperation
				.entrySet()) {
			HWOutput out = new HWOutput();
			out.setName(iterable_element.getKey().getVariable().toString()
					.replace("[", "").replace("]", ""));

			out.setData(iterable_element.getValue());
			Predicate ptemp = new Predicate(Predicate.TYPE.INIT);
			g.addOperation(ptemp);
			ptemp.setData(l);
			out.addPredicate(ptemp);
			g.addOperation(out);


		}
		

		Schedule s = new GreedySchedule();

    /* store helping information from the old graph in the options */
    Options opts = new Options();
    opts.setStartVariableMinValues(formerGraph.getPragmaMinValue());
    opts.setStartVariableMaxValues(formerGraph.getPragmaMaxValue());
    opts.setOutputVariables(formerGraph.getPragmaOutputVariables());

    DeadTreeElimination dte = new DeadTreeElimination(g, opts.getOutputVariables());
    dte.perform();

    WordLengthGUI dialog = new WordLengthGUI(null, true, opts);
    dialog.setVisible(true);
	  WordlengthOptimization w = opts.getSelectedOptimizer();
    w.setOptions(opts);
    System.out.println("Wordlength optimization (" + w + ") finished. Changed " + w.optimize(g) + " nodes");
    wordlengthoptimization.Util.fixHWInputs(g);
		s.scheduleAll(g);
  
		g.display(new DotDisplayFactory());

                for(ParentOutput out : g.getOutput()){
                    System.out.println("building graph for "+out.getName());
                    g.display(new DotDisplayFactory(), out);
                }

		try {
			StringWriter x = new StringWriter();

			ModlibWriter.write(g, new BufferedWriter(new FileWriter(
					"testoutput.v")));
			ModlibWriter.write(g, new BufferedWriter(x));
			result = x.toString();
			TestbenchCreator.writeTestbench(g, new BufferedWriter(
					new FileWriter("testbench.v")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// tellWithout();
	}

	public HashMap<AssignmentNode, Operation> getAssignmentToOperationMap() {
		return assginmentToOperation;	
	}

	public HashMap<String, AssignmentNode> getStringToAssignment() {
		return stringToAssignmentNode;
	}

	public void addToGraph(Operation n) {
		assert n != null;
		System.out.println("Adding Node to CookieGraph: "
				+ n.getClass().getSimpleName());
		g.addOperation(n);
	}


	// public void pumpoutGraph(){
	//		
	// for (VerilogNode iterable_element : allnodes) {
	// System.out.println("test pump: " + iterable_element.getVarname() );
	//	
	// }
	//			
	// }

	// public void tellWithout(){
	//		
	// for (VerilogNode iterable_element : allnodes) {
	// if (iterable_element.getPredecessors().length==0) {
	// System.out.println("test pump: " + iterable_element.getVarname() +
	// " hat keinen Vorgaenger" );
	//	
	// }
	// }
	//			
	// }

}
