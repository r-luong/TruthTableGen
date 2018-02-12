import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ImplicationOperatorTest {

    private static Parser p = Parser.getInstance();

	@Test
	public void StandardTruthTableTest() {
		String input = "P>Q";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
		assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {true, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}
	
	@Test
	public void NestedImplicationTest0() {
		String input = "(((P>Q)))";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
		assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {true, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}
	
	@Test
	public void NestedImplicationTest1() {
		String input = "(P>Q)>R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
		assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, false, true, true, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}
	
	@Test
	public void NestedImplicationTest2() {
		String input = "P>(Q>R)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
		assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {true, true, true, true, true, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}

	@Test
	public void MultipleImplicationTest0() {
		// Implication operator should be right associative
		String input = "P>Q>R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
		assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {true, true, true, true, true, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}

	@Test
	public void MultipleImplicationTest1() {
		String input = "((((P>Q)>R))>S)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
		assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {true, true, false, true, true, true, false, true, 
							false, true, false, true, true, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}
}
