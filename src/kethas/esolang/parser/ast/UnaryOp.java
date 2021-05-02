package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents a Unary Operation.
 * @author Kethas
 */
public class UnaryOp extends AST {

    private final AST node;

    /**
     * Constructs a Unary Operation AST Node.
     * @param token The token representing the type of the operation itself.
     * @param node The operand of this unary operation.
     */
    public UnaryOp(Token token, AST node) {
        super(token);
        this.node = node;
    }

    /**
     * @return The operand of this unary operation.
     */
    public AST getNode() {
        return node;
    }
}
