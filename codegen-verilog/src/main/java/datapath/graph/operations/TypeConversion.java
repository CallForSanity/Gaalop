package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import datapath.graph.type.Type;

/**
 * Describes a Type Conversion, orginial type is the type of the predecessor
 * Output Type is the type of this.
 * @author fs
 */
public class TypeConversion extends UnaryOperation {


  public TypeConversion() {
    super();
  }

  public TypeConversion(Type targetType) {
    super();
    setType(targetType);
  }

  @Override
  public void visit(OperationVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String getDisplayLabel() {
    return "TYPECONVERSION";
  }

  @Override
  public int getDelay() {
      return 0;
  }

  public Type getInputType() {
    return this.getData().getType();
  }

  public Type  getOutputType() {
    return this.getType();
  }

  

}
