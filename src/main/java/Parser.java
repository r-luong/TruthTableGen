import java.util.*;

/**
 * @author Richard Luong
 * Parser to get the tokens out of a string and parse it to form a parse tree which can be evaluated
 * to form a truth table of a valid propositional logic expression
 */
public class Parser {
    private static Parser instance = null;

    // Singleton class so class should not be instantiated
    private Parser() {}

    // Only create and retrieve 1 instance of the Parser
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    /**
     * Gets a string representing a propositional logic expression and iterates over the characters to create a list of
     * tokens that represent the symbols in the expression such as the variables and connectives. In the end return
     * this list
     * @param inputStr prop logic expression from the user as a string
     * @return a list of tokens
     */
    public ArrayList<Token> createTokensFromInput(String inputStr) {
        ArrayList<Token> tokens = new ArrayList<>();
        char[] charArr = inputStr.toCharArray();
        for (char c: charArr) {
            // Ignore whitespace
            if (c == ' ') continue;
            Token currToken = Token.charToToken(c);
            tokens.add(currToken);
        }
        return tokens;
    }

    /**
     * Checks if a list of tokens is a valid expression or not
     * @param tokens list of tokens that form the expression
     * @return true if the tokens form a valid expression, false otherwise
     */
    public boolean isValidExpr(ArrayList<Token> tokens) {
        // An empty expression is invalid
        if (tokens.isEmpty()) return false;
        // Check there is no unclosed pair of parentheses
        if (!isParenBalanced(tokens)) return false;
        // Checks that there are no two variables, connective or pair of matching parentheses are adjacent to each other
        if (!adjacencyTest(tokens)) return false;
        // Check all operators have a valid sub expr on their left or right
        if (!operatorTest(tokens)) return false;
        return true;

    }

    // Helper methods to determine if an expression is valid

    /**
     * Checks if the list of tokens has no unmatched pairs of parenthesis
     * @param tokens Tokens representing the expression
     * @return true if there are no unmatched pairs of parenthesis
     */
    private boolean isParenBalanced(ArrayList<Token> tokens) {
        Stack<Token> parenStack = new Stack<>();
        for (Token currToken: tokens) {
            if (currToken == Token.L_PAREN) {
                parenStack.add(currToken);
            } else if (currToken == Token.R_PAREN) {
                if (parenStack.isEmpty()) {
                    return false;
                } else {
                    parenStack.pop();
                }
            }
        }
        // If the stack is not empty then there is at least one unmatched parenthesis pair somewhere
        return parenStack.isEmpty();
    }

    /**
     * Checks that there are no two variables, connective, pair of matching parentheses are adjacent to each other
     * @param tokens List of tokens representing the prop logic expression
     * @return true if it passes the test
     */
    private boolean adjacencyTest(ArrayList<Token> tokens) {
        for (int i = 0; i < tokens.size()-1; ++i) {
            Token currToken = tokens.get(i);
            Token nextToken = tokens.get(i+1);
            if (Token.isVar(currToken) && Token.isVar(nextToken)) {
                return false;
            }  else if (Token.isConnective(currToken) && Token.isConnective(nextToken)) {
                return false;
            } else if (currToken == Token.L_PAREN && nextToken == Token.R_PAREN) {
                return false;
            } else if (currToken == Token.NOT && Token.isConnective(nextToken)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks all operators (special case with NOT) have a valid sub expr to their left and right through the
     * existence of a variable or sub expression
     * @param tokens List of tokens representing the prop logic expression
     * @return true or false
     */
    private boolean operatorTest(ArrayList<Token> tokens) {
        // First find all the connectives in the expr and note their index
        ArrayList<Integer> operatorIndexes = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token currToken = tokens.get(i);
            if (Token.isOperator(currToken)) {
                operatorIndexes.add(i);
            }
        }
        Stack<Token> parenStack = new Stack<>();
        // Now check there are sub expressions on the left and right side of each operator
        // Note: Special case for NOT operators as having a NOT either side is valid
        for (int cIndex: operatorIndexes) {
            Token currOperator = tokens.get(cIndex);
            if (operatorTestLeftSide(currOperator, cIndex, tokens, parenStack) == false)
                return false;
            if (operatorTestRightSide(currOperator, cIndex, tokens, parenStack) == false)
                return false;
        }
        return true;
    }


    /**
     * Helper method for operatorTest: Tests to see if the LHS of an operator contains either a sub expression or a
     * variable that the operator can be applied to which is required. Special case for the NOT operator as it is the
     * only operator that can be applied to itself and only applies itself to the sub expression or variable on its RHS
     * @param currOperator Current operator that we are trying to determine is valid in its use
     * @param cIndex Current index of the operator in the tokens list
     * @param tokens List of tokens representing a propositional logic expression
     * @param parenStack Stack used to keep track of parenthesis to identify sub expressions
     * @return true or false
     */
    private boolean operatorTestLeftSide(Token currOperator, int cIndex, ArrayList<Token> tokens, Stack<Token> parenStack) {
        parenStack.clear();
        // A NOT should not have a variable right parenthesis of its left
        if (currOperator == Token.NOT && cIndex != 0) {
            Token leftToken = tokens.get(cIndex-1);
            if (Token.isVar(leftToken) || leftToken == Token.R_PAREN)
                return false;
        } else if (currOperator == Token.NOT && cIndex == 0) {
            // If the NOT is at the start, there doesn't need to be anything on its LHS
            return true;
        }
        // Iterate to search that there is either a sub expression or a variable that the operator is applied to
        for (int i = cIndex-1; i >= 0; i--) {
            Token currToken = tokens.get(i);
            // A stack is used to keep track of parenthesis to identify sub expressions that have been grouped
            if (currToken == Token.L_PAREN) {
                // On the LHS of an operator, a right parenthesis should be the first one we encounter, should be left
                if (parenStack.isEmpty())
                    return false;
                parenStack.pop();
                // This means a grouped sub expression has been found which the operator can be applied on
                if (parenStack.isEmpty())
                    return true;
            } else if (currToken == Token.R_PAREN) {
                parenStack.add(currToken);
            } else if (Token.isVar(currToken) && parenStack.isEmpty()) {
                return true;
            } else if (Token.isOperator(currToken) && parenStack.isEmpty()) {
                // It is valid for the LHS of a NOT to be a NOT
                if (currOperator == Token.NOT && currToken == Token.NOT)
                    return true;
                // Invalid if an operator is found before a sub expression or variable
                return false;
            }
        }
        return false;
    }


    /**
     * Helper method for operatorTest: Tests to see if the RHS of an operator contains either a sub expression or a
     * variable that the operator can be applied to which is required. Special case for the NOT operator as it is the
     * only operator that can be applied to itself and only applies itself to the sub expression or variable on its RHS
     * @param currOperator Current operator that we are trying to determine is valid in its use
     * @param cIndex Current index of the operator in the tokens list
     * @param tokens List of tokens representing a propositional logic expression
     * @param parenStack Stack used to keep track of parenthesis to identify sub expressions
     * @return true or false
     */
    private boolean operatorTestRightSide(Token currOperator, int cIndex, ArrayList<Token> tokens, Stack<Token> parenStack) {
        parenStack.clear();
        for (int i = cIndex+1; i < tokens.size(); i++) {
            Token currToken = tokens.get(i);
            // Keep track of parenthesis to identify sub expressions that have been grouped
            if (currToken == Token.L_PAREN) {
                parenStack.add(currToken);
            } else if (currToken == Token.R_PAREN) {
                // On the RHS of an operator, a right parenthesis should be the first one we encounter, should be left
                if (parenStack.isEmpty())
                    return false;
                parenStack.pop();
                // This means a grouped sub expression has been found which the operator can be applied on
                if (parenStack.isEmpty())
                    return true;
            } else if (Token.isVar(currToken) && parenStack.isEmpty()) {
                return true;
            } else if (Token.isOperator(currToken) && parenStack.isEmpty()) {
                // It is valid for the RHS of a NOT to be a NOT
                if (currOperator == Token.NOT && currToken == Token.NOT)
                    return true;
                // Invalid if an operator is found before a sub expression or variable
                return false;
            }
        }
        return false;
    }

    /**
     * Creates a ParseTreeRoot given a valid propositional logic expression
     * @param tokens list of tokens that represent a valid prop logic expression
     * @return ParseTreeRoot used to evaluate the expression or carry out other operations
     */
    public ParseTreeRoot createParseTree(ArrayList<Token> tokens) {
        // TODO: Implement
        return null;
    }

    /**
     * Forms a list of all the variables in a prop logic expression
     * @param tokens List of tokens representing a prop logic expression
     * @return List of variables
     */
    private Set<Token> getVariables(ArrayList<Token> tokens) {
        // TODO: Implement
        // Order of variables is in the order they appear
        return new LinkedHashSet<>();
    }


}

