public class JavaIdentifier{

    public static boolean scan(String s){
        int state = 0;
        int i = 0;
        while(state >= 0 && i < s.length()){
            final char ch = s.charAt(i++);
            //Using char ASCII decimal representation to check if ch is a letter(capital and not) or a number
            switch(state){
                case 0:
                    if(isLetter(ch))
                        state = 1;
                    else if(ch == '_'){
                        state = 2;
                    }
                    else
                        state = -1;
                    break;
                case 1:
                    if(isAlphabet(ch))
                        state = 1;
                    else
                        state = -1;
                    break;
                case 2:
                    if(isNumber(ch) || isLetter(ch))
                        state = 1;
                    else if(ch == '_')
                        state = 2;
                    else
                        state = -1;
                    break;
            }
        }
        return state == 1;
    }

    public static boolean isLetter(char ch){
        return (ch > 64 && ch < 91) || (ch > 96 && ch < 123);
    }

    public static boolean isNumber(char ch){
        return (ch > 47 && ch < 58);
    }

    private static boolean isAlphabet(char ch){
        return isLetter(ch) || isNumber(ch) || ch == '_';
    }

}
