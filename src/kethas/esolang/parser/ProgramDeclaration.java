package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 17/04/2017.
 */
public class ProgramDeclaration extends AST {

    private final CompoundStatement statements;

    public ProgramDeclaration(Token token, CompoundStatement statements) {
        super(token);
        this.statements = statements;
    }

    public String getName(){
        return getToken().value;
    }


    public CompoundStatement getStatements() {
        return statements;
    }
}
