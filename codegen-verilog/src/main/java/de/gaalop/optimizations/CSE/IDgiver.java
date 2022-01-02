package de.gaalop.optimizations.CSE;

/**
 *
 * @author pj
 */
public class IDgiver {
 private static IDgiver instance = null;
 private int counter=1;

    private IDgiver(){  }


 public static IDgiver getINSTANCE () {

if (instance == null) {

            instance = new IDgiver();

        }

    return instance;




 }

 public int getUnusedID(){


     return counter++;
 }

 public void reset(){
 counter = 0;


 }
}
