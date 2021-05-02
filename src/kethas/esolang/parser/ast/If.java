package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * This AST node represents an If statement and its associated ElseIfs and Elses.
 * @author Kethas
 */
public class If extends AST {

    private final AST condition;
    private final CompoundStatement statements;
    private final List<ElseIf> elseIfs;
    private final Else elseNode;

    /**
     * Constructs an If statement node.
     * @param token The token representing this node.
     * @param condition The condition of this If statement.
     * @param statements The body of this If statement.
     * @param elseIfs The ElseIf statements associated with this If statement.
     * @param elseNode The Else statement associated with this If statement.
     */
    public If(Token token, AST condition, CompoundStatement statements, List<ElseIf> elseIfs, Else elseNode) {
        super(token);
        this.condition = condition;
        this.statements = statements;
        this.elseIfs = elseIfs;
        this.elseNode = elseNode;
    }

    /**
     * @return The condition of this If statement.
     */
    public AST getCondition() {
        return condition;
    }

    /**
     * @return The body of this If statement.
     */
    public CompoundStatement getStatements() {
        return statements;
    }

    /**
     * @return The ElseIf statements associated with this If statement.
     */
    public List<ElseIf> getElseIfs() {
        return elseIfs;
    }

    /**
     * @return The Else statement associated with this If statement.
     */
    public Else getElse() {
        return elseNode;
    }
}
