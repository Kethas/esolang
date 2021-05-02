package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * This AST Node represents an entire Fox program.
 * @author Kethas
 */
public class Program extends AST {

    private final List<AST> statements;

    /**
     * Constructs a Program node.
     * @param token The token representing this node.
     * @param statements The body of the program -- a list of statements to be executed in sequential order.
     */
    public Program(Token token, List<AST> statements) {
        super(token);
        this.statements = statements;
    }

    /**
     * @return The body of the program -- a list of statements to be executed in sequential order.
     */
    public List<AST> getStatements() {
        return statements;
    }
}
