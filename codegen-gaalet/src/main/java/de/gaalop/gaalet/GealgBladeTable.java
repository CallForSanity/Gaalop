package de.gaalop.gealg;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Variable;


/**
 * The blade distribution of gealg differs to the one in gaalop. 
 * @author thomaskanold
 *
 */
public class GealgBladeTable {

	private static int e1 = 1;
	private static int e2 = 2;
	private static int e3 = 4;
	private static int e4 = 8;
	private static int e5 = 16;
	
	private static Integer  bladeList []  =
	{
			0, e1, e2, e3, e4, e5,	// 
			e1+e2, e1+e3, e1+e4, e1+e5, e2+e3, 
			e2+e4, e2+e5, e3+e4, e3+e5, e4+e5,// 
			e1+e2+e3, e1+e2+e4, e1+e2+e5, e1+e3+e4, e1+e3+e5, 
			e1+e4+e5, e2+e3+e4, e2+e3+e5, e2+e4+e5, e3+e4+e5,//
			e1+e2+e3+e4, e1+e2+e3+e5, e1+e2+e4+e5, e1+e3+e4+e5, e2+e3+e4+e5,//
			e1+e2+e3+e4+e5
	};
	
	private static Expression one = new FloatConstant(1.0f);
	private static Expression _e1 = new BaseVector(1);
	private static Expression _e2 = new BaseVector(2);
	private static Expression _e3 = new BaseVector(3);
	private static Expression _e4 = new BaseVector(4);
	private static Expression _e5 = new BaseVector(5);
		
	private static Expression expList [] =
	{
		one, _e1, _e2, _e3, _e4, _e5, //
		new OuterProduct(_e1, _e2),
		new OuterProduct(_e1, _e3),
		new OuterProduct(_e1, _e4),
		new OuterProduct(_e1, _e5),		
		new OuterProduct(_e2, _e3),
		new OuterProduct(_e2, _e4),	
		new OuterProduct(_e2, _e5),
		new OuterProduct(_e3, _e4),
		new OuterProduct(_e3, _e5),
		new OuterProduct(_e4, _e5), //
		new OuterProduct(_e1, new OuterProduct(_e2, _e3)),
		new OuterProduct(_e1, new OuterProduct(_e2, _e4)),
		new OuterProduct(_e1, new OuterProduct(_e2, _e5)),
		new OuterProduct(_e1, new OuterProduct(_e3, _e4)),
		new OuterProduct(_e1, new OuterProduct(_e3, _e5)),
		new OuterProduct(_e1, new OuterProduct(_e4, _e5)),		
		new OuterProduct(_e2, new OuterProduct(_e3, _e4)),
		new OuterProduct(_e2, new OuterProduct(_e3, _e5)),	
		new OuterProduct(_e2, new OuterProduct(_e4, _e5)),	
		new OuterProduct(_e3, new OuterProduct(_e4, _e5)),//			
		new OuterProduct(_e1, new OuterProduct(_e2, new OuterProduct(_e3, _e4))),
		new OuterProduct(_e1, new OuterProduct(_e2, new OuterProduct(_e3, _e5))),		
		new OuterProduct(_e1, new OuterProduct(_e2, new OuterProduct(_e4, _e5))),	
		new OuterProduct(_e1, new OuterProduct(_e3, new OuterProduct(_e4, _e5))),	
		new OuterProduct(_e2, new OuterProduct(_e3, new OuterProduct(_e4, _e5))),//	
		new OuterProduct(_e1, new OuterProduct(_e2, new OuterProduct(_e3, new OuterProduct(_e4, _e5))))		
	};
		
		
	
	public static String bladeToHex(int blade) {
		String result = new String();
		if (bladeList[blade] < 17)
			result = "0";
		result += Integer.toHexString(gaalopToGaalet(blade)); 
		return result;
	};
	
/**
 * 
 * @param number can be an integer or an hex.
 * @return 
 */
	
	public static int numberToBlade(String number) {

		int result;
		if ((number.length()>1)&&(number.charAt(1) =='x')) {
			number = number.substring(2);
			result = Integer.valueOf(number,16);			
		} else
			result = new Integer(number);

		return result;
	};
	
	
	public static int gaalopToGaalet(int gaalop) {
		return bladeList[gaalop];
	}
	
	public GaaletIndex gaalopToGaalet(GaalopIndex gaalop) {
		return new GaaletIndex(gaalopToGaalet(gaalop.get()), this);
	}
	
	public GaalopIndex gaaletToGaalop(GaaletIndex gaalet) {
		return new GaalopIndex(gealgToGaalop(gaalet.get()), this);
	}
		
	
	// 
	public Expression getExpression(Integer gaaletIndex) {

		return expList[gealgToGaalop(gaaletIndex)];
	}

	private int gealgToGaalop(Integer gaaletIndex) {
		int result = 0;
		for (int n = 0; n <= 32; n++ ) 
			if (bladeList[n].equals(gaaletIndex)) {
				result = n;
				break;
			}		
		return result;
	}

}
