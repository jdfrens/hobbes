package org.norecess.hobbes.compiler;

/**
 * @see Code
 */
public interface ICode<T> extends Iterable<T> {

    ICode<T> add(T command);

}
