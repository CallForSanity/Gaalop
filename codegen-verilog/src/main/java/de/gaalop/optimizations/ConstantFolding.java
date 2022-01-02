package de.gaalop.optimizations;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.FunctionArgument;
import de.gaalop.dfg.Inequality;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.LogicalAnd;
import de.gaalop.dfg.LogicalNegation;
import de.gaalop.dfg.LogicalOr;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;

/**
 *
 * @author fs
 */
public class ConstantFolding implements ExpressionVisitor, ControlFlowVisitor {

  /* global visitor return variable */
  private Expression resultExpr;

  @Override
  public void visit(Subtraction node) {
    node.getLeft().accept(this);
    Expression left = resultExpr;
    node.getRight().accept(this);
    Expression right = resultExpr;
    resultExpr = new Subtraction(left, right);
    if ((left instanceof FloatConstant) &&
            (right instanceof FloatConstant)) {
      FloatConstant leftc = (FloatConstant) left;
      FloatConstant rightc = (FloatConstant) right;
      resultExpr = new FloatConstant(leftc.getValue() - rightc.getValue());
    } else if (right instanceof Negation) {
      Negation neg = (Negation) right;
      resultExpr = new Addition(left, neg.getOperand());
    } else if (left instanceof FloatConstant) {
      FloatConstant leftc = (FloatConstant) left;
      if(leftc.getValue() == 0.0) {
          resultExpr = new Negation(right);
      }
    } else if(right instanceof FloatConstant) {
      FloatConstant rightc = (FloatConstant) right;
      if(rightc.getValue() == 0.0) {
          resultExpr = left;
      }
    }
  }

  @Override
  public void visit(Addition node) {
    node.getLeft().accept(this);
    Expression left = resultExpr;
    node.getRight().accept(this);
    Expression right = resultExpr;
    resultExpr = new Addition(left, right);
    if ((left instanceof FloatConstant) &&
            (right instanceof FloatConstant)) {
      FloatConstant leftc = (FloatConstant) left;
      FloatConstant rightc = (FloatConstant) right;
      resultExpr = new FloatConstant(leftc.getValue() + rightc.getValue());
    } else if(left instanceof FloatConstant) {
        FloatConstant leftc = (FloatConstant) left;
        if(leftc.getValue() == 0.0) {
            resultExpr = right;
        }
    } else if (right instanceof FloatConstant) {
        FloatConstant rightc = (FloatConstant) right;
        if(rightc.getValue() == 0.0) {
            resultExpr = left;
        }
    }
  }

  @Override
  public void visit(Division node) {
    node.getLeft().accept(this);
    Expression left = resultExpr;
    node.getRight().accept(this);
    Expression right = resultExpr;
    if(left instanceof FloatConstant && ((FloatConstant)left).getValue() == 1.0) {
        resultExpr = new Division(left, right);
    } else {
    resultExpr = new Multiplication(left, new  Division(new FloatConstant("1.0"), right));
    }
    if ((left instanceof FloatConstant) &&
            (right instanceof FloatConstant)) {
      FloatConstant leftc = (FloatConstant) left;
      FloatConstant rightc = (FloatConstant) right;
      resultExpr = new FloatConstant(leftc.getValue() / rightc.getValue());
    } else if ((node.getRight() instanceof FloatConstant)) {
       /* division by 1 gets canceld */
      FloatConstant floatConst = (FloatConstant) node.getRight();
      if (floatConst.getValue() == 1.0) {
        resultExpr = left;
      }
    }

  }

  @Override
  public void visit(InnerProduct node) {
    resultExpr = node;
  }

  @Override
  public void visit(Multiplication node) {
    node.getLeft().accept(this);
    Expression left = resultExpr;
    node.getRight().accept(this);
    Expression right = resultExpr;
    resultExpr = new Multiplication(left, right);
    if ((left instanceof FloatConstant) &&
            (right instanceof FloatConstant)) {
      FloatConstant leftc = (FloatConstant) left;
      FloatConstant rightc = (FloatConstant) right;
      resultExpr = new FloatConstant(leftc.getValue() * rightc.getValue());
    } else if ((right instanceof FloatConstant)) {
       /* mult by 1 gets canceld */
      FloatConstant floatConst = (FloatConstant) right;
      if (floatConst.getValue() == 1.0) {
        resultExpr = left;
      } else if (floatConst.getValue() == 0.5) {
        resultExpr = new Division(left, new FloatConstant(2.0f));
      } else if (floatConst.getValue() == -1.0) {
        resultExpr = new Negation(left);
      } else if (floatConst.getValue() == 0.0) {
        resultExpr = right;
      }

    } else if ((left instanceof FloatConstant)) {
        /* mult by 1 gets canceld */
      FloatConstant floatConst = (FloatConstant) left;
      if (floatConst.getValue() == 1.0) {
        resultExpr = right;
      }  else  if (floatConst.getValue() == 0.5) {
        resultExpr = new Division(right, new FloatConstant(2.0f));
      } else if (floatConst.getValue() == -1.0) {
        resultExpr = new Negation(right);
      } else if (floatConst.getValue() == 0.0) {
        resultExpr = left;
      }

    } else if ((left instanceof MathFunctionCall) &&
            (right instanceof MathFunctionCall)) {
        /* optimize sqrts and abs function calls by combining them */
      MathFunctionCall leftFunc = (MathFunctionCall) left;
      MathFunctionCall rightFunc = (MathFunctionCall) right;
      if ((leftFunc.getFunction() == MathFunction.ABS) &&
              (rightFunc.getFunction() == MathFunction.ABS)) {
        resultExpr = new MathFunctionCall(new Multiplication(rightFunc.getOperand(), leftFunc.getOperand()), MathFunction.ABS);
      }
      if ((leftFunc.getFunction() == MathFunction.SQRT) &&
              (rightFunc.getFunction() == MathFunction.SQRT)) {
        resultExpr = new MathFunctionCall(new Multiplication(rightFunc.getOperand(), leftFunc.getOperand()), MathFunction.SQRT);
      }
    }
  }


  @Override
  public void visit(MathFunctionCall node) {
    node.getOperand().accept(this);
    Expression previousExpr = resultExpr;
    resultExpr = new MathFunctionCall(previousExpr, node.getFunction());
    if ((node.getFunction() == MathFunction.SQRT) &&
            (previousExpr instanceof FloatConstant)) {
      FloatConstant operand = (FloatConstant) previousExpr;
      resultExpr = new FloatConstant(Math.sqrt(operand.getValue()));
    } else if ((node.getFunction() == MathFunction.ABS) &&
            (previousExpr instanceof MathFunctionCall)) {
      /* remove abs() around sqrts, as they are always positive */
      MathFunctionCall insideFunc = (MathFunctionCall) previousExpr;
      if (insideFunc.getFunction() == MathFunction.ABS ||
              insideFunc.getFunction() == MathFunction.SQRT)
        resultExpr = previousExpr;
    } else if ((node.getFunction() == MathFunction.SQRT) &&
            ((!(previousExpr instanceof MathFunctionCall)) ||
            (((MathFunctionCall) previousExpr).getFunction() != MathFunction.ABS))) {
      /* insert in every sqrt() an abs() */
      resultExpr = new MathFunctionCall(new MathFunctionCall(previousExpr, MathFunction.ABS), MathFunction.SQRT);
    }

  }

  @Override
  public void visit(Variable node) {
    resultExpr = node;
  }

  @Override
  public void visit(MultivectorComponent node) {
    resultExpr = node;
  }

  @Override
  public void visit(Exponentiation node) {
    node.getLeft().accept(this);
    Expression left = resultExpr;
    node.getRight().accept(this);
    Expression right = resultExpr;
    resultExpr = new Exponentiation(left, right);
    if(left instanceof FloatConstant && right instanceof FloatConstant) {
      // const ^ const => const
      FloatConstant leftc = (FloatConstant) left;
      FloatConstant rightc = (FloatConstant) right;
      resultExpr = new FloatConstant(new Float(Math.pow(leftc.getValue(), rightc.getValue())));
    } else if (left instanceof FloatConstant && ((FloatConstant)left).getValue() == 0.0) {
      // 0 ^ x => 0
      resultExpr = left;
    } else if (right instanceof FloatConstant) {
      // x ^ const
      FloatConstant rightc = (FloatConstant) right;
      boolean isSqrt = rightc.getValue() - (double) new Double(rightc.getValue()).intValue() == 0.5;
      if(isSqrt && rightc.getValue() != 0.5) {
          MathFunctionCall newsqrt = new MathFunctionCall(new Exponentiation(
                  left, new FloatConstant(rightc.getValue() - 0.5f)), MathFunction.SQRT);
          resultExpr = newsqrt;
      }
      if(rightc.getValue() == 1.0) {
        // x ^ 1 => x
        resultExpr = left;
      }
      else if (rightc.getValue() == 0.5) {
        MathFunctionCall newsqrt = new MathFunctionCall(left, MathFunction.SQRT);
        newsqrt.accept(this);
      } else if (rightc.getValue() == 2.0) {
          resultExpr = new Multiplication(left, left);
      }
    }
  }

  @Override
  public void visit(FloatConstant node) {
    resultExpr = node;
  }

  @Override
  public void visit(OuterProduct node) {
    resultExpr = node;
  }

  @Override
  public void visit(BaseVector node) {
    resultExpr = node;
  }

  @Override
  public void visit(Negation node) {
    node.getOperand().accept(this);
    Expression previousExpr = resultExpr;
    resultExpr = new Negation(resultExpr);
    if ((previousExpr instanceof FloatConstant)) {
      FloatConstant operand = (FloatConstant) previousExpr;
      if(operand.getValue() == 0.0) {
          resultExpr = new FloatConstant(0.0f);
      } else {
          resultExpr = new FloatConstant(-operand.getValue());
      }
    } else if (previousExpr instanceof Negation) {
      Negation operand = (Negation) previousExpr;
      resultExpr = operand.getOperand();
    }
  }

  @Override
  public void visit(Reverse node) {
    resultExpr = node;
  }

  @Override
  public void visit(LogicalOr node) {
    resultExpr = node;
  }

  @Override
  public void visit(LogicalAnd node) {
    resultExpr = node;
  }

  @Override
  public void visit(Equality node) {
    resultExpr = node;
  }

  @Override
  public void visit(Inequality node) {
    resultExpr = node;
  }

  @Override
  public void visit(Relation relation) {
    resultExpr = relation;
  }




  @Override
  public void visit(StartNode node) {
    node.getSuccessor().accept(this);
  }

  @Override
  public void visit(AssignmentNode node) {
    node.getSuccessor().accept(this);
    node.getValue().accept(this);
    node.setValue(resultExpr);
  }

  @Override
  public void visit(StoreResultNode node) {
    node.getSuccessor().accept(this);
  }

  @Override
  public void visit(IfThenElseNode node) {
    node.getSuccessor().accept(this);
  }

  @Override
  public void visit(BlockEndNode node) {
    node.getSuccessor().accept(this);

  }

  @Override
  public void visit(EndNode node) {
  }

    @Override
    public void visit(LogicalNegation node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LoopNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BreakNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Macro node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ExpressionStatement node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public void visit(ColorNode node) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
