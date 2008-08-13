package org.norecess.hobbes.compiler;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;

public class HobbesPIRCompilerTest {

    private IMocksControl     myControl;
    private HobbesPIRCompiler myCompiler;

    @Before
    public void setUp() {
        myControl = EasyMock.createControl();
        myCompiler = new HobbesPIRCompiler();
    }

    @Test
    public void shouldCompile() throws IOException {
        IHobbesFrontEnd frontEnd = myControl.createMock(IHobbesFrontEnd.class);
        expect(frontEnd.process()).andReturn(8);

        myControl.replay();
        assertEquals(//
                new Code<String>().add(".sub main") //
                        .add("print 8") //
                        .add("print \"\\n\"") //
                        .add(".end"),//
                myCompiler.compile(frontEnd, new Code<String>()));
        myControl.verify();
    }

}
