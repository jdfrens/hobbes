package org.norecess.hobbes.support;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

public class HobbesMocksControl implements IHobbesMocksControl {

	private final IMocksControl	myMocksControl;

	public HobbesMocksControl() {
		myMocksControl = EasyMock.createControl();
	}

	public <T> T createMock(Class<T> clazz) {
		return myMocksControl.createMock(clazz);
	}

	public <T> List<T> createListOfMocks(int count, Class<T> clazz) {
		List<T> result = new ArrayList<T>(count);
		for (int i = 0; i < count; i++) {
			result.add(createMock(clazz));
		}
		return result;
	}

	public void replay() {
		myMocksControl.replay();
	}

	public void verify() {
		myMocksControl.verify();
	}

}
