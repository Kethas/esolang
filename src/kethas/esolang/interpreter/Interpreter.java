package kethas.esolang.interpreter;

import kethas.esolang.lexer.Lexer;
import kethas.esolang.lexer.TokenType;
import kethas.esolang.parser.Parser;
import kethas.esolang.parser.ast.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.*;

import static kethas.esolang.interpreter.Obj.NULL;

/**
 * This class visits every node in the AST and executes it accordingly.
 * @author Kethas
 */
public class Interpreter extends NodeVisitor {

    /**
     * This external function prints out (to stdout) its arguments separated by a single space and followed by a newline.
     */
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

    /**
     * This external function will println its arguments if there are any and then block until the user has input
     * a line of text to stdin.
     */
    public static final ExternalFunction readln = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 0)
                println.invoke(args);

            Scanner s = new Scanner(System.in);
            String in = s.next();

            try {
                int i = Integer.parseInt(in);
                return new Obj(i);
            } catch (NumberFormatException e) {
                return new Obj(in);
            }
        }
    };

    /**
     * This external function returns the contents (in the form of a string) of the file specified by the string path in the first argument
     */
    public static final ExternalFunction file_read = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 1)
                return NULL;

            Obj o = args.get(0);
            if (o.getValue() instanceof String) {
                File f = new File((String) o.getValue());
                try {
                    String contents = new String(Files.readAllBytes(f.toPath()));
                    return new Obj(contents);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return NULL;
        }
    };

    /**
     * This external function writes (overwrites, if necessary) the given contents (arg 1) to the file specified by the string path (arg 0).
     */
    public static final ExternalFunction file_write = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 2) {
                return NULL;
            }

            Obj path = args.get(0);
            Obj contents = args.get(1);

            if (path.getValue() instanceof String) {
                File f = new File((String) path.getValue());
                if (f.exists()) f.delete();

                try {
                    File d = f.getParentFile();
                    if (d != null) d.mkdirs();
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (f.isFile()) {
                    try {
                        Files.write(f.toPath(), contents.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            return NULL;
        }
    };

    /**
     * This external function appends the contents (arg 1) to the end of the file specified by the string path (arg 0).
     */
    public static final ExternalFunction file_append = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 2) {
                return NULL;
            }

            Obj path = args.get(0);
            Obj contents = args.get(1);

            if (path.getValue() instanceof String) {
                File f = new File((String) path.getValue());
                if (!f.exists()) {
                    try {
                        File d = f.getParentFile();
                        if (d != null) d.mkdirs();
                        f.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (f.isFile()) {
                    try {
                       Files.write(f.toPath(), contents.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            return NULL;
        }
    };

    /**
     * This external function returns the char (as a number) at a given index (arg 1) of a string (arg 0).
     */
    public static final ExternalFunction charAt = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 2)
                return NULL;

            if (args.get(1).getValue() instanceof Integer) {
                return new Obj(args.get(0).toString().charAt((Integer) args.get(1).getValue()));
            }

            return NULL;
        }
    };

    /**
     * This external function attempts to convert its sole argument into an integer.
     */
    public static final ExternalFunction toint = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 1)
                return NULL;

            Obj o = args.get(0);

            if (o.getValue() instanceof Integer) {
                return new Obj(o.getValue());
            } else if (o.getValue() instanceof String) {
                try {
                    return new Obj(Integer.parseInt(o.toString()));
                } catch (NumberFormatException e) {
                    return NULL;
                }
            }
            return NULL;
        }
    };

    /**
     * This external function returns the name of the type of its sole argument (as a string).
     */
    public static final ExternalFunction typeof = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            if (args.size() != 1)
                return NULL;

            Obj o = args.get(0);

            if (o.getValue() == null) {
                return new Obj("null");
            }

            if (o.getValue() instanceof Integer) {
                return new Obj("int");
            } else if (o.getValue() instanceof String) {
                return new Obj("string");
            } else if (o.getValue() instanceof Function || o.getValue() instanceof ExternalFunction) {
                return new Obj("function");
            }

            return NULL;
        }
    };


    public static final ExternalFunction _args = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            return NULL;
        }
    };

    public static final ExternalFunction __printstack = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            return NULL;
        }
    };

    public static final ExternalFunction __printstacktrace = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            return NULL;
        }
    };

    public static final ExternalFunction require = new ExternalFunction() {
        @Override
        public Obj invoke(List<Obj> args) {
            return null;
        }
    };

    private String path;

    private Stack<Map<String, Obj>> stack = new Stack<>();
    private Stack<AST> stackTrace = new Stack<>();
    private String[] args;

    public Interpreter(String[] args) {
        this.args = args;

        Map<String, Obj> globals = new HashMap<>();

        globals.put("_println", new Obj(println, true));
        globals.put("_readln", new Obj(readln, true));
        globals.put("_args", new Obj(_args, true));
        globals.put("println", new Obj(println));
        globals.put("readln", new Obj(readln));
        globals.put("charat", new Obj(charAt, true));
        globals.put("toint", new Obj(toint, true));
        globals.put("typeof", new Obj(typeof, true));
        globals.put("require", new Obj(require, true));
        globals.put("file_read", new Obj(file_read, true));
        globals.put("file_write", new Obj(file_write, true));
        globals.put("file_append", new Obj(file_append, true));

        stack.push(globals);
    }

    /**
     * Attempts to look up a variable in the current scope, and if it is not found, in the parent scopes.
     * @param name The name of the variable to look up.
     * @return The Obj representing the variable.
     */
    public Obj lookup(String name) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            Map<String, Obj> m = stack.get(i);
            if (m.containsKey(name) && m.get(name) != null)
                return m.get(name);
        }
        return NULL;
    }

    /**
     * Attempts to set a named value. This function will try to find the variable in the current scope, and if it is not found, in the parent scopes.
     * @param name The name of the variable to set.
     * @param value The value to set it the variable to.
     */
    public void setObj(String name, Object value) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            Map<String, Obj> m = stack.get(i);
            if (m.containsKey(name) && m.get(name) != null) {
                m.put(name, new Obj(value));
            }
        }
        stack.lastElement().put(name, new Obj(value));
    }

    private void printStack() {
        int level = 0;
        for (Map<String, Obj> m : stack) {
            System.out.println("--------------------------------");
            System.out.println("level " + level);
            for (Map.Entry<String, Obj> e : m.entrySet()) {
                System.out.println(e.getKey() + ": " + e.getValue().getValue());
            }
            System.out.println("--------------------------------");

            level++;
        }
        System.out.println("\n\n\n");
    }

    private void printStackTrace() {
        //print cause
        printStackTraceAST(stackTrace.lastElement());
        System.out.println(" caused by:");

        for (int i = stackTrace.size() - 2; i >= 0; i--) {
            System.out.print("\t");
            printStackTraceAST(stackTrace.get(i));
            System.out.println();
        }
    }

    private void printStackTraceAST(AST ast) {
        if (ast instanceof FuncCall) {
            System.out.print("[" + ast.getToken().line + ":" + ast.getToken().column + "] '" + ast.getToken().value + "'");
        }
    }

    public Obj visitStr(Str node) {
        return new Obj(node.getValue());
    }

    public Obj visitVar(Var node) {
        return lookup(node.getName());
    }

    public Obj visitVarAssign(VarAssign node) {
        Obj newValue = visitNode(node.getValue());

        Obj prevValue = lookup(node.getVar().getName());

        if (prevValue != NULL) {
            if (prevValue.isReference()) {
                prevValue.setValue(newValue.getValue());
            } else if (!prevValue.isConstant()) {
                setObj(node.getVar().getName(), newValue.getValue());
            } else {
                //except when exceptions are implemented.
            }
        } else {
            setObj(node.getVar().getName(), newValue.getValue());
        }

        Obj value = lookup(node.getVar().getName());

        return value;
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

        stackTrace.push(node);

        if (o.getValue() instanceof Function) {
            Function func = (Function) o.getValue();

            Stack<Map<String, Obj>> temp = stack;

            Map<String, Obj> funcLocals = new HashMap<>();

            Iterator<Var> argumentDec = func.getFuncDeclaration().getArguments().iterator();
            Iterator<AST> arguments = node.getArguments().iterator();

            for (int i = 0; i < func.getFuncDeclaration().getArguments().size(); i++) {
                Var var = argumentDec.next();
                Obj obj;

                try {
                    obj = visitNode(arguments.next());
                } catch (ReturnException e) {
                    obj = e.getObject();
                } catch (NoSuchElementException e) {
                    obj = NULL;
                }

                obj.setReference(true);

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

            for (Var var : func.getFuncDeclaration().getArguments()) {
                stack.lastElement().get(var.getName()).setReference(false);
            }

            stack = temp;


            return result;
        } else if (o.getValue() instanceof ExternalFunction) {
            if (o.getValue() == __printstack) {
                printStack();
                return NULL;
            } else if (o.getValue() == __printstacktrace) {
                printStackTrace();
                return NULL;
            }
            //FIXED! Oh my god I am SUCH an idiot.

            List<Obj> args = new ArrayList<>();

            for (AST ast : node.getArguments()) {
                Obj arg = visitNode(ast);
                arg.setReference(true);
                args.add(arg);
            }

            Obj result = ((ExternalFunction) o.getValue()).invoke(args);

            if (o.getValue() == _args) {
                if (args.size() == 1) {
                    if (args.get(0).getValue() instanceof Integer)
                        result = new Obj(this.args[(int) args.get(0).getValue()]);
                }
            } else if (o.getValue() == require) {
                if (args.size() == 1) {
                    String p = path;
                    File f = new File(new File(p).getParentFile(), args.get(0).toString());

                    List<String> contents = null;

                    try {
                        contents = Files.readAllLines(f.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    assert contents != null;

                    setPath(path);

                    Lexer lexer = new Lexer(contents);

                    Parser parser = new Parser(lexer);

                    result = visitNode(parser.parse());

                    path = p;
                }
            }

            for (Obj arg : args) {
                arg.setReference(false);
            }

            return result;
        } else if (o.getValue() instanceof String) {
            List<Object> args = new ArrayList<>();

            for (AST ast : node.getArguments()) {
                Obj arg = visitNode(ast);
                args.add(arg.getValue());
            }

            String formatted = MessageFormat.format((String) o.getValue(), args.toArray());

            return new Obj(formatted);
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

    public Obj visitIf(If node) {
        Obj condition = visitNode(node.getCondition());

        if (condition.isTruthy()) {
            return visitNode(node.getStatements());
        } else {
            for (ElseIf elseIf : node.getElseIfs()) {
                if (visitNode(elseIf.getCondition()).isTruthy()) {
                    return visitNode(elseIf.getStatements());
                }
            }

            if (node.getElse() != null) {
                return visitNode(node.getElse().getStatements());
            }
        }
        return NULL;
    }

    public Obj visitProgram(Program node) {
        try {
            Obj result;
            for (AST ast : node.getStatements()) {
                result = visitNode(ast);

                if (ast instanceof Return)
                    throw new ReturnException(result);
            }
        } catch (ReturnException e) {
            return e.getObject();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return NULL;
    }

    public Obj visitNum(Num node) {
        return new Obj(node.getValue());
    }

    public Obj visitBinaryOp(BinaryOp node) {
        Obj left, right;

        switch (node.getToken().type) {
            case PLUS:
                return visitNode(node.getLeft()).add(visitNode(node.getRight()));
            case MINUS:
                return visitNode(node.getLeft()).subtract(visitNode(node.getRight()));
            case MUL:
                return visitNode(node.getLeft()).multiply(visitNode(node.getRight()));
            case DIV:
                return visitNode(node.getLeft()).divide(visitNode(node.getRight()));
            case AND:
                left = visitNode(node.getLeft());
                if (left.isTruthy()) {
                    if ((right = visitNode(node.getRight())).isTruthy())
                        return new Obj(1);
                }

                return new Obj(0);
            case NAND:
                left = visitNode(node.getLeft());
                if (!left.isTruthy()) {
                    if (!(right = visitNode(node.getRight())).isTruthy())
                        return new Obj(1);
                }

                return new Obj(0);
            case OR:
                left = visitNode(node.getLeft());

                if (left.isTruthy()) {
                    return left;
                } else {
                    right = visitNode(node.getRight());
                    if (right.isTruthy()) {
                        return right;
                    } else {
                        return left;
                    }
                }
            case NOR:
                left = visitNode(node.getLeft());

                if (!left.isTruthy()) {
                    return left;
                } else {
                    right = visitNode(node.getRight());
                    if (!right.isTruthy()) {
                        return right;
                    } else {
                        return left;
                    }
                }
            case EQUALS:
                left = visitNode(node.getLeft());
                right = visitNode(node.getRight());

                return left.getValue().equals(right.getValue()) ? new Obj(1) : new Obj(0);
            case NOT:
                left = visitNode(node.getLeft());
                right = visitNode(node.getRight());

                return !left.getValue().equals(right.getValue()) ? new Obj(1) : new Obj(0);
            case LABRACKET:
                left = visitNode(node.getLeft());
                right = visitNode(node.getRight());

                if (left.getValue() instanceof Integer && right.getValue() instanceof Integer) {
                    return (Integer) left.getValue() < (Integer) right.getValue() ? new Obj(1) : new Obj(0);
                }
                return NULL;
            case RABRACKET:
                left = visitNode(node.getLeft());
                right = visitNode(node.getRight());

                if (left.getValue() instanceof Integer && right.getValue() instanceof Integer) {
                    return (Integer) left.getValue() > (Integer) right.getValue() ? new Obj(1) : new Obj(0);
                }
                return NULL;
        }

        return NULL;
    }

    public Obj visitUnaryOp(UnaryOp node) {
        Obj result = visitNode(node.getNode());

        if (node.getToken().type.is(TokenType.MINUS)) {
            result = result.multiply(new Obj(-1));
        } else if (node.getToken().type.is(TokenType.NOT)) {
            boolean truthy = result.isTruthy();
            if (truthy)
                result = new Obj(0);
            else
                result = new Obj(1);
        }


        return result;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
