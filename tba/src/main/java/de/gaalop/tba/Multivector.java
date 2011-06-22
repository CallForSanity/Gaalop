package de.gaalop.tba;

import java.util.Arrays;
import java.util.Vector;

public class Multivector {
	
	private Algebra algebra;
	private Vector<Blade> blades;
	
	public Multivector(Algebra algebra) {
		blades = new Vector<Blade>();
		this.algebra = algebra;
	}
	
	public void addBlade(Blade blade) {
		blades.add(blade);
	}

	public static Multivector parse(String toParse, String[] base, Algebra algebra) {
		  Multivector result = new Multivector(algebra);

		  String[] parts = toParse.split("\\+");
		  
		  for (String part: parts) 
		      result.setBlade(Blade.parseStr(part,algebra));
		  
		  
		  return result;
	}

	private void setBlade(Blade blade) {
		blades.add(blade);
	}
	
	public Multivector getSum(Multivector mvWith) {
		Multivector result = new Multivector(algebra);
		
		double[] arrmv = mvWith.getValueArr();
		
		double[] arrthis = getValueArr();
		
		for (int i=0;i<arrthis.length;i++)
			arrmv[i] += arrthis[i];
		
		result.fromValueArr(arrmv);
		return result;
	}
	
	public double[] getValueArr() {
		int size = algebra.getBlades().size();
		double[] result = new double[size];
		Arrays.fill(result, 0);
		
		for (Blade cur: blades)
			result[algebra.getIndex(cur)] += cur.getValue(); 
		
		return result;
	}
	
	public void fromValueArr(double[] arr) {
		blades.clear();
		
		for (int i=0;i<arr.length;i++) {
			if (Math.abs(arr[i])>=10E-7) {
				Blade b = (arr[i]<0) ? 
						new Blade(algebra,(byte) -1,-arr[i]) :
							new Blade(algebra,(byte) 1,arr[i]);
				for (String curBase : algebra.getBlade(i).getBases())
					b.addBasis(curBase);
				blades.add(b);
			}
		}

	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Multivector) {
			Multivector comp = (Multivector) obj;		
			
			return Arrays.equals(getValueArr(),comp.getValueArr());
		}
		return false;
	}

	@Override
	public String toString() {
		return blades.toString();
	}
}
