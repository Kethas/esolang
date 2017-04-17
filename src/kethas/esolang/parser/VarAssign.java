package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 17/04/2017.
 */
public class VarAssign extends AST{

    private final Var var;

    private final AST value;

    public VarAssign(Token token, Var var, AST value) {
        super(token);
        this.var = var;
        this.value = value;
    }


    public Var getVar() {
        return var;
    }

    public AST getValue() {
        return value;
    }
}
