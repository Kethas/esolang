package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 17/04/2017.
 */
public class Var extends AST {

    public Var(Token token) {
        super(token);
    }

    public String getName(){
        return getToken().value;
    }

}
