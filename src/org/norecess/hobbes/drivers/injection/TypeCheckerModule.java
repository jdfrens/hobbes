package org.norecess.hobbes.drivers.injection;

import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.environment.NullEnvironment;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.ITopLevelTypeChecker;
import org.norecess.hobbes.typechecker.ITypeChecker;
import org.norecess.hobbes.typechecker.TopLevelTypeChecker;
import org.norecess.hobbes.typechecker.TypeChecker;
import org.norecess.hobbes.typechecker.operators.OperatorTypeChecker;
import org.norecess.hobbes.typechecker.operators.OperatorTypeCheckersFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class TypeCheckerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ITypeChecker.class).to(TypeChecker.class);
		bind(ITopLevelTypeChecker.class).to(TopLevelTypeChecker.class);
	}

	@Provides
	public IEnvironment<PrimitiveType> provideInitialTypeEnvironment() {
		return new NullEnvironment<PrimitiveType>();
	}

	@Provides
	public Map<IOperator, OperatorTypeChecker> provideOperatorTypeCheckers() {
		return new OperatorTypeCheckersFactory().createOperatorTypeCheckers();
	}

}
