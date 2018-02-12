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

    /**
     * Constructor: Assumes left and right are null
     * @param currToken Token the node holds
     * @param tokens List of tokens that make up the current expression
     */
    ParseTreeNode(Token currToken, List<Token> tokens) {
        this.currToken = currToken;
        this.leftNode = null;
        this.rightNode = null;
        setExprStr(tokens);
    }

    /**
     * Constructor
     * @param currToken Token the node holds
     * @param leftNode Node connected to the left branch of this node
     * @param rightNode Node connected to the right branch of this node
     * @param tokens List of tokens that make up the current expression
     */
    ParseTreeNode(Token currToken, ParseTreeNode leftNode, ParseTreeNode rightNode, List<Token> tokens) {
        this.currToken = currToken;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        setExprStr(tokens);
    }

    /**
     * Iterates through the list of tokens to create a string representing the expression
     * @param tokens List of tokens representing a propositional logic expression
     */
    private void setExprStr(List<Token> tokens) {
        StringBuilder stringBuilder = new StringBuilder();;
        for (Token currToken: tokens) {
            stringBuilder.append(currToken.getStringRep());
        }
        exprStr = stringBuilder.toString();
    }

    /**
     * @return Token held in the node
     */
    public Token getCurrToken() {
        return currToken;
    }

    /**
     * @return Node connected to left branch of this node
     */
    public ParseTreeNode getLeftNode() {
        return leftNode;
    }

    /**
     * @return Node connected to right branch of this node
     */
    public ParseTreeNode getRightNode() {
        return rightNode;
    }

    /**
     * @return String representing the expression for this node and its children
     */
    public String getExprStr() {
        return exprStr;
    }
}
