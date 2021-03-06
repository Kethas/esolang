package kethas.esolang.interpreter;

import kethas.esolang.parser.ast.FuncDeclaration;
import kethas.esolang.parser.ast.Var;

import java.util.Map;
import java.util.Stack;

/**
 * This class represents a Fox function implemented using a reference to its AST node.
 * @author Kethas
 */
public class Function {

    private final Stack<Map<String, Obj>> locals;

    private final FuncDeclaration funcDeclaration;

    public Function(Stack<Map<String, Obj>> locals, FuncDeclaration funcDeclaration) {
        this.locals = locals;
        this.funcDeclaration = funcDeclaration;
    }

    /**
     * @return A clone of the stack used when declaring this function.
     */
    public Stack<Map<String, Obj>> getLocals() {
        return locals;
    }

    /**
     * @return The original function declaration AST.
     */
    public FuncDeclaration getFuncDeclaration() {
        return funcDeclaration;
    }

    @Override
    public String toString() {
        String args = "";
        for (Var var : funcDeclaration.getArguments()) {
            args += var.getName() + ", ";
        }
        if (!args.isEmpty()) {
            args = args.substring(0, args.length() - 2);
            args += " ";
        }

        return "λ " + args + "{ ... }";
    }
}
