package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 16/04/2017.
 */
public class UnaryOp extends AST {

    private final AST node;

    public UnaryOp(Token token, AST node) {
        super(token);
        this.node = node;
    }

    public AST getNode() {
        return node;
    }
}
