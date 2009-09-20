package org.norecess.hobbes.backend;

import org.norecess.hobbes.compiler.resources.ILabel;

/**
 * @see Code
 */
public interface ICode extends Iterable<String> {

	ICode add(String command);

	ICode append(ICode code);

	ICode add(ILabel nextLabel);

}
