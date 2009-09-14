package org.norecess.hobbes.frontend;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.TreeParser;
import org.norecess.antlr.IANTLRFrontEnd;

public class HobbesFrontEndFactory implements IANTLRFrontEnd {

	public Lexer createLexer(String string) {
		return new HobbesLexer(new ANTLRStringStream(string));
	}

	public Parser createParser(TokenStream stream) {
		return new HobbesParser(stream);
	}

	public TreeParser createTreeParser(Tree tree) {
		return new HobbesTIRBuilder(new CommonTreeNodeStream(tree));
	}

}
