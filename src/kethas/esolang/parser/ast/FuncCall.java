package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * This AST Node represents a Function Call.
 * @author Kethas
 */
public class FuncCall extends AST {

    private final AST func;
    private final List<AST> arguments;

    /**
     * Construct a Function Call AST Node.
     * @param token The token representing this node.
     * @param func The node representing the expression to be called.
     * @param arguments A list of nodes representing the expressions of the arguments the function will be called with.
     */
    public FuncCall(Token token, AST func, List<AST> arguments) {
        super(token);
        this.func = func;
        this.arguments = arguments;
    }

    /**
     * @return The node representing the expression to be called.
     */
    public AST getFunc() {
        return func;
    }

    /**
     * @return A list of nodes representing the expressions of the arguments the function will be called with.
     */
    public List<AST> getArguments() {
        return arguments;
    }
}
