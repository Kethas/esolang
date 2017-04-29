package kethas.esolang.interpreter;

import kethas.esolang.parser.ast.AST;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Kethas on 14/04/2017.
 */
public class NodeVisitor {

    public final Obj visitNode(AST node) {
        Class<? extends NodeVisitor> clazz = getClass();
        Class<? extends AST> nodeClass = node.getClass();
        String methodName = String.format("visit%s", nodeClass.getSimpleName());

        try {
            Method method = clazz.getMethod(methodName, nodeClass);
            return (Obj) method.invoke(this, node);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof ReturnException)
                throw (ReturnException) e.getTargetException();
            else
                e.getTargetException().printStackTrace();
        }
        return new Obj(null);
    }

}
