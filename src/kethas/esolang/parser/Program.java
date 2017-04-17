package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

import java.util.Set;

/**
 * Created by Kethas on 17/04/2017.
 */
public class Program extends AST {

    private final ProgramDeclaration main;

    private final Set<ProgramDeclaration> programs;

    public Program(Token token, ProgramDeclaration main, Set<ProgramDeclaration> programs) {
        super(token);
        this.main = main;
        this.programs = programs;
    }

    public ProgramDeclaration getMain() {
        return main;
    }

    public Set<ProgramDeclaration> getPrograms() {
        return programs;
    }
}
