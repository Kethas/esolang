package kethas.esolang.lexer;

/**
 * Created by Kethas on 14/04/2017.
 */
public enum TokenType {
    INTEGER("integer constant"),
    STRING("string constant"),
    ID("identifier"),
    PLUS("'+'"),
    MINUS("'-'"),
    MUL("'*'"),
    DIV("'/'"),
    LPAREN("'('"),
    RPAREN("')'"),
    DOLLAR("'$'"),
    LCBRACE("'{'"),
    RCBRACE("'}'"),
    ASSIGN("'='"),
    COMMA("','"),
    COLON("':'"),
    RETURN("'return' keyword"),
    SEMI("';'"),
    NULL("'null' keyword"),
    FUNCTION("'fun', 'lambda' or 'λ' keyword"),
    IF("'if' keyword"),
    ELSE("'else' keyword"),
    ELSEIF("'elseif' keyword"),
    NOT("'not' keyword"),
    AND("'and' keyword"),
    OR("'or' keyword"),
    NAND("'nand' keyword"),
    NOR("'nor' keyword"),
    EOF("end of file"), ARROW_R, EQUALS, LABRACKET, RABRACKET;

    private final String description;

    TokenType() {
        description = "NO DESCRIPTION";
    }

    TokenType(String description) {
        this.description = description;
    }

    public boolean is(TokenType... others) {
        for (TokenType type : others) {
            if (this == type) {
                return true;
            }
        }

        return false;
    }


    public String getDescription() {
        return description;
    }
}
