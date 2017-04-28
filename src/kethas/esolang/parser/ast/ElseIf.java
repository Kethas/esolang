package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * Created by kethas on 4/27/17.
 */
public class ElseIf extends AST {

    private final AST condition;
    private final CompoundStatement statements;

    public ElseIf(Token token, AST condition, CompoundStatement statements) {
        super(token);
        this.condition = condition;
        this.statements = statements;
    }

    public AST getCondition() {
        return condition;
    }

    public CompoundStatement getStatements() {
        return statements;
    }
}
