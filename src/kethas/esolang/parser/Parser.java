package kethas.esolang.parser;

import kethas.esolang.lexer.Lexer;
import kethas.esolang.lexer.Token;
import kethas.esolang.lexer.TokenType;
import kethas.esolang.parser.ast.*;

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
        AST result = new Undefined(token);
        if (token.type.is(INTEGER)) {
            eat(INTEGER);
            result = new Num(token);
        } else if (token.type.is(STRING)) {
            eat(STRING);
            result = new Str(token);
        } else if (token.type.is(ID)) {
            result = var();
        } else if (token.type.is(FUNCTION)) {
            result = funcDeclaration();
        } else if (token.type.is(NULL)) {
            result = _null();
        } else if (token.type.is(UNDEFINED)) {
            eat(UNDEFINED);
        } else if (token.type.is(LPAREN)) {
            eat(LPAREN);
            AST node = expr();
            eat(RPAREN);
            result = node;
        }


        return result;
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

    private AST funcDeclaration() {
        eat(FUNCTION);
        Set<Var> arguments = new HashSet<>();
        CompoundStatement compoundStatement;
        if (currentToken.type.is(ID)) {
            arguments.add(var());
            while (currentToken.type.is(COMMA)) {
                eat(COMMA);
                if (currentToken.type.is(ID)) {
                    arguments.add(var());
                } else {
                    error();
                }
            }
        }
        if (currentToken.type.is(LCBRACE)) {
            compoundStatement = compoundStatement();
            return new FuncDeclaration(currentToken, compoundStatement, arguments);
        } else if (!currentToken.type.is(LCBRACE)) {
            error();
        }

        return new Undefined(currentToken);
    }

    private CompoundStatement compoundStatement() {
        eat(LCBRACE);
        Set<AST> statements = new HashSet<>();
        while (!currentToken.type.is(RCBRACE)) {
            statements.add(statement());
        }
        eat(RCBRACE);
        return new CompoundStatement(currentToken, statements);
    }

    private Var var() {
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
