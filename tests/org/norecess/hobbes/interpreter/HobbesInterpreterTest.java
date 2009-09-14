package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;
import static org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest.MINUS_TOKEN;
import static org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest.MULTIPLY_TOKEN;
import static org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest.PLUS_TOKEN;
import static org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest.createArgvTree;
import static org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest.createIntegerTree;
import static org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest.createOpTree;

import org.junit.Before;
import org.junit.Test;

public class HobbesInterpreterTest {

	private String[]			myArgv;

	private HobbesInterpreter	myInterpreter;

	@Before
	public void setUp() {
		myArgv = new String[10];
		myInterpreter = new HobbesInterpreter(myArgv);
	}

	@Test
	public void shouldInterpretInteger() {
		assertEquals("5", myInterpreter.interpret(createIntegerTree(5)));
		assertEquals("555", myInterpreter.interpret(createIntegerTree(555)));
	}

	@Test
	public void shouldInterpretCommandLineArguments() {
		myArgv[8] = "7";
		assertEquals("7", myInterpreter.interpret(createArgvTree(8)));
		myArgv[3] = "abc";
		assertEquals("abc", myInterpreter.interpret(createArgvTree(3)));
	}

	@Test
	public void shouldInterpretUnaryMinus() {
		assertEquals("-10", myInterpreter.interpret(createOpTree(MINUS_TOKEN,
				createIntegerTree(10))));
		assertEquals("-8723", myInterpreter.interpret(createOpTree(MINUS_TOKEN,
				createIntegerTree(8723))));
	}

	@Test
	public void shouldInterpretAddition() {
		assertEquals("10", myInterpreter.interpret(createOpTree(PLUS_TOKEN,
				createIntegerTree(2), createIntegerTree(8))));
		assertEquals("5", myInterpreter.interpret(createOpTree(PLUS_TOKEN,
				createIntegerTree(15), createIntegerTree(-10))));
	}

	@Test
	public void shouldInterpretMultiplication() {
		assertEquals("16", myInterpreter.interpret(createOpTree(MULTIPLY_TOKEN,
				createIntegerTree(2), createIntegerTree(8))));
		assertEquals("-150", myInterpreter.interpret(createOpTree(
				MULTIPLY_TOKEN, createIntegerTree(15), createIntegerTree(-10))));
	}

	@Test
	public void shouldInterpretAdditionAndMultiplication() {
		assertEquals("40", myInterpreter.interpret(createOpTree(MULTIPLY_TOKEN,
				createOpTree(PLUS_TOKEN, createIntegerTree(2),
						createIntegerTree(3)), createIntegerTree(8))));
		assertEquals("240", myInterpreter.interpret(createOpTree(
				MULTIPLY_TOKEN, createOpTree(PLUS_TOKEN, createIntegerTree(15),
						createIntegerTree(-5)), createOpTree(MULTIPLY_TOKEN,
						createIntegerTree(8), createIntegerTree(3)))));
	}

}
