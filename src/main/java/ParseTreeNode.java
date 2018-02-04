/**
 * @author Richard Luong
 * Node in a ParseTree containing a token from a propositional logic expression.
 * Includes a left and right pointer to other ParseTreeNodes. 
 */
public class ParseTreeNode {
	public Token currToken;
	public ParseTreeNode leftNode;
	public ParseTreeNode rightNode;

	ParseTreeNode(Token currToken) {
		this.currToken = currToken;
		this.leftNode = null;
		this.rightNode = null;
	}	
	
	ParseTreeNode(Token currToken, ParseTreeNode leftNode, ParseTreeNode rightNode) {
		this.currToken = currToken;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}
}
