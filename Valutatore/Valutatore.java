package Valutatore;
import Lexer.*;
import java.io.*;


public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) { 
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
        int expr_val = 0;
        expr_val = expr();
        match(Tag.EOF);
        System.out.println(expr_val);
    }

    private int expr() { 
        int term_val, exprp_val = 0;
        term_val = term();
        exprp_val = exprp(term_val);
        return exprp_val;
    }

    private int exprp(int exprp_i) {
	int term_val, exprp_val = 0;
	switch (look.tag) {
	    case '+':
            match('+');
            term_val = term();
            exprp_val = exprp(exprp_i + term_val);
            break;
        case '-':
            match('-');
            term_val = term();
            exprp_val = exprp(exprp_i - term_val);
            break;
        default:
            exprp_val = exprp_i;
	    }
    return exprp_val;
    }

    private int term() {
        int fact_val, termp_val = 0;
        fact_val = fact();
        termp_val = termp(fact_val);
        return termp_val;
    }
    
    private int termp(int termp_i) {
        int fact_val, termp_val = 0;
        switch (look.tag){
            case '*':
                match('*');
                fact_val = fact();
                termp_val = termp(fact_val * termp_i);
                break;
            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val );
                break;
            default:
                termp_val = termp_i;
        }
        return termp_val;
    }
    
    private int fact() {
        int fact_val = 0;
        switch (look.tag){
            case '(':
                match('(');
                fact_val = expr();
                match(')');
                break;
            case Tag.NUM:
                NumberTok num = (NumberTok) look;
                fact_val = Integer.parseInt(num.lexeme);
                match(Tag.NUM);
                break;
            default:
                error("Error in fact()");
        }
    return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./Valutatore/ValutatoreTest.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
