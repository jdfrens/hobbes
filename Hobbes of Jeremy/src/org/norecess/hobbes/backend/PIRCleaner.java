package org.norecess.hobbes.backend;

import org.norecess.hobbes.compiler.Code;
import org.norecess.hobbes.compiler.ICode;

public class PIRCleaner implements IPIRCleaner {

    public String process(String instruction) {
        return requiresTab(instruction) ? "\t" + instruction : instruction;
    }

    public ICode<String> process(ICode<String> code) {
        Code<String> processed = new Code<String>();
        String previous = "";
        for (String instruction : code) {
            if (!isUnnecessaryRepeat(previous, instruction)) {
                processed.add(process(instruction));
            }
            previous = instruction;
        }
        return processed;
    }

    private boolean requiresTab(String instruction) {
        return !instruction.startsWith(".sub")
                && !instruction.startsWith(".end");
    }

    private boolean isUnnecessaryRepeat(String previous, String instruction) {
        return previous.equals(".param pmc argv")
                && instruction.equals(".param pmc argv");
    }

}
