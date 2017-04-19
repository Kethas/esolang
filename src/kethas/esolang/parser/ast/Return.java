package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 17/04/2017.
 */
public class Return extends AST {

    private final AST node;

    public Return(Token token, AST node) {
        super(token);
        if (node != null)
            this.node = node;
        else
            this.node = new Null(token);
    }

    public AST getNode() {
        return node;
    }
}
