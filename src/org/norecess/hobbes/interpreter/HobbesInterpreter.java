package org.norecess.hobbes.interpreter;

import org.antlr.runtime.tree.Tree;
import org.norecess.hobbes.frontend.HobbesParser;

public class HobbesInterpreter {

	private final String[]	myArgv;

	public HobbesInterpreter(String[] argv) {
		myArgv = argv;
	}

	public String interpret(Tree ast) {
		switch (ast.getType()) {
		case HobbesParser.INTEGER:
			return ast.getText();
		case HobbesParser.ARGV:
			return myArgv[Integer.parseInt(ast.getChild(0).getText())];
		case HobbesParser.PLUS:
		case HobbesParser.MULTIPLY:
			int left = Integer.parseInt(interpret(ast.getChild(0)));
			int right = Integer.parseInt(interpret(ast.getChild(1)));
			switch (ast.getType()) {
			case HobbesParser.PLUS:
				return String.valueOf(left + right);
			case HobbesParser.MULTIPLY:
				return String.valueOf(left * right);
			}
		default:
			throw new IllegalArgumentException(ast + " cannot be interpreted");
		}
	}

}
