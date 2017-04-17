package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

import java.util.Set;

/**
 * Created by Kethas on 17/04/2017.
 */
public class CompoundStatement extends AST{

    private final Set<AST> statements;

    public CompoundStatement(Token token, Set<AST> statements) {
        super(token);
        this.statements = statements;
    }

    public Set<AST> getStatements() {
        return statements;
    }
}
