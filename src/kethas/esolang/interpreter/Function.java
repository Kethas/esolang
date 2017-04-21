package kethas.esolang.interpreter;

import kethas.esolang.parser.ast.FuncDeclaration;
import kethas.esolang.parser.ast.Var;

import java.util.Map;
import java.util.Stack;

/**
 * Created by kethas on 4/21/17.
 */
public class Function {

    private final Stack<Map<String, Obj>> locals;

    private final FuncDeclaration funcDeclaration;

    public Function(Stack<Map<String, Obj>> locals, FuncDeclaration funcDeclaration) {
        this.locals = locals;
        this.funcDeclaration = funcDeclaration;
    }

    public Stack<Map<String, Obj>> getLocals() {
        return locals;
    }

    public FuncDeclaration getFuncDeclaration() {
        return funcDeclaration;
    }

    @Override
    public String toString() {
        String args = "";
        for (Var var : funcDeclaration.getArguments()) {
            args += var.getName() + ", ";
        }
        args = args.substring(0, args.length() - 2);

        return "λ " + args + " { ... }";
    }
}
