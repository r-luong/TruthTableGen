import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class MixedOperatorsTest {

    private static Parser p = Parser.getInstance();

	@Test
	public void CorrectOperatorPrecedenceTest0() {
		String input = "P^QVR";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, false, true, false, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void CorrectOperatorPrecedenceTest1() {
		String input = "PVQ^R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, false, false, true, true, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void CorrectOperatorPrecedenceTest2() {
		String input = "PV~Q^R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, false, false, true, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void CorrectOperatorPrecedenceTest3() {
		String input = "(PVQ)^R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, false, false, true, false, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void CorrectOperatorPrecedenceTest4() {
		String input = "~(PVQ)>R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, true, true, true, true, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void CorrectOperatorPrecedenceTest5() {
		String input = "P>Q=R";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, false, true, true, false, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void AllOperatorsTest() {
		String input = "P^Q=~RVS>W";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {true, false, true, false, false, false, true, false, true, false, 
							true, false, false, false, true, false, true, false, true, false, false,
							false, true, false, false, true, false, true, true, true, false, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
}
