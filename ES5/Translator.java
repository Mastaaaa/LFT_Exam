package ES5;
import Lexer.*;
import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;
    boolean read = false;

    public Translator(Lexer l, BufferedReader br) {
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

    public void prog() {
        statlist();
        match(Tag.EOF);
        try {
        	code.toJasmin();
        }
        catch(java.io.IOException e) {
        	System.out.println("IO error\n");
        };
    }

    private void statlist(){
        if(look.tag == Tag.ASSIGN || look.tag == Tag.PRINT || look.tag == Tag.READ|| look.tag == Tag.FOR || look.tag == Tag.IF || look.tag == '{' ){
            stat();
            statlistp();
        }
        else
            error("error in method statlist");
    }

    private void statlistp() {
        if (look.tag == ';') {
            match(';');
            stat();
            statlistp();
        } else if (look.tag == Tag.EOF || look.tag == '}') {

        } else {
            // Errore di sintassi se ci si aspetta un punto e virgola o la fine della lista
            error("Syntax error in statlistp");
        }
    }

    public void stat() {
        switch(look.tag) {
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                match('[');
                expr();
                match(Tag.TO);
                idlist();
                match(']');
                assignlistp();
                statlistp();
                break;
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist(0);
                match(')');
                break;
            case Tag.READ:
                match(Tag.READ);
                match('(');
                read = true;
                code.emit(OpCode.invokestatic,0);
                idlist();
                match(')');
                read = false;
                break;

            case Tag.FOR:
                match(Tag.FOR);
                int ltrue = code.newLabel();
                int lfalse = code.newLabel();
                match('(');

                if(look.tag == Tag.ID){
                    idlist();
                }

                int begin = code.newLabel();
                code.emitLabel(begin);
                bexpr(ltrue);
                code.emit(OpCode.GOto, lfalse);
                code.emitLabel(ltrue);
                match(')');
                match(Tag.DO);
                stat();

                code.emit(OpCode.GOto, begin);
                code.emitLabel(lfalse);
                break;

            case Tag.IF:
                match(Tag.IF);
                match('(');
                int ltrue_if = code.newLabel();
                int lfalse_if = code.newLabel();
                int lend_if = code.newLabel();
                bexpr(ltrue_if);
                code.emit(OpCode.GOto, lfalse_if);
                match(')');
                code.emitLabel(ltrue_if);
                stat();
                code.emit(OpCode.GOto, lend_if);
                code.emitLabel(lfalse_if);
                if(look.tag == Tag.ELSE){
                    match(Tag.ELSE);
                    stat();
                }
                code.emitLabel(lend_if);
                match(Tag.END);
                break;

            case '{':
                match('{');
                statlist();
                match('}');
                break;
        }
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
    private void idlist() {
        if(look.tag == Tag.ID){
        	int id_addr = st.lookupAddress(((Word)look).lexeme);
            if (id_addr == -1) {
                code.emit(OpCode.istore, count);
                id_addr = count;
                st.insert(((Word)look).lexeme, count++);
            }
            String id = ((Word) look).lexeme;
            match(Tag.ID);
            if(look.tag == Tag.INIT) {
                match(Tag.INIT);
                expr();
                match(';');
            }
            idlistp();
    	}
    }
    private void idlistp() {
        if (look.tag == ',') {
            match(',');
            idlist();
        }
    }
    private void bexpr(int ltrue) {
        if (look == Word.eq) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpeq,ltrue);

        } else if (look == Word.ne) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpne,ltrue);

        } else if (look == Word.le) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmple,ltrue);

        } else if (look == Word.ge) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpge,ltrue);

        } else if (look == Word.lt) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmplt,ltrue);

        } else if (look == Word.gt) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpgt,ltrue);

        } else {
            error("Syntax error in bexpr");
        }
    }


    private void expr() {
        switch(look.tag) {
            case Tag.NUM:
                NumberTok nt = (NumberTok)look;
                match(Tag.NUM);
                code.emit(OpCode.ldc, Integer.parseInt(nt.lexeme));
                break;
            case Tag.ID:
                Token m_token = look;
                match(Tag.ID);
                code.emit(OpCode.iload, st.lookupAddress(((Word) m_token).lexeme));
                break;
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                code.emit(OpCode.istore);
                break;
            case '+':
                match('+');
                match('(');
                exprlist(1);
                match(')');
                break;
            case '*':
                match('*');
                match('(');
                exprlist(2);
                match(')');
                break;
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                code.emit(OpCode.istore);
                break;

            default:
                error("Syntax error in expr");
        }
    }

    private void exprlist(int context) {
        expr();
        if (context == 0) {
            code.emit(OpCode.invokestatic, 1);
        }
        exprlistp(context);
    }

    private void exprlistp(int context) {
        if (look.tag == ',') {
            match(',');
            expr();
            if (context == 0) {
                code.emit(OpCode.invokestatic, 1);
            }
            else if(context == 1){
                code.emit(OpCode.iadd);
                code.emit(OpCode.istore);
            }
            else if(context == 2){
                code.emit(OpCode.imul);
                code.emit(OpCode.istore);
            }
            exprlistp(context);
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();

        //directory di lavoro: LFT_Exam
        String path = ".\\ES5\\provaLexer.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            System.out.println(translator.code.instructions);
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }

}
