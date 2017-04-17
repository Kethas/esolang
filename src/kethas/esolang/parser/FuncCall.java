package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

import java.util.Set;

/**
 * Created by Kethas on 17/04/2017.
 */
public class FuncCall extends AST {

    private final AST func;
    private final Set<AST> parameters;

    public FuncCall(Token token, AST func, Set<AST> parameters) {
        super(token);
        this.func = func;
        this.parameters = parameters;
    }

    public AST getFunc() {
        return func;
    }

    public Set<AST> getParameters() {
        return parameters;
    }
}
