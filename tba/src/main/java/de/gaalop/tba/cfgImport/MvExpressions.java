package de.gaalop.tba.cfgImport;

import de.gaalop.dfg.Expression;

/**
 * Stores blade expressions
 * @author Christian Steinmetz
 */
public class MvExpressions {

	public String nameMv;
	
	public Expression[] bladeExpressions;
	
	public MvExpressions(String nameMv, int bladeCount) {
		this.nameMv = nameMv;
		bladeExpressions = new Expression[bladeCount];
	}
	
}
