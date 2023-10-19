public class JavaIdentifier{
    public static boolean scan(String s){
        int state = 0;
        int i = 0;
        while(state >= 0 && i < s.length()){
            final char ch = s.charAt(i++);
            switch(state){
                case 0:
                    if(Character.isLetter(ch))
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
                    if(Character.isDigit(ch) || Character.isLetter(ch))
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

    private static boolean isAlphabet(char ch){
        return Character.isLetter(ch) || Character.isDigit(ch) || ch == '_';
    }

}
