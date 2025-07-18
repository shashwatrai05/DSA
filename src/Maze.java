import java.util.Scanner;

public class Maze {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int n=sc.nextInt();
        int m=sc.nextInt();
        int k=sc.nextInt();
        char[][] maze=new char[n][m];
        for(int i=0;i<n;i++) {
            String line = sc.nextLine();
            for (int j = 0; j < m; j++) {
                maze[i][j] = line.charAt(j);
            }
        }
        int count=0;
        for(int i=0;i<n;i++) {
            for (int j = 0; j < m; j++) {
                if(maze[i][j] =='.')
                    count++;
            }
        }

        dfs(0,0, maze, count-k);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public static void dfs(int x, int y, char[][] maze, int curr){
        //if(curr==count-k) return;
        if (x < 0 || y< 0 || x >= maze.length || y >= maze[0].length || maze[x][y] == '#' )
            return;
        curr++;
        maze[x][y]='X';
        dfs( x+1, y, maze, curr);
        dfs( x-1, y, maze, curr);
        dfs( x, y+1, maze, curr);
        dfs( x, y-1, maze, curr);
    }
}
