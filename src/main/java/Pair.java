/**
 * @author Richard Luong
 * Object that acts like a tuple to hold 2 variables, a token representing the current operator and the index of it
 * in the token list
 */
class Pair {
    private Token operator;
    private int index;

    /**
     * Constructor
     * @param operator Current operator
     * @param index Position of the operator in the token list
     */
    Pair (Token operator, int index) {
        this.operator = operator;
        this.index = index;
    }

    // Getters

    /**
     * @return The operator
     */
    public Token getOperator() {
        return operator;
    }

    /**
     * @return The position of the operator in the token list
     */
    public int getIndex() {
        return index;
    }

    /**
     * Increment the index of the operator by a specific value
     * @param val Number of times to increment the position of the operator
     */
    public void incrementIndex(int val) {
        index+=val;
    }

};