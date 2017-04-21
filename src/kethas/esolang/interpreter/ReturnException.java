package kethas.esolang.interpreter;

/**
 * Created by kethas on 4/21/17.
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
