package Lexer;
import java.io.*;
public class LexerExtended {

    public static int line = 1;
    private char peek = ' ';

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan_extended(BufferedReader br) {


        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r' || peek == '/') {
            if (peek == '\n') line++;
            //Handle comments
            if(peek == '/'){
                readch(br);
                if(peek == '*'){
                    boolean commentOpen = true;
                    while(commentOpen){
                        readch(br);
                        if(peek != '*')
                            continue;
                        readch(br);
                        if(peek != '/')
                            continue;
                        commentOpen = false;
                        peek = ' ';
                    }
                    continue;
                }
                else if(peek == '/'){
                    do{
                        readch(br);
                    }while(peek != '\n');
                    continue;
                }
                return Token.div;
            }
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
                if (Character.isLetter(peek) || peek == '_') {
                    String temp = "";
                    while(Character.isDigit(peek) || Character.isLetter(peek) || peek == '_'){
                        temp += peek;
                        readch(br);
                    }
                    if(temp.charAt(0) == '_' && temp.length() < 2){
                        System.err.println("Identifier can't be only '_'");
                        return null;
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
                    //return identifier
                    return new Word(Tag.ID,temp);


                }
                // ... gestire il caso dei numeri ... //              DONE
                else if (Character.isDigit(peek)) {
                    if(peek == '0'){
                        readch(br);
                        if(Character.isDigit(peek)){
                            System.err.println("Erroneous character"
                                    + " after : : "  + peek );
                            return null;
                        }
                        return new NumberTok(Tag.NUM,"0");
                    }
                    String temp = "";
                    while(peek != ' '){
                        if(!Character.isDigit(peek)){
                            System.err.println("Identifier mustn't start by number");
                            return null;
                        }
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
        LexerExtended lex = new LexerExtended();
        String path = "C:\\Users\\danie\\Documents\\Universita'\\Secondo anno\\LFT\\LFT_Exam\\Lexer\\esempio.txt "; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan_extended(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }

}