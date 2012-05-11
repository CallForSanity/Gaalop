// $ANTLR 3.2 Sep 23, 2009 12:02:23 GaaletParser.g 2010-12-19 07:56:27

  package de.gaalop.gaalet.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class GaaletParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DECIMAL_LITERAL", "HEX_PREFIX", "DIGIT", "HEX", "RANGE_LITERAL", "OUTPUT_LITERAL", "UNROLL_LITERAL", "COUNT_LITERAL", "IGNORE_LITERAL", "LOOP", "BREAK", "GEALG_MV", "GEALG_TYPE", "DOUBLE", "FLOAT", "INTEGER", "UNSIGNED", "SIGNED", "AUTO", "EVAL", "INVERSE", "MINUS", "EXPONENT", "FLOATTYPESUFFIX", "OPNS", "IPNS", "IF", "ELSE", "IDENTIFIER_RECURSIVE", "IDENTIFIER", "LETTER", "DOT", "DOUBLE_COLON", "ARROW_RIGHT", "LSBRACKET", "RSBRACKET", "LBRACKET", "RBRACKET", "IDENTIFIER_TYPE_CAST", "FLOATING_POINT_LITERAL", "SPACE", "WS", "COMMA", "PLUS", "STAR", "SLASH", "MODULO", "CLBRACKET", "CRBRACKET", "REVERSE", "NOT", "DOUBLE_NOT", "SEMICOLON", "WEDGE", "QUESTIONMARK", "COLON", "DOUBLE_BAR", "DOUBLE_AND", "SINGLE_AND", "DOUBLE_EQUALS", "EQUALS", "UNEQUAL", "LESS", "GREATER", "LESS_OR_EQUAL", "GREATER_OR_EQUAL", "SET_OUTPUT", "COMMENT", "PRAGMA", "LINE_COMMENT", "FUNCTION", "PROCEDURE", "NEGATION", "LOGICAL_NEGATION", "DUAL", "BLOCK", "ELSEIF", "DEFINE_V", "DEFINE_ASSIGNMENT", "DEFINE_MV", "DEFINE_MV_AND_ASSIGN", "DEFINE_GEALG", "BLADE", "BLADE_ASSIGN", "MACRO", "ARGUMENT", "RETURN", "ARG_LIST_SEP", "ARGUMENT_PREFIX"
    };
    public static final int FUNCTION=74;
    public static final int ARROW_RIGHT=37;
    public static final int EXPONENT=26;
    public static final int SIGNED=21;
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
    public static final int LESS=66;
    public static final int RETURN=90;
    public static final int DOUBLE=17;
    public static final int COMMENT=71;
    public static final int SET_OUTPUT=70;
    public static final int DUAL=78;
    public static final int LINE_COMMENT=73;
    public static final int ELSE=31;
    public static final int SEMICOLON=56;
    public static final int DOUBLE_AND=61;
    public static final int LSBRACKET=38;
    public static final int INVERSE=24;
    public static final int WS=45;
    public static final int EVAL=23;
    public static final int RANGE_LITERAL=8;
    public static final int DOUBLE_COLON=36;
    public static final int GEALG_TYPE=16;
    public static final int DEFINE_V=81;
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


        public GaaletParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GaaletParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[101+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return GaaletParser.tokenNames; }
    public String getGrammarFileName() { return "GaaletParser.g"; }


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
    // GaaletParser.g:55:1: script : ( statement )* EOF ;
    public final GaaletParser.script_return script() throws RecognitionException {
        GaaletParser.script_return retval = new GaaletParser.script_return();
        retval.start = input.LT(1);
        int script_StartIndex = input.index();
        Object root_0 = null;

        Token EOF2=null;
        GaaletParser.statement_return statement1 = null;


        Object EOF2_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // GaaletParser.g:55:9: ( ( statement )* EOF )
            // GaaletParser.g:56:3: ( statement )* EOF
            {
            root_0 = (Object)adaptor.nil();

            // GaaletParser.g:56:3: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==DECIMAL_LITERAL||(LA1_0>=LOOP && LA1_0<=GEALG_MV)||(LA1_0>=DOUBLE && LA1_0<=MINUS)||LA1_0==IF||LA1_0==IDENTIFIER||LA1_0==LBRACKET||LA1_0==FLOATING_POINT_LITERAL||LA1_0==CLBRACKET||(LA1_0>=REVERSE && LA1_0<=NOT)||LA1_0==SEMICOLON||LA1_0==SET_OUTPUT||LA1_0==PRAGMA||LA1_0==ARGUMENT_PREFIX) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GaaletParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_script172);
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

            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_script175); if (state.failed) return retval;

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
    // GaaletParser.g:59:1: float_literal : ( MINUS )? FLOATING_POINT_LITERAL ;
    public final GaaletParser.float_literal_return float_literal() throws RecognitionException {
        GaaletParser.float_literal_return retval = new GaaletParser.float_literal_return();
        retval.start = input.LT(1);
        int float_literal_StartIndex = input.index();
        Object root_0 = null;

        Token MINUS3=null;
        Token FLOATING_POINT_LITERAL4=null;

        Object MINUS3_tree=null;
        Object FLOATING_POINT_LITERAL4_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // GaaletParser.g:59:14: ( ( MINUS )? FLOATING_POINT_LITERAL )
            // GaaletParser.g:60:3: ( MINUS )? FLOATING_POINT_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            // GaaletParser.g:60:3: ( MINUS )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==MINUS) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // GaaletParser.g:0:0: MINUS
                    {
                    MINUS3=(Token)match(input,MINUS,FOLLOW_MINUS_in_float_literal189); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS3_tree = (Object)adaptor.create(MINUS3);
                    adaptor.addChild(root_0, MINUS3_tree);
                    }

                    }
                    break;

            }

            FLOATING_POINT_LITERAL4=(Token)match(input,FLOATING_POINT_LITERAL,FOLLOW_FLOATING_POINT_LITERAL_in_float_literal192); if (state.failed) return retval;
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
    // GaaletParser.g:66:1: pragma : ( PRAGMA RANGE_LITERAL float_literal LESS_OR_EQUAL IDENTIFIER LESS_OR_EQUAL float_literal | PRAGMA OUTPUT_LITERAL IDENTIFIER | PRAGMA IGNORE_LITERAL IDENTIFIER );
    public final GaaletParser.pragma_return pragma() throws RecognitionException {
        GaaletParser.pragma_return retval = new GaaletParser.pragma_return();
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
        Token PRAGMA15=null;
        Token IGNORE_LITERAL16=null;
        Token IDENTIFIER17=null;
        GaaletParser.float_literal_return float_literal7 = null;

        GaaletParser.float_literal_return float_literal11 = null;


        Object PRAGMA5_tree=null;
        Object RANGE_LITERAL6_tree=null;
        Object LESS_OR_EQUAL8_tree=null;
        Object IDENTIFIER9_tree=null;
        Object LESS_OR_EQUAL10_tree=null;
        Object PRAGMA12_tree=null;
        Object OUTPUT_LITERAL13_tree=null;
        Object IDENTIFIER14_tree=null;
        Object PRAGMA15_tree=null;
        Object IGNORE_LITERAL16_tree=null;
        Object IDENTIFIER17_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // GaaletParser.g:67:3: ( PRAGMA RANGE_LITERAL float_literal LESS_OR_EQUAL IDENTIFIER LESS_OR_EQUAL float_literal | PRAGMA OUTPUT_LITERAL IDENTIFIER | PRAGMA IGNORE_LITERAL IDENTIFIER )
            int alt3=3;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==PRAGMA) ) {
                switch ( input.LA(2) ) {
                case RANGE_LITERAL:
                    {
                    alt3=1;
                    }
                    break;
                case OUTPUT_LITERAL:
                    {
                    alt3=2;
                    }
                    break;
                case IGNORE_LITERAL:
                    {
                    alt3=3;
                    }
                    break;
                default:
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
                    // GaaletParser.g:67:5: PRAGMA RANGE_LITERAL float_literal LESS_OR_EQUAL IDENTIFIER LESS_OR_EQUAL float_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    PRAGMA5=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma207); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRAGMA5_tree = (Object)adaptor.create(PRAGMA5);
                    adaptor.addChild(root_0, PRAGMA5_tree);
                    }
                    RANGE_LITERAL6=(Token)match(input,RANGE_LITERAL,FOLLOW_RANGE_LITERAL_in_pragma209); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RANGE_LITERAL6_tree = (Object)adaptor.create(RANGE_LITERAL6);
                    adaptor.addChild(root_0, RANGE_LITERAL6_tree);
                    }
                    pushFollow(FOLLOW_float_literal_in_pragma211);
                    float_literal7=float_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, float_literal7.getTree());
                    LESS_OR_EQUAL8=(Token)match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma213); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESS_OR_EQUAL8_tree = (Object)adaptor.create(LESS_OR_EQUAL8);
                    adaptor.addChild(root_0, LESS_OR_EQUAL8_tree);
                    }
                    IDENTIFIER9=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma215); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER9_tree = (Object)adaptor.create(IDENTIFIER9);
                    adaptor.addChild(root_0, IDENTIFIER9_tree);
                    }
                    LESS_OR_EQUAL10=(Token)match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_pragma217); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESS_OR_EQUAL10_tree = (Object)adaptor.create(LESS_OR_EQUAL10);
                    adaptor.addChild(root_0, LESS_OR_EQUAL10_tree);
                    }
                    pushFollow(FOLLOW_float_literal_in_pragma219);
                    float_literal11=float_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, float_literal11.getTree());

                    }
                    break;
                case 2 :
                    // GaaletParser.g:68:5: PRAGMA OUTPUT_LITERAL IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    PRAGMA12=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma225); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRAGMA12_tree = (Object)adaptor.create(PRAGMA12);
                    adaptor.addChild(root_0, PRAGMA12_tree);
                    }
                    OUTPUT_LITERAL13=(Token)match(input,OUTPUT_LITERAL,FOLLOW_OUTPUT_LITERAL_in_pragma227); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    OUTPUT_LITERAL13_tree = (Object)adaptor.create(OUTPUT_LITERAL13);
                    adaptor.addChild(root_0, OUTPUT_LITERAL13_tree);
                    }
                    IDENTIFIER14=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma229); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER14_tree = (Object)adaptor.create(IDENTIFIER14);
                    adaptor.addChild(root_0, IDENTIFIER14_tree);
                    }

                    }
                    break;
                case 3 :
                    // GaaletParser.g:69:5: PRAGMA IGNORE_LITERAL IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    PRAGMA15=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_pragma235); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRAGMA15_tree = (Object)adaptor.create(PRAGMA15);
                    adaptor.addChild(root_0, PRAGMA15_tree);
                    }
                    IGNORE_LITERAL16=(Token)match(input,IGNORE_LITERAL,FOLLOW_IGNORE_LITERAL_in_pragma237); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IGNORE_LITERAL16_tree = (Object)adaptor.create(IGNORE_LITERAL16);
                    adaptor.addChild(root_0, IGNORE_LITERAL16_tree);
                    }
                    IDENTIFIER17=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_pragma239); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER17_tree = (Object)adaptor.create(IDENTIFIER17);
                    adaptor.addChild(root_0, IDENTIFIER17_tree);
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
    // GaaletParser.g:75:1: expression : ( type lvalue EQUALS expression -> ^( DEFINE_ASSIGNMENT lvalue expression ) | type lvalue -> ^( DEFINE_V lvalue ) | lvalue EQUALS expression -> ^( EQUALS lvalue expression ) | logical_or_expression | eval );
    public final GaaletParser.expression_return expression() throws RecognitionException {
        GaaletParser.expression_return retval = new GaaletParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        Token EQUALS20=null;
        Token EQUALS25=null;
        GaaletParser.type_return type18 = null;

        GaaletParser.lvalue_return lvalue19 = null;

        GaaletParser.expression_return expression21 = null;

        GaaletParser.type_return type22 = null;

        GaaletParser.lvalue_return lvalue23 = null;

        GaaletParser.lvalue_return lvalue24 = null;

        GaaletParser.expression_return expression26 = null;

        GaaletParser.logical_or_expression_return logical_or_expression27 = null;

        GaaletParser.eval_return eval28 = null;


        Object EQUALS20_tree=null;
        Object EQUALS25_tree=null;
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_lvalue=new RewriteRuleSubtreeStream(adaptor,"rule lvalue");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // GaaletParser.g:76:3: ( type lvalue EQUALS expression -> ^( DEFINE_ASSIGNMENT lvalue expression ) | type lvalue -> ^( DEFINE_V lvalue ) | lvalue EQUALS expression -> ^( EQUALS lvalue expression ) | logical_or_expression | eval )
            int alt4=5;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // GaaletParser.g:77:5: type lvalue EQUALS expression
                    {
                    pushFollow(FOLLOW_type_in_expression258);
                    type18=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_type.add(type18.getTree());
                    pushFollow(FOLLOW_lvalue_in_expression260);
                    lvalue19=lvalue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_lvalue.add(lvalue19.getTree());
                    EQUALS20=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_expression262); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EQUALS.add(EQUALS20);

                    pushFollow(FOLLOW_expression_in_expression264);
                    expression21=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression21.getTree());


                    // AST REWRITE
                    // elements: lvalue, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 77:36: -> ^( DEFINE_ASSIGNMENT lvalue expression )
                    {
                        // GaaletParser.g:77:39: ^( DEFINE_ASSIGNMENT lvalue expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINE_ASSIGNMENT, "DEFINE_ASSIGNMENT"), root_1);

                        adaptor.addChild(root_1, stream_lvalue.nextTree());
                        adaptor.addChild(root_1, stream_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // GaaletParser.g:78:5: type lvalue
                    {
                    pushFollow(FOLLOW_type_in_expression282);
                    type22=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_type.add(type22.getTree());
                    pushFollow(FOLLOW_lvalue_in_expression284);
                    lvalue23=lvalue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_lvalue.add(lvalue23.getTree());


                    // AST REWRITE
                    // elements: lvalue
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 78:17: -> ^( DEFINE_V lvalue )
                    {
                        // GaaletParser.g:78:20: ^( DEFINE_V lvalue )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINE_V, "DEFINE_V"), root_1);

                        adaptor.addChild(root_1, stream_lvalue.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // GaaletParser.g:79:5: lvalue EQUALS expression
                    {
                    pushFollow(FOLLOW_lvalue_in_expression298);
                    lvalue24=lvalue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_lvalue.add(lvalue24.getTree());
                    EQUALS25=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_expression300); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EQUALS.add(EQUALS25);

                    pushFollow(FOLLOW_expression_in_expression302);
                    expression26=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression26.getTree());


                    // AST REWRITE
                    // elements: EQUALS, expression, lvalue
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 79:32: -> ^( EQUALS lvalue expression )
                    {
                        // GaaletParser.g:79:35: ^( EQUALS lvalue expression )
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
                case 4 :
                    // GaaletParser.g:80:5: logical_or_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_logical_or_expression_in_expression321);
                    logical_or_expression27=logical_or_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logical_or_expression27.getTree());

                    }
                    break;
                case 5 :
                    // GaaletParser.g:81:5: eval
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_eval_in_expression330);
                    eval28=eval();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, eval28.getTree());

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
    // GaaletParser.g:84:1: argument_expression_list : ( ( expression COMMA )* expression ) ;
    public final GaaletParser.argument_expression_list_return argument_expression_list() throws RecognitionException {
        GaaletParser.argument_expression_list_return retval = new GaaletParser.argument_expression_list_return();
        retval.start = input.LT(1);
        int argument_expression_list_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA30=null;
        GaaletParser.expression_return expression29 = null;

        GaaletParser.expression_return expression31 = null;


        Object COMMA30_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // GaaletParser.g:85:3: ( ( ( expression COMMA )* expression ) )
            // GaaletParser.g:85:7: ( ( expression COMMA )* expression )
            {
            root_0 = (Object)adaptor.nil();

            // GaaletParser.g:85:7: ( ( expression COMMA )* expression )
            // GaaletParser.g:85:8: ( expression COMMA )* expression
            {
            // GaaletParser.g:85:8: ( expression COMMA )*
            loop5:
            do {
                int alt5=2;
                alt5 = dfa5.predict(input);
                switch (alt5) {
            	case 1 :
            	    // GaaletParser.g:85:9: expression COMMA
            	    {
            	    pushFollow(FOLLOW_expression_in_argument_expression_list348);
            	    expression29=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression29.getTree());
            	    COMMA30=(Token)match(input,COMMA,FOLLOW_COMMA_in_argument_expression_list351); if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            pushFollow(FOLLOW_expression_in_argument_expression_list356);
            expression31=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression31.getTree());

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
    // GaaletParser.g:88:1: lvalue : unary_expression ;
    public final GaaletParser.lvalue_return lvalue() throws RecognitionException {
        GaaletParser.lvalue_return retval = new GaaletParser.lvalue_return();
        retval.start = input.LT(1);
        int lvalue_StartIndex = input.index();
        Object root_0 = null;

        GaaletParser.unary_expression_return unary_expression32 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // GaaletParser.g:89:3: ( unary_expression )
            // GaaletParser.g:89:5: unary_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unary_expression_in_lvalue372);
            unary_expression32=unary_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression32.getTree());

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
    // GaaletParser.g:92:1: logical_or_expression : logical_and_expression ( DOUBLE_BAR logical_and_expression )* ;
    public final GaaletParser.logical_or_expression_return logical_or_expression() throws RecognitionException {
        GaaletParser.logical_or_expression_return retval = new GaaletParser.logical_or_expression_return();
        retval.start = input.LT(1);
        int logical_or_expression_StartIndex = input.index();
        Object root_0 = null;

        Token DOUBLE_BAR34=null;
        GaaletParser.logical_and_expression_return logical_and_expression33 = null;

        GaaletParser.logical_and_expression_return logical_and_expression35 = null;


        Object DOUBLE_BAR34_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // GaaletParser.g:93:3: ( logical_and_expression ( DOUBLE_BAR logical_and_expression )* )
            // GaaletParser.g:93:5: logical_and_expression ( DOUBLE_BAR logical_and_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_logical_and_expression_in_logical_or_expression385);
            logical_and_expression33=logical_and_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logical_and_expression33.getTree());
            // GaaletParser.g:93:28: ( DOUBLE_BAR logical_and_expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==DOUBLE_BAR) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // GaaletParser.g:93:29: DOUBLE_BAR logical_and_expression
            	    {
            	    DOUBLE_BAR34=(Token)match(input,DOUBLE_BAR,FOLLOW_DOUBLE_BAR_in_logical_or_expression388); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DOUBLE_BAR34_tree = (Object)adaptor.create(DOUBLE_BAR34);
            	    root_0 = (Object)adaptor.becomeRoot(DOUBLE_BAR34_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_logical_and_expression_in_logical_or_expression391);
            	    logical_and_expression35=logical_and_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, logical_and_expression35.getTree());

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
    // GaaletParser.g:96:1: logical_and_expression : equality_expression ( DOUBLE_AND equality_expression )* ;
    public final GaaletParser.logical_and_expression_return logical_and_expression() throws RecognitionException {
        GaaletParser.logical_and_expression_return retval = new GaaletParser.logical_and_expression_return();
        retval.start = input.LT(1);
        int logical_and_expression_StartIndex = input.index();
        Object root_0 = null;

        Token DOUBLE_AND37=null;
        GaaletParser.equality_expression_return equality_expression36 = null;

        GaaletParser.equality_expression_return equality_expression38 = null;


        Object DOUBLE_AND37_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // GaaletParser.g:97:3: ( equality_expression ( DOUBLE_AND equality_expression )* )
            // GaaletParser.g:97:5: equality_expression ( DOUBLE_AND equality_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_equality_expression_in_logical_and_expression406);
            equality_expression36=equality_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equality_expression36.getTree());
            // GaaletParser.g:97:25: ( DOUBLE_AND equality_expression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==DOUBLE_AND) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // GaaletParser.g:97:26: DOUBLE_AND equality_expression
            	    {
            	    DOUBLE_AND37=(Token)match(input,DOUBLE_AND,FOLLOW_DOUBLE_AND_in_logical_and_expression409); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DOUBLE_AND37_tree = (Object)adaptor.create(DOUBLE_AND37);
            	    root_0 = (Object)adaptor.becomeRoot(DOUBLE_AND37_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_equality_expression_in_logical_and_expression412);
            	    equality_expression38=equality_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equality_expression38.getTree());

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
    // GaaletParser.g:100:1: equality_expression : relational_expression ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )* ;
    public final GaaletParser.equality_expression_return equality_expression() throws RecognitionException {
        GaaletParser.equality_expression_return retval = new GaaletParser.equality_expression_return();
        retval.start = input.LT(1);
        int equality_expression_StartIndex = input.index();
        Object root_0 = null;

        Token DOUBLE_EQUALS40=null;
        Token UNEQUAL41=null;
        GaaletParser.relational_expression_return relational_expression39 = null;

        GaaletParser.relational_expression_return relational_expression42 = null;


        Object DOUBLE_EQUALS40_tree=null;
        Object UNEQUAL41_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // GaaletParser.g:101:3: ( relational_expression ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )* )
            // GaaletParser.g:101:5: relational_expression ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_relational_expression_in_equality_expression427);
            relational_expression39=relational_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relational_expression39.getTree());
            // GaaletParser.g:101:27: ( ( DOUBLE_EQUALS | UNEQUAL ) relational_expression )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==DOUBLE_EQUALS||LA9_0==UNEQUAL) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // GaaletParser.g:101:28: ( DOUBLE_EQUALS | UNEQUAL ) relational_expression
            	    {
            	    // GaaletParser.g:101:28: ( DOUBLE_EQUALS | UNEQUAL )
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
            	            // GaaletParser.g:101:29: DOUBLE_EQUALS
            	            {
            	            DOUBLE_EQUALS40=(Token)match(input,DOUBLE_EQUALS,FOLLOW_DOUBLE_EQUALS_in_equality_expression431); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            DOUBLE_EQUALS40_tree = (Object)adaptor.create(DOUBLE_EQUALS40);
            	            root_0 = (Object)adaptor.becomeRoot(DOUBLE_EQUALS40_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // GaaletParser.g:101:46: UNEQUAL
            	            {
            	            UNEQUAL41=(Token)match(input,UNEQUAL,FOLLOW_UNEQUAL_in_equality_expression436); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            UNEQUAL41_tree = (Object)adaptor.create(UNEQUAL41);
            	            root_0 = (Object)adaptor.becomeRoot(UNEQUAL41_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_relational_expression_in_equality_expression440);
            	    relational_expression42=relational_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relational_expression42.getTree());

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
    // GaaletParser.g:104:1: relational_expression : additive_expression ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )* ;
    public final GaaletParser.relational_expression_return relational_expression() throws RecognitionException {
        GaaletParser.relational_expression_return retval = new GaaletParser.relational_expression_return();
        retval.start = input.LT(1);
        int relational_expression_StartIndex = input.index();
        Object root_0 = null;

        Token LESS44=null;
        Token GREATER45=null;
        Token LESS_OR_EQUAL46=null;
        Token GREATER_OR_EQUAL47=null;
        GaaletParser.additive_expression_return additive_expression43 = null;

        GaaletParser.additive_expression_return additive_expression48 = null;


        Object LESS44_tree=null;
        Object GREATER45_tree=null;
        Object LESS_OR_EQUAL46_tree=null;
        Object GREATER_OR_EQUAL47_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // GaaletParser.g:105:3: ( additive_expression ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )* )
            // GaaletParser.g:105:5: additive_expression ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_additive_expression_in_relational_expression455);
            additive_expression43=additive_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression43.getTree());
            // GaaletParser.g:105:25: ( ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>=LESS && LA11_0<=GREATER_OR_EQUAL)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // GaaletParser.g:105:26: ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL ) additive_expression
            	    {
            	    // GaaletParser.g:105:26: ( LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL )
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
            	            // GaaletParser.g:105:27: LESS
            	            {
            	            LESS44=(Token)match(input,LESS,FOLLOW_LESS_in_relational_expression459); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            LESS44_tree = (Object)adaptor.create(LESS44);
            	            root_0 = (Object)adaptor.becomeRoot(LESS44_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // GaaletParser.g:105:35: GREATER
            	            {
            	            GREATER45=(Token)match(input,GREATER,FOLLOW_GREATER_in_relational_expression464); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            GREATER45_tree = (Object)adaptor.create(GREATER45);
            	            root_0 = (Object)adaptor.becomeRoot(GREATER45_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // GaaletParser.g:105:46: LESS_OR_EQUAL
            	            {
            	            LESS_OR_EQUAL46=(Token)match(input,LESS_OR_EQUAL,FOLLOW_LESS_OR_EQUAL_in_relational_expression469); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            LESS_OR_EQUAL46_tree = (Object)adaptor.create(LESS_OR_EQUAL46);
            	            root_0 = (Object)adaptor.becomeRoot(LESS_OR_EQUAL46_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // GaaletParser.g:105:63: GREATER_OR_EQUAL
            	            {
            	            GREATER_OR_EQUAL47=(Token)match(input,GREATER_OR_EQUAL,FOLLOW_GREATER_OR_EQUAL_in_relational_expression474); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            GREATER_OR_EQUAL47_tree = (Object)adaptor.create(GREATER_OR_EQUAL47);
            	            root_0 = (Object)adaptor.becomeRoot(GREATER_OR_EQUAL47_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_additive_expression_in_relational_expression478);
            	    additive_expression48=additive_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, additive_expression48.getTree());

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
    // GaaletParser.g:108:1: additive_expression : multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )* ;
    public final GaaletParser.additive_expression_return additive_expression() throws RecognitionException {
        GaaletParser.additive_expression_return retval = new GaaletParser.additive_expression_return();
        retval.start = input.LT(1);
        int additive_expression_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS50=null;
        Token MINUS51=null;
        GaaletParser.multiplicative_expression_return multiplicative_expression49 = null;

        GaaletParser.multiplicative_expression_return multiplicative_expression52 = null;


        Object PLUS50_tree=null;
        Object MINUS51_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // GaaletParser.g:109:3: ( multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )* )
            // GaaletParser.g:109:5: multiplicative_expression ( ( PLUS | MINUS ) multiplicative_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multiplicative_expression_in_additive_expression493);
            multiplicative_expression49=multiplicative_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicative_expression49.getTree());
            // GaaletParser.g:109:31: ( ( PLUS | MINUS ) multiplicative_expression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==MINUS||LA13_0==PLUS) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // GaaletParser.g:109:33: ( PLUS | MINUS ) multiplicative_expression
            	    {
            	    // GaaletParser.g:109:33: ( PLUS | MINUS )
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
            	            // GaaletParser.g:109:34: PLUS
            	            {
            	            PLUS50=(Token)match(input,PLUS,FOLLOW_PLUS_in_additive_expression498); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            PLUS50_tree = (Object)adaptor.create(PLUS50);
            	            root_0 = (Object)adaptor.becomeRoot(PLUS50_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // GaaletParser.g:109:42: MINUS
            	            {
            	            MINUS51=(Token)match(input,MINUS,FOLLOW_MINUS_in_additive_expression503); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            MINUS51_tree = (Object)adaptor.create(MINUS51);
            	            root_0 = (Object)adaptor.becomeRoot(MINUS51_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_multiplicative_expression_in_additive_expression507);
            	    multiplicative_expression52=multiplicative_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicative_expression52.getTree());

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
    // GaaletParser.g:112:1: multiplicative_expression : outer_product_expression ( ( STAR | SLASH ) outer_product_expression )* ;
    public final GaaletParser.multiplicative_expression_return multiplicative_expression() throws RecognitionException {
        GaaletParser.multiplicative_expression_return retval = new GaaletParser.multiplicative_expression_return();
        retval.start = input.LT(1);
        int multiplicative_expression_StartIndex = input.index();
        Object root_0 = null;

        Token STAR54=null;
        Token SLASH55=null;
        GaaletParser.outer_product_expression_return outer_product_expression53 = null;

        GaaletParser.outer_product_expression_return outer_product_expression56 = null;


        Object STAR54_tree=null;
        Object SLASH55_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // GaaletParser.g:113:3: ( outer_product_expression ( ( STAR | SLASH ) outer_product_expression )* )
            // GaaletParser.g:113:5: outer_product_expression ( ( STAR | SLASH ) outer_product_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_outer_product_expression_in_multiplicative_expression523);
            outer_product_expression53=outer_product_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, outer_product_expression53.getTree());
            // GaaletParser.g:113:30: ( ( STAR | SLASH ) outer_product_expression )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=STAR && LA15_0<=SLASH)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // GaaletParser.g:113:32: ( STAR | SLASH ) outer_product_expression
            	    {
            	    // GaaletParser.g:113:32: ( STAR | SLASH )
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
            	            // GaaletParser.g:113:33: STAR
            	            {
            	            STAR54=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicative_expression528); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            STAR54_tree = (Object)adaptor.create(STAR54);
            	            root_0 = (Object)adaptor.becomeRoot(STAR54_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // GaaletParser.g:113:41: SLASH
            	            {
            	            SLASH55=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicative_expression533); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            SLASH55_tree = (Object)adaptor.create(SLASH55);
            	            root_0 = (Object)adaptor.becomeRoot(SLASH55_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_outer_product_expression_in_multiplicative_expression537);
            	    outer_product_expression56=outer_product_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, outer_product_expression56.getTree());

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
    // GaaletParser.g:116:1: outer_product_expression : inner_product_expression ( WEDGE inner_product_expression )* ;
    public final GaaletParser.outer_product_expression_return outer_product_expression() throws RecognitionException {
        GaaletParser.outer_product_expression_return retval = new GaaletParser.outer_product_expression_return();
        retval.start = input.LT(1);
        int outer_product_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WEDGE58=null;
        GaaletParser.inner_product_expression_return inner_product_expression57 = null;

        GaaletParser.inner_product_expression_return inner_product_expression59 = null;


        Object WEDGE58_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // GaaletParser.g:117:3: ( inner_product_expression ( WEDGE inner_product_expression )* )
            // GaaletParser.g:117:5: inner_product_expression ( WEDGE inner_product_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_inner_product_expression_in_outer_product_expression553);
            inner_product_expression57=inner_product_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, inner_product_expression57.getTree());
            // GaaletParser.g:117:30: ( WEDGE inner_product_expression )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==WEDGE) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // GaaletParser.g:117:32: WEDGE inner_product_expression
            	    {
            	    WEDGE58=(Token)match(input,WEDGE,FOLLOW_WEDGE_in_outer_product_expression557); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    WEDGE58_tree = (Object)adaptor.create(WEDGE58);
            	    root_0 = (Object)adaptor.becomeRoot(WEDGE58_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_inner_product_expression_in_outer_product_expression560);
            	    inner_product_expression59=inner_product_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, inner_product_expression59.getTree());

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
    // GaaletParser.g:120:1: inner_product_expression : unary_expression ( SINGLE_AND unary_expression )* ;
    public final GaaletParser.inner_product_expression_return inner_product_expression() throws RecognitionException {
        GaaletParser.inner_product_expression_return retval = new GaaletParser.inner_product_expression_return();
        retval.start = input.LT(1);
        int inner_product_expression_StartIndex = input.index();
        Object root_0 = null;

        Token SINGLE_AND61=null;
        GaaletParser.unary_expression_return unary_expression60 = null;

        GaaletParser.unary_expression_return unary_expression62 = null;


        Object SINGLE_AND61_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // GaaletParser.g:121:3: ( unary_expression ( SINGLE_AND unary_expression )* )
            // GaaletParser.g:121:5: unary_expression ( SINGLE_AND unary_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unary_expression_in_inner_product_expression576);
            unary_expression60=unary_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression60.getTree());
            // GaaletParser.g:121:22: ( SINGLE_AND unary_expression )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==SINGLE_AND) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // GaaletParser.g:121:24: SINGLE_AND unary_expression
            	    {
            	    SINGLE_AND61=(Token)match(input,SINGLE_AND,FOLLOW_SINGLE_AND_in_inner_product_expression580); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SINGLE_AND61_tree = (Object)adaptor.create(SINGLE_AND61);
            	    root_0 = (Object)adaptor.becomeRoot(SINGLE_AND61_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_unary_expression_in_inner_product_expression583);
            	    unary_expression62=unary_expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expression62.getTree());

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

    public static class unary_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_expression"
    // GaaletParser.g:124:1: unary_expression : ( postfix_expression | MINUS operand= unary_expression -> ^( NEGATION $operand) | REVERSE operand= unary_expression -> ^( REVERSE $operand) | INVERSE LBRACKET operand= unary_expression RBRACKET -> ^( INVERSE $operand) | NOT operand= unary_expression -> ^( LOGICAL_NEGATION $operand) | name= IDENTIFIER LSBRACKET blade= DECIMAL_LITERAL RSBRACKET -> ^( BLADE $name $blade) );
    public final GaaletParser.unary_expression_return unary_expression() throws RecognitionException {
        GaaletParser.unary_expression_return retval = new GaaletParser.unary_expression_return();
        retval.start = input.LT(1);
        int unary_expression_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;
        Token blade=null;
        Token MINUS64=null;
        Token REVERSE65=null;
        Token INVERSE66=null;
        Token LBRACKET67=null;
        Token RBRACKET68=null;
        Token NOT69=null;
        Token LSBRACKET70=null;
        Token RSBRACKET71=null;
        GaaletParser.unary_expression_return operand = null;

        GaaletParser.postfix_expression_return postfix_expression63 = null;


        Object name_tree=null;
        Object blade_tree=null;
        Object MINUS64_tree=null;
        Object REVERSE65_tree=null;
        Object INVERSE66_tree=null;
        Object LBRACKET67_tree=null;
        Object RBRACKET68_tree=null;
        Object NOT69_tree=null;
        Object LSBRACKET70_tree=null;
        Object RSBRACKET71_tree=null;
        RewriteRuleTokenStream stream_LSBRACKET=new RewriteRuleTokenStream(adaptor,"token LSBRACKET");
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_INVERSE=new RewriteRuleTokenStream(adaptor,"token INVERSE");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_REVERSE=new RewriteRuleTokenStream(adaptor,"token REVERSE");
        RewriteRuleTokenStream stream_RSBRACKET=new RewriteRuleTokenStream(adaptor,"token RSBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_DECIMAL_LITERAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL_LITERAL");
        RewriteRuleSubtreeStream stream_unary_expression=new RewriteRuleSubtreeStream(adaptor,"rule unary_expression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // GaaletParser.g:125:3: ( postfix_expression | MINUS operand= unary_expression -> ^( NEGATION $operand) | REVERSE operand= unary_expression -> ^( REVERSE $operand) | INVERSE LBRACKET operand= unary_expression RBRACKET -> ^( INVERSE $operand) | NOT operand= unary_expression -> ^( LOGICAL_NEGATION $operand) | name= IDENTIFIER LSBRACKET blade= DECIMAL_LITERAL RSBRACKET -> ^( BLADE $name $blade) )
            int alt18=6;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==EOF||LA18_1==DECIMAL_LITERAL||(LA18_1>=LOOP && LA18_1<=GEALG_MV)||(LA18_1>=DOUBLE && LA18_1<=MINUS)||(LA18_1>=IF && LA18_1<=ELSE)||LA18_1==IDENTIFIER||(LA18_1>=LBRACKET && LA18_1<=RBRACKET)||LA18_1==FLOATING_POINT_LITERAL||(LA18_1>=COMMA && LA18_1<=SLASH)||(LA18_1>=CLBRACKET && LA18_1<=NOT)||(LA18_1>=SEMICOLON && LA18_1<=WEDGE)||(LA18_1>=DOUBLE_BAR && LA18_1<=SET_OUTPUT)||LA18_1==PRAGMA||LA18_1==ARGUMENT_PREFIX) ) {
                    alt18=1;
                }
                else if ( (LA18_1==LSBRACKET) ) {
                    alt18=6;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
                }
                break;
            case DECIMAL_LITERAL:
            case LBRACKET:
            case FLOATING_POINT_LITERAL:
            case ARGUMENT_PREFIX:
                {
                alt18=1;
                }
                break;
            case MINUS:
                {
                alt18=2;
                }
                break;
            case REVERSE:
                {
                alt18=3;
                }
                break;
            case INVERSE:
                {
                alt18=4;
                }
                break;
            case NOT:
                {
                alt18=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // GaaletParser.g:125:5: postfix_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_postfix_expression_in_unary_expression600);
                    postfix_expression63=postfix_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, postfix_expression63.getTree());

                    }
                    break;
                case 2 :
                    // GaaletParser.g:126:5: MINUS operand= unary_expression
                    {
                    MINUS64=(Token)match(input,MINUS,FOLLOW_MINUS_in_unary_expression606); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_MINUS.add(MINUS64);

                    pushFollow(FOLLOW_unary_expression_in_unary_expression610);
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
                    // 126:36: -> ^( NEGATION $operand)
                    {
                        // GaaletParser.g:126:39: ^( NEGATION $operand)
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
                case 3 :
                    // GaaletParser.g:127:5: REVERSE operand= unary_expression
                    {
                    REVERSE65=(Token)match(input,REVERSE,FOLLOW_REVERSE_in_unary_expression625); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_REVERSE.add(REVERSE65);

                    pushFollow(FOLLOW_unary_expression_in_unary_expression629);
                    operand=unary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unary_expression.add(operand.getTree());


                    // AST REWRITE
                    // elements: REVERSE, operand
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
                    // 127:38: -> ^( REVERSE $operand)
                    {
                        // GaaletParser.g:127:41: ^( REVERSE $operand)
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
                case 4 :
                    // GaaletParser.g:128:5: INVERSE LBRACKET operand= unary_expression RBRACKET
                    {
                    INVERSE66=(Token)match(input,INVERSE,FOLLOW_INVERSE_in_unary_expression644); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_INVERSE.add(INVERSE66);

                    LBRACKET67=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_unary_expression646); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET67);

                    pushFollow(FOLLOW_unary_expression_in_unary_expression650);
                    operand=unary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unary_expression.add(operand.getTree());
                    RBRACKET68=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_unary_expression652); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET68);



                    // AST REWRITE
                    // elements: operand, INVERSE
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
                    // 128:55: -> ^( INVERSE $operand)
                    {
                        // GaaletParser.g:128:58: ^( INVERSE $operand)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_INVERSE.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_operand.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // GaaletParser.g:129:5: NOT operand= unary_expression
                    {
                    NOT69=(Token)match(input,NOT,FOLLOW_NOT_in_unary_expression666); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_NOT.add(NOT69);

                    pushFollow(FOLLOW_unary_expression_in_unary_expression670);
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
                    // 129:34: -> ^( LOGICAL_NEGATION $operand)
                    {
                        // GaaletParser.g:129:37: ^( LOGICAL_NEGATION $operand)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(LOGICAL_NEGATION, "LOGICAL_NEGATION"), root_1);

                        adaptor.addChild(root_1, stream_operand.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // GaaletParser.g:130:5: name= IDENTIFIER LSBRACKET blade= DECIMAL_LITERAL RSBRACKET
                    {
                    name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_unary_expression687); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

                    LSBRACKET70=(Token)match(input,LSBRACKET,FOLLOW_LSBRACKET_in_unary_expression689); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LSBRACKET.add(LSBRACKET70);

                    blade=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_unary_expression693); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_DECIMAL_LITERAL.add(blade);

                    RSBRACKET71=(Token)match(input,RSBRACKET,FOLLOW_RSBRACKET_in_unary_expression695); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RSBRACKET.add(RSBRACKET71);



                    // AST REWRITE
                    // elements: blade, name
                    // token labels: name, blade
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
                    RewriteRuleTokenStream stream_blade=new RewriteRuleTokenStream(adaptor,"token blade",blade);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 130:63: -> ^( BLADE $name $blade)
                    {
                        // GaaletParser.g:130:66: ^( BLADE $name $blade)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLADE, "BLADE"), root_1);

                        adaptor.addChild(root_1, stream_name.nextNode());
                        adaptor.addChild(root_1, stream_blade.nextNode());

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
            if ( state.backtracking>0 ) { memoize(input, 15, unary_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unary_expression"

    public static class postfix_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "postfix_expression"
    // GaaletParser.g:134:1: postfix_expression : ( function_call | primary_expression );
    public final GaaletParser.postfix_expression_return postfix_expression() throws RecognitionException {
        GaaletParser.postfix_expression_return retval = new GaaletParser.postfix_expression_return();
        retval.start = input.LT(1);
        int postfix_expression_StartIndex = input.index();
        Object root_0 = null;

        GaaletParser.function_call_return function_call72 = null;

        GaaletParser.primary_expression_return primary_expression73 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // GaaletParser.g:135:3: ( function_call | primary_expression )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==IDENTIFIER) ) {
                int LA19_1 = input.LA(2);

                if ( (synpred29_GaaletParser()) ) {
                    alt19=1;
                }
                else if ( (true) ) {
                    alt19=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA19_0==DECIMAL_LITERAL||LA19_0==LBRACKET||LA19_0==FLOATING_POINT_LITERAL||LA19_0==ARGUMENT_PREFIX) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // GaaletParser.g:135:5: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_postfix_expression727);
                    function_call72=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call72.getTree());

                    }
                    break;
                case 2 :
                    // GaaletParser.g:136:5: primary_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primary_expression_in_postfix_expression733);
                    primary_expression73=primary_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primary_expression73.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 16, postfix_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "postfix_expression"

    public static class function_call_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // GaaletParser.g:139:1: function_call : ( (name= IDENTIFIER LBRACKET (args= argument_expression_list ) RBRACKET ) -> ^( FUNCTION $name $args) | (name= IDENTIFIER LBRACKET RBRACKET ) -> ^( FUNCTION $name) );
    public final GaaletParser.function_call_return function_call() throws RecognitionException {
        GaaletParser.function_call_return retval = new GaaletParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;
        Token LBRACKET74=null;
        Token RBRACKET75=null;
        Token LBRACKET76=null;
        Token RBRACKET77=null;
        GaaletParser.argument_expression_list_return args = null;


        Object name_tree=null;
        Object LBRACKET74_tree=null;
        Object RBRACKET75_tree=null;
        Object LBRACKET76_tree=null;
        Object RBRACKET77_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_argument_expression_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_expression_list");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // GaaletParser.g:140:3: ( (name= IDENTIFIER LBRACKET (args= argument_expression_list ) RBRACKET ) -> ^( FUNCTION $name $args) | (name= IDENTIFIER LBRACKET RBRACKET ) -> ^( FUNCTION $name) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==IDENTIFIER) ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1==LBRACKET) ) {
                    int LA20_2 = input.LA(3);

                    if ( (LA20_2==RBRACKET) ) {
                        alt20=2;
                    }
                    else if ( (LA20_2==DECIMAL_LITERAL||(LA20_2>=DOUBLE && LA20_2<=MINUS)||LA20_2==IDENTIFIER||LA20_2==LBRACKET||LA20_2==FLOATING_POINT_LITERAL||(LA20_2>=REVERSE && LA20_2<=NOT)||LA20_2==ARGUMENT_PREFIX) ) {
                        alt20=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // GaaletParser.g:140:5: (name= IDENTIFIER LBRACKET (args= argument_expression_list ) RBRACKET )
                    {
                    // GaaletParser.g:140:5: (name= IDENTIFIER LBRACKET (args= argument_expression_list ) RBRACKET )
                    // GaaletParser.g:140:6: name= IDENTIFIER LBRACKET (args= argument_expression_list ) RBRACKET
                    {
                    name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function_call749); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

                    LBRACKET74=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_function_call751); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET74);

                    // GaaletParser.g:140:31: (args= argument_expression_list )
                    // GaaletParser.g:140:32: args= argument_expression_list
                    {
                    pushFollow(FOLLOW_argument_expression_list_in_function_call756);
                    args=argument_expression_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_argument_expression_list.add(args.getTree());

                    }

                    RBRACKET75=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_function_call759); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET75);


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
                    // 141:3: -> ^( FUNCTION $name $args)
                    {
                        // GaaletParser.g:141:6: ^( FUNCTION $name $args)
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
                    break;
                case 2 :
                    // GaaletParser.g:142:5: (name= IDENTIFIER LBRACKET RBRACKET )
                    {
                    // GaaletParser.g:142:5: (name= IDENTIFIER LBRACKET RBRACKET )
                    // GaaletParser.g:142:6: name= IDENTIFIER LBRACKET RBRACKET
                    {
                    name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_function_call783); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

                    LBRACKET76=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_function_call785); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET76);

                    RBRACKET77=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_function_call788); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET77);


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
                    // 143:3: -> ^( FUNCTION $name)
                    {
                        // GaaletParser.g:143:6: ^( FUNCTION $name)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION, "FUNCTION"), root_1);

                        adaptor.addChild(root_1, stream_name.nextNode());

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
            if ( state.backtracking>0 ) { memoize(input, 17, function_call_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function_call"

    public static class eval_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eval"
    // GaaletParser.g:146:1: eval : EVAL LBRACKET expression RBRACKET ;
    public final GaaletParser.eval_return eval() throws RecognitionException {
        GaaletParser.eval_return retval = new GaaletParser.eval_return();
        retval.start = input.LT(1);
        int eval_StartIndex = input.index();
        Object root_0 = null;

        Token EVAL78=null;
        Token LBRACKET79=null;
        Token RBRACKET81=null;
        GaaletParser.expression_return expression80 = null;


        Object EVAL78_tree=null;
        Object LBRACKET79_tree=null;
        Object RBRACKET81_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // GaaletParser.g:147:3: ( EVAL LBRACKET expression RBRACKET )
            // GaaletParser.g:147:5: EVAL LBRACKET expression RBRACKET
            {
            root_0 = (Object)adaptor.nil();

            EVAL78=(Token)match(input,EVAL,FOLLOW_EVAL_in_eval815); if (state.failed) return retval;
            LBRACKET79=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_eval818); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_eval821);
            expression80=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression80.getTree());
            RBRACKET81=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_eval823); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 18, eval_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "eval"

    public static class primary_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primary_expression"
    // GaaletParser.g:150:1: primary_expression : ( IDENTIFIER | constant | function_argument | LBRACKET expression RBRACKET );
    public final GaaletParser.primary_expression_return primary_expression() throws RecognitionException {
        GaaletParser.primary_expression_return retval = new GaaletParser.primary_expression_return();
        retval.start = input.LT(1);
        int primary_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IDENTIFIER82=null;
        Token LBRACKET85=null;
        Token RBRACKET87=null;
        GaaletParser.constant_return constant83 = null;

        GaaletParser.function_argument_return function_argument84 = null;

        GaaletParser.expression_return expression86 = null;


        Object IDENTIFIER82_tree=null;
        Object LBRACKET85_tree=null;
        Object RBRACKET87_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // GaaletParser.g:151:3: ( IDENTIFIER | constant | function_argument | LBRACKET expression RBRACKET )
            int alt21=4;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt21=1;
                }
                break;
            case DECIMAL_LITERAL:
            case FLOATING_POINT_LITERAL:
                {
                alt21=2;
                }
                break;
            case ARGUMENT_PREFIX:
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
                    // GaaletParser.g:151:5: IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    IDENTIFIER82=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_primary_expression839); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER82_tree = (Object)adaptor.create(IDENTIFIER82);
                    adaptor.addChild(root_0, IDENTIFIER82_tree);
                    }

                    }
                    break;
                case 2 :
                    // GaaletParser.g:152:5: constant
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constant_in_primary_expression845);
                    constant83=constant();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, constant83.getTree());

                    }
                    break;
                case 3 :
                    // GaaletParser.g:153:5: function_argument
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_argument_in_primary_expression851);
                    function_argument84=function_argument();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_argument84.getTree());

                    }
                    break;
                case 4 :
                    // GaaletParser.g:154:5: LBRACKET expression RBRACKET
                    {
                    root_0 = (Object)adaptor.nil();

                    LBRACKET85=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_primary_expression860); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_primary_expression863);
                    expression86=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression86.getTree());
                    RBRACKET87=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_primary_expression865); if (state.failed) return retval;

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
    // GaaletParser.g:158:1: constant : ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL );
    public final GaaletParser.constant_return constant() throws RecognitionException {
        GaaletParser.constant_return retval = new GaaletParser.constant_return();
        retval.start = input.LT(1);
        int constant_StartIndex = input.index();
        Object root_0 = null;

        Token set88=null;

        Object set88_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // GaaletParser.g:159:5: ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL )
            // GaaletParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set88=(Token)input.LT(1);
            if ( input.LA(1)==DECIMAL_LITERAL||input.LA(1)==FLOATING_POINT_LITERAL ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set88));
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

    public static class statement_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement_list"
    // GaaletParser.g:166:1: statement_list : ( statement )+ ;
    public final GaaletParser.statement_list_return statement_list() throws RecognitionException {
        GaaletParser.statement_list_return retval = new GaaletParser.statement_list_return();
        retval.start = input.LT(1);
        int statement_list_StartIndex = input.index();
        Object root_0 = null;

        GaaletParser.statement_return statement89 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // GaaletParser.g:167:3: ( ( statement )+ )
            // GaaletParser.g:167:5: ( statement )+
            {
            root_0 = (Object)adaptor.nil();

            // GaaletParser.g:167:5: ( statement )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==DECIMAL_LITERAL||(LA22_0>=LOOP && LA22_0<=GEALG_MV)||(LA22_0>=DOUBLE && LA22_0<=MINUS)||LA22_0==IF||LA22_0==IDENTIFIER||LA22_0==LBRACKET||LA22_0==FLOATING_POINT_LITERAL||LA22_0==CLBRACKET||(LA22_0>=REVERSE && LA22_0<=NOT)||LA22_0==SEMICOLON||LA22_0==SET_OUTPUT||LA22_0==PRAGMA||LA22_0==ARGUMENT_PREFIX) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // GaaletParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statement_list915);
            	    statement89=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement89.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 21, statement_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "statement_list"

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // GaaletParser.g:173:1: statement : ( SEMICOLON | block | expression SEMICOLON | define_gealg_name_and_exp | if_statement | set_output | macro_definition | loop | BREAK | pragma );
    public final GaaletParser.statement_return statement() throws RecognitionException {
        GaaletParser.statement_return retval = new GaaletParser.statement_return();
        retval.start = input.LT(1);
        int statement_StartIndex = input.index();
        Object root_0 = null;

        Token SEMICOLON90=null;
        Token SEMICOLON93=null;
        Token BREAK99=null;
        GaaletParser.block_return block91 = null;

        GaaletParser.expression_return expression92 = null;

        GaaletParser.define_gealg_name_and_exp_return define_gealg_name_and_exp94 = null;

        GaaletParser.if_statement_return if_statement95 = null;

        GaaletParser.set_output_return set_output96 = null;

        GaaletParser.macro_definition_return macro_definition97 = null;

        GaaletParser.loop_return loop98 = null;

        GaaletParser.pragma_return pragma100 = null;


        Object SEMICOLON90_tree=null;
        Object SEMICOLON93_tree=null;
        Object BREAK99_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // GaaletParser.g:174:3: ( SEMICOLON | block | expression SEMICOLON | define_gealg_name_and_exp | if_statement | set_output | macro_definition | loop | BREAK | pragma )
            int alt23=10;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // GaaletParser.g:174:4: SEMICOLON
                    {
                    root_0 = (Object)adaptor.nil();

                    SEMICOLON90=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement932); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // GaaletParser.g:175:5: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_statement939);
                    block91=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block91.getTree());

                    }
                    break;
                case 3 :
                    // GaaletParser.g:176:5: expression SEMICOLON
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_statement945);
                    expression92=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression92.getTree());
                    SEMICOLON93=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement947); if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // GaaletParser.g:177:5: define_gealg_name_and_exp
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_define_gealg_name_and_exp_in_statement954);
                    define_gealg_name_and_exp94=define_gealg_name_and_exp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, define_gealg_name_and_exp94.getTree());

                    }
                    break;
                case 5 :
                    // GaaletParser.g:178:5: if_statement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_if_statement_in_statement964);
                    if_statement95=if_statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_statement95.getTree());

                    }
                    break;
                case 6 :
                    // GaaletParser.g:179:4: set_output
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_set_output_in_statement969);
                    set_output96=set_output();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, set_output96.getTree());

                    }
                    break;
                case 7 :
                    // GaaletParser.g:180:5: macro_definition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_macro_definition_in_statement975);
                    macro_definition97=macro_definition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, macro_definition97.getTree());

                    }
                    break;
                case 8 :
                    // GaaletParser.g:181:5: loop
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_loop_in_statement981);
                    loop98=loop();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, loop98.getTree());

                    }
                    break;
                case 9 :
                    // GaaletParser.g:182:5: BREAK
                    {
                    root_0 = (Object)adaptor.nil();

                    BREAK99=(Token)match(input,BREAK,FOLLOW_BREAK_in_statement987); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BREAK99_tree = (Object)adaptor.create(BREAK99);
                    adaptor.addChild(root_0, BREAK99_tree);
                    }

                    }
                    break;
                case 10 :
                    // GaaletParser.g:183:5: pragma
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pragma_in_statement993);
                    pragma100=pragma();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pragma100.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 22, statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "block"
    // GaaletParser.g:186:1: block : CLBRACKET ( statement )* CRBRACKET -> ^( BLOCK ( statement )* ) ;
    public final GaaletParser.block_return block() throws RecognitionException {
        GaaletParser.block_return retval = new GaaletParser.block_return();
        retval.start = input.LT(1);
        int block_StartIndex = input.index();
        Object root_0 = null;

        Token CLBRACKET101=null;
        Token CRBRACKET103=null;
        GaaletParser.statement_return statement102 = null;


        Object CLBRACKET101_tree=null;
        Object CRBRACKET103_tree=null;
        RewriteRuleTokenStream stream_CRBRACKET=new RewriteRuleTokenStream(adaptor,"token CRBRACKET");
        RewriteRuleTokenStream stream_CLBRACKET=new RewriteRuleTokenStream(adaptor,"token CLBRACKET");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // GaaletParser.g:187:3: ( CLBRACKET ( statement )* CRBRACKET -> ^( BLOCK ( statement )* ) )
            // GaaletParser.g:187:5: CLBRACKET ( statement )* CRBRACKET
            {
            CLBRACKET101=(Token)match(input,CLBRACKET,FOLLOW_CLBRACKET_in_block1009); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CLBRACKET.add(CLBRACKET101);

            // GaaletParser.g:187:15: ( statement )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==DECIMAL_LITERAL||(LA24_0>=LOOP && LA24_0<=GEALG_MV)||(LA24_0>=DOUBLE && LA24_0<=MINUS)||LA24_0==IF||LA24_0==IDENTIFIER||LA24_0==LBRACKET||LA24_0==FLOATING_POINT_LITERAL||LA24_0==CLBRACKET||(LA24_0>=REVERSE && LA24_0<=NOT)||LA24_0==SEMICOLON||LA24_0==SET_OUTPUT||LA24_0==PRAGMA||LA24_0==ARGUMENT_PREFIX) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // GaaletParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block1011);
            	    statement102=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement102.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            CRBRACKET103=(Token)match(input,CRBRACKET,FOLLOW_CRBRACKET_in_block1014); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CRBRACKET.add(CRBRACKET103);



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
            // 187:36: -> ^( BLOCK ( statement )* )
            {
                // GaaletParser.g:187:39: ^( BLOCK ( statement )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCK, "BLOCK"), root_1);

                // GaaletParser.g:187:47: ( statement )*
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
            if ( state.backtracking>0 ) { memoize(input, 23, block_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "block"

    public static class float_or_dec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "float_or_dec"
    // GaaletParser.g:191:10: fragment float_or_dec : ( ( MINUS )? DECIMAL_LITERAL | float_literal );
    public final GaaletParser.float_or_dec_return float_or_dec() throws RecognitionException {
        GaaletParser.float_or_dec_return retval = new GaaletParser.float_or_dec_return();
        retval.start = input.LT(1);
        int float_or_dec_StartIndex = input.index();
        Object root_0 = null;

        Token MINUS104=null;
        Token DECIMAL_LITERAL105=null;
        GaaletParser.float_literal_return float_literal106 = null;


        Object MINUS104_tree=null;
        Object DECIMAL_LITERAL105_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // GaaletParser.g:192:3: ( ( MINUS )? DECIMAL_LITERAL | float_literal )
            int alt26=2;
            switch ( input.LA(1) ) {
            case MINUS:
                {
                int LA26_1 = input.LA(2);

                if ( (LA26_1==DECIMAL_LITERAL) ) {
                    alt26=1;
                }
                else if ( (LA26_1==FLOATING_POINT_LITERAL) ) {
                    alt26=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 1, input);

                    throw nvae;
                }
                }
                break;
            case DECIMAL_LITERAL:
                {
                alt26=1;
                }
                break;
            case FLOATING_POINT_LITERAL:
                {
                alt26=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // GaaletParser.g:192:5: ( MINUS )? DECIMAL_LITERAL
                    {
                    root_0 = (Object)adaptor.nil();

                    // GaaletParser.g:192:5: ( MINUS )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==MINUS) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // GaaletParser.g:192:6: MINUS
                            {
                            MINUS104=(Token)match(input,MINUS,FOLLOW_MINUS_in_float_or_dec1043); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            MINUS104_tree = (Object)adaptor.create(MINUS104);
                            adaptor.addChild(root_0, MINUS104_tree);
                            }

                            }
                            break;

                    }

                    DECIMAL_LITERAL105=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_float_or_dec1047); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DECIMAL_LITERAL105_tree = (Object)adaptor.create(DECIMAL_LITERAL105);
                    adaptor.addChild(root_0, DECIMAL_LITERAL105_tree);
                    }

                    }
                    break;
                case 2 :
                    // GaaletParser.g:193:5: float_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_float_literal_in_float_or_dec1053);
                    float_literal106=float_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, float_literal106.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 24, float_or_dec_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "float_or_dec"

    public static class macro_definition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "macro_definition"
    // GaaletParser.g:196:1: macro_definition : id= IDENTIFIER EQUALS CLBRACKET ( statement )* (e= return_value )? CRBRACKET -> ^( MACRO $id ( statement )* ( $e)? ) ;
    public final GaaletParser.macro_definition_return macro_definition() throws RecognitionException {
        GaaletParser.macro_definition_return retval = new GaaletParser.macro_definition_return();
        retval.start = input.LT(1);
        int macro_definition_StartIndex = input.index();
        Object root_0 = null;

        Token id=null;
        Token EQUALS107=null;
        Token CLBRACKET108=null;
        Token CRBRACKET110=null;
        GaaletParser.return_value_return e = null;

        GaaletParser.statement_return statement109 = null;


        Object id_tree=null;
        Object EQUALS107_tree=null;
        Object CLBRACKET108_tree=null;
        Object CRBRACKET110_tree=null;
        RewriteRuleTokenStream stream_CRBRACKET=new RewriteRuleTokenStream(adaptor,"token CRBRACKET");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_CLBRACKET=new RewriteRuleTokenStream(adaptor,"token CLBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_return_value=new RewriteRuleSubtreeStream(adaptor,"rule return_value");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // GaaletParser.g:197:3: (id= IDENTIFIER EQUALS CLBRACKET ( statement )* (e= return_value )? CRBRACKET -> ^( MACRO $id ( statement )* ( $e)? ) )
            // GaaletParser.g:197:5: id= IDENTIFIER EQUALS CLBRACKET ( statement )* (e= return_value )? CRBRACKET
            {
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_macro_definition1070); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(id);

            EQUALS107=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_macro_definition1072); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EQUALS.add(EQUALS107);

            CLBRACKET108=(Token)match(input,CLBRACKET,FOLLOW_CLBRACKET_in_macro_definition1074); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CLBRACKET.add(CLBRACKET108);

            // GaaletParser.g:197:36: ( statement )*
            loop27:
            do {
                int alt27=2;
                alt27 = dfa27.predict(input);
                switch (alt27) {
            	case 1 :
            	    // GaaletParser.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_macro_definition1076);
            	    statement109=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement109.getTree());

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            // GaaletParser.g:197:48: (e= return_value )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==DECIMAL_LITERAL||(LA28_0>=INVERSE && LA28_0<=MINUS)||LA28_0==IDENTIFIER||LA28_0==LBRACKET||LA28_0==FLOATING_POINT_LITERAL||(LA28_0>=REVERSE && LA28_0<=NOT)||LA28_0==ARGUMENT_PREFIX) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // GaaletParser.g:0:0: e= return_value
                    {
                    pushFollow(FOLLOW_return_value_in_macro_definition1081);
                    e=return_value();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_return_value.add(e.getTree());

                    }
                    break;

            }

            CRBRACKET110=(Token)match(input,CRBRACKET,FOLLOW_CRBRACKET_in_macro_definition1084); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CRBRACKET.add(CRBRACKET110);



            // AST REWRITE
            // elements: statement, id, e
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
            // 197:73: -> ^( MACRO $id ( statement )* ( $e)? )
            {
                // GaaletParser.g:197:76: ^( MACRO $id ( statement )* ( $e)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(MACRO, "MACRO"), root_1);

                adaptor.addChild(root_1, stream_id.nextNode());
                // GaaletParser.g:197:88: ( statement )*
                while ( stream_statement.hasNext() ) {
                    adaptor.addChild(root_1, stream_statement.nextTree());

                }
                stream_statement.reset();
                // GaaletParser.g:197:99: ( $e)?
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
            if ( state.backtracking>0 ) { memoize(input, 25, macro_definition_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "macro_definition"

    public static class return_value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "return_value"
    // GaaletParser.g:200:1: return_value : (add= additive_expression -> ^( RETURN $add) | or= logical_or_expression -> ^( RETURN $or) );
    public final GaaletParser.return_value_return return_value() throws RecognitionException {
        GaaletParser.return_value_return retval = new GaaletParser.return_value_return();
        retval.start = input.LT(1);
        int return_value_StartIndex = input.index();
        Object root_0 = null;

        GaaletParser.additive_expression_return add = null;

        GaaletParser.logical_or_expression_return or = null;


        RewriteRuleSubtreeStream stream_additive_expression=new RewriteRuleSubtreeStream(adaptor,"rule additive_expression");
        RewriteRuleSubtreeStream stream_logical_or_expression=new RewriteRuleSubtreeStream(adaptor,"rule logical_or_expression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // GaaletParser.g:201:3: (add= additive_expression -> ^( RETURN $add) | or= logical_or_expression -> ^( RETURN $or) )
            int alt29=2;
            alt29 = dfa29.predict(input);
            switch (alt29) {
                case 1 :
                    // GaaletParser.g:201:5: add= additive_expression
                    {
                    pushFollow(FOLLOW_additive_expression_in_return_value1117);
                    add=additive_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_additive_expression.add(add.getTree());


                    // AST REWRITE
                    // elements: add
                    // token labels: 
                    // rule labels: retval, add
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_add=new RewriteRuleSubtreeStream(adaptor,"rule add",add!=null?add.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 201:29: -> ^( RETURN $add)
                    {
                        // GaaletParser.g:201:32: ^( RETURN $add)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(RETURN, "RETURN"), root_1);

                        adaptor.addChild(root_1, stream_add.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // GaaletParser.g:202:5: or= logical_or_expression
                    {
                    pushFollow(FOLLOW_logical_or_expression_in_return_value1134);
                    or=logical_or_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_logical_or_expression.add(or.getTree());


                    // AST REWRITE
                    // elements: or
                    // token labels: 
                    // rule labels: retval, or
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_or=new RewriteRuleSubtreeStream(adaptor,"rule or",or!=null?or.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 202:30: -> ^( RETURN $or)
                    {
                        // GaaletParser.g:202:33: ^( RETURN $or)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(RETURN, "RETURN"), root_1);

                        adaptor.addChild(root_1, stream_or.nextTree());

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
            if ( state.backtracking>0 ) { memoize(input, 26, return_value_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "return_value"

    public static class loop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "loop"
    // GaaletParser.g:205:1: loop : ( PRAGMA UNROLL_LITERAL number= DECIMAL_LITERAL | PRAGMA COUNT_LITERAL varname= IDENTIFIER )? LOOP stmt= statement -> ^( LOOP $stmt ( $number)? ( $varname)? ) ;
    public final GaaletParser.loop_return loop() throws RecognitionException {
        GaaletParser.loop_return retval = new GaaletParser.loop_return();
        retval.start = input.LT(1);
        int loop_StartIndex = input.index();
        Object root_0 = null;

        Token number=null;
        Token varname=null;
        Token PRAGMA111=null;
        Token UNROLL_LITERAL112=null;
        Token PRAGMA113=null;
        Token COUNT_LITERAL114=null;
        Token LOOP115=null;
        GaaletParser.statement_return stmt = null;


        Object number_tree=null;
        Object varname_tree=null;
        Object PRAGMA111_tree=null;
        Object UNROLL_LITERAL112_tree=null;
        Object PRAGMA113_tree=null;
        Object COUNT_LITERAL114_tree=null;
        Object LOOP115_tree=null;
        RewriteRuleTokenStream stream_UNROLL_LITERAL=new RewriteRuleTokenStream(adaptor,"token UNROLL_LITERAL");
        RewriteRuleTokenStream stream_DECIMAL_LITERAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL_LITERAL");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_COUNT_LITERAL=new RewriteRuleTokenStream(adaptor,"token COUNT_LITERAL");
        RewriteRuleTokenStream stream_LOOP=new RewriteRuleTokenStream(adaptor,"token LOOP");
        RewriteRuleTokenStream stream_PRAGMA=new RewriteRuleTokenStream(adaptor,"token PRAGMA");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // GaaletParser.g:206:3: ( ( PRAGMA UNROLL_LITERAL number= DECIMAL_LITERAL | PRAGMA COUNT_LITERAL varname= IDENTIFIER )? LOOP stmt= statement -> ^( LOOP $stmt ( $number)? ( $varname)? ) )
            // GaaletParser.g:206:5: ( PRAGMA UNROLL_LITERAL number= DECIMAL_LITERAL | PRAGMA COUNT_LITERAL varname= IDENTIFIER )? LOOP stmt= statement
            {
            // GaaletParser.g:206:5: ( PRAGMA UNROLL_LITERAL number= DECIMAL_LITERAL | PRAGMA COUNT_LITERAL varname= IDENTIFIER )?
            int alt30=3;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==PRAGMA) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==UNROLL_LITERAL) ) {
                    alt30=1;
                }
                else if ( (LA30_1==COUNT_LITERAL) ) {
                    alt30=2;
                }
            }
            switch (alt30) {
                case 1 :
                    // GaaletParser.g:206:6: PRAGMA UNROLL_LITERAL number= DECIMAL_LITERAL
                    {
                    PRAGMA111=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_loop1159); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_PRAGMA.add(PRAGMA111);

                    UNROLL_LITERAL112=(Token)match(input,UNROLL_LITERAL,FOLLOW_UNROLL_LITERAL_in_loop1161); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_UNROLL_LITERAL.add(UNROLL_LITERAL112);

                    number=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_loop1165); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_DECIMAL_LITERAL.add(number);


                    }
                    break;
                case 2 :
                    // GaaletParser.g:207:7: PRAGMA COUNT_LITERAL varname= IDENTIFIER
                    {
                    PRAGMA113=(Token)match(input,PRAGMA,FOLLOW_PRAGMA_in_loop1173); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_PRAGMA.add(PRAGMA113);

                    COUNT_LITERAL114=(Token)match(input,COUNT_LITERAL,FOLLOW_COUNT_LITERAL_in_loop1175); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_COUNT_LITERAL.add(COUNT_LITERAL114);

                    varname=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_loop1179); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IDENTIFIER.add(varname);


                    }
                    break;

            }

            LOOP115=(Token)match(input,LOOP,FOLLOW_LOOP_in_loop1183); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LOOP.add(LOOP115);

            pushFollow(FOLLOW_statement_in_loop1187);
            stmt=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(stmt.getTree());


            // AST REWRITE
            // elements: varname, LOOP, number, stmt
            // token labels: number, varname
            // rule labels: retval, stmt
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_number=new RewriteRuleTokenStream(adaptor,"token number",number);
            RewriteRuleTokenStream stream_varname=new RewriteRuleTokenStream(adaptor,"token varname",varname);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_stmt=new RewriteRuleSubtreeStream(adaptor,"rule stmt",stmt!=null?stmt.tree:null);

            root_0 = (Object)adaptor.nil();
            // 207:69: -> ^( LOOP $stmt ( $number)? ( $varname)? )
            {
                // GaaletParser.g:207:72: ^( LOOP $stmt ( $number)? ( $varname)? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_LOOP.nextNode(), root_1);

                adaptor.addChild(root_1, stream_stmt.nextTree());
                // GaaletParser.g:207:85: ( $number)?
                if ( stream_number.hasNext() ) {
                    adaptor.addChild(root_1, stream_number.nextNode());

                }
                stream_number.reset();
                // GaaletParser.g:207:94: ( $varname)?
                if ( stream_varname.hasNext() ) {
                    adaptor.addChild(root_1, stream_varname.nextNode());

                }
                stream_varname.reset();

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
            if ( state.backtracking>0 ) { memoize(input, 27, loop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "loop"

    public static class if_statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_statement"
    // GaaletParser.g:210:1: if_statement : IF LBRACKET condition= logical_or_expression RBRACKET then_part= statement ( else_part )? -> ^( IF $condition $then_part ( else_part )? ) ;
    public final GaaletParser.if_statement_return if_statement() throws RecognitionException {
        GaaletParser.if_statement_return retval = new GaaletParser.if_statement_return();
        retval.start = input.LT(1);
        int if_statement_StartIndex = input.index();
        Object root_0 = null;

        Token IF116=null;
        Token LBRACKET117=null;
        Token RBRACKET118=null;
        GaaletParser.logical_or_expression_return condition = null;

        GaaletParser.statement_return then_part = null;

        GaaletParser.else_part_return else_part119 = null;


        Object IF116_tree=null;
        Object LBRACKET117_tree=null;
        Object RBRACKET118_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_logical_or_expression=new RewriteRuleSubtreeStream(adaptor,"rule logical_or_expression");
        RewriteRuleSubtreeStream stream_else_part=new RewriteRuleSubtreeStream(adaptor,"rule else_part");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // GaaletParser.g:211:3: ( IF LBRACKET condition= logical_or_expression RBRACKET then_part= statement ( else_part )? -> ^( IF $condition $then_part ( else_part )? ) )
            // GaaletParser.g:211:5: IF LBRACKET condition= logical_or_expression RBRACKET then_part= statement ( else_part )?
            {
            IF116=(Token)match(input,IF,FOLLOW_IF_in_if_statement1222); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IF.add(IF116);

            LBRACKET117=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_if_statement1224); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET117);

            pushFollow(FOLLOW_logical_or_expression_in_if_statement1228);
            condition=logical_or_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_logical_or_expression.add(condition.getTree());
            RBRACKET118=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_if_statement1230); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET118);

            pushFollow(FOLLOW_statement_in_if_statement1243);
            then_part=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(then_part.getTree());
            // GaaletParser.g:213:5: ( else_part )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==ELSE) ) {
                int LA31_1 = input.LA(2);

                if ( (synpred53_GaaletParser()) ) {
                    alt31=1;
                }
            }
            switch (alt31) {
                case 1 :
                    // GaaletParser.g:213:6: else_part
                    {
                    pushFollow(FOLLOW_else_part_in_if_statement1288);
                    else_part119=else_part();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_else_part.add(else_part119.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: condition, IF, else_part, then_part
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
            // 214:5: -> ^( IF $condition $then_part ( else_part )? )
            {
                // GaaletParser.g:214:8: ^( IF $condition $then_part ( else_part )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_IF.nextNode(), root_1);

                adaptor.addChild(root_1, stream_condition.nextTree());
                adaptor.addChild(root_1, stream_then_part.nextTree());
                // GaaletParser.g:214:35: ( else_part )?
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
            if ( state.backtracking>0 ) { memoize(input, 28, if_statement_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "if_statement"

    public static class else_part_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "else_part"
    // GaaletParser.g:217:1: else_part : ( ELSE block -> ^( ELSE block ) | ELSE stmt= if_statement -> ^( ELSEIF $stmt) );
    public final GaaletParser.else_part_return else_part() throws RecognitionException {
        GaaletParser.else_part_return retval = new GaaletParser.else_part_return();
        retval.start = input.LT(1);
        int else_part_StartIndex = input.index();
        Object root_0 = null;

        Token ELSE120=null;
        Token ELSE122=null;
        GaaletParser.if_statement_return stmt = null;

        GaaletParser.block_return block121 = null;


        Object ELSE120_tree=null;
        Object ELSE122_tree=null;
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleSubtreeStream stream_if_statement=new RewriteRuleSubtreeStream(adaptor,"rule if_statement");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // GaaletParser.g:218:3: ( ELSE block -> ^( ELSE block ) | ELSE stmt= if_statement -> ^( ELSEIF $stmt) )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==ELSE) ) {
                int LA32_1 = input.LA(2);

                if ( (LA32_1==IF) ) {
                    alt32=2;
                }
                else if ( (LA32_1==CLBRACKET) ) {
                    alt32=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // GaaletParser.g:218:5: ELSE block
                    {
                    ELSE120=(Token)match(input,ELSE,FOLLOW_ELSE_in_else_part1391); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ELSE.add(ELSE120);

                    pushFollow(FOLLOW_block_in_else_part1393);
                    block121=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_block.add(block121.getTree());


                    // AST REWRITE
                    // elements: ELSE, block
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 218:16: -> ^( ELSE block )
                    {
                        // GaaletParser.g:218:19: ^( ELSE block )
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
                    // GaaletParser.g:219:5: ELSE stmt= if_statement
                    {
                    ELSE122=(Token)match(input,ELSE,FOLLOW_ELSE_in_else_part1407); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ELSE.add(ELSE122);

                    pushFollow(FOLLOW_if_statement_in_else_part1411);
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
                    // 219:28: -> ^( ELSEIF $stmt)
                    {
                        // GaaletParser.g:219:31: ^( ELSEIF $stmt)
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
            if ( state.backtracking>0 ) { memoize(input, 29, else_part_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "else_part"

    public static class hex_literal_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "hex_literal"
    // GaaletParser.g:224:1: hex_literal : HEX ;
    public final GaaletParser.hex_literal_return hex_literal() throws RecognitionException {
        GaaletParser.hex_literal_return retval = new GaaletParser.hex_literal_return();
        retval.start = input.LT(1);
        int hex_literal_StartIndex = input.index();
        Object root_0 = null;

        Token HEX123=null;

        Object HEX123_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // GaaletParser.g:225:3: ( HEX )
            // GaaletParser.g:225:5: HEX
            {
            root_0 = (Object)adaptor.nil();

            HEX123=(Token)match(input,HEX,FOLLOW_HEX_in_hex_literal1439); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            HEX123_tree = (Object)adaptor.create(HEX123);
            adaptor.addChild(root_0, HEX123_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 30, hex_literal_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "hex_literal"

    public static class function_argument_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_argument"
    // GaaletParser.g:228:1: function_argument : ARGUMENT_PREFIX index= DECIMAL_LITERAL RBRACKET -> ^( ARGUMENT $index) ;
    public final GaaletParser.function_argument_return function_argument() throws RecognitionException {
        GaaletParser.function_argument_return retval = new GaaletParser.function_argument_return();
        retval.start = input.LT(1);
        int function_argument_StartIndex = input.index();
        Object root_0 = null;

        Token index=null;
        Token ARGUMENT_PREFIX124=null;
        Token RBRACKET125=null;

        Object index_tree=null;
        Object ARGUMENT_PREFIX124_tree=null;
        Object RBRACKET125_tree=null;
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_ARGUMENT_PREFIX=new RewriteRuleTokenStream(adaptor,"token ARGUMENT_PREFIX");
        RewriteRuleTokenStream stream_DECIMAL_LITERAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL_LITERAL");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // GaaletParser.g:229:3: ( ARGUMENT_PREFIX index= DECIMAL_LITERAL RBRACKET -> ^( ARGUMENT $index) )
            // GaaletParser.g:229:5: ARGUMENT_PREFIX index= DECIMAL_LITERAL RBRACKET
            {
            ARGUMENT_PREFIX124=(Token)match(input,ARGUMENT_PREFIX,FOLLOW_ARGUMENT_PREFIX_in_function_argument1455); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ARGUMENT_PREFIX.add(ARGUMENT_PREFIX124);

            index=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_function_argument1459); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DECIMAL_LITERAL.add(index);

            RBRACKET125=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_function_argument1461); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET125);



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
            // 229:52: -> ^( ARGUMENT $index)
            {
                // GaaletParser.g:229:55: ^( ARGUMENT $index)
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
            if ( state.backtracking>0 ) { memoize(input, 31, function_argument_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function_argument"

    public static class gaalet_arguments_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "gaalet_arguments"
    // GaaletParser.g:232:1: gaalet_arguments : ( ( DECIMAL_LITERAL ( COMMA DECIMAL_LITERAL )* ) | ( hex_literal ( COMMA hex_literal )* ) );
    public final GaaletParser.gaalet_arguments_return gaalet_arguments() throws RecognitionException {
        GaaletParser.gaalet_arguments_return retval = new GaaletParser.gaalet_arguments_return();
        retval.start = input.LT(1);
        int gaalet_arguments_StartIndex = input.index();
        Object root_0 = null;

        Token DECIMAL_LITERAL126=null;
        Token COMMA127=null;
        Token DECIMAL_LITERAL128=null;
        Token COMMA130=null;
        GaaletParser.hex_literal_return hex_literal129 = null;

        GaaletParser.hex_literal_return hex_literal131 = null;


        Object DECIMAL_LITERAL126_tree=null;
        Object COMMA127_tree=null;
        Object DECIMAL_LITERAL128_tree=null;
        Object COMMA130_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // GaaletParser.g:233:3: ( ( DECIMAL_LITERAL ( COMMA DECIMAL_LITERAL )* ) | ( hex_literal ( COMMA hex_literal )* ) )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==DECIMAL_LITERAL) ) {
                alt35=1;
            }
            else if ( (LA35_0==HEX) ) {
                alt35=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // GaaletParser.g:233:5: ( DECIMAL_LITERAL ( COMMA DECIMAL_LITERAL )* )
                    {
                    root_0 = (Object)adaptor.nil();

                    // GaaletParser.g:233:5: ( DECIMAL_LITERAL ( COMMA DECIMAL_LITERAL )* )
                    // GaaletParser.g:233:6: DECIMAL_LITERAL ( COMMA DECIMAL_LITERAL )*
                    {
                    DECIMAL_LITERAL126=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_gaalet_arguments1486); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DECIMAL_LITERAL126_tree = (Object)adaptor.create(DECIMAL_LITERAL126);
                    adaptor.addChild(root_0, DECIMAL_LITERAL126_tree);
                    }
                    // GaaletParser.g:233:22: ( COMMA DECIMAL_LITERAL )*
                    loop33:
                    do {
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==COMMA) ) {
                            alt33=1;
                        }


                        switch (alt33) {
                    	case 1 :
                    	    // GaaletParser.g:233:23: COMMA DECIMAL_LITERAL
                    	    {
                    	    COMMA127=(Token)match(input,COMMA,FOLLOW_COMMA_in_gaalet_arguments1489); if (state.failed) return retval;
                    	    DECIMAL_LITERAL128=(Token)match(input,DECIMAL_LITERAL,FOLLOW_DECIMAL_LITERAL_in_gaalet_arguments1492); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DECIMAL_LITERAL128_tree = (Object)adaptor.create(DECIMAL_LITERAL128);
                    	    adaptor.addChild(root_0, DECIMAL_LITERAL128_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop33;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // GaaletParser.g:234:5: ( hex_literal ( COMMA hex_literal )* )
                    {
                    root_0 = (Object)adaptor.nil();

                    // GaaletParser.g:234:5: ( hex_literal ( COMMA hex_literal )* )
                    // GaaletParser.g:234:6: hex_literal ( COMMA hex_literal )*
                    {
                    pushFollow(FOLLOW_hex_literal_in_gaalet_arguments1502);
                    hex_literal129=hex_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, hex_literal129.getTree());
                    // GaaletParser.g:234:18: ( COMMA hex_literal )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==COMMA) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // GaaletParser.g:234:19: COMMA hex_literal
                    	    {
                    	    COMMA130=(Token)match(input,COMMA,FOLLOW_COMMA_in_gaalet_arguments1505); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_hex_literal_in_gaalet_arguments1508);
                    	    hex_literal131=hex_literal();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, hex_literal131.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);


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
            if ( state.backtracking>0 ) { memoize(input, 32, gaalet_arguments_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "gaalet_arguments"

    public static class define_gealg_name_and_exp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "define_gealg_name_and_exp"
    // GaaletParser.g:238:1: define_gealg_name_and_exp : GEALG_MV LESS blades= gaalet_arguments GREATER GEALG_TYPE name= IDENTIFIER ( EQUALS ( ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) ) | ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) ) ) | SEMICOLON -> ^( DEFINE_MV $name $blades) ) ;
    public final GaaletParser.define_gealg_name_and_exp_return define_gealg_name_and_exp() throws RecognitionException {
        GaaletParser.define_gealg_name_and_exp_return retval = new GaaletParser.define_gealg_name_and_exp_return();
        retval.start = input.LT(1);
        int define_gealg_name_and_exp_StartIndex = input.index();
        Object root_0 = null;

        Token name=null;
        Token GEALG_MV132=null;
        Token LESS133=null;
        Token GREATER134=null;
        Token GEALG_TYPE135=null;
        Token EQUALS136=null;
        Token SEMICOLON137=null;
        Token CLBRACKET138=null;
        Token CRBRACKET139=null;
        Token SEMICOLON140=null;
        Token SEMICOLON141=null;
        GaaletParser.gaalet_arguments_return blades = null;

        GaaletParser.expression_return value = null;

        GaaletParser.argument_expression_list_return values = null;


        Object name_tree=null;
        Object GEALG_MV132_tree=null;
        Object LESS133_tree=null;
        Object GREATER134_tree=null;
        Object GEALG_TYPE135_tree=null;
        Object EQUALS136_tree=null;
        Object SEMICOLON137_tree=null;
        Object CLBRACKET138_tree=null;
        Object CRBRACKET139_tree=null;
        Object SEMICOLON140_tree=null;
        Object SEMICOLON141_tree=null;
        RewriteRuleTokenStream stream_CRBRACKET=new RewriteRuleTokenStream(adaptor,"token CRBRACKET");
        RewriteRuleTokenStream stream_GREATER=new RewriteRuleTokenStream(adaptor,"token GREATER");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_GEALG_MV=new RewriteRuleTokenStream(adaptor,"token GEALG_MV");
        RewriteRuleTokenStream stream_CLBRACKET=new RewriteRuleTokenStream(adaptor,"token CLBRACKET");
        RewriteRuleTokenStream stream_GEALG_TYPE=new RewriteRuleTokenStream(adaptor,"token GEALG_TYPE");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_LESS=new RewriteRuleTokenStream(adaptor,"token LESS");
        RewriteRuleSubtreeStream stream_gaalet_arguments=new RewriteRuleSubtreeStream(adaptor,"rule gaalet_arguments");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_argument_expression_list=new RewriteRuleSubtreeStream(adaptor,"rule argument_expression_list");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // GaaletParser.g:239:3: ( GEALG_MV LESS blades= gaalet_arguments GREATER GEALG_TYPE name= IDENTIFIER ( EQUALS ( ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) ) | ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) ) ) | SEMICOLON -> ^( DEFINE_MV $name $blades) ) )
            // GaaletParser.g:239:5: GEALG_MV LESS blades= gaalet_arguments GREATER GEALG_TYPE name= IDENTIFIER ( EQUALS ( ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) ) | ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) ) ) | SEMICOLON -> ^( DEFINE_MV $name $blades) )
            {
            GEALG_MV132=(Token)match(input,GEALG_MV,FOLLOW_GEALG_MV_in_define_gealg_name_and_exp1529); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_GEALG_MV.add(GEALG_MV132);

            LESS133=(Token)match(input,LESS,FOLLOW_LESS_in_define_gealg_name_and_exp1538); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LESS.add(LESS133);

            pushFollow(FOLLOW_gaalet_arguments_in_define_gealg_name_and_exp1544);
            blades=gaalet_arguments();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_gaalet_arguments.add(blades.getTree());
            GREATER134=(Token)match(input,GREATER,FOLLOW_GREATER_in_define_gealg_name_and_exp1547); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_GREATER.add(GREATER134);

            GEALG_TYPE135=(Token)match(input,GEALG_TYPE,FOLLOW_GEALG_TYPE_in_define_gealg_name_and_exp1555); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_GEALG_TYPE.add(GEALG_TYPE135);

            name=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_define_gealg_name_and_exp1559); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IDENTIFIER.add(name);

            // GaaletParser.g:242:5: ( EQUALS ( ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) ) | ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) ) ) | SEMICOLON -> ^( DEFINE_MV $name $blades) )
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==EQUALS) ) {
                alt37=1;
            }
            else if ( (LA37_0==SEMICOLON) ) {
                alt37=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // GaaletParser.g:242:9: EQUALS ( ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) ) | ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) ) )
                    {
                    EQUALS136=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_define_gealg_name_and_exp1573); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EQUALS.add(EQUALS136);

                    // GaaletParser.g:242:16: ( ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) ) | ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) ) )
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==DECIMAL_LITERAL||(LA36_0>=DOUBLE && LA36_0<=MINUS)||LA36_0==IDENTIFIER||LA36_0==LBRACKET||LA36_0==FLOATING_POINT_LITERAL||(LA36_0>=REVERSE && LA36_0<=NOT)||LA36_0==ARGUMENT_PREFIX) ) {
                        alt36=1;
                    }
                    else if ( (LA36_0==CLBRACKET) ) {
                        alt36=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 0, input);

                        throw nvae;
                    }
                    switch (alt36) {
                        case 1 :
                            // GaaletParser.g:242:17: ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) )
                            {
                            // GaaletParser.g:242:17: ( (value= expression SEMICOLON ) -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value) )
                            // GaaletParser.g:242:19: (value= expression SEMICOLON )
                            {
                            // GaaletParser.g:242:19: (value= expression SEMICOLON )
                            // GaaletParser.g:242:20: value= expression SEMICOLON
                            {
                            pushFollow(FOLLOW_expression_in_define_gealg_name_and_exp1581);
                            value=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(value.getTree());
                            SEMICOLON137=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_define_gealg_name_and_exp1583); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON137);


                            }



                            // AST REWRITE
                            // elements: value, name, blades
                            // token labels: name
                            // rule labels: retval, blades, value
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_blades=new RewriteRuleSubtreeStream(adaptor,"rule blades",blades!=null?blades.tree:null);
                            RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value",value!=null?value.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 243:23: -> ^( DEFINE_MV_AND_ASSIGN $name $blades $value)
                            {
                                // GaaletParser.g:243:26: ^( DEFINE_MV_AND_ASSIGN $name $blades $value)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINE_MV_AND_ASSIGN, "DEFINE_MV_AND_ASSIGN"), root_1);

                                adaptor.addChild(root_1, stream_name.nextNode());
                                adaptor.addChild(root_1, stream_blades.nextTree());
                                adaptor.addChild(root_1, stream_value.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }


                            }
                            break;
                        case 2 :
                            // GaaletParser.g:244:20: ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) )
                            {
                            // GaaletParser.g:244:20: ( ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON ) -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values) )
                            // GaaletParser.g:244:21: ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON )
                            {
                            // GaaletParser.g:244:21: ( CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON )
                            // GaaletParser.g:244:22: CLBRACKET values= argument_expression_list CRBRACKET SEMICOLON
                            {
                            CLBRACKET138=(Token)match(input,CLBRACKET,FOLLOW_CLBRACKET_in_define_gealg_name_and_exp1646); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_CLBRACKET.add(CLBRACKET138);

                            pushFollow(FOLLOW_argument_expression_list_in_define_gealg_name_and_exp1650);
                            values=argument_expression_list();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_argument_expression_list.add(values.getTree());
                            CRBRACKET139=(Token)match(input,CRBRACKET,FOLLOW_CRBRACKET_in_define_gealg_name_and_exp1652); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_CRBRACKET.add(CRBRACKET139);

                            SEMICOLON140=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_define_gealg_name_and_exp1654); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON140);


                            }



                            // AST REWRITE
                            // elements: name, blades, values
                            // token labels: name
                            // rule labels: retval, values, blades
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_values=new RewriteRuleSubtreeStream(adaptor,"rule values",values!=null?values.tree:null);
                            RewriteRuleSubtreeStream stream_blades=new RewriteRuleSubtreeStream(adaptor,"rule blades",blades!=null?blades.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 245:23: -> ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values)
                            {
                                // GaaletParser.g:245:26: ^( DEFINE_GEALG $name $blades ARG_LIST_SEP $values)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINE_GEALG, "DEFINE_GEALG"), root_1);

                                adaptor.addChild(root_1, stream_name.nextNode());
                                adaptor.addChild(root_1, stream_blades.nextTree());
                                adaptor.addChild(root_1, (Object)adaptor.create(ARG_LIST_SEP, "ARG_LIST_SEP"));
                                adaptor.addChild(root_1, stream_values.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GaaletParser.g:247:9: SEMICOLON
                    {
                    SEMICOLON141=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_define_gealg_name_and_exp1730); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON141);



                    // AST REWRITE
                    // elements: blades, name
                    // token labels: name
                    // rule labels: retval, blades
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_blades=new RewriteRuleSubtreeStream(adaptor,"rule blades",blades!=null?blades.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 248:11: -> ^( DEFINE_MV $name $blades)
                    {
                        // GaaletParser.g:248:14: ^( DEFINE_MV $name $blades)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINE_MV, "DEFINE_MV"), root_1);

                        adaptor.addChild(root_1, stream_name.nextNode());
                        adaptor.addChild(root_1, stream_blades.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

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
            if ( state.backtracking>0 ) { memoize(input, 33, define_gealg_name_and_exp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "define_gealg_name_and_exp"

    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // GaaletParser.g:252:1: type : ( ( ( SIGNED | UNSIGNED ) ( FLOAT | INTEGER | DOUBLE ) ) | ( FLOAT | INTEGER | DOUBLE ) | AUTO );
    public final GaaletParser.type_return type() throws RecognitionException {
        GaaletParser.type_return retval = new GaaletParser.type_return();
        retval.start = input.LT(1);
        int type_StartIndex = input.index();
        Object root_0 = null;

        Token set142=null;
        Token set143=null;
        Token set144=null;
        Token AUTO145=null;

        Object set142_tree=null;
        Object set143_tree=null;
        Object set144_tree=null;
        Object AUTO145_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // GaaletParser.g:253:3: ( ( ( SIGNED | UNSIGNED ) ( FLOAT | INTEGER | DOUBLE ) ) | ( FLOAT | INTEGER | DOUBLE ) | AUTO )
            int alt38=3;
            switch ( input.LA(1) ) {
            case UNSIGNED:
            case SIGNED:
                {
                alt38=1;
                }
                break;
            case DOUBLE:
            case FLOAT:
            case INTEGER:
                {
                alt38=2;
                }
                break;
            case AUTO:
                {
                alt38=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }

            switch (alt38) {
                case 1 :
                    // GaaletParser.g:253:5: ( ( SIGNED | UNSIGNED ) ( FLOAT | INTEGER | DOUBLE ) )
                    {
                    root_0 = (Object)adaptor.nil();

                    // GaaletParser.g:253:5: ( ( SIGNED | UNSIGNED ) ( FLOAT | INTEGER | DOUBLE ) )
                    // GaaletParser.g:253:6: ( SIGNED | UNSIGNED ) ( FLOAT | INTEGER | DOUBLE )
                    {
                    set142=(Token)input.LT(1);
                    if ( (input.LA(1)>=UNSIGNED && input.LA(1)<=SIGNED) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set142));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    set143=(Token)input.LT(1);
                    if ( (input.LA(1)>=DOUBLE && input.LA(1)<=INTEGER) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set143));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }


                    }
                    break;
                case 2 :
                    // GaaletParser.g:254:5: ( FLOAT | INTEGER | DOUBLE )
                    {
                    root_0 = (Object)adaptor.nil();

                    set144=(Token)input.LT(1);
                    if ( (input.LA(1)>=DOUBLE && input.LA(1)<=INTEGER) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set144));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;
                case 3 :
                    // GaaletParser.g:255:5: AUTO
                    {
                    root_0 = (Object)adaptor.nil();

                    AUTO145=(Token)match(input,AUTO,FOLLOW_AUTO_in_type1821); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    AUTO145_tree = (Object)adaptor.create(AUTO145);
                    adaptor.addChild(root_0, AUTO145_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 34, type_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "type"

    public static class set_output_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "set_output"
    // GaaletParser.g:258:1: set_output : SET_OUTPUT value= lvalue -> ^( SET_OUTPUT $value) ;
    public final GaaletParser.set_output_return set_output() throws RecognitionException {
        GaaletParser.set_output_return retval = new GaaletParser.set_output_return();
        retval.start = input.LT(1);
        int set_output_StartIndex = input.index();
        Object root_0 = null;

        Token SET_OUTPUT146=null;
        GaaletParser.lvalue_return value = null;


        Object SET_OUTPUT146_tree=null;
        RewriteRuleTokenStream stream_SET_OUTPUT=new RewriteRuleTokenStream(adaptor,"token SET_OUTPUT");
        RewriteRuleSubtreeStream stream_lvalue=new RewriteRuleSubtreeStream(adaptor,"rule lvalue");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // GaaletParser.g:259:3: ( SET_OUTPUT value= lvalue -> ^( SET_OUTPUT $value) )
            // GaaletParser.g:259:5: SET_OUTPUT value= lvalue
            {
            SET_OUTPUT146=(Token)match(input,SET_OUTPUT,FOLLOW_SET_OUTPUT_in_set_output1836); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SET_OUTPUT.add(SET_OUTPUT146);

            pushFollow(FOLLOW_lvalue_in_set_output1840);
            value=lvalue();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_lvalue.add(value.getTree());


            // AST REWRITE
            // elements: SET_OUTPUT, value
            // token labels: 
            // rule labels: retval, value
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value",value!=null?value.tree:null);

            root_0 = (Object)adaptor.nil();
            // 259:30: -> ^( SET_OUTPUT $value)
            {
                // GaaletParser.g:259:33: ^( SET_OUTPUT $value)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_SET_OUTPUT.nextNode(), root_1);

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
            if ( state.backtracking>0 ) { memoize(input, 35, set_output_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "set_output"

    // $ANTLR start synpred5_GaaletParser
    public final void synpred5_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:77:5: ( type lvalue EQUALS expression )
        // GaaletParser.g:77:5: type lvalue EQUALS expression
        {
        pushFollow(FOLLOW_type_in_synpred5_GaaletParser258);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_lvalue_in_synpred5_GaaletParser260);
        lvalue();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUALS,FOLLOW_EQUALS_in_synpred5_GaaletParser262); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred5_GaaletParser264);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_GaaletParser

    // $ANTLR start synpred6_GaaletParser
    public final void synpred6_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:78:5: ( type lvalue )
        // GaaletParser.g:78:5: type lvalue
        {
        pushFollow(FOLLOW_type_in_synpred6_GaaletParser282);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_lvalue_in_synpred6_GaaletParser284);
        lvalue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_GaaletParser

    // $ANTLR start synpred7_GaaletParser
    public final void synpred7_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:79:5: ( lvalue EQUALS expression )
        // GaaletParser.g:79:5: lvalue EQUALS expression
        {
        pushFollow(FOLLOW_lvalue_in_synpred7_GaaletParser298);
        lvalue();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUALS,FOLLOW_EQUALS_in_synpred7_GaaletParser300); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred7_GaaletParser302);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_GaaletParser

    // $ANTLR start synpred8_GaaletParser
    public final void synpred8_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:80:5: ( logical_or_expression )
        // GaaletParser.g:80:5: logical_or_expression
        {
        pushFollow(FOLLOW_logical_or_expression_in_synpred8_GaaletParser321);
        logical_or_expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_GaaletParser

    // $ANTLR start synpred9_GaaletParser
    public final void synpred9_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:85:9: ( expression COMMA )
        // GaaletParser.g:85:9: expression COMMA
        {
        pushFollow(FOLLOW_expression_in_synpred9_GaaletParser348);
        expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,COMMA,FOLLOW_COMMA_in_synpred9_GaaletParser351); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_GaaletParser

    // $ANTLR start synpred29_GaaletParser
    public final void synpred29_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:135:5: ( function_call )
        // GaaletParser.g:135:5: function_call
        {
        pushFollow(FOLLOW_function_call_in_synpred29_GaaletParser727);
        function_call();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred29_GaaletParser

    // $ANTLR start synpred48_GaaletParser
    public final void synpred48_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:197:36: ( statement )
        // GaaletParser.g:197:36: statement
        {
        pushFollow(FOLLOW_statement_in_synpred48_GaaletParser1076);
        statement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred48_GaaletParser

    // $ANTLR start synpred50_GaaletParser
    public final void synpred50_GaaletParser_fragment() throws RecognitionException {   
        GaaletParser.additive_expression_return add = null;


        // GaaletParser.g:201:5: (add= additive_expression )
        // GaaletParser.g:201:5: add= additive_expression
        {
        pushFollow(FOLLOW_additive_expression_in_synpred50_GaaletParser1117);
        add=additive_expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred50_GaaletParser

    // $ANTLR start synpred53_GaaletParser
    public final void synpred53_GaaletParser_fragment() throws RecognitionException {   
        // GaaletParser.g:213:6: ( else_part )
        // GaaletParser.g:213:6: else_part
        {
        pushFollow(FOLLOW_else_part_in_synpred53_GaaletParser1288);
        else_part();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred53_GaaletParser

    // Delegated rules

    public final boolean synpred6_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred53_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred53_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred50_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred50_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred48_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred48_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred29_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred29_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_GaaletParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_GaaletParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_GaaletParser_fragment(); // can never throw exception
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
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA29 dfa29 = new DFA29(this);
    static final String DFA4_eotS =
        "\21\uffff";
    static final String DFA4_eofS =
        "\21\uffff";
    static final String DFA4_minS =
        "\1\4\13\0\5\uffff";
    static final String DFA4_maxS =
        "\1\134\13\0\5\uffff";
    static final String DFA4_acceptS =
        "\14\uffff\1\5\1\1\1\2\1\3\1\4";
    static final String DFA4_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\5\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\5\14\uffff\3\2\2\1\1\3\1\14\1\12\1\10\7\uffff\1\4\6\uffff"+
            "\1\7\2\uffff\1\5\11\uffff\1\11\1\13\45\uffff\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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
            return "75:1: expression : ( type lvalue EQUALS expression -> ^( DEFINE_ASSIGNMENT lvalue expression ) | type lvalue -> ^( DEFINE_V lvalue ) | lvalue EQUALS expression -> ^( EQUALS lvalue expression ) | logical_or_expression | eval );";
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
                        if ( (synpred5_GaaletParser()) ) {s = 13;}

                        else if ( (synpred6_GaaletParser()) ) {s = 14;}

                         
                        input.seek(index4_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_2 = input.LA(1);

                         
                        int index4_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_GaaletParser()) ) {s = 13;}

                        else if ( (synpred6_GaaletParser()) ) {s = 14;}

                         
                        input.seek(index4_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_3 = input.LA(1);

                         
                        int index4_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_GaaletParser()) ) {s = 13;}

                        else if ( (synpred6_GaaletParser()) ) {s = 14;}

                         
                        input.seek(index4_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_4 = input.LA(1);

                         
                        int index4_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA4_5 = input.LA(1);

                         
                        int index4_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA4_6 = input.LA(1);

                         
                        int index4_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA4_9 = input.LA(1);

                         
                        int index4_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA4_10 = input.LA(1);

                         
                        int index4_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA4_11 = input.LA(1);

                         
                        int index4_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_GaaletParser()) ) {s = 15;}

                        else if ( (synpred8_GaaletParser()) ) {s = 16;}

                         
                        input.seek(index4_11);
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
    static final String DFA5_eotS =
        "\17\uffff";
    static final String DFA5_eofS =
        "\17\uffff";
    static final String DFA5_minS =
        "\1\4\14\0\2\uffff";
    static final String DFA5_maxS =
        "\1\134\14\0\2\uffff";
    static final String DFA5_acceptS =
        "\15\uffff\1\1\1\2";
    static final String DFA5_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\2\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\5\14\uffff\3\2\2\1\1\3\1\14\1\12\1\10\7\uffff\1\4\6\uffff"+
            "\1\7\2\uffff\1\5\11\uffff\1\11\1\13\45\uffff\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "()* loopback of 85:8: ( expression COMMA )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_1 = input.LA(1);

                         
                        int index5_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_2 = input.LA(1);

                         
                        int index5_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_3 = input.LA(1);

                         
                        int index5_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_4 = input.LA(1);

                         
                        int index5_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA5_5 = input.LA(1);

                         
                        int index5_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA5_6 = input.LA(1);

                         
                        int index5_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA5_7 = input.LA(1);

                         
                        int index5_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA5_8 = input.LA(1);

                         
                        int index5_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA5_9 = input.LA(1);

                         
                        int index5_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA5_10 = input.LA(1);

                         
                        int index5_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA5_11 = input.LA(1);

                         
                        int index5_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA5_12 = input.LA(1);

                         
                        int index5_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GaaletParser()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 5, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA23_eotS =
        "\16\uffff";
    static final String DFA23_eofS =
        "\16\uffff";
    static final String DFA23_minS =
        "\1\4\3\uffff\1\31\3\uffff\1\10\2\uffff\1\4\2\uffff";
    static final String DFA23_maxS =
        "\1\134\3\uffff\1\105\3\uffff\1\14\2\uffff\1\134\2\uffff";
    static final String DFA23_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\4\1\5\1\6\1\uffff\1\10\1\11\1\uffff"+
        "\1\12\1\7";
    static final String DFA23_specialS =
        "\16\uffff}>";
    static final String[] DFA23_transitionS = {
            "\1\3\10\uffff\1\11\1\12\1\5\1\uffff\11\3\4\uffff\1\6\2\uffff"+
            "\1\4\6\uffff\1\3\2\uffff\1\3\7\uffff\1\2\1\uffff\2\3\1\uffff"+
            "\1\1\15\uffff\1\7\1\uffff\1\10\23\uffff\1\3",
            "",
            "",
            "",
            "\1\3\14\uffff\1\3\1\uffff\1\3\6\uffff\3\3\6\uffff\2\3\2\uffff"+
            "\4\3\1\13\5\3",
            "",
            "",
            "",
            "\2\14\2\11\1\14",
            "",
            "",
            "\1\3\14\uffff\11\3\7\uffff\1\3\6\uffff\1\3\2\uffff\1\3\7\uffff"+
            "\1\15\1\uffff\2\3\45\uffff\1\3",
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
            return "173:1: statement : ( SEMICOLON | block | expression SEMICOLON | define_gealg_name_and_exp | if_statement | set_output | macro_definition | loop | BREAK | pragma );";
        }
    }
    static final String DFA27_eotS =
        "\26\uffff";
    static final String DFA27_eofS =
        "\26\uffff";
    static final String DFA27_minS =
        "\1\4\10\0\15\uffff";
    static final String DFA27_maxS =
        "\1\134\10\0\15\uffff";
    static final String DFA27_acceptS =
        "\11\uffff\1\2\1\1\13\uffff";
    static final String DFA27_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\15\uffff}>";
    static final String[] DFA27_transitionS = {
            "\1\2\10\uffff\3\12\1\uffff\7\12\1\7\1\5\4\uffff\1\12\2\uffff"+
            "\1\1\6\uffff\1\4\2\uffff\1\2\7\uffff\1\12\1\11\1\6\1\10\1\uffff"+
            "\1\12\15\uffff\1\12\1\uffff\1\12\23\uffff\1\3",
            "\1\uffff",
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
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA27_eot = DFA.unpackEncodedString(DFA27_eotS);
    static final short[] DFA27_eof = DFA.unpackEncodedString(DFA27_eofS);
    static final char[] DFA27_min = DFA.unpackEncodedStringToUnsignedChars(DFA27_minS);
    static final char[] DFA27_max = DFA.unpackEncodedStringToUnsignedChars(DFA27_maxS);
    static final short[] DFA27_accept = DFA.unpackEncodedString(DFA27_acceptS);
    static final short[] DFA27_special = DFA.unpackEncodedString(DFA27_specialS);
    static final short[][] DFA27_transition;

    static {
        int numStates = DFA27_transitionS.length;
        DFA27_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA27_transition[i] = DFA.unpackEncodedString(DFA27_transitionS[i]);
        }
    }

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = DFA27_eot;
            this.eof = DFA27_eof;
            this.min = DFA27_min;
            this.max = DFA27_max;
            this.accept = DFA27_accept;
            this.special = DFA27_special;
            this.transition = DFA27_transition;
        }
        public String getDescription() {
            return "()* loopback of 197:36: ( statement )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_1 = input.LA(1);

                         
                        int index27_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_2 = input.LA(1);

                         
                        int index27_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA27_3 = input.LA(1);

                         
                        int index27_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA27_4 = input.LA(1);

                         
                        int index27_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA27_5 = input.LA(1);

                         
                        int index27_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA27_6 = input.LA(1);

                         
                        int index27_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA27_7 = input.LA(1);

                         
                        int index27_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA27_8 = input.LA(1);

                         
                        int index27_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred48_GaaletParser()) ) {s = 10;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index27_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 27, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA29_eotS =
        "\13\uffff";
    static final String DFA29_eofS =
        "\13\uffff";
    static final String DFA29_minS =
        "\1\4\10\0\2\uffff";
    static final String DFA29_maxS =
        "\1\134\10\0\2\uffff";
    static final String DFA29_acceptS =
        "\11\uffff\1\1\1\2";
    static final String DFA29_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\2\uffff}>";
    static final String[] DFA29_transitionS = {
            "\1\2\23\uffff\1\7\1\5\7\uffff\1\1\6\uffff\1\4\2\uffff\1\2\11"+
            "\uffff\1\6\1\10\45\uffff\1\3",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA29_eot = DFA.unpackEncodedString(DFA29_eotS);
    static final short[] DFA29_eof = DFA.unpackEncodedString(DFA29_eofS);
    static final char[] DFA29_min = DFA.unpackEncodedStringToUnsignedChars(DFA29_minS);
    static final char[] DFA29_max = DFA.unpackEncodedStringToUnsignedChars(DFA29_maxS);
    static final short[] DFA29_accept = DFA.unpackEncodedString(DFA29_acceptS);
    static final short[] DFA29_special = DFA.unpackEncodedString(DFA29_specialS);
    static final short[][] DFA29_transition;

    static {
        int numStates = DFA29_transitionS.length;
        DFA29_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA29_transition[i] = DFA.unpackEncodedString(DFA29_transitionS[i]);
        }
    }

    class DFA29 extends DFA {

        public DFA29(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 29;
            this.eot = DFA29_eot;
            this.eof = DFA29_eof;
            this.min = DFA29_min;
            this.max = DFA29_max;
            this.accept = DFA29_accept;
            this.special = DFA29_special;
            this.transition = DFA29_transition;
        }
        public String getDescription() {
            return "200:1: return_value : (add= additive_expression -> ^( RETURN $add) | or= logical_or_expression -> ^( RETURN $or) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA29_1 = input.LA(1);

                         
                        int index29_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA29_2 = input.LA(1);

                         
                        int index29_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA29_3 = input.LA(1);

                         
                        int index29_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA29_4 = input.LA(1);

                         
                        int index29_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA29_5 = input.LA(1);

                         
                        int index29_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA29_6 = input.LA(1);

                         
                        int index29_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA29_7 = input.LA(1);

                         
                        int index29_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA29_8 = input.LA(1);

                         
                        int index29_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred50_GaaletParser()) ) {s = 9;}

                        else if ( (true) ) {s = 10;}

                         
                        input.seek(index29_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 29, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_statement_in_script172 = new BitSet(new long[]{0x0168090243FEE010L,0x0000000010000140L});
    public static final BitSet FOLLOW_EOF_in_script175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_float_literal189 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_FLOATING_POINT_LITERAL_in_float_literal192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma207 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RANGE_LITERAL_in_pragma209 = new BitSet(new long[]{0x0000080002000000L});
    public static final BitSet FOLLOW_float_literal_in_pragma211 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma213 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma215 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_pragma217 = new BitSet(new long[]{0x0000080002000000L});
    public static final BitSet FOLLOW_float_literal_in_pragma219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma225 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_OUTPUT_LITERAL_in_pragma227 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_pragma235 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IGNORE_LITERAL_in_pragma237 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_pragma239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_expression258 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_lvalue_in_expression260 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_EQUALS_in_expression262 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_expression264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_expression282 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_lvalue_in_expression284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_expression298 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_EQUALS_in_expression300 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_expression302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logical_or_expression_in_expression321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eval_in_expression330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_argument_expression_list348 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COMMA_in_argument_expression_list351 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_argument_expression_list356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_expression_in_lvalue372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logical_and_expression_in_logical_or_expression385 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_BAR_in_logical_or_expression388 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_logical_and_expression_in_logical_or_expression391 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_equality_expression_in_logical_and_expression406 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_AND_in_logical_and_expression409 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_equality_expression_in_logical_and_expression412 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_relational_expression_in_equality_expression427 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_EQUALS_in_equality_expression431 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_UNEQUAL_in_equality_expression436 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_relational_expression_in_equality_expression440 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_additive_expression_in_relational_expression455 = new BitSet(new long[]{0x0000000000000002L,0x000000000000003CL});
    public static final BitSet FOLLOW_LESS_in_relational_expression459 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_GREATER_in_relational_expression464 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_LESS_OR_EQUAL_in_relational_expression469 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_GREATER_OR_EQUAL_in_relational_expression474 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_additive_expression_in_relational_expression478 = new BitSet(new long[]{0x0000000000000002L,0x000000000000003CL});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression493 = new BitSet(new long[]{0x0000800002000002L});
    public static final BitSet FOLLOW_PLUS_in_additive_expression498 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_MINUS_in_additive_expression503 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression507 = new BitSet(new long[]{0x0000800002000002L});
    public static final BitSet FOLLOW_outer_product_expression_in_multiplicative_expression523 = new BitSet(new long[]{0x0003000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicative_expression528 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_SLASH_in_multiplicative_expression533 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_outer_product_expression_in_multiplicative_expression537 = new BitSet(new long[]{0x0003000000000002L});
    public static final BitSet FOLLOW_inner_product_expression_in_outer_product_expression553 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_WEDGE_in_outer_product_expression557 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_inner_product_expression_in_outer_product_expression560 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_unary_expression_in_inner_product_expression576 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_SINGLE_AND_in_inner_product_expression580 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_unary_expression_in_inner_product_expression583 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_postfix_expression_in_unary_expression600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_unary_expression606 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_unary_expression_in_unary_expression610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REVERSE_in_unary_expression625 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_unary_expression_in_unary_expression629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INVERSE_in_unary_expression644 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LBRACKET_in_unary_expression646 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_unary_expression_in_unary_expression650 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_unary_expression652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_unary_expression666 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_unary_expression_in_unary_expression670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_unary_expression687 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_LSBRACKET_in_unary_expression689 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_unary_expression693 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_RSBRACKET_in_unary_expression695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_postfix_expression727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_expression_in_postfix_expression733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function_call749 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LBRACKET_in_function_call751 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_argument_expression_list_in_function_call756 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_function_call759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_function_call783 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LBRACKET_in_function_call785 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_function_call788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_in_eval815 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LBRACKET_in_eval818 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_eval821 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_eval823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_primary_expression839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_primary_expression845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_argument_in_primary_expression851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_primary_expression860 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_primary_expression863 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_primary_expression865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_statement_list915 = new BitSet(new long[]{0x0168090243FEE012L,0x0000000010000140L});
    public static final BitSet FOLLOW_SEMICOLON_in_statement932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_statement945 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_statement947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_define_gealg_name_and_exp_in_statement954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_statement_in_statement964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_output_in_statement969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_macro_definition_in_statement975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_statement981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_statement987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pragma_in_statement993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLBRACKET_in_block1009 = new BitSet(new long[]{0x0178090243FEE010L,0x0000000010000140L});
    public static final BitSet FOLLOW_statement_in_block1011 = new BitSet(new long[]{0x0178090243FEE010L,0x0000000010000140L});
    public static final BitSet FOLLOW_CRBRACKET_in_block1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_float_or_dec1043 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_float_or_dec1047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_float_literal_in_float_or_dec1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_macro_definition1070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_EQUALS_in_macro_definition1072 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_CLBRACKET_in_macro_definition1074 = new BitSet(new long[]{0x0178090243FEE010L,0x0000000010000140L});
    public static final BitSet FOLLOW_statement_in_macro_definition1076 = new BitSet(new long[]{0x0178090243FEE010L,0x0000000010000140L});
    public static final BitSet FOLLOW_return_value_in_macro_definition1081 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_CRBRACKET_in_macro_definition1084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additive_expression_in_return_value1117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logical_or_expression_in_return_value1134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRAGMA_in_loop1159 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_UNROLL_LITERAL_in_loop1161 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_loop1165 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRAGMA_in_loop1173 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COUNT_LITERAL_in_loop1175 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_loop1179 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LOOP_in_loop1183 = new BitSet(new long[]{0x0168090243FEE010L,0x0000000010000140L});
    public static final BitSet FOLLOW_statement_in_loop1187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_statement1222 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_LBRACKET_in_if_statement1224 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_logical_or_expression_in_if_statement1228 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_if_statement1230 = new BitSet(new long[]{0x0168090243FEE010L,0x0000000010000140L});
    public static final BitSet FOLLOW_statement_in_if_statement1243 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_else_part_in_if_statement1288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_else_part1391 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_block_in_else_part1393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_else_part1407 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_if_statement_in_else_part1411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HEX_in_hex_literal1439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARGUMENT_PREFIX_in_function_argument1455 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_function_argument1459 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_RBRACKET_in_function_argument1461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_gaalet_arguments1486 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_COMMA_in_gaalet_arguments1489 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_DECIMAL_LITERAL_in_gaalet_arguments1492 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_hex_literal_in_gaalet_arguments1502 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_COMMA_in_gaalet_arguments1505 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_hex_literal_in_gaalet_arguments1508 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_GEALG_MV_in_define_gealg_name_and_exp1529 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_LESS_in_define_gealg_name_and_exp1538 = new BitSet(new long[]{0x0000000000000090L});
    public static final BitSet FOLLOW_gaalet_arguments_in_define_gealg_name_and_exp1544 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_GREATER_in_define_gealg_name_and_exp1547 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_GEALG_TYPE_in_define_gealg_name_and_exp1555 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_define_gealg_name_and_exp1559 = new BitSet(new long[]{0x0100000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_EQUALS_in_define_gealg_name_and_exp1573 = new BitSet(new long[]{0x0068090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_define_gealg_name_and_exp1581 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_define_gealg_name_and_exp1583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLBRACKET_in_define_gealg_name_and_exp1646 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_argument_expression_list_in_define_gealg_name_and_exp1650 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_CRBRACKET_in_define_gealg_name_and_exp1652 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_define_gealg_name_and_exp1654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_define_gealg_name_and_exp1730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_type1779 = new BitSet(new long[]{0x00000000000E0000L});
    public static final BitSet FOLLOW_set_in_type1787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_type1805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AUTO_in_type1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SET_OUTPUT_in_set_output1836 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_lvalue_in_set_output1840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred5_GaaletParser258 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_lvalue_in_synpred5_GaaletParser260 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_EQUALS_in_synpred5_GaaletParser262 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_synpred5_GaaletParser264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred6_GaaletParser282 = new BitSet(new long[]{0x0060090203000010L,0x0000000010000000L});
    public static final BitSet FOLLOW_lvalue_in_synpred6_GaaletParser284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lvalue_in_synpred7_GaaletParser298 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_EQUALS_in_synpred7_GaaletParser300 = new BitSet(new long[]{0x0060090203FE0010L,0x0000000010000000L});
    public static final BitSet FOLLOW_expression_in_synpred7_GaaletParser302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logical_or_expression_in_synpred8_GaaletParser321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred9_GaaletParser348 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred9_GaaletParser351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_synpred29_GaaletParser727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_synpred48_GaaletParser1076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additive_expression_in_synpred50_GaaletParser1117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_else_part_in_synpred53_GaaletParser1288 = new BitSet(new long[]{0x0000000000000002L});

}