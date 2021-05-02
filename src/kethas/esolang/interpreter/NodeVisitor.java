package kethas.esolang.interpreter;

import kethas.esolang.parser.ast.AST;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A NodeVisitor is a class that has a function that can handle any AST node and produce from it an Obj.
 * The method NodeVisitor#visitNode(AST) will automatically call the appropriately named function using reflection.
 * @author Kethas
 */
public class NodeVisitor {

    /**
     * Visit an AST node by automatically calling its associated function visitX where X is the classname of the node being visited.
     * @param node The node to visit.
     * @return The output of the associated function which visited the node.
     */
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
