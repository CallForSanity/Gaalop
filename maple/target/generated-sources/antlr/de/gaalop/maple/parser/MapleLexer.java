// $ANTLR 3.1.1 de\\gaalop\\maple\\parser\\MapleLexer.g 2010-05-05 19:19:48

	package de.gaalop.maple.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class MapleLexer extends Lexer {
    public static final int MODULO=22;
    public static final int EXPONENT=9;
    public static final int STAR=20;
    public static final int LETTER=5;
    public static final int GAALOPARRAY=4;
    public static final int SEMICOLON=23;
    public static final int EQUALS=17;
    public static final int MINUS=11;
    public static final int EOF=-1;
    public static final int LSBRACKET=12;
    public static final int LBRACKET=14;
    public static final int WEDGE=24;
    public static final int WS=25;
    public static final int SLASH=21;
    public static final int FLOATING_POINT_LITERAL=10;
    public static final int RSBRACKET=13;
    public static final int COMMA=18;
    public static final int IDENTIFIER=7;
    public static final int PLUS=19;
    public static final int ASSIGNMENT=16;
    public static final int DIGIT=6;
    public static final int RBRACKET=15;
    public static final int DECIMAL_LITERAL=8;

    // delegates
    // delegators

    public MapleLexer() {;} 
    public MapleLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public MapleLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "de\\gaalop\\maple\\parser\\MapleLexer.g"; }

    // $ANTLR start "GAALOPARRAY"
    public final void mGAALOPARRAY() throws RecognitionException {
        try {
            int _type = GAALOPARRAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:10:2: ( 'gaaloparray' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:10:4: 'gaaloparray'
            {
            match("gaaloparray"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GAALOPARRAY"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:14:2: ( LETTER ( LETTER | DIGIT )* )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:14:4: LETTER ( LETTER | DIGIT )*
            {
            mLETTER(); 
            // de\\gaalop\\maple\\parser\\MapleLexer.g:14:11: ( LETTER | DIGIT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // de\\gaalop\\maple\\parser\\MapleLexer.g:
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
            	    break loop1;
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

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // de\\gaalop\\maple\\parser\\MapleLexer.g:19:2: ( 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:25:7: ( '0' .. '9' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:25:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "DECIMAL_LITERAL"
    public final void mDECIMAL_LITERAL() throws RecognitionException {
        try {
            int _type = DECIMAL_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:29:2: ( ( '0' .. '9' )+ )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:29:4: ( '0' .. '9' )+
            {
            // de\\gaalop\\maple\\parser\\MapleLexer.g:29:4: ( '0' .. '9' )+
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
            	    // de\\gaalop\\maple\\parser\\MapleLexer.g:29:5: '0' .. '9'
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:33:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT )
            int alt9=3;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:33:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )?
                    {
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:33:9: ( '0' .. '9' )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // de\\gaalop\\maple\\parser\\MapleLexer.g:33:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);

                    match('.'); 
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:33:25: ( '0' .. '9' )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // de\\gaalop\\maple\\parser\\MapleLexer.g:33:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    // de\\gaalop\\maple\\parser\\MapleLexer.g:33:37: ( EXPONENT )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='e') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // de\\gaalop\\maple\\parser\\MapleLexer.g:33:37: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:34:9: '.' ( '0' .. '9' )+ ( EXPONENT )?
                    {
                    match('.'); 
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:34:13: ( '0' .. '9' )+
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
                    	    // de\\gaalop\\maple\\parser\\MapleLexer.g:34:14: '0' .. '9'
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

                    // de\\gaalop\\maple\\parser\\MapleLexer.g:34:25: ( EXPONENT )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='e') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // de\\gaalop\\maple\\parser\\MapleLexer.g:34:25: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:35:9: ( '0' .. '9' )+ EXPONENT
                    {
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:35:9: ( '0' .. '9' )+
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
                    	    // de\\gaalop\\maple\\parser\\MapleLexer.g:35:10: '0' .. '9'
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

                    mEXPONENT(); 

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

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // de\\gaalop\\maple\\parser\\MapleLexer.g:40:2: ( 'e' ( MINUS )? ( '0' .. '9' )+ )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:40:4: 'e' ( MINUS )? ( '0' .. '9' )+
            {
            match('e'); 
            // de\\gaalop\\maple\\parser\\MapleLexer.g:40:8: ( MINUS )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='-') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // de\\gaalop\\maple\\parser\\MapleLexer.g:40:8: MINUS
                    {
                    mMINUS(); 

                    }
                    break;

            }

            // de\\gaalop\\maple\\parser\\MapleLexer.g:40:15: ( '0' .. '9' )+
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
            	    // de\\gaalop\\maple\\parser\\MapleLexer.g:40:16: '0' .. '9'
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


            }

        }
        finally {
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "LSBRACKET"
    public final void mLSBRACKET() throws RecognitionException {
        try {
            int _type = LSBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:44:2: ( '[' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:44:4: '['
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:48:2: ( ']' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:48:4: ']'
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:52:2: ( '(' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:52:4: '('
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:56:2: ( ')' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:56:4: ')'
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

    // $ANTLR start "ASSIGNMENT"
    public final void mASSIGNMENT() throws RecognitionException {
        try {
            int _type = ASSIGNMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:60:2: ( ':=' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:60:4: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSIGNMENT"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:64:2: ( '=' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:64:4: '='
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:69:2: ( ',' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:69:4: ','
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:73:2: ( '+' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:73:4: '+'
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:77:2: ( '-' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:77:4: '-'
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:82:2: ( '*' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:82:4: '*'
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:86:2: ( '/' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:86:4: '/'
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:90:2: ( '%' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:90:4: '%'
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

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:95:2: ( ';' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:95:4: ';'
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
            // de\\gaalop\\maple\\parser\\MapleLexer.g:98:7: ( '^' )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:98:9: '^'
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

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // de\\gaalop\\maple\\parser\\MapleLexer.g:101:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // de\\gaalop\\maple\\parser\\MapleLexer.g:101:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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

    public void mTokens() throws RecognitionException {
        // de\\gaalop\\maple\\parser\\MapleLexer.g:1:8: ( GAALOPARRAY | IDENTIFIER | DECIMAL_LITERAL | FLOATING_POINT_LITERAL | LSBRACKET | RSBRACKET | LBRACKET | RBRACKET | ASSIGNMENT | EQUALS | COMMA | PLUS | MINUS | STAR | SLASH | MODULO | SEMICOLON | WEDGE | WS )
        int alt12=19;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:10: GAALOPARRAY
                {
                mGAALOPARRAY(); 

                }
                break;
            case 2 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:22: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 3 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:33: DECIMAL_LITERAL
                {
                mDECIMAL_LITERAL(); 

                }
                break;
            case 4 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:49: FLOATING_POINT_LITERAL
                {
                mFLOATING_POINT_LITERAL(); 

                }
                break;
            case 5 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:72: LSBRACKET
                {
                mLSBRACKET(); 

                }
                break;
            case 6 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:82: RSBRACKET
                {
                mRSBRACKET(); 

                }
                break;
            case 7 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:92: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 8 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:101: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 9 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:110: ASSIGNMENT
                {
                mASSIGNMENT(); 

                }
                break;
            case 10 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:121: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 11 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:128: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 12 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:134: PLUS
                {
                mPLUS(); 

                }
                break;
            case 13 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:139: MINUS
                {
                mMINUS(); 

                }
                break;
            case 14 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:145: STAR
                {
                mSTAR(); 

                }
                break;
            case 15 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:150: SLASH
                {
                mSLASH(); 

                }
                break;
            case 16 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:156: MODULO
                {
                mMODULO(); 

                }
                break;
            case 17 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:163: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 18 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:173: WEDGE
                {
                mWEDGE(); 

                }
                break;
            case 19 :
                // de\\gaalop\\maple\\parser\\MapleLexer.g:1:179: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA9_eotS =
        "\5\uffff";
    static final String DFA9_eofS =
        "\5\uffff";
    static final String DFA9_minS =
        "\2\56\3\uffff";
    static final String DFA9_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\2\1\3\1\1";
    static final String DFA9_specialS =
        "\5\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\4\1\uffff\12\1\53\uffff\1\3",
            "",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "32:1: FLOATING_POINT_LITERAL : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT );";
        }
    }
    static final String DFA12_eotS =
        "\1\uffff\1\2\1\uffff\1\25\20\uffff\1\2\1\uffff\10\2\1\37\1\uffff";
    static final String DFA12_eofS =
        "\40\uffff";
    static final String DFA12_minS =
        "\1\11\1\141\1\uffff\1\56\20\uffff\1\141\1\uffff\1\154\1\157\1\160"+
        "\1\141\2\162\1\141\1\171\1\60\1\uffff";
    static final String DFA12_maxS =
        "\1\172\1\141\1\uffff\1\145\20\uffff\1\141\1\uffff\1\154\1\157\1"+
        "\160\1\141\2\162\1\141\1\171\1\172\1\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23\1\uffff\1\3\11\uffff\1\1";
    static final String DFA12_specialS =
        "\40\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\23\1\uffff\2\23\22\uffff\1\23\4\uffff\1\20\2\uffff\1\7\1"+
            "\10\1\16\1\14\1\13\1\15\1\4\1\17\12\3\1\11\1\21\1\uffff\1\12"+
            "\3\uffff\32\2\1\5\1\uffff\1\6\1\22\1\2\1\uffff\6\2\1\1\23\2",
            "\1\24",
            "",
            "\1\4\1\uffff\12\3\53\uffff\1\4",
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
            "\1\26",
            "",
            "\1\27",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\12\2\7\uffff\32\2\4\uffff\1\2\1\uffff\32\2",
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
            return "1:1: Tokens : ( GAALOPARRAY | IDENTIFIER | DECIMAL_LITERAL | FLOATING_POINT_LITERAL | LSBRACKET | RSBRACKET | LBRACKET | RBRACKET | ASSIGNMENT | EQUALS | COMMA | PLUS | MINUS | STAR | SLASH | MODULO | SEMICOLON | WEDGE | WS );";
        }
    }
 

}