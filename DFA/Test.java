import java.util.Scanner;
public class Test {
    public static void main(String[] args){

        while(true) {
            Scanner scan = new Scanner(System.in);
            String s = scan.nextLine();

            //Test for NoThreeZero                               WORKS
            //System.out.println(NoThreeZero.scan(s));

            //Test for JavaIdentifier                            WORKS
            //System.out.println(JavaIdentifier.scan(s));

            //Test for SerialNumber                              WORKS
            //System.out.println(SerialNumber.scan(s));

            //Test for ConstantIdentifier                        WORKS
            //System.out.println(ConstantIdentifier.scan(s));

            //Test for CommentIdentifier                         Works
            //System.out.println(CommentIdentifier.scan(s));

            //Test for CommentIdentifierExtended                 Works
            //System.out.println(CommentIdentifierExtended.scan(s));
        }

    }
}
