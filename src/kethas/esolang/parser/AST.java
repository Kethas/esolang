package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 14/04/2017.
 */
public abstract class AST {

    private final Token token;

    protected AST(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
