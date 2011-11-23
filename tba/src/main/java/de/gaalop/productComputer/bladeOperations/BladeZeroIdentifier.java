package de.gaalop.productComputer.bladeOperations;

import java.util.HashSet;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.visitor.TCReplaceVisitor;

/**
 * Identifes blades that are zero (because they have two equal base vectors)
 * @author Christian Steinmetz
 */
public class BladeZeroIdentifier extends TCReplaceVisitor {

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        HashSet<String> keySet = new HashSet<String>();
        for (String b: tcBlade.getBase()) {
            if (keySet.contains(b)) {
                result = new TCConstant(0);
                return null;
            } else
                keySet.add(b);
        }
        return null;
    }

}
