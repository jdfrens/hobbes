tree grammar HobbesTIRBuilder;

options { 
  tokenVocab=Hobbes; 
  ASTLabelType=CommonTree;
}

@header {
  package org.norecess.hobbes.frontend;
  import org.norecess.citkit.tir.*;
  import org.norecess.citkit.tir.expressions.*;
  import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
  import org.norecess.citkit.tir.lvalues.*;
}

program returns [ExpressionTIR tir]
  : t=expression EOF
    { tir = t; }
  ;

expression returns [ExpressionTIR tir]
  : i=INTEGER
    { tir = new IntegerETIR(i.getText()); }
  | b=BOOLEAN
    { tir = new IntegerETIR("#t".equals(b.getText()) ? 1 : 0); }
  | ^(ARGV e=expression)
    { tir = new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR("ARGV"), e)); }
  | ^(MINUS i=INTEGER)
    { tir = new IntegerETIR("-" + i.getText()); }
  | ^(op=operator left=expression right=expression)
    { tir = new OperatorETIR(left, op, right); }
  | ^(IF test=expression consequence=expression otherwise=expression)
    { tir = new IfETIR(test, consequence, otherwise); }
  ;

operator returns [Operator o]
  : op=( PLUS | MINUS | MULTIPLY | DIVIDE | MODULUS )
    { o = Operator.convertPunctuation(op.getText()); }
  ;
