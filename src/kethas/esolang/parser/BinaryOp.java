package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 14/04/2017.
 */
public class BinaryOp extends AST {

    private final AST left, right;

    public BinaryOp(Token token, AST left, AST right) {
        super(token);
        this.left = left;
        this.right = right;
    }

    public AST getLeft() {
        return left;
    }

    public AST getRight() {
        return right;
    }
}
