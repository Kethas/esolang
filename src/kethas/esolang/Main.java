package kethas.esolang;

import kethas.esolang.interpreter.Interpreter;
import kethas.esolang.interpreter.NodeVisitor;
import kethas.esolang.lexer.Lexer;
import kethas.esolang.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        NodeVisitor interpreter = new Interpreter();

        List<String> file = Files.readAllLines(new File("script.fox").toPath());

        Lexer lexer = new Lexer(file);

        Parser parser = new Parser(lexer);

        interpreter.visitNode(parser.parse());
    }

}
