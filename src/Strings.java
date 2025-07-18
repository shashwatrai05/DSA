import java.util.Arrays;
import java.util.ArrayList;

public class Strings {
    public static void main(String[] args) {
        // comparison();
        //methods();
       //operators();
        String str = "abddesba";
        System.out.println(isPalindrome(str));
        stringBuilder();
    }

    static void comparison(){
        String a = "Kunal";
        String b = "Kunal";
        String c = a;
        System.out.println(c == a);
        System.out.println(a == b);

        String name1 = new String("Kunal");
        String name2 = new String("Kunal");

        System.out.println(name1 == name2);

        System.out.println(name1.equals(name2));
    }

    static void methods(){
        String name = "Shashwat Rai Hello World";
        System.out.println(Arrays.toString(name.toCharArray()));
        System.out.println(name.toLowerCase());
        System.out.println(name);
        System.out.println(name.indexOf('a'));
        System.out.println("     Kunal   ".strip());
        System.out.println(Arrays.toString(name.split(" ")));
    }

    static void operators(){
        System.out.println('a' + 'b');
        System.out.println("a" + "b");
        System.out.println((char)('a' + 3));

        System.out.println("a" + 1);
        // this is same as after a few steps: "a" + "1"
        // integer will be converted to Integer that will call toString()

        System.out.println("Kunal" + new ArrayList<>());
        System.out.println("Kunal" + 56);
        //String ans = new Integer(56) + "" + new ArrayList<>();
        //System.out.println(ans);
        System.out.println("a" + 'b');
    }

    static boolean isPalindrome(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        str = str.toLowerCase();
        for (int i = 0; i <= str.length() / 2; i++) {
            char start = str.charAt(i);
            char end = str.charAt(str.length() - 1 - i);

            if (start != end) {
                return false;
            }
        }
        return true;
    }

    static void stringBuilder(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            char ch = (char)('a' + i);
            builder.append(ch);
        }

        System.out.println(builder.toString());

        builder.reverse();

        System.out.println(builder);
    }
}
