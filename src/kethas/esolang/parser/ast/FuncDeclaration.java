package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.Set;

/**
 * Created by Kethas on 17/04/2017.
 */
public class FuncDeclaration extends AST {

    private final CompoundStatement statements;

    private final Set<Var> arguments;

    public FuncDeclaration(Token token, CompoundStatement statements, Set<Var> arguments) {
        super(token);
        this.statements = statements;
        this.arguments = arguments;
    }

    public CompoundStatement getStatements() {
        return statements;
    }

    public Set<Var> getArguments() {
        return arguments;
    }
}
