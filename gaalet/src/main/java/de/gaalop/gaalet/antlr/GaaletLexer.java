// $ANTLR 3.2 Sep 23, 2009 12:02:23 GaaletLexer.g 2010-12-19 07:55:39

	package de.gaalop.gaalet.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GaaletLexer extends Lexer {
    public static final int SINGLE_AND=62;
    public static final int ARROW_RIGHT=37;
    public static final int SIGNED=21;
    public static final int EXPONENT=26;
    public static final int STAR=48;
    public static final int DOUBLE_BAR=60;
    public static final int LETTER=34;
    public static final int GREATER_OR_EQUAL=69;
    public static final int PRAGMA=72;
    public static final int DOUBLE_EQUALS=63;
    public static final int HEX_PREFIX=5;
    public static final int EQUALS=64;
    public static final int FLOAT=18;
    public static final int NOT=54;
    public static final int IDENTIFIER_TYPE_CAST=42;
    public static final int SPACE=44;
    public static final int EOF=-1;
    public static final int BREAK=14;
    public static final int DOUBLE_NOT=55;
    public static final int IF=30;
    public static final int FLOATTYPESUFFIX=27;
    public static final int CRBRACKET=52;
    public static final int LBRACKET=40;
    public static final int GREATER=67;
    public static final int SLASH=49;
    public static final int FLOATING_POINT_LITERAL=43;
    public static final int GEALG_MV=15;
    public static final int IDENTIFIER_RECURSIVE=32;
    public static final int COMMA=46;
    public static final int HEX=7;
    public static final int RSBRACKET=39;
    public static final int IDENTIFIER=33;
    public static final int LOOP=13;
    public static final int QUESTIONMARK=58;
    public static final int LESS=66;
    public static final int AUTO=22;
    public static final int DOUBLE=17;
    public static final int PLUS=47;
    public static final int UNROLL_LITERAL=10;
    public static final int DIGIT=6;
    public static final int IPNS=29;
    public static final int RBRACKET=41;
    public static final int COMMENT=71;
    public static final int DOT=35;
    public static final int SET_OUTPUT=70;
    public static final int CLBRACKET=51;
    public static final int INTEGER=19;
    public static final int MODULO=50;
    public static final int LINE_COMMENT=73;
    public static final int LESS_OR_EQUAL=68;
    public static final int ELSE=31;
    public static final int SEMICOLON=56;
    public static final int MINUS=25;
    public static final int OPNS=28;
    public static final int UNEQUAL=65;
    public static final int DOUBLE_AND=61;
    public static final int COLON=59;
    public static final int LSBRACKET=38;
    public static final int INVERSE=24;
    public static final int WEDGE=57;
    public static final int WS=45;
    public static final int UNSIGNED=20;
    public static final int EVAL=23;
    public static final int RANGE_LITERAL=8;
    public static final int DOUBLE_COLON=36;
    public static final int GEALG_TYPE=16;
    public static final int COUNT_LITERAL=11;
    public static final int OUTPUT_LITERAL=9;
    public static final int REVERSE=53;
    public static final int DECIMAL_LITERAL=4;
    public static final int IGNORE_LITERAL=12;

    // delegates
    // delegators

    public GaaletLexer() {;} 
    public GaaletLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public GaaletLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "GaaletLexer.g"; }

    // $ANTLR start "DECIMAL_LITERAL"
    public final void mDECIMAL_LITERAL() throws RecognitionException {
        try {
            int _type = DECIMAL_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:14:17: ( ( '0' .. '9' )+ )
            // GaaletLexer.g:14:19: ( '0' .. '9' )+
            {
            // GaaletLexer.g:14:19: ( '0' .. '9' )+
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
            	    // GaaletLexer.g:14:20: '0' .. '9'
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

    // $ANTLR start "HEX_PREFIX"
    public final void mHEX_PREFIX() throws RecognitionException {
        try {
            // GaaletLexer.g:17:12: ( '0x' )
            // GaaletLexer.g:17:14: '0x'
            {
            match("0x"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "HEX_PREFIX"

    // $ANTLR start "HEX"
    public final void mHEX() throws RecognitionException {
        try {
            int _type = HEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:19:5: ( HEX_PREFIX ( DIGIT | ( 'a' .. 'f' ) )+ )
            // GaaletLexer.g:19:7: HEX_PREFIX ( DIGIT | ( 'a' .. 'f' ) )+
            {
            mHEX_PREFIX(); 
            // GaaletLexer.g:19:17: ( DIGIT | ( 'a' .. 'f' ) )+
            int cnt2=0;
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }
                else if ( ((LA2_0>='a' && LA2_0<='f')) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // GaaletLexer.g:19:18: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;
            	case 2 :
            	    // GaaletLexer.g:19:26: ( 'a' .. 'f' )
            	    {
            	    // GaaletLexer.g:19:26: ( 'a' .. 'f' )
            	    // GaaletLexer.g:19:27: 'a' .. 'f'
            	    {
            	    matchRange('a','f'); 

            	    }


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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HEX"

    // $ANTLR start "RANGE_LITERAL"
    public final void mRANGE_LITERAL() throws RecognitionException {
        try {
            int _type = RANGE_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:21:15: ( 'range' )
            // GaaletLexer.g:21:17: 'range'
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
            // GaaletLexer.g:23:15: ( 'output' )
            // GaaletLexer.g:23:17: 'output'
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

    // $ANTLR start "UNROLL_LITERAL"
    public final void mUNROLL_LITERAL() throws RecognitionException {
        try {
            int _type = UNROLL_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:25:15: ( 'unroll' )
            // GaaletLexer.g:25:17: 'unroll'
            {
            match("unroll"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNROLL_LITERAL"

    // $ANTLR start "COUNT_LITERAL"
    public final void mCOUNT_LITERAL() throws RecognitionException {
        try {
            int _type = COUNT_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:27:14: ( 'count' )
            // GaaletLexer.g:27:16: 'count'
            {
            match("count"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COUNT_LITERAL"

    // $ANTLR start "IGNORE_LITERAL"
    public final void mIGNORE_LITERAL() throws RecognitionException {
        try {
            int _type = IGNORE_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:29:15: ( 'ignore' )
            // GaaletLexer.g:29:17: 'ignore'
            {
            match("ignore"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IGNORE_LITERAL"

    // $ANTLR start "LOOP"
    public final void mLOOP() throws RecognitionException {
        try {
            int _type = LOOP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:32:7: ( 'loop' )
            // GaaletLexer.g:32:9: 'loop'
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
            // GaaletLexer.g:35:7: ( 'break' )
            // GaaletLexer.g:35:9: 'break'
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

    // $ANTLR start "GEALG_MV"
    public final void mGEALG_MV() throws RecognitionException {
        try {
            int _type = GEALG_MV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:39:3: ( 'gaalet::cm::mv' )
            // GaaletLexer.g:39:5: 'gaalet::cm::mv'
            {
            match("gaalet::cm::mv"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GEALG_MV"

    // $ANTLR start "GEALG_TYPE"
    public final void mGEALG_TYPE() throws RecognitionException {
        try {
            int _type = GEALG_TYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:44:3: ( '::type' )
            // GaaletLexer.g:44:5: '::type'
            {
            match("::type"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GEALG_TYPE"

    // $ANTLR start "DOUBLE"
    public final void mDOUBLE() throws RecognitionException {
        try {
            int _type = DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:49:3: ( 'double' )
            // GaaletLexer.g:49:5: 'double'
            {
            match("double"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:53:3: ( 'float' )
            // GaaletLexer.g:53:5: 'float'
            {
            match("float"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:57:3: ( 'int' )
            // GaaletLexer.g:57:5: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "UNSIGNED"
    public final void mUNSIGNED() throws RecognitionException {
        try {
            int _type = UNSIGNED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:61:3: ( 'unsigned' )
            // GaaletLexer.g:61:5: 'unsigned'
            {
            match("unsigned"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNSIGNED"

    // $ANTLR start "SIGNED"
    public final void mSIGNED() throws RecognitionException {
        try {
            int _type = SIGNED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:65:3: ( 'signed' )
            // GaaletLexer.g:65:5: 'signed'
            {
            match("signed"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIGNED"

    // $ANTLR start "AUTO"
    public final void mAUTO() throws RecognitionException {
        try {
            int _type = AUTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:69:3: ( 'auto' )
            // GaaletLexer.g:69:5: 'auto'
            {
            match("auto"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AUTO"

    // $ANTLR start "EVAL"
    public final void mEVAL() throws RecognitionException {
        try {
            int _type = EVAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:73:3: ( 'eval' )
            // GaaletLexer.g:73:5: 'eval'
            {
            match("eval"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EVAL"

    // $ANTLR start "INVERSE"
    public final void mINVERSE() throws RecognitionException {
        try {
            int _type = INVERSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:77:3: ( 'gaalet::inverse' )
            // GaaletLexer.g:77:5: 'gaalet::inverse'
            {
            match("gaalet::inverse"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INVERSE"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // GaaletLexer.g:82:2: ( 'e' ( MINUS )? ( '0' .. '9' )+ )
            // GaaletLexer.g:82:4: 'e' ( MINUS )? ( '0' .. '9' )+
            {
            match('e'); 
            // GaaletLexer.g:82:8: ( MINUS )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // GaaletLexer.g:82:8: MINUS
                    {
                    mMINUS(); 

                    }
                    break;

            }

            // GaaletLexer.g:82:15: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // GaaletLexer.g:82:16: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
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
            // GaaletLexer.g:87:2: ( ( 'f' | 'd' ) )
            // GaaletLexer.g:87:4: ( 'f' | 'd' )
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
            // GaaletLexer.g:90:6: ( 'OPNS' )
            // GaaletLexer.g:90:8: 'OPNS'
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
            // GaaletLexer.g:93:6: ( 'IPNS' )
            // GaaletLexer.g:93:8: 'IPNS'
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
            // GaaletLexer.g:96:5: ( 'if' )
            // GaaletLexer.g:96:7: 'if'
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
            // GaaletLexer.g:99:6: ( 'else' )
            // GaaletLexer.g:99:8: 'else'
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

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:105:3: ( IDENTIFIER_RECURSIVE )
            // GaaletLexer.g:106:3: IDENTIFIER_RECURSIVE
            {
            mIDENTIFIER_RECURSIVE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "IDENTIFIER_RECURSIVE"
    public final void mIDENTIFIER_RECURSIVE() throws RecognitionException {
        try {
            // GaaletLexer.g:112:3: ( ( LETTER ) ( LETTER | DIGIT | ( DOT IDENTIFIER_RECURSIVE ) | ( DOUBLE_COLON IDENTIFIER_RECURSIVE ) | ( ARROW_RIGHT IDENTIFIER_RECURSIVE ) )* ( LSBRACKET IDENTIFIER_RECURSIVE RSBRACKET )* )
            // GaaletLexer.g:112:7: ( LETTER ) ( LETTER | DIGIT | ( DOT IDENTIFIER_RECURSIVE ) | ( DOUBLE_COLON IDENTIFIER_RECURSIVE ) | ( ARROW_RIGHT IDENTIFIER_RECURSIVE ) )* ( LSBRACKET IDENTIFIER_RECURSIVE RSBRACKET )*
            {
            // GaaletLexer.g:112:7: ( LETTER )
            // GaaletLexer.g:112:8: LETTER
            {
            mLETTER(); 

            }

            // GaaletLexer.g:112:16: ( LETTER | DIGIT | ( DOT IDENTIFIER_RECURSIVE ) | ( DOUBLE_COLON IDENTIFIER_RECURSIVE ) | ( ARROW_RIGHT IDENTIFIER_RECURSIVE ) )*
            loop5:
            do {
                int alt5=6;
                switch ( input.LA(1) ) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt5=1;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    {
                    alt5=2;
                    }
                    break;
                case '.':
                    {
                    alt5=3;
                    }
                    break;
                case ':':
                    {
                    alt5=4;
                    }
                    break;
                case '-':
                    {
                    alt5=5;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // GaaletLexer.g:112:17: LETTER
            	    {
            	    mLETTER(); 

            	    }
            	    break;
            	case 2 :
            	    // GaaletLexer.g:112:24: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;
            	case 3 :
            	    // GaaletLexer.g:112:30: ( DOT IDENTIFIER_RECURSIVE )
            	    {
            	    // GaaletLexer.g:112:30: ( DOT IDENTIFIER_RECURSIVE )
            	    // GaaletLexer.g:112:31: DOT IDENTIFIER_RECURSIVE
            	    {
            	    mDOT(); 
            	    mIDENTIFIER_RECURSIVE(); 

            	    }


            	    }
            	    break;
            	case 4 :
            	    // GaaletLexer.g:113:20: ( DOUBLE_COLON IDENTIFIER_RECURSIVE )
            	    {
            	    // GaaletLexer.g:113:20: ( DOUBLE_COLON IDENTIFIER_RECURSIVE )
            	    // GaaletLexer.g:113:21: DOUBLE_COLON IDENTIFIER_RECURSIVE
            	    {
            	    mDOUBLE_COLON(); 
            	    mIDENTIFIER_RECURSIVE(); 

            	    }


            	    }
            	    break;
            	case 5 :
            	    // GaaletLexer.g:114:20: ( ARROW_RIGHT IDENTIFIER_RECURSIVE )
            	    {
            	    // GaaletLexer.g:114:20: ( ARROW_RIGHT IDENTIFIER_RECURSIVE )
            	    // GaaletLexer.g:114:21: ARROW_RIGHT IDENTIFIER_RECURSIVE
            	    {
            	    mARROW_RIGHT(); 
            	    mIDENTIFIER_RECURSIVE(); 

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // GaaletLexer.g:115:7: ( LSBRACKET IDENTIFIER_RECURSIVE RSBRACKET )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='[') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // GaaletLexer.g:115:8: LSBRACKET IDENTIFIER_RECURSIVE RSBRACKET
            	    {
            	    mLSBRACKET(); 
            	    mIDENTIFIER_RECURSIVE(); 
            	    mRSBRACKET(); 

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER_RECURSIVE"

    // $ANTLR start "IDENTIFIER_TYPE_CAST"
    public final void mIDENTIFIER_TYPE_CAST() throws RecognitionException {
        try {
            // GaaletLexer.g:120:3: ( LBRACKET ( DOUBLE | FLOAT | INTEGER ) RBRACKET )
            // GaaletLexer.g:120:5: LBRACKET ( DOUBLE | FLOAT | INTEGER ) RBRACKET
            {
            mLBRACKET(); 
            // GaaletLexer.g:120:14: ( DOUBLE | FLOAT | INTEGER )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 'd':
                {
                alt7=1;
                }
                break;
            case 'f':
                {
                alt7=2;
                }
                break;
            case 'i':
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // GaaletLexer.g:120:15: DOUBLE
                    {
                    mDOUBLE(); 

                    }
                    break;
                case 2 :
                    // GaaletLexer.g:120:24: FLOAT
                    {
                    mFLOAT(); 

                    }
                    break;
                case 3 :
                    // GaaletLexer.g:120:32: INTEGER
                    {
                    mINTEGER(); 

                    }
                    break;

            }

            mRBRACKET(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER_TYPE_CAST"

    // $ANTLR start "FLOATING_POINT_LITERAL"
    public final void mFLOATING_POINT_LITERAL() throws RecognitionException {
        try {
            int _type = FLOATING_POINT_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:126:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? ( FLOATTYPESUFFIX )? | '.' ( '0' .. '9' )+ ( EXPONENT )? ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ EXPONENT ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ FLOATTYPESUFFIX )
            int alt18=4;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // GaaletLexer.g:126:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? ( FLOATTYPESUFFIX )?
                    {
                    // GaaletLexer.g:126:9: ( '0' .. '9' )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // GaaletLexer.g:126:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);

                    match('.'); 
                    // GaaletLexer.g:126:25: ( '0' .. '9' )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // GaaletLexer.g:126:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    // GaaletLexer.g:126:37: ( EXPONENT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // GaaletLexer.g:126:37: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }

                    // GaaletLexer.g:126:47: ( FLOATTYPESUFFIX )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='d'||LA11_0=='f') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // GaaletLexer.g:126:47: FLOATTYPESUFFIX
                            {
                            mFLOATTYPESUFFIX(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // GaaletLexer.g:127:9: '.' ( '0' .. '9' )+ ( EXPONENT )? ( FLOATTYPESUFFIX )?
                    {
                    match('.'); 
                    // GaaletLexer.g:127:13: ( '0' .. '9' )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // GaaletLexer.g:127:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);

                    // GaaletLexer.g:127:25: ( EXPONENT )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='e') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // GaaletLexer.g:127:25: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }

                    // GaaletLexer.g:127:35: ( FLOATTYPESUFFIX )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='d'||LA14_0=='f') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // GaaletLexer.g:127:35: FLOATTYPESUFFIX
                            {
                            mFLOATTYPESUFFIX(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // GaaletLexer.g:128:9: ( '0' .. '9' )+ EXPONENT ( FLOATTYPESUFFIX )?
                    {
                    // GaaletLexer.g:128:9: ( '0' .. '9' )+
                    int cnt15=0;
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // GaaletLexer.g:128:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt15 >= 1 ) break loop15;
                                EarlyExitException eee =
                                    new EarlyExitException(15, input);
                                throw eee;
                        }
                        cnt15++;
                    } while (true);

                    mEXPONENT(); 
                    // GaaletLexer.g:128:30: ( FLOATTYPESUFFIX )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='d'||LA16_0=='f') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // GaaletLexer.g:128:30: FLOATTYPESUFFIX
                            {
                            mFLOATTYPESUFFIX(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // GaaletLexer.g:129:9: ( '0' .. '9' )+ FLOATTYPESUFFIX
                    {
                    // GaaletLexer.g:129:9: ( '0' .. '9' )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // GaaletLexer.g:129:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
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

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // GaaletLexer.g:134:2: ( 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // GaaletLexer.g:
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
            // GaaletLexer.g:140:7: ( '0' .. '9' )
            // GaaletLexer.g:140:9: '0' .. '9'
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
            // GaaletLexer.g:143:5: ( SPACE )
            // GaaletLexer.g:143:7: SPACE
            {
            mSPACE(); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "SPACE"
    public final void mSPACE() throws RecognitionException {
        try {
            // GaaletLexer.g:148:3: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // GaaletLexer.g:148:6: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
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
    // $ANTLR end "SPACE"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:153:2: ( ',' )
            // GaaletLexer.g:153:4: ','
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
            // GaaletLexer.g:157:2: ( '+' )
            // GaaletLexer.g:157:4: '+'
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
            // GaaletLexer.g:161:2: ( '-' )
            // GaaletLexer.g:161:4: '-'
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
            // GaaletLexer.g:166:2: ( '*' )
            // GaaletLexer.g:166:4: '*'
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
            // GaaletLexer.g:170:2: ( '/' )
            // GaaletLexer.g:170:4: '/'
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
            // GaaletLexer.g:174:2: ( '%' )
            // GaaletLexer.g:174:4: '%'
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
            // GaaletLexer.g:178:2: ( '[' )
            // GaaletLexer.g:178:4: '['
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
            // GaaletLexer.g:182:2: ( ']' )
            // GaaletLexer.g:182:4: ']'
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
            // GaaletLexer.g:186:2: ( '(' )
            // GaaletLexer.g:186:4: '('
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
            // GaaletLexer.g:190:2: ( ')' )
            // GaaletLexer.g:190:4: ')'
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
            // GaaletLexer.g:194:2: ( '{' )
            // GaaletLexer.g:194:4: '{'
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
            // GaaletLexer.g:198:2: ( '}' )
            // GaaletLexer.g:198:4: '}'
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
            // GaaletLexer.g:202:2: ( '~' )
            // GaaletLexer.g:202:4: '~'
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
            // GaaletLexer.g:206:2: ( '!' )
            // GaaletLexer.g:206:4: '!'
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
            // GaaletLexer.g:210:2: ( '!!' )
            // GaaletLexer.g:210:4: '!!'
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
            // GaaletLexer.g:214:2: ( ';' )
            // GaaletLexer.g:214:4: ';'
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
            // GaaletLexer.g:217:7: ( '^' )
            // GaaletLexer.g:217:9: '^'
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
            // GaaletLexer.g:221:5: ( '.' )
            // GaaletLexer.g:221:7: '.'
            {
            match('.'); 

            }

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
            // GaaletLexer.g:226:2: ( '?' )
            // GaaletLexer.g:226:4: '?'
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
            // GaaletLexer.g:230:2: ( ':' )
            // GaaletLexer.g:230:4: ':'
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

    // $ANTLR start "DOUBLE_COLON"
    public final void mDOUBLE_COLON() throws RecognitionException {
        try {
            // GaaletLexer.g:235:3: ( '::' )
            // GaaletLexer.g:235:5: '::'
            {
            match("::"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_COLON"

    // $ANTLR start "DOUBLE_BAR"
    public final void mDOUBLE_BAR() throws RecognitionException {
        try {
            int _type = DOUBLE_BAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:239:2: ( '||' )
            // GaaletLexer.g:239:5: '||'
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
            // GaaletLexer.g:243:2: ( '&&' )
            // GaaletLexer.g:243:4: '&&'
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

    // $ANTLR start "SINGLE_AND"
    public final void mSINGLE_AND() throws RecognitionException {
        try {
            int _type = SINGLE_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:247:3: ( '&' )
            // GaaletLexer.g:247:5: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SINGLE_AND"

    // $ANTLR start "DOUBLE_EQUALS"
    public final void mDOUBLE_EQUALS() throws RecognitionException {
        try {
            int _type = DOUBLE_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:251:2: ( '==' )
            // GaaletLexer.g:251:4: '=='
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

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:255:3: ( '=' )
            // GaaletLexer.g:255:5: '='
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

    // $ANTLR start "UNEQUAL"
    public final void mUNEQUAL() throws RecognitionException {
        try {
            int _type = UNEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:259:2: ( '!=' )
            // GaaletLexer.g:259:4: '!='
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
            // GaaletLexer.g:263:2: ( '<' )
            // GaaletLexer.g:263:4: '<'
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
            // GaaletLexer.g:267:2: ( '>' )
            // GaaletLexer.g:267:5: '>'
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

    // $ANTLR start "ARROW_RIGHT"
    public final void mARROW_RIGHT() throws RecognitionException {
        try {
            // GaaletLexer.g:272:3: ( '->' )
            // GaaletLexer.g:272:5: '->'
            {
            match("->"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "ARROW_RIGHT"

    // $ANTLR start "LESS_OR_EQUAL"
    public final void mLESS_OR_EQUAL() throws RecognitionException {
        try {
            int _type = LESS_OR_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:275:2: ( '<=' )
            // GaaletLexer.g:275:4: '<='
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
            // GaaletLexer.g:279:2: ( '>=' )
            // GaaletLexer.g:279:4: '>='
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

    // $ANTLR start "SET_OUTPUT"
    public final void mSET_OUTPUT() throws RecognitionException {
        try {
            int _type = SET_OUTPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:283:3: ( '//?' )
            // GaaletLexer.g:283:5: '//?'
            {
            match("//?"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SET_OUTPUT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GaaletLexer.g:288:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // GaaletLexer.g:288:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // GaaletLexer.g:288:14: ( options {greedy=false; } : . )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0=='*') ) {
                    int LA19_1 = input.LA(2);

                    if ( (LA19_1=='/') ) {
                        alt19=2;
                    }
                    else if ( ((LA19_1>='\u0000' && LA19_1<='.')||(LA19_1>='0' && LA19_1<='\uFFFF')) ) {
                        alt19=1;
                    }


                }
                else if ( ((LA19_0>='\u0000' && LA19_0<=')')||(LA19_0>='+' && LA19_0<='\uFFFF')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // GaaletLexer.g:288:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop19;
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
            // GaaletLexer.g:294:5: ( '//#pragma' )
            // GaaletLexer.g:294:9: '//#pragma'
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
            // GaaletLexer.g:298:5: ( '//' ~ ( '#' | '?' ) (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // GaaletLexer.g:298:7: '//' ~ ( '#' | '?' ) (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<='\"')||(input.LA(1)>='$' && input.LA(1)<='>')||(input.LA(1)>='@' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // GaaletLexer.g:298:23: (~ ( '\\n' | '\\r' ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='\u0000' && LA20_0<='\t')||(LA20_0>='\u000B' && LA20_0<='\f')||(LA20_0>='\u000E' && LA20_0<='\uFFFF')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // GaaletLexer.g:298:23: ~ ( '\\n' | '\\r' )
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
            	    break loop20;
                }
            } while (true);

            // GaaletLexer.g:298:37: ( '\\r' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='\r') ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // GaaletLexer.g:298:37: '\\r'
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

    public void mTokens() throws RecognitionException {
        // GaaletLexer.g:1:8: ( DECIMAL_LITERAL | HEX | RANGE_LITERAL | OUTPUT_LITERAL | UNROLL_LITERAL | COUNT_LITERAL | IGNORE_LITERAL | LOOP | BREAK | GEALG_MV | GEALG_TYPE | DOUBLE | FLOAT | INTEGER | UNSIGNED | SIGNED | AUTO | EVAL | INVERSE | OPNS | IPNS | IF | ELSE | IDENTIFIER | FLOATING_POINT_LITERAL | WS | COMMA | PLUS | MINUS | STAR | SLASH | MODULO | LSBRACKET | RSBRACKET | LBRACKET | RBRACKET | CLBRACKET | CRBRACKET | REVERSE | NOT | DOUBLE_NOT | SEMICOLON | WEDGE | QUESTIONMARK | COLON | DOUBLE_BAR | DOUBLE_AND | SINGLE_AND | DOUBLE_EQUALS | EQUALS | UNEQUAL | LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL | SET_OUTPUT | COMMENT | PRAGMA | LINE_COMMENT )
        int alt22=59;
        alt22 = dfa22.predict(input);
        switch (alt22) {
            case 1 :
                // GaaletLexer.g:1:10: DECIMAL_LITERAL
                {
                mDECIMAL_LITERAL(); 

                }
                break;
            case 2 :
                // GaaletLexer.g:1:26: HEX
                {
                mHEX(); 

                }
                break;
            case 3 :
                // GaaletLexer.g:1:30: RANGE_LITERAL
                {
                mRANGE_LITERAL(); 

                }
                break;
            case 4 :
                // GaaletLexer.g:1:44: OUTPUT_LITERAL
                {
                mOUTPUT_LITERAL(); 

                }
                break;
            case 5 :
                // GaaletLexer.g:1:59: UNROLL_LITERAL
                {
                mUNROLL_LITERAL(); 

                }
                break;
            case 6 :
                // GaaletLexer.g:1:74: COUNT_LITERAL
                {
                mCOUNT_LITERAL(); 

                }
                break;
            case 7 :
                // GaaletLexer.g:1:88: IGNORE_LITERAL
                {
                mIGNORE_LITERAL(); 

                }
                break;
            case 8 :
                // GaaletLexer.g:1:103: LOOP
                {
                mLOOP(); 

                }
                break;
            case 9 :
                // GaaletLexer.g:1:108: BREAK
                {
                mBREAK(); 

                }
                break;
            case 10 :
                // GaaletLexer.g:1:114: GEALG_MV
                {
                mGEALG_MV(); 

                }
                break;
            case 11 :
                // GaaletLexer.g:1:123: GEALG_TYPE
                {
                mGEALG_TYPE(); 

                }
                break;
            case 12 :
                // GaaletLexer.g:1:134: DOUBLE
                {
                mDOUBLE(); 

                }
                break;
            case 13 :
                // GaaletLexer.g:1:141: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 14 :
                // GaaletLexer.g:1:147: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 15 :
                // GaaletLexer.g:1:155: UNSIGNED
                {
                mUNSIGNED(); 

                }
                break;
            case 16 :
                // GaaletLexer.g:1:164: SIGNED
                {
                mSIGNED(); 

                }
                break;
            case 17 :
                // GaaletLexer.g:1:171: AUTO
                {
                mAUTO(); 

                }
                break;
            case 18 :
                // GaaletLexer.g:1:176: EVAL
                {
                mEVAL(); 

                }
                break;
            case 19 :
                // GaaletLexer.g:1:181: INVERSE
                {
                mINVERSE(); 

                }
                break;
            case 20 :
                // GaaletLexer.g:1:189: OPNS
                {
                mOPNS(); 

                }
                break;
            case 21 :
                // GaaletLexer.g:1:194: IPNS
                {
                mIPNS(); 

                }
                break;
            case 22 :
                // GaaletLexer.g:1:199: IF
                {
                mIF(); 

                }
                break;
            case 23 :
                // GaaletLexer.g:1:202: ELSE
                {
                mELSE(); 

                }
                break;
            case 24 :
                // GaaletLexer.g:1:207: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 25 :
                // GaaletLexer.g:1:218: FLOATING_POINT_LITERAL
                {
                mFLOATING_POINT_LITERAL(); 

                }
                break;
            case 26 :
                // GaaletLexer.g:1:241: WS
                {
                mWS(); 

                }
                break;
            case 27 :
                // GaaletLexer.g:1:244: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 28 :
                // GaaletLexer.g:1:250: PLUS
                {
                mPLUS(); 

                }
                break;
            case 29 :
                // GaaletLexer.g:1:255: MINUS
                {
                mMINUS(); 

                }
                break;
            case 30 :
                // GaaletLexer.g:1:261: STAR
                {
                mSTAR(); 

                }
                break;
            case 31 :
                // GaaletLexer.g:1:266: SLASH
                {
                mSLASH(); 

                }
                break;
            case 32 :
                // GaaletLexer.g:1:272: MODULO
                {
                mMODULO(); 

                }
                break;
            case 33 :
                // GaaletLexer.g:1:279: LSBRACKET
                {
                mLSBRACKET(); 

                }
                break;
            case 34 :
                // GaaletLexer.g:1:289: RSBRACKET
                {
                mRSBRACKET(); 

                }
                break;
            case 35 :
                // GaaletLexer.g:1:299: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 36 :
                // GaaletLexer.g:1:308: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 37 :
                // GaaletLexer.g:1:317: CLBRACKET
                {
                mCLBRACKET(); 

                }
                break;
            case 38 :
                // GaaletLexer.g:1:327: CRBRACKET
                {
                mCRBRACKET(); 

                }
                break;
            case 39 :
                // GaaletLexer.g:1:337: REVERSE
                {
                mREVERSE(); 

                }
                break;
            case 40 :
                // GaaletLexer.g:1:345: NOT
                {
                mNOT(); 

                }
                break;
            case 41 :
                // GaaletLexer.g:1:349: DOUBLE_NOT
                {
                mDOUBLE_NOT(); 

                }
                break;
            case 42 :
                // GaaletLexer.g:1:360: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 43 :
                // GaaletLexer.g:1:370: WEDGE
                {
                mWEDGE(); 

                }
                break;
            case 44 :
                // GaaletLexer.g:1:376: QUESTIONMARK
                {
                mQUESTIONMARK(); 

                }
                break;
            case 45 :
                // GaaletLexer.g:1:389: COLON
                {
                mCOLON(); 

                }
                break;
            case 46 :
                // GaaletLexer.g:1:395: DOUBLE_BAR
                {
                mDOUBLE_BAR(); 

                }
                break;
            case 47 :
                // GaaletLexer.g:1:406: DOUBLE_AND
                {
                mDOUBLE_AND(); 

                }
                break;
            case 48 :
                // GaaletLexer.g:1:417: SINGLE_AND
                {
                mSINGLE_AND(); 

                }
                break;
            case 49 :
                // GaaletLexer.g:1:428: DOUBLE_EQUALS
                {
                mDOUBLE_EQUALS(); 

                }
                break;
            case 50 :
                // GaaletLexer.g:1:442: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 51 :
                // GaaletLexer.g:1:449: UNEQUAL
                {
                mUNEQUAL(); 

                }
                break;
            case 52 :
                // GaaletLexer.g:1:457: LESS
                {
                mLESS(); 

                }
                break;
            case 53 :
                // GaaletLexer.g:1:462: GREATER
                {
                mGREATER(); 

                }
                break;
            case 54 :
                // GaaletLexer.g:1:470: LESS_OR_EQUAL
                {
                mLESS_OR_EQUAL(); 

                }
                break;
            case 55 :
                // GaaletLexer.g:1:484: GREATER_OR_EQUAL
                {
                mGREATER_OR_EQUAL(); 

                }
                break;
            case 56 :
                // GaaletLexer.g:1:501: SET_OUTPUT
                {
                mSET_OUTPUT(); 

                }
                break;
            case 57 :
                // GaaletLexer.g:1:512: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 58 :
                // GaaletLexer.g:1:520: PRAGMA
                {
                mPRAGMA(); 

                }
                break;
            case 59 :
                // GaaletLexer.g:1:527: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA18 dfa18 = new DFA18(this);
    protected DFA22 dfa22 = new DFA22(this);
    static final String DFA18_eotS =
        "\6\uffff";
    static final String DFA18_eofS =
        "\6\uffff";
    static final String DFA18_minS =
        "\2\56\4\uffff";
    static final String DFA18_maxS =
        "\1\71\1\146\4\uffff";
    static final String DFA18_acceptS =
        "\2\uffff\1\2\1\4\1\1\1\3";
    static final String DFA18_specialS =
        "\6\uffff}>";
    static final String[] DFA18_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\4\1\uffff\12\1\52\uffff\1\3\1\5\1\3",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "125:1: FLOATING_POINT_LITERAL : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? ( FLOATTYPESUFFIX )? | '.' ( '0' .. '9' )+ ( EXPONENT )? ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ EXPONENT ( FLOATTYPESUFFIX )? | ( '0' .. '9' )+ FLOATTYPESUFFIX );";
        }
    }
    static final String DFA22_eotS =
        "\1\uffff\2\55\10\23\1\71\7\23\7\uffff\1\104\10\uffff\1\107\4\uffff"+
        "\1\111\1\113\1\115\1\117\2\uffff\6\23\1\127\3\23\2\uffff\10\23\16"+
        "\uffff\6\23\1\154\1\uffff\13\23\3\uffff\6\23\1\uffff\1\176\5\23"+
        "\1\u0084\1\u0085\1\u0086\1\u0087\1\u0088\1\u0089\3\23\1\u008d\1"+
        "\23\1\uffff\1\u008f\2\23\1\u0092\1\23\6\uffff\1\u0094\1\u0095\1"+
        "\23\1\uffff\1\u0097\1\uffff\1\23\1\u0099\1\uffff\1\u009a\2\uffff"+
        "\1\23\4\uffff\1\u009d\2\uffff\4\23\1\uffff\1\23\1\uffff\3\23\1\u00aa"+
        "\1\23\1\uffff\1\u00ac\1\uffff";
    static final String DFA22_eofS =
        "\u00ad\uffff";
    static final String DFA22_minS =
        "\1\11\2\56\1\141\1\165\1\156\1\157\1\146\1\157\1\162\1\141\1\72"+
        "\1\157\1\154\1\151\1\165\1\154\2\120\7\uffff\1\52\10\uffff\1\41"+
        "\4\uffff\1\46\3\75\2\uffff\1\156\1\164\1\162\1\165\1\156\1\164\1"+
        "\55\1\157\1\145\1\141\2\uffff\1\165\1\157\1\147\1\164\1\141\1\163"+
        "\2\116\1\0\15\uffff\1\147\1\160\1\157\1\151\1\156\1\157\1\55\1\uffff"+
        "\1\160\1\141\1\154\1\142\1\141\1\156\1\157\1\154\1\145\2\123\3\uffff"+
        "\1\145\1\165\1\154\1\147\1\164\1\162\1\uffff\1\55\1\153\1\145\1"+
        "\154\1\164\1\145\6\55\1\164\1\154\1\156\1\55\1\145\1\uffff\1\55"+
        "\1\164\1\145\1\55\1\144\6\uffff\2\55\1\145\1\uffff\1\55\1\uffff"+
        "\1\72\1\55\1\uffff\1\55\2\uffff\1\144\1\uffff\1\72\2\uffff\1\55"+
        "\1\101\1\uffff\1\155\1\156\1\72\1\166\1\72\1\145\1\101\1\162\1\166"+
        "\1\163\1\55\1\145\1\uffff\1\55\1\uffff";
    static final String DFA22_maxS =
        "\1\176\1\170\1\146\1\141\1\165\1\156\1\157\1\156\1\157\1\162\1\141"+
        "\1\72\1\157\1\154\1\151\1\165\1\166\2\120\7\uffff\1\57\10\uffff"+
        "\1\75\4\uffff\1\46\3\75\2\uffff\1\156\1\164\1\163\1\165\1\156\1"+
        "\164\1\172\1\157\1\145\1\141\2\uffff\1\165\1\157\1\147\1\164\1\141"+
        "\1\163\2\116\1\uffff\15\uffff\1\147\1\160\1\157\1\151\1\156\1\157"+
        "\1\172\1\uffff\1\160\1\141\1\154\1\142\1\141\1\156\1\157\1\154\1"+
        "\145\2\123\3\uffff\1\145\1\165\1\154\1\147\1\164\1\162\1\uffff\1"+
        "\172\1\153\1\145\1\154\1\164\1\145\6\172\1\164\1\154\1\156\1\172"+
        "\1\145\1\uffff\1\172\1\164\1\145\1\172\1\144\6\uffff\2\172\1\145"+
        "\1\uffff\1\172\1\uffff\1\72\1\172\1\uffff\1\172\2\uffff\1\144\1"+
        "\uffff\1\72\2\uffff\2\172\1\uffff\1\155\1\156\1\72\1\166\1\72\1"+
        "\145\1\172\1\162\1\166\1\163\1\172\1\145\1\uffff\1\172\1\uffff";
    static final String DFA22_acceptS =
        "\23\uffff\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\uffff\1\40\1\41\1"+
        "\42\1\43\1\44\1\45\1\46\1\47\1\uffff\1\52\1\53\1\54\1\56\4\uffff"+
        "\1\2\1\1\12\uffff\1\13\1\55\11\uffff\1\71\1\37\1\51\1\63\1\50\1"+
        "\57\1\60\1\61\1\62\1\66\1\64\1\67\1\65\7\uffff\1\26\13\uffff\1\70"+
        "\1\72\1\73\6\uffff\1\16\21\uffff\1\10\5\uffff\1\21\1\22\1\27\1\24"+
        "\1\25\1\3\3\uffff\1\6\1\uffff\1\11\2\uffff\1\15\1\uffff\1\4\1\5"+
        "\1\uffff\1\7\1\uffff\1\14\1\20\2\uffff\1\17\14\uffff\1\12\1\uffff"+
        "\1\23";
    static final String DFA22_specialS =
        "\102\uffff\1\0\152\uffff}>";
    static final String[] DFA22_transitionS = {
            "\2\25\1\uffff\2\25\22\uffff\1\25\1\43\3\uffff\1\33\1\50\1\uffff"+
            "\1\36\1\37\1\31\1\27\1\26\1\30\1\24\1\32\1\1\11\2\1\13\1\44"+
            "\1\52\1\51\1\53\1\46\1\uffff\10\23\1\22\5\23\1\21\13\23\1\34"+
            "\1\uffff\1\35\1\45\1\23\1\uffff\1\17\1\11\1\6\1\14\1\20\1\15"+
            "\1\12\1\23\1\7\2\23\1\10\2\23\1\4\2\23\1\3\1\16\1\23\1\5\5\23"+
            "\1\40\1\47\1\41\1\42",
            "\1\24\1\uffff\12\2\52\uffff\3\24\21\uffff\1\54",
            "\1\24\1\uffff\12\2\52\uffff\3\24",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\64\1\62\6\uffff\1\63",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\77\11\uffff\1\76",
            "\1\100",
            "\1\101",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\103\4\uffff\1\102",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\105\33\uffff\1\106",
            "",
            "",
            "",
            "",
            "\1\110",
            "\1\112",
            "\1\114",
            "\1\116",
            "",
            "",
            "\1\120",
            "\1\121",
            "\1\122\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\130",
            "\1\131",
            "\1\132",
            "",
            "",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\43\145\1\144\33\145\1\143\uffc0\145",
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
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "",
            "",
            "",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\u008e",
            "",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\u0090",
            "\1\u0091",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\u0093",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\u0096",
            "",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u0098",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "",
            "",
            "\1\u009b",
            "",
            "\1\u009c",
            "",
            "",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\32\23\4\uffff\1\23\1\uffff\2\23\1\u009e\5\23\1\u009f\21\23",
            "",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\32\23\4\uffff\1\23\1\uffff\14\23\1\u00a6\15\23",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            "\1\u00ab",
            "",
            "\2\23\1\uffff\13\23\6\uffff\33\23\3\uffff\1\23\1\uffff\32\23",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( DECIMAL_LITERAL | HEX | RANGE_LITERAL | OUTPUT_LITERAL | UNROLL_LITERAL | COUNT_LITERAL | IGNORE_LITERAL | LOOP | BREAK | GEALG_MV | GEALG_TYPE | DOUBLE | FLOAT | INTEGER | UNSIGNED | SIGNED | AUTO | EVAL | INVERSE | OPNS | IPNS | IF | ELSE | IDENTIFIER | FLOATING_POINT_LITERAL | WS | COMMA | PLUS | MINUS | STAR | SLASH | MODULO | LSBRACKET | RSBRACKET | LBRACKET | RBRACKET | CLBRACKET | CRBRACKET | REVERSE | NOT | DOUBLE_NOT | SEMICOLON | WEDGE | QUESTIONMARK | COLON | DOUBLE_BAR | DOUBLE_AND | SINGLE_AND | DOUBLE_EQUALS | EQUALS | UNEQUAL | LESS | GREATER | LESS_OR_EQUAL | GREATER_OR_EQUAL | SET_OUTPUT | COMMENT | PRAGMA | LINE_COMMENT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA22_66 = input.LA(1);

                        s = -1;
                        if ( (LA22_66=='?') ) {s = 99;}

                        else if ( (LA22_66=='#') ) {s = 100;}

                        else if ( ((LA22_66>='\u0000' && LA22_66<='\"')||(LA22_66>='$' && LA22_66<='>')||(LA22_66>='@' && LA22_66<='\uFFFF')) ) {s = 101;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 22, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}