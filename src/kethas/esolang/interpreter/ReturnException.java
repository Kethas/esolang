package kethas.esolang.interpreter;

/**
 * This class is a hack to allow kethas.esolang.interpreter.Interpreter to easily return values.
 * The reason this class is an exception is that it allows for the return statement to propagate upwards until it is
 * caught by a function call and returned as the value.
 * @author Kethas
 */
public class ReturnException extends RuntimeException {

    private final Obj ret;

    public ReturnException(Obj ret) {
        super("return " + ret.getValue());
        this.ret = ret;
    }

    public Obj getObject() {
        return ret;
    }
}
