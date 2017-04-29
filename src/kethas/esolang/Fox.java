package kethas.esolang;

import kethas.esolang.interpreter.Interpreter;
import kethas.esolang.interpreter.NodeVisitor;
import kethas.esolang.interpreter.Obj;
import kethas.esolang.lexer.Lexer;
import kethas.esolang.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by kethas on 4/28/17.
 */
public class Fox {

    public static Obj interpret(List<String> contents) {
        NodeVisitor interpreter = new Interpreter();

        Lexer lexer = new Lexer(contents);

        Parser parser = new Parser(lexer);

        return interpreter.visitNode(parser.parse());
    }

    public static Obj interpret(File file) {
        return interpret(file.toPath());
    }

    public static Obj interpret(Path file) {


        List<String> contents = null;

        try {
            contents = Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert contents != null;

        return interpret(contents);
    }

}
