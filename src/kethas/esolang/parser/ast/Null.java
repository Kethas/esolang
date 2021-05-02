package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents a Null value literal.
 * @author Kethas
 */
public class Null extends AST {

    /**
     * Constructs a Null literal AST.
     * @param token The token representing this AST.
     */
    public Null(Token token) {
        super(token);
    }

}
