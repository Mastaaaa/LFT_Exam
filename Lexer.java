import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
            case '(':
                peek = ' ';
                return Token.lpt;
            case ')':
                peek = ' ';
                return Token.rpt;
            case '[':
                peek = ' ';
                return Token.lpq;
            case ']':
                peek = ' ';
                return Token.rpq;
            case '{':
                peek = ' ';
                return Token.lpg;
            case '}':
                peek = ' ';
                return Token.rpg;
            case '+':
                peek = ' ';
                return Token.plus;
            case '-':
                peek = ' ';
                return Token.minus;
            case '*':
                peek = ' ';
                return Token.mult;
            case '/':
                peek = ' ';
                return Token.div;
            case ';':
                peek = ' ';
                return Token.semicolon;
            case ',':
                peek = ' ';
                return Token.comma;

	// ... gestire i casi di ( ) [ ] { } + - * / ; , ... //        DONE
	
            case '&':
                readch(br);
                if (peek != '&') {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }
                peek = ' ';
                return Word.and;
            case '|':
                readch(br);
                if(peek != '|'){
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }
                peek = ' ';
                return Word.or;
            case '<':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.le;
                }
                else if(peek == '>'){
                    peek = ' ';
                    return Word.ne;
                }
                else
                    return Word.lt;
                //Maybe can delete else
            case '>':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.ge;
                }
                else{
                    return Word.gt;
                }
            case '=':
                readch(br);
                if(peek != '='){
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }
                peek = ' ';
                return Word.eq;
            case ':':
                readch(br);
                if(peek != '='){
                    System.err.println("Erroneous character"
                            + " after : : "  + peek );
                    return null;
                }
                peek = ' ';
                return Word.init;

	// ... gestire i casi di || < > <= >= == <> ... //             DONE
          
            case (char)-1:
                return new Token(Tag.EOF);

            default:
                // ... gestire il caso degli identificatori e delle parole chiave //
                if (Character.isLetter(peek)) {
                    String temp = "" + peek;


                } else if (Character.isDigit(peek)) {

	// ... gestire il caso dei numeri ... //

                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
         }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "...path..."; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}
