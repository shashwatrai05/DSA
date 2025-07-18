import java.util.*;

public class BFS {
    public static void main(String[] args) {
        int n = 6; // Number of nodes
        List<Integer>[] adjList = new ArrayList[n];

        // Initialize adjacency list
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }

        // Example edges (Undirected Graph)
        adjList[0].add(1);
        adjList[0].add(2);
        adjList[1].add(3);
        adjList[2].add(4);
        adjList[3].add(5);

        bfs(0, adjList, n);
    }

    static void bfs(int start, List<Integer>[] adjList, int n) {
        Queue<Pair<Integer, Integer>> que;
        que = new LinkedList<>();
        que.offer(new Pair<>(start, 0));

        boolean[] isVis = new boolean[n];
        isVis[start] = true;

        while (!que.isEmpty()) {
            Pair<Integer, Integer> p = que.poll();
            int node = p.getKey();
            int level = p.getValue();

            System.out.println("Node: " + node + ", Level: " + level);

            for (int neighbor : adjList[node]) {
                if (!isVis[neighbor]) {
                    isVis[neighbor] = true;
                    que.offer(new Pair<>(neighbor, level + 1));
                }
            }
        }
    }
}

//class Pair<K, V> {
//    private final K key;
//    private final V value;
//
//    public Pair(K key, V value) {
//        this.key = key;
//        this.value = value;
//    }
//
//    public K getKey() { return key; }
//    public V getValue() { return value; }
//}
