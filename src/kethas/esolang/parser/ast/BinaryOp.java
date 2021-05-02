package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents a Binary Operation.
 * @author Kethas
 */
public class BinaryOp extends AST {

    private final AST left, right;

    /**
     * Constructs a Binary Operation AST Node.
     * @param token The token representing the type of the operation itself.
     * @param left The left operand of this binary operation.
     * @param right The right operand of this binary operation.
     */
    public BinaryOp(Token token, AST left, AST right) {
        super(token);
        this.left = left;
        this.right = right;
    }

    /**
     * @return The left operand of this binary operation.
     */
    public AST getLeft() {
        return left;
    }

    /**
     * @return The right operand of this binary operation.
     */
    public AST getRight() {
        return right;
    }
}
