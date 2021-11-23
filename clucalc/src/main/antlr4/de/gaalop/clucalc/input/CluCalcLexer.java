// Generated from CluCalcLexer.g by ANTLR 4.5.3

	package de.gaalop.clucalc.input;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CluCalcLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DECIMAL_LITERAL=1, FLOATING_POINT_LITERAL=2, RANGE_LITERAL=3, UNROLL_LITERAL=4, 
		COUNT_LITERAL=5, STRING_LITERAL=6, OPNS=7, IPNS=8, IF=9, ELSE=10, LOOP=11, 
		BREAK=12, SLIDER_LITERAL=13, COLOR_LITERAL=14, BGCOLOR_LITERAL=15, BLACK=16, 
		BLUE=17, CYAN=18, GREEN=19, MAGENTA=20, ORANGE=21, RED=22, WHITE=23, YELLOW=24, 
		IDENTIFIER=25, ARGUMENT_PREFIX=26, WS=27, COMMENT=28, PRAGMA=29, LINE_COMMENT=30, 
		EQUALS=31, COMMA=32, PLUS=33, MINUS=34, STAR=35, SLASH=36, MODULO=37, 
		LSBRACKET=38, RSBRACKET=39, LBRACKET=40, RBRACKET=41, CLBRACKET=42, CRBRACKET=43, 
		REVERSE=44, NOT=45, DOUBLE_NOT=46, SEMICOLON=47, WEDGE=48, DOT=49, QUESTIONMARK=50, 
		COLON=51, DOUBLE_BAR=52, DOUBLE_AND=53, DOUBLE_EQUALS=54, UNEQUAL=55, 
		LESS=56, GREATER=57, LESS_OR_EQUAL=58, GREATER_OR_EQUAL=59, REFERENCE_OPERATOR=60;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"DECIMAL_LITERAL", "FLOATING_POINT_LITERAL", "RANGE_LITERAL", "UNROLL_LITERAL", 
		"COUNT_LITERAL", "STRING_LITERAL", "EXPONENT", "FLOATTYPESUFFIX", "OPNS", 
		"IPNS", "IF", "ELSE", "LOOP", "BREAK", "SLIDER_LITERAL", "COLOR_LITERAL", 
		"BGCOLOR_LITERAL", "BLACK", "BLUE", "CYAN", "GREEN", "MAGENTA", "ORANGE", 
		"RED", "WHITE", "YELLOW", "IDENTIFIER", "ARGUMENT_PREFIX", "LETTER", "DIGIT", 
		"WS", "COMMENT", "PRAGMA", "LINE_COMMENT", "EQUALS", "COMMA", "PLUS", 
		"MINUS", "STAR", "SLASH", "MODULO", "LSBRACKET", "RSBRACKET", "LBRACKET", 
		"RBRACKET", "CLBRACKET", "CRBRACKET", "REVERSE", "NOT", "DOUBLE_NOT", 
		"SEMICOLON", "WEDGE", "DOT", "QUESTIONMARK", "COLON", "DOUBLE_BAR", "DOUBLE_AND", 
		"DOUBLE_EQUALS", "UNEQUAL", "LESS", "GREATER", "LESS_OR_EQUAL", "GREATER_OR_EQUAL", 
		"REFERENCE_OPERATOR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, "'range'", "'unroll'", "'count'", null, "'OPNS'", "'IPNS'", 
		"'if'", "'else'", "'loop'", "'break'", "'Slider'", "'Color'", "'_BGColor'", 
		"'Black'", "'Blue'", "'Cyan'", "'Green'", "'Magenta'", "'Orange'", "'Red'", 
		"'White'", "'Yellow'", null, "'_P('", null, null, "'//#pragma'", null, 
		"'='", "','", "'+'", "'-'", "'*'", "'/'", "'%'", "'['", "']'", "'('", 
		"')'", "'{'", "'}'", "'~'", "'!'", "'!!'", "';'", "'^'", "'.'", "'?'", 
		"':'", "'||'", "'&&'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", "'->'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "DECIMAL_LITERAL", "FLOATING_POINT_LITERAL", "RANGE_LITERAL", "UNROLL_LITERAL", 
		"COUNT_LITERAL", "STRING_LITERAL", "OPNS", "IPNS", "IF", "ELSE", "LOOP", 
		"BREAK", "SLIDER_LITERAL", "COLOR_LITERAL", "BGCOLOR_LITERAL", "BLACK", 
		"BLUE", "CYAN", "GREEN", "MAGENTA", "ORANGE", "RED", "WHITE", "YELLOW", 
		"IDENTIFIER", "ARGUMENT_PREFIX", "WS", "COMMENT", "PRAGMA", "LINE_COMMENT", 
		"EQUALS", "COMMA", "PLUS", "MINUS", "STAR", "SLASH", "MODULO", "LSBRACKET", 
		"RSBRACKET", "LBRACKET", "RBRACKET", "CLBRACKET", "CRBRACKET", "REVERSE", 
		"NOT", "DOUBLE_NOT", "SEMICOLON", "WEDGE", "DOT", "QUESTIONMARK", "COLON", 
		"DOUBLE_BAR", "DOUBLE_AND", "DOUBLE_EQUALS", "UNEQUAL", "LESS", "GREATER", 
		"LESS_OR_EQUAL", "GREATER_OR_EQUAL", "REFERENCE_OPERATOR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public CluCalcLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CluCalcLexer.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 30:
			WS_action((RuleContext)_localctx, actionIndex);
			break;
		case 31:
			COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 33:
			LINE_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			break;
		}
	}
	private void LINE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2>\u01ca\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\3\2\6\2\u0085\n\2\r\2\16\2\u0086\3\3\6\3\u008a"+
		"\n\3\r\3\16\3\u008b\3\3\3\3\7\3\u0090\n\3\f\3\16\3\u0093\13\3\3\3\5\3"+
		"\u0096\n\3\3\3\5\3\u0099\n\3\3\3\3\3\6\3\u009d\n\3\r\3\16\3\u009e\3\3"+
		"\5\3\u00a2\n\3\3\3\5\3\u00a5\n\3\3\3\6\3\u00a8\n\3\r\3\16\3\u00a9\3\3"+
		"\3\3\5\3\u00ae\n\3\3\3\6\3\u00b1\n\3\r\3\16\3\u00b2\3\3\5\3\u00b6\n\3"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\7\7\u00cd\n\7\f\7\16\7\u00d0\13\7\3\7\3\7\3\b\3\b\5\b\u00d6"+
		"\n\b\3\b\6\b\u00d9\n\b\r\b\16\b\u00da\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3"+
		"\31\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\34\3\34\5\34\u014a\n\34\3\34\3\34\3\34\7\34\u014f\n\34\f\34\16\34\u0152"+
		"\13\34\3\35\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3!\7"+
		"!\u0163\n!\f!\16!\u0166\13!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3#\3#\3#\3#\3#\7#\u017c\n#\f#\16#\u017f\13#\3#\5#\u0182\n"+
		"#\3#\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-"+
		"\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\63\3\64\3\64"+
		"\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\39\3:\3:\3:\3;\3;\3;\3<\3<"+
		"\3<\3=\3=\3>\3>\3?\3?\3?\3@\3@\3@\3A\3A\3A\2\2B\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\2\21\2\23\t\25\n\27\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24"+
		"+\25-\26/\27\61\30\63\31\65\32\67\339\34;\2=\2?\35A\36C\37E G!I\"K#M$"+
		"O%Q&S\'U(W)Y*[+],_-a.c/e\60g\61i\62k\63m\64o\65q\66s\67u8w9y:{;}<\177"+
		"=\u0081>\3\2\b\4\2$$^^\4\2ffhh\6\2&&C\\aac|\5\2\13\f\16\17\"\"\3\2%%\4"+
		"\2\f\f\17\17\u01dc\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2?\3\2\2\2\2A\3"+
		"\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2"+
		"\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2"+
		"[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3"+
		"\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2"+
		"\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2"+
		"\2\2\u0081\3\2\2\2\3\u0084\3\2\2\2\5\u00b5\3\2\2\2\7\u00b7\3\2\2\2\t\u00bd"+
		"\3\2\2\2\13\u00c4\3\2\2\2\r\u00ca\3\2\2\2\17\u00d3\3\2\2\2\21\u00dc\3"+
		"\2\2\2\23\u00de\3\2\2\2\25\u00e3\3\2\2\2\27\u00e8\3\2\2\2\31\u00eb\3\2"+
		"\2\2\33\u00f0\3\2\2\2\35\u00f5\3\2\2\2\37\u00fb\3\2\2\2!\u0102\3\2\2\2"+
		"#\u0108\3\2\2\2%\u0111\3\2\2\2\'\u0117\3\2\2\2)\u011c\3\2\2\2+\u0121\3"+
		"\2\2\2-\u0127\3\2\2\2/\u012f\3\2\2\2\61\u0136\3\2\2\2\63\u013a\3\2\2\2"+
		"\65\u0140\3\2\2\2\67\u0149\3\2\2\29\u0153\3\2\2\2;\u0157\3\2\2\2=\u0159"+
		"\3\2\2\2?\u015b\3\2\2\2A\u015e\3\2\2\2C\u016c\3\2\2\2E\u0176\3\2\2\2G"+
		"\u0186\3\2\2\2I\u0188\3\2\2\2K\u018a\3\2\2\2M\u018c\3\2\2\2O\u018e\3\2"+
		"\2\2Q\u0190\3\2\2\2S\u0192\3\2\2\2U\u0194\3\2\2\2W\u0196\3\2\2\2Y\u0198"+
		"\3\2\2\2[\u019a\3\2\2\2]\u019c\3\2\2\2_\u019e\3\2\2\2a\u01a0\3\2\2\2c"+
		"\u01a2\3\2\2\2e\u01a4\3\2\2\2g\u01a7\3\2\2\2i\u01a9\3\2\2\2k\u01ab\3\2"+
		"\2\2m\u01ad\3\2\2\2o\u01af\3\2\2\2q\u01b1\3\2\2\2s\u01b4\3\2\2\2u\u01b7"+
		"\3\2\2\2w\u01ba\3\2\2\2y\u01bd\3\2\2\2{\u01bf\3\2\2\2}\u01c1\3\2\2\2\177"+
		"\u01c4\3\2\2\2\u0081\u01c7\3\2\2\2\u0083\u0085\4\62;\2\u0084\u0083\3\2"+
		"\2\2\u0085\u0086\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"\4\3\2\2\2\u0088\u008a\4\62;\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2"+
		"\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u0091"+
		"\7\60\2\2\u008e\u0090\4\62;\2\u008f\u008e\3\2\2\2\u0090\u0093\3\2\2\2"+
		"\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091"+
		"\3\2\2\2\u0094\u0096\5\17\b\2\u0095\u0094\3\2\2\2\u0095\u0096\3\2\2\2"+
		"\u0096\u0098\3\2\2\2\u0097\u0099\5\21\t\2\u0098\u0097\3\2\2\2\u0098\u0099"+
		"\3\2\2\2\u0099\u00b6\3\2\2\2\u009a\u009c\7\60\2\2\u009b\u009d\4\62;\2"+
		"\u009c\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f"+
		"\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u00a2\5\17\b\2\u00a1\u00a0\3\2\2\2"+
		"\u00a1\u00a2\3\2\2\2\u00a2\u00a4\3\2\2\2\u00a3\u00a5\5\21\t\2\u00a4\u00a3"+
		"\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00b6\3\2\2\2\u00a6\u00a8\4\62;\2\u00a7"+
		"\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2"+
		"\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\5\17\b\2\u00ac\u00ae\5\21\t\2\u00ad"+
		"\u00ac\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b6\3\2\2\2\u00af\u00b1\4\62"+
		";\2\u00b0\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b6\5\21\t\2\u00b5\u0089\3"+
		"\2\2\2\u00b5\u009a\3\2\2\2\u00b5\u00a7\3\2\2\2\u00b5\u00b0\3\2\2\2\u00b6"+
		"\6\3\2\2\2\u00b7\u00b8\7t\2\2\u00b8\u00b9\7c\2\2\u00b9\u00ba\7p\2\2\u00ba"+
		"\u00bb\7i\2\2\u00bb\u00bc\7g\2\2\u00bc\b\3\2\2\2\u00bd\u00be\7w\2\2\u00be"+
		"\u00bf\7p\2\2\u00bf\u00c0\7t\2\2\u00c0\u00c1\7q\2\2\u00c1\u00c2\7n\2\2"+
		"\u00c2\u00c3\7n\2\2\u00c3\n\3\2\2\2\u00c4\u00c5\7e\2\2\u00c5\u00c6\7q"+
		"\2\2\u00c6\u00c7\7w\2\2\u00c7\u00c8\7p\2\2\u00c8\u00c9\7v\2\2\u00c9\f"+
		"\3\2\2\2\u00ca\u00ce\7$\2\2\u00cb\u00cd\n\2\2\2\u00cc\u00cb\3\2\2\2\u00cd"+
		"\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d1\3\2"+
		"\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00d2\7$\2\2\u00d2\16\3\2\2\2\u00d3\u00d5"+
		"\7g\2\2\u00d4\u00d6\5M\'\2\u00d5\u00d4\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6"+
		"\u00d8\3\2\2\2\u00d7\u00d9\4\62;\2\u00d8\u00d7\3\2\2\2\u00d9\u00da\3\2"+
		"\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db\20\3\2\2\2\u00dc\u00dd"+
		"\t\3\2\2\u00dd\22\3\2\2\2\u00de\u00df\7Q\2\2\u00df\u00e0\7R\2\2\u00e0"+
		"\u00e1\7P\2\2\u00e1\u00e2\7U\2\2\u00e2\24\3\2\2\2\u00e3\u00e4\7K\2\2\u00e4"+
		"\u00e5\7R\2\2\u00e5\u00e6\7P\2\2\u00e6\u00e7\7U\2\2\u00e7\26\3\2\2\2\u00e8"+
		"\u00e9\7k\2\2\u00e9\u00ea\7h\2\2\u00ea\30\3\2\2\2\u00eb\u00ec\7g\2\2\u00ec"+
		"\u00ed\7n\2\2\u00ed\u00ee\7u\2\2\u00ee\u00ef\7g\2\2\u00ef\32\3\2\2\2\u00f0"+
		"\u00f1\7n\2\2\u00f1\u00f2\7q\2\2\u00f2\u00f3\7q\2\2\u00f3\u00f4\7r\2\2"+
		"\u00f4\34\3\2\2\2\u00f5\u00f6\7d\2\2\u00f6\u00f7\7t\2\2\u00f7\u00f8\7"+
		"g\2\2\u00f8\u00f9\7c\2\2\u00f9\u00fa\7m\2\2\u00fa\36\3\2\2\2\u00fb\u00fc"+
		"\7U\2\2\u00fc\u00fd\7n\2\2\u00fd\u00fe\7k\2\2\u00fe\u00ff\7f\2\2\u00ff"+
		"\u0100\7g\2\2\u0100\u0101\7t\2\2\u0101 \3\2\2\2\u0102\u0103\7E\2\2\u0103"+
		"\u0104\7q\2\2\u0104\u0105\7n\2\2\u0105\u0106\7q\2\2\u0106\u0107\7t\2\2"+
		"\u0107\"\3\2\2\2\u0108\u0109\7a\2\2\u0109\u010a\7D\2\2\u010a\u010b\7I"+
		"\2\2\u010b\u010c\7E\2\2\u010c\u010d\7q\2\2\u010d\u010e\7n\2\2\u010e\u010f"+
		"\7q\2\2\u010f\u0110\7t\2\2\u0110$\3\2\2\2\u0111\u0112\7D\2\2\u0112\u0113"+
		"\7n\2\2\u0113\u0114\7c\2\2\u0114\u0115\7e\2\2\u0115\u0116\7m\2\2\u0116"+
		"&\3\2\2\2\u0117\u0118\7D\2\2\u0118\u0119\7n\2\2\u0119\u011a\7w\2\2\u011a"+
		"\u011b\7g\2\2\u011b(\3\2\2\2\u011c\u011d\7E\2\2\u011d\u011e\7{\2\2\u011e"+
		"\u011f\7c\2\2\u011f\u0120\7p\2\2\u0120*\3\2\2\2\u0121\u0122\7I\2\2\u0122"+
		"\u0123\7t\2\2\u0123\u0124\7g\2\2\u0124\u0125\7g\2\2\u0125\u0126\7p\2\2"+
		"\u0126,\3\2\2\2\u0127\u0128\7O\2\2\u0128\u0129\7c\2\2\u0129\u012a\7i\2"+
		"\2\u012a\u012b\7g\2\2\u012b\u012c\7p\2\2\u012c\u012d\7v\2\2\u012d\u012e"+
		"\7c\2\2\u012e.\3\2\2\2\u012f\u0130\7Q\2\2\u0130\u0131\7t\2\2\u0131\u0132"+
		"\7c\2\2\u0132\u0133\7p\2\2\u0133\u0134\7i\2\2\u0134\u0135\7g\2\2\u0135"+
		"\60\3\2\2\2\u0136\u0137\7T\2\2\u0137\u0138\7g\2\2\u0138\u0139\7f\2\2\u0139"+
		"\62\3\2\2\2\u013a\u013b\7Y\2\2\u013b\u013c\7j\2\2\u013c\u013d\7k\2\2\u013d"+
		"\u013e\7v\2\2\u013e\u013f\7g\2\2\u013f\64\3\2\2\2\u0140\u0141\7[\2\2\u0141"+
		"\u0142\7g\2\2\u0142\u0143\7n\2\2\u0143\u0144\7n\2\2\u0144\u0145\7q\2\2"+
		"\u0145\u0146\7y\2\2\u0146\66\3\2\2\2\u0147\u0148\7<\2\2\u0148\u014a\7"+
		"<\2\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014b\3\2\2\2\u014b"+
		"\u0150\5;\36\2\u014c\u014f\5;\36\2\u014d\u014f\5=\37\2\u014e\u014c\3\2"+
		"\2\2\u014e\u014d\3\2\2\2\u014f\u0152\3\2\2\2\u0150\u014e\3\2\2\2\u0150"+
		"\u0151\3\2\2\2\u01518\3\2\2\2\u0152\u0150\3\2\2\2\u0153\u0154\7a\2\2\u0154"+
		"\u0155\7R\2\2\u0155\u0156\7*\2\2\u0156:\3\2\2\2\u0157\u0158\t\4\2\2\u0158"+
		"<\3\2\2\2\u0159\u015a\4\62;\2\u015a>\3\2\2\2\u015b\u015c\t\5\2\2\u015c"+
		"\u015d\b \2\2\u015d@\3\2\2\2\u015e\u015f\7\61\2\2\u015f\u0160\7,\2\2\u0160"+
		"\u0164\3\2\2\2\u0161\u0163\13\2\2\2\u0162\u0161\3\2\2\2\u0163\u0166\3"+
		"\2\2\2\u0164\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0167\3\2\2\2\u0166"+
		"\u0164\3\2\2\2\u0167\u0168\7,\2\2\u0168\u0169\7\61\2\2\u0169\u016a\3\2"+
		"\2\2\u016a\u016b\b!\3\2\u016bB\3\2\2\2\u016c\u016d\7\61\2\2\u016d\u016e"+
		"\7\61\2\2\u016e\u016f\7%\2\2\u016f\u0170\7r\2\2\u0170\u0171\7t\2\2\u0171"+
		"\u0172\7c\2\2\u0172\u0173\7i\2\2\u0173\u0174\7o\2\2\u0174\u0175\7c\2\2"+
		"\u0175D\3\2\2\2\u0176\u0177\7\61\2\2\u0177\u0178\7\61\2\2\u0178\u0179"+
		"\3\2\2\2\u0179\u017d\n\6\2\2\u017a\u017c\n\7\2\2\u017b\u017a\3\2\2\2\u017c"+
		"\u017f\3\2\2\2\u017d\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017e\u0181\3\2"+
		"\2\2\u017f\u017d\3\2\2\2\u0180\u0182\7\17\2\2\u0181\u0180\3\2\2\2\u0181"+
		"\u0182\3\2\2\2\u0182\u0183\3\2\2\2\u0183\u0184\7\f\2\2\u0184\u0185\b#"+
		"\4\2\u0185F\3\2\2\2\u0186\u0187\7?\2\2\u0187H\3\2\2\2\u0188\u0189\7.\2"+
		"\2\u0189J\3\2\2\2\u018a\u018b\7-\2\2\u018bL\3\2\2\2\u018c\u018d\7/\2\2"+
		"\u018dN\3\2\2\2\u018e\u018f\7,\2\2\u018fP\3\2\2\2\u0190\u0191\7\61\2\2"+
		"\u0191R\3\2\2\2\u0192\u0193\7\'\2\2\u0193T\3\2\2\2\u0194\u0195\7]\2\2"+
		"\u0195V\3\2\2\2\u0196\u0197\7_\2\2\u0197X\3\2\2\2\u0198\u0199\7*\2\2\u0199"+
		"Z\3\2\2\2\u019a\u019b\7+\2\2\u019b\\\3\2\2\2\u019c\u019d\7}\2\2\u019d"+
		"^\3\2\2\2\u019e\u019f\7\177\2\2\u019f`\3\2\2\2\u01a0\u01a1\7\u0080\2\2"+
		"\u01a1b\3\2\2\2\u01a2\u01a3\7#\2\2\u01a3d\3\2\2\2\u01a4\u01a5\7#\2\2\u01a5"+
		"\u01a6\7#\2\2\u01a6f\3\2\2\2\u01a7\u01a8\7=\2\2\u01a8h\3\2\2\2\u01a9\u01aa"+
		"\7`\2\2\u01aaj\3\2\2\2\u01ab\u01ac\7\60\2\2\u01acl\3\2\2\2\u01ad\u01ae"+
		"\7A\2\2\u01aen\3\2\2\2\u01af\u01b0\7<\2\2\u01b0p\3\2\2\2\u01b1\u01b2\7"+
		"~\2\2\u01b2\u01b3\7~\2\2\u01b3r\3\2\2\2\u01b4\u01b5\7(\2\2\u01b5\u01b6"+
		"\7(\2\2\u01b6t\3\2\2\2\u01b7\u01b8\7?\2\2\u01b8\u01b9\7?\2\2\u01b9v\3"+
		"\2\2\2\u01ba\u01bb\7#\2\2\u01bb\u01bc\7?\2\2\u01bcx\3\2\2\2\u01bd\u01be"+
		"\7>\2\2\u01bez\3\2\2\2\u01bf\u01c0\7@\2\2\u01c0|\3\2\2\2\u01c1\u01c2\7"+
		">\2\2\u01c2\u01c3\7?\2\2\u01c3~\3\2\2\2\u01c4\u01c5\7@\2\2\u01c5\u01c6"+
		"\7?\2\2\u01c6\u0080\3\2\2\2\u01c7\u01c8\7/\2\2\u01c8\u01c9\7@\2\2\u01c9"+
		"\u0082\3\2\2\2\30\2\u0086\u008b\u0091\u0095\u0098\u009e\u00a1\u00a4\u00a9"+
		"\u00ad\u00b2\u00b5\u00ce\u00d5\u00da\u0149\u014e\u0150\u0164\u017d\u0181"+
		"\5\3 \2\3!\3\3#\4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}