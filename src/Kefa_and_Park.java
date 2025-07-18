import java.util.*;

public class Kefa_and_Park {
    static int n, m; // Number of vertices (n) and max consecutive cats allowed (m)
    static int[] hasCat; // Array to store if a vertex has a cat (1 if cat, 0 if no cat)
    static List<List<Integer>> tree; // Adjacency list for the tree
    static int validRestaurants = 0; // Count of valid restaurants

    // DFS function to traverse the tree
    void dfs(int curr, int par, List<List<Integer>> li, int currcat) {
        // If current node has a cat, increment the consecutive cats count
        if (hasCat[curr] == 1) {
            currcat++;
        } else {
            currcat = 0; // Reset if no cat at current node
        }

        // If the consecutive cats exceed the allowed limit, stop the DFS
        if (currcat > m) {
            return;
        }

        // Check if the current node is a leaf node (no children except its parent)
        boolean isLeaf = true;

        for (int next : li.get(curr)) {
            if (next != par) { // Avoid revisiting the parent
                isLeaf = false; // Not a leaf if it has children
                dfs(next, curr, li, currcat);
            }
        }

        // If it's a leaf and the path to this node is valid, count it
        if (isLeaf) {
            validRestaurants++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of vertices (n) and max consecutive cats allowed (m)
        n = sc.nextInt();
        m = sc.nextInt();

        // Initialize hasCat[] array
        hasCat = new int[n + 1]; // 1-based indexing
        for (int i = 1; i <= n; i++) {
            hasCat[i] = sc.nextInt();
        }

        // Initialize the tree (adjacency list)
        tree = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            tree.add(new ArrayList<>());
        }

        // Input tree edges
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            tree.get(u).add(v);
            tree.get(v).add(u);
        }

        // Create an instance of the class to call the dfs function
        Kefa_and_Park dfsTraversal = new Kefa_and_Park();

        // Start DFS from the root (node 1), with parent as -1 and 0 consecutive cats
        dfsTraversal.dfs(1, -1, tree, 0);

        // Output the number of valid restaurants
        System.out.println(validRestaurants);
    }
}
