package org.norecess.hobbes.compiler;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;

public class HobbesCompilerTest {

    private IMocksControl  myControl;
    private HobbesCompiler myCompiler;

    @Before
    public void setUp() {
        myControl = EasyMock.createControl();
        myCompiler = new HobbesCompiler();
    }

    @Test
    public void shouldCompile() throws IOException {
        IHobbesFrontEnd frontEnd = myControl.createMock(IHobbesFrontEnd.class);
        expect(frontEnd.process()).andReturn(8);

        myControl.replay();
        assertEquals(".sub main\n\tprint 8\n\tprint \"\\n\"\n.end\n",
                myCompiler.compile(frontEnd, new StringBuilder()).toString());
        myControl.verify();
    }

}
