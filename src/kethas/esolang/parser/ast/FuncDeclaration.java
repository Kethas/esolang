package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * This AST Node represents an Anonymous Function Declaration
 * @author Kethas
 */
public class FuncDeclaration extends AST {

    private final CompoundStatement statements;

    private final List<Var> arguments;

    /**
     * Constructs an Anonymous Function Declaration AST Node.
     * @param token The token representing this node.
     * @param statements The body of the function.
     * @param arguments A list of names of the arguments.
     */
    public FuncDeclaration(Token token, CompoundStatement statements, List<Var> arguments) {
        super(token);
        this.statements = statements;
        this.arguments = arguments;
    }

    /**
     * @return The body of the function.
     */
    public CompoundStatement getStatements() {
        return statements;
    }

    /**
     * @return A list of names of the arguments.
     */
    public List<Var> getArguments() {
        return arguments;
    }
}
