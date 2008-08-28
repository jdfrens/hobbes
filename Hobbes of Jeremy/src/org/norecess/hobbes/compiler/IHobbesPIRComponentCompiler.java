package org.norecess.hobbes.compiler;

import org.antlr.runtime.tree.Tree;

/**
 * @see HobbesPIRComponentCompiler
 * 
 */
public interface IHobbesPIRComponentCompiler {

    public ICode<String> generateProlog(ICode<String> code);

    public ICode<String> generateEpilog(ICode<String> code);

    public ICode<String> generateCode(ICode<String> code, String target,
            Tree ast);

}
