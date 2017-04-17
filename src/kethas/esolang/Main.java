package kethas.esolang;

import kethas.esolang.interpreter.Interpreter;
import kethas.esolang.interpreter.NodeVisitor;
import kethas.esolang.interpreter.Obj;
import kethas.esolang.lexer.Lexer;
import kethas.esolang.parser.Parser;

import java.util.Scanner;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Main {

    public static void main(String[] args) {
        NodeVisitor interpreter = new Interpreter();

        Scanner s = new Scanner(System.in);

        while (true) {
            String input = s.nextLine();

            Lexer lexer = new Lexer(input);

            Parser parser = new Parser(lexer);

            Obj out = interpreter.visitNode(parser.parse());

            if (out != null)
                System.out.println(out);
        }
    }

}
