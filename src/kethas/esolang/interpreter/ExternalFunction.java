package kethas.esolang.interpreter;

import java.util.List;

/**
 * This function represents an external function (One which is implemented in Java, not Fox.
 * To make your own external function, either anonymously implement a new instance of this class, or extend it
 * via your own class.
 * @author Kethas
 */

public abstract class ExternalFunction {

    /**
     * This function invokes the external function using a list of arguments.
     * The function then must return a single Obj as output. Return Obj.NULL if there isn't any output.
     * @param args The arguments the function has been called with.
     * @return The result of this function.
     */
    public abstract Obj invoke(List<Obj> args);

    @Override
    public String toString() {
        return "external function";
    }
}
