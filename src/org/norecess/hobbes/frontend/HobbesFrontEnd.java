package org.norecess.hobbes.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.norecess.citkit.tir.ExpressionTIR;

/*
 * Provides an front end for the front-end testers and compilers.
 */
public class HobbesFrontEnd implements IHobbesFrontEnd {

	private final ANTLRStringStream	myInputStream;

	public HobbesFrontEnd(ANTLRStringStream inputStream) {
		myInputStream = inputStream;
	}

	public HobbesFrontEnd(File file) throws IOException {
		myInputStream = new ANTLRInputStream(new FileInputStream(file));
	}

	public ExpressionTIR process() throws RecognitionException {
		Tree tree = new HobbesParser(new CommonTokenStream(new HobbesLexer(
				myInputStream))).program().tree;
		return new HobbesTIRBuilder(new CommonTreeNodeStream(tree)).program();
	}
}
