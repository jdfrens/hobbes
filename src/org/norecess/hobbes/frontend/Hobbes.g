grammar Hobbes;

options {
  language=Java;
  output=AST;
  ASTLabelType=CommonTree;
}
tokens {
  ARGV;
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
  : additive_expression
  ;
  
additive_expression
	:	multiplicative_expression (additive_op^ multiplicative_expression)*
	;
	
multiplicative_expression
  : simple_expression (MULTIPLY^ simple_expression)*
  ;
  
additive_op
  : PLUS | MINUS
  ;
	
simple_expression
  : INTEGER
  | MINUS INTEGER
    -> ^(MINUS INTEGER)
  | 'ARGV[' INTEGER ']'
    -> ^(ARGV INTEGER)
  ;
  
INTEGER
  : ('0'..'9')+
	;

PLUS  : '+' ;
MINUS : '-' ;
MULTIPLY : '*' ;

WS
  :	(' ' | '\t' | '\n')+
		{ skip(); }
	;
