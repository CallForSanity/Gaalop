package wordlengthoptimization;

import java.util.HashMap;
import java.util.Set;

/**
 * Options for the wordlength optimization.
 *
 * @author fs
 */
public class Options {

  private int monteCarloIterations;

  private HashMap<String, String> startVariableMinValues;
  private HashMap<String, String> startVariableMaxValues;
  private Set<String> outputVariables;

  public HashMap<String, String> getStartVariableMaxValues() {
    return startVariableMaxValues;
  }

  public Set<String> getOutputVariables() {
    return outputVariables;
  }

  public void setOutputVariables(Set<String> pragmaOutputVariables) {
   outputVariables = pragmaOutputVariables;
  }

  public void setStartVariableMaxValues(HashMap<String, String> startVariableMaxValues) {
    this.startVariableMaxValues = startVariableMaxValues;
  }

  public HashMap<String, String> getStartVariableMinValues() {
    return startVariableMinValues;
  }

  public void setStartVariableMinValues(HashMap<String, String> startVariableMinValues) {
    this.startVariableMinValues = startVariableMinValues;
  }

  public int getMonteCarloIterations() {
    return monteCarloIterations;
  }

  public void setMonteCarloIterations(int monteCarloIterations) {
    this.monteCarloIterations = monteCarloIterations;
  }

  

  private WordlengthOptimization possibleOptimizers[] = {new ForwardPropagation(),
          new AllFloat(),
          new AllDouble(),
          new Basic(32,16, true)};

  public WordlengthOptimization[] getPossibleOptimizers() {
    return possibleOptimizers;
  }

  public int getMaxWordlength() {
    return maxWordlength;
  }

  public void setMaxWordlength(int maxWordlength) {
    this.maxWordlength = maxWordlength;
  }

  public int getMinFractionlength() {
    return minFractionlength;
  }

  public void setMinFractionlength(int minFractionlength) {
    this.minFractionlength = minFractionlength;
  }

  public WordlengthOptimization getSelectedOptimizer() {
    return selectedOptimizer;
  }

  public void setSelectedOptimizer(WordlengthOptimization selectedOptimizer) {
    this.selectedOptimizer = selectedOptimizer;
  }

  private WordlengthOptimization selectedOptimizer;

  private int maxWordlength;

  private int minFractionlength;



}
