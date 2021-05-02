package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents an Else statement.
 * @author Kethas
 */
public class Else extends AST {

    private final CompoundStatement statements;

    /**
     * Constructs an Else statement AST node.
     * @param token The token representing this node.
     * @param statements The body of this Else statement.
     */
    public Else(Token token, CompoundStatement statements) {
        super(token);
        this.statements = statements;
    }

    /**
     * @return The body of this Else statement.
     */
    public CompoundStatement getStatements() {
        return statements;
    }
}
