package org.norecess.hobbes.frontend;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

public interface IHobbesFrontEnd {

    public Tree process() throws RecognitionException;

}
