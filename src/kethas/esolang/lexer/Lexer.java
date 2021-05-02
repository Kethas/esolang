package kethas.esolang.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This represents a Lexer, which is given a text source in the form of a list of lines, and parses it to produces tokens on demand.
 * @author Kethas
 */
public class Lexer {

    private final List<String> text;
    private final Map<String, TokenType> keyWords = new HashMap<>();

    private int line;
    private int pos;
    private char currentChar;
    private String currentLine;

    /**
     * Construct a new Lexer with a program.
     * @param text The lines of Fox code to lex.
     */
    public Lexer(List<String> text) {
        this.text = text;

        for (int i = 0; i < this.text.size(); i++) {
            this.text.set(i, this.text.get(i) + "\n");
        }

        this.text.add("\n");
        pos = 0;
        line = 0;

        currentLine = text.get(line);

        currentChar = pos > currentLine.length() - 1 ? '\0' : currentLine.charAt(pos);

        keyWords.put("lambda", TokenType.FUNCTION);
        keyWords.put("if", TokenType.IF);
        keyWords.put("else", TokenType.ELSE);
        keyWords.put("elseif", TokenType.ELSEIF);
        keyWords.put("not", TokenType.NOT);
        keyWords.put("and", TokenType.AND);
        keyWords.put("nand", TokenType.NAND);
        keyWords.put("or", TokenType.OR);
        keyWords.put("nor", TokenType.NOR);
        keyWords.put("fun", TokenType.FUNCTION);
        keyWords.put("return", TokenType.RETURN);
        keyWords.put("null", TokenType.NULL);
    }

    private int getLine() {
        return line + 1;
    }

    private int getColumn() {
        return pos + 1;
    }

    private void advance() {
        pos++;
        if (pos > currentLine.length() - 1) {
            line++;
            pos = 0;
            if (line > text.size() - 1) {
                line--;
                currentChar = '\0';
            } else {
                currentLine = text.get(line);
                currentChar = currentLine.charAt(pos);
            }
        } else
            currentChar = currentLine.charAt(pos);
    }

    private void error() {
        throw new RuntimeException("Unexpected character '" + Character.getName(currentChar) + "' at " + getLine() + ":" + getColumn());
    }

    private void skipComment() {
        while (!(currentChar == '\n' || currentChar == '\r'))
            advance();
    }

    private void skipWhitespace() {
        while (currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '\r')
            advance();
    }

    private String integer() {
        String result = "";
        while (Character.isDigit(currentChar)) {
            result += currentChar;
            advance();
        }
        return result;
    }

    private String parseEscape() {
        advance();

        String result = "";
        if (currentChar == '"')
            result += "\"";
        else if (currentChar == 'n')
            result += "\n";
        else if (currentChar == 't')
            result += "\t";
        else if (currentChar == '\\')
            result += "\\";

        advance();
        return result;
    }

    private String string() {
        advance();
        String result = "";
        while (currentChar != '"' && currentChar != '\n' && currentChar != '\r') {
            if (currentChar == '\\') {
                result += parseEscape();
                continue;
            }
            result += currentChar;
            advance();
        }
        if (currentChar != '"')
            error();
        advance();
        return result;
    }

    private Token _id() {
        String id = "";

        while (isAlphabetic() || Character.isDigit(currentChar) || currentChar == '_') {
            id += currentChar;
            advance();
        }

        TokenType tokenType = TokenType.ID;

        if (keyWords.containsKey(id)) {
            tokenType = keyWords.get(id);
        }

        return new Token(tokenType, id, getLine(), getColumn());
    }

    /**
     * This method will attempt to peek at the next token without moving the current position in the text.
     * This operation is somewhat expensive since Lexer#getNextToken() will have to lex the same thing again.
     * @return The next token.
     */
    public Token peekNextToken() {
        int p = pos;
        int l = line;
        Token token = getNextToken();
        line = l;
        currentLine = text.get(line);
        pos = p;
        currentChar = currentLine.charAt(pos);
        return token;
    }

    /**
     * This method lexes the next part of text and returns the token it finds.
     * @return The token lexed.
     */
    public Token getNextToken() {
        while (currentChar != '\0') {
            if (currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '\r') {
                skipWhitespace();
                continue;
            }

            if (currentChar == '#') {
                skipComment();
                continue;
            }

            if (currentChar == '"') {
                return new Token(TokenType.STRING, string(), getLine(), getColumn());
            }

            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.INTEGER, integer(), getLine(), getColumn());
            }

            if (isAlphabetic() || currentChar == '_')
                return _id();

            switch (currentChar) {
                case '+':
                    advance();
                    return new Token(TokenType.PLUS, "+", getLine(), getColumn());
                case '-':
                    advance();
                    if (currentChar == '>') {
                        advance();
                        return new Token(TokenType.ARROW_R, "->", getLine(), getColumn());
                    }
                    return new Token(TokenType.MINUS, "-", getLine(), getColumn());
                case '*':
                    advance();
                    return new Token(TokenType.MUL, "*", getLine(), getColumn());
                case '/':
                    advance();
                    return new Token(TokenType.DIV, "/", getLine(), getColumn());
                case '(':
                    advance();
                    return new Token(TokenType.LPAREN, "(", getLine(), getColumn());
                case ')':
                    advance();
                    return new Token(TokenType.RPAREN, ")", getLine(), getColumn());
                case '{':
                    advance();
                    return new Token(TokenType.LCBRACE, "{", getLine(), getColumn());
                case '}':
                    advance();
                    return new Token(TokenType.RCBRACE, "}", getLine(), getColumn());
                case '\u03BB':
                    advance();
                    return new Token(TokenType.FUNCTION, "λ", getLine(), getColumn());
                case '=':
                    advance();
                    if (currentChar == '=') {
                        advance();
                        return new Token(TokenType.EQUALS, "==", getLine(), getColumn());
                    }
                    return new Token(TokenType.ASSIGN, "=", getLine(), getColumn());
                case ',':
                    advance();
                    return new Token(TokenType.COMMA, ",", getLine(), getColumn());
                case ':':
                    advance();
                    return new Token(TokenType.COLON, ":", getLine(), getColumn());
                case ';':
                    advance();
                    return new Token(TokenType.SEMI, ";", getLine(), getColumn());
                case '<':
                    advance();
                    return new Token(TokenType.LABRACKET, "<", getLine(), getColumn());
                case '>':
                    advance();
                    return new Token(TokenType.RABRACKET, ">", getLine(), getColumn());
            }

            error();
        }
        return new Token(TokenType.EOF, "\\0", getLine(), getColumn());
    }

    private boolean isAlphabetic() {
        return (currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z');
    }

}
