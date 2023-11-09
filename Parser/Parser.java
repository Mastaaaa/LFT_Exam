package Parser;
import Lexer.*;
import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
	throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
	if (look.tag != t)
        error("syntax error, symbol: " + (char)look.tag + " not accepted");
    if (look.tag != Tag.EOF)
        move();
    }

    public void start() {
	expr();
	match(Tag.EOF);
    }

    private void expr() {
	    term();
        exprp();
    }

    private void exprp() {
        if(look == null)
            error("No token for this symbol");
	switch (look.tag) {
	    case '+':
            match('+');
            term();
            exprp();
            break;
        case '-':
            match('-');
            term();
            exprp();
            break;
        default:
            break;
	    }
    }

    private void term() {
        fact();
        termp();
    }

    private void termp() {
        if(look == null)
            error("No token for this symbol");
        switch (look.tag) {
            case '*':
                match('*');
                fact();
                termp();
                break;
            case '/':
                match('/');
                fact();
                termp();
                break;
            default:
                break;
        }
    }

    private void fact() {
        if(look == null)
            error("No token for this symbol");
        switch (look.tag) {
            case '(':
                match('(');
                expr();
                match(')');
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break;
            default:
                error("erroroneus symbol: " +(char)look.tag + " ");
        }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./Parser/ParserTest.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}