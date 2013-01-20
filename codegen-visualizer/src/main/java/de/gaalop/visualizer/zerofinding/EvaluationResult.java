package de.gaalop.visualizer.zerofinding;

/**
 * Stores the result of the evaluation, 
 * i.e. the scalar value of the function and the gradient at this point
 * 
 * @author Christian Steinmetz
 */
public class EvaluationResult {
    
    public double f;
    public VecN3 gradient;

}
