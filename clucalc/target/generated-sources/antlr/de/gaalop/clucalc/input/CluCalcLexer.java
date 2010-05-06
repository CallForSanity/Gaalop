// $ANTLR 3.2 Sep 23, 2009 12:02:23 C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g 2010-05-06 11:38:43

	package de.gaalop.clucalc.input;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CluCalcLexer extends Lexer {
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
    public static final int LBRACKET=33;
    public static final int CRBRACKET=36;
    public static final int GREATER=50;
    public static final int SLASH=29;
    public static final int FLOATING_POINT_LITERAL=7;
    public static final int COMMA=26;
    public static final int RSBRACKET=32;
    public static final int IDENTIFIER=19;
    public static final int LOOP=15;
    public static final int QUESTIONMARK=43;
    public static final int LESS=49;
    public static final int PLUS=27;
    public static final int DIGIT=18;
    public static final int IPNS=12;
    public static final int RBRACKET=34;
    public static final int COMMENT=22;
    public static final int DOT=42;
    public static final int CLBRACKET=35;
    public static final int MODULO=30;
    public static final int LINE_COMMENT=24;
    public static final int LESS_OR_EQUAL=51;
    public static final int ELSE=14;
    public static final int SEMICOLON=40;
    public static final int MINUS=10;
    public static final int OPNS=11;
    public static final int UNEQUAL=48;
    public static final int DOUBLE_AND=46;
    public static final int COLON=44;
    public static final int LSBRACKET=31;
    public static final int WEDGE=41;
    public static final int WS=21;
    public static final int RANGE_LITERAL=8;
    public static final int OUTPUT_LITERAL=9;
    public static final int REVERSE=37;
    public static final int DECIMAL_LITERAL=4;

    // delegates
    // delegators

    public CluCalcLexer() {;} 
    public CluCalcLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CluCalcLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g"; }

    // $ANTLR start "DECIMAL_LITERAL"
    public final void mDECIMAL_LITERAL() throws RecognitionException {
        try {
            int _type = DECIMAL_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:12:17: ( ( '0' .. '9' )+ )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:12:19: ( '0' .. '9' )+
            {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:12:19: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:12:20: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DECIMAL_LITERAL"

    // $ANTLR start "FLOATING_POINT_LITERAL"
    public final void mFLOATING_POINT_LITERAL() throws RecognitionException {
        try {
            int _type = FLOATING_POINT_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? ( FLOATTYPESUFFIX )? | '.' ( '0' .. '9' )+ ( EXPONENT )? ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ EXPONENT ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ FLOATTYPESUFFIX )
            int alt12=4;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? ( FLOATTYPESUFFIX )?
                    {
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:9: ( '0' .. '9' )+
                    int cnt2=0;
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt2 >= 1 ) break loop2;
                                EarlyExitException eee =
                                    new EarlyExitException(2, input);
                                throw eee;
                        }
                        cnt2++;
                    } while (true);

                    match('.'); 
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:25: ( '0' .. '9' )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:37: ( EXPONENT )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='e') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:37: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }

                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:47: ( FLOATTYPESUFFIX )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='d'||LA5_0=='f') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:15:47: FLOATTYPESUFFIX
                            {
                            mFLOATTYPESUFFIX(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:16:9: '.' ( '0' .. '9' )+ ( EXPONENT )? ( FLOATTYPESUFFIX )?
                    {
                    match('.'); 
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:16:13: ( '0' .. '9' )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:16:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);

                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:16:25: ( EXPONENT )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='e') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:16:25: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }

                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:16:35: ( FLOATTYPESUFFIX )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='d'||LA8_0=='f') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:16:35: FLOATTYPESUFFIX
                            {
                            mFLOATTYPESUFFIX(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:17:9: ( '0' .. '9' )+ EXPONENT ( FLOATTYPESUFFIX )?
                    {
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:17:9: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:17:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    mEXPONENT(); 
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:17:30: ( FLOATTYPESUFFIX )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='d'||LA10_0=='f') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:17:30: FLOATTYPESUFFIX
                            {
                            mFLOATTYPESUFFIX(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:18:9: ( '0' .. '9' )+ FLOATTYPESUFFIX
                    {
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:18:9: ( '0' .. '9' )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:18:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt11 >= 1 ) break loop11;
                                EarlyExitException eee =
                                    new EarlyExitException(11, input);
                                throw eee;
                        }
                        cnt11++;
                    } while (true);

                    mFLOATTYPESUFFIX(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOATING_POINT_LITERAL"

    // $ANTLR start "RANGE_LITERAL"
    public final void mRANGE_LITERAL() throws RecognitionException {
        try {
            int _type = RANGE_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:21:15: ( 'range' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:21:17: 'range'
            {
            match("range"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RANGE_LITERAL"

    // $ANTLR start "OUTPUT_LITERAL"
    public final void mOUTPUT_LITERAL() throws RecognitionException {
        try {
            int _type = OUTPUT_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:23:15: ( 'output' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:23:17: 'output'
            {
            match("output"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OUTPUT_LITERAL"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:27:2: ( 'e' ( MINUS )? ( '0' .. '9' )+ )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:27:4: 'e' ( MINUS )? ( '0' .. '9' )+
            {
            match('e'); 
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:27:8: ( MINUS )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='-') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:27:8: MINUS
                    {
                    mMINUS(); 

                    }
                    break;

            }

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:27:15: ( '0' .. '9' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:27:16: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "FLOATTYPESUFFIX"
    public final void mFLOATTYPESUFFIX() throws RecognitionException {
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:32:2: ( ( 'f' | 'd' ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:32:4: ( 'f' | 'd' )
            {
            if ( input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "FLOATTYPESUFFIX"

    // $ANTLR start "OPNS"
    public final void mOPNS() throws RecognitionException {
        try {
            int _type = OPNS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:35:6: ( 'OPNS' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:35:8: 'OPNS'
            {
            match("OPNS"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OPNS"

    // $ANTLR start "IPNS"
    public final void mIPNS() throws RecognitionException {
        try {
            int _type = IPNS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:38:6: ( 'IPNS' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:38:8: 'IPNS'
            {
            match("IPNS"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IPNS"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:41:5: ( 'if' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:41:7: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:44:6: ( 'else' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:44:8: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "LOOP"
    public final void mLOOP() throws RecognitionException {
        try {
            int _type = LOOP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:47:7: ( 'loop' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:47:9: 'loop'
            {
            match("loop"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOOP"

    // $ANTLR start "BREAK"
    public final void mBREAK() throws RecognitionException {
        try {
            int _type = BREAK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:50:7: ( 'break' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:50:9: 'break'
            {
            match("break"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BREAK"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:54:2: ( LETTER ( LETTER | DIGIT )* )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:54:4: LETTER ( LETTER | DIGIT )*
            {
            mLETTER(); 
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:54:11: ( LETTER | DIGIT )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>='0' && LA15_0<='9')||(LA15_0>='A' && LA15_0<='Z')||LA15_0=='_'||(LA15_0>='a' && LA15_0<='z')) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "ARGUMENT_PREFIX"
    public final void mARGUMENT_PREFIX() throws RecognitionException {
        try {
            int _type = ARGUMENT_PREFIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:58:3: ( '_P(' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:58:5: '_P('
            {
            match("_P("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARGUMENT_PREFIX"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:63:2: ( 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:69:7: ( '0' .. '9' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:69:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:72:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:72:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:76:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:76:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:76:14: ( options {greedy=false; } : . )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='*') ) {
                    int LA16_1 = input.LA(2);

                    if ( (LA16_1=='/') ) {
                        alt16=2;
                    }
                    else if ( ((LA16_1>='\u0000' && LA16_1<='.')||(LA16_1>='0' && LA16_1<='\uFFFF')) ) {
                        alt16=1;
                    }


                }
                else if ( ((LA16_0>='\u0000' && LA16_0<=')')||(LA16_0>='+' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:76:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "PRAGMA"
    public final void mPRAGMA() throws RecognitionException {
        try {
            int _type = PRAGMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:82:5: ( '//#pragma' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:82:9: '//#pragma'
            {
            match("//#pragma"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRAGMA"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:86:5: ( '//' ~ '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:86:7: '//' ~ '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<='\"')||(input.LA(1)>='$' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:86:17: (~ ( '\\n' | '\\r' ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='\u0000' && LA17_0<='\t')||(LA17_0>='\u000B' && LA17_0<='\f')||(LA17_0>='\u000E' && LA17_0<='\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:86:17: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:86:31: ( '\\r' )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='\r') ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:86:31: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:91:2: ( '=' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:91:4: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:96:2: ( ',' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:96:4: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:100:2: ( '+' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:100:4: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:104:2: ( '-' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:104:4: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:109:2: ( '*' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:109:4: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "SLASH"
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:113:2: ( '/' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:113:4: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLASH"

    // $ANTLR start "MODULO"
    public final void mMODULO() throws RecognitionException {
        try {
            int _type = MODULO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:117:2: ( '%' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:117:4: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODULO"

    // $ANTLR start "LSBRACKET"
    public final void mLSBRACKET() throws RecognitionException {
        try {
            int _type = LSBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:121:2: ( '[' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:121:4: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LSBRACKET"

    // $ANTLR start "RSBRACKET"
    public final void mRSBRACKET() throws RecognitionException {
        try {
            int _type = RSBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:125:2: ( ']' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:125:4: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RSBRACKET"

    // $ANTLR start "LBRACKET"
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:129:2: ( '(' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:129:4: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACKET"

    // $ANTLR start "RBRACKET"
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:133:2: ( ')' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:133:4: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACKET"

    // $ANTLR start "CLBRACKET"
    public final void mCLBRACKET() throws RecognitionException {
        try {
            int _type = CLBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:137:2: ( '{' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:137:4: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLBRACKET"

    // $ANTLR start "CRBRACKET"
    public final void mCRBRACKET() throws RecognitionException {
        try {
            int _type = CRBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:141:2: ( '}' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:141:4: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CRBRACKET"

    // $ANTLR start "REVERSE"
    public final void mREVERSE() throws RecognitionException {
        try {
            int _type = REVERSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:145:2: ( '~' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:145:4: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REVERSE"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:149:2: ( '!' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:149:4: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "DOUBLE_NOT"
    public final void mDOUBLE_NOT() throws RecognitionException {
        try {
            int _type = DOUBLE_NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:153:2: ( '!!' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:153:4: '!!'
            {
            match("!!"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_NOT"

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:157:2: ( ';' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:157:4: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "WEDGE"
    public final void mWEDGE() throws RecognitionException {
        try {
            int _type = WEDGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:160:7: ( '^' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:160:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WEDGE"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:163:5: ( '.' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:163:7: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "QUESTIONMARK"
    public final void mQUESTIONMARK() throws RecognitionException {
        try {
            int _type = QUESTIONMARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:168:2: ( '?' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:168:4: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUESTIONMARK"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:172:2: ( ':' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:172:4: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "DOUBLE_BAR"
    public final void mDOUBLE_BAR() throws RecognitionException {
        try {
            int _type = DOUBLE_BAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:176:2: ( '||' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:176:5: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_BAR"

    // $ANTLR start "DOUBLE_AND"
    public final void mDOUBLE_AND() throws RecognitionException {
        try {
            int _type = DOUBLE_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:180:2: ( '&&' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:180:4: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_AND"

    // $ANTLR start "DOUBLE_EQUALS"
    public final void mDOUBLE_EQUALS() throws RecognitionException {
        try {
            int _type = DOUBLE_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:184:2: ( '==' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:184:4: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_EQUALS"

    // $ANTLR start "UNEQUAL"
    public final void mUNEQUAL() throws RecognitionException {
        try {
            int _type = UNEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:188:2: ( '!=' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:188:4: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNEQUAL"

    // $ANTLR start "LESS"
    public final void mLESS() throws RecognitionException {
        try {
            int _type = LESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:192:2: ( '<' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:192:4: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS"

    // $ANTLR start "GREATER"
    public final void mGREATER() throws RecognitionException {
        try {
            int _type = GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:196:2: ( '>' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:196:5: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER"

    // $ANTLR start "LESS_OR_EQUAL"
    public final void mLESS_OR_EQUAL() throws RecognitionException {
        try {
            int _type = LESS_OR_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:200:2: ( '<=' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:200:4: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_OR_EQUAL"

    // $ANTLR start "GREATER_OR_EQUAL"
    public final void mGREATER_OR_EQUAL() throws RecognitionException {
        try {
            int _type = GREATER_OR_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:204:2: ( '>=' )
            // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:204:4: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_OR_EQUAL"

    public void mTokens() throws RecognitionException {
        // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:8: ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL | RANGE_LITERAL | OUTPUT_LITERAL | OPNS | IPNS | IF | ELSE | LOOP | BREAK | IDENTIFIER | ARGUMENT_PREFIX | WS | COMMENT | PRAGMA | LINE_COMMENT | EQUALS | COMMA | PLUS | MINUS | STAR | SLASH | MODULO | LSBRACKET | RSBRACKET | LBRACKET | RBRACKET | CLBRACKET | CRBRACKET | REVERSE | NOT | DOUBLE_NOT | SEMICOLON | WEDGE | DOT | QUESTIONMARK | COLON | DOUBLE_BAR | DOUBLE_AND | DOUBLE_EQUALS | UNEQUAL | LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL )
        int alt19=45;
        alt19 = dfa19.predict(input);
        switch (alt19) {
            case 1 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:10: DECIMAL_LITERAL
                {
                mDECIMAL_LITERAL(); 

                }
                break;
            case 2 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:26: FLOATING_POINT_LITERAL
                {
                mFLOATING_POINT_LITERAL(); 

                }
                break;
            case 3 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:49: RANGE_LITERAL
                {
                mRANGE_LITERAL(); 

                }
                break;
            case 4 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:63: OUTPUT_LITERAL
                {
                mOUTPUT_LITERAL(); 

                }
                break;
            case 5 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:78: OPNS
                {
                mOPNS(); 

                }
                break;
            case 6 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:83: IPNS
                {
                mIPNS(); 

                }
                break;
            case 7 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:88: IF
                {
                mIF(); 

                }
                break;
            case 8 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:91: ELSE
                {
                mELSE(); 

                }
                break;
            case 9 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:96: LOOP
                {
                mLOOP(); 

                }
                break;
            case 10 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:101: BREAK
                {
                mBREAK(); 

                }
                break;
            case 11 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:107: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 12 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:118: ARGUMENT_PREFIX
                {
                mARGUMENT_PREFIX(); 

                }
                break;
            case 13 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:134: WS
                {
                mWS(); 

                }
                break;
            case 14 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:137: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 15 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:145: PRAGMA
                {
                mPRAGMA(); 

                }
                break;
            case 16 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:152: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 17 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:165: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 18 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:172: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 19 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:178: PLUS
                {
                mPLUS(); 

                }
                break;
            case 20 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:183: MINUS
                {
                mMINUS(); 

                }
                break;
            case 21 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:189: STAR
                {
                mSTAR(); 

                }
                break;
            case 22 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:194: SLASH
                {
                mSLASH(); 

                }
                break;
            case 23 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:200: MODULO
                {
                mMODULO(); 

                }
                break;
            case 24 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:207: LSBRACKET
                {
                mLSBRACKET(); 

                }
                break;
            case 25 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:217: RSBRACKET
                {
                mRSBRACKET(); 

                }
                break;
            case 26 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:227: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 27 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:236: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 28 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:245: CLBRACKET
                {
                mCLBRACKET(); 

                }
                break;
            case 29 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:255: CRBRACKET
                {
                mCRBRACKET(); 

                }
                break;
            case 30 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:265: REVERSE
                {
                mREVERSE(); 

                }
                break;
            case 31 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:273: NOT
                {
                mNOT(); 

                }
                break;
            case 32 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:277: DOUBLE_NOT
                {
                mDOUBLE_NOT(); 

                }
                break;
            case 33 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:288: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 34 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:298: WEDGE
                {
                mWEDGE(); 

                }
                break;
            case 35 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:304: DOT
                {
                mDOT(); 

                }
                break;
            case 36 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:308: QUESTIONMARK
                {
                mQUESTIONMARK(); 

                }
                break;
            case 37 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:321: COLON
                {
                mCOLON(); 

                }
                break;
            case 38 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:327: DOUBLE_BAR
                {
                mDOUBLE_BAR(); 

                }
                break;
            case 39 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:338: DOUBLE_AND
                {
                mDOUBLE_AND(); 

                }
                break;
            case 40 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:349: DOUBLE_EQUALS
                {
                mDOUBLE_EQUALS(); 

                }
                break;
            case 41 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:363: UNEQUAL
                {
                mUNEQUAL(); 

                }
                break;
            case 42 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:371: LESS
                {
                mLESS(); 

                }
                break;
            case 43 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:376: GREATER
                {
                mGREATER(); 

                }
                break;
            case 44 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:384: LESS_OR_EQUAL
                {
                mLESS_OR_EQUAL(); 

                }
                break;
            case 45 :
                // C:\\Users\\Christian\\workspace\\gaalop-2.0\\branches\\control-flow\\clucalc\\src\\main\\antlr\\de\\gaalop\\clucalc\\input\\CluCalcLexer.g:1:398: GREATER_OR_EQUAL
                {
                mGREATER_OR_EQUAL(); 

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA12_eotS =
        "\6\uffff";
    static final String DFA12_eofS =
        "\6\uffff";
    static final String DFA12_minS =
        "\2\56\4\uffff";
    static final String DFA12_maxS =
        "\1\71\1\146\4\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\1\4\1\1\1\3";
    static final String DFA12_specialS =
        "\6\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\4\1\uffff\12\1\52\uffff\1\3\1\5\1\3",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "14:1: FLOATING_POINT_LITERAL : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? ( FLOATTYPESUFFIX )? | '.' ( '0' .. '9' )+ ( EXPONENT )? ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ EXPONENT ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ FLOATTYPESUFFIX );";
        }
    }
    static final String DFA19_eotS =
        "\1\uffff\1\46\1\47\11\14\2\uffff\1\63\1\65\14\uffff\1\70\6\uffff"+
        "\1\72\1\74\3\uffff\4\14\1\101\4\14\14\uffff\4\14\1\uffff\3\14\3"+
        "\uffff\2\14\1\121\1\122\1\123\1\124\1\14\1\126\1\14\4\uffff\1\130"+
        "\1\uffff\1\131\2\uffff";
    static final String DFA19_eofS =
        "\132\uffff";
    static final String DFA19_minS =
        "\1\11\1\56\1\60\1\141\1\165\2\120\1\146\1\154\1\157\1\162\1\120"+
        "\2\uffff\1\52\1\75\14\uffff\1\41\6\uffff\2\75\3\uffff\1\156\1\164"+
        "\2\116\1\60\1\163\1\157\1\145\1\50\1\uffff\1\0\12\uffff\1\147\1"+
        "\160\2\123\1\uffff\1\145\1\160\1\141\3\uffff\1\145\1\165\4\60\1"+
        "\153\1\60\1\164\4\uffff\1\60\1\uffff\1\60\2\uffff";
    static final String DFA19_maxS =
        "\1\176\1\146\1\71\1\141\1\165\2\120\1\146\1\154\1\157\1\162\1\120"+
        "\2\uffff\1\57\1\75\14\uffff\1\75\6\uffff\2\75\3\uffff\1\156\1\164"+
        "\2\116\1\172\1\163\1\157\1\145\1\50\1\uffff\1\uffff\12\uffff\1\147"+
        "\1\160\2\123\1\uffff\1\145\1\160\1\141\3\uffff\1\145\1\165\4\172"+
        "\1\153\1\172\1\164\4\uffff\1\172\1\uffff\1\172\2\uffff";
    static final String DFA19_acceptS =
        "\14\uffff\1\13\1\15\2\uffff\1\22\1\23\1\24\1\25\1\27\1\30\1\31"+
        "\1\32\1\33\1\34\1\35\1\36\1\uffff\1\41\1\42\1\44\1\45\1\46\1\47"+
        "\2\uffff\1\2\1\1\1\43\11\uffff\1\16\1\uffff\1\26\1\50\1\21\1\40"+
        "\1\51\1\37\1\54\1\52\1\55\1\53\4\uffff\1\7\3\uffff\1\14\1\17\1\20"+
        "\11\uffff\1\5\1\6\1\10\1\11\1\uffff\1\3\1\uffff\1\12\1\4";
    static final String DFA19_specialS =
        "\62\uffff\1\0\47\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\15\1\uffff\2\15\22\uffff\1\15\1\34\3\uffff\1\24\1\42\1\uffff"+
            "\1\27\1\30\1\23\1\21\1\20\1\22\1\2\1\16\12\1\1\40\1\35\1\43"+
            "\1\17\1\44\1\37\1\uffff\10\14\1\6\5\14\1\5\13\14\1\25\1\uffff"+
            "\1\26\1\36\1\13\1\uffff\1\14\1\12\2\14\1\10\3\14\1\7\2\14\1"+
            "\11\2\14\1\4\2\14\1\3\10\14\1\31\1\41\1\32\1\33",
            "\1\45\1\uffff\12\1\52\uffff\3\45",
            "\12\45",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "",
            "",
            "\1\61\4\uffff\1\62",
            "\1\64",
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
            "\1\66\33\uffff\1\67",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\71",
            "\1\73",
            "",
            "",
            "",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "",
            "\43\107\1\106\uffdc\107",
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
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "",
            "\1\114",
            "\1\115",
            "\1\116",
            "",
            "",
            "",
            "\1\117",
            "\1\120",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "\1\125",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "\1\127",
            "",
            "",
            "",
            "",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "",
            "\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32\14",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( DECIMAL_LITERAL | FLOATING_POINT_LITERAL | RANGE_LITERAL | OUTPUT_LITERAL | OPNS | IPNS | IF | ELSE | LOOP | BREAK | IDENTIFIER | ARGUMENT_PREFIX | WS | COMMENT | PRAGMA | LINE_COMMENT | EQUALS | COMMA | PLUS | MINUS | STAR | SLASH | MODULO | LSBRACKET | RSBRACKET | LBRACKET | RBRACKET | CLBRACKET | CRBRACKET | REVERSE | NOT | DOUBLE_NOT | SEMICOLON | WEDGE | DOT | QUESTIONMARK | COLON | DOUBLE_BAR | DOUBLE_AND | DOUBLE_EQUALS | UNEQUAL | LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_50 = input.LA(1);

                        s = -1;
                        if ( (LA19_50=='#') ) {s = 70;}

                        else if ( ((LA19_50>='\u0000' && LA19_50<='\"')||(LA19_50>='$' && LA19_50<='\uFFFF')) ) {s = 71;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}