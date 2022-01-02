package de.gaalop.ganja;

import de.gaalop.cfg.AlgebraSignature;

/**
 *
 * @author CSteinmetz15
 */
public class AlgebraProperties {

    public AlgebraSignature signature;

    protected AlgebraProperties(AlgebraSignature signature) {
        this.signature = signature;
    }
    
    public boolean isConformal() {   
        return false;
    }
    
    public boolean isGL() {
        return true;
    }
    
    public int getRenderingDimensions() {
        return 0;
    }
    
    public String getAlgebraHeader() {
        return "Algebra(" + signature.toString() + ",()=>{\n";
    }
    
    public String getExtraBasisVectors() {
        return "";
    }
    
    public String getUp() {
        return "";
    }
    
    public static AlgebraProperties fromSignature(AlgebraSignature signature) {
        for (AlgebraProperties algebraProperties: new AlgebraProperties[] {
            new AlgebraCCGA(),
            new AlgebraCGA(),
            new AlgebraCRA(),
            new AlgebraDCGA(),
            new AlgebraGAC(),
            new AlgebraPGA2d(),
            new AlgebraPGA3d(),
            new AlgebraQGA(),
        }) 
            if (algebraProperties.signature.equals(signature)) 
                return algebraProperties;
        return null;
    }
    
    private abstract static class Algebra2d extends AlgebraProperties {

        public Algebra2d(AlgebraSignature signature) {
            super(signature);
        }
        
        @Override
        public int getRenderingDimensions() {
            return 2;
        }
        
    }
    
    private abstract static class Algebra3d extends AlgebraProperties {
        
        public Algebra3d(AlgebraSignature signature) {
            super(signature);
        }
        
        @Override
        public int getRenderingDimensions() {
            return 3;
        }
        
    }
    
    public static class AlgebraCGA extends Algebra3d {

        public AlgebraCGA() {
            super(new AlgebraSignature(4, 1, 0));
        }
        
        @Override
        public boolean isConformal() {
            return true;
        }
    }

    public static class AlgebraCRA extends Algebra2d {
        
        public AlgebraCRA() {
            super(new AlgebraSignature(3, 1, 0));
        }
        
        @Override
        public boolean isConformal() {
            return true;
        }
    }
    
    public static class AlgebraPGA3d extends Algebra3d {
        
        public AlgebraPGA3d() {
            super(new AlgebraSignature(3, 0, 1));
        }
        
    }
    
    public static class AlgebraPGA2d extends Algebra2d {
        
        public AlgebraPGA2d() {
            super(new AlgebraSignature(2, 0, 1));
        }

        @Override
        public boolean isGL() {
            return false;
        }
        
    }
    
    public static class AlgebraGAC extends Algebra2d {
        
        public AlgebraGAC() {
            super(new AlgebraSignature(5, 3, 0));
        }

        @Override
        public boolean isGL() {
            return false;
        }
        
        @Override
        public String getExtraBasisVectors() {
            return "var [einfp,einfm,einfc] = [1e6+1e3,1e7+1e4,1e8+1e5], [e0p,e0m,e0t] = 0.5*[1e6-1e3,1e7-1e4,1e8-1e5];\n";
        }

        @Override
        public String getUp() {
            return "var up = (x,y)=> e0p + x*1e1 + y*1e2 + 0.5*(x*x+y*y)*einfp + 0.5*(x*x-y*y)*einfm + x*y*einfc;\n";
        }

    }
    
    private static class AlgebraCCGA extends Algebra2d {
        
        public AlgebraCCGA() {
            super(new AlgebraSignature(9, 7, 0));
        }

        @Override
        public String getExtraBasisVectors() {
            return "var plus = [1e03,1e04,1e05,1e06,1e07,1e08,1e09],\n"
                 + "min = [1e10,1e11,1e12,1e13,1e14,1e15,1e16],\n"
                 + "[ei1,ei2,ei3,ei4,ei5,ei6,ei7]=2**-.5*(min+plus),\n"
                 + "[eo1,eo2,eo3,eo4,eo5,eo6,eo7]=2**-.5*(min-plus),\n"
                 + "EO = eo1+eo2;\n";
        }

        @Override
        public boolean isGL() {
            return false;
        }

        @Override
        public String getUp() {
            return "var up = (x,y)=> x*1e01 + y*1e02 + .5*(x*x*ei1 + y*y*ei2) + x*y*ei3 + x*x*x*ei4 + x*x*y*ei5 + x*y*y*ei6 + y*y*y*ei7 + EO;\n";
        }
    }
    
    private static class AlgebraDCGA extends Algebra3d {
        
        public AlgebraDCGA() {
            super(new AlgebraSignature(8, 2, 0));
        }

        @Override
        public String getAlgebraHeader() {
            return "Algebra({ metric:[1,1,1,1,-1,1,1,1,1,-1] },()=>{\n";
        }

        @Override
        public String getUp() {
            return "var up = (x,y,z)=> (x*1e01+y*1e02+z*1e03 + 0.5*(x*x+y*y+z*z)*(1e04+1e05)+(0.5*1e05-0.5*1e04))^(x*1e06+y*1e07+z*1e08+0.5*(x*x+y*y+z*z)*(1e09+1e10)+(0.5*1e10-0.5*1e09));\n";
        }
    }
    
    public static class AlgebraQGA extends Algebra3d {
        
        public AlgebraQGA() {
            super(new AlgebraSignature(6, 3, 0));
        }

        @Override
        public boolean isGL() {
            return false;
        }
   
        @Override
        public String getExtraBasisVectors() {
            return "var [einfx,einfy,einfz] = [1e4+1e7,1e5+1e8,1e6+1e9], [e0x,e0y,e0z] = 0.5*[1e7-1e4,1e8-1e5,1e9-1e6];\n";
        }

        @Override
        public String getUp() {
            return "var up = (x,y,z)=> x*1e1 + y*1e2 + z*1e3 + 0.5*(x*x*einfx + y*y*einfy + z*z*einfz) + e0x + e0y + e0z;\n";
        }

    }
    
}
