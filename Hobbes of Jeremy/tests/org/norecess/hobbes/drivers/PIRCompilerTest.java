package org.norecess.hobbes.drivers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;

public class PIRCompilerTest {

    private IMocksControl myControl;

    private PIRCompiler   myCompiler;

    @Before
    public void setUp() {
        myControl = EasyMock.createControl();
        myCompiler = new PIRCompiler();
    }

}
