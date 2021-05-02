package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents a String literal.
 * @author Kethas
 */
public class Str extends AST {

    /**
     * Constructs a String literal AST Node.
     * The string value of the token given will be used as the value of the node.
     * @param token The token representing the integer.
     */
    public Str(Token token) {
        super(token);
    }

    /**
     * @return The String value of the literal.
     */
    public String getValue() {
        return getToken().value;
    }
}
