tree grammar GappTransformer;

options {
	ASTLabelType = CommonTree;
	tokenVocab = GappParser;
}

@header {
	package de.gaalop.gappDebugger;

	import java.util.Collections;	
	import java.util.LinkedList;
	import de.gaalop.gapp.*;
	import de.gaalop.gapp.instructionSet.*;
	import de.gaalop.gapp.variables.*;
	import de.gaalop.gapp.visitor.*;
}

@members {
	private GAPPBuilder gappBuilder;

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
}

script	returns [GAPPBuilder result]
	@init {
		gappBuilder = new GAPPBuilder();
		result = gappBuilder;
	}
 	: statement*;

statement
	: gappcommandRes=gappcommand SEMICOLON { gappBuilder.add($gappcommandRes.instruction); }
	;

float_literal returns [float value]
  : f1=FLOATING_POINT_LITERAL { $value=Float.parseFloat($f1.text); }
  | MINUS f2=FLOATING_POINT_LITERAL { $value=-Float.parseFloat($f2.text); }
  ;

calcOperationType returns [CalculationType type]
  : CALC_OP_DIVISION_LITERAL { $type = CalculationType.DIVISION; }
  | CALC_OP_EXPONENTIATION_LITERAL { $type = CalculationType.EXPONENTIATION; }
  | CALC_OP_INVERT_LITERAL { $type = CalculationType.INVERT; }
  | CALC_OP_ABS_LITERAL { $type = CalculationType.ABS; }
  | CALC_OP_ACOS_LITERAL { $type = CalculationType.ACOS; }
  | CALC_OP_ASIN_LITERAL { $type = CalculationType.ASIN; }
  | CALC_OP_ATAN_LITERAL { $type = CalculationType.ATAN; }
  | CALC_OP_CEIL_LITERAL { $type = CalculationType.CEIL; }
  | CALC_OP_COS_LITERAL { $type = CalculationType.COS; }
  | CALC_OP_EXP_LITERAL { $type = CalculationType.EXP; }
  | CALC_OP_FACT_LITERAL { $type = CalculationType.FACT; }
  | CALC_OP_FLOOR_LITERAL { $type = CalculationType.FLOOR; }
  | CALC_OP_LOG_LITERAL { $type = CalculationType.LOG; }
  | CALC_OP_SIN_LITERAL { $type = CalculationType.SIN; }
  | CALC_OP_SQRT_LITERAL { $type = CalculationType.SQRT; }
  | CALC_OP_TAN_LITERAL { $type = CalculationType.TAN; }
  ;

multivector returns [GAPPMultivector multivector]
  : t=IDENTIFIER { $multivector=new GAPPMultivector($t.text); }
  ;

vector returns [GAPPVector vector]
  : t=IDENTIFIER { $vector=new GAPPVector($t.text); }
  ;

variableset returns [Variableset variableset]
  @init { $variableset = new Variableset(); }
  : LSBRACKET m1=multivector { $variableset.add(new GAPPScalarVariable($m1.multivector.getName())); }
        (COMMA m2=multivector { $variableset.add(new GAPPScalarVariable($m2.multivector.getName())); })* RSBRACKET
  ;

valueset returns [Valueset valueset]
  @init { $valueset = new Valueset(); }
  : LSBRACKET m1=float_literal { $valueset.add(new GAPPConstant($m1.value)); }
        (COMMA m2=float_literal { $valueset.add(new GAPPConstant($m2.value)); })* RSBRACKET
  ;

posSelector returns [PosSelector selector]
  : sel=DECIMAL_LITERAL { $selector = new PosSelector(Integer.parseInt($sel.text), null); }
  ;

posSelectors returns [PosSelectorset selectors]
    @init { $selectors = new PosSelectorset(); }
  : LSBRACKET p1=posSelector { $selectors.add($p1.selector); } (COMMA pn=posSelector { $selectors.add($pn.selector); } )* RSBRACKET
  ;

setOfVariables returns [GAPPMultivector var]
  : t=IDENTIFIER { $var=new GAPPMultivector($t.text); }
  ;

selector returns [Selector selector]
  : MINUS sel=DECIMAL_LITERAL { $selector = new Selector(Integer.parseInt($sel.text),(byte) -1, null); }
  | sel=DECIMAL_LITERAL { $selector = new Selector(Integer.parseInt($sel.text),(byte) 1, null); }
  ;

selectors returns [Selectorset selectors]
    @init { $selectors = new Selectorset(); }
  : LSBRACKET sel1=selector { $selectors.add($sel1.selector); } (COMMA seln=selector { $selectors.add($seln.selector); })* RSBRACKET
  ;

pairSetOfVariablesAndIndices returns [PairSetOfVariablesAndIndices pair]
  : set=setOfVariables sel=selectors { $pair=new PairSetOfVariablesAndIndices($set.var, $sel.selectors); }
  ;

argument returns [SetVectorArgument arg]
  : t=float_literal { $arg=new ConstantSetVectorArgument($t.value); }
  | pair=pairSetOfVariablesAndIndices { $arg=$pair.pair; }
  ;

listOfArguments returns [LinkedList<SetVectorArgument> entries]
    @init { $entries = new LinkedList<SetVectorArgument>(); }
  : a1=argument { $entries.add($a1.arg); } (COMMA a2=argument { $entries.add($a2.arg); } )*
  ;

dotproduct returns [LinkedList<GAPPVector> parts]
    @init { $parts = new LinkedList<GAPPVector>(); }
  : LESS v1=vector { $parts.add($v1.vector); } (COMMA vn=vector { $parts.add($vn.vector); })* GREATER
  ;

oneOrTwoMultivectors returns [GAPPMultivector m1,GAPPMultivector m2]
    @init { $m2=null; }
  : m1m = multivector { $m1 = $m1m.multivector; } (COMMA m2m=multivector { $m2 = $m2m.multivector; } )?
  ;

gappcommand returns [GAPPBaseInstruction instruction]
  : ASSIGNINPUTSVECTOR_LITERAL IDENTIFIER EQUALS v1=variableset
    { $instruction = new GAPPAssignInputsVector($v1.variableset); }

  | RESETMV_LITERAL v2=multivector LSBRACKET size=DECIMAL_LITERAL RSBRACKET
    { $instruction = new GAPPResetMv($v2.multivector,Integer.parseInt($size.text)); }

  | SETMV_LITERAL dest1=multivector destSel=posSelectors EQUALS source=setOfVariables srcSel=selectors
    { $instruction = new GAPPSetMv($dest1.multivector,$source.var,$destSel.selectors,$srcSel.selectors); }

  | SETVECTOR_LITERAL dest2=vector EQUALS CLBRACKET args1=listOfArguments CRBRACKET
    { $instruction = new GAPPSetVector($dest2.vector,$args1.entries); }

  | DOTVECTORS_LITERAL dest3=multivector LSBRACKET sel1=selector RSBRACKET EQUALS dp=dotproduct
    { $instruction = new GAPPDotVectors($dest3.multivector,$sel1.selector,$dp.parts); }

  | ASSIGNMV_LITERAL dest4=multivector sel2=posSelectors EQUALS valset=valueset
    { $instruction = new GAPPAssignMv($dest4.multivector,$sel2.selectors,$valset.valueset); }

  | CALCULATEMVCOEFF_LITERAL dest5=multivector LSBRACKET destComp=DECIMAL_LITERAL RSBRACKET EQUALS type1=calcOperationType LBRACKET args2=oneOrTwoMultivectors RBRACKET
    { $instruction = new GAPPCalculateMvCoeff($type1.type,new GAPPMultivectorComponent($dest5.multivector.getName(),Integer.parseInt($destComp.text)),$args2.m1,$args2.m2); }

  | CALCULATEMV_LITERAL dest6=multivector EQUALS type2=calcOperationType LBRACKET args3=oneOrTwoMultivectors RBRACKET
    { $instruction = new GAPPCalculateMv($type2.type,$dest6.multivector,$args3.m1,$args3.m2); }
  ;