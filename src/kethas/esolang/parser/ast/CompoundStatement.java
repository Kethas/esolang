package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * This AST Node represents a Compound Statement, also known as a block.
 * @author Kethas
 */
public class CompoundStatement extends AST {

    private final List<AST> statements;

    /**
     * Constructs a Compound Statement AST node.
     * @param token The token representing this node (usually the opening brace or arrow).
     * @param statements The statements that make up this compound statement.
     */
    public CompoundStatement(Token token, List<AST> statements) {
        super(token);
        this.statements = statements;
    }

    /**
     * @return The statements that make up this compound statement.
     */
    public List<AST> getStatements() {
        return statements;
    }
}
