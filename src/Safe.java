import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Safe {
    static void generateAllBinaryStrings(int n, String current, List<String> result) {
        if (current.length() == n) {
            result.add(current);
            return;
        }
        generateAllBinaryStrings(n, current + "0", result);
        generateAllBinaryStrings(n, current + "1", result);
    }
    static int countMatches(String s1, String s2) {
        int matches = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                matches++;
            }
        }
        return matches;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        String[] attempts = new String[m];
        int[] matches = new int[m];

        for (int i = 0; i < m; i++) {
            attempts[i] = sc.next();
            matches[i] = sc.nextInt();
        }

        List<String> allPossible = new ArrayList<>();
        generateAllBinaryStrings(n, "", allPossible);

        int validCombinations = 0;

        for (String possible : allPossible) {
            boolean isValid = true;

            for (int i = 0; i < m; i++) {
                int matchCount = countMatches(possible, attempts[i]);
                if (matchCount != matches[i]) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                validCombinations++;
            }
        }

        System.out.println(validCombinations);
        sc.close();
    }
}