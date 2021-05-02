package kethas.esolang.interpreter;

import java.util.List;

/**
 * This function represents an external function (One which is implemented in Java, not Fox.
 * @author Kethas
 */

public abstract class ExternalFunction {

    public abstract Obj invoke(List<Obj> args);

    @Override
    public String toString() {
        return "external function";
    }
}
