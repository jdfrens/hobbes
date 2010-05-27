package org.norecess.hobbes.compiler.body.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IntegerRegister;
import org.norecess.hobbes.compiler.resources.NumberRegister;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;

public class ArithmeticOperatorTest {

	private ArithmeticOperator	myApersandOperator;
	private ArithmeticOperator	myDollarOperator;

	@Before
	public void setUp() {
		myApersandOperator = new ArithmeticOperator("&");
		myDollarOperator = new ArithmeticOperator("$");
	}

	@Test
	public void shouldCompileInstructions() {
		assertEquals(new Code("$I5 = $I3 & $I12"), myApersandOperator.compile(
				new ResourceAllocator(), new IntegerRegister(5),
				new IRegister[] { new IntegerRegister(3),
						new IntegerRegister(12) }));
		assertEquals(new Code("$I23 = $N88 $ $N9"),
				myDollarOperator
						.compile(new ResourceAllocator(), new IntegerRegister(
								23), new IRegister[] { new NumberRegister(88),
								new NumberRegister(9) }));
	}

	@Test
	public void shouldCompileInstructionWithReusedTarget() {
		assertEquals(new Code("$I5 &= $I12"), myApersandOperator.compile(
				new ResourceAllocator(), new IntegerRegister(5),
				new IRegister[] { new IntegerRegister(5),
						new IntegerRegister(12) }));
	}

}
