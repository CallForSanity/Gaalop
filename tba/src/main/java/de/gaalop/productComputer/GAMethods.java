package de.gaalop.productComputer;

/**
 * Provides Geometric Algebra methods
 * @author Christian Steinmetz
 */
public class GAMethods {
    
    /**
     * Computes ‘reordering sign’ to get into canonical order.
     * Arguments 'a' and 'b' are both bitmaps representing basis blades.
     * Copy a!
     * This method is taken from the dissertation of Daniel Fontijne - Efficient Implementation of Gemoetric Algebra
     * @param a_p The first blade
     * @param b The second blade
     * @param bitCount The maximum number of bits
     * @return The sign which is yielded while reordering
     */
    public static float canonicalReorderingSign(Blade a_p, Blade b, int bitCount)
    {
        Blade a = new Blade(bitCount, a_p);
        // Count the number of basis vector swaps required to
        // get 'a' and 'b' into canonical order.
        a.shiftRight();
        int sum = 0;
        while (!a.isEmpty())
        {
                // the function bitCount() counts the number of
                // 1-bits in the argument
                Blade aAndB = new Blade(bitCount, a);
                aAndB.and(b);
                sum += aAndB.cardinality();
                a.shiftRight();
        }
        // even number of swaps -> return 1
        // odd number of swaps -> return -1
        return ((sum & 1) == 0) ? 1.0f : -1.0f;
    }
}
