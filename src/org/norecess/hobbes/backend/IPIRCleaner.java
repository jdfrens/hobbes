package org.norecess.hobbes.backend;

public interface IPIRCleaner {

	String process(String instruction);

	ICode process(ICode code);

}
