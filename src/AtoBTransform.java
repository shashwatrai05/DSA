public class AtoBTransform {
    public static void main(String[] args) {
        int b=40021;
        int a=100;

        System.out.println(isPossible(a,b));
    }

    public static boolean isPossible(int a, int b) {
        while (b >= a) {
            System.out.println("B is" +b);
            if (b == a) return true;
            if (b % 2 == 0) {
                b = b / 2;
            } else {
                b = b / 10;
            }
        }


        return false;
    }
}
