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
	:	simple_expression (op^ simple_expression)*
	;
	
op
  : PLUS | MULTIPLY
  ;
	
simple_expression
  : INTEGER
  | 'ARGV[' INTEGER ']'
    -> ^(ARGV INTEGER)
  ;  

INTEGER
  :	'-'? ('0'..'9')+
	;

PLUS  : '+' ;
MULTIPLY : '*' ;

WS
  :	(' ' | '\t' | '\n')+
		{ skip(); }
	;
