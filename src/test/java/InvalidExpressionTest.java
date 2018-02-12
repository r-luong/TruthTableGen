import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class InvalidExpressionTest {

    private static Parser p = Parser.getInstance();

    @Test
    public void InvalidExprTest0() {
        String input = "";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.EMPTY_EXPR, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest1() {
        String input = "PP";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.ADJACENT_VARIABLES, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest2() {
        String input = "^^";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.ADJACENT_OPERATORS, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest3() {
        String input = "P^^Q";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.ADJACENT_OPERATORS, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest4() {
        String input = "P~Q";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.LHS_OR_RHS_OF_OPERATOR_INVALID, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest5() {
        String input = "()";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.ADJACENT_PARENTHESES, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest6() {
        String input = "(PVQ";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.UNBALANCED_PARENTHESES, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest7() {
        String input = ")PVQ(";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.UNBALANCED_PARENTHESES, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest8() {
        String input = "=";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.LHS_OR_RHS_OF_OPERATOR_INVALID, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest9() {
        String input = "(PVQ)^(WVS)=((XVY)";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.UNBALANCED_PARENTHESES, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest10() {
        String input = "(PVQ)=~()";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.ADJACENT_PARENTHESES, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest11() {
        String input = "PV~)";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.UNBALANCED_PARENTHESES, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest12() {
        String input = "PV(Q~)";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.LHS_OR_RHS_OF_OPERATOR_INVALID, p.isValidExpr(tokens));
    }

    @Test
    public void InvalidExprTest13() {
        String input = "(~)P";
        ArrayList<Token> tokens = p.createTokensFromInput(input);
        assertEquals(ValidExprStatus.LHS_OR_RHS_OF_OPERATOR_INVALID, p.isValidExpr(tokens));
    }


}
