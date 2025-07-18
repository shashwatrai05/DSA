import java.util.*;

public class Labyrinth {
    static int n, m;
    static char[][] grid;
    static int[][] directions = {{0, -1, 'L'}, {0, 1, 'R'}, {-1, 0, 'U'}, {1, 0, 'D'}};
    static boolean[][] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        grid = new char[n][m];
        visited = new boolean[n][m];

        int startX = -1, startY = -1, endX = -1, endY = -1;

        // Read the input grid
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'A') {
                    startX = i;
                    startY = j;
                } else if (grid[i][j] == 'B') {
                    endX = i;
                    endY = j;
                }
            }
        }

        bfs(startX, startY, endX, endY);
    }

    static void bfs(int startX, int startY, int endX, int endY) {
        Queue<Pair<Pair<Integer, Integer>, String>> queue = new LinkedList<>();
        queue.offer(new Pair<>(new Pair<>(startX, startY), ""));
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Pair<Pair<Integer, Integer>, String> current = queue.poll();
            int x = current.getKey().getKey();
            int y = current.getKey().getValue();
            String path = current.getValue();

            if (x == endX && y == endY) {
                System.out.println("YES");
                System.out.println(path.length());
                System.out.println(path);
                return;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                char move = (char) dir[2];

                if (isValid(newX, newY)) {
                    visited[newX][newY] = true;
                    queue.offer(new Pair<>(new Pair<>(newX, newY), path + move));
                }
            }
        }

        System.out.println("NO");
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m && !visited[x][y] && grid[x][y] != '#';
    }
}

class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
}
