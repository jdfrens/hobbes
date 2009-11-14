package org.norecess.hobbes.drivers.injection;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.environment.NullEnvironment;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.backend.CodeWriter;
import org.norecess.hobbes.backend.ICodeWriter;
import org.norecess.hobbes.backend.IPIRCleaner;
import org.norecess.hobbes.backend.PIRCleaner;
import org.norecess.hobbes.compiler.CompilerFactory;
import org.norecess.hobbes.compiler.ICompilerFactory;
import org.norecess.hobbes.compiler.IPIRCompiler;
import org.norecess.hobbes.compiler.PIRCompiler;
import org.norecess.hobbes.compiler.body.IPIRBodyCompiler;
import org.norecess.hobbes.compiler.body.PIRBodyCompiler;
import org.norecess.hobbes.compiler.epilog.IPIREpilogCompiler;
import org.norecess.hobbes.compiler.epilog.PIREpilogCompiler;
import org.norecess.hobbes.compiler.operators.ArithmeticOperator;
import org.norecess.hobbes.compiler.operators.ComparisonOperator;
import org.norecess.hobbes.compiler.operators.OperatorInstruction;
import org.norecess.hobbes.compiler.prolog.IPIRPrologCompiler;
import org.norecess.hobbes.compiler.prolog.PIRPrologCompiler;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class PIRModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IPIRCleaner.class).to(PIRCleaner.class);
		bind(IResourceAllocator.class).to(ResourceAllocator.class);
		bind(ICodeWriter.class).to(CodeWriter.class);
		bind(Appendable.class).annotatedWith(Names.named("output")).toInstance(
				System.out);
		bind(IPIRCompiler.class).to(PIRCompiler.class);
		bind(IPIRPrologCompiler.class).to(PIRPrologCompiler.class);
		bind(IPIRBodyCompiler.class).to(PIRBodyCompiler.class);
		bind(IPIREpilogCompiler.class).to(PIREpilogCompiler.class);
		bind(ICompilerFactory.class).to(CompilerFactory.class);
	}

	@Provides
	public IEnvironment<IRegister> provideInitialEnvironment() {
		return new NullEnvironment<IRegister>();
	}

	@Provides
	public IEnvironment<PrimitiveType> provideInitialTypeEnvironment() {
		return new NullEnvironment<PrimitiveType>();
	}

	@Provides
	public Map<Operator, OperatorInstruction> provideOperatorInstructions() {
		Map<Operator, OperatorInstruction> operatorInstructions = new HashMap<Operator, OperatorInstruction>();
		addArithmeticOperators(operatorInstructions);
		addComparisonOperators(operatorInstructions);
		return operatorInstructions;
	}

	private void addComparisonOperators(
			Map<Operator, OperatorInstruction> operatorInstructions) {
		operatorInstructions.put(Operator.LESS_THAN,
				new ComparisonOperator("<"));
		operatorInstructions.put(Operator.LESS_EQUALS, new ComparisonOperator(
				"<="));
		operatorInstructions.put(Operator.EQUALS, new ComparisonOperator("=="));
		operatorInstructions.put(Operator.NOT_EQUALS, new ComparisonOperator(
				"!="));
		operatorInstructions.put(Operator.GREATER_EQUALS,
				new ComparisonOperator(">="));
		operatorInstructions.put(Operator.GREATER_THAN, new ComparisonOperator(
				">"));
	}

	private void addArithmeticOperators(
			Map<Operator, OperatorInstruction> operatorInstructions) {
		operatorInstructions.put(Operator.ADD, new ArithmeticOperator("+"));
		operatorInstructions
				.put(Operator.SUBTRACT, new ArithmeticOperator("-"));
		operatorInstructions
				.put(Operator.MULTIPLY, new ArithmeticOperator("*"));
		operatorInstructions.put(Operator.DIVIDE, new ArithmeticOperator("/"));
		operatorInstructions.put(Operator.MODULUS, new ArithmeticOperator("%"));
	}

}