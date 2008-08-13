package org.norecess.hobbes.compiler;

public interface ICode<T> extends Iterable<T> {

    ICode<T> add(T command);

}
