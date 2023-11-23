package Parser;
import Lexer.*;
import java.io.*;

public class ProgramParser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public ProgramParser(Lexer l, BufferedReader br) {
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
            error("Match error, symbol: " + (char) look.tag + " not accepted");
        if (look.tag != Tag.EOF)
            move();
    }

    public void prog(){
        statlist();
        match(Tag.EOF);
    }

    public void statlist(){
        stat();
        statlistp();
    }

    public void stat(){
        switch (look.tag){
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                assignlist();
                break;
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist();
                match(')');
                break;
            case Tag.READ:
                match(Tag.READ);
                match('(');
                idlist();
                match(')');
                break;
            case Tag.FOR:
                match(Tag.FOR);
                match('(');
                if(look.tag == Tag.ID){
                    match(Tag.ID);
                    match(Tag.INIT);
                    expr();
                    match(Token.semicolon.tag);
                    bexpr();
                    match(')');
                    match(Tag.DO);
                    stat();
                    break;
                }
                else{
                    bexpr();
                    match(')');
                    match(Tag.DO);
                    stat();
                    break;
                }
            case Tag.IF:
                match(Tag.IF);
                match('(');
                bexpr();
                match(')');
                stat();
                if(look.tag == Tag.ELSE) {
                    match(Tag.ELSE);
                    stat();
                    match(Tag.END);
                    break;
                }
                else{
                    match(Tag.END);
                    break;
                }
            default:
                if(look.tag == '{') {
                    match('{');
                    statlist();
                    match('}');
                    break;
                }
                error("error in stat: no stat expression");
        }
    }

    public void statlistp(){
        if(look.tag == Token.semicolon.tag){
            match(Token.semicolon.tag);
            stat();
            statlistp();
        }
    }

    public void assignlist(){
        match('[');
        expr();
        match(Tag.TO);
        idlist();
        match(']');
        assignlistp();
    }

    public void assignlistp(){
        if(look.tag == '[') {
            match('[');
            expr();
            match(Tag.TO);
            idlist();
            match(']');
            assignlistp();
        }
    }

    public void idlist(){
        match(Tag.ID);
        idlistp();
    }

    public void idlistp(){
        if(look.tag == Token.comma.tag){
            match(',');
            match(Tag.ID);
            idlistp();
        }
    }

    public void bexpr(){
        match(Tag.RELOP);
        expr();
        expr();
    }

    public void expr(){
        switch (look.tag){
            case '+':
                match('+');
                match('(');
                exprlist();
                match(')');
                break;
            case '-':
                match('-');
                expr();
                expr();
                break;
            case '*':
                match('*');
                match('(');
                exprlist();
                match(')');
                break;
            case '/':
                match('+');
                expr();
                expr();
                break;
            case Tag.ID:
                match(Tag.ID);
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break;
            default:
                error("error in expr: not a valid expression");
        }
    }

    public void exprlist(){
        expr();
        exprlistp();
    }

    public void exprlistp(){
        if(look.tag == Token.comma.tag){
            match(',');
            expr();
            exprlistp();
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "./Parser/ProgramParserTest.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            ProgramParser programParser = new ProgramParser(lex, br);
            programParser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
