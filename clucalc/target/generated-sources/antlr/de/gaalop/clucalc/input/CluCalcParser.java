// $ANTLR 3.2 Sep 23, 2009 12:02:23 C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g 2010-05-06 11:38:43

  package de.gaalop.clucalc.input;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class CluCalcParser extends Parser {
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
    public static final int ELSEIF=58;
    public static final int UNEQUAL=48;
    public static final int LSBRACKET=31;
    public static final int COLON=44;
    public static final int DOUBLE_AND=46;
    public static final int WEDGE=41;
    public static final int WS=21;
    public static final int NEGATION=55;
    public static final int RANGE_LITERAL=8;
    public static final int BLOCK=57;
    public static final int OUTPUT_LITERAL=9;
    public static final int REVERSE=37;
    public static final int MACRO=59;
    public static final int DECIMAL_LITERAL=4;

    // delegates
    // delegators


        public CluCalcParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public CluCalcParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[78+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return CluCalcParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g"; }


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


    public static class script_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "script"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:41:1: script : ( statement )* EOF ;
    public final CluCalcParser.script_return script() throws RecognitionException {
        CluCalcParser.script_return retval = new CluCalcParser.script_return();
        retval.start = input.LT(1);
        int script_StartIndex = input.index();
        Object root_0 = null;

        Token EOF2=null;
        CluCalcParser.statement_return statement1 = null;


        Object EOF2_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:41:9: ( ( statement )* EOF )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:42:3: ( statement )* EOF
            {
            root_0 = (Object)adaptor.nil();

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:42:3: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==DECIMAL_LITERAL||LA1_0==FLOATING_POINT_LITERAL||LA1_0==MINUS||LA1_0==IF||(LA1_0>=LOOP && LA1_0<=BREAK)||(LA1_0>=IDENTIFIER && LA1_0<=ARGUMENT_PREFIX)||LA1_0==PRAGMA||LA1_0==STAR||LA1_0==LBRACKET||LA1_0==CLBRACKET||LA1_0==REVERSE||LA1_0==SEMICOLON||(LA1_0>=QUESTIONMARK && LA1_0<=COLON)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_script107);
            	    statement1=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_script110); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 1, script_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "script"

    public static class float_literal_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "float_literal"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:45:1: float_literal : ( MINUS )? FLOATING_POINT_LITERAL ;
    public final CluCalcParser.float_literal_return float_literal() throws RecognitionException {
        CluCalcParser.float_literal_return retval = new CluCalcParser.float_literal_return();
        retval.start = input.LT(1);
        int float_literal_StartIndex = input.index();
        Object root_0 = null;

        Token MINUS3=null;
        Token FLOATING_POINT_LITERAL4=null;

        Object MINUS3_tree=null;
        Object FLOATING_POINT_LITERAL4_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:45:14: ( ( MINUS )? FLOATING_POINT_LITERAL )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:46:3: ( MINUS )? FLOATING_POINT_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:46:3: ( MINUS )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==MINUS) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:0:0: MINUS
                    {
                    MINUS3=(Token)match(input,MINUS,FOLLOW_MINUS_in_float_literal124); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS3_tree = (Object)adaptor.create(MINUS3);
                    adaptor.addChild(root_0, MINUS3_tree);
                    }

                    }
                    break;

            }

            FLOATING_POINT_LITERAL4=(Token)match(input,FLOATING_POINT_LITERAL,FOLLOW_FLOATING_POINT_LITERAL_in_float_literal127); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FLOATING_POINT_LITERAL4_tree = (Object)adaptor.create(FLOATING_POINT_LITERAL4);
            adaptor.addChild(root_0, FLOATING_POINT_LITERAL4_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 2, float_literal_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "float_literal"

    public static class pragma_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pragma"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:52:1: pragma : ( PRAGMA RANGE_LITERAL float_literal LESS_OR_EQUAL IDENTIFIER LESS_OR_EQUAL float_literal | PRAGMA OUTPUT_LITERAL IDENTIFIER );
    public final CluCalcParser.pragma_return pragma() throws RecognitionException {
        CluCalcParser.pragma_return retval = new CluCalcParser.pragma_return();
        retval.start = input.LT(1);
        int pragma_StartIndex = input.index();
        Object root_0 = null;

        Token PRAGMA5=null;
        Token RANGE_LITERAL6=null;
        Token LESS_OR_EQUAL8=null;
        Token IDENTIFIER9=null;
        Token LESS_OR_EQUAL10=null;
        Token PRAGMA12=null;
        Token OUTPUT_LITERAL13=null;
        Token IDENTIFIER14=null;
        CluCalcParser.float_literal_return float_literal7 = null;

        CluCalcParser.float_literal_return float_literal11 = null;


        Object PRAGMA5_tree=null;
        Object RANGE_LITERAL6_tree=null;
        Object LESS_OR_EQUAL8_tree=null;
        Object IDENTIFIER9_tree=null;
        Object LESS_OR_EQUAL10_tree=null;
        Object PRAGMA12_tree=null;
        Object OUTPUT_LITERAL13_tree=null;
        Object IDENTIFIER14_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:53:3: ( PRAGMA RANGE_LITERAL float_literal LESS_OR_EQUAL IDENTIFIER LESS_OR_EQUAL float_literal | PRAGMA OUTPUT_LITERAL IDENTIFIER )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==PRAGMA) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==RANGE_LITERAL) ) {
                    alt3=1;
                }
                else if ( (LA3_1==OUTPUT_LITERAL) ) {
                    alt3=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:53:5: PRAGMA RANGE_LITERAL float_literal LESS_OR_EQUAL IDENTIFIER LESS_OR_EQUAL float_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    PRAGMA5=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma142); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRAGMA5_tree = (Object)adaptor.create(PRAGMA5);
                    adaptor.addChild(root_0, PRAGMA5_tree);
                    }
                    RANGE_LITERAL6=(Token)match(input,RANGE_LITERAL,FOLLOW_RANGE_LITERAL_in_pragma144); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RANGE_LITERAL6_tree = (Object)adaptor.create(RANGE_LITERAL6);
                    adaptor.addChild(root_0, RANGE_LITERAL6_tree);
                    }
                    pushFollow(FOLLOW_float_literal_in_pragma146);
                    float_literal7=float_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, float_literal7.getTree());
                    LESS_OR_EQUAL8=(Token)match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma148); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESS_OR_EQUAL8_tree = (Object)adaptor.create(LESS_OR_EQUAL8);
                    adaptor.addChild(root_0, LESS_OR_EQUAL8_tree);
                    }
                    IDENTIFIER9=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma150); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER9_tree = (Object)adaptor.create(IDENTIFIER9);
                    adaptor.addChild(root_0, IDENTIFIER9_tree);
                    }
                    LESS_OR_EQUAL10=(Token)match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma152); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESS_OR_EQUAL10_tree = (Object)adaptor.create(LESS_OR_EQUAL10);
                    adaptor.addChild(root_0, LESS_OR_EQUAL10_tree);
                    }
                    pushFollow(FOLLOW_float_literal_in_pragma154);
                    float_literal11=float_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, float_literal11.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:54:5: PRAGMA OUTPUT_LITERAL IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    PRAGMA12=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma160); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRAGMA12_tree = (Object)adaptor.create(PRAGMA12);
                    adaptor.addChild(root_0, PRAGMA12_tree);
                    }
                    OUTPUT_LITERAL13=(Token)match(input,OUTPUT_LITERAL,FOLLOW_OUTPUT_LITERAL_in_pragma162); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    OUTPUT_LITERAL13_tree = (Object)adaptor.create(OUTPUT_LITERAL13);
                    adaptor.addChild(root_0, OUTPUT_LITERAL13_tree);
                    }
                    IDENTIFIER14=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma164); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER14_tree = (Object)adaptor.create(IDENTIFIER14);
                    adaptor.addChild(root_0, IDENTIFIER14_tree);
                    }

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
            if ( state.backtracking>0 ) { memoize(input, 3, pragma_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pragma"

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:60:1: expression : ( lvalue EQUALS expression -> ^( EQUALS lvalue expression ) | additive_expression | logical_or_expression );
    public final CluCalcParser.expression_return expression() throws RecognitionException {
        CluCalcParser.expression_return retval = new CluCalcParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        Token EQUALS16=null;
        CluCalcParser.lvalue_return lvalue15 = null;

        CluCalcParser.expression_return expression17 = null;

        CluCalcParser.additive_expression_return additive_expression18 = null;

        CluCalcParser.logical_or_expression_return logical_or_expression19 = null;


        Object EQUALS16_tree=null;
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_lvalue=new RewriteRuleSubtreeStream(adaptor,"rule lvalue");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:61:3: ( lvalue EQUALS expression -> ^( EQUALS lvalue expression ) | additive_expression | logical_or_expression )
            int alt4=3;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:61:5: lvalue EQUALS expression
                    {
                    pushFollow(FOLLOW_lvalue_in_expression179);
                    lvalue15=lvalue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_lvalue.add(lvalue15.getTree());
                    EQUALS16=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_expression181); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EQUALS.add(EQUALS16);

                    pushFollow(FOLLOW_expression_in_expression183);
                    expression17=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression17.getTree());


                    // AST REWRITE
                    // elements: expression, EQUALS, lvalue
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 61:30: -> ^( EQUALS lvalue expression )
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:61:33: ^( EQUALS lvalue expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_EQUALS.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_lvalue.nextTree());
                        adaptor.addChild(root_1, stream_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:62:5: additive_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_additive_expression_in_expression199);
                    additive_expression18=additive_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression18.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:63:5: logical_or_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_logical_or_expression_in_expression205);
                    logical_or_expression19=logical_or_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logical_or_expression19.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 4, expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class argument_expression_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument_expression_list"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:66:1: argument_expression_list : expression ( COMMA expression )* ;
    public final CluCalcParser.argument_expression_list_return argument_expression_list() throws RecognitionException {
        CluCalcParser.argument_expression_list_return retval = new CluCalcParser.argument_expression_list_return();
        retval.start = input.LT(1);
        int argument_expression_list_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA21=null;
        CluCalcParser.expression_return expression20 = null;

        CluCalcParser.expression_return expression22 = null;


        Object COMMA21_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:67:3: ( expression ( COMMA expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:67:7: expression ( COMMA expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_argument_expression_list220);
            expression20=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression20.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:67:18: ( COMMA expression )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:67:20: COMMA expression
            	    {
            	    COMMA21=(Token)match(input,COMMA,FOLLOW_COMMA_in_argument_expression_list224); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_argument_expression_list227);
            	    expression22=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression22.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 5, argument_expression_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "argument_expression_list"

    public static class lvalue_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lvalue"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:70:1: lvalue : unary_expression ;
    public final CluCalcParser.lvalue_return lvalue() throws RecognitionException {
        CluCalcParser.lvalue_return retval = new CluCalcParser.lvalue_return();
        retval.start = input.LT(1);
        int lvalue_StartIndex = input.index();
        Object root_0 = null;

        CluCalcParser.unary_expression_return unary_expression23 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:71:3: ( unary_expression )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:71:5: unary_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unary_expression_in_lvalue245);
            unary_expression23=unary_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression23.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 6, lvalue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "lvalue"

    public static class logical_or_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "logical_or_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:74:1: logical_or_expression : logical_and_expression ( DOUBLE_BAR logical_and_expression )* ;
    public final CluCalcParser.logical_or_expression_return logical_or_expression() throws RecognitionException {
        CluCalcParser.logical_or_expression_return retval = new CluCalcParser.logical_or_expression_return();
        retval.start = input.LT(1);
        int logical_or_expression_StartIndex = input.index();
        Object root_0 = null;

        Token DOUBLE_BAR25=null;
        CluCalcParser.logical_and_expression_return logical_and_expression24 = null;

        CluCalcParser.logical_and_expression_return logical_and_expression26 = null;


        Object DOUBLE_BAR25_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:75:3: ( logical_and_expression ( DOUBLE_BAR logical_and_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:75:5: logical_and_expression ( DOUBLE_BAR logical_and_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_logical_and_expression_in_logical_or_expression258);
            logical_and_expression24=logical_and_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logical_and_expression24.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:75:28: ( DOUBLE_BAR logical_and_expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==DOUBLE_BAR) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:75:29: DOUBLE_BAR logical_and_expression
            	    {
            	    DOUBLE_BAR25=(Token)match(input,DOUBLE_BAR,FOLLOW_DOUBLE_BAR_in_logical_or_expression261); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DOUBLE_BAR25_tree = (Object)adaptor.create(DOUBLE_BAR25);
            	    root_0 = (Object)adaptor.becomeRoot(DOUBLE_BAR25_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_logical_and_expression_in_logical_or_expression264);
            	    logical_and_expression26=logical_and_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, logical_and_expression26.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
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
            if ( state.backtracking>0 ) { memoize(input, 7, logical_or_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "logical_or_expression"

    public static class logical_and_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "logical_and_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:78:1: logical_and_expression : equality_expression ( DOUBLE_AND equality_expression )* ;
    public final CluCalcParser.logical_and_expression_return logical_and_expression() throws RecognitionException {
        CluCalcParser.logical_and_expression_return retval = new CluCalcParser.logical_and_expression_return();
        retval.start = input.LT(1);
        int logical_and_expression_StartIndex = input.index();
        Object root_0 = null;

        Token DOUBLE_AND28=null;
        CluCalcParser.equality_expression_return equality_expression27 = null;

        CluCalcParser.equality_expression_return equality_expression29 = null;


        Object DOUBLE_AND28_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:79:3: ( equality_expression ( DOUBLE_AND equality_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:79:5: equality_expression ( DOUBLE_AND equality_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_equality_expression_in_logical_and_expression279);
            equality_expression27=equality_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equality_expression27.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:79:25: ( DOUBLE_AND equality_expression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==DOUBLE_AND) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:79:26: DOUBLE_AND equality_expression
            	    {
            	    DOUBLE_AND28=(Token)match(input,DOUBLE_AND,FOLLOW_DOUBLE_AND_in_logical_and_expression282); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DOUBLE_AND28_tree = (Object)adaptor.create(DOUBLE_AND28);
            	    root_0 = (Object)adaptor.becomeRoot(DOUBLE_AND28_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_equality_expression_in_logical_and_expression285);
            	    equality_expression29=equality_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equality_expression29.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
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
            if ( state.backtracking>0 ) { memoize(input, 8, logical_and_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "logical_and_expression"

    public static class equality_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equality_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:82:1: equality_expression : relational_expression ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )* ;
    public final CluCalcParser.equality_expression_return equality_expression() throws RecognitionException {
        CluCalcParser.equality_expression_return retval = new CluCalcParser.equality_expression_return();
        retval.start = input.LT(1);
        int equality_expression_StartIndex = input.index();
        Object root_0 = null;

        Token DOUBLE_EQUALS31=null;
        Token UNEQUAL32=null;
        CluCalcParser.relational_expression_return relational_expression30 = null;

        CluCalcParser.relational_expression_return relational_expression33 = null;


        Object DOUBLE_EQUALS31_tree=null;
        Object UNEQUAL32_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:83:3: ( relational_expression ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:83:5: relational_expression ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_relational_expression_in_equality_expression300);
            relational_expression30=relational_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relational_expression30.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:83:27: ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>=DOUBLE_EQUALS && LA9_0<=UNEQUAL)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:83:28: ( DOUBLE_EQUALS | UNEQUAL ) relational_expression
            	    {
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:83:28: ( DOUBLE_EQUALS | UNEQUAL )
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==DOUBLE_EQUALS) ) {
            	        alt8=1;
            	    }
            	    else if ( (LA8_0==UNEQUAL) ) {
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
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:83:29: DOUBLE_EQUALS
            	            {
            	            DOUBLE_EQUALS31=(Token)match(input,DOUBLE_EQUALS,FOLLOW_DOUBLE_EQUALS_in_equality_expression304); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            DOUBLE_EQUALS31_tree = (Object)adaptor.create(DOUBLE_EQUALS31);
            	            root_0 = (Object)adaptor.becomeRoot(DOUBLE_EQUALS31_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:83:46: UNEQUAL
            	            {
            	            UNEQUAL32=(Token)match(input,UNEQUAL,FOLLOW_UNEQUAL_in_equality_expression309); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            UNEQUAL32_tree = (Object)adaptor.create(UNEQUAL32);
            	            root_0 = (Object)adaptor.becomeRoot(UNEQUAL32_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_relational_expression_in_equality_expression313);
            	    relational_expression33=relational_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relational_expression33.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 9, equality_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "equality_expression"

    public static class relational_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relational_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:86:1: relational_expression : additive_expression ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )* ;
    public final CluCalcParser.relational_expression_return relational_expression() throws RecognitionException {
        CluCalcParser.relational_expression_return retval = new CluCalcParser.relational_expression_return();
        retval.start = input.LT(1);
        int relational_expression_StartIndex = input.index();
        Object root_0 = null;

        Token LESS35=null;
        Token GREATER36=null;
        Token LESS_OR_EQUAL37=null;
        Token GREATER_OR_EQUAL38=null;
        CluCalcParser.additive_expression_return additive_expression34 = null;

        CluCalcParser.additive_expression_return additive_expression39 = null;


        Object LESS35_tree=null;
        Object GREATER36_tree=null;
        Object LESS_OR_EQUAL37_tree=null;
        Object GREATER_OR_EQUAL38_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:3: ( additive_expression ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:5: additive_expression ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_additive_expression_in_relational_expression328);
            additive_expression34=additive_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression34.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:25: ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>=LESS && LA11_0<=GREATER_OR_EQUAL)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:26: ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression
            	    {
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:26: ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL )
            	    int alt10=4;
            	    switch ( input.LA(1) ) {
            	    case LESS:
            	        {
            	        alt10=1;
            	        }
            	        break;
            	    case GREATER:
            	        {
            	        alt10=2;
            	        }
            	        break;
            	    case LESS_OR_EQUAL:
            	        {
            	        alt10=3;
            	        }
            	        break;
            	    case GREATER_OR_EQUAL:
            	        {
            	        alt10=4;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt10) {
            	        case 1 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:27: LESS
            	            {
            	            LESS35=(Token)match(input,LESS,FOLLOW_LESS_in_relational_expression332); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            LESS35_tree = (Object)adaptor.create(LESS35);
            	            root_0 = (Object)adaptor.becomeRoot(LESS35_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:35: GREATER
            	            {
            	            GREATER36=(Token)match(input,GREATER,FOLLOW_GREATER_in_relational_expression337); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            GREATER36_tree = (Object)adaptor.create(GREATER36);
            	            root_0 = (Object)adaptor.becomeRoot(GREATER36_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:46: LESS_OR_EQUAL
            	            {
            	            LESS_OR_EQUAL37=(Token)match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_relational_expression342); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            LESS_OR_EQUAL37_tree = (Object)adaptor.create(LESS_OR_EQUAL37);
            	            root_0 = (Object)adaptor.becomeRoot(LESS_OR_EQUAL37_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:87:63: GREATER_OR_EQUAL
            	            {
            	            GREATER_OR_EQUAL38=(Token)match(input,GREATER_OR_EQUAL,FOLLOW_GREATER_OR_EQUAL_in_relational_expression347); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            GREATER_OR_EQUAL38_tree = (Object)adaptor.create(GREATER_OR_EQUAL38);
            	            root_0 = (Object)adaptor.becomeRoot(GREATER_OR_EQUAL38_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_additive_expression_in_relational_expression351);
            	    additive_expression39=additive_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression39.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
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
            if ( state.backtracking>0 ) { memoize(input, 10, relational_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relational_expression"

    public static class additive_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additive_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:90:1: additive_expression : multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )* ;
    public final CluCalcParser.additive_expression_return additive_expression() throws RecognitionException {
        CluCalcParser.additive_expression_return retval = new CluCalcParser.additive_expression_return();
        retval.start = input.LT(1);
        int additive_expression_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS41=null;
        Token MINUS42=null;
        CluCalcParser.multiplicative_expression_return multiplicative_expression40 = null;

        CluCalcParser.multiplicative_expression_return multiplicative_expression43 = null;


        Object PLUS41_tree=null;
        Object MINUS42_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:91:3: ( multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:91:5: multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multiplicative_expression_in_additive_expression366);
            multiplicative_expression40=multiplicative_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicative_expression40.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:91:31: ( ( PLUS | MINUS ) multiplicative_expression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==MINUS||LA13_0==PLUS) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:91:33: ( PLUS | MINUS ) multiplicative_expression
            	    {
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:91:33: ( PLUS | MINUS )
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==PLUS) ) {
            	        alt12=1;
            	    }
            	    else if ( (LA12_0==MINUS) ) {
            	        alt12=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 12, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:91:34: PLUS
            	            {
            	            PLUS41=(Token)match(input,PLUS,FOLLOW_PLUS_in_additive_expression371); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            PLUS41_tree = (Object)adaptor.create(PLUS41);
            	            root_0 = (Object)adaptor.becomeRoot(PLUS41_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:91:42: MINUS
            	            {
            	            MINUS42=(Token)match(input,MINUS,FOLLOW_MINUS_in_additive_expression376); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            MINUS42_tree = (Object)adaptor.create(MINUS42);
            	            root_0 = (Object)adaptor.becomeRoot(MINUS42_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_multiplicative_expression_in_additive_expression380);
            	    multiplicative_expression43=multiplicative_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicative_expression43.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
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
            if ( state.backtracking>0 ) { memoize(input, 11, additive_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "additive_expression"

    public static class multiplicative_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicative_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:94:1: multiplicative_expression : outer_product_expression ( ( STAR | SLASH ) outer_product_expression )* ;
    public final CluCalcParser.multiplicative_expression_return multiplicative_expression() throws RecognitionException {
        CluCalcParser.multiplicative_expression_return retval = new CluCalcParser.multiplicative_expression_return();
        retval.start = input.LT(1);
        int multiplicative_expression_StartIndex = input.index();
        Object root_0 = null;

        Token STAR45=null;
        Token SLASH46=null;
        CluCalcParser.outer_product_expression_return outer_product_expression44 = null;

        CluCalcParser.outer_product_expression_return outer_product_expression47 = null;


        Object STAR45_tree=null;
        Object SLASH46_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:95:3: ( outer_product_expression ( ( STAR | SLASH ) outer_product_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:95:5: outer_product_expression ( ( STAR | SLASH ) outer_product_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_outer_product_expression_in_multiplicative_expression396);
            outer_product_expression44=outer_product_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, outer_product_expression44.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:95:30: ( ( STAR | SLASH ) outer_product_expression )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=STAR && LA15_0<=SLASH)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:95:32: ( STAR | SLASH ) outer_product_expression
            	    {
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:95:32: ( STAR | SLASH )
            	    int alt14=2;
            	    int LA14_0 = input.LA(1);

            	    if ( (LA14_0==STAR) ) {
            	        alt14=1;
            	    }
            	    else if ( (LA14_0==SLASH) ) {
            	        alt14=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 14, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt14) {
            	        case 1 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:95:33: STAR
            	            {
            	            STAR45=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicative_expression401); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            STAR45_tree = (Object)adaptor.create(STAR45);
            	            root_0 = (Object)adaptor.becomeRoot(STAR45_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:95:41: SLASH
            	            {
            	            SLASH46=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicative_expression406); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            SLASH46_tree = (Object)adaptor.create(SLASH46);
            	            root_0 = (Object)adaptor.becomeRoot(SLASH46_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_outer_product_expression_in_multiplicative_expression410);
            	    outer_product_expression47=outer_product_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, outer_product_expression47.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
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
            if ( state.backtracking>0 ) { memoize(input, 12, multiplicative_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiplicative_expression"

    public static class outer_product_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "outer_product_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:98:1: outer_product_expression : inner_product_expression ( WEDGE inner_product_expression )* ;
    public final CluCalcParser.outer_product_expression_return outer_product_expression() throws RecognitionException {
        CluCalcParser.outer_product_expression_return retval = new CluCalcParser.outer_product_expression_return();
        retval.start = input.LT(1);
        int outer_product_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WEDGE49=null;
        CluCalcParser.inner_product_expression_return inner_product_expression48 = null;

        CluCalcParser.inner_product_expression_return inner_product_expression50 = null;


        Object WEDGE49_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:99:3: ( inner_product_expression ( WEDGE inner_product_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:99:5: inner_product_expression ( WEDGE inner_product_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_inner_product_expression_in_outer_product_expression426);
            inner_product_expression48=inner_product_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, inner_product_expression48.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:99:30: ( WEDGE inner_product_expression )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==WEDGE) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:99:32: WEDGE inner_product_expression
            	    {
            	    WEDGE49=(Token)match(input,WEDGE,FOLLOW_WEDGE_in_outer_product_expression430); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    WEDGE49_tree = (Object)adaptor.create(WEDGE49);
            	    root_0 = (Object)adaptor.becomeRoot(WEDGE49_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_inner_product_expression_in_outer_product_expression433);
            	    inner_product_expression50=inner_product_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, inner_product_expression50.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
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
            if ( state.backtracking>0 ) { memoize(input, 13, outer_product_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "outer_product_expression"

    public static class inner_product_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inner_product_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:102:1: inner_product_expression : modulo_expression ( DOT modulo_expression )* ;
    public final CluCalcParser.inner_product_expression_return inner_product_expression() throws RecognitionException {
        CluCalcParser.inner_product_expression_return retval = new CluCalcParser.inner_product_expression_return();
        retval.start = input.LT(1);
        int inner_product_expression_StartIndex = input.index();
        Object root_0 = null;

        Token DOT52=null;
        CluCalcParser.modulo_expression_return modulo_expression51 = null;

        CluCalcParser.modulo_expression_return modulo_expression53 = null;


        Object DOT52_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:103:3: ( modulo_expression ( DOT modulo_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:103:5: modulo_expression ( DOT modulo_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_modulo_expression_in_inner_product_expression449);
            modulo_expression51=modulo_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, modulo_expression51.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:103:23: ( DOT modulo_expression )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==DOT) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:103:25: DOT modulo_expression
            	    {
            	    DOT52=(Token)match(input,DOT,FOLLOW_DOT_in_inner_product_expression453); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DOT52_tree = (Object)adaptor.create(DOT52);
            	    root_0 = (Object)adaptor.becomeRoot(DOT52_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_modulo_expression_in_inner_product_expression456);
            	    modulo_expression53=modulo_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, modulo_expression53.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
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
            if ( state.backtracking>0 ) { memoize(input, 14, inner_product_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "inner_product_expression"

    public static class modulo_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "modulo_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:106:1: modulo_expression : unary_expression ( MODULO unary_expression )* ;
    public final CluCalcParser.modulo_expression_return modulo_expression() throws RecognitionException {
        CluCalcParser.modulo_expression_return retval = new CluCalcParser.modulo_expression_return();
        retval.start = input.LT(1);
        int modulo_expression_StartIndex = input.index();
        Object root_0 = null;

        Token MODULO55=null;
        CluCalcParser.unary_expression_return unary_expression54 = null;

        CluCalcParser.unary_expression_return unary_expression56 = null;


        Object MODULO55_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:107:3: ( unary_expression ( MODULO unary_expression )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:107:5: unary_expression ( MODULO unary_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unary_expression_in_modulo_expression472);
            unary_expression54=unary_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression54.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:107:22: ( MODULO unary_expression )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==MODULO) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:107:24: MODULO unary_expression
            	    {
            	    MODULO55=(Token)match(input,MODULO,FOLLOW_MODULO_in_modulo_expression476); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MODULO55_tree = (Object)adaptor.create(MODULO55);
            	    root_0 = (Object)adaptor.becomeRoot(MODULO55_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_unary_expression_in_modulo_expression479);
            	    unary_expression56=unary_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression56.getTree());

            	    }
            	    break;

            	default :
            	    break loop18;
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
            if ( state.backtracking>0 ) { memoize(input, 15, modulo_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "modulo_expression"

    public static class unary_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:110:1: unary_expression : ( postfix_expression | STAR operand= unary_expression -> ^( DUAL $operand) | MINUS operand= unary_expression -> ^( NEGATION $operand) | REVERSE operand= unary_expression -> ^( REVERSE $operand) );
    public final CluCalcParser.unary_expression_return unary_expression() throws RecognitionException {
        CluCalcParser.unary_expression_return retval = new CluCalcParser.unary_expression_return();
        retval.start = input.LT(1);
        int unary_expression_StartIndex = input.index();
        Object root_0 = null;

        Token STAR58=null;
        Token MINUS59=null;
        Token REVERSE60=null;
        CluCalcParser.unary_expression_return operand = null;

        CluCalcParser.postfix_expression_return postfix_expression57 = null;


        Object STAR58_tree=null;
        Object MINUS59_tree=null;
        Object REVERSE60_tree=null;
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_REVERSE=new RewriteRuleTokenStream(adaptor,"token REVERSE");
        RewriteRuleSubtreeStream stream_unary_expression=new RewriteRuleSubtreeStream(adaptor,"rule unary_expression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:111:3: ( postfix_expression | STAR operand= unary_expression -> ^( DUAL $operand) | MINUS operand= unary_expression -> ^( NEGATION $operand) | REVERSE operand= unary_expression -> ^( REVERSE $operand) )
            int alt19=4;
            switch ( input.LA(1) ) {
            case DECIMAL_LITERAL:
            case FLOATING_POINT_LITERAL:
            case IDENTIFIER:
            case ARGUMENT_PREFIX:
            case LBRACKET:
                {
                alt19=1;
                }
                break;
            case STAR:
                {
                alt19=2;
                }
                break;
            case MINUS:
                {
                alt19=3;
                }
                break;
            case REVERSE:
                {
                alt19=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:111:5: postfix_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_postfix_expression_in_unary_expression495);
                    postfix_expression57=postfix_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, postfix_expression57.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:112:5: STAR operand= unary_expression
                    {
                    STAR58=(Token)match(input,STAR,FOLLOW_STAR_in_unary_expression501); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STAR.add(STAR58);

                    pushFollow(FOLLOW_unary_expression_in_unary_expression505);
                    operand=unary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unary_expression.add(operand.getTree());


                    // AST REWRITE
                    // elements: operand
                    // token labels: 
                    // rule labels: retval, operand
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_operand=new RewriteRuleSubtreeStream(adaptor,"rule operand",operand!=null?operand.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 112:35: -> ^( DUAL $operand)
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:112:38: ^( DUAL $operand)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DUAL, "DUAL"), root_1);

                        adaptor.addChild(root_1, stream_operand.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:113:5: MINUS operand= unary_expression
                    {
                    MINUS59=(Token)match(input,MINUS,FOLLOW_MINUS_in_unary_expression520); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_MINUS.add(MINUS59);

                    pushFollow(FOLLOW_unary_expression_in_unary_expression524);
                    operand=unary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unary_expression.add(operand.getTree());


                    // AST REWRITE
                    // elements: operand
                    // token labels: 
                    // rule labels: retval, operand
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_operand=new RewriteRuleSubtreeStream(adaptor,"rule operand",operand!=null?operand.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 113:36: -> ^( NEGATION $operand)
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:113:39: ^( NEGATION $operand)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NEGATION, "NEGATION"), root_1);

                        adaptor.addChild(root_1, stream_operand.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:114:5: REVERSE operand= unary_expression
                    {
                    REVERSE60=(Token)match(input,REVERSE,FOLLOW_REVERSE_in_unary_expression539); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_REVERSE.add(REVERSE60);

                    pushFollow(FOLLOW_unary_expression_in_unary_expression543);
                    operand=unary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unary_expression.add(operand.getTree());


                    // AST REWRITE
                    // elements: operand, REVERSE
                    // token labels: 
                    // rule labels: retval, operand
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_operand=new RewriteRuleSubtreeStream(adaptor,"rule operand",operand!=null?operand.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 114:38: -> ^( REVERSE $operand)
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:114:41: ^( REVERSE $operand)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_REVERSE.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_operand.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
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
            if ( state.backtracking>0 ) { memoize(input, 16, unary_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unary_expression"

    public static class postfix_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "postfix_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:117:1: postfix_expression : ( primary_expression | function_call );
    public final CluCalcParser.postfix_expression_return postfix_expression() throws RecognitionException {
        CluCalcParser.postfix_expression_return retval = new CluCalcParser.postfix_expression_return();
        retval.start = input.LT(1);
        int postfix_expression_StartIndex = input.index();
        Object root_0 = null;

        CluCalcParser.primary_expression_return primary_expression61 = null;

        CluCalcParser.function_call_return function_call62 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:118:3: ( primary_expression | function_call )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==IDENTIFIER) ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1==LBRACKET) ) {
                    alt20=2;
                }
                else if ( (LA20_1==EOF||LA20_1==MINUS||(LA20_1>=EQUALS && LA20_1<=MODULO)||LA20_1==RBRACKET||LA20_1==CRBRACKET||(LA20_1>=SEMICOLON && LA20_1<=DOT)||(LA20_1>=DOUBLE_BAR && LA20_1<=GREATER_OR_EQUAL)) ) {
                    alt20=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA20_0==DECIMAL_LITERAL||LA20_0==FLOATING_POINT_LITERAL||LA20_0==ARGUMENT_PREFIX||LA20_0==LBRACKET) ) {
                alt20=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:118:5: primary_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primary_expression_in_postfix_expression567);
                    primary_expression61=primary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primary_expression61.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:119:5: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_postfix_expression573);
                    function_call62=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call62.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 17, postfix_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "postfix_expression"

    public static class function_call_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:122:1: function_call : (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET ) -> ^( FUNCTION $name $args) ;
    public final CluCalcParser.function_call_return function_call() throws RecognitionException {
        CluCalcParser.function_call_return retval = new CluCalcParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;
        Token LBRACKET63=null;
        Token RBRACKET64=null;
        CluCalcParser.argument_expression_list_return args = null;


        Object name_tree=null;
        Object LBRACKET63_tree=null;
        Object RBRACKET64_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argument_expression_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_expression_list");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:123:3: ( (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET ) -> ^( FUNCTION $name $args) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:123:5: (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET )
            {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:123:5: (name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:123:6: name= IDENTIFIER LBRACKET args= argument_expression_list RBRACKET
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function_call589); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

            LBRACKET63=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_function_call591); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET63);

            pushFollow(FOLLOW_argument_expression_list_in_function_call595);
            args=argument_expression_list();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_argument_expression_list.add(args.getTree());
            RBRACKET64=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_function_call597); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET64);


            }



            // AST REWRITE
            // elements: name, args
            // token labels: name
            // rule labels: retval, args
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_args=new RewriteRuleSubtreeStream(adaptor,"rule args",args!=null?args.tree:null);

            root_0 = (Object)adaptor.nil();
            // 124:3: -> ^( FUNCTION $name $args)
            {
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:124:6: ^( FUNCTION $name $args)
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
            if ( state.backtracking>0 ) { memoize(input, 18, function_call_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function_call"

    public static class primary_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primary_expression"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:127:1: primary_expression : ( IDENTIFIER | function_argument | constant | LBRACKET expression RBRACKET );
    public final CluCalcParser.primary_expression_return primary_expression() throws RecognitionException {
        CluCalcParser.primary_expression_return retval = new CluCalcParser.primary_expression_return();
        retval.start = input.LT(1);
        int primary_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER65=null;
        Token LBRACKET68=null;
        Token RBRACKET70=null;
        CluCalcParser.function_argument_return function_argument66 = null;

        CluCalcParser.constant_return constant67 = null;

        CluCalcParser.expression_return expression69 = null;


        Object IDENTIFIER65_tree=null;
        Object LBRACKET68_tree=null;
        Object RBRACKET70_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:128:3: ( IDENTIFIER | function_argument | constant | LBRACKET expression RBRACKET )
            int alt21=4;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt21=1;
                }
                break;
            case ARGUMENT_PREFIX:
                {
                alt21=2;
                }
                break;
            case DECIMAL_LITERAL:
            case FLOATING_POINT_LITERAL:
                {
                alt21=3;
                }
                break;
            case LBRACKET:
                {
                alt21=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:128:5: IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    IDENTIFIER65=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_primary_expression625); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER65_tree = (Object)adaptor.create(IDENTIFIER65);
                    adaptor.addChild(root_0, IDENTIFIER65_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:129:5: function_argument
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_argument_in_primary_expression631);
                    function_argument66=function_argument();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_argument66.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:130:5: constant
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constant_in_primary_expression638);
                    constant67=constant();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, constant67.getTree());

                    }
                    break;
                case 4 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:131:5: LBRACKET expression RBRACKET
                    {
                    root_0 = (Object)adaptor.nil();

                    LBRACKET68=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_primary_expression644); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_primary_expression647);
                    expression69=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression69.getTree());
                    RBRACKET70=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_primary_expression649); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 19, primary_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "primary_expression"

    public static class constant_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:133:1: constant : ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL );
    public final CluCalcParser.constant_return constant() throws RecognitionException {
        CluCalcParser.constant_return retval = new CluCalcParser.constant_return();
        retval.start = input.LT(1);
        int constant_StartIndex = input.index();
        Object root_0 = null;

        Token set71=null;

        Object set71_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:134:5: ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set71=(Token)input.LT(1);
            if ( input.LA(1)==DECIMAL_LITERAL||input.LA(1)==FLOATING_POINT_LITERAL ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set71));
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
            if ( state.backtracking>0 ) { memoize(input, 20, constant_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constant"

    public static class function_argument_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_argument"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:138:1: function_argument : ARGUMENT_PREFIX index= DECIMAL_LITERAL RBRACKET -> ^( ARGUMENT $index) ;
    public final CluCalcParser.function_argument_return function_argument() throws RecognitionException {
        CluCalcParser.function_argument_return retval = new CluCalcParser.function_argument_return();
        retval.start = input.LT(1);
        int function_argument_StartIndex = input.index();
        Object root_0 = null;

        Token index=null;
        Token ARGUMENT_PREFIX72=null;
        Token RBRACKET73=null;

        Object index_tree=null;
        Object ARGUMENT_PREFIX72_tree=null;
        Object RBRACKET73_tree=null;
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_ARGUMENT_PREFIX=new RewriteRuleTokenStream(adaptor,"token ARGUMENT_PREFIX");
        RewriteRuleTokenStream stream_DECIMAL_LITERAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL_LITERAL");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:139:3: ( ARGUMENT_PREFIX index= DECIMAL_LITERAL RBRACKET -> ^( ARGUMENT $index) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:139:5: ARGUMENT_PREFIX index= DECIMAL_LITERAL RBRACKET
            {
            ARGUMENT_PREFIX72=(Token)match(input,ARGUMENT_PREFIX,FOLLOW_ARGUMENT_PREFIX_in_function_argument695); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ARGUMENT_PREFIX.add(ARGUMENT_PREFIX72);

            index=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_function_argument699); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DECIMAL_LITERAL.add(index);

            RBRACKET73=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_function_argument701); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET73);



            // AST REWRITE
            // elements: index
            // token labels: index
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_index=new RewriteRuleTokenStream(adaptor,"token index",index);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 139:52: -> ^( ARGUMENT $index)
            {
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:139:55: ^( ARGUMENT $index)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARGUMENT, "ARGUMENT"), root_1);

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
            if ( state.backtracking>0 ) { memoize(input, 21, function_argument_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function_argument"

    public static class statement_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement_list"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:145:1: statement_list : ( statement )+ ;
    public final CluCalcParser.statement_list_return statement_list() throws RecognitionException {
        CluCalcParser.statement_list_return retval = new CluCalcParser.statement_list_return();
        retval.start = input.LT(1);
        int statement_list_StartIndex = input.index();
        Object root_0 = null;

        CluCalcParser.statement_return statement74 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:146:3: ( ( statement )+ )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:146:5: ( statement )+
            {
            root_0 = (Object)adaptor.nil();

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:146:5: ( statement )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==DECIMAL_LITERAL||LA22_0==FLOATING_POINT_LITERAL||LA22_0==MINUS||LA22_0==IF||(LA22_0>=LOOP && LA22_0<=BREAK)||(LA22_0>=IDENTIFIER && LA22_0<=ARGUMENT_PREFIX)||LA22_0==PRAGMA||LA22_0==STAR||LA22_0==LBRACKET||LA22_0==CLBRACKET||LA22_0==REVERSE||LA22_0==SEMICOLON||(LA22_0>=QUESTIONMARK && LA22_0<=COLON)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statement_list725);
            	    statement74=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement74.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt22 >= 1 ) break loop22;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(22, input);
                        throw eee;
                }
                cnt22++;
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
            if ( state.backtracking>0 ) { memoize(input, 22, statement_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "statement_list"

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:152:1: statement : ( expression_statement | procedure_call | macro_definition | draw_mode | block | if_statement | loop | BREAK | pragma );
    public final CluCalcParser.statement_return statement() throws RecognitionException {
        CluCalcParser.statement_return retval = new CluCalcParser.statement_return();
        retval.start = input.LT(1);
        int statement_StartIndex = input.index();
        Object root_0 = null;

        Token BREAK82=null;
        CluCalcParser.expression_statement_return expression_statement75 = null;

        CluCalcParser.procedure_call_return procedure_call76 = null;

        CluCalcParser.macro_definition_return macro_definition77 = null;

        CluCalcParser.draw_mode_return draw_mode78 = null;

        CluCalcParser.block_return block79 = null;

        CluCalcParser.if_statement_return if_statement80 = null;

        CluCalcParser.loop_return loop81 = null;

        CluCalcParser.pragma_return pragma83 = null;


        Object BREAK82_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:153:3: ( expression_statement | procedure_call | macro_definition | draw_mode | block | if_statement | loop | BREAK | pragma )
            int alt23=9;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:153:5: expression_statement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_statement_in_statement743);
                    expression_statement75=expression_statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression_statement75.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:154:5: procedure_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_procedure_call_in_statement749);
                    procedure_call76=procedure_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, procedure_call76.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:155:5: macro_definition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_macro_definition_in_statement755);
                    macro_definition77=macro_definition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, macro_definition77.getTree());

                    }
                    break;
                case 4 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:156:5: draw_mode
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_draw_mode_in_statement761);
                    draw_mode78=draw_mode();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, draw_mode78.getTree());

                    }
                    break;
                case 5 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:157:5: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_statement767);
                    block79=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block79.getTree());

                    }
                    break;
                case 6 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:158:5: if_statement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_if_statement_in_statement773);
                    if_statement80=if_statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_statement80.getTree());

                    }
                    break;
                case 7 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:159:5: loop
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_loop_in_statement779);
                    loop81=loop();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, loop81.getTree());

                    }
                    break;
                case 8 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:160:5: BREAK
                    {
                    root_0 = (Object)adaptor.nil();

                    BREAK82=(Token)match(input,BREAK,FOLLOW_BREAK_in_statement785); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BREAK82_tree = (Object)adaptor.create(BREAK82);
                    adaptor.addChild(root_0, BREAK82_tree);
                    }

                    }
                    break;
                case 9 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:161:5: pragma
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pragma_in_statement791);
                    pragma83=pragma();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pragma83.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 23, statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class macro_definition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "macro_definition"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:164:1: macro_definition : id= IDENTIFIER EQUALS CLBRACKET ( statement )* (e= additive_expression )? CRBRACKET -> ^( MACRO $id ( statement )* ( $e)? ) ;
    public final CluCalcParser.macro_definition_return macro_definition() throws RecognitionException {
        CluCalcParser.macro_definition_return retval = new CluCalcParser.macro_definition_return();
        retval.start = input.LT(1);
        int macro_definition_StartIndex = input.index();
        Object root_0 = null;

        Token id=null;
        Token EQUALS84=null;
        Token CLBRACKET85=null;
        Token CRBRACKET87=null;
        CluCalcParser.additive_expression_return e = null;

        CluCalcParser.statement_return statement86 = null;


        Object id_tree=null;
        Object EQUALS84_tree=null;
        Object CLBRACKET85_tree=null;
        Object CRBRACKET87_tree=null;
        RewriteRuleTokenStream stream_CRBRACKET=new RewriteRuleTokenStream(adaptor,"token CRBRACKET");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_CLBRACKET=new RewriteRuleTokenStream(adaptor,"token CLBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_additive_expression=new RewriteRuleSubtreeStream(adaptor,"rule additive_expression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:3: (id= IDENTIFIER EQUALS CLBRACKET ( statement )* (e= additive_expression )? CRBRACKET -> ^( MACRO $id ( statement )* ( $e)? ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:5: id= IDENTIFIER EQUALS CLBRACKET ( statement )* (e= additive_expression )? CRBRACKET
            {
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_macro_definition809); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(id);

            EQUALS84=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_macro_definition811); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EQUALS.add(EQUALS84);

            CLBRACKET85=(Token)match(input,CLBRACKET,FOLLOW_CLBRACKET_in_macro_definition813); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CLBRACKET.add(CLBRACKET85);

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:36: ( statement )*
            loop24:
            do {
                int alt24=2;
                alt24 = dfa24.predict(input);
                switch (alt24) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_macro_definition815);
            	    statement86=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement86.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:48: (e= additive_expression )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==DECIMAL_LITERAL||LA25_0==FLOATING_POINT_LITERAL||LA25_0==MINUS||(LA25_0>=IDENTIFIER && LA25_0<=ARGUMENT_PREFIX)||LA25_0==STAR||LA25_0==LBRACKET||LA25_0==REVERSE) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:0:0: e= additive_expression
                    {
                    pushFollow(FOLLOW_additive_expression_in_macro_definition820);
                    e=additive_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_additive_expression.add(e.getTree());

                    }
                    break;

            }

            CRBRACKET87=(Token)match(input,CRBRACKET,FOLLOW_CRBRACKET_in_macro_definition823); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CRBRACKET.add(CRBRACKET87);



            // AST REWRITE
            // elements: id, statement, e
            // token labels: id
            // rule labels: retval, e
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_id=new RewriteRuleTokenStream(adaptor,"token id",id);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);

            root_0 = (Object)adaptor.nil();
            // 165:80: -> ^( MACRO $id ( statement )* ( $e)? )
            {
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:83: ^( MACRO $id ( statement )* ( $e)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MACRO, "MACRO"), root_1);

                adaptor.addChild(root_1, stream_id.nextNode());
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:95: ( statement )*
                while ( stream_statement.hasNext() ) {
                    adaptor.addChild(root_1, stream_statement.nextTree());

                }
                stream_statement.reset();
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:106: ( $e)?
                if ( stream_e.hasNext() ) {
                    adaptor.addChild(root_1, stream_e.nextTree());

                }
                stream_e.reset();

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
            if ( state.backtracking>0 ) { memoize(input, 24, macro_definition_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "macro_definition"

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "block"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:168:1: block : CLBRACKET ( statement )* CRBRACKET -> ^( BLOCK ( statement )* ) ;
    public final CluCalcParser.block_return block() throws RecognitionException {
        CluCalcParser.block_return retval = new CluCalcParser.block_return();
        retval.start = input.LT(1);
        int block_StartIndex = input.index();
        Object root_0 = null;

        Token CLBRACKET88=null;
        Token CRBRACKET90=null;
        CluCalcParser.statement_return statement89 = null;


        Object CLBRACKET88_tree=null;
        Object CRBRACKET90_tree=null;
        RewriteRuleTokenStream stream_CRBRACKET=new RewriteRuleTokenStream(adaptor,"token CRBRACKET");
        RewriteRuleTokenStream stream_CLBRACKET=new RewriteRuleTokenStream(adaptor,"token CLBRACKET");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:169:3: ( CLBRACKET ( statement )* CRBRACKET -> ^( BLOCK ( statement )* ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:169:5: CLBRACKET ( statement )* CRBRACKET
            {
            CLBRACKET88=(Token)match(input,CLBRACKET,FOLLOW_CLBRACKET_in_block854); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CLBRACKET.add(CLBRACKET88);

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:169:15: ( statement )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==DECIMAL_LITERAL||LA26_0==FLOATING_POINT_LITERAL||LA26_0==MINUS||LA26_0==IF||(LA26_0>=LOOP && LA26_0<=BREAK)||(LA26_0>=IDENTIFIER && LA26_0<=ARGUMENT_PREFIX)||LA26_0==PRAGMA||LA26_0==STAR||LA26_0==LBRACKET||LA26_0==CLBRACKET||LA26_0==REVERSE||LA26_0==SEMICOLON||(LA26_0>=QUESTIONMARK && LA26_0<=COLON)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block856);
            	    statement89=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement89.getTree());

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            CRBRACKET90=(Token)match(input,CRBRACKET,FOLLOW_CRBRACKET_in_block859); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CRBRACKET.add(CRBRACKET90);



            // AST REWRITE
            // elements: statement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 169:36: -> ^( BLOCK ( statement )* )
            {
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:169:39: ^( BLOCK ( statement )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCK, "BLOCK"), root_1);

                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:169:47: ( statement )*
                while ( stream_statement.hasNext() ) {
                    adaptor.addChild(root_1, stream_statement.nextTree());

                }
                stream_statement.reset();

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
            if ( state.backtracking>0 ) { memoize(input, 25, block_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "block"

    public static class draw_mode_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "draw_mode"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:172:1: draw_mode : ( ( COLON IPNS ) -> ^( IPNS ) | ( COLON OPNS ) -> ^( OPNS ) );
    public final CluCalcParser.draw_mode_return draw_mode() throws RecognitionException {
        CluCalcParser.draw_mode_return retval = new CluCalcParser.draw_mode_return();
        retval.start = input.LT(1);
        int draw_mode_StartIndex = input.index();
        Object root_0 = null;

        Token COLON91=null;
        Token IPNS92=null;
        Token COLON93=null;
        Token OPNS94=null;

        Object COLON91_tree=null;
        Object IPNS92_tree=null;
        Object COLON93_tree=null;
        Object OPNS94_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_IPNS=new RewriteRuleTokenStream(adaptor,"token IPNS");
        RewriteRuleTokenStream stream_OPNS=new RewriteRuleTokenStream(adaptor,"token OPNS");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:173:3: ( ( COLON IPNS ) -> ^( IPNS ) | ( COLON OPNS ) -> ^( OPNS ) )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==COLON) ) {
                int LA27_1 = input.LA(2);

                if ( (LA27_1==IPNS) ) {
                    alt27=1;
                }
                else if ( (LA27_1==OPNS) ) {
                    alt27=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:173:5: ( COLON IPNS )
                    {
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:173:5: ( COLON IPNS )
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:173:6: COLON IPNS
                    {
                    COLON91=(Token)match(input,COLON,FOLLOW_COLON_in_draw_mode884); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_COLON.add(COLON91);

                    IPNS92=(Token)match(input,IPNS,FOLLOW_IPNS_in_draw_mode886); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IPNS.add(IPNS92);


                    }



                    // AST REWRITE
                    // elements: IPNS
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 173:18: -> ^( IPNS )
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:173:21: ^( IPNS )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_IPNS.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:174:5: ( COLON OPNS )
                    {
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:174:5: ( COLON OPNS )
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:174:6: COLON OPNS
                    {
                    COLON93=(Token)match(input,COLON,FOLLOW_COLON_in_draw_mode900); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_COLON.add(COLON93);

                    OPNS94=(Token)match(input,OPNS,FOLLOW_OPNS_in_draw_mode902); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_OPNS.add(OPNS94);


                    }



                    // AST REWRITE
                    // elements: OPNS
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 174:18: -> ^( OPNS )
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:174:21: ^( OPNS )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_OPNS.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
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
            if ( state.backtracking>0 ) { memoize(input, 26, draw_mode_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "draw_mode"

    public static class procedure_call_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure_call"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:177:1: procedure_call : (name= IDENTIFIER LBRACKET RBRACKET ) -> ^( PROCEDURE $name) ;
    public final CluCalcParser.procedure_call_return procedure_call() throws RecognitionException {
        CluCalcParser.procedure_call_return retval = new CluCalcParser.procedure_call_return();
        retval.start = input.LT(1);
        int procedure_call_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;
        Token LBRACKET95=null;
        Token RBRACKET96=null;

        Object name_tree=null;
        Object LBRACKET95_tree=null;
        Object RBRACKET96_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:178:3: ( (name= IDENTIFIER LBRACKET RBRACKET ) -> ^( PROCEDURE $name) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:178:5: (name= IDENTIFIER LBRACKET RBRACKET )
            {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:178:5: (name= IDENTIFIER LBRACKET RBRACKET )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:178:6: name= IDENTIFIER LBRACKET RBRACKET
            {
            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_procedure_call927); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

            LBRACKET95=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_procedure_call929); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET95);

            RBRACKET96=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_procedure_call931); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET96);


            }



            // AST REWRITE
            // elements: name
            // token labels: name
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 178:41: -> ^( PROCEDURE $name)
            {
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:178:44: ^( PROCEDURE $name)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROCEDURE, "PROCEDURE"), root_1);

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
            if ( state.backtracking>0 ) { memoize(input, 27, procedure_call_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "procedure_call"

    public static class expression_statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression_statement"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:181:1: expression_statement : ( SEMICOLON | ( QUESTIONMARK | COLON )? expression SEMICOLON );
    public final CluCalcParser.expression_statement_return expression_statement() throws RecognitionException {
        CluCalcParser.expression_statement_return retval = new CluCalcParser.expression_statement_return();
        retval.start = input.LT(1);
        int expression_statement_StartIndex = input.index();
        Object root_0 = null;

        Token SEMICOLON97=null;
        Token QUESTIONMARK98=null;
        Token COLON99=null;
        Token SEMICOLON101=null;
        CluCalcParser.expression_return expression100 = null;


        Object SEMICOLON97_tree=null;
        Object QUESTIONMARK98_tree=null;
        Object COLON99_tree=null;
        Object SEMICOLON101_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:182:3: ( SEMICOLON | ( QUESTIONMARK | COLON )? expression SEMICOLON )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==SEMICOLON) ) {
                alt29=1;
            }
            else if ( (LA29_0==DECIMAL_LITERAL||LA29_0==FLOATING_POINT_LITERAL||LA29_0==MINUS||(LA29_0>=IDENTIFIER && LA29_0<=ARGUMENT_PREFIX)||LA29_0==STAR||LA29_0==LBRACKET||LA29_0==REVERSE||(LA29_0>=QUESTIONMARK && LA29_0<=COLON)) ) {
                alt29=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:182:5: SEMICOLON
                    {
                    root_0 = (Object)adaptor.nil();

                    SEMICOLON97=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_expression_statement956); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:183:5: ( QUESTIONMARK | COLON )? expression SEMICOLON
                    {
                    root_0 = (Object)adaptor.nil();

                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:183:5: ( QUESTIONMARK | COLON )?
                    int alt28=3;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==QUESTIONMARK) ) {
                        alt28=1;
                    }
                    else if ( (LA28_0==COLON) ) {
                        alt28=2;
                    }
                    switch (alt28) {
                        case 1 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:183:6: QUESTIONMARK
                            {
                            QUESTIONMARK98=(Token)match(input,QUESTIONMARK,FOLLOW_QUESTIONMARK_in_expression_statement964); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            QUESTIONMARK98_tree = (Object)adaptor.create(QUESTIONMARK98);
                            root_0 = (Object)adaptor.becomeRoot(QUESTIONMARK98_tree, root_0);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:183:22: COLON
                            {
                            COLON99=(Token)match(input,COLON,FOLLOW_COLON_in_expression_statement969); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COLON99_tree = (Object)adaptor.create(COLON99);
                            root_0 = (Object)adaptor.becomeRoot(COLON99_tree, root_0);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_expression_in_expression_statement974);
                    expression100=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression100.getTree());
                    SEMICOLON101=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_expression_statement976); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 28, expression_statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expression_statement"

    public static class if_statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_statement"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:186:1: if_statement : IF LBRACKET condition= logical_or_expression RBRACKET then_part= statement ( else_part )? -> ^( IF $condition $then_part ( else_part )? ) ;
    public final CluCalcParser.if_statement_return if_statement() throws RecognitionException {
        CluCalcParser.if_statement_return retval = new CluCalcParser.if_statement_return();
        retval.start = input.LT(1);
        int if_statement_StartIndex = input.index();
        Object root_0 = null;

        Token IF102=null;
        Token LBRACKET103=null;
        Token RBRACKET104=null;
        CluCalcParser.logical_or_expression_return condition = null;

        CluCalcParser.statement_return then_part = null;

        CluCalcParser.else_part_return else_part105 = null;


        Object IF102_tree=null;
        Object LBRACKET103_tree=null;
        Object RBRACKET104_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_logical_or_expression=new RewriteRuleSubtreeStream(adaptor,"rule logical_or_expression");
        RewriteRuleSubtreeStream stream_else_part=new RewriteRuleSubtreeStream(adaptor,"rule else_part");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:187:3: ( IF LBRACKET condition= logical_or_expression RBRACKET then_part= statement ( else_part )? -> ^( IF $condition $then_part ( else_part )? ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:187:5: IF LBRACKET condition= logical_or_expression RBRACKET then_part= statement ( else_part )?
            {
            IF102=(Token)match(input,IF,FOLLOW_IF_in_if_statement993); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IF.add(IF102);

            LBRACKET103=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_if_statement995); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET103);

            pushFollow(FOLLOW_logical_or_expression_in_if_statement999);
            condition=logical_or_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_logical_or_expression.add(condition.getTree());
            RBRACKET104=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_if_statement1001); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET104);

            pushFollow(FOLLOW_statement_in_if_statement1014);
            then_part=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(then_part.getTree());
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:189:5: ( else_part )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ELSE) ) {
                int LA30_1 = input.LA(2);

                if ( (synpred46_CluCalcParser()) ) {
                    alt30=1;
                }
            }
            switch (alt30) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:189:6: else_part
                    {
                    pushFollow(FOLLOW_else_part_in_if_statement1059);
                    else_part105=else_part();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_else_part.add(else_part105.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: IF, condition, else_part, then_part
            // token labels: 
            // rule labels: retval, then_part, condition
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_then_part=new RewriteRuleSubtreeStream(adaptor,"rule then_part",then_part!=null?then_part.tree:null);
            RewriteRuleSubtreeStream stream_condition=new RewriteRuleSubtreeStream(adaptor,"rule condition",condition!=null?condition.tree:null);

            root_0 = (Object)adaptor.nil();
            // 190:5: -> ^( IF $condition $then_part ( else_part )? )
            {
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:190:8: ^( IF $condition $then_part ( else_part )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_IF.nextNode(), root_1);

                adaptor.addChild(root_1, stream_condition.nextTree());
                adaptor.addChild(root_1, stream_then_part.nextTree());
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:190:35: ( else_part )?
                if ( stream_else_part.hasNext() ) {
                    adaptor.addChild(root_1, stream_else_part.nextTree());

                }
                stream_else_part.reset();

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
            if ( state.backtracking>0 ) { memoize(input, 29, if_statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "if_statement"

    public static class else_part_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "else_part"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:193:1: else_part : ( ELSE block -> ^( ELSE block ) | ELSE stmt= if_statement -> ^( ELSEIF $stmt) );
    public final CluCalcParser.else_part_return else_part() throws RecognitionException {
        CluCalcParser.else_part_return retval = new CluCalcParser.else_part_return();
        retval.start = input.LT(1);
        int else_part_StartIndex = input.index();
        Object root_0 = null;

        Token ELSE106=null;
        Token ELSE108=null;
        CluCalcParser.if_statement_return stmt = null;

        CluCalcParser.block_return block107 = null;


        Object ELSE106_tree=null;
        Object ELSE108_tree=null;
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleSubtreeStream stream_if_statement=new RewriteRuleSubtreeStream(adaptor,"rule if_statement");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:194:3: ( ELSE block -> ^( ELSE block ) | ELSE stmt= if_statement -> ^( ELSEIF $stmt) )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==ELSE) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==CLBRACKET) ) {
                    alt31=1;
                }
                else if ( (LA31_1==IF) ) {
                    alt31=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:194:5: ELSE block
                    {
                    ELSE106=(Token)match(input,ELSE,FOLLOW_ELSE_in_else_part1162); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ELSE.add(ELSE106);

                    pushFollow(FOLLOW_block_in_else_part1164);
                    block107=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_block.add(block107.getTree());


                    // AST REWRITE
                    // elements: block, ELSE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 194:16: -> ^( ELSE block )
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:194:19: ^( ELSE block )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_ELSE.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_block.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:195:5: ELSE stmt= if_statement
                    {
                    ELSE108=(Token)match(input,ELSE,FOLLOW_ELSE_in_else_part1178); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ELSE.add(ELSE108);

                    pushFollow(FOLLOW_if_statement_in_else_part1182);
                    stmt=if_statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_if_statement.add(stmt.getTree());


                    // AST REWRITE
                    // elements: stmt
                    // token labels: 
                    // rule labels: retval, stmt
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_stmt=new RewriteRuleSubtreeStream(adaptor,"rule stmt",stmt!=null?stmt.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 195:28: -> ^( ELSEIF $stmt)
                    {
                        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:195:31: ^( ELSEIF $stmt)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ELSEIF, "ELSEIF"), root_1);

                        adaptor.addChild(root_1, stream_stmt.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
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
            if ( state.backtracking>0 ) { memoize(input, 30, else_part_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "else_part"

    public static class loop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "loop"
    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:198:1: loop : LOOP stmt= statement -> ^( LOOP $stmt) ;
    public final CluCalcParser.loop_return loop() throws RecognitionException {
        CluCalcParser.loop_return retval = new CluCalcParser.loop_return();
        retval.start = input.LT(1);
        int loop_StartIndex = input.index();
        Object root_0 = null;

        Token LOOP109=null;
        CluCalcParser.statement_return stmt = null;


        Object LOOP109_tree=null;
        RewriteRuleTokenStream stream_LOOP=new RewriteRuleTokenStream(adaptor,"token LOOP");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:199:3: ( LOOP stmt= statement -> ^( LOOP $stmt) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:199:5: LOOP stmt= statement
            {
            LOOP109=(Token)match(input,LOOP,FOLLOW_LOOP_in_loop1206); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LOOP.add(LOOP109);

            pushFollow(FOLLOW_statement_in_loop1210);
            stmt=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(stmt.getTree());


            // AST REWRITE
            // elements: stmt, LOOP
            // token labels: 
            // rule labels: retval, stmt
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_stmt=new RewriteRuleSubtreeStream(adaptor,"rule stmt",stmt!=null?stmt.tree:null);

            root_0 = (Object)adaptor.nil();
            // 199:25: -> ^( LOOP $stmt)
            {
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:199:28: ^( LOOP $stmt)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_LOOP.nextNode(), root_1);

                adaptor.addChild(root_1, stream_stmt.nextTree());

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
            if ( state.backtracking>0 ) { memoize(input, 31, loop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "loop"

    // $ANTLR start synpred4_CluCalcParser
    public final void synpred4_CluCalcParser_fragment() throws RecognitionException {   
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:61:5: ( lvalue EQUALS expression )
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:61:5: lvalue EQUALS expression
        {
        pushFollow(FOLLOW_lvalue_in_synpred4_CluCalcParser179);
        lvalue();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUALS,FOLLOW_EQUALS_in_synpred4_CluCalcParser181); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred4_CluCalcParser183);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_CluCalcParser

    // $ANTLR start synpred5_CluCalcParser
    public final void synpred5_CluCalcParser_fragment() throws RecognitionException {   
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:62:5: ( additive_expression )
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:62:5: additive_expression
        {
        pushFollow(FOLLOW_additive_expression_in_synpred5_CluCalcParser199);
        additive_expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_CluCalcParser

    // $ANTLR start synpred39_CluCalcParser
    public final void synpred39_CluCalcParser_fragment() throws RecognitionException {   
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:36: ( statement )
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:165:36: statement
        {
        pushFollow(FOLLOW_statement_in_synpred39_CluCalcParser815);
        statement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred39_CluCalcParser

    // $ANTLR start synpred46_CluCalcParser
    public final void synpred46_CluCalcParser_fragment() throws RecognitionException {   
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:189:6: ( else_part )
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcParser.g:189:6: else_part
        {
        pushFollow(FOLLOW_else_part_in_synpred46_CluCalcParser1059);
        else_part();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred46_CluCalcParser

    // Delegated rules

    public final boolean synpred46_CluCalcParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred46_CluCalcParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_CluCalcParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_CluCalcParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_CluCalcParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_CluCalcParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_CluCalcParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_CluCalcParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA24 dfa24 = new DFA24(this);
    static final String DFA4_eotS =
        "\13\uffff";
    static final String DFA4_eofS =
        "\13\uffff";
    static final String DFA4_minS =
        "\1\4\7\0\3\uffff";
    static final String DFA4_maxS =
        "\1\45\7\0\3\uffff";
    static final String DFA4_acceptS =
        "\10\uffff\1\1\1\2\1\3";
    static final String DFA4_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\3\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\3\2\uffff\1\3\2\uffff\1\6\10\uffff\1\1\1\2\7\uffff\1\5\4"+
            "\uffff\1\4\3\uffff\1\7",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "60:1: expression : ( lvalue EQUALS expression -> ^( EQUALS lvalue expression ) | additive_expression | logical_or_expression );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_1 = input.LA(1);

                         
                        int index4_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_CluCalcParser()) ) {s = 8;}

                        else if ( (synpred5_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index4_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_2 = input.LA(1);

                         
                        int index4_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_CluCalcParser()) ) {s = 8;}

                        else if ( (synpred5_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index4_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_3 = input.LA(1);

                         
                        int index4_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_CluCalcParser()) ) {s = 8;}

                        else if ( (synpred5_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index4_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_4 = input.LA(1);

                         
                        int index4_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_CluCalcParser()) ) {s = 8;}

                        else if ( (synpred5_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index4_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA4_5 = input.LA(1);

                         
                        int index4_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_CluCalcParser()) ) {s = 8;}

                        else if ( (synpred5_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index4_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA4_6 = input.LA(1);

                         
                        int index4_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_CluCalcParser()) ) {s = 8;}

                        else if ( (synpred5_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index4_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_CluCalcParser()) ) {s = 8;}

                        else if ( (synpred5_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index4_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 4, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA23_eotS =
        "\16\uffff";
    static final String DFA23_eofS =
        "\16\uffff";
    static final String DFA23_minS =
        "\1\4\1\uffff\1\4\1\12\6\uffff\2\4\2\uffff";
    static final String DFA23_maxS =
        "\1\54\1\uffff\1\45\1\64\6\uffff\2\45\2\uffff";
    static final String DFA23_acceptS =
        "\1\uffff\1\1\2\uffff\1\5\1\6\1\7\1\10\1\11\1\4\2\uffff\1\2\1\3";
    static final String DFA23_specialS =
        "\16\uffff}>";
    static final String[] DFA23_transitionS = {
            "\1\1\2\uffff\1\1\2\uffff\1\1\2\uffff\1\5\1\uffff\1\6\1\7\2"+
            "\uffff\1\3\1\1\2\uffff\1\10\4\uffff\1\1\4\uffff\1\1\1\uffff"+
            "\1\4\1\uffff\1\1\2\uffff\1\1\2\uffff\1\1\1\2",
            "",
            "\1\1\2\uffff\1\1\2\uffff\1\1\2\11\6\uffff\2\1\7\uffff\1\1"+
            "\4\uffff\1\1\3\uffff\1\1",
            "\1\1\16\uffff\1\13\1\uffff\4\1\2\uffff\1\12\6\uffff\3\1\2"+
            "\uffff\10\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\1\2\uffff\1\1\2\uffff\1\1\10\uffff\2\1\7\uffff\1\1\4\uffff"+
            "\1\1\1\14\2\uffff\1\1",
            "\1\1\2\uffff\1\1\2\uffff\1\1\10\uffff\2\1\7\uffff\1\1\4\uffff"+
            "\1\1\1\uffff\1\15\1\uffff\1\1",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "152:1: statement : ( expression_statement | procedure_call | macro_definition | draw_mode | block | if_statement | loop | BREAK | pragma );";
        }
    }
    static final String DFA24_eotS =
        "\21\uffff";
    static final String DFA24_eofS =
        "\21\uffff";
    static final String DFA24_minS =
        "\1\4\7\0\11\uffff";
    static final String DFA24_maxS =
        "\1\54\7\0\11\uffff";
    static final String DFA24_acceptS =
        "\10\uffff\1\2\1\1\7\uffff";
    static final String DFA24_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\11\uffff}>";
    static final String[] DFA24_transitionS = {
            "\1\3\2\uffff\1\3\2\uffff\1\6\2\uffff\1\11\1\uffff\2\11\2\uffff"+
            "\1\1\1\2\2\uffff\1\11\4\uffff\1\5\4\uffff\1\4\1\uffff\1\11\1"+
            "\10\1\7\2\uffff\1\11\2\uffff\2\11",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA24_eot = DFA.unpackEncodedString(DFA24_eotS);
    static final short[] DFA24_eof = DFA.unpackEncodedString(DFA24_eofS);
    static final char[] DFA24_min = DFA.unpackEncodedStringToUnsignedChars(DFA24_minS);
    static final char[] DFA24_max = DFA.unpackEncodedStringToUnsignedChars(DFA24_maxS);
    static final short[] DFA24_accept = DFA.unpackEncodedString(DFA24_acceptS);
    static final short[] DFA24_special = DFA.unpackEncodedString(DFA24_specialS);
    static final short[][] DFA24_transition;

    static {
        int numStates = DFA24_transitionS.length;
        DFA24_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA24_transition[i] = DFA.unpackEncodedString(DFA24_transitionS[i]);
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = DFA24_eot;
            this.eof = DFA24_eof;
            this.min = DFA24_min;
            this.max = DFA24_max;
            this.accept = DFA24_accept;
            this.special = DFA24_special;
            this.transition = DFA24_transition;
        }
        public String getDescription() {
            return "()* loopback of 165:36: ( statement )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA24_1 = input.LA(1);

                         
                        int index24_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA24_2 = input.LA(1);

                         
                        int index24_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA24_3 = input.LA(1);

                         
                        int index24_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA24_4 = input.LA(1);

                         
                        int index24_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA24_5 = input.LA(1);

                         
                        int index24_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA24_6 = input.LA(1);

                         
                        int index24_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA24_7 = input.LA(1);

                         
                        int index24_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred39_CluCalcParser()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index24_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 24, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_statement_in_script107 = new BitSet(new long[]{0x0000192A1099A490L});
    public static final BitSet FOLLOW_EOF_in_script110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_float_literal124 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FLOATING_POINT_LITERAL_in_float_literal127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma142 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RANGE_LITERAL_in_pragma144 = new BitSet(new long[]{0x0000000000000480L});
    public static final BitSet FOLLOW_float_literal_in_pragma146 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma148 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma150 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma152 = new BitSet(new long[]{0x0000000000000480L});
    public static final BitSet FOLLOW_float_literal_in_pragma154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma160 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_OUTPUT_LITERAL_in_pragma162 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_expression179 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_EQUALS_in_expression181 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_expression_in_expression183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additive_expression_in_expression199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logical_or_expression_in_expression205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_argument_expression_list220 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_COMMA_in_argument_expression_list224 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_expression_in_argument_expression_list227 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_unary_expression_in_lvalue245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logical_and_expression_in_logical_or_expression258 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_DOUBLE_BAR_in_logical_or_expression261 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_logical_and_expression_in_logical_or_expression264 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_equality_expression_in_logical_and_expression279 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_DOUBLE_AND_in_logical_and_expression282 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_equality_expression_in_logical_and_expression285 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_relational_expression_in_equality_expression300 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_DOUBLE_EQUALS_in_equality_expression304 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_UNEQUAL_in_equality_expression309 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_relational_expression_in_equality_expression313 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_additive_expression_in_relational_expression328 = new BitSet(new long[]{0x001E000000000002L});
    public static final BitSet FOLLOW_LESS_in_relational_expression332 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_GREATER_in_relational_expression337 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_relational_expression342 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_GREATER_OR_EQUAL_in_relational_expression347 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_additive_expression_in_relational_expression351 = new BitSet(new long[]{0x001E000000000002L});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression366 = new BitSet(new long[]{0x0000000008000402L});
    public static final BitSet FOLLOW_PLUS_in_additive_expression371 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_MINUS_in_additive_expression376 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression380 = new BitSet(new long[]{0x0000000008000402L});
    public static final BitSet FOLLOW_outer_product_expression_in_multiplicative_expression396 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicative_expression401 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_SLASH_in_multiplicative_expression406 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_outer_product_expression_in_multiplicative_expression410 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_inner_product_expression_in_outer_product_expression426 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_WEDGE_in_outer_product_expression430 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_inner_product_expression_in_outer_product_expression433 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_modulo_expression_in_inner_product_expression449 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_DOT_in_inner_product_expression453 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_modulo_expression_in_inner_product_expression456 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_unary_expression_in_modulo_expression472 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_MODULO_in_modulo_expression476 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_unary_expression_in_modulo_expression479 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_postfix_expression_in_unary_expression495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_unary_expression501 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_unary_expression_in_unary_expression505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_unary_expression520 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_unary_expression_in_unary_expression524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REVERSE_in_unary_expression539 = new BitSet(new long[]{0x0000002210180490L});
    public static final BitSet FOLLOW_unary_expression_in_unary_expression543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_expression_in_postfix_expression567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_postfix_expression573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function_call589 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACKET_in_function_call591 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_argument_expression_list_in_function_call595 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RBRACKET_in_function_call597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_primary_expression625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_argument_in_primary_expression631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_primary_expression638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_primary_expression644 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_expression_in_primary_expression647 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RBRACKET_in_primary_expression649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARGUMENT_PREFIX_in_function_argument695 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_function_argument699 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RBRACKET_in_function_argument701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_statement_list725 = new BitSet(new long[]{0x0000192A1099A492L});
    public static final BitSet FOLLOW_expression_statement_in_statement743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedure_call_in_statement749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_macro_definition_in_statement755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_draw_mode_in_statement761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_statement_in_statement773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_statement779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_statement785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pragma_in_statement791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_macro_definition809 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_EQUALS_in_macro_definition811 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_CLBRACKET_in_macro_definition813 = new BitSet(new long[]{0x0000193A1099A490L});
    public static final BitSet FOLLOW_statement_in_macro_definition815 = new BitSet(new long[]{0x0000193A1099A490L});
    public static final BitSet FOLLOW_additive_expression_in_macro_definition820 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_CRBRACKET_in_macro_definition823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLBRACKET_in_block854 = new BitSet(new long[]{0x0000193A1099A490L});
    public static final BitSet FOLLOW_statement_in_block856 = new BitSet(new long[]{0x0000193A1099A490L});
    public static final BitSet FOLLOW_CRBRACKET_in_block859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_draw_mode884 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IPNS_in_draw_mode886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_draw_mode900 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_OPNS_in_draw_mode902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_procedure_call927 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACKET_in_procedure_call929 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RBRACKET_in_procedure_call931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_expression_statement956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QUESTIONMARK_in_expression_statement964 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_COLON_in_expression_statement969 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_expression_in_expression_statement974 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_expression_statement976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_statement993 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACKET_in_if_statement995 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_logical_or_expression_in_if_statement999 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RBRACKET_in_if_statement1001 = new BitSet(new long[]{0x0000192A1099A490L});
    public static final BitSet FOLLOW_statement_in_if_statement1014 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_else_part_in_if_statement1059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_else_part1162 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_block_in_else_part1164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_else_part1178 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_if_statement_in_else_part1182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LOOP_in_loop1206 = new BitSet(new long[]{0x0000192A1099A490L});
    public static final BitSet FOLLOW_statement_in_loop1210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_synpred4_CluCalcParser179 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_EQUALS_in_synpred4_CluCalcParser181 = new BitSet(new long[]{0x0000192210180490L});
    public static final BitSet FOLLOW_expression_in_synpred4_CluCalcParser183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additive_expression_in_synpred5_CluCalcParser199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_synpred39_CluCalcParser815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_else_part_in_synpred46_CluCalcParser1059 = new BitSet(new long[]{0x0000000000000002L});

}