package de.gaalop.tba;

import java.util.Arrays;
import java.util.Vector;

/**
 * Represents a multivector, e.g. a vector of blades
 * @author Christian Steinmetz
 */
public class Multivector {
	
	private Algebra algebra;
	private Vector<BladeRef> blades;
	
	public Multivector(Algebra algebra) {
		blades = new Vector<BladeRef>();
		this.algebra = algebra;
	}

        /**
         * Adds a BladeRef object to this multivector
         * @param blade The bladeref object to be added
         */
	public void addBlade(BladeRef blade) {
		blades.add(blade);
	}

        /**
         * Returns the values of all blades in this multivector
         * @return The values of all blades
         */
	public byte[] getValueArr() {
		int size = algebra.getBlades().size();
		byte[] result = new byte[size];
		Arrays.fill(result,(byte) 0);
		
		for (BladeRef cur: blades)
			result[cur.getIndex()] += cur.getPrefactor();
		
		return result;
	}

        /**
         * Fills this multivector with values from an array
         * @param arr The array
         */
	public void fromValueArr(byte[] arr) {
		blades.clear();
		
		for (int i=0;i<arr.length;i++) {
			if (Math.abs(arr[i])>=10E-7) {
				BladeRef b = new BladeRef((byte) arr[i], i);
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
