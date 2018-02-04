import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class OrOperatorTestTest {

    private static Parser p = Parser.getInstance();

	@Test
	public void StandardTruthTableTest() {
		String input = "PVQ";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void NestedOrTest0() {
		String input = "(((PVQ)))";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void NestedOrTest1() {
		String input = "(PVQ)VR";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, true, true, true, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
	
	@Test
	public void NestedOrTest2() {
		String input = "PV(QVR)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, true, true, true, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}

	@Test
	public void MultipleOrTest() {
		String input = "((((PVQ)VR))VS)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		boolean answers[] = {false, true, true, true, true, true, true, true, 
							true, true, true, true, true, true, true, true};
		ArrayList<Boolean> output = root.getTruthTableResults();
		for (int i = 0; i < output.size(); ++i) {
			assertEquals(output.get(i), answers[i]);
		}
	}
}
