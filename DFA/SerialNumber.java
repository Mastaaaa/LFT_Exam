public class SerialNumber {
    public static boolean scan(String s){
        int i = 0;
        int state = 0;

        while(i < s.length()){
            final char ch = s.charAt(i++);

            switch (state){
                case 0:
                    if(Character.isDigit(ch))
                        state = 1;
                    else
                        state = -1;
                    break;
                case 1:
                    int charToInt = Character.getNumericValue(s.charAt(i-2));

                    if(Character.isDigit(ch))
                        state = 1;
                    else if(isT3(ch)){
                        if(charToInt % 2 == 0)
                            state = -1;
                        else
                            state = 2;
                    }
                    else if(isT2(ch)){
                        if(charToInt % 2 != 0)
                            state = -1;
                        else
                            state = 2;
                    }
                    else
                        state = -1;
                    break;
                case 2:
                    state = T2T3Logic(ch);
                    break;

            }
        }
        return state == 2;
    }

    //Since both T3 and T2 have the same logic I group them in a method
    //(Once I checked that serial number and first surname letter match, everything after must be a Latin letter for both T2,T3)
    private static int T2T3Logic(char ch){
        if(!Character.isLetter(ch))
            return -1;
        return 2;
    }

    //return ture if char belongs to T3 starting letters (from L to Z)
    public static boolean isT3(char ch){
        return ((ch > 75 && ch < 91) || (ch > 107 && ch < 123));
    }

    //return true if char belongs to T2 starting letters(from A to K)
    public static boolean isT2(char ch){
        return ((ch > 64 && ch < 76) || (ch > 96 && ch < 108));
    }
}
