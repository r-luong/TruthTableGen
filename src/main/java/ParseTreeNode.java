import java.util.List;

/**
 * @author Richard Luong
 * Node in a ParseTree containing a token from a propositional logic expression.
 * Includes a left and right pointer to other ParseTreeNodes. 
 */
public class ParseTreeNode {
    private Token currToken;
    private ParseTreeNode leftNode;
    private ParseTreeNode rightNode;
    private String exprStr;

    ParseTreeNode(Token currToken, List<Token> tokens) {
        this.currToken = currToken;
        this.leftNode = null;
        this.rightNode = null;
        setExprStr(tokens);
    }

    ParseTreeNode(Token currToken, ParseTreeNode leftNode, ParseTreeNode rightNode, List<Token> tokens) {
        this.currToken = currToken;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        setExprStr(tokens);
    }

    private void setExprStr(List<Token> tokens) {
        exprStr = "";
        for (Token currToken: tokens) {
            exprStr += currToken.getCharRep();
        }
    }

    public Token getCurrToken() {
        return currToken;
    }

    public ParseTreeNode getLeftNode() {
        return leftNode;
    }

    public ParseTreeNode getRightNode() {
        return rightNode;
    }

    public String getExprStr() {
        return exprStr;
    }
}
