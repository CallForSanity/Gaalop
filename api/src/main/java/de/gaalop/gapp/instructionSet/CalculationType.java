package de.gaalop.gapp.instructionSet;

/**
 * Defines the calculations that can be performed by GAPPCalculate
 * @author christian
 */
public enum CalculationType {
    ADDITION,SUBTRACTION,MULTIPLICATION,NEGATION, //TODO chs remove this row, when they are implemented
    DIVISION,EXPONENTIATION,
    ABS,ACOS,ASIN,ATAN,CEIL,COS,EXP,FACT,FLOOR,LOG,SIN,SQRT,TAN,
    LOG_OR, LOG_AND, LOG_INV, EQUAL, INEQUAL, REL_GR, REL_GREQ, REL_LE, REL_LEEQ                        // logical functions
}
