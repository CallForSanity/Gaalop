package de.gaalop.api.dfg;

/**
 * Declares all types of expression
 * @author Christian Steinmetz
 */
public enum DFGNodeType {

    Subtraction, Addition, Division, InnerProduct, Multiplication,
    MathFunctionCall, Variable, MultivectorComponent,
    Exponentiation, FloatConstant, OuterProduct, BaseVector, 
    Negation, Reverse, LogicalOr, LogicalAnd, LogicalNegation,
    Equality, Inequality, Relation, FunctionArgument, MacroCall

}
