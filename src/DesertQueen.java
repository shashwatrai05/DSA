import java.util.*;

public class DesertQueen {

    static class Cell {
        int x, y, cost;
        Cell(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }

    public static int minWater(int n, char[][] dsrt) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(cell -> cell.cost));

        int strtX = 0, strtY = 0, endX = 0, endY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dsrt[i][j] == 'S') {
                    strtX = i;
                    strtY = j;
                } else if (dsrt[i][j] == 'E') {
                    endX = i;
                    endY = j;
                }
            }
        }

        int[][] dist = new int[n][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[strtX][strtY] = 0;

        pq.offer(new Cell(strtX, strtY, 0));

        while (!pq.isEmpty()) {
            Cell curr = pq.poll();
            int x = curr.x, y = curr.y, cost = curr.cost;

            if (x == endX && y == endY) return cost;

            if (cost > dist[x][y]) continue;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i], ny = y + dy[i];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && dsrt[nx][ny] != 'M') {

                    int newCost = cost + (dsrt[nx][ny] == 'T' ? 0 : 1);
                    if (newCost < dist[nx][ny]) {
                        dist[nx][ny] = newCost;
                        pq.offer(new Cell(nx, ny, newCost));
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        char[][] desert = new char[n][n];

        for (int i = 0; i < n; i++) {
            String[] row = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                desert[i][j] = row[j].charAt(0);
            }
        }
        int result = minWater(n, desert);
        System.out.println(result+1);
    }
}
