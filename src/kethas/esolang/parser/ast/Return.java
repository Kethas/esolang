package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents a Return statement.
 * @author Kethas
 */
public class Return extends AST {

    private final AST node;

    /**
     * Constructs a Return node.
     * @param token The token representing this node.
     * @param node A node representing the expression to be evaluated to get the value that is to be returned.
     */
    public Return(Token token, AST node) {
        super(token);
        if (node != null)
            this.node = node;
        else
            this.node = new Null(token);
    }

    /**
     * @return A node representing the expression to be evaluated to get the value that is to be returned.
     */
    public AST getNode() {
        return node;
    }
}
