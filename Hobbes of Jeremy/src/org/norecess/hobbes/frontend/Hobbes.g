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
	:	INTEGER
	|	INTEGER PLUS INTEGER
		-> ^(PLUS INTEGER+)
	| 'ARGV[' INTEGER ']'
	  -> ^(ARGV INTEGER)
	;

	
INTEGER
  :	'-'? ('0'..'9')+
	;

PLUS  : '+' ;

WS
  :	(' ' | '\t' | '\n')+
		{ skip(); }
	;
