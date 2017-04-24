package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * Created by Kethas on 17/04/2017.
 */
public class FuncDeclaration extends AST {

    private final CompoundStatement statements;

    private final List<Var> arguments;

    public FuncDeclaration(Token token, CompoundStatement statements, List<Var> arguments) {
        super(token);
        this.statements = statements;
        this.arguments = arguments;
    }

    public CompoundStatement getStatements() {
        return statements;
    }

    public List<Var> getArguments() {
        return arguments;
    }
}
