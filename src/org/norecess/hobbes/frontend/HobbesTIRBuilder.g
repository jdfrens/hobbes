tree grammar HobbesTIRBuilder;

options { 
  tokenVocab=Hobbes; 
  ASTLabelType=CommonTree;
}

@header {
  package org.norecess.hobbes.frontend;

  import org.norecess.hobbes.*;
  import org.norecess.citkit.ISymbol;
  import org.norecess.citkit.Symbol;
  import org.norecess.citkit.tir.*;
  import org.norecess.citkit.tir.expressions.*;
  import org.norecess.citkit.tir.declarations.*;
  import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
  import org.norecess.citkit.tir.lvalues.*;
  import java.util.Arrays;
}

program returns [ExpressionTIR tir]
  : t=expression EOF
    { tir = t; }
  ;

expression returns [ExpressionTIR tir]
  : i=INTEGER
    { tir = new IntegerETIR(new Position(i.getLine()), i.getText()); }
  | f=FLOAT
    { tir = new FloatingPointETIR(new Position(f.getLine()), f.getText()); }
  | b=BOOLEAN
    { tir = new BooleanETIR(new Position(b.getLine()), b.getText()); }
  | s=IDENTIFIER
    { tir = new VariableETIR(new Position(s.getLine()), new SimpleLValueTIR(Symbol.createSymbol(s.getText()))); }
  | ^(ARGV e=expression)
    { tir = new VariableETIR(e.getPosition(), new SubscriptLValueTIR(e.getPosition(), new SimpleLValueTIR(Symbol.createSymbol("ARGV")), e)); }
  | ^(MINUS i=INTEGER)
    { tir = new IntegerETIR("-" + i.getText()); }
  | ^(MINUS f=FLOAT)
    { tir = new FloatingPointETIR(new Position(f.getLine()), "-" + f.getText()); }
  | ^(op=operator left=expression right=expression)
    { tir = new OperatorETIR(new Position(op.line), left, op.operator, right); }
  | ^(IF test=expression consequence=expression otherwise=expression)
    { tir = new IfETIR(test.getPosition(), test, consequence, otherwise); }
  | ^(LET decls=declarations body=expression)
    { tir = new LetETIR(decls, body); }
  ;
  
symbol returns [ISymbol symbol]
  : i=IDENTIFIER
    { symbol = Symbol.createSymbol(i.getText()); }
  ;
  
declarations returns [List<DeclarationTIR> decls = new ArrayList<DeclarationTIR>()]
  : ^(DECLS (
        s=symbol init=expression
        { decls.add(new VariableDTIR(s, init)); }
     )*
    )
  ;

operator returns [Operator operator, int line]
  : op=( PLUS | MINUS | MULTIPLY | DIVIDE | MODULUS | LT | LTE | GTE | GT )
    { 
      $line = op.getLine(); 
      $operator = Operator.convertPunctuation(op.getText());
    }
  | op=EQ
    { 
      $line = op.getLine();  
      $operator = Operator.EQUALS; 
    }
  | op=NEQ
    { $line = op.getLine();  $operator = Operator.NOT_EQUALS; }
  ;
