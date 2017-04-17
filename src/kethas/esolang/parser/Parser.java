package kethas.esolang.parser;

import kethas.esolang.lexer.Lexer;
import kethas.esolang.lexer.Token;
import kethas.esolang.lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

import static kethas.esolang.lexer.TokenType.*;

/**
 * Created by Kethas on 01/04/2017.
 */
public class Parser {

    private Token currentToken;

    private Lexer lexer;

    public Parser(Lexer lexer){
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    private void error() {
        throw new RuntimeException("Invalid syntax at " + currentToken.line + ":" + currentToken.column + ": " + currentToken.value);
    }

    private void eat(TokenType tokenType) {
        if (currentToken.type.is(tokenType))
            currentToken = lexer.getNextToken();
        else
            error();
    }

    private AST factor() {
        Token token = currentToken;
        if (token.type.is(INTEGER)) {
            eat(INTEGER);
            return new Num(token);
        } else if (token.type.is(STRING)) {
            eat(STRING);
            return new Str(token);
        } else if (token.type.is(LPAREN)) {
            eat(LPAREN);
            AST node = expression();
            eat(RPAREN);
            return node;
        }

        error();
        return null;
    }

    private AST term() {
        AST node = factor();
        while (currentToken.type.is(MUL, DIV)){
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, factor());
        }

        return node;
    }

    private AST expr() {
        AST node = term();
        while (currentToken.type.is(PLUS, MINUS)){
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, term());
        }

        return node;
    }

    private AST expression(){
        AST result = new Null(currentToken);
        if (currentToken.type.is(INTEGER, STRING, PLUS, MINUS, LPAREN))
            result = expr();
        else if (currentToken.type.is(ID))
            result = var();
        else if (currentToken.type.is(NULL))
            return _null();
        else if (currentToken.type.is(DOLLAR))
            result = paramVar();
        else if (currentToken.type.is(FUNCTION))
            return anonFuncDeclaration();
        else
            error();

        if (currentToken.type.is(LPAREN)){
            Set<AST> parameters = new HashSet<>();
            eat(LPAREN);
            if (!currentToken.type.is(RPAREN)) {
                parameters.add(expression());
                while (!currentToken.type.is(RPAREN)) {
                    eat(COMMA);
                    parameters.add(expression());
                }
            }
            eat(RPAREN);
            result = new FuncCall(currentToken, result, parameters);
        }

        return result;
    }

    private AST statement(){
        AST result = new Null(currentToken);
        if(currentToken.type.is(INTEGER, STRING));

        return result;
    }

    private AST paramVar() {
        Token token = currentToken;
        eat(DOLLAR);
        return new Var(token);
    }

    private AST _null() {
        Token token = currentToken;
        eat(NULL);
        return new Null(token);
    }

    private AST anonFuncDeclaration() {


        return null;
    }

    private AST var() {
        Var var = new Var(currentToken);
        eat(ID);
        return var;
    }

    public AST parse() {
        AST node = expr();

        if (!currentToken.type.is(EOF))
            error();

        return node;
    }

}
