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
                return Word.lt;
                //TO CHECK: Else delete

            case '>':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.ge;
                }
                return Word.gt;

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
                // ... gestire il caso degli identificatori e delle parole chiave //   DONE
                //Handle case where I read a number and I skip keyword check?
                if (Character.isLetter(peek)) {
                    String temp = "";
                    while(Character.isDigit(peek) || Character.isLetter(peek)){
                        temp += peek;
                        readch(br);
                    }
                    if(temp.equals(Word.assign.lexeme))
                        return Word.assign;
                    if(temp.equals(Word.to.lexeme))
                        return Word.to;
                    if(temp.equals(Word.iftok.lexeme))
                        return Word.iftok;
                    if(temp.equals(Word.elsetok.lexeme))
                        return Word.elsetok;
                    if(temp.equals(Word.dotok.lexeme))
                        return Word.dotok;
                    if(temp.equals(Word.fortok.lexeme))
                        return Word.fortok;
                    if(temp.equals(Word.begin.lexeme))
                        return Word.begin;
                    if(temp.equals(Word.end.lexeme))
                        return Word.end;
                    if(temp.equals(Word.print.lexeme))
                        return Word.print;
                    if(temp.equals(Word.read.lexeme))
                        return Word.read;
                    return new Word(Tag.ID, temp);

                    // ... gestire il caso dei numeri ... //              DONE
                } else if (Character.isDigit(peek)) {
                    if(peek == '0'){
                        readch(br);
                        if(Character.isDigit(peek)){
                            System.err.println("Erroneous character"
                                    + " after : : "  + peek );
                            return null;
                        }
                         return new NumberTok(Tag.NUM,"0");
                    }
                    String temp = "" + peek;
                    while(Character.isDigit(peek)){
                        temp += peek;
                        readch(br);
                    }
                    return new NumberTok(Tag.NUM, temp);

                }
                else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
         }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "C:\\Users\\danie\\Documents\\Universita'\\Secondo anno\\LFT\\LFT_Exam\\esempio.txt "; // il percorso del file da leggere
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
