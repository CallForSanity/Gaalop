lexer grammar CDLexer; 
options {
    k=4;
    filter=IGNORE;
}
@header {
package de.gaalop.segmenter.antlr;
    
import de.gaalop.CodeGenerator;
import de.gaalop.CodeParser;
import de.gaalop.OptimizationStrategy;
import de.gaalop.CompilerDriver.*;   
}

@members {

    public CDLexer(CharStream input, CodeParser parser, OptimizationStrategy optimizationStrategy, CodeGenerator codeGenerator) {
        super(input, new RecognizerSharedState());
        this.parser = parser;
        this.optimizationStrategy = optimizationStrategy;
        this.codeGenerator = codeGenerator;

    }
private OptimizationStrategy optimizationStrategy;
private CodeGenerator codeGenerator;
private CodeParser parser;
 public  CDMachine machine = new CDMachine();
}

OPT : '//#optimize' {System.out.println("//optimized code \n");
                  machine.changeState(new CodeOptimize(parser, optimizationStrategy, codeGenerator));};
TRANS : '//#translate' {System.out.println("//translated code \n");
                  machine.changeState(new CodeTranslate(parser, codeGenerator));};   
HIDE: '//#hide' {System.out.println("//hide code \n");
                   machine.changeState(new CodeHide(parser, optimizationStrategy, codeGenerator)); };                  

DELETE: '//#delete' {System.out.println("//delete code \n");
                   machine.changeState(new CodeDelete()); };                  

                      
RAW : '//#end' {System.out.println("//back to raw code \n");
       machine.changeState(new CodeRaw());}; 
       
DRAW : '/*draw';

DRAW_END: '//#*/';

CORRECT_PRAGMA: '//#' { machine.addString("//#");    
    } ;

CORRECT: '//' { machine.addString("//");    
    } ;


protected
IGNORE
    :   //'<' (~('>' | 'p'| 'b'))* '>'
        
        //{System.out.println("bad tag:"+ getText()); }
    |   . { machine.addString(getText());    
    }
    ;