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
     * @return ValidExprStatus.VALID_EXPR if valid, otherwise another enum is returned detailing why it was invalid
     */
    public ValidExprStatus isValidExpr(List<Token> tokens) {
        // An empty expression is invalid
        if (tokens.isEmpty()) return ValidExprStatus.EMPTY_EXPR;
        // Check there is no unclosed pair of parentheses
        if (!isParenBalanced(tokens)) return ValidExprStatus.UNBALANCED_PARENTHESES;
        // Checks that there are no two variables, connective or pair of matching parentheses are adjacent to each other
        ValidExprStatus status = adjacencyTest(tokens);
        if (status != ValidExprStatus.VALID_EXPR) return status;
        // Check all operators have a valid sub expr on their left or right
        if (!operatorTest(tokens)) return ValidExprStatus.LHS_OR_RHS_OF_OPERATOR_INVALID;
        return ValidExprStatus.VALID_EXPR;
    }

    // Helper methods to determine if an expression is valid

    /**
     * Checks if the list of tokens has no unmatched pairs of parenthesis
     * @param tokens Tokens representing the expression
     * @return true if there are no unmatched pairs of parenthesis
     */
    private boolean isParenBalanced(List<Token> tokens) {
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
     * @return a ValidExprStatus
     */
    private ValidExprStatus adjacencyTest(List<Token> tokens) {
        for (int i = 0; i < tokens.size()-1; i++) {
            Token currToken = tokens.get(i);
            Token nextToken = tokens.get(i+1);
            if (Token.isVar(currToken) && Token.isVar(nextToken)) {
                return ValidExprStatus.ADJACENT_VARIABLES;
            }  else if (Token.isConnective(currToken) && Token.isConnective(nextToken)) {
                return ValidExprStatus.ADJACENT_OPERATORS;
            } else if (currToken == Token.L_PAREN && nextToken == Token.R_PAREN) {
                return ValidExprStatus.ADJACENT_PARENTHESES;
            } else if (currToken == Token.NOT && Token.isConnective(nextToken)) {
                return ValidExprStatus.ADJACENT_OPERATORS;
            }
        }
        return ValidExprStatus.VALID_EXPR;
    }

    /**
     * Checks all operators (special case with NOT) have a valid sub expr to their left and right through the
     * existence of a variable or sub expression
     * @param tokens List of tokens representing the prop logic expression
     * @return true or false
     */
    private boolean operatorTest(List<Token> tokens) {
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
    private boolean operatorTestLeftSide(Token currOperator, int cIndex, List<Token> tokens, Stack<Token> parenStack) {
        parenStack.clear();
        // A NOT should not have a variable right parenthesis of its left
        if (currOperator == Token.NOT && cIndex != 0) {
            Token leftToken = tokens.get(cIndex-1);
            if (Token.isVar(leftToken) || leftToken == Token.R_PAREN)
                return false;
            else
                return true;
        } else if (currOperator == Token.NOT && cIndex == 0) {
            // If the NOT is at the start, there doesn't need to be anything on its LHS
            return true;
        }
        // Iterate to search that there is either a sub expression or a variable that the operator is applied to
        for (int i = cIndex-1; i >= 0; i--) {
            Token currToken = tokens.get(i);
            // A stack is used to keep track of parenthesis to identify sub expressions that have been grouped
            if (currToken == Token.L_PAREN) {
                // On the LHS of an operator, a right parenthesis should be the operator one we encounter, should be left
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
    private boolean operatorTestRightSide(Token currOperator, int cIndex, List<Token> tokens, Stack<Token> parenStack) {
        parenStack.clear();
        for (int i = cIndex+1; i < tokens.size(); i++) {
            Token currToken = tokens.get(i);
            // Keep track of parenthesis to identify sub expressions that have been grouped
            if (currToken == Token.L_PAREN) {
                parenStack.add(currToken);
            } else if (currToken == Token.R_PAREN) {
                // On the RHS of an operator, a right parenthesis should be the operator one we encounter, should be left
                if (parenStack.isEmpty())
                    return false;
                parenStack.pop();
                // This means a grouped sub expression has been found which the operator can be applied on
                if (parenStack.isEmpty())
                    return true;
            } else if (Token.isVar(currToken) && parenStack.isEmpty()) {
                return true;
            } else if (Token.isOperator(currToken) && parenStack.isEmpty()) {
                // Finding a NOT is valid as they can be the sub expression that the operator is applied to
                if (currToken == Token.NOT)
                    return true;
                // Invalid if an operator other than a NOT is found before a sub expression or variable
                return false;
            }
        }
        return false;
    }

    /**
     * Creates a ParseTreeRoot given a valid propositional logic expression
     * @precondition Must call isValidExpr() on token list before passing it into this function
     * @param tokens List of tokens that represent a valid prop logic expression
     * @return ParseTreeRoot used to evaluate the expression or carry out other operations
     */
    public ParseTreeRoot createParseTree(List<Token> tokens) {
        formatTokens(tokens);
        return new ParseTreeRoot(tokens);
    }

    /**
     * Formats the token list by adding parentheses to create sub expressions to allow the parse
     * tree to be easily created
     * @param tokens List of tokens
     * @return An updated list of tokens
     */
    private List<Token> formatTokens(List<Token> tokens) {
        // Get the location of all the connectives that are not between parentheses
        Stack<Token> parenStack = new Stack<>();
        ArrayList<Pair> connectives = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token currToken = tokens.get(i);
            if (currToken == Token.L_PAREN) {
                parenStack.add(Token.L_PAREN);
            } else if (currToken == Token.R_PAREN) {
                parenStack.pop();
            } else if (Token.isOperator(currToken) && parenStack.isEmpty()) {
                connectives.add(new Pair(currToken, i));
            }
        }
        // Sort all the operators into order of precedence and making them right associative
        Collections.sort(connectives, new Comparator<Pair>() {
            @Override
            public int compare(Pair a, Pair b) {
                if (a.getOperator().getPrecedenceVal() == b.getOperator().getPrecedenceVal()) {
                    return (a.getIndex() < b.getIndex()) ? 1: (a.getIndex() == b.getIndex()) ? 0 : -1;
                }
                return (a.getOperator().getPrecedenceVal() < b.getOperator().getPrecedenceVal()) ? -1 :
                        (a.getOperator().getPrecedenceVal() == b.getOperator().getPrecedenceVal()) ? 0 : 1;
            }
        });
        /* Now modify the token list based on the precedence of each operator by adding parentheses
        in between each operator to allow the parse tree to be easily created*/
        for (Pair p: connectives) {
            // LHS of the current operator
            parenStack.clear();
            boolean leftParenAdded = false;
            for (int i = p.getIndex()-1; i >= 0; i--) {
                leftParenAdded = addParenthesis(true, i, tokens, parenStack);
                if (leftParenAdded) {
                    p.incrementIndex(1);
                    break;
                }
            }
            /* If the left parenthesis wasn't added then add one to the end of the token list since this encloses
             * the entire expression */
            if (!leftParenAdded) {
                tokens.add(0, Token.L_PAREN);
                for (Pair p2: connectives) {
                    if (p2.getIndex() < p.getIndex()) {
                        p2.incrementIndex(1);
                    }
                }
                p.incrementIndex(1);
            }
            // RHS of the current operator
            parenStack.clear();
            boolean rightParenAdded = false;
            for (int i = p.getIndex()+1; i < tokens.size(); i++) {
                rightParenAdded = addParenthesis(false, i, tokens, parenStack);
                if (rightParenAdded)
                    break;
            }
            /* If the right parenthesis wasn't added then add one to the end of the token list since this encloses
             * the entire expression */
            if (!rightParenAdded) {
                tokens.add(Token.R_PAREN);
            }
            /* Since we added 2 parentheses, we need to offset the operator index of all the operators
             * that come after the current operator*/
            updateIndexes(connectives, p.getIndex());
        }
        return tokens;
    }


    /**
     * Add a parenthesis on the left or right hand side of a connective to create a sub expression that will enable
     * evaluating the expression to be done easily as the tree representing the expression can be easily created
     * @param iteratingLeft Boolean on whether we are currently looking on the LHS or RHS of a connective
     * @param index Index of the current token we are looking at
     * @param tokens List of tokens that represent a propositional logic expression
     * @param parenStack Stack holding parentheses to help determine where to add the new parenthesis
     * @return true if a parenthesis was added, false otherwise
     */
    private boolean addParenthesis(boolean iteratingLeft, int index, List<Token> tokens, Stack<Token> parenStack) {
        Token currToken = tokens.get(index);
        if (currToken == Token.L_PAREN) {
            if (iteratingLeft) {
                parenStack.pop();
            } else {
                parenStack.add(Token.L_PAREN);
            }
        } else if (currToken == Token.R_PAREN) {
            if (!iteratingLeft) {
                parenStack.pop();
            } else {
                parenStack.add(Token.R_PAREN);
            }
        } else if (Token.isOperator(currToken) && parenStack.isEmpty()) {
            // Only add the parenthesis if we are not already in a sub expression
            if (iteratingLeft) {
                tokens.add(index + 1, Token.L_PAREN);
            } else {
                tokens.add(index, Token.R_PAREN);
            }
            return true;
        } else if (Token.isVar(currToken) && parenStack.isEmpty()) {
            // Iterating left we have to deal with NOT operators
            if (iteratingLeft) {
                int tmpOffset = 1;
                // Case where there is a not operator in front of the variable
                while (index - tmpOffset >= 0 && tokens.get(index - tmpOffset) == Token.NOT) {
                    tmpOffset++;
                }
                // Remember to decrement offset so the left parenthesis goes in front of the variable
                tmpOffset--;
                tokens.add(index - tmpOffset, Token.L_PAREN);
            } else {
                tokens.add(index+1, Token.R_PAREN);
            }
            return true;
        }
        return false;
    }

    /**
     * Update the location of each operator by 2 after the given index since for every operator, we add 2 parentheses
     * to the token list
     * @param connectives List of pairs containing an operator and its location
     * @param index All operators after this index will be updated
     */
    private void updateIndexes(ArrayList<Pair> connectives, int index) {
        for (Pair p: connectives) {
            if (p.getIndex() > index) {
                p.incrementIndex(2);
            }
        };
    }
}

