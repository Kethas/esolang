package kethas.esolang.interpreter;

import kethas.esolang.lexer.TokenType;
import kethas.esolang.parser.ast.*;

import java.util.*;

import static kethas.esolang.interpreter.Obj.NULL;

/**
 * Created by Kethas on 14/04/2017.
 * TODO: Fix closures: variables outside of function scope are not available unless part of another function scope.
 */
public class Interpreter extends NodeVisitor{

    public static final ExternalFunction println = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            for (Obj o : args) {
                System.out.print(o.getValue() + " ");
            }
            System.out.println();
            return NULL;
        }
    };

    public static final ExternalFunction readln = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 0)
                println.invoke(args);

            Scanner s = new Scanner(System.in);
            String in = s.nextLine();

            try {
                int i = Integer.parseInt(in);
                return new Obj(i);
            } catch (NumberFormatException e) {
                return new Obj(in);
            }
        }
    };


    private Stack<Map<String, Obj>> stack = new Stack<>();

    public Interpreter() {
        Map<String, Obj> globals = new HashMap<>();

        globals.put("println", new Obj(println));
        globals.put("readln", new Obj(readln));

        stack.push(globals);
    }

    public Obj visitStr(Str node) {
        return new Obj(node.getValue());
    }

    public Obj visitVar(Var node) {
        for (Map<String, Obj> m : stack) {
            if (m.containsKey(node.getName()) && m.get(node.getName()) != NULL) {
                return m.get(node.getName());
            }
        }
        return NULL;
    }

    public Obj visitVarAssign(VarAssign node) {
        Obj prevValue = stack.lastElement().get(node.getVar().getName());

        stack.lastElement().put(node.getVar().getName(), visitNode(node.getValue()));

        return NULL;
    }

    public Obj visitNull(Null node) {
        return NULL;
    }

    public Obj visitFuncDeclaration(FuncDeclaration node) {
        //noinspection unchecked
        return new Obj(new Function((Stack<Map<String, Obj>>) stack.clone(), node));
    }

    public Obj visitFuncCall(FuncCall node) {
        //TODO: Handle non-function calls (String/Integer)

        Obj o = visitNode(node.getFunc());

        if (o.getValue() instanceof Function) {
            Function func = (Function) o.getValue();

            Stack<Map<String, Obj>> temp = stack;

            Map<String, Obj> funcLocals = new HashMap<>();

            Iterator<Var> argumentDec = func.getFuncDeclaration().getArguments().iterator();
            Iterator<AST> arguments = node.getArguments().iterator();

            for (int i = 0; i < func.getFuncDeclaration().getArguments().size(); i++) {
                Var var = argumentDec.next();
                Obj obj = visitNode(arguments.next());

                funcLocals.put(var.getName(), obj);
            }

            //noinspection unchecked
            Stack<Map<String, Obj>> locals = (Stack<Map<String, Obj>>) func.getLocals().clone();

            locals.push(funcLocals);
            stack = locals;

            Obj result;

            try {
                result = visitNode(func.getFuncDeclaration().getStatements());
            } catch (ReturnException e) {
                result = e.getObject();
            }

            stack = temp;

            return result;
        } else if (o.getValue() instanceof ExternalFunction) {

            //FIXED! Oh my god I am SUCH an idiot.

            List<Obj> args = new ArrayList<>();

            for (AST ast : node.getArguments()) {
                Obj arg = visitNode(ast);
                args.add(arg);
            }

            return ((ExternalFunction) o.getValue()).invoke(args);
        } else {
            throw new RuntimeException("Cannot call value " + o.getValue());
        }
    }

    public Obj visitCompoundStatement(CompoundStatement node) {
        Obj result = NULL;
        for (AST ast : node.getStatements()) {
            result = visitNode(ast);

            if (ast instanceof Return)
                throw new ReturnException(result);
        }

        return result;
    }

    public Obj visitReturn(Return node) {
        return visitNode(node.getNode());
    }

    public void visitProgram(Program node) {
        try {
            Obj result;
            for (AST ast : node.getStatements()) {
                result = visitNode(ast);

                if (ast instanceof Return)
                    throw new ReturnException(result);
            }
        } catch (ReturnException e) {
            System.out.println("Program terminated: " + e.getObject().getValue());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public Obj visitNum(Num node){
        return new Obj(node.getValue());
    }

    public Obj visitBinaryOp(BinaryOp node) {
        switch (node.getToken().type) {
            case PLUS:
                return visitNode(node.getLeft()).add(visitNode(node.getRight()));
            case MINUS:
                return visitNode(node.getLeft()).subtract(visitNode(node.getRight()));
            case MUL:
                return visitNode(node.getLeft()).multiply(visitNode(node.getRight()));
            case DIV:
                return visitNode(node.getLeft()).divide(visitNode(node.getRight()));
        }

        return NULL;
    }

    public Obj visitUnaryOp(UnaryOp node){
        Obj result = visitNode(node.getNode());

        if (node.getToken().type == TokenType.MINUS){
            result = result.multiply(new Obj(-1));
        }

        return result;
    }

}
