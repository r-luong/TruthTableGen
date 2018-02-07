import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class AndOperatorTest {

    private static Parser p = Parser.getInstance();

	@Test
	public void StandardTruthTableTest() {
		String input = "P^Q";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, false, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}
	
	@Test
	public void NestedAndTest0() {
		String input = "(((P^Q)))";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, false, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}
	
	@Test
	public void NestedAndTest1() {
		String input = "(P^Q)^R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, false, false, false, false, false, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}
	
	@Test
	public void NestedAndTest2() {
		String input = "P^(Q^R)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, false, false, false, false, false, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}

	@Test
	public void MultipleAndTest() {
		String input = "((((P^Q)^R))^S)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, false, false, false, false, false, false, false, 
							false, false, false, false, false, false, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(answers[i], output.get(i));
		}
	}

}
