package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.Set;

/**
 * Created by kethas on 4/19/17.
 */
public class Program extends AST {

    private final Set<AST> statements;

    public Program(Token token, Set<AST> statements) {
        super(token);
        this.statements = statements;
    }

    public Set<AST> getStatements() {
        return statements;
    }
}
