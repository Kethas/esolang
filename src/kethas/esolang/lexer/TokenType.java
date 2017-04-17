package kethas.esolang.lexer;

/**
 * Created by Kethas on 14/04/2017.
 */
public enum TokenType {
    INTEGER,
    STRING,
    ID,
    PLUS,
    MINUS,
    MUL,
    DIV,
    LPAREN,
    RPAREN,
    DOLLAR,
    ASSIGN,
    FUNCTION,
    COMMA,
    COLON,
    END,
    RETURN,
    SEMI,
    MAIN,
    NULL,
    EOF;

    public boolean is(TokenType... others){
        for (TokenType type : others){
            if (this == type){
                return true;
            }
        }

        return false;
    }

}
