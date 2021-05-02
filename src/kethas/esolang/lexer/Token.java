package kethas.esolang.lexer;

/**
 * This class represents a token which has a type, a value, and a start position within the text.
 *
 * @author Kethas
 */
public class Token {

    public final TokenType type;
    public final String value;
    public final int line, column;

    Token(TokenType type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    /**
     * Turn this token into a String (prettily)
     * @return A prettified string of this token (excluding position)
     */
    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type.name(), value);
    }
}
