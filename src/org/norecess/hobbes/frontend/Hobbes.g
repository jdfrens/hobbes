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
  : comparitive_expression
  ;
  
decls
  : ('var' IDENTIFIER ':=' expression)*
    -> ^(DECLS (IDENTIFIER expression)*)
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
  : INTEGER | FLOAT | BOOLEAN | IDENTIFIER
  | '('! expression ')'!
  | MINUS INTEGER
    -> ^(MINUS INTEGER)
  | 'ARGV[' INTEGER ']'
    -> ^(ARGV INTEGER)
  | 'if' expression 'then' expression 'else' expression 'end'
    -> ^(IF expression*)
  | 'let' decls 'in' expression 'end'
    -> ^(LET decls expression)
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

WS
  :	(' ' | '\t' | '\n')+
		{ skip(); }
	;

fragment
DIGITS : ('0'..'9') ;