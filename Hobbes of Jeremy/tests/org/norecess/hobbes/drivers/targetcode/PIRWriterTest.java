package org.norecess.hobbes.drivers.targetcode;

import static org.easymock.EasyMock.expect;

import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.compiler.Code;
import org.norecess.hobbes.compiler.ICode;

public class PIRWriterTest {

    private IMocksControl myControl;
    private Appendable    myAppendable;
    private PIRWriter     myWriter;

    @Before
    public void setUp() {
        myControl = EasyMock.createControl();
        myAppendable = myControl.createMock(Appendable.class);
        myWriter = new PIRWriter(myAppendable);
    }

    @Test
    public void shouldWriteCode() throws IOException {
        ICode<String> code = new Code<String>("instruction 1", "instruction 2",
                "instruction 3");

        EasyMock.resetToStrict(myAppendable);
        expectAppended("\t");
        expectAppended("instruction 1");
        expectAppended("\n");
        expectAppended("\t");
        expectAppended("instruction 2");
        expectAppended("\n");
        expectAppended("\t");
        expectAppended("instruction 3");
        expectAppended("\n");

        myControl.replay();
        myWriter.writeCode(code);
        myControl.verify();
    }

    @Test
    public void shouldWriteCodeWithoutTabs() throws IOException {
        ICode<String> code = new Code<String>(".instruction 1",
                ".instruction 2", ".instruction 3");

        EasyMock.resetToStrict(myAppendable);
        expectAppended(".instruction 1");
        expectAppended("\n");
        expectAppended(".instruction 2");
        expectAppended("\n");
        expectAppended(".instruction 3");
        expectAppended("\n");

        myControl.replay();
        myWriter.writeCode(code);
        myControl.verify();
    }

    private void expectAppended(String string) throws IOException {
        expect(myAppendable.append(string)).andReturn(myAppendable);
    }

}
