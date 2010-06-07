package org.norecess.hobbes.compiler.body.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IntegerRegister;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;

public class LogicOperatorTest {

	private LogicOperator	myFooOperator;
	private LogicOperator	myBazOperator;

	@Before
	public void setUp() {
		myFooOperator = new LogicOperator("foo");
		myBazOperator = new LogicOperator("baz");
	}

	@Test
	public void shouldCompileComparisonInstructions2() {
		assertEquals(new Code("$I2 = foo $I55, $I3"), myFooOperator.compile(
				new ResourceAllocator(), new IntegerRegister(2),
				new IRegister[] { new IntegerRegister(55),
						new IntegerRegister(3) }));
		assertEquals(new Code("$I2 = baz $I55, $I3"), myBazOperator.compile(
				new ResourceAllocator(), new IntegerRegister(2),
				new IRegister[] { new IntegerRegister(55),
						new IntegerRegister(3) }));
	}

}
