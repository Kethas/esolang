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

    public Parser(Lexer lexer) {
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

    private AST value_base() {
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
        } else if (token.type.is(IF)) {
            result = ifStatement();
        } else if (token.type.is(NULL)) {
            result = _null();
        } else if (token.type.is(LPAREN)) {
            eat(LPAREN);
            AST node = expr();
            eat(RPAREN);
            result = node;
        } else if (token.type.is(PLUS, MINUS, NOT)) {
            eat(token.type);
            AST node = value_base();
            result = new UnaryOp(token, node);
        } else {
            error();
        }

        while (currentToken.type.is(LPAREN)) { //preparing for [] and . operators
            if (currentToken.type.is(LPAREN)) {
                List<AST> arguments = arguments();
                result = new FuncCall(token, result, arguments);
            }
        }

        return result;
    }

    private AST gt_lt_op() {
        AST node = value_base();
        while (currentToken.type.is(LABRACKET, RABRACKET)) {
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, value_base());
        }

        return node;
    }

    private AST equals_op() {
        AST node = gt_lt_op();
        while (currentToken.type.is(EQUALS, NOT)) {
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, gt_lt_op());
        }

        return node;
    }

    private AST binary_and() {
        AST node = equals_op();
        while (currentToken.type.is(AND, NAND)) {
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, equals_op());
        }

        return node;
    }

    private AST binary_or() {
        AST node = binary_and();
        while (currentToken.type.is(OR, NOR)) {
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, binary_and());
        }

        return node;
    }

    private AST term() {
        AST node = binary_or();
        while (currentToken.type.is(MUL, DIV)) {
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, binary_or());
        }

        return node;
    }

    private AST expr() {
        AST node = term();
        while (currentToken.type.is(PLUS, MINUS)) {
            Token token = currentToken;
            eat(currentToken.type);
            node = new BinaryOp(token, node, term());
        }

        return node;
    }

    private AST ifStatement() {
        Token token = currentToken;
        eat(IF);
        AST condition = expr();
        CompoundStatement statements = compoundStatement();
        List<ElseIf> elseIfs = new ArrayList<>();

        Else elseNode = null;

        while (currentToken.type.is(ELSEIF)) {
            Token elseIfToken = currentToken;
            eat(ELSEIF);

            AST elseIfCondition = expr();
            CompoundStatement elseIfStatements = compoundStatement();

            elseIfs.add(new ElseIf(elseIfToken, elseIfCondition, elseIfStatements));
        }

        if (currentToken.type.is(ELSE)) {
            Token elseToken = currentToken;
            eat(ELSE);

            CompoundStatement elseStatements = compoundStatement();

            elseNode = new Else(elseToken, elseStatements);
        }

        return new If(token, condition, statements, elseIfs, elseNode);
    }

    private AST statement() {
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
        } else if (currentToken.type.is(SEMI)) {
            result = new Null(currentToken);
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
        compoundStatement = compoundStatement();
        return new FuncDeclaration(token, compoundStatement, arguments);
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

    private CompoundStatement compoundStatement() {
        if (currentToken.type.is(LCBRACE)) {
            Token token = currentToken;
            eat(LCBRACE);
            List<AST> statements = new ArrayList<>();
            while (!currentToken.type.is(RCBRACE, EOF)) {
                AST s = statement();
                statements.add(s);
            }
            eat(RCBRACE);
            return new CompoundStatement(token, statements);
        } else if (currentToken.type.is(COLON, ARROW_R)) {
            Token token = currentToken;
            eat(currentToken.type);
            List<AST> statement = new ArrayList<>();
            statement.add(statement());
            return new CompoundStatement(token, statement);
        }

        error();
        return new CompoundStatement(currentToken, new ArrayList<>());
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
