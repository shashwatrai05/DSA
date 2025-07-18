import java.util.Scanner;

public class Marvolo_Gaunt_Ring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long N = sc.nextLong();
        long p = sc.nextLong(), q = sc.nextLong(), r = sc.nextLong();
        long[] a = new long[(int)N];

        for (int i = 0; i < N; i++) {
            a[i] = sc.nextLong();
        }

        long[] prefixMax = new long[(int)N];
        prefixMax[0] = p * a[0];
        for (int i = 1; i < N; i++) {
            prefixMax[i] = Math.max(prefixMax[i-1], p * a[i]);
        }

        long[] suffixMax = new long[(int)N];
        suffixMax[(int)(N-1)] = r * a[(int)(N-1)];
        for (int i = (int)(N-2); i >= 0; i--) {
            suffixMax[i] = Math.max(suffixMax[i+1], r * a[i]);
        }

        long ans = Long.MIN_VALUE;
        for (int j = 0; j < N; j++) {
            ans = Math.max(ans, prefixMax[j] + q * a[j] + suffixMax[j]);
        }

        System.out.println(ans);
        sc.close();
    }
}