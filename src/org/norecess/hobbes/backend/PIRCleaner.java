package org.norecess.hobbes.backend;

public class PIRCleaner implements IPIRCleaner {

	public String process(String instruction) {
		return requiresTab(instruction) ? "\t" + instruction : instruction;
	}

	public ICode process(ICode code) {
		Code processed = new Code();
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
				&& !instruction.startsWith(".end")
				&& !instruction.endsWith(":");
	}

	private boolean isUnnecessaryRepeat(String previous, String instruction) {
		return previous.equals(".param pmc argv")
				&& instruction.equals(".param pmc argv");
	}

}
