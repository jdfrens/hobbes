package org.norecess.hobbes.frontend;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;

public interface IHobbesFrontEnd {

	public ExpressionTIR process() throws RecognitionException, IOException;

}
