import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ValidExpressionTest {

    private static Parser p = Parser.getInstance();

    @Test
    public void ValidExprTest0() {
        String input = "Q";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest1() {
        String input = "QVP";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest2() {
        String input = "PVQ";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest3() {
        String input = "(Q)";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest4() {
        String input = "Q^PVR";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest5() {
        String input = "~~~~P";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest6() {
        String input = "(((P)))=Q";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest7() {
        String input = "Q>Q";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest8() {
        String input = "Q>Q";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest9() {
        String input = "(Q>P)VR";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest10() {
        System.out.println("Test 9");
        String input = "(PVQ)^(WVS)=(XVY)";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void ValidExprTest11() {
        String input = "(PVQ)=R";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.VALID_EXPR, p.isValidExpr(tokens));
    }
}
