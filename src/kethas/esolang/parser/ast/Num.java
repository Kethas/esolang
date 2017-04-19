package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Num extends AST {

    private final int value;

    public Num(Token token) {
        super(token);
        this.value = Integer.parseInt(token.value);
    }


    public int getValue() {
        return value;
    }
}
