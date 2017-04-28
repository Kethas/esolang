package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * Created by kethas on 4/27/17.
 */
public class Else extends AST {

    private final CompoundStatement statements;

    public Else(Token token, CompoundStatement statements) {
        super(token);
        this.statements = statements;
    }

    public CompoundStatement getStatements() {
        return statements;
    }
}
