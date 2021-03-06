package org.norecess.hobbes.frontend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;
import org.norecess.citkit.Symbol;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.HobbesTIR;
import org.norecess.citkit.tir.Position;
import org.norecess.citkit.tir.declarations.VariableDTIR;
import org.norecess.citkit.tir.expressions.BooleanETIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.LetETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.hobbes.HobbesBoolean;

public class HobbesTIRBuilderTest {

	private ANTLRTester	myTester;

	@Before
	public void setUp() {
		myTester = new ANTLRTester(new HobbesFrontEndFactory(), "program",
				"program");
	}

	@Test
	public void shouldBuildIntegers() {
		assertEquals(new IntegerETIR(23), myTester.treeParseInput("23"));
		assertEquals(new IntegerETIR(1234), myTester.treeParseInput("1234"));
	}

	@Test
	public void shouldBuildIntegersWithPosition() {
		assertLine(1, "23");
		assertLine(2, "\n 23");
		assertLine(5, "\n\n\n\n 23");
	}

	@Test
	public void shouldBuildFloats() {
		assertEquals(new FloatingPointETIR(5.0), myTester.treeParseInput("5.0"));
		assertEquals(new FloatingPointETIR(3.14159),
				myTester.treeParseInput("3.14159"));
	}

	@Test
	public void shouldBuildFloatsWithPosition() {
		assertLine(1, "5.0");
		assertLine(3, "\n \n 5.0");
	}

	@Test
	public void shouldBuildBooleans() {
		assertEquals(HobbesBoolean.TRUE, myTester.treeParseInput("#t"));
		assertEquals(HobbesBoolean.FALSE, myTester.treeParseInput("#f"));
	}

	@Test
	public void shouldBuildBooleansWithPosition() {
		assertLine(1, "#t");
		assertLine(2, "\n #f");
		assertLine(7, "\n\n\n\n\n\n #f");
	}

	@Test
	public void shouldBuildAddition() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.ADD,
				new IntegerETIR(2)), myTester.treeParseInput("1+2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.ADD,
				new IntegerETIR(222)), myTester.treeParseInput("1055+222"));
	}

	@Test
	public void shouldBuildSubtraction() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.SUBTRACT,
				new IntegerETIR(2)), myTester.treeParseInput("1-2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.SUBTRACT,
				new IntegerETIR(222)), myTester.treeParseInput("1055-222"));
	}

	@Test
	public void shouldBuildUnaryMinusForIntegers() {
		assertEquals(new IntegerETIR(-5), myTester.treeParseInput("-5"));
		assertEquals(new IntegerETIR(-1223), myTester.treeParseInput("-1223"));
	}

	@Test
	public void shouldBuildUnaryMinusForFloats() {
		assertEquals(new FloatingPointETIR(-3.14159),
				myTester.treeParseInput("-3.14159"));
	}

	@Test
	public void shouldBuildMultiplication() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.MULTIPLY,
				new IntegerETIR(2)), myTester.treeParseInput("1*2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.MULTIPLY,
				new IntegerETIR(222)), myTester.treeParseInput("1055*222"));
	}

	@Test
	public void shouldBuildOperatorsWithPosition() {
		assertLine(1, "1 + 2");
		assertLine(2, "1 \n - \n 2");
		assertLine(1, "1 * 2");
		assertLine(5, "#t \n\n\n\n == #f\n\n");
	}

	@Test
	public void shouldBuildDivision() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.DIVIDE,
				new IntegerETIR(2)), myTester.treeParseInput("1/2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.DIVIDE,
				new IntegerETIR(222)), myTester.treeParseInput("1055/222"));
	}

	@Test
	public void shouldBuildModulus() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.MODULUS,
				new IntegerETIR(2)), myTester.treeParseInput("1%2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.MODULUS,
				new IntegerETIR(222)), myTester.treeParseInput("1055%222"));
	}

	@Test
	public void shouldBuildComparisons() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.LESS_THAN,
				new IntegerETIR(2)), myTester.treeParseInput("1 < 2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.LESS_EQUALS,
				new IntegerETIR(2)), myTester.treeParseInput("1 <= 2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.EQUALS,
				new IntegerETIR(2)), myTester.treeParseInput("1 == 2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.NOT_EQUALS,
				new IntegerETIR(2)), myTester.treeParseInput("1 != 2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1),
				Operator.GREATER_EQUALS, new IntegerETIR(2)),
				myTester.treeParseInput("1 >= 2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1),
				Operator.GREATER_THAN, new IntegerETIR(2)),
				myTester.treeParseInput("1 > 2"));
	}

	@Test
	public void shouldBuildAnd() {
		assertTIR(new OperatorETIR(new BooleanETIR(true), Operator.AND,
				new BooleanETIR(false)), myTester.treeParseInput("#t & #f"));
		assertTIR(
				new OperatorETIR(new VariableETIR(new SimpleLValueTIR(
						Symbol.createSymbol("foo"))), Operator.AND,
						new BooleanETIR(true)),
				myTester.treeParseInput("foo & #t"));
	}

	@Test
	public void shouldBuildOr() {
		assertTIR(new OperatorETIR(new BooleanETIR(true), Operator.OR,
				new BooleanETIR(false)), myTester.treeParseInput("#t | #f"));
		assertTIR(
				new OperatorETIR(new VariableETIR(new SimpleLValueTIR(
						Symbol.createSymbol("foo"))), Operator.OR,
						new BooleanETIR(true)),
				myTester.treeParseInput("foo | #t"));
	}

	@Test
	public void shouldBuildLetExpressions() {
		assertEquals(new LetETIR(new ArrayList<DeclarationTIR>(),
				new IntegerETIR(5)), myTester.treeParseInput("let in 5 end"));
		assertEquals(
				new LetETIR(new ArrayList<DeclarationTIR>(), new OperatorETIR(
						new VariableETIR(new SimpleLValueTIR(
								Symbol.createSymbol("x"))), Operator.ADD,
						new IntegerETIR(5))),
				myTester.treeParseInput("let in x + 5 end"));
		assertEquals(
				new LetETIR( //
						Arrays.asList(new VariableDTIR(
								Symbol.createSymbol("x"), new IntegerETIR(8))), //
						new OperatorETIR(new VariableETIR(new SimpleLValueTIR(
								Symbol.createSymbol("y"))), Operator.ADD,
								new IntegerETIR(5))), //
				myTester.treeParseInput("let var x := 8 in y + 5 end"));
	}

	@Test
	public void shouldBuildDecls() {
		assertEquals(
				Arrays.asList(new VariableDTIR(Symbol.createSymbol("x"),
						new IntegerETIR(8))),
				myTester.scanInput("var x := 8").parseAs("decls")
						.treeParseAs("declarations"));
		assertEquals(Arrays.asList(new VariableDTIR(Symbol.createSymbol("y"),
				HobbesBoolean.TRUE)), myTester.scanInput("var y := #t")
				.parseAs("decls").treeParseAs("declarations"));
		assertEquals(
				Arrays.asList(new VariableDTIR(Symbol.createSymbol("z"),
						new OperatorETIR(new VariableETIR(new SimpleLValueTIR(
								Symbol.createSymbol("x"))), Operator.ADD,
								new IntegerETIR(23)))),
				myTester.scanInput("var z := x + 23").parseAs("decls")
						.treeParseAs("declarations"));
		assertEquals(Arrays.asList(
				new VariableDTIR(Symbol.createSymbol("x"), new IntegerETIR(8)), //
				new VariableDTIR(Symbol.createSymbol("y"), HobbesBoolean.TRUE), //
				new VariableDTIR(Symbol.createSymbol("z"), new OperatorETIR(
						new VariableETIR(new SimpleLValueTIR(Symbol
								.createSymbol("x"))), Operator.ADD,
						new IntegerETIR(23)))), //
				myTester.scanInput("var x := 8  var y := #t  var z := x + 23")
						.parseAs("decls").treeParseAs("declarations"));

	}

	@Test
	public void shouldBuildVariableLValues() {
		assertTIR(
				new VariableETIR(
						new SimpleLValueTIR(Symbol.createSymbol("foo"))),
				myTester.treeParseInput("foo"));
		assertTIR(
				new VariableETIR(new SimpleLValueTIR(
						Symbol.createSymbol("thx1138"))), //
				myTester.treeParseInput("thx1138"));
	}

	@Test
	public void shouldBuildCommandLineArgumentReference() {
		assertTIR(new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
				Symbol.createSymbol("ARGV")), new IntegerETIR(1))), //
				myTester.treeParseInput("ARGV[1]"));
		assertTIR(new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
				Symbol.createSymbol("ARGV")), new IntegerETIR(8))), //
				myTester.treeParseInput("ARGV[8]"));
	}

	@Test
	public void shouldHavePositionInSubscriptLValue() {
		VariableETIR tree = (VariableETIR) myTester.treeParseInput("ARGV[1]");
		assertNotNull(tree.getLValue().getPosition());
	}

	@Test
	public void shouldBuildOperatorAsCommandLineArgumentIndex() {
		assertEquals(new VariableETIR(new SubscriptLValueTIR(
				new SimpleLValueTIR(Symbol.createSymbol("ARGV")),
				new OperatorETIR(new IntegerETIR(1), Operator.ADD,
						new IntegerETIR(2)))), //
				myTester.treeParseInput("ARGV[1+2]"));
	}

	@Test
	public void shouldBuildCommandLineArgumentInPlus() {
		assertEquals(new OperatorETIR(
		//
				new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
						Symbol.createSymbol("ARGV")), new IntegerETIR(1))), //
				Operator.ADD,//
				new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
						Symbol.createSymbol("ARGV")), new IntegerETIR(8)))), //
				myTester.treeParseInput("ARGV[1] + ARGV[8]"));
	}

	@Test
	public void shouldBuildIfExpressions() {
		assertEquals(new IfETIR(HobbesBoolean.TRUE, new IntegerETIR(2),
				new IntegerETIR(3)),
				myTester.treeParseInput("if #t then 2 else 3 end"));
		assertEquals(new IfETIR(HobbesBoolean.FALSE, new OperatorETIR(
				new IntegerETIR(2), Operator.ADD, new IntegerETIR(8)),
				new OperatorETIR(new IntegerETIR(3), Operator.MULTIPLY,
						new IntegerETIR(4))),
				myTester.treeParseInput("if #f then 2+8 else 3*4 end"));
	}

	@Test
	public void shouldBuildIfExpressionsWithPosition() {
		assertLine(1, "if #t then 2 else 3 end");
		assertLine(1, "if #t \n then 2 \n else 3 \n end");
		assertLine(3, "\n\n if #t then 2 else 3 end");
	}

	private void assertLine(int line, String expression) {
		assertEquals(new Position(line),
				((ExpressionTIR) myTester.treeParseInput(expression))
						.getPosition());
	}

	private void assertTIR(ExpressionTIR expectedTIR, Object actualTIR) {
		assertNotNull("should have a TIR", actualTIR);
		assertNotNull("should have a position",
				((HobbesTIR) actualTIR).getPosition());
		assertEquals(expectedTIR, actualTIR);
	}

}
