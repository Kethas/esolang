package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents an ElseIf statement.
 * @author Kethas
 */
public class ElseIf extends AST {

    private final AST condition;
    private final CompoundStatement statements;

    /**
     * Constructs an ElseIf AST node.
     * @param token The token representing this node.
     * @param condition The condition for this ElseIf statement.
     * @param statements The body of this ElseIf statement.
     */
    public ElseIf(Token token, AST condition, CompoundStatement statements) {
        super(token);
        this.condition = condition;
        this.statements = statements;
    }

    /**
     * @return The condition for this ElseIf statement.
     */
    public AST getCondition() {
        return condition;
    }

    /**
     * @return The body of this ElseIf statement.
     */
    public CompoundStatement getStatements() {
        return statements;
    }
}
