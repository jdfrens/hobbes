package org.norecess.hobbes.compiler;

import org.antlr.runtime.tree.Tree;
import org.norecess.hobbes.frontend.HobbesParser;

/*
 * A "component compiler" is a compiler that compiles only components of a program.
 * Like a single integer.
 */
public class HobbesPIRComponentCompiler implements IHobbesPIRComponentCompiler {

    public ICode<String> generateProlog(ICode<String> code) {
        code.add(".sub main");
        return code;
    }

    public ICode<String> generateEpilog(ICode<String> code) {
        code.add("print $I0");
        code.add("print \"\\n\"");
        code.add(".end");
        return code;
    }

    public ICode<String> generateCode(ICode<String> code, String target,
            Tree ast) {
        switch (ast.getType()) {
        case HobbesParser.INTEGER:
            code.add(target + " = " + ast);
            break;
        case HobbesParser.PLUS:
            generateCode(code, "$I0", ast.getChild(0));
            generateCode(code, "$I1", ast.getChild(1));
            code.add("$I0 += $I1");
            break;
        default:
            throw new IllegalArgumentException("invalid ast: " + ast);
        }
        return code;
    }

}
