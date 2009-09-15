package org.norecess.hobbes.compiler;

/**
 * @see Code
 */
public interface ICode extends Iterable<String> {

	ICode add(String command);

	ICode append(ICode code);

}
