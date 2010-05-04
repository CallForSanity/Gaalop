// $ANTLR 3.1.1 de/gaalop/maple/parser/MapleTransformer.g 2010-05-04 18:11:55

	package de.gaalop.maple.parser;
	
	import java.util.List;
	import java.util.ArrayList;
	import de.gaalop.cfg.*;
	import de.gaalop.dfg.*;
  import java.util.HashMap;



import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class MapleTransformer extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "GAALOPARRAY", "LETTER", "DIGIT", "IDENTIFIER", "DECIMAL_LITERAL", "EXPONENT", "FLOATING_POINT_LITERAL", "MINUS", "LSBRACKET", "RSBRACKET", "LBRACKET", "RBRACKET", "ASSIGNMENT", "EQUALS", "COMMA", "PLUS", "STAR", "SLASH", "MODULO", "SEMICOLON", "WEDGE", "WS", "DECLAREARRAY", "COEFFICIENT", "FUNCTION", "MV_SUBSCRIPT", "VARIABLE", "ASSIGNBLADE", "NEGATION"
    };
    public static final int FUNCTION=28;
    public static final int MODULO=22;
    public static final int EXPONENT=9;
    public static final int STAR=20;
    public static final int LETTER=5;
    public static final int GAALOPARRAY=4;
    public static final int ASSIGNBLADE=31;
    public static final int SEMICOLON=23;
    public static final int EQUALS=17;
    public static final int MINUS=11;
    public static final int EOF=-1;
    public static final int MV_SUBSCRIPT=29;
    public static final int LSBRACKET=12;
    public static final int LBRACKET=14;
    public static final int WEDGE=24;
    public static final int WS=25;
    public static final int SLASH=21;
    public static final int FLOATING_POINT_LITERAL=10;
    public static final int VARIABLE=30;
    public static final int NEGATION=32;
    public static final int COMMA=18;
    public static final int RSBRACKET=13;
    public static final int IDENTIFIER=7;
    public static final int PLUS=19;
    public static final int ASSIGNMENT=16;
    public static final int COEFFICIENT=27;
    public static final int DIGIT=6;
    public static final int RBRACKET=15;
    public static final int DECIMAL_LITERAL=8;
    public static final int DECLAREARRAY=26;

    // delegates
    // delegators


        public MapleTransformer(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public MapleTransformer(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return MapleTransformer.tokenNames; }
    public String getGrammarFileName() { return "de/gaalop/maple/parser/MapleTransformer.g"; }

    	
    	// Creates an expression from an identifier and takes constants into account
    	private Expression processIdentifier(String name, HashMap<String, String> minVal, HashMap<String, String> maxVal) {
        Variable v = new Variable(name);
        if (minVal.containsKey(name)) {
          v.setMinValue(minVal.get(name));
        }
    		if (maxVal.containsKey(name)) {
          v.setMaxValue(maxVal.get(name));
        }
    		return v;
    	}
    	
    	private Expression processFunction(String name, ArrayList<Expression> args) {
    		System.out.println("FUNCTION: " + name);
    		for (Object obj : args) {
    			System.out.println("ARG: " + args);
    			System.out.println("ARG-Class: " + args.getClass());
    		}
        if (name.equals("abs")) {
          return new MathFunctionCall(args.get(0), MathFunction.ABS);
        } else {}
          return null;
    	  }



    // $ANTLR start "script"
    // de/gaalop/maple/parser/MapleTransformer.g:45:1: script[ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [List<SequentialNode> nodes] : ( statement[graph, minVal, maxVal, nodes] )* ;
    public final List<SequentialNode> script(ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal) throws RecognitionException {
        List<SequentialNode> nodes = null;

         nodes = new ArrayList<SequentialNode>(); 
        try {
            // de/gaalop/maple/parser/MapleTransformer.g:47:3: ( ( statement[graph, minVal, maxVal, nodes] )* )
            // de/gaalop/maple/parser/MapleTransformer.g:47:5: ( statement[graph, minVal, maxVal, nodes] )*
            {
            // de/gaalop/maple/parser/MapleTransformer.g:47:5: ( statement[graph, minVal, maxVal, nodes] )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=DECLAREARRAY && LA1_0<=COEFFICIENT)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // de/gaalop/maple/parser/MapleTransformer.g:47:5: statement[graph, minVal, maxVal, nodes]
            	    {
            	    pushFollow(FOLLOW_statement_in_script58);
            	    statement(graph, minVal, maxVal, nodes);

            	    state._fsp--;


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
        return nodes;
    }
    // $ANTLR end "script"


    // $ANTLR start "statement"
    // de/gaalop/maple/parser/MapleTransformer.g:49:1: statement[ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal, List<SequentialNode> nodes] : ( declareArray[graph] | coefficient[graph, minVal, maxVal] );
    public final void statement(ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal, List<SequentialNode> nodes) throws RecognitionException {
        SequentialNode coefficient1 = null;


        try {
            // de/gaalop/maple/parser/MapleTransformer.g:50:2: ( declareArray[graph] | coefficient[graph, minVal, maxVal] )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==DECLAREARRAY) ) {
                alt2=1;
            }
            else if ( (LA2_0==COEFFICIENT) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // de/gaalop/maple/parser/MapleTransformer.g:50:4: declareArray[graph]
                    {
                    pushFollow(FOLLOW_declareArray_in_statement70);
                    declareArray(graph);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // de/gaalop/maple/parser/MapleTransformer.g:51:4: coefficient[graph, minVal, maxVal]
                    {
                    pushFollow(FOLLOW_coefficient_in_statement76);
                    coefficient1=coefficient(graph, minVal, maxVal);

                    state._fsp--;

                     nodes.add(coefficient1); 

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
    // $ANTLR end "statement"


    // $ANTLR start "declareArray"
    // de/gaalop/maple/parser/MapleTransformer.g:54:1: declareArray[ControlFlowGraph graph] : ^( DECLAREARRAY name= IDENTIFIER ) ;
    public final void declareArray(ControlFlowGraph graph) throws RecognitionException {
        CommonTree name=null;

        try {
            // de/gaalop/maple/parser/MapleTransformer.g:55:2: ( ^( DECLAREARRAY name= IDENTIFIER ) )
            // de/gaalop/maple/parser/MapleTransformer.g:55:4: ^( DECLAREARRAY name= IDENTIFIER )
            {
            match(input,DECLAREARRAY,FOLLOW_DECLAREARRAY_in_declareArray93); 

            match(input, Token.DOWN, null); 
            name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_declareArray97); 

            match(input, Token.UP, null); 
             /* What to do with declarations? Is it really needed? */ 

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
    // $ANTLR end "declareArray"


    // $ANTLR start "coefficient"
    // de/gaalop/maple/parser/MapleTransformer.g:58:1: coefficient[ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [SequentialNode result] : ^( COEFFICIENT mvName= IDENTIFIER index= DECIMAL_LITERAL value= expression[minVal, maxVal] ) ;
    public final SequentialNode coefficient(ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal) throws RecognitionException {
        SequentialNode result = null;

        CommonTree mvName=null;
        CommonTree index=null;
        Expression value = null;


        try {
            // de/gaalop/maple/parser/MapleTransformer.g:59:2: ( ^( COEFFICIENT mvName= IDENTIFIER index= DECIMAL_LITERAL value= expression[minVal, maxVal] ) )
            // de/gaalop/maple/parser/MapleTransformer.g:59:4: ^( COEFFICIENT mvName= IDENTIFIER index= DECIMAL_LITERAL value= expression[minVal, maxVal] )
            {
            match(input,COEFFICIENT,FOLLOW_COEFFICIENT_in_coefficient118); 

            match(input, Token.DOWN, null); 
            mvName=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_coefficient122); 
            index=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_coefficient126); 
            pushFollow(FOLLOW_expression_in_coefficient130);
            value=expression(minVal, maxVal);

            state._fsp--;


            match(input, Token.UP, null); 

            		MultivectorComponent component = new MultivectorComponent((mvName!=null?mvName.getText():null).replaceAll("_opt",""), Integer.valueOf((index!=null?index.getText():null)) - 1);
            		result = new AssignmentNode(graph, component, value);
            	 

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
    // $ANTLR end "coefficient"


    // $ANTLR start "expression"
    // de/gaalop/maple/parser/MapleTransformer.g:65:1: expression[HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [Expression result] : ( ^( PLUS l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( MINUS l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( STAR l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( SLASH l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( WEDGE l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( NEGATION v= expression[minVal, maxVal] ) | ^( FUNCTION name= IDENTIFIER arguments[minVal, maxVal] ) | ^( VARIABLE name= IDENTIFIER ) | value= DECIMAL_LITERAL | value= FLOATING_POINT_LITERAL | ^( MV_SUBSCRIPT name= IDENTIFIER index= DECIMAL_LITERAL ) );
    public final Expression expression(HashMap<String, String> minVal, HashMap<String, String> maxVal) throws RecognitionException {
        Expression result = null;

        CommonTree name=null;
        CommonTree value=null;
        CommonTree index=null;
        Expression l = null;

        Expression r = null;

        Expression v = null;

        ArrayList<Expression> arguments2 = null;


        try {
            // de/gaalop/maple/parser/MapleTransformer.g:67:2: ( ^( PLUS l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( MINUS l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( STAR l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( SLASH l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( WEDGE l= expression[minVal, maxVal] r= expression[minVal, maxVal] ) | ^( NEGATION v= expression[minVal, maxVal] ) | ^( FUNCTION name= IDENTIFIER arguments[minVal, maxVal] ) | ^( VARIABLE name= IDENTIFIER ) | value= DECIMAL_LITERAL | value= FLOATING_POINT_LITERAL | ^( MV_SUBSCRIPT name= IDENTIFIER index= DECIMAL_LITERAL ) )
            int alt3=11;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt3=1;
                }
                break;
            case MINUS:
                {
                alt3=2;
                }
                break;
            case STAR:
                {
                alt3=3;
                }
                break;
            case SLASH:
                {
                alt3=4;
                }
                break;
            case WEDGE:
                {
                alt3=5;
                }
                break;
            case NEGATION:
                {
                alt3=6;
                }
                break;
            case FUNCTION:
                {
                alt3=7;
                }
                break;
            case VARIABLE:
                {
                alt3=8;
                }
                break;
            case DECIMAL_LITERAL:
                {
                alt3=9;
                }
                break;
            case FLOATING_POINT_LITERAL:
                {
                alt3=10;
                }
                break;
            case MV_SUBSCRIPT:
                {
                alt3=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // de/gaalop/maple/parser/MapleTransformer.g:67:4: ^( PLUS l= expression[minVal, maxVal] r= expression[minVal, maxVal] )
                    {
                    match(input,PLUS,FOLLOW_PLUS_in_expression153); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression157);
                    l=expression(minVal, maxVal);

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression162);
                    r=expression(minVal, maxVal);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new Addition(l, r); 

                    }
                    break;
                case 2 :
                    // de/gaalop/maple/parser/MapleTransformer.g:69:4: ^( MINUS l= expression[minVal, maxVal] r= expression[minVal, maxVal] )
                    {
                    match(input,MINUS,FOLLOW_MINUS_in_expression174); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression178);
                    l=expression(minVal, maxVal);

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression183);
                    r=expression(minVal, maxVal);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new Subtraction(l, r); 

                    }
                    break;
                case 3 :
                    // de/gaalop/maple/parser/MapleTransformer.g:71:4: ^( STAR l= expression[minVal, maxVal] r= expression[minVal, maxVal] )
                    {
                    match(input,STAR,FOLLOW_STAR_in_expression195); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression199);
                    l=expression(minVal, maxVal);

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression204);
                    r=expression(minVal, maxVal);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new Multiplication(l, r); 

                    }
                    break;
                case 4 :
                    // de/gaalop/maple/parser/MapleTransformer.g:73:4: ^( SLASH l= expression[minVal, maxVal] r= expression[minVal, maxVal] )
                    {
                    match(input,SLASH,FOLLOW_SLASH_in_expression216); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression220);
                    l=expression(minVal, maxVal);

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression225);
                    r=expression(minVal, maxVal);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new Division(l, r); 

                    }
                    break;
                case 5 :
                    // de/gaalop/maple/parser/MapleTransformer.g:75:4: ^( WEDGE l= expression[minVal, maxVal] r= expression[minVal, maxVal] )
                    {
                    match(input,WEDGE,FOLLOW_WEDGE_in_expression237); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression241);
                    l=expression(minVal, maxVal);

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression246);
                    r=expression(minVal, maxVal);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new Exponentiation(l, r); 

                    }
                    break;
                case 6 :
                    // de/gaalop/maple/parser/MapleTransformer.g:77:4: ^( NEGATION v= expression[minVal, maxVal] )
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_expression258); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression262);
                    v=expression(minVal, maxVal);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new Negation(v); 

                    }
                    break;
                case 7 :
                    // de/gaalop/maple/parser/MapleTransformer.g:79:4: ^( FUNCTION name= IDENTIFIER arguments[minVal, maxVal] )
                    {
                    match(input,FUNCTION,FOLLOW_FUNCTION_in_expression274); 

                    match(input, Token.DOWN, null); 
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression278); 
                    pushFollow(FOLLOW_arguments_in_expression280);
                    arguments2=arguments(minVal, maxVal);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = processFunction((name!=null?name.getText():null), arguments2); 

                    }
                    break;
                case 8 :
                    // de/gaalop/maple/parser/MapleTransformer.g:81:4: ^( VARIABLE name= IDENTIFIER )
                    {
                    match(input,VARIABLE,FOLLOW_VARIABLE_in_expression292); 

                    match(input, Token.DOWN, null); 
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression296); 

                    match(input, Token.UP, null); 
                     result = processIdentifier((name!=null?name.getText():null), minVal, maxVal); 

                    }
                    break;
                case 9 :
                    // de/gaalop/maple/parser/MapleTransformer.g:83:4: value= DECIMAL_LITERAL
                    {
                    value=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_expression308); 
                     result = new FloatConstant((value!=null?value.getText():null)); 

                    }
                    break;
                case 10 :
                    // de/gaalop/maple/parser/MapleTransformer.g:85:4: value= FLOATING_POINT_LITERAL
                    {
                    value=(CommonTree)match(input,FLOATING_POINT_LITERAL,FOLLOW_FLOATING_POINT_LITERAL_in_expression319); 
                     result = new FloatConstant(java.lang.Float.parseFloat((value!=null?value.getText():null)));

                    }
                    break;
                case 11 :
                    // de/gaalop/maple/parser/MapleTransformer.g:87:4: ^( MV_SUBSCRIPT name= IDENTIFIER index= DECIMAL_LITERAL )
                    {
                    match(input,MV_SUBSCRIPT,FOLLOW_MV_SUBSCRIPT_in_expression329); 

                    match(input, Token.DOWN, null); 
                    name=(CommonTree)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression333); 
                    index=(CommonTree)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_expression337); 

                    match(input, Token.UP, null); 
                     result = new MultivectorComponent((name!=null?name.getText():null).replaceAll("_opt",""), Integer.valueOf((index!=null?index.getText():null)) - 1); 

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


    // $ANTLR start "arguments"
    // de/gaalop/maple/parser/MapleTransformer.g:90:1: arguments[HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [ArrayList<Expression> args] : (arg= expression[minVal, maxVal] )* ;
    public final ArrayList<Expression> arguments(HashMap<String, String> minVal, HashMap<String, String> maxVal) throws RecognitionException {
        ArrayList<Expression> args = null;

        Expression arg = null;


         args = new ArrayList<Expression>(); 
        try {
            // de/gaalop/maple/parser/MapleTransformer.g:92:2: ( (arg= expression[minVal, maxVal] )* )
            // de/gaalop/maple/parser/MapleTransformer.g:92:4: (arg= expression[minVal, maxVal] )*
            {
            // de/gaalop/maple/parser/MapleTransformer.g:92:4: (arg= expression[minVal, maxVal] )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==DECIMAL_LITERAL||(LA4_0>=FLOATING_POINT_LITERAL && LA4_0<=MINUS)||(LA4_0>=PLUS && LA4_0<=SLASH)||LA4_0==WEDGE||(LA4_0>=FUNCTION && LA4_0<=VARIABLE)||LA4_0==NEGATION) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // de/gaalop/maple/parser/MapleTransformer.g:92:5: arg= expression[minVal, maxVal]
            	    {
            	    pushFollow(FOLLOW_expression_in_arguments365);
            	    arg=expression(minVal, maxVal);

            	    state._fsp--;

            	     args.add(arg); 

            	    }
            	    break;

            	default :
            	    break loop4;
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

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_script58 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_declareArray_in_statement70 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_coefficient_in_statement76 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECLAREARRAY_in_declareArray93 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_declareArray97 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COEFFICIENT_in_coefficient118 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_coefficient122 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_coefficient126 = new BitSet(new long[]{0x0000000171380D00L});
    public static final BitSet FOLLOW_expression_in_coefficient130 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PLUS_in_expression153 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression157 = new BitSet(new long[]{0x0000000171380D00L});
    public static final BitSet FOLLOW_expression_in_expression162 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_expression174 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression178 = new BitSet(new long[]{0x0000000171380D00L});
    public static final BitSet FOLLOW_expression_in_expression183 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_expression195 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression199 = new BitSet(new long[]{0x0000000171380D00L});
    public static final BitSet FOLLOW_expression_in_expression204 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SLASH_in_expression216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression220 = new BitSet(new long[]{0x0000000171380D00L});
    public static final BitSet FOLLOW_expression_in_expression225 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEDGE_in_expression237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression241 = new BitSet(new long[]{0x0000000171380D00L});
    public static final BitSet FOLLOW_expression_in_expression246 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEGATION_in_expression258 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression262 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_expression274 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression278 = new BitSet(new long[]{0x0000000171380D08L});
    public static final BitSet FOLLOW_arguments_in_expression280 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIABLE_in_expression292 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression296 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_expression308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOATING_POINT_LITERAL_in_expression319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MV_SUBSCRIPT_in_expression329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression333 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_expression337 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_arguments365 = new BitSet(new long[]{0x0000000171380D02L});

}