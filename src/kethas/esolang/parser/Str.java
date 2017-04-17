package kethas.esolang.parser;

import kethas.esolang.lexer.Token;

/**
 * Created by Kethas on 17/04/2017.
 */
public class Str extends AST{


    public Str(Token token) {
        super(token);
    }

    public String getValue(){
        return getToken().value;
    }
}
