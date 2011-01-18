// $ANTLR 3.2 Sep 23, 2009 12:02:23 GaaletTransformer.g 2010-12-19 07:58:55

  package de.gaalop.gaalet.antlr;

	import java.util.Collections;	
	import java.util.ArrayList;
	import de.gaalop.cfg.*;
	import de.gaalop.dfg.*;
	import static de.gaalop.dfg.ExpressionFactory.*;
	import de.gaalop.gaalet.GraphBuilder;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class GaaletTransformer extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DECIMAL_LITERAL", "HEX_PREFIX", "DIGIT", "HEX", "RANGE_LITERAL", "OUTPUT_LITERAL", "UNROLL_LITERAL", "COUNT_LITERAL", "IGNORE_LITERAL", "LOOP", "BREAK", "GEALG_MV", "GEALG_TYPE", "DOUBLE", "FLOAT", "INTEGER", "UNSIGNED", "SIGNED", "AUTO", "EVAL", "INVERSE", "MINUS", "EXPONENT", "FLOATTYPESUFFIX", "OPNS", "IPNS", "IF", "ELSE", "IDENTIFIER_RECURSIVE", "IDENTIFIER", "LETTER", "DOT", "DOUBLE_COLON", "ARROW_RIGHT", "LSBRACKET", "RSBRACKET", "LBRACKET", "RBRACKET", "IDENTIFIER_TYPE_CAST", "FLOATING_POINT_LITERAL", "SPACE", "WS", "COMMA", "PLUS", "STAR", "SLASH", "MODULO", "CLBRACKET", "CRBRACKET", "REVERSE", "NOT", "DOUBLE_NOT", "SEMICOLON", "WEDGE", "QUESTIONMARK", "COLON", "DOUBLE_BAR", "DOUBLE_AND", "SINGLE_AND", "DOUBLE_EQUALS", "EQUALS", "UNEQUAL", "LESS", "GREATER", "LESS_OR_EQUAL", "GREATER_OR_EQUAL", "SET_OUTPUT", "COMMENT", "PRAGMA", "LINE_COMMENT", "FUNCTION", "PROCEDURE", "NEGATION", "LOGICAL_NEGATION", "DUAL", "BLOCK", "ELSEIF", "DEFINE_V", "DEFINE_ASSIGNMENT", "DEFINE_MV", "DEFINE_MV_AND_ASSIGN", "DEFINE_GEALG", "BLADE", "BLADE_ASSIGN", "MACRO", "ARGUMENT", "RETURN", "ARG_LIST_SEP", "ARGUMENT_PREFIX"
    };
    public static final int FUNCTION=74;
    public static final int ARROW_RIGHT=37;
    public static final int SIGNED=21;
    public static final int EXPONENT=26;
    public static final int STAR=48;
    public static final int LETTER=34;
    public static final int DOUBLE_EQUALS=63;
    public static final int HEX_PREFIX=5;
    public static final int EQUALS=64;
    public static final int NOT=54;
    public static final int EOF=-1;
    public static final int BREAK=14;
    public static final int DOUBLE_NOT=55;
    public static final int LBRACKET=40;
    public static final int GREATER=67;
    public static final int FLOATING_POINT_LITERAL=43;
    public static final int GEALG_MV=15;
    public static final int LOOP=13;
    public static final int RETURN=90;
    public static final int LESS=66;
    public static final int DOUBLE=17;
    public static final int COMMENT=71;
    public static final int SET_OUTPUT=70;
    public static final int DUAL=78;
    public static final int LINE_COMMENT=73;
    public static final int ELSE=31;
    public static final int SEMICOLON=56;
    public static final int LSBRACKET=38;
    public static final int DOUBLE_AND=61;
    public static final int INVERSE=24;
    public static final int WS=45;
    public static final int EVAL=23;
    public static final int DOUBLE_COLON=36;
    public static final int RANGE_LITERAL=8;
    public static final int DEFINE_V=81;
    public static final int GEALG_TYPE=16;
    public static final int OUTPUT_LITERAL=9;
    public static final int BLADE=86;
    public static final int REVERSE=53;
    public static final int DECIMAL_LITERAL=4;
    public static final int SINGLE_AND=62;
    public static final int DEFINE_ASSIGNMENT=82;
    public static final int DOUBLE_BAR=60;
    public static final int GREATER_OR_EQUAL=69;
    public static final int PRAGMA=72;
    public static final int FLOAT=18;
    public static final int ARGUMENT_PREFIX=92;
    public static final int IDENTIFIER_TYPE_CAST=42;
    public static final int SPACE=44;
    public static final int IF=30;
    public static final int FLOATTYPESUFFIX=27;
    public static final int CRBRACKET=52;
    public static final int SLASH=49;
    public static final int LOGICAL_NEGATION=77;
    public static final int IDENTIFIER_RECURSIVE=32;
    public static final int RSBRACKET=39;
    public static final int HEX=7;
    public static final int COMMA=46;
    public static final int IDENTIFIER=33;
    public static final int QUESTIONMARK=58;
    public static final int ARGUMENT=89;
    public static final int AUTO=22;
    public static final int DEFINE_MV=83;
    public static final int DEFINE_GEALG=85;
    public static final int PLUS=47;
    public static final int UNROLL_LITERAL=10;
    public static final int IPNS=29;
    public static final int DIGIT=6;
    public static final int RBRACKET=41;
    public static final int DOT=35;
    public static final int CLBRACKET=51;
    public static final int ARG_LIST_SEP=91;
    public static final int INTEGER=19;
    public static final int MODULO=50;
    public static final int DEFINE_MV_AND_ASSIGN=84;
    public static final int LESS_OR_EQUAL=68;
    public static final int MINUS=25;
    public static final int BLADE_ASSIGN=87;
    public static final int PROCEDURE=75;
    public static final int OPNS=28;
    public static final int UNEQUAL=65;
    public static final int ELSEIF=80;
    public static final int COLON=59;
    public static final int WEDGE=57;
    public static final int UNSIGNED=20;
    public static final int NEGATION=76;
    public static final int COUNT_LITERAL=11;
    public static final int BLOCK=79;
    public static final int MACRO=88;
    public static final int IGNORE_LITERAL=12;

    // delegates
    // delegators


        public GaaletTransformer(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public GaaletTransformer(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return GaaletTransformer.tokenNames; }
    public String getGrammarFileName() { return "GaaletTransformer.g"; }

    
      private GraphBuilder graphBuilder;
      private int inIfBlock = 0;
      private boolean inMacro = false;
      
      private static final class ParserError extends Error {
        public ParserError(String message) {
          super("Parser error: " + message);
        }
      }
    
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



    // $ANTLR start "script"
    // GaaletTransformer.g:43:3: script returns [ControlFlowGraph result] : ( statement )* ;
    public final ControlFlowGraph script() throws RecognitionException {
        ControlFlowGraph result = null;

        
            graphBuilder = new GraphBuilder();
            result = graphBuilder.getGraph();
          
        try {
            // GaaletTransformer.g:51:3: ( ( statement )* )
            // GaaletTransformer.g:51:5: ( statement )*
            {
            // GaaletTransformer.g:51:5: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==DECIMAL_LITERAL||(LA1_0>=LOOP && LA1_0<=BREAK)||(LA1_0>=INVERSE && LA1_0<=MINUS)||LA1_0==IF||LA1_0==IDENTIFIER||LA1_0==FLOATING_POINT_LITERAL||(LA1_0>=PLUS && LA1_0<=SLASH)||LA1_0==REVERSE||LA1_0==WEDGE||(LA1_0>=DOUBLE_BAR && LA1_0<=SET_OUTPUT)||LA1_0==PRAGMA||(LA1_0>=FUNCTION && LA1_0<=BLOCK)||(LA1_0>=DEFINE_V && LA1_0<=MACRO)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GaaletTransformer.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_script93);
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

            if ( state.backtracking==0 ) {
              
                  graphBuilder.finish();
                
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
    // GaaletTransformer.g:54:1: statement returns [ArrayList<SequentialNode> nodes] : ( ^( PROCEDURE name= IDENTIFIER ) | assignment | define_assignment | block | if_statement | loop | BREAK | macro | expression | pragma | ^( SET_OUTPUT variable ) | ^( BLADE_ASSIGN name= IDENTIFIER blade= DECIMAL_LITERAL expression ) | ^( DEFINE_V variable ) | ^( DEFINE_MV name= IDENTIFIER gaalet_arguments_list ) | ^( DEFINE_MV_AND_ASSIGN name= IDENTIFIER gaalet_arguments_list value= expression ) | ^( DEFINE_GEALG name= IDENTIFIER gaalet_arguments_list ARG_LIST_SEP arguments ) );
    public final ArrayList<SequentialNode> statement() throws RecognitionException {
        ArrayList<SequentialNode> nodes = null;

        CommonTree name=null;
        CommonTree blade=null;
        Expression value = null;

        GaaletTransformer.assignment_return assignment1 = null;

        GaaletTransformer.define_assignment_return define_assignment2 = null;

        ArrayList<SequentialNode> block3 = null;

        IfThenElseNode if_statement4 = null;

        LoopNode loop5 = null;

        Expression expression6 = null;

        Variable variable7 = null;

        Expression expression8 = null;

        Variable variable9 = null;

        ArrayList<String> gaalet_arguments_list10 = null;

        ArrayList<String> gaalet_arguments_list11 = null;

        ArrayList<String> gaalet_arguments_list12 = null;

        ArrayList<Expression> arguments13 = null;


         nodes = new ArrayList<SequentialNode>(); 
        try {
            // GaaletTransformer.g:56:2: ( ^( PROCEDURE name= IDENTIFIER ) | assignment | define_assignment | block | if_statement | loop | BREAK | macro | expression | pragma | ^( SET_OUTPUT variable ) | ^( BLADE_ASSIGN name= IDENTIFIER blade= DECIMAL_LITERAL expression ) | ^( DEFINE_V variable ) | ^( DEFINE_MV name= IDENTIFIER gaalet_arguments_list ) | ^( DEFINE_MV_AND_ASSIGN name= IDENTIFIER gaalet_arguments_list value= expression ) | ^( DEFINE_GEALG name= IDENTIFIER gaalet_arguments_list ARG_LIST_SEP arguments ) )
            int alt2=16;
            switch ( input.LA(1) ) {
            case PROCEDURE:
                {
                alt2=1;
                }
                break;
            case EQUALS:
                {
                alt2=2;
                }
                break;
            case DEFINE_ASSIGNMENT:
                {
                alt2=3;
                }
                break;
            case BLOCK:
                {
                alt2=4;
                }
                break;
            case IF:
                {
                alt2=5;
                }
                break;
            case LOOP:
                {
                alt2=6;
                }
                break;
            case BREAK:
                {
                alt2=7;
                }
                break;
            case MACRO:
                {
                alt2=8;
                }
                break;
            case DECIMAL_LITERAL:
            case INVERSE:
            case MINUS:
            case IDENTIFIER:
            case FLOATING_POINT_LITERAL:
            case PLUS:
            case STAR:
            case SLASH:
            case REVERSE:
            case WEDGE:
            case DOUBLE_BAR:
            case DOUBLE_AND:
            case SINGLE_AND:
            case DOUBLE_EQUALS:
            case UNEQUAL:
            case LESS:
            case GREATER:
            case LESS_OR_EQUAL:
            case GREATER_OR_EQUAL:
            case FUNCTION:
            case NEGATION:
            case LOGICAL_NEGATION:
            case DUAL:
            case BLADE:
                {
                alt2=9;
                }
                break;
            case PRAGMA:
                {
                alt2=10;
                }
                break;
            case SET_OUTPUT:
                {
                alt2=11;
                }
                break;
            case BLADE_ASSIGN:
                {
                alt2=12;
                }
                break;
            case DEFINE_V:
                {
                alt2=13;
                }
                break;
            case DEFINE_MV:
                {
                alt2=14;
                }
                break;
            case DEFINE_MV_AND_ASSIGN:
                {
                alt2=15;
                }
                break;
            case DEFINE_GEALG:
                {
                alt2=16;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return nodes;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // GaaletTransformer.g:56:4: ^( PROCEDURE name= IDENTIFIER )
                    {
                    match(input,PROCEDURE,FOLLOW_PROCEDURE_in_statement117); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement121); if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       graphBuilder.handleProcedure((name!=null?name.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // GaaletTransformer.g:59:4: assignment
                    {
                    pushFollow(FOLLOW_assignment_in_statement134);
                    assignment1=assignment();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(graphBuilder.handleAssignment((assignment1!=null?assignment1.variable:null), (assignment1!=null?assignment1.value:null))); 
                    }

                    }
                    break;
                case 3 :
                    // GaaletTransformer.g:61:4: define_assignment
                    {
                    pushFollow(FOLLOW_define_assignment_in_statement143);
                    define_assignment2=define_assignment();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       
                      	graphBuilder.wasDefined((define_assignment2!=null?define_assignment2.variable:null));
                      	nodes.add(graphBuilder.handleAssignment((define_assignment2!=null?define_assignment2.variable:null), (define_assignment2!=null?define_assignment2.value:null))); 
                      	
                    }

                    }
                    break;
                case 4 :
                    // GaaletTransformer.g:66:4: block
                    {
                    pushFollow(FOLLOW_block_in_statement151);
                    block3=block();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes = block3; 
                    }

                    }
                    break;
                case 5 :
                    // GaaletTransformer.g:68:4: if_statement
                    {
                    pushFollow(FOLLOW_if_statement_in_statement160);
                    if_statement4=if_statement();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(if_statement4);
                    }

                    }
                    break;
                case 6 :
                    // GaaletTransformer.g:70:5: loop
                    {
                    pushFollow(FOLLOW_loop_in_statement170);
                    loop5=loop();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes.add(loop5); 
                    }

                    }
                    break;
                case 7 :
                    // GaaletTransformer.g:72:5: BREAK
                    {
                    match(input,BREAK,FOLLOW_BREAK_in_statement181); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                      
                          if (inIfBlock > 0) { 
                            nodes.add(graphBuilder.handleBreak());
                          } else {
                            throw new ParserError("A break command may only occur whithin a conditional statement.");
                          } 
                        
                    }

                    }
                    break;
                case 8 :
                    // GaaletTransformer.g:80:5: macro
                    {
                    pushFollow(FOLLOW_macro_in_statement190);
                    macro();

                    state._fsp--;
                    if (state.failed) return nodes;

                    }
                    break;
                case 9 :
                    // GaaletTransformer.g:83:4: expression
                    {
                    pushFollow(FOLLOW_expression_in_statement201);
                    expression6=expression();

                    state._fsp--;
                    if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                      
                      	    Expression e = expression6;
                      	    if (e != null) { // null e.g. for procedure calls like DefVarsN3()
                      	      nodes.add(graphBuilder.processExpressionStatement(e)); 
                      	    }     
                      	  
                    }

                    }
                    break;
                case 10 :
                    // GaaletTransformer.g:90:5: pragma
                    {
                    pushFollow(FOLLOW_pragma_in_statement210);
                    pragma();

                    state._fsp--;
                    if (state.failed) return nodes;

                    }
                    break;
                case 11 :
                    // GaaletTransformer.g:94:5: ^( SET_OUTPUT variable )
                    {
                    match(input,SET_OUTPUT,FOLLOW_SET_OUTPUT_in_statement226); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_variable_in_statement228);
                    variable7=variable();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                      
                          nodes.add(graphBuilder.handlePrint(variable7.copy()));
                        
                    }

                    }
                    break;
                case 12 :
                    // GaaletTransformer.g:98:5: ^( BLADE_ASSIGN name= IDENTIFIER blade= DECIMAL_LITERAL expression )
                    {
                    match(input,BLADE_ASSIGN,FOLLOW_BLADE_ASSIGN_in_statement240); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement244); if (state.failed) return nodes;
                    blade=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_statement248); if (state.failed) return nodes;
                    pushFollow(FOLLOW_expression_in_statement250);
                    expression8=expression();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                      
                                 nodes.add(graphBuilder.bladeAssignment((name!=null?name.getText():null), (blade!=null?blade.getText():null), expression8));
                    }

                    }
                    break;
                case 13 :
                    // GaaletTransformer.g:101:5: ^( DEFINE_V variable )
                    {
                    match(input,DEFINE_V,FOLLOW_DEFINE_V_in_statement265); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_variable_in_statement267);
                    variable9=variable();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                         
                                 graphBuilder.wasDefined(variable9);
                                 
                    }

                    }
                    break;
                case 14 :
                    // GaaletTransformer.g:105:6: ^( DEFINE_MV name= IDENTIFIER gaalet_arguments_list )
                    {
                    match(input,DEFINE_MV,FOLLOW_DEFINE_MV_in_statement290); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement294); if (state.failed) return nodes;
                    pushFollow(FOLLOW_gaalet_arguments_list_in_statement297);
                    gaalet_arguments_list10=gaalet_arguments_list();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                      
                          System.out.println("DEFINE_MV " + (name!=null?name.getText():null));
                                  graphBuilder.defineMV((name!=null?name.getText():null), gaalet_arguments_list10);
                                  
                    }

                    }
                    break;
                case 15 :
                    // GaaletTransformer.g:109:6: ^( DEFINE_MV_AND_ASSIGN name= IDENTIFIER gaalet_arguments_list value= expression )
                    {
                    match(input,DEFINE_MV_AND_ASSIGN,FOLLOW_DEFINE_MV_AND_ASSIGN_in_statement309); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement313); if (state.failed) return nodes;
                    pushFollow(FOLLOW_gaalet_arguments_list_in_statement316);
                    gaalet_arguments_list11=gaalet_arguments_list();

                    state._fsp--;
                    if (state.failed) return nodes;
                    pushFollow(FOLLOW_expression_in_statement320);
                    value=expression();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                      
                        System.out.println("DEFINE_MV_AND_ASSIGN " + (name!=null?name.getText():null));
                                  graphBuilder.defineMV((name!=null?name.getText():null), gaalet_arguments_list11);
                                  nodes.add(graphBuilder.handleAssignment((name!=null?name.getText():null), value));        
                                  
                    }

                    }
                    break;
                case 16 :
                    // GaaletTransformer.g:115:5: ^( DEFINE_GEALG name= IDENTIFIER gaalet_arguments_list ARG_LIST_SEP arguments )
                    {
                    match(input,DEFINE_GEALG,FOLLOW_DEFINE_GEALG_in_statement355); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement359); if (state.failed) return nodes;
                    pushFollow(FOLLOW_gaalet_arguments_list_in_statement361);
                    gaalet_arguments_list12=gaalet_arguments_list();

                    state._fsp--;
                    if (state.failed) return nodes;
                    match(input,ARG_LIST_SEP,FOLLOW_ARG_LIST_SEP_in_statement363); if (state.failed) return nodes;
                    pushFollow(FOLLOW_arguments_in_statement365);
                    arguments13=arguments();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                         
                                           System.out.println("DEFINE_GEALG " + (name!=null?name.getText():null));
                                 nodes.add(graphBuilder.handleDefGaalet((name!=null?name.getText():null), gaalet_arguments_list12, arguments13));
                                 
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
    // $ANTLR end "statement"


    // $ANTLR start "gaalet_arguments_list"
    // GaaletTransformer.g:121:1: gaalet_arguments_list returns [ArrayList<String> args] : (arg= ( DECIMAL_LITERAL | HEX ) )* ;
    public final ArrayList<String> gaalet_arguments_list() throws RecognitionException {
        ArrayList<String> args = null;

        CommonTree arg=null;

         args = new ArrayList<String>(); 
        try {
            // GaaletTransformer.g:123:3: ( (arg= ( DECIMAL_LITERAL | HEX ) )* )
            // GaaletTransformer.g:123:5: (arg= ( DECIMAL_LITERAL | HEX ) )*
            {
            // GaaletTransformer.g:123:5: (arg= ( DECIMAL_LITERAL | HEX ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==DECIMAL_LITERAL) ) {
                    int LA3_2 = input.LA(2);

                    if ( (LA3_2==UP) ) {
                        int LA3_4 = input.LA(3);

                        if ( (synpred18_GaaletTransformer()) ) {
                            alt3=1;
                        }


                    }
                    else if ( (LA3_2==DECIMAL_LITERAL||LA3_2==HEX||(LA3_2>=INVERSE && LA3_2<=MINUS)||LA3_2==IDENTIFIER||LA3_2==FLOATING_POINT_LITERAL||(LA3_2>=PLUS && LA3_2<=SLASH)||LA3_2==REVERSE||LA3_2==WEDGE||(LA3_2>=DOUBLE_BAR && LA3_2<=DOUBLE_EQUALS)||(LA3_2>=UNEQUAL && LA3_2<=GREATER_OR_EQUAL)||LA3_2==FUNCTION||(LA3_2>=NEGATION && LA3_2<=DUAL)||LA3_2==BLADE||LA3_2==ARG_LIST_SEP) ) {
                        alt3=1;
                    }


                }
                else if ( (LA3_0==HEX) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // GaaletTransformer.g:123:6: arg= ( DECIMAL_LITERAL | HEX )
            	    {
            	    arg=(CommonTree)input.LT(1);
            	    if ( input.LA(1)==DECIMAL_LITERAL||input.LA(1)==HEX ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return args;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    if ( state.backtracking==0 ) {
            	       args.add((arg!=null?arg.getText():null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop3;
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
    // $ANTLR end "gaalet_arguments_list"


    // $ANTLR start "macro"
    // GaaletTransformer.g:126:1: macro : ^( MACRO id= IDENTIFIER lst= statement_list (e= return_value )? ) ;
    public final void macro() throws RecognitionException {
        CommonTree id=null;
        ArrayList<SequentialNode> lst = null;

        Expression e = null;


         
            if (inMacro) {
              throw new ParserError("A macro may only be defined in global scope.");
            }
            graphBuilder.beginNewScope(); 
            inMacro = true;
          
        try {
            // GaaletTransformer.g:138:3: ( ^( MACRO id= IDENTIFIER lst= statement_list (e= return_value )? ) )
            // GaaletTransformer.g:138:5: ^( MACRO id= IDENTIFIER lst= statement_list (e= return_value )? )
            {
            match(input,MACRO,FOLLOW_MACRO_in_macro441); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            id=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_macro445); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               graphBuilder.addMacroName((id!=null?id.getText():null)); 
            }
            pushFollow(FOLLOW_statement_list_in_macro451);
            lst=statement_list();

            state._fsp--;
            if (state.failed) return ;
            // GaaletTransformer.g:138:88: (e= return_value )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RETURN) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // GaaletTransformer.g:0:0: e= return_value
                    {
                    pushFollow(FOLLOW_return_value_in_macro455);
                    e=return_value();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==0 ) {
              
                  graphBuilder.handleMacroDefinition((id!=null?id.getText():null), lst, e);
                
            }

            }

            if ( state.backtracking==0 ) {
               
                  graphBuilder.endNewScope();
                  inMacro = false;
                
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
    // $ANTLR end "macro"


    // $ANTLR start "return_value"
    // GaaletTransformer.g:143:1: return_value returns [Expression result] : ^( RETURN exp= expression ) ;
    public final Expression return_value() throws RecognitionException {
        Expression result = null;

        Expression exp = null;


        try {
            // GaaletTransformer.g:144:3: ( ^( RETURN exp= expression ) )
            // GaaletTransformer.g:144:5: ^( RETURN exp= expression )
            {
            match(input,RETURN,FOLLOW_RETURN_in_return_value479); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            pushFollow(FOLLOW_expression_in_return_value483);
            exp=expression();

            state._fsp--;
            if (state.failed) return result;

            match(input, Token.UP, null); if (state.failed) return result;
            if ( state.backtracking==0 ) {
               result = exp; 
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
    // $ANTLR end "return_value"


    // $ANTLR start "pragma"
    // GaaletTransformer.g:147:1: pragma : ( PRAGMA RANGE_LITERAL min= float_literal LESS_OR_EQUAL varname= IDENTIFIER LESS_OR_EQUAL max= float_literal | PRAGMA OUTPUT_LITERAL varname= IDENTIFIER | PRAGMA IGNORE_LITERAL var= variable );
    public final void pragma() throws RecognitionException {
        CommonTree varname=null;
        String min = null;

        String max = null;

        Variable var = null;


        try {
            // GaaletTransformer.g:148:3: ( PRAGMA RANGE_LITERAL min= float_literal LESS_OR_EQUAL varname= IDENTIFIER LESS_OR_EQUAL max= float_literal | PRAGMA OUTPUT_LITERAL varname= IDENTIFIER | PRAGMA IGNORE_LITERAL var= variable )
            int alt5=3;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==PRAGMA) ) {
                switch ( input.LA(2) ) {
                case RANGE_LITERAL:
                    {
                    alt5=1;
                    }
                    break;
                case OUTPUT_LITERAL:
                    {
                    alt5=2;
                    }
                    break;
                case IGNORE_LITERAL:
                    {
                    alt5=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // GaaletTransformer.g:148:6: PRAGMA RANGE_LITERAL min= float_literal LESS_OR_EQUAL varname= IDENTIFIER LESS_OR_EQUAL max= float_literal
                    {
                    match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma500); if (state.failed) return ;
                    match(input,RANGE_LITERAL,FOLLOW_RANGE_LITERAL_in_pragma502); if (state.failed) return ;
                    pushFollow(FOLLOW_float_literal_in_pragma506);
                    min=float_literal();

                    state._fsp--;
                    if (state.failed) return ;
                    match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma508); if (state.failed) return ;
                    varname=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma512); if (state.failed) return ;
                    match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma514); if (state.failed) return ;
                    pushFollow(FOLLOW_float_literal_in_pragma518);
                    max=float_literal();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                        graphBuilder.addPragmaMinMaxValues((varname!=null?varname.getText():null), min, max);
                    }

                    }
                    break;
                case 2 :
                    // GaaletTransformer.g:150:6: PRAGMA OUTPUT_LITERAL varname= IDENTIFIER
                    {
                    match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma532); if (state.failed) return ;
                    match(input,OUTPUT_LITERAL,FOLLOW_OUTPUT_LITERAL_in_pragma534); if (state.failed) return ;
                    varname=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma538); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                        graphBuilder.addPragmaOutputVariable((varname!=null?varname.getText():null));  
                    }

                    }
                    break;
                case 3 :
                    // GaaletTransformer.g:152:6: PRAGMA IGNORE_LITERAL var= variable
                    {
                    match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma552); if (state.failed) return ;
                    match(input,IGNORE_LITERAL,FOLLOW_IGNORE_LITERAL_in_pragma554); if (state.failed) return ;
                    pushFollow(FOLLOW_variable_in_pragma558);
                    var=variable();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                        graphBuilder.addIgnoreVariable(var);  
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
    // GaaletTransformer.g:156:1: assignment returns [Variable variable, Expression value] : ^( EQUALS l= variable r= expression ) ;
    public final GaaletTransformer.assignment_return assignment() throws RecognitionException {
        GaaletTransformer.assignment_return retval = new GaaletTransformer.assignment_return();
        retval.start = input.LT(1);

        Variable l = null;

        Expression r = null;


        try {
            // GaaletTransformer.g:157:2: ( ^( EQUALS l= variable r= expression ) )
            // GaaletTransformer.g:157:4: ^( EQUALS l= variable r= expression )
            {
            match(input,EQUALS,FOLLOW_EQUALS_in_assignment583); if (state.failed) return retval;

            match(input, Token.DOWN, null); if (state.failed) return retval;
            pushFollow(FOLLOW_variable_in_assignment587);
            l=variable();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_assignment591);
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

    public static class define_assignment_return extends TreeRuleReturnScope {
        public Variable variable;
        public Expression value;
    };

    // $ANTLR start "define_assignment"
    // GaaletTransformer.g:162:1: define_assignment returns [Variable variable, Expression value] : ^( DEFINE_ASSIGNMENT l= variable r= expression ) ;
    public final GaaletTransformer.define_assignment_return define_assignment() throws RecognitionException {
        GaaletTransformer.define_assignment_return retval = new GaaletTransformer.define_assignment_return();
        retval.start = input.LT(1);

        Variable l = null;

        Expression r = null;


        try {
            // GaaletTransformer.g:163:3: ( ^( DEFINE_ASSIGNMENT l= variable r= expression ) )
            // GaaletTransformer.g:163:5: ^( DEFINE_ASSIGNMENT l= variable r= expression )
            {
            match(input,DEFINE_ASSIGNMENT,FOLLOW_DEFINE_ASSIGNMENT_in_define_assignment610); if (state.failed) return retval;

            match(input, Token.DOWN, null); if (state.failed) return retval;
            pushFollow(FOLLOW_variable_in_define_assignment614);
            l=variable();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_define_assignment618);
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
    // $ANTLR end "define_assignment"


    // $ANTLR start "variable"
    // GaaletTransformer.g:169:1: variable returns [Variable result] : variableOrConstant ;
    public final Variable variable() throws RecognitionException {
        Variable result = null;

        Expression variableOrConstant14 = null;


        try {
            // GaaletTransformer.g:170:2: ( variableOrConstant )
            // GaaletTransformer.g:170:4: variableOrConstant
            {
            pushFollow(FOLLOW_variableOrConstant_in_variable637);
            variableOrConstant14=variableOrConstant();

            state._fsp--;
            if (state.failed) return result;
            if ( state.backtracking==0 ) {
              
              		if ( !(variableOrConstant14 instanceof Variable) ) {
              			throw new RecognitionException(input);
              		}
              		result = (Variable)variableOrConstant14;
              	
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
    // GaaletTransformer.g:179:1: if_statement returns [IfThenElseNode node] : ^( IF condition= expression then_part= statement (else_part= else_statement )? ) ;
    public final IfThenElseNode if_statement() throws RecognitionException {
        IfThenElseNode node = null;

        Expression condition = null;

        ArrayList<SequentialNode> then_part = null;

        ArrayList<SequentialNode> else_part = null;


         inIfBlock++; 
        try {
            // GaaletTransformer.g:182:3: ( ^( IF condition= expression then_part= statement (else_part= else_statement )? ) )
            // GaaletTransformer.g:182:5: ^( IF condition= expression then_part= statement (else_part= else_statement )? )
            {
            match(input,IF,FOLLOW_IF_in_if_statement672); if (state.failed) return node;

            match(input, Token.DOWN, null); if (state.failed) return node;
            pushFollow(FOLLOW_expression_in_if_statement676);
            condition=expression();

            state._fsp--;
            if (state.failed) return node;
            pushFollow(FOLLOW_statement_in_if_statement680);
            then_part=statement();

            state._fsp--;
            if (state.failed) return node;
            // GaaletTransformer.g:182:60: (else_part= else_statement )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ELSE||LA6_0==ELSEIF) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // GaaletTransformer.g:0:0: else_part= else_statement
                    {
                    pushFollow(FOLLOW_else_statement_in_if_statement684);
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

            if ( state.backtracking==0 ) {
               inIfBlock--; 
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
    // GaaletTransformer.g:187:1: else_statement returns [ArrayList<SequentialNode> nodes] : ( ^( ELSE block ) | ^( ELSEIF if_statement ) );
    public final ArrayList<SequentialNode> else_statement() throws RecognitionException {
        ArrayList<SequentialNode> nodes = null;

        ArrayList<SequentialNode> block15 = null;

        IfThenElseNode if_statement16 = null;


         
            graphBuilder.beginNewScope();
            nodes = new ArrayList<SequentialNode>(); 
          
        try {
            // GaaletTransformer.g:193:3: ( ^( ELSE block ) | ^( ELSEIF if_statement ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ELSE) ) {
                alt7=1;
            }
            else if ( (LA7_0==ELSEIF) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return nodes;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // GaaletTransformer.g:193:5: ^( ELSE block )
                    {
                    match(input,ELSE,FOLLOW_ELSE_in_else_statement722); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_block_in_else_statement724);
                    block15=block();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       nodes = block15; 
                    }

                    }
                    break;
                case 2 :
                    // GaaletTransformer.g:194:5: ^( ELSEIF if_statement )
                    {
                    match(input,ELSEIF,FOLLOW_ELSEIF_in_else_statement734); if (state.failed) return nodes;

                    match(input, Token.DOWN, null); if (state.failed) return nodes;
                    pushFollow(FOLLOW_if_statement_in_else_statement736);
                    if_statement16=if_statement();

                    state._fsp--;
                    if (state.failed) return nodes;

                    match(input, Token.UP, null); if (state.failed) return nodes;
                    if ( state.backtracking==0 ) {
                       
                          if_statement16.setElseIf(true);
                          nodes.add(if_statement16); 
                        
                    }

                    }
                    break;

            }
            if ( state.backtracking==0 ) {
               graphBuilder.endNewScope(); 
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
    // GaaletTransformer.g:200:1: loop returns [LoopNode node] : ^( LOOP stmt= statement (number= DECIMAL_LITERAL )? (varname= variable )? ) ;
    public final LoopNode loop() throws RecognitionException {
        LoopNode node = null;

        CommonTree number=null;
        ArrayList<SequentialNode> stmt = null;

        Variable varname = null;


        try {
            // GaaletTransformer.g:201:3: ( ^( LOOP stmt= statement (number= DECIMAL_LITERAL )? (varname= variable )? ) )
            // GaaletTransformer.g:201:5: ^( LOOP stmt= statement (number= DECIMAL_LITERAL )? (varname= variable )? )
            {
            match(input,LOOP,FOLLOW_LOOP_in_loop759); if (state.failed) return node;

            match(input, Token.DOWN, null); if (state.failed) return node;
            pushFollow(FOLLOW_statement_in_loop763);
            stmt=statement();

            state._fsp--;
            if (state.failed) return node;
            // GaaletTransformer.g:201:33: (number= DECIMAL_LITERAL )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==DECIMAL_LITERAL) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // GaaletTransformer.g:0:0: number= DECIMAL_LITERAL
                    {
                    number=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_loop767); if (state.failed) return node;

                    }
                    break;

            }

            // GaaletTransformer.g:201:58: (varname= variable )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==IDENTIFIER) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // GaaletTransformer.g:0:0: varname= variable
                    {
                    pushFollow(FOLLOW_variable_in_loop772);
                    varname=variable();

                    state._fsp--;
                    if (state.failed) return node;

                    }
                    break;

            }


            match(input, Token.UP, null); if (state.failed) return node;
            if ( state.backtracking==0 ) {
              
                    node = graphBuilder.handleLoop(stmt, (number!=null?number.getText():null), varname); 
                 
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
    // GaaletTransformer.g:206:1: block returns [ArrayList<SequentialNode> nodes] : ^( BLOCK stmts= statement_list ) ;
    public final ArrayList<SequentialNode> block() throws RecognitionException {
        ArrayList<SequentialNode> nodes = null;

        ArrayList<SequentialNode> stmts = null;


         
            graphBuilder.beginNewScope();
            nodes = new ArrayList<SequentialNode>(); 
          
        try {
            // GaaletTransformer.g:212:3: ( ^( BLOCK stmts= statement_list ) )
            // GaaletTransformer.g:212:5: ^( BLOCK stmts= statement_list )
            {
            match(input,BLOCK,FOLLOW_BLOCK_in_block810); if (state.failed) return nodes;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return nodes;
                pushFollow(FOLLOW_statement_list_in_block814);
                stmts=statement_list();

                state._fsp--;
                if (state.failed) return nodes;

                match(input, Token.UP, null); if (state.failed) return nodes;
            }
            if ( state.backtracking==0 ) {
              
                   nodes.addAll(stmts);
                
            }

            }

            if ( state.backtracking==0 ) {
               graphBuilder.endNewScope(); 
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
    // GaaletTransformer.g:217:1: statement_list returns [ArrayList<SequentialNode> args] : (arg= statement )* ;
    public final ArrayList<SequentialNode> statement_list() throws RecognitionException {
        ArrayList<SequentialNode> args = null;

        ArrayList<SequentialNode> arg = null;


         args = new ArrayList<SequentialNode>(); 
        try {
            // GaaletTransformer.g:219:3: ( (arg= statement )* )
            // GaaletTransformer.g:219:5: (arg= statement )*
            {
            // GaaletTransformer.g:219:5: (arg= statement )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==DECIMAL_LITERAL||(LA10_0>=LOOP && LA10_0<=BREAK)||(LA10_0>=INVERSE && LA10_0<=MINUS)||LA10_0==IF||LA10_0==IDENTIFIER||LA10_0==FLOATING_POINT_LITERAL||(LA10_0>=PLUS && LA10_0<=SLASH)||LA10_0==REVERSE||LA10_0==WEDGE||(LA10_0>=DOUBLE_BAR && LA10_0<=SET_OUTPUT)||LA10_0==PRAGMA||(LA10_0>=FUNCTION && LA10_0<=BLOCK)||(LA10_0>=DEFINE_V && LA10_0<=MACRO)) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // GaaletTransformer.g:219:6: arg= statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statement_list847);
            	    arg=statement();

            	    state._fsp--;
            	    if (state.failed) return args;
            	    if ( state.backtracking==0 ) {
            	       args.addAll(arg); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop10;
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
    // GaaletTransformer.g:222:1: expression returns [Expression result] : ( ^( PLUS l= expression r= expression ) | ^( MINUS l= expression r= expression ) | ^( STAR l= expression r= expression ) | ^( SLASH l= expression r= expression ) | ^( DOUBLE_BAR l= expression r= expression ) | ^( DOUBLE_AND l= expression r= expression ) | ^( DOUBLE_EQUALS l= expression r= expression ) | ^( LOGICAL_NEGATION op= expression ) | ^( UNEQUAL l= expression r= expression ) | ^( LESS l= expression r= expression ) | ^( GREATER l= expression r= expression ) | ^( LESS_OR_EQUAL l= expression r= expression ) | ^( GREATER_OR_EQUAL l= expression r= expression ) | ^( WEDGE l= expression r= expression ) | ^( SINGLE_AND l= expression r= expression ) | ^( NEGATION op= expression ) | ^( DUAL op= expression ) | ^( REVERSE op= expression ) | ^( INVERSE op= expression ) | ^( FUNCTION name= IDENTIFIER arguments ) | ^( FUNCTION name= IDENTIFIER ) | ^( BLADE name= IDENTIFIER blade= DECIMAL_LITERAL ) | value= DECIMAL_LITERAL | value= FLOATING_POINT_LITERAL | variableOrConstant );
    public final Expression expression() throws RecognitionException {
        Expression result = null;

        CommonTree name=null;
        CommonTree blade=null;
        CommonTree value=null;
        Expression l = null;

        Expression r = null;

        Expression op = null;

        ArrayList<Expression> arguments17 = null;

        Expression variableOrConstant18 = null;


        try {
            // GaaletTransformer.g:224:2: ( ^( PLUS l= expression r= expression ) | ^( MINUS l= expression r= expression ) | ^( STAR l= expression r= expression ) | ^( SLASH l= expression r= expression ) | ^( DOUBLE_BAR l= expression r= expression ) | ^( DOUBLE_AND l= expression r= expression ) | ^( DOUBLE_EQUALS l= expression r= expression ) | ^( LOGICAL_NEGATION op= expression ) | ^( UNEQUAL l= expression r= expression ) | ^( LESS l= expression r= expression ) | ^( GREATER l= expression r= expression ) | ^( LESS_OR_EQUAL l= expression r= expression ) | ^( GREATER_OR_EQUAL l= expression r= expression ) | ^( WEDGE l= expression r= expression ) | ^( SINGLE_AND l= expression r= expression ) | ^( NEGATION op= expression ) | ^( DUAL op= expression ) | ^( REVERSE op= expression ) | ^( INVERSE op= expression ) | ^( FUNCTION name= IDENTIFIER arguments ) | ^( FUNCTION name= IDENTIFIER ) | ^( BLADE name= IDENTIFIER blade= DECIMAL_LITERAL ) | value= DECIMAL_LITERAL | value= FLOATING_POINT_LITERAL | variableOrConstant )
            int alt11=25;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // GaaletTransformer.g:224:4: ^( PLUS l= expression r= expression )
                    {
                    match(input,PLUS,FOLLOW_PLUS_in_expression872); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression876);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression880);
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
                    // GaaletTransformer.g:226:4: ^( MINUS l= expression r= expression )
                    {
                    match(input,MINUS,FOLLOW_MINUS_in_expression891); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression895);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression899);
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
                    // GaaletTransformer.g:228:4: ^( STAR l= expression r= expression )
                    {
                    match(input,STAR,FOLLOW_STAR_in_expression910); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression914);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression918);
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
                    // GaaletTransformer.g:230:4: ^( SLASH l= expression r= expression )
                    {
                    match(input,SLASH,FOLLOW_SLASH_in_expression929); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression933);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression937);
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
                    // GaaletTransformer.g:232:5: ^( DOUBLE_BAR l= expression r= expression )
                    {
                    match(input,DOUBLE_BAR,FOLLOW_DOUBLE_BAR_in_expression949); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression953);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression957);
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
                    // GaaletTransformer.g:234:5: ^( DOUBLE_AND l= expression r= expression )
                    {
                    match(input,DOUBLE_AND,FOLLOW_DOUBLE_AND_in_expression970); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression974);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression978);
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
                    // GaaletTransformer.g:236:5: ^( DOUBLE_EQUALS l= expression r= expression )
                    {
                    match(input,DOUBLE_EQUALS,FOLLOW_DOUBLE_EQUALS_in_expression990); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression994);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression998);
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
                    // GaaletTransformer.g:238:5: ^( LOGICAL_NEGATION op= expression )
                    {
                    match(input,LOGICAL_NEGATION,FOLLOW_LOGICAL_NEGATION_in_expression1011); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1015);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new LogicalNegation(op); 
                    }

                    }
                    break;
                case 9 :
                    // GaaletTransformer.g:240:5: ^( UNEQUAL l= expression r= expression )
                    {
                    match(input,UNEQUAL,FOLLOW_UNEQUAL_in_expression1029); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1033);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1037);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Inequality(l, r); 
                    }

                    }
                    break;
                case 10 :
                    // GaaletTransformer.g:242:5: ^( LESS l= expression r= expression )
                    {
                    match(input,LESS,FOLLOW_LESS_in_expression1049); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1053);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1057);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.LESS); 
                    }

                    }
                    break;
                case 11 :
                    // GaaletTransformer.g:244:5: ^( GREATER l= expression r= expression )
                    {
                    match(input,GREATER,FOLLOW_GREATER_in_expression1069); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1073);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1077);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.GREATER); 
                    }

                    }
                    break;
                case 12 :
                    // GaaletTransformer.g:246:5: ^( LESS_OR_EQUAL l= expression r= expression )
                    {
                    match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_expression1090); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1094);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1098);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.LESS_OR_EQUAL); 
                    }

                    }
                    break;
                case 13 :
                    // GaaletTransformer.g:248:5: ^( GREATER_OR_EQUAL l= expression r= expression )
                    {
                    match(input,GREATER_OR_EQUAL,FOLLOW_GREATER_OR_EQUAL_in_expression1110); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1114);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1118);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Relation(l, r, Relation.Type.GREATER_OR_EQUAL); 
                    }

                    }
                    break;
                case 14 :
                    // GaaletTransformer.g:250:4: ^( WEDGE l= expression r= expression )
                    {
                    match(input,WEDGE,FOLLOW_WEDGE_in_expression1129); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1133);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1137);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new OuterProduct(l, r); 
                    }

                    }
                    break;
                case 15 :
                    // GaaletTransformer.g:252:4: ^( SINGLE_AND l= expression r= expression )
                    {
                    match(input,SINGLE_AND,FOLLOW_SINGLE_AND_in_expression1148); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1152);
                    l=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1156);
                    r=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new InnerProduct(l, r); 
                    }

                    }
                    break;
                case 16 :
                    // GaaletTransformer.g:254:4: ^( NEGATION op= expression )
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_expression1168); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1172);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Negation(op); 
                    }

                    }
                    break;
                case 17 :
                    // GaaletTransformer.g:256:4: ^( DUAL op= expression )
                    {
                    match(input,DUAL,FOLLOW_DUAL_in_expression1183); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1187);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = graphBuilder.processFunction("*", Collections.singletonList(op)); 
                    }

                    }
                    break;
                case 18 :
                    // GaaletTransformer.g:258:4: ^( REVERSE op= expression )
                    {
                    match(input,REVERSE,FOLLOW_REVERSE_in_expression1199); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1203);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Reverse(op); 
                    }

                    }
                    break;
                case 19 :
                    // GaaletTransformer.g:261:5: ^( INVERSE op= expression )
                    {
                    match(input,INVERSE,FOLLOW_INVERSE_in_expression1217); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1221);
                    op=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      
                        result = new Division(new Reverse(op), new InnerProduct(op, new Reverse(op)));
                    }

                    }
                    break;
                case 20 :
                    // GaaletTransformer.g:265:4: ^( FUNCTION name= IDENTIFIER arguments )
                    {
                    match(input,FUNCTION,FOLLOW_FUNCTION_in_expression1234); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression1238); if (state.failed) return result;
                    pushFollow(FOLLOW_arguments_in_expression1240);
                    arguments17=arguments();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = graphBuilder.processFunction((name!=null?name.getText():null), arguments17); 
                    }

                    }
                    break;
                case 21 :
                    // GaaletTransformer.g:266:4: ^( FUNCTION name= IDENTIFIER )
                    {
                    match(input,FUNCTION,FOLLOW_FUNCTION_in_expression1249); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression1253); if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = graphBuilder.processFunction((name!=null?name.getText():null), null); 
                    }

                    }
                    break;
                case 22 :
                    // GaaletTransformer.g:269:5: ^( BLADE name= IDENTIFIER blade= DECIMAL_LITERAL )
                    {
                    match(input,BLADE,FOLLOW_BLADE_in_expression1269); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression1273); if (state.failed) return result;
                    blade=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_expression1277); if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = graphBuilder.blade((name!=null?name.getText():null), (blade!=null?blade.getText():null));
                           
                    }

                    }
                    break;
                case 23 :
                    // GaaletTransformer.g:272:4: value= DECIMAL_LITERAL
                    {
                    value=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_expression1290); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new FloatConstant((value!=null?value.getText():null)); 
                    }

                    }
                    break;
                case 24 :
                    // GaaletTransformer.g:274:4: value= FLOATING_POINT_LITERAL
                    {
                    value=(CommonTree)match(input,FLOATING_POINT_LITERAL,FOLLOW_FLOATING_POINT_LITERAL_in_expression1301); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new FloatConstant((value!=null?value.getText():null)); 
                    }

                    }
                    break;
                case 25 :
                    // GaaletTransformer.g:276:4: variableOrConstant
                    {
                    pushFollow(FOLLOW_variableOrConstant_in_expression1310);
                    variableOrConstant18=variableOrConstant();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = variableOrConstant18; 
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
    // GaaletTransformer.g:280:1: variableOrConstant returns [Expression result] : name= IDENTIFIER ;
    public final Expression variableOrConstant() throws RecognitionException {
        Expression result = null;

        CommonTree name=null;

        try {
            // GaaletTransformer.g:281:2: (name= IDENTIFIER )
            // GaaletTransformer.g:281:4: name= IDENTIFIER
            {
            name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_variableOrConstant1329); if (state.failed) return result;
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
    // GaaletTransformer.g:284:1: arguments returns [ArrayList<Expression> args] : (arg= expression )* ;
    public final ArrayList<Expression> arguments() throws RecognitionException {
        ArrayList<Expression> args = null;

        Expression arg = null;


         args = new ArrayList<Expression>(); 
        try {
            // GaaletTransformer.g:286:2: ( (arg= expression )* )
            // GaaletTransformer.g:286:4: (arg= expression )*
            {
            // GaaletTransformer.g:286:4: (arg= expression )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==DECIMAL_LITERAL||(LA12_0>=INVERSE && LA12_0<=MINUS)||LA12_0==IDENTIFIER||LA12_0==FLOATING_POINT_LITERAL||(LA12_0>=PLUS && LA12_0<=SLASH)||LA12_0==REVERSE||LA12_0==WEDGE||(LA12_0>=DOUBLE_BAR && LA12_0<=DOUBLE_EQUALS)||(LA12_0>=UNEQUAL && LA12_0<=GREATER_OR_EQUAL)||LA12_0==FUNCTION||(LA12_0>=NEGATION && LA12_0<=DUAL)||LA12_0==BLADE) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // GaaletTransformer.g:286:5: arg= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_arguments1356);
            	    arg=expression();

            	    state._fsp--;
            	    if (state.failed) return args;
            	    if ( state.backtracking==0 ) {
            	       args.add(arg); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop12;
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
    // GaaletTransformer.g:289:1: float_literal returns [String result] : (sign= MINUS )? val= FLOATING_POINT_LITERAL ;
    public final String float_literal() throws RecognitionException {
        String result = null;

        CommonTree sign=null;
        CommonTree val=null;

        try {
            // GaaletTransformer.g:290:3: ( (sign= MINUS )? val= FLOATING_POINT_LITERAL )
            // GaaletTransformer.g:290:5: (sign= MINUS )? val= FLOATING_POINT_LITERAL
            {
            // GaaletTransformer.g:290:9: (sign= MINUS )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==MINUS) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // GaaletTransformer.g:0:0: sign= MINUS
                    {
                    sign=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_float_literal1378); if (state.failed) return result;

                    }
                    break;

            }

            val=(CommonTree)match(input,FLOATING_POINT_LITERAL,FOLLOW_FLOATING_POINT_LITERAL_in_float_literal1383); if (state.failed) return result;
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


    // $ANTLR start "decimal_literal"
    // GaaletTransformer.g:293:1: decimal_literal returns [String result] : (sign= MINUS )? val= DECIMAL_LITERAL ;
    public final String decimal_literal() throws RecognitionException {
        String result = null;

        CommonTree sign=null;
        CommonTree val=null;

        try {
            // GaaletTransformer.g:294:3: ( (sign= MINUS )? val= DECIMAL_LITERAL )
            // GaaletTransformer.g:294:5: (sign= MINUS )? val= DECIMAL_LITERAL
            {
            // GaaletTransformer.g:294:9: (sign= MINUS )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==MINUS) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // GaaletTransformer.g:0:0: sign= MINUS
                    {
                    sign=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_decimal_literal1403); if (state.failed) return result;

                    }
                    break;

            }

            val=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_decimal_literal1408); if (state.failed) return result;
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
    // $ANTLR end "decimal_literal"

    // $ANTLR start synpred18_GaaletTransformer
    public final void synpred18_GaaletTransformer_fragment() throws RecognitionException {   
        CommonTree arg=null;

        // GaaletTransformer.g:123:6: (arg= ( DECIMAL_LITERAL | HEX ) )
        // GaaletTransformer.g:123:6: arg= ( DECIMAL_LITERAL | HEX )
        {
        arg=(CommonTree)input.LT(1);
        if ( input.LA(1)==DECIMAL_LITERAL||input.LA(1)==HEX ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }
    }
    // $ANTLR end synpred18_GaaletTransformer

    // $ANTLR start synpred46_GaaletTransformer
    public final void synpred46_GaaletTransformer_fragment() throws RecognitionException {   
        CommonTree name=null;

        // GaaletTransformer.g:265:4: ( ^( FUNCTION name= IDENTIFIER arguments ) )
        // GaaletTransformer.g:265:4: ^( FUNCTION name= IDENTIFIER arguments )
        {
        match(input,FUNCTION,FOLLOW_FUNCTION_in_synpred46_GaaletTransformer1234); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_synpred46_GaaletTransformer1238); if (state.failed) return ;
        pushFollow(FOLLOW_arguments_in_synpred46_GaaletTransformer1240);
        arguments();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred46_GaaletTransformer

    // $ANTLR start synpred47_GaaletTransformer
    public final void synpred47_GaaletTransformer_fragment() throws RecognitionException {   
        CommonTree name=null;

        // GaaletTransformer.g:266:4: ( ^( FUNCTION name= IDENTIFIER ) )
        // GaaletTransformer.g:266:4: ^( FUNCTION name= IDENTIFIER )
        {
        match(input,FUNCTION,FOLLOW_FUNCTION_in_synpred47_GaaletTransformer1249); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_synpred47_GaaletTransformer1253); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred47_GaaletTransformer

    // Delegated rules

    public final boolean synpred47_GaaletTransformer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred47_GaaletTransformer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_GaaletTransformer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_GaaletTransformer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred46_GaaletTransformer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred46_GaaletTransformer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
        "\36\uffff";
    static final String DFA11_eofS =
        "\36\uffff";
    static final String DFA11_minS =
        "\1\4\23\uffff\1\2\4\uffff\1\41\1\3\1\0\2\uffff";
    static final String DFA11_maxS =
        "\1\126\23\uffff\1\2\4\uffff\1\41\1\126\1\0\2\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23\1\uffff\1\26\1\27\1\30\1\31\3"+
        "\uffff\1\24\1\25";
    static final String DFA11_specialS =
        "\33\uffff\1\0\2\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\26\23\uffff\1\23\1\2\7\uffff\1\30\11\uffff\1\27\3\uffff\1"+
            "\1\1\3\1\4\3\uffff\1\22\3\uffff\1\16\2\uffff\1\5\1\6\1\17\1"+
            "\7\1\uffff\1\11\1\12\1\13\1\14\1\15\4\uffff\1\24\1\uffff\1\20"+
            "\1\10\1\21\7\uffff\1\25",
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\31",
            "",
            "",
            "",
            "",
            "\1\32",
            "\1\33\1\34\23\uffff\2\34\7\uffff\1\34\11\uffff\1\34\3\uffff"+
            "\3\34\3\uffff\1\34\3\uffff\1\34\2\uffff\4\34\1\uffff\5\34\4"+
            "\uffff\1\34\1\uffff\3\34\7\uffff\1\34",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "222:1: expression returns [Expression result] : ( ^( PLUS l= expression r= expression ) | ^( MINUS l= expression r= expression ) | ^( STAR l= expression r= expression ) | ^( SLASH l= expression r= expression ) | ^( DOUBLE_BAR l= expression r= expression ) | ^( DOUBLE_AND l= expression r= expression ) | ^( DOUBLE_EQUALS l= expression r= expression ) | ^( LOGICAL_NEGATION op= expression ) | ^( UNEQUAL l= expression r= expression ) | ^( LESS l= expression r= expression ) | ^( GREATER l= expression r= expression ) | ^( LESS_OR_EQUAL l= expression r= expression ) | ^( GREATER_OR_EQUAL l= expression r= expression ) | ^( WEDGE l= expression r= expression ) | ^( SINGLE_AND l= expression r= expression ) | ^( NEGATION op= expression ) | ^( DUAL op= expression ) | ^( REVERSE op= expression ) | ^( INVERSE op= expression ) | ^( FUNCTION name= IDENTIFIER arguments ) | ^( FUNCTION name= IDENTIFIER ) | ^( BLADE name= IDENTIFIER blade= DECIMAL_LITERAL ) | value= DECIMAL_LITERAL | value= FLOATING_POINT_LITERAL | variableOrConstant );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TreeNodeStream input = (TreeNodeStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_27 = input.LA(1);

                         
                        int index11_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred46_GaaletTransformer()) ) {s = 28;}

                        else if ( (synpred47_GaaletTransformer()) ) {s = 29;}

                         
                        input.seek(index11_27);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_statement_in_script93 = new BitSet(new long[]{0xF223880243006012L,0x0000000001FEFD7FL});
    public static final BitSet FOLLOW_PROCEDURE_in_statement117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_statement121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_assignment_in_statement134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_define_assignment_in_statement143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_statement_in_statement160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_statement170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_statement181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_macro_in_statement190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_statement201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pragma_in_statement210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SET_OUTPUT_in_statement226 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_in_statement228 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BLADE_ASSIGN_in_statement240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_statement244 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_statement248 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_statement250 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DEFINE_V_in_statement265 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_in_statement267 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DEFINE_MV_in_statement290 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_statement294 = new BitSet(new long[]{0x0000000000000098L});
    public static final BitSet FOLLOW_gaalet_arguments_list_in_statement297 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DEFINE_MV_AND_ASSIGN_in_statement309 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_statement313 = new BitSet(new long[]{0xF223880203000090L,0x000000000040743EL});
    public static final BitSet FOLLOW_gaalet_arguments_list_in_statement316 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_statement320 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DEFINE_GEALG_in_statement355 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_statement359 = new BitSet(new long[]{0x0000000000000090L,0x0000000008000000L});
    public static final BitSet FOLLOW_gaalet_arguments_list_in_statement361 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_ARG_LIST_SEP_in_statement363 = new BitSet(new long[]{0xF223880203000018L,0x000000000040743EL});
    public static final BitSet FOLLOW_arguments_in_statement365 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_gaalet_arguments_list403 = new BitSet(new long[]{0x0000000000000092L});
    public static final BitSet FOLLOW_MACRO_in_macro441 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_macro445 = new BitSet(new long[]{0xF223880243006010L,0x0000000001FEFD7FL});
    public static final BitSet FOLLOW_statement_list_in_macro451 = new BitSet(new long[]{0x0000000000000008L,0x0000000004000000L});
    public static final BitSet FOLLOW_return_value_in_macro455 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RETURN_in_return_value479 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_return_value483 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma500 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RANGE_LITERAL_in_pragma502 = new BitSet(new long[]{0x0000080002000000L});
    public static final BitSet FOLLOW_float_literal_in_pragma506 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma508 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma512 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma514 = new BitSet(new long[]{0x0000080002000000L});
    public static final BitSet FOLLOW_float_literal_in_pragma518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma532 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_OUTPUT_LITERAL_in_pragma534 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma552 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IGNORE_LITERAL_in_pragma554 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_variable_in_pragma558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQUALS_in_assignment583 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_in_assignment587 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_assignment591 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DEFINE_ASSIGNMENT_in_define_assignment610 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_in_define_assignment614 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_define_assignment618 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_variableOrConstant_in_variable637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_statement672 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_if_statement676 = new BitSet(new long[]{0xF2238802C3006018L,0x0000000001FFFD7FL});
    public static final BitSet FOLLOW_statement_in_if_statement680 = new BitSet(new long[]{0x0000000080000008L,0x0000000000010000L});
    public static final BitSet FOLLOW_else_statement_in_if_statement684 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ELSE_in_else_statement722 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_block_in_else_statement724 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ELSEIF_in_else_statement734 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_if_statement_in_else_statement736 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOOP_in_loop759 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_loop763 = new BitSet(new long[]{0xF223880203000018L,0x000000000040743EL});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_loop767 = new BitSet(new long[]{0xF223880203000018L,0x000000000040743EL});
    public static final BitSet FOLLOW_variable_in_loop772 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BLOCK_in_block810 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_list_in_block814 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_statement_in_statement_list847 = new BitSet(new long[]{0xF223880243006012L,0x0000000001FEFD7FL});
    public static final BitSet FOLLOW_PLUS_in_expression872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression876 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression880 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_expression891 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression895 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression899 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_expression910 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression914 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression918 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SLASH_in_expression929 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression933 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression937 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOUBLE_BAR_in_expression949 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression953 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression957 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOUBLE_AND_in_expression970 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression974 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression978 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOUBLE_EQUALS_in_expression990 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression994 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression998 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOGICAL_NEGATION_in_expression1011 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1015 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNEQUAL_in_expression1029 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1033 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression1037 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LESS_in_expression1049 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1053 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression1057 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GREATER_in_expression1069 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1073 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression1077 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_expression1090 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1094 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression1098 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GREATER_OR_EQUAL_in_expression1110 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1114 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression1118 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEDGE_in_expression1129 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1133 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression1137 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SINGLE_AND_in_expression1148 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1152 = new BitSet(new long[]{0xF223880203000010L,0x000000000040743EL});
    public static final BitSet FOLLOW_expression_in_expression1156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEGATION_in_expression1168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1172 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DUAL_in_expression1183 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1187 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REVERSE_in_expression1199 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1203 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INVERSE_in_expression1217 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1221 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_expression1234 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression1238 = new BitSet(new long[]{0xF223880203000018L,0x000000000040743EL});
    public static final BitSet FOLLOW_arguments_in_expression1240 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_expression1249 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression1253 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BLADE_in_expression1269 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression1273 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_expression1277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_expression1290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOATING_POINT_LITERAL_in_expression1301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableOrConstant_in_expression1310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_variableOrConstant1329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_arguments1356 = new BitSet(new long[]{0xF223880203000012L,0x000000000040743EL});
    public static final BitSet FOLLOW_MINUS_in_float_literal1378 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_FLOATING_POINT_LITERAL_in_float_literal1383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_decimal_literal1403 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_decimal_literal1408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred18_GaaletTransformer403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_synpred46_GaaletTransformer1234 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_synpred46_GaaletTransformer1238 = new BitSet(new long[]{0xF223880203000018L,0x000000000040743EL});
    public static final BitSet FOLLOW_arguments_in_synpred46_GaaletTransformer1240 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_synpred47_GaaletTransformer1249 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_synpred47_GaaletTransformer1253 = new BitSet(new long[]{0x0000000000000008L});

}