package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents an Integer literal.
 * @author Kethas
 */
public class Num extends AST {

    private final int value;

    /**
     * Constructs an Integer literal AST Node.
     * The string value of the token given will be used to parse the integer value of the node.
     * @param token The token representing the integer.
     */
    public Num(Token token) {
        super(token);
        this.value = Integer.parseInt(token.value);
    }

    /**
     * @return The Integer value of the literal.
     */
    public int getValue() {
        return value;
    }
}
