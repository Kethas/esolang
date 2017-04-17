package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 17/04/2017.
 */
public class FuncDeclaration extends AST {

    private final CompoundStatement statements;

    public FuncDeclaration(Token token, CompoundStatement statements) {
        super(token);
        this.statements = statements;
    }

    public CompoundStatement getStatements() {
        return statements;
    }
}
