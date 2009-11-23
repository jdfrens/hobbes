package org.norecess.hobbes.compiler.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.declarations.VariableDTIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.LetETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.support.HobbesEasyMock;
import org.norecess.hobbes.support.IHobbesMocksControl;

public class PIRPrologCompilerTest {

	private IHobbesMocksControl	myMocksControl;

	private PIRPrologCompiler	myPrologCompiler;

	@Before
	public void setUp() {
		myMocksControl = HobbesEasyMock.createControl();

		myPrologCompiler = new PIRPrologCompiler();
	}

	@Test
	public void shouldGenerateProlog() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(expression.accept(myPrologCompiler)).andReturn(
				new Code("expression's contribution"));

		myMocksControl.replay();
		assertEquals(new Code(".sub main", "expression's contribution",
				"load_bytecode \"print.pbc\""), //
				myPrologCompiler.generateProlog(expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForInteger() {
		myMocksControl.replay();
		assertEquals(new Code(),
				myPrologCompiler.visitIntegerETIR(new IntegerETIR(5)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForFloat() {
		myMocksControl.replay();
		assertEquals(new Code(),
				myPrologCompiler.visitFloatingPointETIR(new FloatingPointETIR(
						5.0)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForBoolean() {
		myMocksControl.replay();
		assertEquals(new Code(),
				myPrologCompiler.visitBooleanETIR(HobbesBoolean.TRUE));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForVariableExpression() {
		LValueTIR lvalue = myMocksControl.createMock(LValueTIR.class);

		EasyMock.expect(lvalue.accept(myPrologCompiler)).andReturn(
				new Code("lvalue prolog"));

		myMocksControl.replay();
		assertEquals(new Code("lvalue prolog"),
				myPrologCompiler.visitVariableETIR(new VariableETIR(lvalue)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForSimpleLValue() {
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);

		myMocksControl.replay();
		assertEquals(new Code(),
				myPrologCompiler.visitSimpleLValue(new SimpleLValueTIR(symbol)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratorPrologForSubscript() {
		LValueTIR variable = myMocksControl.createMock(LValueTIR.class);
		ExpressionTIR index = myMocksControl.createMock(ExpressionTIR.class);

		myMocksControl.replay();
		assertEquals(new Code(".param pmc argv"),
				myPrologCompiler.visitSubscriptLValue(new SubscriptLValueTIR(
						variable, index)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForOp() {
		IOperator operator = myMocksControl.createMock(IOperator.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(left.accept(myPrologCompiler)).andReturn(
				new Code("left prolog"));
		EasyMock.expect(right.accept(myPrologCompiler)).andReturn(
				new Code("right prolog"));

		myMocksControl.replay();
		assertEquals(new Code("left prolog", "right prolog"),
				myPrologCompiler.visitOperatorETIR(new OperatorETIR(left,
						operator, right)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForIf() {
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(test.accept(myPrologCompiler)).andReturn(
				new Code("test prolog"));
		EasyMock.expect(consequence.accept(myPrologCompiler)).andReturn(
				new Code("then prolog"));
		EasyMock.expect(otherwise.accept(myPrologCompiler)).andReturn(
				new Code("else prolog"));

		myMocksControl.replay();
		assertEquals(new Code("test prolog", "then prolog", "else prolog"), //
				myPrologCompiler.visitIfETIR(new IfETIR(test, consequence,
						otherwise)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForDeclarationFreeLet() {
		List<DeclarationTIR> declarations = myMocksControl.createListOfMocks(0,
				DeclarationTIR.class);
		ExpressionTIR body = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(body.accept(myPrologCompiler)).andReturn(
				new Code("body prolog"));

		myMocksControl.replay();
		assertEquals(new Code("body prolog"),
				myPrologCompiler.visitLetETIR(new LetETIR(declarations, body)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForManyDeclarationLet() {
		List<DeclarationTIR> declarations = myMocksControl.createListOfMocks(3,
				DeclarationTIR.class);
		ExpressionTIR body = myMocksControl.createMock(ExpressionTIR.class);
		List<ICode> declarationCodes = Arrays.<ICode> asList(new Code(
				"declaration 0"), new Code("declaration 1"), new Code(
				"declaration 2"));

		EasyMock.expect(declarations.get(0).accept(myPrologCompiler))
				.andReturn(declarationCodes.get(0));
		EasyMock.expect(declarations.get(1).accept(myPrologCompiler))
				.andReturn(declarationCodes.get(1));
		EasyMock.expect(declarations.get(2).accept(myPrologCompiler))
				.andReturn(declarationCodes.get(2));
		EasyMock.expect(body.accept(myPrologCompiler)).andReturn(
				new Code("body prolog"));

		myMocksControl.replay();
		assertEquals(new Code("declaration 0", "declaration 1",
				"declaration 2", "body prolog"),
				myPrologCompiler.visitLetETIR(new LetETIR(declarations, body)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForVariableDeclaration() {
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		ExpressionTIR initialization = myMocksControl
				.createMock(ExpressionTIR.class);
		ICode code = myMocksControl.createMock(ICode.class);

		EasyMock.expect(initialization.accept(myPrologCompiler))
				.andReturn(code);

		myMocksControl.replay();
		assertSame(code, myPrologCompiler.visitVariableDTIR(new VariableDTIR(
				symbol, initialization)));
		myMocksControl.verify();
	}
}
