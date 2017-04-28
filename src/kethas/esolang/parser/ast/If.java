package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

import java.util.List;

/**
 * Created by kethas on 4/27/17.
 */
public class If extends AST {

    private final AST condition;
    private final CompoundStatement statements;
    private final List<ElseIf> elseIfs;
    private final Else elseNode;

    public If(Token token, AST condition, CompoundStatement statements, List<ElseIf> elseIfs, Else elseNode) {
        super(token);
        this.condition = condition;
        this.statements = statements;
        this.elseIfs = elseIfs;
        this.elseNode = elseNode;
    }

    public AST getCondition() {
        return condition;
    }

    public CompoundStatement getStatements() {
        return statements;
    }

    public List<ElseIf> getElseIfs() {
        return elseIfs;
    }

    public Else getElse() {
        return elseNode;
    }
}
