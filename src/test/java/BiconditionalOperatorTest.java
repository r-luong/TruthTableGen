import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;


public class BiconditionalOperatorTest {

    private static Parser p = Parser.getInstance();

    @Test
    public void StandardTruthTableTest() {
        String input = "P=Q";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {true, false, false, true};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void NestedBiconditionalTest0() {
        String input = "(((P=Q)))";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {true, false, false, true};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void NestedBiconditionalTest1() {
        String input = "(P=Q)=R";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {false, true, true, false, true, false, false, true};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void NestedBiconditionalTest2() {
        String input = "P=(Q=R)";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {false, true, true, false, true, false, false, true};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void MultipleBiconditionalTest0() {
        String input = "P=Q=R";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {false, true, true, false, true, false, false, true};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }

    @Test
    public void MultipleBiconditionalTest1() {
        String input = "((((P=Q)=R))=S)";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
        boolean answers[] = {true, false, false, true, false, true, true, false, false,
                            true, true, false, true, false, false, true};
        ArrayList<Boolean> output = root.getTruthTableResults();
        for (int i = 0; i < output.size(); ++i) {
            assertEquals(answers[i], output.get(i));
        }
    }
}
