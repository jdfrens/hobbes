package org.norecess.hobbes.drivers.injection;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.environment.NullEnvironment;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.backend.CodeWriter;
import org.norecess.hobbes.backend.ICodeWriter;
import org.norecess.hobbes.backend.IPIRCleaner;
import org.norecess.hobbes.backend.PIRCleaner;
import org.norecess.hobbes.compiler.CompilerAsTranslator;
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
import org.norecess.hobbes.interpreter.ErrorHandler;
import org.norecess.hobbes.interpreter.IErrorHandler;
import org.norecess.hobbes.translator.ITranslator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class PIRModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IPIRCleaner.class).to(PIRCleaner.class);
		bind(IResourceAllocator.class).to(ResourceAllocator.class);
		bind(ICodeWriter.class).to(CodeWriter.class);
		bind(ITranslator.class).to(CompilerAsTranslator.class);
		bind(IPIRCompiler.class).to(PIRCompiler.class);
		bind(IPIRPrologCompiler.class).to(PIRPrologCompiler.class);
		bind(IPIRBodyCompiler.class).to(PIRBodyCompiler.class);
		bind(IPIREpilogCompiler.class).to(PIREpilogCompiler.class);
		bind(ICompilerFactory.class).to(CompilerFactory.class);
		bind(IErrorHandler.class).to(ErrorHandler.class);
	}

	@Provides
	public IEnvironment<IRegister> provideInitialEnvironment() {
		return new NullEnvironment<IRegister>();
	}

	@Provides
	public Map<IOperator, OperatorInstruction> provideOperatorInstructions() {
		Map<IOperator, OperatorInstruction> operatorInstructions = new HashMap<IOperator, OperatorInstruction>();
		addArithmeticOperators(operatorInstructions);
		addComparisonOperators(operatorInstructions);
		return operatorInstructions;
	}

	private void addComparisonOperators(
			Map<IOperator, OperatorInstruction> operatorInstructions) {
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
			Map<IOperator, OperatorInstruction> operatorInstructions) {
		operatorInstructions.put(Operator.ADD, new ArithmeticOperator("+"));
		operatorInstructions
				.put(Operator.SUBTRACT, new ArithmeticOperator("-"));
		operatorInstructions
				.put(Operator.MULTIPLY, new ArithmeticOperator("*"));
		operatorInstructions.put(Operator.DIVIDE, new ArithmeticOperator("/"));
		operatorInstructions.put(Operator.MODULUS, new ArithmeticOperator("%"));
	}

}
