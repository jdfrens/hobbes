package org.norecess.hobbes.support;

import java.util.List;

public interface IHobbesMocksControl {

	public <T> T createMock(Class<T> clazz);

	public <T> List<T> createListOfMocks(int count, Class<T> clazz);

	public void replay();

	public void verify();

}
