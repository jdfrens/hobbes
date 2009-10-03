package org.norecess.hobbes.drivers;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.compiler.ArithmeticOperator;
import org.norecess.hobbes.compiler.ComparisonOperator;
import org.norecess.hobbes.compiler.CompilerFactory;
import org.norecess.hobbes.compiler.ICompilerFactory;
import org.norecess.hobbes.compiler.IPIRBodyCompiler;
import org.norecess.hobbes.compiler.IPIREpilogCompiler;
import org.norecess.hobbes.compiler.IPIRPrologCompiler;
import org.norecess.hobbes.compiler.OperatorInstruction;
import org.norecess.hobbes.compiler.PIRBodyCompiler;
import org.norecess.hobbes.compiler.PIREpilogCompiler;
import org.norecess.hobbes.compiler.PIRPrologCompiler;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class PIRModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IPIRPrologCompiler.class).to(PIRPrologCompiler.class);
		bind(IPIREpilogCompiler.class).to(PIREpilogCompiler.class);
		bind(IPIRBodyCompiler.class).to(PIRBodyCompiler.class);
		bind(ICompilerFactory.class).to(CompilerFactory.class);
	}

	@Provides
	@Singleton
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
