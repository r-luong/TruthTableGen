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
	 * Gets a string representing a propositional logic expression and analyses it to creates a list of tokens that
     * correlate to the symbols in the expression such as the variables and connectives. In the end return this list
	 * @param inputStr prop logic expression from the user as a string
	 * @return a list of tokens
	 */
	public ArrayList<Token> createTokensFromInput(String inputStr) {
	    // TODO: Implement
		return new ArrayList<Token>();
	}

    /**
     * Checks if a list of tokens is a valid expression or not
     * @param tokens list of tokens that form the expression
     * @return true if the tokens form a valid expression, false otherwise
     */
    public boolean isValidExpr(ArrayList<Token> tokens) {
        // TODO: Implement
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
        return new LinkedHashSet<Token>();
    }
	

}

