package de.gaalop.gapp.instructionSet;

/**
 * Collects the calculations that can be performed by a GAPP calculateMv operation
 * @author Christian Steinmetz
 */
public enum CalculationType {
    DIVISION, EXPONENTIATION,
    ABS, ACOS, ASIN, ATAN, CEIL, COS, EXP, FACT, FLOOR, LOG, SIN, SQRT, TAN,
    LOG_OR, LOG_AND, LOG_INV, EQUAL, INEQUAL, REL_GR, REL_GREQ, REL_LE, REL_LEEQ  // logical functions
}
