package kethas.esolang.lexer;

/**
 * Created by Kethas on 01/04/2017.
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

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type.name(), value);
    }
}
