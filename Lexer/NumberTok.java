package Lexer;
import java.awt.font.NumericShaper;
public class NumberTok extends Token {
    public String lexeme = "";

    //Since all numbers have tag = 256
    public NumberTok(int t, String lexeme){
        super(t);
        this.lexeme = lexeme;
    }
    public String toString(){
        return "<" + tag + ", " + lexeme + ">";
    }
}
