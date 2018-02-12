import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class NotOperatorTest {

    private static Parser p = Parser.getInstance();

    @Test
    public void StandardTruthTableTest() {
        String input = "~P";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {true, false};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void MultipleNotTest0() {
        String input = "~(~(~(~P)))";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {false, true};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void MultipleNotTest1() {
        String input = "~~~~~~~P";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {true, false};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void NestedNotTest0() {
        String input = "~(((P)))";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {true, false};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }
}
