package de.gaalop.gappZwei.cfgimport;

import de.gaalop.dfg.Expression;

public class MvExpressions {

	public String nameMv;
	
	public Expression[] bladeExpressions;
	
	public MvExpressions(String nameMv, int bladeCount) {
		this.nameMv = nameMv;
		bladeExpressions = new Expression[bladeCount];
	}
	
}
