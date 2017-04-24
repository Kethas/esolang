package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * Created by Kethas on 17/04/2017.
 */
public class FuncCall extends AST {

    private final AST func;
    private final List<AST> arguments;

    public FuncCall(Token token, AST func, List<AST> arguments) {
        super(token);
        this.func = func;
        this.arguments = arguments;
    }

    public AST getFunc() {
        return func;
    }

    public List<AST> getArguments() {
        return arguments;
    }
}
