/**
 *
 * @author Richard Luong
 * Enum used to identify whether an expr put into isValidExpr() in Parser was valid or not. Messages included to
 * state why the expression wasn't valid
 */
public enum ValidExprStatus {
    VALID_EXPR("Valid"),
    EMPTY_EXPR("Invalid: No expression to generate a truth table from"),
    UNBALANCED_PARENTHESES("Invalid: Parentheses are unbalanced"),
    ADJACENT_OPERATORS("Invalid: Two operators founds adjacent to each other excluding(2 NOT operators)"),
    ADJACENT_PARENTHESES("Invalid: A pair of parentheses found adjacent to each other ()"),
    ADJACENT_VARIABLES("Invalid: Two variables found adjacent to each other"),
    LHS_OR_RHS_OF_OPERATOR_INVALID("Invalid: LHS or RHS of one of the operators is not a valid sub expression");

    private String msg;

    /**
     * Constructor
     * @param msg Message stating why the expression was invalid
     */
    ValidExprStatus(String msg) {
        this.msg = msg;
    }

    /**
     * @return Message stating why the expression was invalid
     */
    public String getMsg() {
        return msg;
    }
}
