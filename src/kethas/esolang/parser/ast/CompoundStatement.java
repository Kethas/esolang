package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * Created by Kethas on 17/04/2017.
 */
public class CompoundStatement extends AST {

    private final List<AST> statements;

    public CompoundStatement(Token token, List<AST> statements) {
        super(token);
        this.statements = statements;
    }

    public List<AST> getStatements() {
        return statements;
    }
}
