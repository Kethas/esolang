package kethas.esolang.interpreter;

import kethas.esolang.parser.BinaryOp;
import kethas.esolang.parser.Num;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Decompiler extends NodeVisitor {

    public void visitNum(Num node) {
        System.out.print(node.getValue());
    }

    public void visitBinaryOp(BinaryOp node) {
        System.out.print("(");
        visitNode(node.getLeft());

        System.out.print(" ");
        System.out.print(node.getToken().value);

        System.out.print(" ");
        visitNode(node.getRight());

        System.out.print(")");
    }

}
