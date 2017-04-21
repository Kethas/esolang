package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.Set;

/**
 * Created by Kethas on 17/04/2017.
 */
public class FuncCall extends AST {

    private final AST func;
    private final Set<AST> arguments;

    public FuncCall(Token token, AST func, Set<AST> arguments) {
        super(token);
        this.func = func;
        this.arguments = arguments;
    }

    public AST getFunc() {
        return func;
    }

    public Set<AST> getArguments() {
        return arguments;
    }
}
