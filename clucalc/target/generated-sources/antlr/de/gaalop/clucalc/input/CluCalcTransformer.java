// $ANTLR 3.2 Sep 23, 2009 12:02:23 C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g 2010-05-06 11:38:44

	package de.gaalop.clucalc.input;

	import java.util.Collections;	
	import java.util.ArrayList;
	import de.gaalop.cfg.*;
	import de.gaalop.dfg.*;
	import static de.gaalop.dfg.ExpressionFactory.*;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class CluCalcTransformer extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DECIMAL_LITERAL", "EXPONENT", "FLOATTYPESUFFIX", "FLOATING_POINT_LITERAL", "RANGE_LITERAL", "OUTPUT_LITERAL", "MINUS", "OPNS", "IPNS", "IF", "ELSE", "LOOP", "BREAK", "LETTER", "DIGIT", "IDENTIFIER", "ARGUMENT_PREFIX", "WS", "COMMENT", "PRAGMA", "LINE_COMMENT", "EQUALS", "COMMA", "PLUS", "STAR", "SLASH", "MODULO", "LSBRACKET", "RSBRACKET", "LBRACKET", "RBRACKET", "CLBRACKET", "CRBRACKET", "REVERSE", "NOT", "DOUBLE_NOT", "SEMICOLON", "WEDGE", "DOT", "QUESTIONMARK", "COLON", "DOUBLE_BAR", "DOUBLE_AND", "DOUBLE_EQUALS", "UNEQUAL", "LESS", "GREATER", "LESS_OR_EQUAL", "GREATER_OR_EQUAL", "FUNCTION", "PROCEDURE", "NEGATION", "DUAL", "BLOCK", "ELSEIF", "MACRO", "ARGUMENT"
    };
    public static final int FUNCTION=53;
    public static final int EXPONENT=5;
    public static final int STAR=28;
    public static final int DOUBLE_BAR=45;
    public static final int LETTER=17;
    public static final int GREATER_OR_EQUAL=52;
    public static final int PRAGMA=23;
    public static final int DOUBLE_EQUALS=47;
    public static final int EQUALS=25;
    public static final int NOT=38;
    public static final int ARGUMENT_PREFIX=20;
    public static final int EOF=-1;
    public static final int BREAK=16;
    public static final int DOUBLE_NOT=39;
    public static final int IF=13;
    public static final int FLOATTYPESUFFIX=6;
    public static final int CRBRACKET=36;
    public static final int LBRACKET=33;
    public static final int GREATER=50;
    public static final int SLASH=29;
    public static final int FLOATING_POINT_LITERAL=7;
    public static final int COMMA=26;
    public static final int RSBRACKET=32;
    public static final int IDENTIFIER=19;
    public static final int LOOP=15;
    public static final int ARGUMENT=60;
    public static final int QUESTIONMARK=43;
    public static final int LESS=49;
    public static final int PLUS=27;
    public static final int DIGIT=18;
    public static final int IPNS=12;
    public static final int RBRACKET=34;
    public static final int COMMENT=22;
    public static final int DOT=42;
    public static final int CLBRACKET=35;
    public static final int DUAL=56;
    public static final int MODULO=30;
    public static final int LINE_COMMENT=24;
    public static final int LESS_OR_EQUAL=51;
    public static final int ELSE=14;
    public static final int SEMICOLON=40;
    public static final int MINUS=10;
    public static final int PROCEDURE=54;
    public static final int OPNS=11;
    public static final int UNEQUAL=48;
    public static final int ELSEIF=58;
    public static final int DOUBLE_AND=46;
    public static final int COLON=44;
    public static final int LSBRACKET=31;
    public static final int WEDGE=41;
    public static final int WS=21;
    public static final int NEGATION=55;
    public static final int RANGE_LITERAL=8;
    public static final int OUTPUT_LITERAL=9;
    public static final int BLOCK=57;
    public static final int REVERSE=37;
    public static final int MACRO=59;
    public static final int DECIMAL_LITERAL=4;

    // delegates
    // delegators


        public CluCalcTransformer(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public CluCalcTransformer(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return CluCalcTransformer.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g"; }


    	private GraphBuilder graphBuilder;

    	private List<String> errors = new ArrayList<String>();
    	public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
    		String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
    		errors.add(hdr + " " + msg);
    	}
    	public List<String> getErrors() {
    		return errors;
    	}
    	
    	protected int getNumberOfAssignments() {
    	  return graphBuilder.getNumberOfAssignments();
    	}



    // $ANTLR start "script"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:38:1: script returns [ControlFlowGraph result] : ( statement )* ;
    public final ControlFlowGraph script() throws RecognitionException {
        ControlFlowGraph result = null;


        		graphBuilder = new GraphBuilder();
        		result = graphBuilder.getGraph();
        	
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:43:3: ( ( statement )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:43:5: ( statement )*
            {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:43:5: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=OPNS && LA1_0<=IF)||(LA1_0>=LOOP && LA1_0<=BREAK)||LA1_0==PRAGMA||LA1_0==EQUALS||(LA1_0>=QUESTIONMARK && LA1_0<=COLON)||LA1_0==PROCEDURE||LA1_0==BLOCK||LA1_0==MACRO) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_script80);
            	    statement();

            	    state._fsp--;
            	    if (state.failed) return result;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "script"


    // $ANTLR start "statement"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:45:1: statement returns [ArrayList<SequentialNode> nodes] : ( ^( QUESTIONMARK assignment ) | ^( QUESTIONMARK value= expression ) | ^( PROCEDURE name= IDENTIFIER ) | IPNS | OPNS | ^( COLON assignment ) | assignment | block | if_statement | loop | BREAK | ^( MACRO id= IDENTIFIER lst= statement_list (e= expression )? ) | pragma );
    public final ArrayList<SequentialNode> statement() throws RecognitionException {
        ArrayList<SequentialNode> nodes = null;

        CommonTree name=null;
        CommonTree id=null;
        Expression value = null;

        ArrayList<SequentialNode> lst = null;

        Expression e = null;

        CluCalcTransformer.assignment_return assignment1 = null;

        CluCalcTransformer.assignment_return assignment2 = null;

        CluCalcTransformer.assignment_return assignment3 = null;

        ArrayList<SequentialNode> block4 = null;

        IfThenElseNode if_statement5 = null;

        LoopNode loop6 = null;


         nodes = new ArrayList<SequentialNode>(); 
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:48:2: ( ^( QUESTIONMARK assignment ) | ^( QUESTIONMARK value= expression ) | ^( PROCEDURE name= IDENTIFIER ) | IPNS | OPNS | ^( COLON assignment ) | assignment | block | if_statement | loop | BREAK | ^( MACRO id= IDENTIFIER lst= statement_list (e= expression )? ) | pragma )
            int alt3=13;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:48:4: ^( QUESTIONMARK assignment )
                    {
                    match(input,QUESTIONMARK,FOLLOW_QUESTIONMARK_in_statement103); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_assignment_in_statement105);
                    assignment1=assignment();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {

                      		nodes.add(graphBuilder.handleAssignment((assignment1!=null?assignment1.variable:null), (assignment1!=null?assignment1.value:null)));
                      		nodes.add(graphBuilder.handlePrint((assignment1!=null?assignment1.variable:null).copy()));
                      	
                    }

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:53:4: ^( QUESTIONMARK value= expression )
                    {
                    match(input,QUESTIONMARK,FOLLOW_QUESTIONMARK_in_statement117); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_expression_in_statement121);
                    value=expression();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(graphBuilder.handlePrint(value)); 
                    }

                    }
                    break;
                case 3 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:55:4: ^( PROCEDURE name= IDENTIFIER )
                    {
                    match(input,PROCEDURE,FOLLOW_PROCEDURE_in_statement132); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement136); if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       graphBuilder.handleProcedure((name!=null?name.getText():null)); 
                    }

                    }
                    break;
                case 4 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:57:4: IPNS
                    {
                    match(input,IPNS,FOLLOW_IPNS_in_statement147); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       graphBuilder.handleNullSpace(NullSpace.IPNS); 
                    }

                    }
                    break;
                case 5 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:59:4: OPNS
                    {
                    match(input,OPNS,FOLLOW_OPNS_in_statement156); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       graphBuilder.handleNullSpace(NullSpace.OPNS); 
                    }

                    }
                    break;
                case 6 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:62:4: ^( COLON assignment )
                    {
                    match(input,COLON,FOLLOW_COLON_in_statement168); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_assignment_in_statement170);
                    assignment2=assignment();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(graphBuilder.handleAssignment((assignment2!=null?assignment2.variable:null), (assignment2!=null?assignment2.value:null))); 
                    }

                    }
                    break;
                case 7 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:65:4: assignment
                    {
                    pushFollow(FOLLOW_assignment_in_statement182);
                    assignment3=assignment();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(graphBuilder.handleAssignment((assignment3!=null?assignment3.variable:null), (assignment3!=null?assignment3.value:null))); 
                    }

                    }
                    break;
                case 8 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:67:4: block
                    {
                    pushFollow(FOLLOW_block_in_statement191);
                    block4=block();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes = block4; 
                    }

                    }
                    break;
                case 9 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:69:4: if_statement
                    {
                    pushFollow(FOLLOW_if_statement_in_statement200);
                    if_statement5=if_statement();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(if_statement5);
                    }

                    }
                    break;
                case 10 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:71:4: loop
                    {
                    pushFollow(FOLLOW_loop_in_statement209);
                    loop6=loop();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(loop6); 
                    }

                    }
                    break;
                case 11 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:73:4: BREAK
                    {
                    match(input,BREAK,FOLLOW_BREAK_in_statement218); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(graphBuilder.handleBreak()); 
                    }

                    }
                    break;
                case 12 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:75:5: ^( MACRO id= IDENTIFIER lst= statement_list (e= expression )? )
                    {
                    match(input,MACRO,FOLLOW_MACRO_in_statement228); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    id=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement232); if (state.failed) return nodes;
                    pushFollow(FOLLOW_statement_list_in_statement236);
                    lst=statement_list();

                    state._fsp--;
                    if (state.failed) return nodes;
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:75:47: (e= expression )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==DECIMAL_LITERAL||LA2_0==FLOATING_POINT_LITERAL||LA2_0==MINUS||LA2_0==IDENTIFIER||(LA2_0>=PLUS && LA2_0<=SLASH)||LA2_0==REVERSE||(LA2_0>=WEDGE && LA2_0<=DOT)||(LA2_0>=DOUBLE_BAR && LA2_0<=FUNCTION)||(LA2_0>=NEGATION && LA2_0<=DUAL)||LA2_0==ARGUMENT) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:0:0: e= expression
                            {
                            pushFollow(FOLLOW_expression_in_statement240);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return nodes;

                            }
                            break;

                    }


                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {

                          graphBuilder.handleMacroDefinition((id!=null?id.getText():null), lst, e);
                        
                    }

                    }
                    break;
                case 13 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:82:5: pragma
                    {
                    pushFollow(FOLLOW_pragma_in_statement257);
                    pragma();

                    state._fsp--;
                    if (state.failed) return nodes;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return nodes;
    }
    // $ANTLR end "statement"


    // $ANTLR start "pragma"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:85:1: pragma : ( PRAGMA RANGE_LITERAL min= float_literal LESS_OR_EQUAL varname= IDENTIFIER LESS_OR_EQUAL max= float_literal | PRAGMA OUTPUT_LITERAL varname= IDENTIFIER );
    public final void pragma() throws RecognitionException {
        CommonTree varname=null;
        String min = null;

        String max = null;


        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:86:3: ( PRAGMA RANGE_LITERAL min= float_literal LESS_OR_EQUAL varname= IDENTIFIER LESS_OR_EQUAL max= float_literal | PRAGMA OUTPUT_LITERAL varname= IDENTIFIER )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==PRAGMA) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==RANGE_LITERAL) ) {
                    alt4=1;
                }
                else if ( (LA4_1==OUTPUT_LITERAL) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:86:6: PRAGMA RANGE_LITERAL min= float_literal LESS_OR_EQUAL varname= IDENTIFIER LESS_OR_EQUAL max= float_literal
                    {
                    match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma270); if (state.failed) return ;
                    match(input,RANGE_LITERAL,FOLLOW_RANGE_LITERAL_in_pragma272); if (state.failed) return ;
                    pushFollow(FOLLOW_float_literal_in_pragma276);
                    min=float_literal();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma278); if (state.failed) return ;
                    varname=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma282); if (state.failed) return ;
                    match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma284); if (state.failed) return ;
                    pushFollow(FOLLOW_float_literal_in_pragma288);
                    max=float_literal();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                        graphBuilder.addPragmaMinMaxValues((varname!=null?varname.getText():null), min, max);
                    }

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:88:6: PRAGMA OUTPUT_LITERAL varname= IDENTIFIER
                    {
                    match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma302); if (state.failed) return ;
                    match(input,OUTPUT_LITERAL,FOLLOW_OUTPUT_LITERAL_in_pragma304); if (state.failed) return ;
                    varname=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma308); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                        graphBuilder.addPragmaOutputVariable((varname!=null?varname.getText():null));  
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "pragma"

    public static class assignment_return extends TreeRuleReturnScope {
        public Variable variable;
        public Expression value;
    };

    // $ANTLR start "assignment"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:93:1: assignment returns [Variable variable, Expression value] : ^( EQUALS l= variable r= expression ) ;
    public final CluCalcTransformer.assignment_return assignment() throws RecognitionException {
        CluCalcTransformer.assignment_return retval = new CluCalcTransformer.assignment_return();
        retval.start = input.LT(1);

        Variable l = null;

        Expression r = null;


        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:94:2: ( ^( EQUALS l= variable r= expression ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:94:4: ^( EQUALS l= variable r= expression )
            {
            match(input,EQUALS,FOLLOW_EQUALS_in_assignment333); if (state.failed) return retval;

            match(input, Token.DOWN, null); if (state.failed) return retval;
            pushFollow(FOLLOW_variable_in_assignment337);
            l=variable();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_assignment341);
            r=expression();

            state._fsp--;
            if (state.failed) return retval;

            match(input, Token.UP, null); if (state.failed) return retval;
            if ( state.backtracking==0 ) {

              			retval.variable = l;
              			retval.value = r;
              	
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "assignment"


    // $ANTLR start "variable"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:101:1: variable returns [Variable result] : variableOrConstant ;
    public final Variable variable() throws RecognitionException {
        Variable result = null;

        Expression variableOrConstant7 = null;


        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:102:2: ( variableOrConstant )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:102:4: variableOrConstant
            {
            pushFollow(FOLLOW_variableOrConstant_in_variable360);
            variableOrConstant7=variableOrConstant();

            state._fsp--;
            if (state.failed) return result;
            if ( state.backtracking==0 ) {

              		if ( !(variableOrConstant7 instanceof Variable) ) {
              			throw new RecognitionException(input);
              		}
              		result = (Variable)variableOrConstant7;
              	
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "variable"


    // $ANTLR start "if_statement"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:110:1: if_statement returns [IfThenElseNode node] : ^( IF condition= expression then_part= statement (else_part= else_statement )? ) ;
    public final IfThenElseNode if_statement() throws RecognitionException {
        IfThenElseNode node = null;

        Expression condition = null;

        ArrayList<SequentialNode> then_part = null;

        ArrayList<SequentialNode> else_part = null;


        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:111:3: ( ^( IF condition= expression then_part= statement (else_part= else_statement )? ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:111:5: ^( IF condition= expression then_part= statement (else_part= else_statement )? )
            {
            match(input,IF,FOLLOW_IF_in_if_statement380); if (state.failed) return node;

            match(input, Token.DOWN, null); if (state.failed) return node;
            pushFollow(FOLLOW_expression_in_if_statement384);
            condition=expression();

            state._fsp--;
            if (state.failed) return node;
            pushFollow(FOLLOW_statement_in_if_statement388);
            then_part=statement();

            state._fsp--;
            if (state.failed) return node;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:111:60: (else_part= else_statement )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ELSE||LA5_0==ELSEIF) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:0:0: else_part= else_statement
                    {
                    pushFollow(FOLLOW_else_statement_in_if_statement392);
                    else_part=else_statement();

                    state._fsp--;
                    if (state.failed) return node;

                    }
                    break;

            }


            match(input, Token.UP, null); if (state.failed) return node;
            if ( state.backtracking==0 ) {

                  node = graphBuilder.handleIfStatement(condition, then_part, else_part);
                
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return node;
    }
    // $ANTLR end "if_statement"


    // $ANTLR start "else_statement"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:116:1: else_statement returns [ArrayList<SequentialNode> nodes] : ( ^( ELSE block ) | ^( ELSEIF if_statement ) );
    public final ArrayList<SequentialNode> else_statement() throws RecognitionException {
        ArrayList<SequentialNode> nodes = null;

        ArrayList<SequentialNode> block8 = null;

        IfThenElseNode if_statement9 = null;


         nodes = new ArrayList<SequentialNode>(); 
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:118:3: ( ^( ELSE block ) | ^( ELSEIF if_statement ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ELSE) ) {
                alt6=1;
            }
            else if ( (LA6_0==ELSEIF) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return nodes;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:118:5: ^( ELSE block )
                    {
                    match(input,ELSE,FOLLOW_ELSE_in_else_statement423); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_block_in_else_statement425);
                    block8=block();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes = block8; 
                    }

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:119:5: ^( ELSEIF if_statement )
                    {
                    match(input,ELSEIF,FOLLOW_ELSEIF_in_else_statement435); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_if_statement_in_else_statement437);
                    if_statement9=if_statement();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       
                          if_statement9.setElseIf(true);
                          nodes.add(if_statement9); 
                        
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return nodes;
    }
    // $ANTLR end "else_statement"


    // $ANTLR start "loop"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:125:1: loop returns [LoopNode node] : ^( LOOP stmt= statement ) ;
    public final LoopNode loop() throws RecognitionException {
        LoopNode node = null;

        ArrayList<SequentialNode> stmt = null;


        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:126:3: ( ^( LOOP stmt= statement ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:126:5: ^( LOOP stmt= statement )
            {
            match(input,LOOP,FOLLOW_LOOP_in_loop460); if (state.failed) return node;

            match(input, Token.DOWN, null); if (state.failed) return node;
            pushFollow(FOLLOW_statement_in_loop464);
            stmt=statement();

            state._fsp--;
            if (state.failed) return node;

            match(input, Token.UP, null); if (state.failed) return node;
            if ( state.backtracking==0 ) {
               node = graphBuilder.handleLoop(stmt); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return node;
    }
    // $ANTLR end "loop"


    // $ANTLR start "block"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:129:1: block returns [ArrayList<SequentialNode> nodes] : ^( BLOCK stmts= statement_list ) ;
    public final ArrayList<SequentialNode> block() throws RecognitionException {
        ArrayList<SequentialNode> nodes = null;

        ArrayList<SequentialNode> stmts = null;


         nodes = new ArrayList<SequentialNode>(); 
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:131:3: ( ^( BLOCK stmts= statement_list ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:131:5: ^( BLOCK stmts= statement_list )
            {
            match(input,BLOCK,FOLLOW_BLOCK_in_block494); if (state.failed) return nodes;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return nodes;
                pushFollow(FOLLOW_statement_list_in_block498);
                stmts=statement_list();

                state._fsp--;
                if (state.failed) return nodes;

                match(input, Token.UP, null); if (state.failed) return nodes;
            }
            if ( state.backtracking==0 ) {

                   nodes.addAll(stmts);
                
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return nodes;
    }
    // $ANTLR end "block"


    // $ANTLR start "statement_list"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:136:1: statement_list returns [ArrayList<SequentialNode> args] : (arg= statement )* ;
    public final ArrayList<SequentialNode> statement_list() throws RecognitionException {
        ArrayList<SequentialNode> args = null;

        ArrayList<SequentialNode> arg = null;


         args = new ArrayList<SequentialNode>(); 
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:138:3: ( (arg= statement )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:138:5: (arg= statement )*
            {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:138:5: (arg= statement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=OPNS && LA7_0<=IF)||(LA7_0>=LOOP && LA7_0<=BREAK)||LA7_0==PRAGMA||LA7_0==EQUALS||(LA7_0>=QUESTIONMARK && LA7_0<=COLON)||LA7_0==PROCEDURE||LA7_0==BLOCK||LA7_0==MACRO) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:138:6: arg= statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statement_list531);
            	    arg=statement();

            	    state._fsp--;
            	    if (state.failed) return args;
            	    if ( state.backtracking==0 ) {
            	       args.addAll(arg); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return args;
    }
    // $ANTLR end "statement_list"


    // $ANTLR start "expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:141:1: expression returns [Expression result] : ( ^( PLUS l= expression r= expression ) | ^( MINUS l= expression r= expression ) | ^( STAR l= expression r= expression ) | ^( SLASH l= expression r= expression ) | ^( DOUBLE_BAR l= expression r= expression ) | ^( DOUBLE_AND l= expression r= expression ) | ^( DOUBLE_EQUALS l= expression r= expression ) | ^( UNEQUAL l= expression r= expression ) | ^( LESS l= expression r= expression ) | ^( GREATER l= expression r= expression ) | ^( LESS_OR_EQUAL l= expression r= expression ) | ^( GREATER_OR_EQUAL l= expression r= expression ) | ^( WEDGE l= expression r= expression ) | ^( DOT l= expression r= expression ) | ^( NEGATION op= expression ) | ^( DUAL op= expression ) | ^( REVERSE op= expression ) | ^( FUNCTION name= IDENTIFIER arguments ) | value= DECIMAL_LITERAL | value= FLOATING_POINT_LITERAL | ^( ARGUMENT index= DECIMAL_LITERAL ) | variableOrConstant );
    public final Expression expression() throws RecognitionException {
        Expression result = null;

        CommonTree name=null;
        CommonTree value=null;
        CommonTree index=null;
        Expression l = null;

        Expression r = null;

        Expression op = null;

        ArrayList<Expression> arguments10 = null;

        Expression variableOrConstant11 = null;


        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:143:2: ( ^( PLUS l= expression r= expression ) | ^( MINUS l= expression r= expression ) | ^( STAR l= expression r= expression ) | ^( SLASH l= expression r= expression ) | ^( DOUBLE_BAR l= expression r= expression ) | ^( DOUBLE_AND l= expression r= expression ) | ^( DOUBLE_EQUALS l= expression r= expression ) | ^( UNEQUAL l= expression r= expression ) | ^( LESS l= expression r= expression ) | ^( GREATER l= expression r= expression ) | ^( LESS_OR_EQUAL l= expression r= expression ) | ^( GREATER_OR_EQUAL l= expression r= expression ) | ^( WEDGE l= expression r= expression ) | ^( DOT l= expression r= expression ) | ^( NEGATION op= expression ) | ^( DUAL op= expression ) | ^( REVERSE op= expression ) | ^( FUNCTION name= IDENTIFIER arguments ) | value= DECIMAL_LITERAL | value= FLOATING_POINT_LITERAL | ^( ARGUMENT index= DECIMAL_LITERAL ) | variableOrConstant )
            int alt8=22;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt8=1;
                }
                break;
            case MINUS:
                {
                alt8=2;
                }
                break;
            case STAR:
                {
                alt8=3;
                }
                break;
            case SLASH:
                {
                alt8=4;
                }
                break;
            case DOUBLE_BAR:
                {
                alt8=5;
                }
                break;
            case DOUBLE_AND:
                {
                alt8=6;
                }
                break;
            case DOUBLE_EQUALS:
                {
                alt8=7;
                }
                break;
            case UNEQUAL:
                {
                alt8=8;
                }
                break;
            case LESS:
                {
                alt8=9;
                }
                break;
            case GREATER:
                {
                alt8=10;
                }
                break;
            case LESS_OR_EQUAL:
                {
                alt8=11;
                }
                break;
            case GREATER_OR_EQUAL:
                {
                alt8=12;
                }
                break;
            case WEDGE:
                {
                alt8=13;
                }
                break;
            case DOT:
                {
                alt8=14;
                }
                break;
            case NEGATION:
                {
                alt8=15;
                }
                break;
            case DUAL:
                {
                alt8=16;
                }
                break;
            case REVERSE:
                {
                alt8=17;
                }
                break;
            case FUNCTION:
                {
                alt8=18;
                }
                break;
            case DECIMAL_LITERAL:
                {
                alt8=19;
                }
                break;
            case FLOATING_POINT_LITERAL:
                {
                alt8=20;
                }
                break;
            case ARGUMENT:
                {
                alt8=21;
                }
                break;
            case IDENTIFIER:
                {
                alt8=22;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:143:4: ^( PLUS l= expression r= expression )
                    {
                    match(input,PLUS,FOLLOW_PLUS_in_expression556); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression560);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression564);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Addition(l, r); 
                    }

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:145:4: ^( MINUS l= expression r= expression )
                    {
                    match(input,MINUS,FOLLOW_MINUS_in_expression575); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression579);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression583);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Subtraction(l, r); 
                    }

                    }
                    break;
                case 3 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:147:4: ^( STAR l= expression r= expression )
                    {
                    match(input,STAR,FOLLOW_STAR_in_expression594); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression598);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression602);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Multiplication(l, r); 
                    }

                    }
                    break;
                case 4 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:149:4: ^( SLASH l= expression r= expression )
                    {
                    match(input,SLASH,FOLLOW_SLASH_in_expression613); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression617);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression621);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Division(l, r); 
                    }

                    }
                    break;
                case 5 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:151:5: ^( DOUBLE_BAR l= expression r= expression )
                    {
                    match(input,DOUBLE_BAR,FOLLOW_DOUBLE_BAR_in_expression633); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression637);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression641);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new LogicalOr(l, r); 
                    }

                    }
                    break;
                case 6 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:153:5: ^( DOUBLE_AND l= expression r= expression )
                    {
                    match(input,DOUBLE_AND,FOLLOW_DOUBLE_AND_in_expression654); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression658);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression662);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new LogicalAnd(l, r); 
                    }

                    }
                    break;
                case 7 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:155:5: ^( DOUBLE_EQUALS l= expression r= expression )
                    {
                    match(input,DOUBLE_EQUALS,FOLLOW_DOUBLE_EQUALS_in_expression674); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression678);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression682);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Equality(l, r); 
                    }

                    }
                    break;
                case 8 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:157:5: ^( UNEQUAL l= expression r= expression )
                    {
                    match(input,UNEQUAL,FOLLOW_UNEQUAL_in_expression694); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression698);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression702);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Inequality(l, r); 
                    }

                    }
                    break;
                case 9 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:159:5: ^( LESS l= expression r= expression )
                    {
                    match(input,LESS,FOLLOW_LESS_in_expression714); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression718);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression722);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.LESS); 
                    }

                    }
                    break;
                case 10 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:161:5: ^( GREATER l= expression r= expression )
                    {
                    match(input,GREATER,FOLLOW_GREATER_in_expression734); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression738);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression742);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.GREATER); 
                    }

                    }
                    break;
                case 11 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:163:5: ^( LESS_OR_EQUAL l= expression r= expression )
                    {
                    match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_expression755); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression759);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression763);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.LESS_OR_EQUAL); 
                    }

                    }
                    break;
                case 12 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:165:5: ^( GREATER_OR_EQUAL l= expression r= expression )
                    {
                    match(input,GREATER_OR_EQUAL,FOLLOW_GREATER_OR_EQUAL_in_expression775); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression779);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression783);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.GREATER_OR_EQUAL); 
                    }

                    }
                    break;
                case 13 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:167:4: ^( WEDGE l= expression r= expression )
                    {
                    match(input,WEDGE,FOLLOW_WEDGE_in_expression794); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression798);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression802);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new OuterProduct(l, r); 
                    }

                    }
                    break;
                case 14 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:169:4: ^( DOT l= expression r= expression )
                    {
                    match(input,DOT,FOLLOW_DOT_in_expression813); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression817);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression821);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new InnerProduct(l, r); 
                    }

                    }
                    break;
                case 15 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:171:4: ^( NEGATION op= expression )
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_expression832); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression836);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Negation(op); 
                    }

                    }
                    break;
                case 16 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:173:4: ^( DUAL op= expression )
                    {
                    match(input,DUAL,FOLLOW_DUAL_in_expression847); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression851);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = graphBuilder.processFunction("*", Collections.singletonList(op)); 
                    }

                    }
                    break;
                case 17 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:175:4: ^( REVERSE op= expression )
                    {
                    match(input,REVERSE,FOLLOW_REVERSE_in_expression863); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression867);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Reverse(op); 
                    }

                    }
                    break;
                case 18 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:177:4: ^( FUNCTION name= IDENTIFIER arguments )
                    {
                    match(input,FUNCTION,FOLLOW_FUNCTION_in_expression878); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression882); if (state.failed) return result;
                    pushFollow(FOLLOW_arguments_in_expression884);
                    arguments10=arguments();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = graphBuilder.processFunction((name!=null?name.getText():null), arguments10); 
                    }

                    }
                    break;
                case 19 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:179:4: value= DECIMAL_LITERAL
                    {
                    value=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_expression896); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new FloatConstant((value!=null?value.getText():null)); 
                    }

                    }
                    break;
                case 20 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:181:4: value= FLOATING_POINT_LITERAL
                    {
                    value=(CommonTree)match(input,FLOATING_POINT_LITERAL,FOLLOW_FLOATING_POINT_LITERAL_in_expression907); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new FloatConstant((value!=null?value.getText():null)); 
                    }

                    }
                    break;
                case 21 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:183:4: ^( ARGUMENT index= DECIMAL_LITERAL )
                    {
                    match(input,ARGUMENT,FOLLOW_ARGUMENT_in_expression917); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    index=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_expression921); if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new FunctionArgument(Integer.parseInt((index!=null?index.getText():null))); 
                    }

                    }
                    break;
                case 22 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:185:4: variableOrConstant
                    {
                    pushFollow(FOLLOW_variableOrConstant_in_expression931);
                    variableOrConstant11=variableOrConstant();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = variableOrConstant11; 
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "expression"


    // $ANTLR start "variableOrConstant"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:188:1: variableOrConstant returns [Expression result] : name= IDENTIFIER ;
    public final Expression variableOrConstant() throws RecognitionException {
        Expression result = null;

        CommonTree name=null;

        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:189:2: (name= IDENTIFIER )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:189:4: name= IDENTIFIER
            {
            name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_variableOrConstant950); if (state.failed) return result;
            if ( state.backtracking==0 ) {
               result = graphBuilder.processIdentifier((name!=null?name.getText():null)); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "variableOrConstant"


    // $ANTLR start "arguments"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:192:1: arguments returns [ArrayList<Expression> args] : (arg= expression )* ;
    public final ArrayList<Expression> arguments() throws RecognitionException {
        ArrayList<Expression> args = null;

        Expression arg = null;


         args = new ArrayList<Expression>(); 
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:194:2: ( (arg= expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:194:4: (arg= expression )*
            {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:194:4: (arg= expression )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==DECIMAL_LITERAL||LA9_0==FLOATING_POINT_LITERAL||LA9_0==MINUS||LA9_0==IDENTIFIER||(LA9_0>=PLUS && LA9_0<=SLASH)||LA9_0==REVERSE||(LA9_0>=WEDGE && LA9_0<=DOT)||(LA9_0>=DOUBLE_BAR && LA9_0<=FUNCTION)||(LA9_0>=NEGATION && LA9_0<=DUAL)||LA9_0==ARGUMENT) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:194:5: arg= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_arguments977);
            	    arg=expression();

            	    state._fsp--;
            	    if (state.failed) return args;
            	    if ( state.backtracking==0 ) {
            	       args.add(arg); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return args;
    }
    // $ANTLR end "arguments"


    // $ANTLR start "float_literal"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:197:1: float_literal returns [String result] : (sign= MINUS )? val= FLOATING_POINT_LITERAL ;
    public final String float_literal() throws RecognitionException {
        String result = null;

        CommonTree sign=null;
        CommonTree val=null;

        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:198:3: ( (sign= MINUS )? val= FLOATING_POINT_LITERAL )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:198:5: (sign= MINUS )? val= FLOATING_POINT_LITERAL
            {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:198:9: (sign= MINUS )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==MINUS) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcTransformer.g:0:0: sign= MINUS
                    {
                    sign=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_float_literal999); if (state.failed) return result;

                    }
                    break;

            }

            val=(CommonTree)match(input,FLOATING_POINT_LITERAL,FOLLOW_FLOATING_POINT_LITERAL_in_float_literal1004); if (state.failed) return result;
            if ( state.backtracking==0 ) {
              result = new String((sign!=null?(sign!=null?sign.getText():null):"") + (val!=null?val.getText():null));
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "float_literal"

    // Delegated rules


    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA3_eotS =
        "\20\uffff";
    static final String DFA3_eofS =
        "\20\uffff";
    static final String DFA3_minS =
        "\1\13\1\2\13\uffff\1\4\2\uffff";
    static final String DFA3_maxS =
        "\1\73\1\2\13\uffff\1\74\2\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\uffff"+
        "\1\2\1\1";
    static final String DFA3_specialS =
        "\20\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\4\1\3\1\10\1\uffff\1\11\1\12\6\uffff\1\14\1\uffff\1\6\21"+
            "\uffff\1\1\1\5\11\uffff\1\2\2\uffff\1\7\1\uffff\1\13",
            "\1\15",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\16\2\uffff\1\16\2\uffff\1\16\10\uffff\1\16\5\uffff\1\17"+
            "\1\uffff\3\16\7\uffff\1\16\3\uffff\2\16\2\uffff\11\16\1\uffff"+
            "\2\16\3\uffff\1\16",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "45:1: statement returns [ArrayList<SequentialNode> nodes] : ( ^( QUESTIONMARK assignment ) | ^( QUESTIONMARK value= expression ) | ^( PROCEDURE name= IDENTIFIER ) | IPNS | OPNS | ^( COLON assignment ) | assignment | block | if_statement | loop | BREAK | ^( MACRO id= IDENTIFIER lst= statement_list (e= expression )? ) | pragma );";
        }
    }
 

    public static final BitSet FOLLOW_statement_in_script80 = new BitSet(new long[]{0x0A4018000281B802L});
    public static final BitSet FOLLOW_QUESTIONMARK_in_statement103 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_assignment_in_statement105 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_QUESTIONMARK_in_statement117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_statement121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROCEDURE_in_statement132 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_statement136 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IPNS_in_statement147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OPNS_in_statement156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_statement168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_assignment_in_statement170 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_assignment_in_statement182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_statement_in_statement200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_statement209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_statement218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MACRO_in_statement228 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_statement232 = new BitSet(new long[]{0x0A4018000281B800L});
    public static final BitSet FOLLOW_statement_list_in_statement236 = new BitSet(new long[]{0x11BFE62038080498L});
    public static final BitSet FOLLOW_expression_in_statement240 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_pragma_in_statement257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma270 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RANGE_LITERAL_in_pragma272 = new BitSet(new long[]{0x0000000000000480L});
    public static final BitSet FOLLOW_float_literal_in_pragma276 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma278 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma282 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma284 = new BitSet(new long[]{0x0000000000000480L});
    public static final BitSet FOLLOW_float_literal_in_pragma288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma302 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_OUTPUT_LITERAL_in_pragma304 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQUALS_in_assignment333 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_in_assignment337 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_assignment341 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_variableOrConstant_in_variable360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_statement380 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_if_statement384 = new BitSet(new long[]{0x0E4018000281F808L});
    public static final BitSet FOLLOW_statement_in_if_statement388 = new BitSet(new long[]{0x0400000000004008L});
    public static final BitSet FOLLOW_else_statement_in_if_statement392 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ELSE_in_else_statement423 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_block_in_else_statement425 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ELSEIF_in_else_statement435 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_if_statement_in_else_statement437 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOOP_in_loop460 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_loop464 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BLOCK_in_block494 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_list_in_block498 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_statement_in_statement_list531 = new BitSet(new long[]{0x0A4018000281B802L});
    public static final BitSet FOLLOW_PLUS_in_expression556 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression560 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression564 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_expression575 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression579 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression583 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_expression594 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression598 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression602 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SLASH_in_expression613 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression617 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression621 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOUBLE_BAR_in_expression633 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression637 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression641 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOUBLE_AND_in_expression654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression658 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression662 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOUBLE_EQUALS_in_expression674 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression678 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression682 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNEQUAL_in_expression694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression698 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression702 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LESS_in_expression714 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression718 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression722 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GREATER_in_expression734 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression738 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression742 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_expression755 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression759 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression763 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GREATER_OR_EQUAL_in_expression775 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression779 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression783 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEDGE_in_expression794 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression798 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression802 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOT_in_expression813 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression817 = new BitSet(new long[]{0x11BFE62038080490L});
    public static final BitSet FOLLOW_expression_in_expression821 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEGATION_in_expression832 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression836 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DUAL_in_expression847 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression851 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REVERSE_in_expression863 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression867 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_expression878 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression882 = new BitSet(new long[]{0x11BFE62038080498L});
    public static final BitSet FOLLOW_arguments_in_expression884 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_expression896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOATING_POINT_LITERAL_in_expression907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARGUMENT_in_expression917 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_expression921 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_variableOrConstant_in_expression931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_variableOrConstant950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_arguments977 = new BitSet(new long[]{0x11BFE62038080492L});
    public static final BitSet FOLLOW_MINUS_in_float_literal999 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOATING_POINT_LITERAL_in_float_literal1004 = new BitSet(new long[]{0x0000000000000002L});

}