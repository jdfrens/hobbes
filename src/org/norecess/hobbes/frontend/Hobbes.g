grammar Hobbes;

options {
  language=Java;
  output=AST;
  ASTLabelType=CommonTree;
}
tokens {
  ARGV;
  DECLS;
  IF;
  LET;
}

@header {
  package org.norecess.hobbes.frontend;
}
@lexer::header {
  package org.norecess.hobbes.frontend;
}

program
	:	expression EOF
		-> ^(expression)
	;

expression
  : logic_expression
  ;
  
logic_expression
  : comparitive_expression (logic_op^ comparitive_expression)*
  ;
  
comparitive_expression
  : additive_expression (comparitive_op^ additive_expression)*
  ;
  
additive_expression
	:	multiplicative_expression (additive_op^ multiplicative_expression)*
	;
	
multiplicative_expression
  : simple_expression (multiplicative_op^ simple_expression)*
  ;
  
logic_op
  : AND | OR
  ;
comparitive_op
  : LT | LTE | EQ | NEQ | GT | GTE
  ;
additive_op
  : PLUS | MINUS
  ;
multiplicative_op
  : MULTIPLY | DIVIDE | MODULUS
  ;
	
simple_expression
  : atom
  | '('! expression ')'!
  | MINUS atom
    -> ^(MINUS atom)
  | 'ARGV[' expression ']'
    -> ^(ARGV expression)
  | 'if' expression 'then' expression 'else' expression 'end'
    -> ^(IF expression*)
  | 'let' decls 'in' expression 'end'
    -> ^(LET decls expression)
  ;
  
atom
  : INTEGER | FLOAT | BOOLEAN | IDENTIFIER
  ;
  
decls
  : ('var' IDENTIFIER ':=' expression)*
    -> ^(DECLS (IDENTIFIER expression)*)
  ;
  
INTEGER
  : ('0'..'9')+
	;
	
FLOAT
  : DIGITS+ '.' DIGITS+
  ;
	
BOOLEAN
  : '#f' | '#t'
  ;
  
IDENTIFIER
  : ('a'..'z') ('a'..'z' | '0'..'9')*
  ;

PLUS  : '+' ;
MINUS : '-' ;
MULTIPLY : '*' ;
DIVIDE : '/' ;
MODULUS : '%' ;

LT : '<' ;
LTE : '<=' ;
EQ : '==' ;
NEQ : '!=' ;
GTE : '>=' ;
GT : '>' ;

AND : '&' ;
OR : '|' ;

WS
  :	(' ' | '\t' | '\n')+
		{ skip(); }
	;

fragment
DIGITS : ('0'..'9') ;