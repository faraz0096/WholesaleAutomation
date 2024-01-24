import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args){


        List<Float> list = new ArrayList<>();

        String[] amount = {"$10.00" , "$5.00" , "$20.59"};

        for(String s: amount){

            s.replace("$" , "").trim();

            System.out.println(s);
        }

    }
}
