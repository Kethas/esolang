package kethas.esolang.lexer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kethas on 01/04/2017.
 */
public class Lexer {

    private final String text;
    private final Map<String, TokenType> keyWords = new HashMap<>();
    private int pos;
    private char currentChar;

    public Lexer(String text) {
        this.text = text;
        pos = 0;
        currentChar = pos > text.length() - 1 ? '\0' : text.charAt(pos);

        keyWords.put("lambda", TokenType.FUNCTION);
        keyWords.put("fun", TokenType.FUNCTION);
        keyWords.put("return", TokenType.RETURN);
        keyWords.put("null", TokenType.NULL);
    }

    private int getLine(){
        String t = text.substring(0, pos);
        t = t.replaceAll("[^\\n]", "");
        return t.length() + 1;
    }

    private int getColumn(){
        String t = text.substring(0, pos);
        t = t.substring(t.lastIndexOf('\n') + 1);
        return t.length() + 1;
    }

    private void advance(){
        pos++;
        if (pos > text.length() - 1)
            currentChar = '\0';
        else
            currentChar = text.charAt(pos);
    }

    private void error(){
        throw new RuntimeException("Unexpected character '" + (int) currentChar + "' at " + getColumn() + ":" + getColumn() + "");
    }

    private void skipComment(){
        while (!(currentChar == '\n' || currentChar == '\r'))
            advance();
    }

    private void skipWhitespace(){
        while (currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '\r')
            advance();
    }

    private String integer(){
        String result = "";
        while (Character.isDigit(currentChar)){
            result += currentChar;
            advance();
        }
        return result;
    }

    private String parseEscape(){
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

    private String string(){
        advance();
        String result = "";
        while (currentChar != '"' & !(currentChar == '\n' || currentChar == '\r')) {
            if (currentChar == '\\'){
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

    private Token _id(){
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

    public Token peekNextToken() {
        int p = pos;
        Token token = getNextToken();
        pos = p;
        return token;
    }

    public Token getNextToken(){
        while (currentChar != '\0') {
            if (currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '\r') {
                skipWhitespace();
                continue;
            }

            if (currentChar == '#'){
                skipComment();
                continue;
            }

            if (currentChar == '"'){
                return new Token(TokenType.STRING, string(), getLine(), getColumn());
            }

            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.INTEGER, integer(), getLine(), getColumn());
            }

            if (isAlphabetic() || currentChar == '_')
                return _id();

            if (currentChar == '+') {
                advance();
                return new Token(TokenType.PLUS, "+", getLine(), getColumn());
            } else if (currentChar == '-') {
                advance();
                return new Token(TokenType.MINUS, "-", getLine(), getColumn());
            } else if (currentChar == '*') {
                advance();
                return new Token(TokenType.MUL, "*", getLine(), getColumn());
            } else if (currentChar == '/') {
                advance();
                return new Token(TokenType.DIV, "/", getLine(), getColumn());
            } else if (currentChar == '(') {
                advance();
                return new Token(TokenType.LPAREN, "(", getLine(), getColumn());
            } else if (currentChar == ')') {
                advance();
                return new Token(TokenType.RPAREN, ")", getLine(), getColumn());
            } else if (currentChar == '{') {
                advance();
                return new Token(TokenType.LCBRACE, "{", getLine(), getColumn());
            } else if (currentChar == '}') {
                advance();
                return new Token(TokenType.RCBRACE, "}", getLine(), getColumn());
            } else if (currentChar == 'λ') {
                advance();
                return new Token(TokenType.FUNCTION, "λ", getLine(), getColumn());
            } else if (currentChar == '=') {
                advance();
                return new Token(TokenType.ASSIGN, "=", getLine(), getColumn());
            } else if (currentChar == ',') {
                advance();
                return new Token(TokenType.COMMA, ",", getLine(), getColumn());
            } else if (currentChar == ':') {
                advance();
                return new Token(TokenType.COLON, ":", getLine(), getColumn());
            } else if (currentChar == ';') {
                advance();
                return new Token(TokenType.SEMI, ";", getLine(), getColumn());
            }

            error();
        }
        return new Token(TokenType.EOF, "\\0", getLine(), getColumn());
    }

    private boolean isAlphabetic() {
        return (currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z');
    }

}
