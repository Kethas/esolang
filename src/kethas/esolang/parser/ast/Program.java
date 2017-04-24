package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * Created by kethas on 4/19/17.
 */
public class Program extends AST {

    private final List<AST> statements;

    public Program(Token token, List<AST> statements) {
        super(token);
        this.statements = statements;
    }

    public List<AST> getStatements() {
        return statements;
    }
}
