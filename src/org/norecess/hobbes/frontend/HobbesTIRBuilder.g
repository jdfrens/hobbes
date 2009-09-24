tree grammar HobbesTIRBuilder;

options { 
  tokenVocab=Hobbes; 
  ASTLabelType=CommonTree;
}

@header {
  package org.norecess.hobbes.frontend;

  import org.norecess.hobbes.*;
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
    { tir = HobbesBoolean.parse(b.getText()); }
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
  : op=( PLUS | MINUS | MULTIPLY | DIVIDE | MODULUS | LT | LTE | GTE | GT )
    { o = Operator.convertPunctuation(op.getText()); }
  | eq=( EQ )
    { o = Operator.EQUALS; }
  | neq=( NEQ )
    { o = Operator.NOT_EQUALS; }
  ;
