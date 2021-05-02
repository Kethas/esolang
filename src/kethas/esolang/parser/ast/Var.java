package kethas.esolang.parser.ast;

import kethas.esolang.lexer.Token;

/**
 * This AST Node represents a Variable Name.
 * @author Kethas
 */
public class Var extends AST {

    /**
     * Constructs a Variable Name AST Node.
     * The value of this node is equivalent to the value of its token.
     * @param token The token representing the variable name.
     */
    public Var(Token token) {
        super(token);
    }

    /**
     * @return The name of the variable.
     */
    public String getName() {
        return getToken().value;
    }

}
