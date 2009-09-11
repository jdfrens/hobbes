package org.norecess.hobbes.compiler;

import org.antlr.runtime.tree.Tree;
import org.norecess.hobbes.frontend.HobbesParser;

/*
 * A "component compiler" is a compiler that compiles only components of a program.
 * Like a single integer.
 */
public class HobbesPIRComponentCompiler implements IHobbesPIRComponentCompiler {

	private final IRegisterAllocator	myRegisterAllocator;
	private String						myTarget;

	public HobbesPIRComponentCompiler(IRegisterAllocator registerAllocator) {
		myRegisterAllocator = registerAllocator;
	}

	public ICode generateProlog(ICode code, Tree ast) {
		code.add(".sub main");
		generateParameterProlog(code, ast);
		return code;
	}

	private void generateParameterProlog(ICode code, Tree ast) {
		switch (ast.getType()) {
		case HobbesParser.INTEGER:
			break;
		case HobbesParser.PLUS:
		case HobbesParser.MULTIPLY:
			generateParameterProlog(code, ast.getChild(0));
			generateParameterProlog(code, ast.getChild(1));
			break;
		case HobbesParser.ARGV:
			code.add(".param pmc argv");
			break;
		default:
			throw new RuntimeException("Unrecognized AST: " + ast);
		}
	}

	public ICode generateEpilog(ICode code) {
		code.add("print " + myTarget);
		code.add("print \"\\n\"");
		code.add(".end");
		return code;
	}

	public ICode generateCode(ICode code, Tree ast) {
		myTarget = myRegisterAllocator.next();
		return generateCode(code, myTarget, ast);
	}

	private ICode generateCode(ICode code, String target, Tree ast) {
		switch (ast.getType()) {
		case HobbesParser.INTEGER:
			code.add(target + " = " + ast);
			break;
		case HobbesParser.PLUS:
		case HobbesParser.MULTIPLY:
			generateCode(code, target, ast.getChild(0));
			String temp = myRegisterAllocator.next();
			generateCode(code, temp, ast.getChild(1));
			code.add(target + " " + ast.getText() + "= " + temp);
			break;
		case HobbesParser.ARGV:
			code.add(target + " = argv[" + ast.getChild(0) + "]");
			break;
		default:
			throw new IllegalArgumentException("invalid ast: " + ast);
		}
		return code;
	}
}
