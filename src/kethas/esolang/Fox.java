package kethas.esolang;

import kethas.esolang.interpreter.Interpreter;
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

    public static Obj interpret(List<String> contents, String path, String... args) {
        Interpreter interpreter = new Interpreter(args);
        interpreter.setPath(path);

        Lexer lexer = new Lexer(contents);

        Parser parser = new Parser(lexer);

        return interpreter.visitNode(parser.parse());
    }

    public static Obj interpret(File file, String... args) {
        return interpret(file.toPath(), args);
    }

    public static Obj interpret(Path file, String... args) {


        List<String> contents = null;

        try {
            contents = Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert contents != null;

        return interpret(contents, file.toString(), args);
    }

}
