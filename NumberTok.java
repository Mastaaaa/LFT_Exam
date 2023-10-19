import java.awt.font.NumericShaper;

public class NumberTok extends Token {
    public String lexeme = "";

    //Since all numbers have tag = 256
    public NumberTok(String lexeme){
        super(256);
        this.lexeme = lexeme;
    }
    public String toString(NumberTok token){
        return "<" + tag + ", " + lexeme + ">";
    }
}
