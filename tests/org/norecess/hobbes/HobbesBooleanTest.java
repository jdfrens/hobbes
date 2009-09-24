package org.norecess.hobbes;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class HobbesBooleanTest {

	@Test
	public void shouldConvertBooleans() {
		assertSame(HobbesBoolean.TRUE, HobbesBoolean.convert(true));
		assertSame(HobbesBoolean.FALSE, HobbesBoolean.convert(false));
	}

	@Test
	public void shouldParseString() {
		assertSame(HobbesBoolean.TRUE, HobbesBoolean.parse("#t"));
		assertSame(HobbesBoolean.FALSE, HobbesBoolean.parse("#f"));
	}

}
