package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents a Variable Assignment operation.
 * @author Kethas
 */
public class VarAssign extends AST {

    private final Var var;

    private final AST value;

    /**
     * Constructs a Variable Assignment operation node.
     * @param token The token representing this node.
     * @param var The name of the variable to assign to.
     * @param value The node representing the expression to evaluate and assign as the value.
     */
    public VarAssign(Token token, Var var, AST value) {
        super(token);
        this.var = var;
        this.value = value;
    }


    /**
     * @return The name of the variable to assign to.
     */
    public Var getVar() {
        return var;
    }

    /**
     * @return The node representing the expression to evaluate and assign as the value.
     */
    public AST getValue() {
        return value;
    }
}
