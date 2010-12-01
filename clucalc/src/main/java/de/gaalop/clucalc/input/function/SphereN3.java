package de.gaalop.clucalc.input.function;

import de.gaalop.clucalc.algebra.AlgebraMode;
import de.gaalop.clucalc.algebra.AlgebraN3;
import de.gaalop.clucalc.input.Function;
import de.gaalop.dfg.Expression;
import static de.gaalop.dfg.ExpressionFactory.*;
import de.gaalop.dfg.FloatConstant;

/**
 * This class implements the SphereN3 function as defined in the CluCalc documentation.<br />
 * <br />
 * <b>Attention:</b> According to the CluCalc documentation, this function returns a sphere in OPNS representation. To
 * keep consistent with CluCalc, variables have to be dualized.
 */
public class SphereN3 implements Function {
	private static final Expression HALF = new FloatConstant(0.5f);

	/**
	 * Returns true if mode is AlgebraN3, false otherwise.
	 */
	@Override
	public boolean isDefinedIn(AlgebraMode mode) {
		return mode instanceof AlgebraN3;
	}

	@Override
	public Expression createExpression(AlgebraMode mode, Expression... args) {
		assert isDefinedIn(mode);

		if (args.length == 2) {
			return createSphere(mode, args[0], args[1]);
		} else if (args.length == 4) {
			VecN3 vecn3 = new VecN3();
			Expression center = vecn3.createExpression(mode, args[0], args[1], args[2]);
			return createSphere(mode, center, args[3]);
		} else {
			throw new IllegalArgumentException("The SphereN3 function needs two or four arguments.");
		}
	}

	private Expression createSphere(AlgebraMode mode, Expression center, Expression radius) {
		Expression ipnsSphere = subtract(center, product(HALF.copy(), square(radius), mode.getConstant("einf")));
		Expression opnsSphere = new Dual().createExpression(mode, ipnsSphere);
		return opnsSphere;
	}
}
