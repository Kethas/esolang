package kethas.esolang;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Kethas on 14/04/2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length > 0)
            Fox.interpret(new File(args[0]), Arrays.copyOfRange(args, 1, args.length));
    }

}
