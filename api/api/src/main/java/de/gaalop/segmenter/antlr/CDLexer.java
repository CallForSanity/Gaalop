// $ANTLR 3.2 Sep 23, 2009 12:02:23 CDLexer.g 2010-11-18 13:21:53

    package de.gaalop.segmenter.antlr;
    
import de.gaalop.CodeGenerator;
import de.gaalop.CodeParser;
import de.gaalop.OptimizationStrategy;
import de.gaalop.segmenter.*;   


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CDLexer extends Lexer {
    public static final int DRAW_END=10;
    public static final int RAW=8;
    public static final int HIDE=6;
    public static final int OPT=4;
    public static final int CORRECT_PRAGMA=11;
    public static final int DELETE=7;
    public static final int IGNORE=13;
    public static final int EOF=-1;
    public static final int CORRECT=12;
    public static final int DRAW=9;
    public static final int TRANS=5;


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


    // delegates
    // delegators

    public CDLexer() {;} 
    public CDLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CDLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "CDLexer.g"; }

    // $ANTLR start "OPT"
    public final void mOPT() throws RecognitionException {
        try {
            int _type = OPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:30:5: ( '//#optimize' )
            // CDLexer.g:30:7: '//#optimize'
            {
            match("//#optimize"); 

            System.out.println("//optimized code \n");
                              machine.changeState(new CodeOptimize(parser, optimizationStrategy, codeGenerator));

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OPT"

    // $ANTLR start "TRANS"
    public final void mTRANS() throws RecognitionException {
        try {
            int _type = TRANS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:32:7: ( '//#translate' )
            // CDLexer.g:32:9: '//#translate'
            {
            match("//#translate"); 

            System.out.println("//translated code \n");
                              machine.changeState(new CodeTranslate(parser, codeGenerator));

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRANS"

    // $ANTLR start "HIDE"
    public final void mHIDE() throws RecognitionException {
        try {
            int _type = HIDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:34:5: ( '//#hide' )
            // CDLexer.g:34:7: '//#hide'
            {
            match("//#hide"); 

            System.out.println("//hide code \n");
                               machine.changeState(new CodeHide(parser, optimizationStrategy, codeGenerator)); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HIDE"

    // $ANTLR start "DELETE"
    public final void mDELETE() throws RecognitionException {
        try {
            int _type = DELETE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:37:7: ( '//#delete' )
            // CDLexer.g:37:9: '//#delete'
            {
            match("//#delete"); 

            System.out.println("//delete code \n");
                               machine.changeState(new CodeDelete()); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DELETE"

    // $ANTLR start "RAW"
    public final void mRAW() throws RecognitionException {
        try {
            int _type = RAW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:41:5: ( '//#end' )
            // CDLexer.g:41:7: '//#end'
            {
            match("//#end"); 

            System.out.println("//back to raw code \n");
                   machine.changeState(new CodeRaw());

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RAW"

    // $ANTLR start "DRAW"
    public final void mDRAW() throws RecognitionException {
        try {
            int _type = DRAW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:44:6: ( '/*draw' )
            // CDLexer.g:44:8: '/*draw'
            {
            match("/*draw"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DRAW"

    // $ANTLR start "DRAW_END"
    public final void mDRAW_END() throws RecognitionException {
        try {
            int _type = DRAW_END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:46:9: ( '//#*/' )
            // CDLexer.g:46:11: '//#*/'
            {
            match("//#*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DRAW_END"

    // $ANTLR start "CORRECT_PRAGMA"
    public final void mCORRECT_PRAGMA() throws RecognitionException {
        try {
            int _type = CORRECT_PRAGMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:48:15: ( '//#' )
            // CDLexer.g:48:17: '//#'
            {
            match("//#"); 

             machine.addString("//#");    
                

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CORRECT_PRAGMA"

    // $ANTLR start "CORRECT"
    public final void mCORRECT() throws RecognitionException {
        try {
            int _type = CORRECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:51:8: ( '//' )
            // CDLexer.g:51:10: '//'
            {
            match("//"); 

             machine.addString("//");    
                

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CORRECT"

    // $ANTLR start "IGNORE"
    public final void mIGNORE() throws RecognitionException {
        try {
            int _type = IGNORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // CDLexer.g:57:5: ( | . )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>='\u0000' && LA1_0<='\uFFFF')) ) {
                alt1=2;
            }
            else {
                alt1=1;}
            switch (alt1) {
                case 1 :
                    // CDLexer.g:60:5: 
                    {
                    }
                    break;
                case 2 :
                    // CDLexer.g:60:9: .
                    {
                    matchAny(); 
                     machine.addString(getText());    
                        

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IGNORE"

    public void mTokens() throws RecognitionException {
        // CDLexer.g:1:8: ( OPT | TRANS | HIDE | DELETE | RAW | DRAW | DRAW_END | CORRECT_PRAGMA | CORRECT | IGNORE )
        int alt2=10;
        alt2 = dfa2.predict(input);
        switch (alt2) {
            case 1 :
                // CDLexer.g:1:10: OPT
                {
                mOPT(); 

                }
                break;
            case 2 :
                // CDLexer.g:1:14: TRANS
                {
                mTRANS(); 

                }
                break;
            case 3 :
                // CDLexer.g:1:20: HIDE
                {
                mHIDE(); 

                }
                break;
            case 4 :
                // CDLexer.g:1:25: DELETE
                {
                mDELETE(); 

                }
                break;
            case 5 :
                // CDLexer.g:1:32: RAW
                {
                mRAW(); 

                }
                break;
            case 6 :
                // CDLexer.g:1:36: DRAW
                {
                mDRAW(); 

                }
                break;
            case 7 :
                // CDLexer.g:1:41: DRAW_END
                {
                mDRAW_END(); 

                }
                break;
            case 8 :
                // CDLexer.g:1:50: CORRECT_PRAGMA
                {
                mCORRECT_PRAGMA(); 

                }
                break;
            case 9 :
                // CDLexer.g:1:65: CORRECT
                {
                mCORRECT(); 

                }
                break;
            case 10 :
                // CDLexer.g:1:73: IGNORE
                {
                mIGNORE(); 

                }
                break;

        }

    }


    protected DFA2 dfa2 = new DFA2(this);
    static final String DFA2_eotS =
        "\2\2\2\uffff\1\10\2\uffff\1\17\10\uffff";
    static final String DFA2_eofS =
        "\20\uffff";
    static final String DFA2_minS =
        "\1\57\1\52\2\uffff\1\43\2\uffff\1\52\10\uffff";
    static final String DFA2_maxS =
        "\2\57\2\uffff\1\43\2\uffff\1\164\10\uffff";
    static final String DFA2_acceptS =
        "\2\uffff\1\12\2\uffff\1\6\2\uffff\1\11\1\1\1\2\1\3\1\4\1\5\1\7\1"+
        "\10";
    static final String DFA2_specialS =
        "\20\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1",
            "\1\5\4\uffff\1\4",
            "",
            "",
            "\1\7",
            "",
            "",
            "\1\16\71\uffff\1\14\1\15\2\uffff\1\13\6\uffff\1\11\4\uffff\1"+
            "\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( OPT | TRANS | HIDE | DELETE | RAW | DRAW | DRAW_END | CORRECT_PRAGMA | CORRECT | IGNORE );";
        }
    }
 

}