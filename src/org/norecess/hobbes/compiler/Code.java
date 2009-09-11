package org.norecess.hobbes.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
 * Mostly just a fancy name for "a list of instructions".  At least for now.
 */
public class Code implements ICode {

    private final List<String> myInstructions;

    public Code() {
        myInstructions = new ArrayList<String>();
    }

    public Code(String... instructions) {
        myInstructions = Arrays.asList(instructions);
    }

    public ICode add(String command) {
        myInstructions.add(command);
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((myInstructions == null) ? 0 : myInstructions.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Code other = (Code) obj;
        if (myInstructions == null) {
            if (other.myInstructions != null) {
                return false;
            }
        } else if (!myInstructions.equals(other.myInstructions)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return myInstructions.toString();
    }

    public Iterator<String> iterator() {
        return myInstructions.iterator();
    }

}
