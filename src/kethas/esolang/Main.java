package kethas.esolang;

import java.io.File;
import java.io.IOException;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length > 0)
            Fox.interpret(new File(args[0]));
    }

}
