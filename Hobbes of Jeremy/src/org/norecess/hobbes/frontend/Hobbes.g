grammar Hobbes;

options {
  language=Java;
  ASTLabelType=CommonTree;
}

@header {
  package org.norecess.hobbes.frontend;
}
@lexer::header {
  package org.norecess.hobbes.frontend;
}
@members { 
  protected void mismatch(IntStream input, int ttype, BitSet follow) 
    throws RecognitionException { 
    throw new MismatchedTokenException(ttype, input); 
  } 
  public void recoverFromMismatchedSet(
      IntStream input, RecognitionException e, BitSet follow) 
      throws RecognitionException { 
    throw e; 
  } 
} 
@rulecatch { 
  catch (RecognitionException e) { 
    throw e; 
  } 
} 
@lexer::members { 
  protected void mismatch(IntStream input, int ttype, BitSet follow) 
    throws RecognitionException { 
    throw new MismatchedTokenException(ttype, input); 
  } 
  public void recoverFromMismatchedSet(
      IntStream input, RecognitionException e, BitSet follow) 
      throws RecognitionException { 
    throw e; 
  } 
} 
@lexer::rulecatch { 
  catch (RecognitionException e) { 
    throw e; 
  } 
} 

expression
	:	INTEGER EOF
	;
	
INTEGER	:	'-'? ('0'..'9')+
	;
