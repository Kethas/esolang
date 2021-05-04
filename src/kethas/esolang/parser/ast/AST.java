package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This class is the superclass for any Abstract Syntax Tree Node type.
 * An Abstract Syntax Tree (AST) is a programmatical representation of a Fox program.
 *
 * This specific design of AST fits Fox in its current state because it can both be easily parsed from tokens and easily
 * executed using a NodeVisitor. In the future there will be a NodeVisitor to convert AST into bytecode which would then
 * be executed on its own VM.
 *
 * @author Kethas
 */
public abstract class AST {

    private final Token token;

    /**
     * Constructs the base of an AST node.
     * @param token A token representing the node.
     */
    protected AST(Token token) {
        this.token = token;
    }

    /**
     * @return The token from which the AST started to be parsed.
     */
    public Token getToken() {
        return token;
    }
}
