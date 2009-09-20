package org.norecess.hobbes.backend;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PIRCleanerTest {

	private PIRCleaner	myCleaner;

	@Before
	public void setup() {
		myCleaner = new PIRCleaner();
	}

	@Test
	public void shouldAddTab() {
		assertEquals("\t$I0 += $I1", myCleaner.process("$I0 += $I1"));
		assertEquals("\t.param pmc argv", myCleaner.process(".param pmc argv"));
		assertEquals("\tfoobar", myCleaner.process("foobar"));
	}

	@Test
	public void shouldNotAddTab() {
		assertEquals(".sub something", myCleaner.process(".sub something"));
		assertEquals(".end", myCleaner.process(".end"));
	}

	@Test
	public void shouldNotAddTabToLabelDeclarations() {
		assertEquals("label:", myCleaner.process("label:"));
		assertEquals("foo:", myCleaner.process("foo:"));
	}

	@Test
	public void shouldProcessCode() {
		assertEquals(new Code(".sub me", "\tprint 5", ".end"), myCleaner
				.process(new Code(".sub me", "print 5", ".end")));
	}

	@Test
	public void shouldRemoveDuplicateParamDirectives() {
		assertEquals(new Code("\t.param pmc argv"), myCleaner.process(new Code(
				".param pmc argv")));
		assertEquals(new Code("\t.param pmc argv"), myCleaner.process(new Code(
				".param pmc argv", ".param pmc argv")));
	}
}
