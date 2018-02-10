import java.util.*;

/**
 *
 * @author Richard Luong
 * Root of a Parse Tree representing a propositional logic expression and enables for the
 * expression to be evaluated recursively to generate a truth table for the expression
 *
 */
public class ParseTreeRoot {
    private ParseTreeNode root;
    private Set<Token> variables;

    /**
     * Constructor
     * @param tokens List of tokens representing a propositional logic expression
     */
    ParseTreeRoot(List<Token> tokens) {
        this.root = makeParseTree(tokens);
        this.variables = new LinkedHashSet<>();
        for (Token currToken: tokens) {
            if (Token.isVar(currToken)) {
                variables.add(currToken);
            }
        }
    }

    /**
     * Recursively build a parse tree representing the prop logic expression by splitting the list of
     * tokens into two based on the operator not surrounded by parentheses
     * @param tokens List of tokens representing the prop logic expression
     * @return a ParseTreeNode and eventually the last node returned will be the root
     */
    private ParseTreeNode makeParseTree(List<Token> tokens) {
        // Base cases
        if (tokens.isEmpty()) return null;
        if (tokens.size() == 1) {
            return new ParseTreeNode(tokens.get(0), tokens);
        }
        // Recursive cases
        int splitIndex = 0;
        Token currOperator = Token.UNKNOWN;
        while (currOperator == Token.UNKNOWN) {
            Stack<Token> parenStack = new Stack<>();
            // Find the operator that has the most precedence that isn't in a sub expression
            for (int i = 0; i < tokens.size(); i++) {
                Token currToken = tokens.get(i);
                // Use parenStack so that we ignore operators that are in sub expressions
                if (currToken == Token.L_PAREN) {
                    parenStack.add(Token.L_PAREN);
                } else if (currToken == Token.R_PAREN) {
                    parenStack.pop();
                } else if (Token.isOperator(currToken) && parenStack.isEmpty()) {
                    currOperator = currToken;
                    splitIndex = i;
                    break;
                } else if (Token.isVar(currToken) && tokens.size() == 1) {
                    // Special case where a variable would have been nested at least 1 pair of parenthesis
                    return new ParseTreeNode(currToken, tokens);
                }
            }
            /* Since we formatted the expression so that sub expressions are explicitly grouped via parentheses,
             * we can assume that if no operator was found then the current expression must be surrounded by at
             * least one pair of parentheses at the moment so take them out */
            if (currOperator == Token.UNKNOWN) {
                tokens = tokens.subList(1, tokens.size()-1);
            }
        }
        // Split the token list on the operator found and recursively build the rest of the nodes
        // Special case for NOT since it doesn't require a left node
        if (currOperator == Token.NOT) {
            ParseTreeNode rightNode = makeParseTree(tokens.subList(1, tokens.size()));
            return new ParseTreeNode(currOperator, null, rightNode, tokens);
        } else {
            ParseTreeNode leftNode = makeParseTree(tokens.subList(0, splitIndex));
            ParseTreeNode rightNode = makeParseTree(tokens.subList(splitIndex+1, tokens.size()));
            return new ParseTreeNode(currOperator, leftNode, rightNode, tokens);
        }
    }


    /**
     * Evaluate each sub expression in the tree to return either a true or false value representing the value
     * of the expression with the specified values for the variables in the expression
     * @param currNode Current sub expression to evaluate
     * @param valMappings Ordered HashMap containing the value of each variable
     * @return a true or false value for the current expression
     */
    public boolean evalTree(ParseTreeNode currNode, LinkedHashMap<Token, Boolean> valMappings) {
        // Base case: If the current symbol is a variable
        if (Token.isVar(currNode.getCurrToken())) {
            return valMappings.get(currNode.getCurrToken());
        }
        // Recursive case: Evaluate the left and right hand side of the current node
        boolean leftVal = true;
        if (currNode.getCurrToken() != Token.NOT) {
            leftVal = evalTree(currNode.getLeftNode(), valMappings);
        }
        boolean rightVal = evalTree(currNode.getRightNode(), valMappings);
        // Evaluate the current operator
        switch (currNode.getCurrToken()) {
            case AND:
                return leftVal && rightVal;
            case OR:
                return leftVal || rightVal;
            case NOT:
                return !rightVal;
            case IMPL:
                return !leftVal || rightVal;
            case BICOND:
                return (!leftVal || rightVal) && (!rightVal || leftVal);
            // This should never be reached unless given an unknown symbol
            default:
                return false;
        }
    }

    /**
     * Calculates the truth value of each row in the truth table for the expression
     * @return A list of the truth values obtained from evaluating each row of the table
     */
    public ArrayList<Boolean> getTruthTableResults() {
        ArrayList<Boolean> results = new ArrayList<>();
        LinkedHashMap<Token, Boolean> varMappings = new LinkedHashMap<>();
        ArrayList<Token> varList = new ArrayList<>();
        for (Token currVar: variables) {
            varList.add(currVar);
        }
        /* Add the variables to the Ordered hashmap in reverse order to allow table to
         * be presented in the standard form in terms of each variable value */
        for (int i = varList.size()-1; i >= 0; i--) {
            varMappings.put(varList.get(i), false);
        }
        double rows = Math.pow(2, variables.size());
        // Use a binary number set to 0 and increment for every new row
        byte currBinNum = (byte) 0b00000000;
        for (int i = 0; i < rows; i++) {
            Iterator<Token> varIt = varMappings.keySet().iterator();
            int shiftCount = 0;
            while (varIt.hasNext()) {
                Token varToChange = varIt.next();
                /* Use a mask and bitshifting to get the next value of a variable according to the
                 * bits in the binary number */
                if ((currBinNum & (1 << shiftCount)) != 0) {
                    varMappings.put(varToChange, true);
                } else {
                    varMappings.put(varToChange, false);
                }
                shiftCount++;
            }
            currBinNum++;
            // Add the result to the list
            boolean output = evalTree(root, varMappings);
            results.add(output);
        }
        return results;
    }


    /**
     * Start the traversal the Parse tree to get an equivalent expression to the original expression
     * @return a string containing a propositional logic expression
     */
    public String getExpression() {
        return root.getExprStr();
    }

    /**
     * Getter method
     * @return the number of unique variables in the expression
     */
    public int getNumVariables() {
        return variables.size();
    }

}
