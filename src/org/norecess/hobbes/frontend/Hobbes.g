grammar Hobbes;

options {
  language=Java;
  output=AST;
  ASTLabelType=CommonTree;
}
tokens {
  ARGV;
  IF;
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
  | 'if' expression 'then' expression 'else' expression 'end'
    -> ^(IF expression*)
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
  : INTEGER | BOOLEAN
  | '('! expression ')'!
  | MINUS INTEGER
    -> ^(MINUS INTEGER)
  | 'ARGV[' INTEGER ']'
    -> ^(ARGV INTEGER)
  ;
  
INTEGER
  : ('0'..'9')+
	;
	
BOOLEAN
  : '#f' | '#t'
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
