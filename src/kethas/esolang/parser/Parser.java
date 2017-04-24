package kethas.esolang.parser;

import kethas.esolang.lexer.Lexer;
import kethas.esolang.lexer.Token;
import kethas.esolang.lexer.TokenType;
import kethas.esolang.parser.ast.*;

import java.util.ArrayList;
import java.util.List;

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
        throw new RuntimeException("Invalid syntax at " + currentToken.line + ":" + currentToken.column + ": '" + currentToken.value + "'");
    }

    private void error(String info) {
        throw new RuntimeException("Invalid syntax at " + currentToken.line + ":" + currentToken.column + ": '" + currentToken.value + "': " + info);
    }

    private void eat(TokenType tokenType) {
        if (currentToken.type.is(tokenType))
            currentToken = lexer.getNextToken();
        else
            error("expecting " + tokenType.getDescription() + ", instead got " + currentToken.type.getDescription());
    }

    private AST factor() {
        Token token = currentToken;
        AST result = new Null(token);
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
        } else if (token.type.is(LPAREN)) {
            eat(LPAREN);
            AST node = expr();
            eat(RPAREN);
            result = node;
        }

        while (currentToken.type.is(LPAREN)) {
            List<AST> arguments = arguments();
            result = new FuncCall(currentToken, result, arguments);
        }

        return result;
    }

    private List<AST> arguments() {
        List<AST> arguments = new ArrayList<>();
        eat(LPAREN);
        if (!currentToken.type.is(RPAREN)) {
            arguments.add(expr());
            while (currentToken.type.is(COMMA)) {
                eat(COMMA);
                arguments.add(expr());
            }
        }
        eat(RPAREN);
        return arguments;
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
        AST result;
        if (currentToken.type.is(RETURN)) {
            Token token = currentToken;
            eat(RETURN);
            result = new Return(token, expr());
        } else if (currentToken.type.is(ID)) {
            if (lexer.peekNextToken().type.is(ASSIGN)) {
                Var var = var();
                eat(ASSIGN);
                AST value = expr();
                result = new VarAssign(var.getToken(), var, value);
            } else {
                result = expr();
            }
        } else {
            result = expr();
        }

        while (currentToken.type.is(SEMI)) {
            eat(SEMI);
        }

        return result;
    }

    private AST _null() {
        Token token = currentToken;
        eat(NULL);
        return new Null(token);
    }

    private AST funcDeclaration() {
        Token token = currentToken;
        eat(FUNCTION);
        List<Var> arguments = new ArrayList<>();
        CompoundStatement compoundStatement;
        if (currentToken.type.is(ID)) {
            arguments.add(var());
            while (currentToken.type.is(COMMA)) {
                eat(COMMA);
                if (currentToken.type.is(ID)) {
                    Var var = var();
                    for (Var v : arguments) {
                        if (var.getName().equals(v.getName())) {
                            error("argument of name '" + var.getName() + "' has already been declared for this function");
                        }
                    }
                    arguments.add(var);
                } else {
                    error();
                }
            }
        }
        if (currentToken.type.is(LCBRACE)) {
            compoundStatement = compoundStatement();
            return new FuncDeclaration(token, compoundStatement, arguments);
        } else if (!currentToken.type.is(LCBRACE)) {
            error();
        }

        return new Null(currentToken);
    }

    private CompoundStatement compoundStatement() {
        eat(LCBRACE);
        List<AST> statements = new ArrayList<>();
        while (!currentToken.type.is(RCBRACE, EOF)) {
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

    private Program program() {
        Program program;
        Token token = currentToken;

        List<AST> statements = new ArrayList<>();

        while (!currentToken.type.is(EOF)) {
            statements.add(statement());
        }

        program = new Program(token, statements);

        return program;
    }

    public AST parse() {
        AST node = program();

        if (!currentToken.type.is(EOF))
            error();

        return node;
    }

}
