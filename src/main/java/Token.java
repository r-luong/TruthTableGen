/**
 * 
 * @author Richard Luong
 * Enum used to represent the operators in propositional logic and 8 variables that can be used
 *
 */

public enum Token {
    // Operations
    NOT(1, '~'), AND(2, '^'), OR(3, 'V'),  IMPL(4, '>'), BICOND(5, '='),
    // Variables
    VAR_P(99, 'P'), VAR_Q(99, 'Q'), VAR_R(99, 'R'), VAR_S(99, 'S'),
    VAR_U(99, 'U'), VAR_W(99, 'W'), VAR_X(99, 'X'), VAR_Y(99, 'Y'),
    // Parentheses
    L_PAREN(0, '('), R_PAREN(0, ')'),
    // Non matching
    UNKNOWN(100, '?');

    // Determines which order the token should be evaluated compared to other tokens
    private int precedenceVal;
    // Character that represents the token in ASCII form
    private char charRep;

    /**
     * Constructor
     * @param precedenceVal value indicating precedence (The lower the more precedence the token has)
     * @param charRep Character that the token is mapped to
     */
    Token(int precedenceVal, char charRep) {
        this.precedenceVal = precedenceVal;
        this.charRep = charRep;
    }

    /**
     * Gets the precedence value of the token
     * @return the precedence value
     */
    public int getPrecedenceVal() {
        return precedenceVal;
    }

    /**
     * Get the character the token is mapped to
     * @return a character
     */
    public char getCharRep() {
        return charRep;
    }

    /**
     * Get the String the token is mapped to. Used so that unicode characters can replace the placeholder characters
     * used for the operators when the inputStr is being converted into a list of tokens.
     * @return a string
     */
    public String getStringRep() {
        switch (charRep) {
            case '^': return "\u2227";
            case 'V': return "\u2228";
            case '>': return "\u2192";
            case '=': return "\u2194";
            case '~': return "\u00AC";
            default: return Character.toString(charRep);
        }
    }

    /**
     * Check if the token is a variable
     * @param token the token to check
     * @return true or false
     */
    public static boolean isVar(Token token) {
        switch (token) {
            case AND: case OR: case NOT: case IMPL: case BICOND:
            case L_PAREN: case R_PAREN:
                return false;
            default:
                return true;
        }
    }

    /**
     * Check if the token is an operator
     * @param token the token to check
     * @return true or false
     */
    public static boolean isOperator(Token token) {
        if (isVar(token)) return false;
        switch (token) {
            case L_PAREN:
            case R_PAREN:
                return false;
            default:
                return true;
        }
    }

    /**
     * Check if the token is a connective operator
     * @param token the token to check
     * @return true or false
     */
    public static boolean isConnective(Token token) {
        return (isOperator(token) && token != Token.NOT);
    }


    /**
     * Returns the Token that matches with the char given
     * @param c character to check
     * @return a token
     */
    public static Token charToToken(char c) {
        switch (c) {
            case '~': return NOT;
            case '^': return AND;
            case 'V': return OR;
            case '>': return IMPL;
            case '=': return BICOND;
            case 'P': return VAR_P;
            case 'Q': return VAR_Q;
            case 'R': return VAR_R;
            case 'S': return VAR_S;
            case 'W': return VAR_W;
            case 'U': return VAR_U;
            case 'X': return VAR_X;
            case 'Y': return VAR_Y;
            case '(': return L_PAREN;
            case ')': return R_PAREN;
            default: return UNKNOWN;
        }
    }

}

