package org.norecess.hobbes.interpreter;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;

public interface IInterpreter extends ExpressionTIRVisitor<DatumTIR>,
		LValueTIRVisitor<DatumTIR> {

	public DatumTIR interpret(ExpressionTIR expression);

	public DatumTIR interpret(IEnvironment<DatumTIR> newEnvironment,
			ExpressionTIR expression);

	public IDeclarationBinder createBinder();

}
