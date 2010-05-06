// $ANTLR 3.1.1 de\\gaalop\\maple\\parser\\MapleParser.g 2010-05-05 19:19:49

	package de.gaalop.maple.parser;
	
	import java.util.List;
	import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class MapleParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "GAALOPARRAY", "LETTER", "DIGIT", "IDENTIFIER", "DECIMAL_LITERAL", "EXPONENT", "FLOATING_POINT_LITERAL", "MINUS", "LSBRACKET", "RSBRACKET", "LBRACKET", "RBRACKET", "ASSIGNMENT", "EQUALS", "COMMA", "PLUS", "STAR", "SLASH", "MODULO", "SEMICOLON", "WEDGE", "WS", "DECLAREARRAY", "COEFFICIENT", "FUNCTION", "MV_SUBSCRIPT", "VARIABLE", "ASSIGNBLADE", "NEGATION"
    };
    public static final int FUNCTION=28;
    public static final int EXPONENT=9;
    public static final int MODULO=22;
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
    public static final int NEGATION=32;
    public static final int VARIABLE=30;
    public static final int FLOATING_POINT_LITERAL=10;
    public static final int RSBRACKET=13;
    public static final int COMMA=18;
    public static final int IDENTIFIER=7;
    public static final int PLUS=19;
    public static final int COEFFICIENT=27;
    public static final int ASSIGNMENT=16;
    public static final int DIGIT=6;
    public static final int RBRACKET=15;
    public static final int DECIMAL_LITERAL=8;
    public static final int DECLAREARRAY=26;

    // delegates
    // delegators


        public MapleParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public MapleParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[37+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return MapleParser.tokenNames; }
    public String getGrammarFileName() { return "de\\gaalop\\maple\\parser\\MapleParser.g"; }


    public static class program_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // de\\gaalop\\maple\\parser\\MapleParser.g:32:1: program : ( statement )+ ;
    public final MapleParser.program_return program() throws RecognitionException {
        MapleParser.program_return retval = new MapleParser.program_return();
        retval.start = input.LT(1);
        int program_StartIndex = input.index();
        Object root_0 = null;

        MapleParser.statement_return statement1 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:32:9: ( ( statement )+ )
            // de\\gaalop\\maple\\parser\\MapleParser.g:33:2: ( statement )+
            {
            root_0 = (Object)adaptor.nil();

            // de\\gaalop\\maple\\parser\\MapleParser.g:33:2: ( statement )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==GAALOPARRAY||LA1_0==IDENTIFIER) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // de\\gaalop\\maple\\parser\\MapleParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_program97);
            	    statement1=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, program_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "program"

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // de\\gaalop\\maple\\parser\\MapleParser.g:36:1: statement : ( declareArray | assignCoefficient ) SEMICOLON ;
    public final MapleParser.statement_return statement() throws RecognitionException {
        MapleParser.statement_return retval = new MapleParser.statement_return();
        retval.start = input.LT(1);
        int statement_StartIndex = input.index();
        Object root_0 = null;

        Token SEMICOLON4=null;
        MapleParser.declareArray_return declareArray2 = null;

        MapleParser.assignCoefficient_return assignCoefficient3 = null;


        Object SEMICOLON4_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:37:2: ( ( declareArray | assignCoefficient ) SEMICOLON )
            // de\\gaalop\\maple\\parser\\MapleParser.g:37:4: ( declareArray | assignCoefficient ) SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            // de\\gaalop\\maple\\parser\\MapleParser.g:37:4: ( declareArray | assignCoefficient )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==GAALOPARRAY) ) {
                alt2=1;
            }
            else if ( (LA2_0==IDENTIFIER) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:37:6: declareArray
                    {
                    pushFollow(FOLLOW_declareArray_in_statement111);
                    declareArray2=declareArray();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, declareArray2.getTree());

                    }
                    break;
                case 2 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:37:21: assignCoefficient
                    {
                    pushFollow(FOLLOW_assignCoefficient_in_statement115);
                    assignCoefficient3=assignCoefficient();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignCoefficient3.getTree());

                    }
                    break;

            }

            SEMICOLON4=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement119); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class declareArray_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declareArray"
    // de\\gaalop\\maple\\parser\\MapleParser.g:41:1: declareArray : ( GAALOPARRAY LBRACKET var= IDENTIFIER RBRACKET ) -> ^( DECLAREARRAY $var) ;
    public final MapleParser.declareArray_return declareArray() throws RecognitionException {
        MapleParser.declareArray_return retval = new MapleParser.declareArray_return();
        retval.start = input.LT(1);
        int declareArray_StartIndex = input.index();
        Object root_0 = null;

        Token var=null;
        Token GAALOPARRAY5=null;
        Token LBRACKET6=null;
        Token RBRACKET7=null;

        Object var_tree=null;
        Object GAALOPARRAY5_tree=null;
        Object LBRACKET6_tree=null;
        Object RBRACKET7_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_GAALOPARRAY=new RewriteRuleTokenStream(adaptor,"token GAALOPARRAY");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:42:2: ( ( GAALOPARRAY LBRACKET var= IDENTIFIER RBRACKET ) -> ^( DECLAREARRAY $var) )
            // de\\gaalop\\maple\\parser\\MapleParser.g:42:4: ( GAALOPARRAY LBRACKET var= IDENTIFIER RBRACKET )
            {
            // de\\gaalop\\maple\\parser\\MapleParser.g:42:4: ( GAALOPARRAY LBRACKET var= IDENTIFIER RBRACKET )
            // de\\gaalop\\maple\\parser\\MapleParser.g:42:5: GAALOPARRAY LBRACKET var= IDENTIFIER RBRACKET
            {
            GAALOPARRAY5=(Token)match(input,GAALOPARRAY,FOLLOW_GAALOPARRAY_in_declareArray135); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_GAALOPARRAY.add(GAALOPARRAY5);

            LBRACKET6=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_declareArray137); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET6);

            var=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_declareArray141); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(var);

            RBRACKET7=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_declareArray143); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET7);


            }



            // AST REWRITE
            // elements: var
            // token labels: var
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_var=new RewriteRuleTokenStream(adaptor,"token var",var);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 42:51: -> ^( DECLAREARRAY $var)
            {
                // de\\gaalop\\maple\\parser\\MapleParser.g:42:54: ^( DECLAREARRAY $var)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DECLAREARRAY, "DECLAREARRAY"), root_1);

                adaptor.addChild(root_1, stream_var.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, declareArray_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "declareArray"

    public static class assignCoefficient_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignCoefficient"
    // de\\gaalop\\maple\\parser\\MapleParser.g:45:1: assignCoefficient : (mvName= IDENTIFIER LSBRACKET bladeIndex= DECIMAL_LITERAL RSBRACKET ASSIGNMENT value= additive_expression ) -> ^( COEFFICIENT $mvName $bladeIndex $value) ;
    public final MapleParser.assignCoefficient_return assignCoefficient() throws RecognitionException {
        MapleParser.assignCoefficient_return retval = new MapleParser.assignCoefficient_return();
        retval.start = input.LT(1);
        int assignCoefficient_StartIndex = input.index();
        Object root_0 = null;

        Token mvName=null;
        Token bladeIndex=null;
        Token LSBRACKET8=null;
        Token RSBRACKET9=null;
        Token ASSIGNMENT10=null;
        MapleParser.additive_expression_return value = null;


        Object mvName_tree=null;
        Object bladeIndex_tree=null;
        Object LSBRACKET8_tree=null;
        Object RSBRACKET9_tree=null;
        Object ASSIGNMENT10_tree=null;
        RewriteRuleTokenStream stream_LSBRACKET=new RewriteRuleTokenStream(adaptor,"token LSBRACKET");
        RewriteRuleTokenStream stream_ASSIGNMENT=new RewriteRuleTokenStream(adaptor,"token ASSIGNMENT");
        RewriteRuleTokenStream stream_RSBRACKET=new RewriteRuleTokenStream(adaptor,"token RSBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_DECIMAL_LITERAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL_LITERAL");
        RewriteRuleSubtreeStream stream_additive_expression=new RewriteRuleSubtreeStream(adaptor,"rule additive_expression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:46:2: ( (mvName= IDENTIFIER LSBRACKET bladeIndex= DECIMAL_LITERAL RSBRACKET ASSIGNMENT value= additive_expression ) -> ^( COEFFICIENT $mvName $bladeIndex $value) )
            // de\\gaalop\\maple\\parser\\MapleParser.g:46:4: (mvName= IDENTIFIER LSBRACKET bladeIndex= DECIMAL_LITERAL RSBRACKET ASSIGNMENT value= additive_expression )
            {
            // de\\gaalop\\maple\\parser\\MapleParser.g:46:4: (mvName= IDENTIFIER LSBRACKET bladeIndex= DECIMAL_LITERAL RSBRACKET ASSIGNMENT value= additive_expression )
            // de\\gaalop\\maple\\parser\\MapleParser.g:46:5: mvName= IDENTIFIER LSBRACKET bladeIndex= DECIMAL_LITERAL RSBRACKET ASSIGNMENT value= additive_expression
            {
            mvName=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_assignCoefficient167); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(mvName);

            LSBRACKET8=(Token)match(input,LSBRACKET,FOLLOW_LSBRACKET_in_assignCoefficient169); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LSBRACKET.add(LSBRACKET8);

            bladeIndex=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_assignCoefficient173); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DECIMAL_LITERAL.add(bladeIndex);

            RSBRACKET9=(Token)match(input,RSBRACKET,FOLLOW_RSBRACKET_in_assignCoefficient175); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RSBRACKET.add(RSBRACKET9);

            ASSIGNMENT10=(Token)match(input,ASSIGNMENT,FOLLOW_ASSIGNMENT_in_assignCoefficient177); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ASSIGNMENT.add(ASSIGNMENT10);

            pushFollow(FOLLOW_additive_expression_in_assignCoefficient181);
            value=additive_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_additive_expression.add(value.getTree());

            }



            // AST REWRITE
            // elements: bladeIndex, value, mvName
            // token labels: bladeIndex, mvName
            // rule labels: retval, value
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_bladeIndex=new RewriteRuleTokenStream(adaptor,"token bladeIndex",bladeIndex);
            RewriteRuleTokenStream stream_mvName=new RewriteRuleTokenStream(adaptor,"token mvName",mvName);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"token value",value!=null?value.tree:null);

            root_0 = (Object)adaptor.nil();
            // 46:108: -> ^( COEFFICIENT $mvName $bladeIndex $value)
            {
                // de\\gaalop\\maple\\parser\\MapleParser.g:46:111: ^( COEFFICIENT $mvName $bladeIndex $value)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COEFFICIENT, "COEFFICIENT"), root_1);

                adaptor.addChild(root_1, stream_mvName.nextNode());
                adaptor.addChild(root_1, stream_bladeIndex.nextNode());
                adaptor.addChild(root_1, stream_value.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, assignCoefficient_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "assignCoefficient"

    public static class coefficientExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "coefficientExpression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:49:1: coefficientExpression : coefficientBlade ( PLUS coefficientBlade )* ;
    public final MapleParser.coefficientExpression_return coefficientExpression() throws RecognitionException {
        MapleParser.coefficientExpression_return retval = new MapleParser.coefficientExpression_return();
        retval.start = input.LT(1);
        int coefficientExpression_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS12=null;
        MapleParser.coefficientBlade_return coefficientBlade11 = null;

        MapleParser.coefficientBlade_return coefficientBlade13 = null;


        Object PLUS12_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:50:2: ( coefficientBlade ( PLUS coefficientBlade )* )
            // de\\gaalop\\maple\\parser\\MapleParser.g:50:4: coefficientBlade ( PLUS coefficientBlade )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_coefficientBlade_in_coefficientExpression208);
            coefficientBlade11=coefficientBlade();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, coefficientBlade11.getTree());
            // de\\gaalop\\maple\\parser\\MapleParser.g:50:21: ( PLUS coefficientBlade )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==PLUS) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // de\\gaalop\\maple\\parser\\MapleParser.g:50:23: PLUS coefficientBlade
            	    {
            	    PLUS12=(Token)match(input,PLUS,FOLLOW_PLUS_in_coefficientExpression212); if (state.failed) return retval;
            	    pushFollow(FOLLOW_coefficientBlade_in_coefficientExpression215);
            	    coefficientBlade13=coefficientBlade();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, coefficientBlade13.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, coefficientExpression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "coefficientExpression"

    public static class coefficientBlade_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "coefficientBlade"
    // de\\gaalop\\maple\\parser\\MapleParser.g:53:1: coefficientBlade : mvName= IDENTIFIER LSBRACKET index= DECIMAL_LITERAL RSBRACKET STAR assignedBlade= blade -> ^( ASSIGNBLADE $index $assignedBlade) ;
    public final MapleParser.coefficientBlade_return coefficientBlade() throws RecognitionException {
        MapleParser.coefficientBlade_return retval = new MapleParser.coefficientBlade_return();
        retval.start = input.LT(1);
        int coefficientBlade_StartIndex = input.index();
        Object root_0 = null;

        Token mvName=null;
        Token index=null;
        Token LSBRACKET14=null;
        Token RSBRACKET15=null;
        Token STAR16=null;
        MapleParser.blade_return assignedBlade = null;


        Object mvName_tree=null;
        Object index_tree=null;
        Object LSBRACKET14_tree=null;
        Object RSBRACKET15_tree=null;
        Object STAR16_tree=null;
        RewriteRuleTokenStream stream_LSBRACKET=new RewriteRuleTokenStream(adaptor,"token LSBRACKET");
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_RSBRACKET=new RewriteRuleTokenStream(adaptor,"token RSBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_DECIMAL_LITERAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL_LITERAL");
        RewriteRuleSubtreeStream stream_blade=new RewriteRuleSubtreeStream(adaptor,"rule blade");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:54:2: (mvName= IDENTIFIER LSBRACKET index= DECIMAL_LITERAL RSBRACKET STAR assignedBlade= blade -> ^( ASSIGNBLADE $index $assignedBlade) )
            // de\\gaalop\\maple\\parser\\MapleParser.g:54:5: mvName= IDENTIFIER LSBRACKET index= DECIMAL_LITERAL RSBRACKET STAR assignedBlade= blade
            {
            mvName=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_coefficientBlade233); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(mvName);

            LSBRACKET14=(Token)match(input,LSBRACKET,FOLLOW_LSBRACKET_in_coefficientBlade235); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LSBRACKET.add(LSBRACKET14);

            index=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_coefficientBlade239); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DECIMAL_LITERAL.add(index);

            RSBRACKET15=(Token)match(input,RSBRACKET,FOLLOW_RSBRACKET_in_coefficientBlade241); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RSBRACKET.add(RSBRACKET15);

            STAR16=(Token)match(input,STAR,FOLLOW_STAR_in_coefficientBlade243); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STAR.add(STAR16);

            pushFollow(FOLLOW_blade_in_coefficientBlade247);
            assignedBlade=blade();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_blade.add(assignedBlade.getTree());


            // AST REWRITE
            // elements: assignedBlade, index
            // token labels: index
            // rule labels: retval, assignedBlade
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_index=new RewriteRuleTokenStream(adaptor,"token index",index);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_assignedBlade=new RewriteRuleSubtreeStream(adaptor,"token assignedBlade",assignedBlade!=null?assignedBlade.tree:null);

            root_0 = (Object)adaptor.nil();
            // 54:90: -> ^( ASSIGNBLADE $index $assignedBlade)
            {
                // de\\gaalop\\maple\\parser\\MapleParser.g:54:93: ^( ASSIGNBLADE $index $assignedBlade)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASSIGNBLADE, "ASSIGNBLADE"), root_1);

                adaptor.addChild(root_1, stream_index.nextNode());
                adaptor.addChild(root_1, stream_assignedBlade.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, coefficientBlade_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "coefficientBlade"

    public static class blade_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "blade"
    // de\\gaalop\\maple\\parser\\MapleParser.g:57:1: blade : primary_expression ;
    public final MapleParser.blade_return blade() throws RecognitionException {
        MapleParser.blade_return retval = new MapleParser.blade_return();
        retval.start = input.LT(1);
        int blade_StartIndex = input.index();
        Object root_0 = null;

        MapleParser.primary_expression_return primary_expression17 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:57:7: ( primary_expression )
            // de\\gaalop\\maple\\parser\\MapleParser.g:57:9: primary_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_primary_expression_in_blade270);
            primary_expression17=primary_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, primary_expression17.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, blade_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "blade"

    public static class additive_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additive_expression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:60:1: additive_expression : multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )* ;
    public final MapleParser.additive_expression_return additive_expression() throws RecognitionException {
        MapleParser.additive_expression_return retval = new MapleParser.additive_expression_return();
        retval.start = input.LT(1);
        int additive_expression_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS19=null;
        Token MINUS20=null;
        MapleParser.multiplicative_expression_return multiplicative_expression18 = null;

        MapleParser.multiplicative_expression_return multiplicative_expression21 = null;


        Object PLUS19_tree=null;
        Object MINUS20_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:61:2: ( multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )* )
            // de\\gaalop\\maple\\parser\\MapleParser.g:61:4: multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multiplicative_expression_in_additive_expression281);
            multiplicative_expression18=multiplicative_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicative_expression18.getTree());
            // de\\gaalop\\maple\\parser\\MapleParser.g:61:30: ( ( PLUS | MINUS ) multiplicative_expression )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==MINUS||LA5_0==PLUS) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // de\\gaalop\\maple\\parser\\MapleParser.g:61:32: ( PLUS | MINUS ) multiplicative_expression
            	    {
            	    // de\\gaalop\\maple\\parser\\MapleParser.g:61:32: ( PLUS | MINUS )
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==PLUS) ) {
            	        alt4=1;
            	    }
            	    else if ( (LA4_0==MINUS) ) {
            	        alt4=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 4, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // de\\gaalop\\maple\\parser\\MapleParser.g:61:33: PLUS
            	            {
            	            PLUS19=(Token)match(input,PLUS,FOLLOW_PLUS_in_additive_expression286); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            PLUS19_tree = (Object)adaptor.create(PLUS19);
            	            root_0 = (Object)adaptor.becomeRoot(PLUS19_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // de\\gaalop\\maple\\parser\\MapleParser.g:61:41: MINUS
            	            {
            	            MINUS20=(Token)match(input,MINUS,FOLLOW_MINUS_in_additive_expression291); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            MINUS20_tree = (Object)adaptor.create(MINUS20);
            	            root_0 = (Object)adaptor.becomeRoot(MINUS20_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_multiplicative_expression_in_additive_expression295);
            	    multiplicative_expression21=multiplicative_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicative_expression21.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, additive_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "additive_expression"

    public static class multiplicative_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicative_expression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:64:1: multiplicative_expression : ( outer_product_expression ( ( STAR | SLASH ) outer_product_expression )* | negation );
    public final MapleParser.multiplicative_expression_return multiplicative_expression() throws RecognitionException {
        MapleParser.multiplicative_expression_return retval = new MapleParser.multiplicative_expression_return();
        retval.start = input.LT(1);
        int multiplicative_expression_StartIndex = input.index();
        Object root_0 = null;

        Token STAR23=null;
        Token SLASH24=null;
        MapleParser.outer_product_expression_return outer_product_expression22 = null;

        MapleParser.outer_product_expression_return outer_product_expression25 = null;

        MapleParser.negation_return negation26 = null;


        Object STAR23_tree=null;
        Object SLASH24_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:65:2: ( outer_product_expression ( ( STAR | SLASH ) outer_product_expression )* | negation )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=IDENTIFIER && LA8_0<=DECIMAL_LITERAL)||LA8_0==FLOATING_POINT_LITERAL||LA8_0==LBRACKET) ) {
                alt8=1;
            }
            else if ( (LA8_0==MINUS) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:65:4: outer_product_expression ( ( STAR | SLASH ) outer_product_expression )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_outer_product_expression_in_multiplicative_expression309);
                    outer_product_expression22=outer_product_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, outer_product_expression22.getTree());
                    // de\\gaalop\\maple\\parser\\MapleParser.g:65:29: ( ( STAR | SLASH ) outer_product_expression )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0>=STAR && LA7_0<=SLASH)) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // de\\gaalop\\maple\\parser\\MapleParser.g:65:31: ( STAR | SLASH ) outer_product_expression
                    	    {
                    	    // de\\gaalop\\maple\\parser\\MapleParser.g:65:31: ( STAR | SLASH )
                    	    int alt6=2;
                    	    int LA6_0 = input.LA(1);

                    	    if ( (LA6_0==STAR) ) {
                    	        alt6=1;
                    	    }
                    	    else if ( (LA6_0==SLASH) ) {
                    	        alt6=2;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return retval;}
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 6, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt6) {
                    	        case 1 :
                    	            // de\\gaalop\\maple\\parser\\MapleParser.g:65:32: STAR
                    	            {
                    	            STAR23=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicative_expression314); if (state.failed) return retval;
                    	            if ( state.backtracking==0 ) {
                    	            STAR23_tree = (Object)adaptor.create(STAR23);
                    	            root_0 = (Object)adaptor.becomeRoot(STAR23_tree, root_0);
                    	            }

                    	            }
                    	            break;
                    	        case 2 :
                    	            // de\\gaalop\\maple\\parser\\MapleParser.g:65:40: SLASH
                    	            {
                    	            SLASH24=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicative_expression319); if (state.failed) return retval;
                    	            if ( state.backtracking==0 ) {
                    	            SLASH24_tree = (Object)adaptor.create(SLASH24);
                    	            root_0 = (Object)adaptor.becomeRoot(SLASH24_tree, root_0);
                    	            }

                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_outer_product_expression_in_multiplicative_expression323);
                    	    outer_product_expression25=outer_product_expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, outer_product_expression25.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:66:5: negation
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_negation_in_multiplicative_expression332);
                    negation26=negation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, negation26.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, multiplicative_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiplicative_expression"

    public static class outer_product_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "outer_product_expression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:69:1: outer_product_expression : modulo_expression ( WEDGE modulo_expression )* ;
    public final MapleParser.outer_product_expression_return outer_product_expression() throws RecognitionException {
        MapleParser.outer_product_expression_return retval = new MapleParser.outer_product_expression_return();
        retval.start = input.LT(1);
        int outer_product_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WEDGE28=null;
        MapleParser.modulo_expression_return modulo_expression27 = null;

        MapleParser.modulo_expression_return modulo_expression29 = null;


        Object WEDGE28_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:70:2: ( modulo_expression ( WEDGE modulo_expression )* )
            // de\\gaalop\\maple\\parser\\MapleParser.g:70:4: modulo_expression ( WEDGE modulo_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_modulo_expression_in_outer_product_expression343);
            modulo_expression27=modulo_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, modulo_expression27.getTree());
            // de\\gaalop\\maple\\parser\\MapleParser.g:70:22: ( WEDGE modulo_expression )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==WEDGE) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // de\\gaalop\\maple\\parser\\MapleParser.g:70:24: WEDGE modulo_expression
            	    {
            	    WEDGE28=(Token)match(input,WEDGE,FOLLOW_WEDGE_in_outer_product_expression347); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    WEDGE28_tree = (Object)adaptor.create(WEDGE28);
            	    root_0 = (Object)adaptor.becomeRoot(WEDGE28_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_modulo_expression_in_outer_product_expression350);
            	    modulo_expression29=modulo_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, modulo_expression29.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, outer_product_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "outer_product_expression"

    public static class modulo_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "modulo_expression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:73:1: modulo_expression : unary_expression ( MODULO unary_expression )* ;
    public final MapleParser.modulo_expression_return modulo_expression() throws RecognitionException {
        MapleParser.modulo_expression_return retval = new MapleParser.modulo_expression_return();
        retval.start = input.LT(1);
        int modulo_expression_StartIndex = input.index();
        Object root_0 = null;

        Token MODULO31=null;
        MapleParser.unary_expression_return unary_expression30 = null;

        MapleParser.unary_expression_return unary_expression32 = null;


        Object MODULO31_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:74:2: ( unary_expression ( MODULO unary_expression )* )
            // de\\gaalop\\maple\\parser\\MapleParser.g:74:4: unary_expression ( MODULO unary_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unary_expression_in_modulo_expression365);
            unary_expression30=unary_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression30.getTree());
            // de\\gaalop\\maple\\parser\\MapleParser.g:74:21: ( MODULO unary_expression )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==MODULO) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // de\\gaalop\\maple\\parser\\MapleParser.g:74:23: MODULO unary_expression
            	    {
            	    MODULO31=(Token)match(input,MODULO,FOLLOW_MODULO_in_modulo_expression369); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MODULO31_tree = (Object)adaptor.create(MODULO31);
            	    root_0 = (Object)adaptor.becomeRoot(MODULO31_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_unary_expression_in_modulo_expression372);
            	    unary_expression32=unary_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression32.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, modulo_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "modulo_expression"

    public static class negation_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "negation"
    // de\\gaalop\\maple\\parser\\MapleParser.g:77:1: negation : ( unary_operator value= multiplicative_expression ) -> ^( NEGATION $value) ;
    public final MapleParser.negation_return negation() throws RecognitionException {
        MapleParser.negation_return retval = new MapleParser.negation_return();
        retval.start = input.LT(1);
        int negation_StartIndex = input.index();
        Object root_0 = null;

        MapleParser.multiplicative_expression_return value = null;

        MapleParser.unary_operator_return unary_operator33 = null;


        RewriteRuleSubtreeStream stream_unary_operator=new RewriteRuleSubtreeStream(adaptor,"rule unary_operator");
        RewriteRuleSubtreeStream stream_multiplicative_expression=new RewriteRuleSubtreeStream(adaptor,"rule multiplicative_expression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:78:2: ( ( unary_operator value= multiplicative_expression ) -> ^( NEGATION $value) )
            // de\\gaalop\\maple\\parser\\MapleParser.g:78:4: ( unary_operator value= multiplicative_expression )
            {
            // de\\gaalop\\maple\\parser\\MapleParser.g:78:4: ( unary_operator value= multiplicative_expression )
            // de\\gaalop\\maple\\parser\\MapleParser.g:78:5: unary_operator value= multiplicative_expression
            {
            pushFollow(FOLLOW_unary_operator_in_negation387);
            unary_operator33=unary_operator();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_unary_operator.add(unary_operator33.getTree());
            pushFollow(FOLLOW_multiplicative_expression_in_negation391);
            value=multiplicative_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_multiplicative_expression.add(value.getTree());

            }



            // AST REWRITE
            // elements: value
            // token labels: 
            // rule labels: retval, value
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"token value",value!=null?value.tree:null);

            root_0 = (Object)adaptor.nil();
            // 78:53: -> ^( NEGATION $value)
            {
                // de\\gaalop\\maple\\parser\\MapleParser.g:78:56: ^( NEGATION $value)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NEGATION, "NEGATION"), root_1);

                adaptor.addChild(root_1, stream_value.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, negation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "negation"

    public static class unary_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_expression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:82:1: unary_expression : postfix_expression ;
    public final MapleParser.unary_expression_return unary_expression() throws RecognitionException {
        MapleParser.unary_expression_return retval = new MapleParser.unary_expression_return();
        retval.start = input.LT(1);
        int unary_expression_StartIndex = input.index();
        Object root_0 = null;

        MapleParser.postfix_expression_return postfix_expression34 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:83:2: ( postfix_expression )
            // de\\gaalop\\maple\\parser\\MapleParser.g:83:4: postfix_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_postfix_expression_in_unary_expression412);
            postfix_expression34=postfix_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, postfix_expression34.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, unary_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unary_expression"

    public static class postfix_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "postfix_expression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:86:1: postfix_expression : ( primary_expression | function_call );
    public final MapleParser.postfix_expression_return postfix_expression() throws RecognitionException {
        MapleParser.postfix_expression_return retval = new MapleParser.postfix_expression_return();
        retval.start = input.LT(1);
        int postfix_expression_StartIndex = input.index();
        Object root_0 = null;

        MapleParser.primary_expression_return primary_expression35 = null;

        MapleParser.function_call_return function_call36 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:87:2: ( primary_expression | function_call )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENTIFIER) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==EOF||(LA11_1>=MINUS && LA11_1<=LSBRACKET)||LA11_1==RBRACKET||(LA11_1>=COMMA && LA11_1<=WEDGE)) ) {
                    alt11=1;
                }
                else if ( (LA11_1==LBRACKET) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA11_0==DECIMAL_LITERAL||LA11_0==FLOATING_POINT_LITERAL||LA11_0==LBRACKET) ) {
                alt11=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:87:4: primary_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primary_expression_in_postfix_expression424);
                    primary_expression35=primary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primary_expression35.getTree());

                    }
                    break;
                case 2 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:88:4: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_postfix_expression429);
                    function_call36=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call36.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, postfix_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "postfix_expression"

    public static class function_call_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // de\\gaalop\\maple\\parser\\MapleParser.g:91:1: function_call : (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET ) -> ^( FUNCTION $name $args) ;
    public final MapleParser.function_call_return function_call() throws RecognitionException {
        MapleParser.function_call_return retval = new MapleParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;
        Token LBRACKET37=null;
        Token RBRACKET38=null;
        MapleParser.argument_expression_list_return args = null;


        Object name_tree=null;
        Object LBRACKET37_tree=null;
        Object RBRACKET38_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argument_expression_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_expression_list");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:92:2: ( (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET ) -> ^( FUNCTION $name $args) )
            // de\\gaalop\\maple\\parser\\MapleParser.g:92:4: (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET )
            {
            // de\\gaalop\\maple\\parser\\MapleParser.g:92:4: (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET )
            // de\\gaalop\\maple\\parser\\MapleParser.g:92:5: name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function_call443); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

            LBRACKET37=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_function_call445); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET37);

            pushFollow(FOLLOW_argument_expression_list_in_function_call449);
            args=argument_expression_list();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_argument_expression_list.add(args.getTree());
            RBRACKET38=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_function_call451); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET38);


            }



            // AST REWRITE
            // elements: name, args
            // token labels: name
            // rule labels: retval, args
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_args=new RewriteRuleSubtreeStream(adaptor,"token args",args!=null?args.tree:null);

            root_0 = (Object)adaptor.nil();
            // 93:2: -> ^( FUNCTION $name $args)
            {
                // de\\gaalop\\maple\\parser\\MapleParser.g:93:5: ^( FUNCTION $name $args)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION, "FUNCTION"), root_1);

                adaptor.addChild(root_1, stream_name.nextNode());
                adaptor.addChild(root_1, stream_args.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, function_call_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function_call"

    public static class argument_expression_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument_expression_list"
    // de\\gaalop\\maple\\parser\\MapleParser.g:96:1: argument_expression_list : additive_expression ( COMMA additive_expression )* ;
    public final MapleParser.argument_expression_list_return argument_expression_list() throws RecognitionException {
        MapleParser.argument_expression_list_return retval = new MapleParser.argument_expression_list_return();
        retval.start = input.LT(1);
        int argument_expression_list_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA40=null;
        MapleParser.additive_expression_return additive_expression39 = null;

        MapleParser.additive_expression_return additive_expression41 = null;


        Object COMMA40_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:97:2: ( additive_expression ( COMMA additive_expression )* )
            // de\\gaalop\\maple\\parser\\MapleParser.g:97:6: additive_expression ( COMMA additive_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_additive_expression_in_argument_expression_list479);
            additive_expression39=additive_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression39.getTree());
            // de\\gaalop\\maple\\parser\\MapleParser.g:97:26: ( COMMA additive_expression )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==COMMA) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // de\\gaalop\\maple\\parser\\MapleParser.g:97:28: COMMA additive_expression
            	    {
            	    COMMA40=(Token)match(input,COMMA,FOLLOW_COMMA_in_argument_expression_list483); if (state.failed) return retval;
            	    pushFollow(FOLLOW_additive_expression_in_argument_expression_list486);
            	    additive_expression41=additive_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression41.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, argument_expression_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "argument_expression_list"

    public static class unary_operator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_operator"
    // de\\gaalop\\maple\\parser\\MapleParser.g:100:1: unary_operator : MINUS ;
    public final MapleParser.unary_operator_return unary_operator() throws RecognitionException {
        MapleParser.unary_operator_return retval = new MapleParser.unary_operator_return();
        retval.start = input.LT(1);
        int unary_operator_StartIndex = input.index();
        Object root_0 = null;

        Token MINUS42=null;

        Object MINUS42_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:101:2: ( MINUS )
            // de\\gaalop\\maple\\parser\\MapleParser.g:101:4: MINUS
            {
            root_0 = (Object)adaptor.nil();

            MINUS42=(Token)match(input,MINUS,FOLLOW_MINUS_in_unary_operator500); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MINUS42_tree = (Object)adaptor.create(MINUS42);
            adaptor.addChild(root_0, MINUS42_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, unary_operator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unary_operator"

    public static class primary_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primary_expression"
    // de\\gaalop\\maple\\parser\\MapleParser.g:104:1: primary_expression : ( mv_coefficient | variable | constant | LBRACKET additive_expression RBRACKET );
    public final MapleParser.primary_expression_return primary_expression() throws RecognitionException {
        MapleParser.primary_expression_return retval = new MapleParser.primary_expression_return();
        retval.start = input.LT(1);
        int primary_expression_StartIndex = input.index();
        Object root_0 = null;

        Token LBRACKET46=null;
        Token RBRACKET48=null;
        MapleParser.mv_coefficient_return mv_coefficient43 = null;

        MapleParser.variable_return variable44 = null;

        MapleParser.constant_return constant45 = null;

        MapleParser.additive_expression_return additive_expression47 = null;


        Object LBRACKET46_tree=null;
        Object RBRACKET48_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:105:2: ( mv_coefficient | variable | constant | LBRACKET additive_expression RBRACKET )
            int alt13=4;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==LSBRACKET) ) {
                    alt13=1;
                }
                else if ( (LA13_1==EOF||LA13_1==MINUS||LA13_1==RBRACKET||(LA13_1>=COMMA && LA13_1<=WEDGE)) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }
                }
                break;
            case DECIMAL_LITERAL:
            case FLOATING_POINT_LITERAL:
                {
                alt13=3;
                }
                break;
            case LBRACKET:
                {
                alt13=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:105:4: mv_coefficient
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_mv_coefficient_in_primary_expression511);
                    mv_coefficient43=mv_coefficient();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, mv_coefficient43.getTree());

                    }
                    break;
                case 2 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:106:4: variable
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_variable_in_primary_expression516);
                    variable44=variable();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, variable44.getTree());

                    }
                    break;
                case 3 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:107:4: constant
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constant_in_primary_expression521);
                    constant45=constant();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, constant45.getTree());

                    }
                    break;
                case 4 :
                    // de\\gaalop\\maple\\parser\\MapleParser.g:108:4: LBRACKET additive_expression RBRACKET
                    {
                    root_0 = (Object)adaptor.nil();

                    LBRACKET46=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_primary_expression526); if (state.failed) return retval;
                    pushFollow(FOLLOW_additive_expression_in_primary_expression529);
                    additive_expression47=additive_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression47.getTree());
                    RBRACKET48=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_primary_expression531); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, primary_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "primary_expression"

    public static class mv_coefficient_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mv_coefficient"
    // de\\gaalop\\maple\\parser\\MapleParser.g:111:1: mv_coefficient : name= IDENTIFIER LSBRACKET index= DECIMAL_LITERAL RSBRACKET -> ^( MV_SUBSCRIPT $name $index) ;
    public final MapleParser.mv_coefficient_return mv_coefficient() throws RecognitionException {
        MapleParser.mv_coefficient_return retval = new MapleParser.mv_coefficient_return();
        retval.start = input.LT(1);
        int mv_coefficient_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;
        Token index=null;
        Token LSBRACKET49=null;
        Token RSBRACKET50=null;

        Object name_tree=null;
        Object index_tree=null;
        Object LSBRACKET49_tree=null;
        Object RSBRACKET50_tree=null;
        RewriteRuleTokenStream stream_LSBRACKET=new RewriteRuleTokenStream(adaptor,"token LSBRACKET");
        RewriteRuleTokenStream stream_RSBRACKET=new RewriteRuleTokenStream(adaptor,"token RSBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_DECIMAL_LITERAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL_LITERAL");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:112:2: (name= IDENTIFIER LSBRACKET index= DECIMAL_LITERAL RSBRACKET -> ^( MV_SUBSCRIPT $name $index) )
            // de\\gaalop\\maple\\parser\\MapleParser.g:112:4: name= IDENTIFIER LSBRACKET index= DECIMAL_LITERAL RSBRACKET
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_mv_coefficient545); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

            LSBRACKET49=(Token)match(input,LSBRACKET,FOLLOW_LSBRACKET_in_mv_coefficient547); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LSBRACKET.add(LSBRACKET49);

            index=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_mv_coefficient551); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DECIMAL_LITERAL.add(index);

            RSBRACKET50=(Token)match(input,RSBRACKET,FOLLOW_RSBRACKET_in_mv_coefficient553); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RSBRACKET.add(RSBRACKET50);



            // AST REWRITE
            // elements: index, name
            // token labels: index, name
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_index=new RewriteRuleTokenStream(adaptor,"token index",index);
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 112:62: -> ^( MV_SUBSCRIPT $name $index)
            {
                // de\\gaalop\\maple\\parser\\MapleParser.g:112:65: ^( MV_SUBSCRIPT $name $index)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MV_SUBSCRIPT, "MV_SUBSCRIPT"), root_1);

                adaptor.addChild(root_1, stream_name.nextNode());
                adaptor.addChild(root_1, stream_index.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, mv_coefficient_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "mv_coefficient"

    public static class variable_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variable"
    // de\\gaalop\\maple\\parser\\MapleParser.g:115:1: variable : name= IDENTIFIER -> ^( VARIABLE $name) ;
    public final MapleParser.variable_return variable() throws RecognitionException {
        MapleParser.variable_return retval = new MapleParser.variable_return();
        retval.start = input.LT(1);
        int variable_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;

        Object name_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:115:9: (name= IDENTIFIER -> ^( VARIABLE $name) )
            // de\\gaalop\\maple\\parser\\MapleParser.g:115:11: name= IDENTIFIER
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_variable577); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);



            // AST REWRITE
            // elements: name
            // token labels: name
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 115:27: -> ^( VARIABLE $name)
            {
                // de\\gaalop\\maple\\parser\\MapleParser.g:115:30: ^( VARIABLE $name)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                adaptor.addChild(root_1, stream_name.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, variable_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variable"

    public static class constant_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // de\\gaalop\\maple\\parser\\MapleParser.g:118:1: constant : ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL );
    public final MapleParser.constant_return constant() throws RecognitionException {
        MapleParser.constant_return retval = new MapleParser.constant_return();
        retval.start = input.LT(1);
        int constant_StartIndex = input.index();
        Object root_0 = null;

        Token set51=null;

        Object set51_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // de\\gaalop\\maple\\parser\\MapleParser.g:119:5: ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL )
            // de\\gaalop\\maple\\parser\\MapleParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set51=(Token)input.LT(1);
            if ( input.LA(1)==DECIMAL_LITERAL||input.LA(1)==FLOATING_POINT_LITERAL ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set51));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, constant_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constant"

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_program97 = new BitSet(new long[]{0x0000000000000092L});
    public static final BitSet FOLLOW_declareArray_in_statement111 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_assignCoefficient_in_statement115 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_SEMICOLON_in_statement119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GAALOPARRAY_in_declareArray135 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LBRACKET_in_declareArray137 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENTIFIER_in_declareArray141 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RBRACKET_in_declareArray143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_assignCoefficient167 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LSBRACKET_in_assignCoefficient169 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_assignCoefficient173 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RSBRACKET_in_assignCoefficient175 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ASSIGNMENT_in_assignCoefficient177 = new BitSet(new long[]{0x0000000000004D80L});
    public static final BitSet FOLLOW_additive_expression_in_assignCoefficient181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_coefficientBlade_in_coefficientExpression208 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_PLUS_in_coefficientExpression212 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_coefficientBlade_in_coefficientExpression215 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_coefficientBlade233 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LSBRACKET_in_coefficientBlade235 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_coefficientBlade239 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RSBRACKET_in_coefficientBlade241 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_STAR_in_coefficientBlade243 = new BitSet(new long[]{0x0000000000004580L});
    public static final BitSet FOLLOW_blade_in_coefficientBlade247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_expression_in_blade270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression281 = new BitSet(new long[]{0x0000000000080802L});
    public static final BitSet FOLLOW_PLUS_in_additive_expression286 = new BitSet(new long[]{0x0000000000004D80L});
    public static final BitSet FOLLOW_MINUS_in_additive_expression291 = new BitSet(new long[]{0x0000000000004D80L});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression295 = new BitSet(new long[]{0x0000000000080802L});
    public static final BitSet FOLLOW_outer_product_expression_in_multiplicative_expression309 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_STAR_in_multiplicative_expression314 = new BitSet(new long[]{0x0000000000004580L});
    public static final BitSet FOLLOW_SLASH_in_multiplicative_expression319 = new BitSet(new long[]{0x0000000000004580L});
    public static final BitSet FOLLOW_outer_product_expression_in_multiplicative_expression323 = new BitSet(new long[]{0x0000000000300002L});
    public static final BitSet FOLLOW_negation_in_multiplicative_expression332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modulo_expression_in_outer_product_expression343 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_WEDGE_in_outer_product_expression347 = new BitSet(new long[]{0x0000000000004580L});
    public static final BitSet FOLLOW_modulo_expression_in_outer_product_expression350 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_unary_expression_in_modulo_expression365 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_MODULO_in_modulo_expression369 = new BitSet(new long[]{0x0000000000004580L});
    public static final BitSet FOLLOW_unary_expression_in_modulo_expression372 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_unary_operator_in_negation387 = new BitSet(new long[]{0x0000000000004D80L});
    public static final BitSet FOLLOW_multiplicative_expression_in_negation391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_unary_expression412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_expression_in_postfix_expression424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_postfix_expression429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function_call443 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LBRACKET_in_function_call445 = new BitSet(new long[]{0x0000000000004D80L});
    public static final BitSet FOLLOW_argument_expression_list_in_function_call449 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RBRACKET_in_function_call451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additive_expression_in_argument_expression_list479 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_COMMA_in_argument_expression_list483 = new BitSet(new long[]{0x0000000000004D80L});
    public static final BitSet FOLLOW_additive_expression_in_argument_expression_list486 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_MINUS_in_unary_operator500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mv_coefficient_in_primary_expression511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_primary_expression516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_primary_expression521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_primary_expression526 = new BitSet(new long[]{0x0000000000004D80L});
    public static final BitSet FOLLOW_additive_expression_in_primary_expression529 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RBRACKET_in_primary_expression531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_mv_coefficient545 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LSBRACKET_in_mv_coefficient547 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_mv_coefficient551 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RSBRACKET_in_mv_coefficient553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_variable577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});

}