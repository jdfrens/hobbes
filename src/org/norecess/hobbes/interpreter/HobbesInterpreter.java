package org.norecess.hobbes.interpreter;

import org.antlr.runtime.tree.Tree;

public class HobbesInterpreter {

	public String interpret(Tree ast) {
		return ast.getText();
	}

}
