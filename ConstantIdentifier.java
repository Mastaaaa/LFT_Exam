public class ConstantIdentifier {

    public static boolean scan(String s){
        int i = 0;
        int state = 0;
        int dot = 0;
        while(state >= 0 && i < s.length()){
            final char ch = s.charAt(i++);

            switch(state){
                case 0:
                    if(ch == '+' || ch == '-')
                        state = 1;
                    else if(ch == '.'){
                        dot++;
                        state = 2;
                    }
                    else if(Character.isDigit(ch))
                        state = 6;
                    else
                        state = -1;
                    break;
                case 1:
                    if(ch == '.'){
                        dot++;
                        state = 2;
                    }
                    else if(Character.isDigit(ch))
                        state = 6;
                    else
                        state = -1;
                    break;
                case 2:
                    if(Character.isDigit(ch))
                        state = 6;
                    else
                        state = -1;
                    break;
                case 3:
                    if(ch == '+' || ch == '-' || Character.isDigit(ch))
                        state = 4;
                    else
                        state = -1;
                    break;
                case 4:
                    if(ch == '.' && dot != 2){
                        dot++;
                        state = 5;
                    }
                    else if(Character.isDigit(ch))
                        state = 4;
                    else
                        state = -1;
                    break;
                case 5:
                    if(Character.isDigit(ch))
                        state = 4;
                    else
                        state = -1;
                    break;
                case 6:
                    if(Character.isDigit(ch))
                        state = 6;
                    else if(ch == '.' && dot != 1){
                        dot++;
                        state = 2;
                    }
                    else if(ch == 'e')
                        state = 3;
                    else
                        state = -1;
                    break;
            }
        }
        return state == 6 || state == 4;
    }


}
