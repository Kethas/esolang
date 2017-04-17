package kethas.esolang.interpreter;

import kethas.esolang.lexer.TokenType;
import kethas.esolang.parser.BinaryOp;
import kethas.esolang.parser.Num;
import kethas.esolang.parser.UnaryOp;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Interpreter extends NodeVisitor{

    public Obj visitNum(Num node){
        return new Obj(node.getValue());
    }

    public Obj visitBinaryOp(BinaryOp node){
        try {
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
        } catch (NullPointerException ignored) {
            throw new RuntimeException("Cannot perform binary operation on null object");
        }
        return null;
    }

    public Obj visitUnaryOp(UnaryOp node){
        Obj result = visitNode(node.getNode());

        if (node.getToken().type == TokenType.MINUS){
            result = result.multiply(new Obj(-1));
        }

        return result;
    }

}
