package org.norecess.hobbes.backend;

import org.norecess.hobbes.compiler.resources.ILabel;

/**
 * @see Code
 */
public interface ICode extends Iterable<String> {

	ICode add(Object... values);

	ICode add(ILabel label);

	ICode append(ICode code);

}
