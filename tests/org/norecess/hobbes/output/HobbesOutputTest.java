package org.norecess.hobbes.output;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.StringETIR;
import org.norecess.hobbes.HobbesBoolean;

public class HobbesOutputTest {

	private IHobbesOutput	myHobbesOutput;

	@Before
	public void setUp() {
		myHobbesOutput = new HobbesOutput();
	}

	@Test
	public void shouldOutputTrue() {
		assertEquals("#t", myHobbesOutput.asHobbesOutput(HobbesBoolean.TRUE));
	}

	@Test
	public void shouldOutputFalse() {
		assertEquals("#f", myHobbesOutput.asHobbesOutput(HobbesBoolean.FALSE));
	}

	@Test
	public void shouldOutputToString() {
		assertEquals("123", myHobbesOutput.asHobbesOutput(new IntegerETIR(123)));
		assertEquals("abc", myHobbesOutput
				.asHobbesOutput(new StringETIR("abc")));
	}
}
