package org.norecess.hobbes.compiler.resources;

public class Label implements ILabel {

	private final int	myI;

	public Label(int i) {
		myI = i;
	}

	@Override
	public String toString() {
		return "label_" + myI;
	}

}
