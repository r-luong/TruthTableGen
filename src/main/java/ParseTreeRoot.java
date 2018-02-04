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
	 * @param root The root ParseTreeNode
	 * @param variables variables in the prop logic expression
	 */
	ParseTreeRoot(ParseTreeNode root, Set<Token> variables) {
		this.root = root;
		this.variables = variables;
	}

	/**
	 * Evaluate each sub expression in the tree to return either a true or false value representing the value
     * of the expression with the specified values for the variables in the expression
	 * @param currNode Current sub expression to evaluate
	 * @param valMappings Ordered HashMap containing the value of each variable
	 * @return a true or false value for the current expression
	 */
	public boolean evalTree(ParseTreeNode currNode, LinkedHashMap<Token, Boolean> valMappings) {
	    // TODO: Implement
	    return false;
	}

	/**
	 * Calculates the truth value of each row in the truth table of the expression
	 * and returns an arraylist of the truth values obtained
	 */
	public ArrayList<Boolean> getTruthTableResults() {
	    // TODO: Implement
        return new ArrayList<Boolean>();
	}

	
	/**
	 * Start the traversal the Parse tree to get an equivalent expression to the original expression
	 * @return a string containing a propositional logic expression
	 */
	public String getExpression() {
        // TODO: Implement
	    return "Not implemented yet";
	}
	
	/**
	 * Getter method
	 * @return the number of unique variables in the expression
	 */
	public int getNumVariables() {
		return variables.size();
	}
	
}
