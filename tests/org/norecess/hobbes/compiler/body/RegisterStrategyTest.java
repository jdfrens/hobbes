package org.norecess.hobbes.compiler.body;

import static org.junit.Assert.assertArrayEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class RegisterStrategyTest {

	private IMocksControl		myMocksControl;

	private IResourceAllocator	myResourceAllocator;

	private RegisterStrategy	myRegisterStrategy;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myResourceAllocator = myMocksControl
				.createMock(IResourceAllocator.class);

		myRegisterStrategy = new RegisterStrategy(myResourceAllocator);
	}

	@Test
	public void shouldReuseTarget() {
		IRegister originalTarget = myMocksControl.createMock(IRegister.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		HobbesType leftType = myMocksControl.createMock(HobbesType.class);
		HobbesType rightType = myMocksControl.createMock(HobbesType.class);
		IRegister rightRegister = myMocksControl.createMock(IRegister.class);

		EasyMock.expect(left.getType()).andReturn(leftType);
		EasyMock.expect(originalTarget.isCompatible(leftType)).andReturn(true);
		EasyMock.expect(right.getType()).andReturn(rightType);
		EasyMock.expect(myResourceAllocator.nextRegister(rightType)).andReturn(
				rightRegister);

		myMocksControl.replay();
		assertArrayEquals(new IRegister[] { originalTarget, rightRegister },
				myRegisterStrategy.operatorRegisters(originalTarget, left,
						right));
		myMocksControl.verify();
	}

	@Test
	public void shouldNotReuseTarget() {
		IRegister originalTarget = myMocksControl.createMock(IRegister.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		HobbesType leftType = myMocksControl.createMock(HobbesType.class);
		HobbesType rightType = myMocksControl.createMock(HobbesType.class);
		IRegister leftRegister = myMocksControl.createMock(IRegister.class);
		IRegister rightRegister = myMocksControl.createMock(IRegister.class);

		EasyMock.expect(left.getType()).andReturn(leftType).atLeastOnce();
		EasyMock.expect(originalTarget.isCompatible(leftType)).andReturn(false);
		EasyMock.expect(myResourceAllocator.nextRegister(leftType)).andReturn(
				leftRegister);
		EasyMock.expect(right.getType()).andReturn(rightType);
		EasyMock.expect(myResourceAllocator.nextRegister(rightType)).andReturn(
				rightRegister);

		myMocksControl.replay();
		assertArrayEquals(new IRegister[] { leftRegister, rightRegister },
				myRegisterStrategy.operatorRegisters(originalTarget, left,
						right));
		myMocksControl.verify();
	}

}
