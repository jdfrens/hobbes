package org.norecess.hobbes.backend;

import static org.easymock.EasyMock.expect;

import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.compiler.Code;
import org.norecess.hobbes.compiler.ICode;

public class CodeWriterTest {

    private IMocksControl myControl;
    private Appendable    myAppendable;
    private CodeWriter    myWriter;

    @Before
    public void setUp() {
        myControl = EasyMock.createControl();
        myAppendable = myControl.createMock(Appendable.class);
        myWriter = new CodeWriter(myAppendable);
    }

    @Test
    public void shouldWriteNoCode() throws IOException {
        ICode<String> code = new Code<String>();

        myControl.replay();
        myWriter.writeCode(code);
        myControl.verify();
    }

    @Test
    public void shouldWriteCode() throws IOException {
        ICode<String> code = new Code<String>("instruction 1", "instruction 2",
                "instruction 3");

        EasyMock.resetToStrict(myAppendable);
        expectAppended("instruction 1");
        expectAppended("\n");
        expectAppended("instruction 2");
        expectAppended("\n");
        expectAppended("instruction 3");
        expectAppended("\n");

        myControl.replay();
        myWriter.writeCode(code);
        myControl.verify();
    }

    private void expectAppended(String string) throws IOException {
        expect(myAppendable.append(string)).andReturn(myAppendable);
    }

}