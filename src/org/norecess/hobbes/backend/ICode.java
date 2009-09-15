package org.norecess.hobbes.backend;

/**
 * @see Code
 */
public interface ICode extends Iterable<String> {

	ICode add(String command);

	ICode append(ICode code);

}
