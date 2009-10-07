package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.BreakETIR;
import org.norecess.citkit.tir.expressions.IArrayETIR;
import org.norecess.citkit.tir.expressions.IAssignmentETIR;
import org.norecess.citkit.tir.expressions.IBooleanETIR;
import org.norecess.citkit.tir.expressions.ICallETIR;
import org.norecess.citkit.tir.expressions.IFieldAssignmentTIR;
import org.norecess.citkit.tir.expressions.IForETIR;
import org.norecess.citkit.tir.expressions.IIfETIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.ILambdaETIR;
import org.norecess.citkit.tir.expressions.ILetETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.citkit.tir.expressions.IRecordETIR;
import org.norecess.citkit.tir.expressions.ISequenceETIR;
import org.norecess.citkit.tir.expressions.IStringETIR;
import org.norecess.citkit.tir.expressions.IVariableETIR;
import org.norecess.citkit.tir.expressions.IWhileETIR;
import org.norecess.citkit.tir.expressions.NilETIR;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;

public class PIRPrologCompiler implements IPIRPrologCompiler {

	public ICode generateProlog(ExpressionTIR expr) {
		ICode code = new Code();
		code.add(".sub main");
		code.append(expr.accept(this));
		code.add("load_bytecode \"print.pbc\"");
		return code;
	}

	public ICode visitIntegerETIR(IIntegerETIR integer) {
		return new Code();
	}

	public Code visitBooleanETIR(IBooleanETIR arg0) {
		return new Code();
	}

	public ICode visitOperatorETIR(IOperatorETIR expression) {
		Code code = new Code();
		code.append(expression.getLeft().accept(this));
		code.append(expression.getRight().accept(this));
		return code;
	}

	public ICode visitVariableETIR(IVariableETIR arg0) {
		return new Code(".param pmc argv");
	}

	public Code visitIfETIR(IIfETIR ife) {
		Code code = new Code();
		code.append(ife.getTest().accept(this));
		code.append(ife.getThenClause().accept(this));
		code.append(ife.getElseClause().accept(this));
		return code;
	}

	public Code visitLetETIR(ILetETIR arg0) {
		return new Code();
	}

	//
	// Unimplemented
	//
	public Code visitArrayETIR(IArrayETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitAssignmentETIR(IAssignmentETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitBreakETIR(BreakETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitCallETIR(ICallETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitFieldAssignmentETIR(IFieldAssignmentTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitForETIR(IForETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitLambdaETIR(ILambdaETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitNilETIR(NilETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitRecordETIR(IRecordETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitSequenceETIR(ISequenceETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitStringETIR(IStringETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public Code visitWhileETIR(IWhileETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
