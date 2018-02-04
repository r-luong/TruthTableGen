import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class InvalidExpressionTest {

	private static Parser p = Parser.getInstance();

	@Test
	public void InvalidExprTest0() {
		String input = "";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest1() {
		String input = "PP";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest2() {
		String input = "^^";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest3() {
		String input = "P^^Q";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest4() {
		String input = "P~Q";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest5() {
		String input = "()";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest6() {
		String input = "(PVQ";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest7() {
		String input = ")PVQ(";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest8() {
		String input = "=";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest9() {
		String input = "(PVQ)^(WVS)=((XVY)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest10() {
		String input = "(PVQ)=~()";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	@Test
	public void InvalidExprTest11() {
		String input = "PV~)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}

	@Test
	public void InvalidExprTest12() {
		String input = "PV(Q~)";
		ArrayList<Token> tokens = p.createTokensFromInput(input);
        ParseTreeRoot root = p.createParseTree(tokens);
		assertEquals(p.isValidExpr(tokens), false);
	}
	
	
}
