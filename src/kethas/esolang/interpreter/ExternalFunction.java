package kethas.esolang.interpreter;

import java.util.List;

/**
 * Created by kethas on 4/21/17.
 */

public abstract class ExternalFunction {

    public abstract Obj invoke(List<Obj> args);

    @Override
    public String toString() {
        return "external function";
    }
}
