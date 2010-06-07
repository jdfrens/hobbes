package org.norecess.hobbes.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.norecess.citkit.tir.ExpressionTIR;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/*
 * Provides an front end for the front-end testers and compilers.
 */
public class HobbesFrontEnd implements IHobbesFrontEnd {

	private ANTLRStringStream	myInputStream;
	private File				myFile;

	public HobbesFrontEnd(ANTLRStringStream inputStream) {
		myInputStream = inputStream;
	}

	@Inject
	public HobbesFrontEnd(@Named("SourceFile") File file) {
		myFile = file;
	}

	public ExpressionTIR process() throws RecognitionException, IOException {
		return buildTir(parse());
	}

	private ExpressionTIR buildTir(Tree tree) throws RecognitionException {
		HobbesTIRBuilder tirBuilder = new HobbesTIRBuilder(
				new CommonTreeNodeStream(tree));
		ExpressionTIR program = tirBuilder.program();
		reportErrors(tirBuilder, "TIR builder");
		return program;
	}

	private Tree parse() throws IOException, RecognitionException {
		HobbesLexer lexer = new HobbesLexer(getInputStream());
		HobbesParser parser = new HobbesParser(new CommonTokenStream(lexer));
		Tree tree = parser.program().tree;
		reportErrors(lexer, "lexer");
		reportErrors(parser, "parser");
		return tree;
	}

	private void reportErrors(BaseRecognizer recognizer, String where) {
		if (recognizer.getNumberOfSyntaxErrors() > 0) {
			throw new TooManySyntaxErrors(recognizer.getNumberOfSyntaxErrors()
					+ " in the " + where);
		}
	}

	private ANTLRStringStream getInputStream() throws IOException {
		if (myInputStream == null) {
			myInputStream = new ANTLRInputStream(new FileInputStream(myFile));
		}
		return myInputStream;
	}
}
