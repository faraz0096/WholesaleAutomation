import java.util.Scanner;

public class palindrom {

    public static void main(String[] args) {

       String original , reverse="";

       Scanner in = new Scanner(System.in);

       original = in.nextLine();

       for(int i = original.length()-1 ; i>=0 ; i--)

           reverse = reverse + original.charAt(i);

           if(original.equals(reverse)){

               System.out.println("Palindrom " + reverse);
           }else {

               System.out.println("not palindrom");
           }


    }
}
