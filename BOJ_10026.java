// [BOJ] 백준 10026 적록색약
// https://www.acmicpc.net/problem/10026

import java.util.Scanner;
import java.util.Stack;

public class Main {
    static char[][] arr;
    static int[][] visited;
    static char searchType;
    static int[] dx, dy;
    static Stack<Node> stack;
    static int N;
    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        arr = new char[N][N];
        
        for(int i=0; i<N; i++) {
            String str = sc.next();
            for(int j=0; j<N; j++) {
                arr[i][j] = str.charAt(j);
            }
        }
        
        // 방문 표시용
        visited = new int[N][N];
        // DFS 탐색을 위한 stack
        stack = new Stack<Node>();
        // 기본 이동 반경(상하좌우)
        dx = new int[] {0,0,-1,1};
        dy = new int[] {-1,1,0,0};
        
        int normal = 0; // 정상인 사람이 보는 영역의 개수
        
        // 모든 지점을 탐색했는지 확인한다.
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                // 이미 방문한 곳이라면 continue
                if(visited[i][j] == 1) continue;

                // 탐색해야할 색깔 설정
                searchType = arr[i][j];
                // 방문표시
                visited[i][j] = 1;
                
                stack.push(new Node(i, j));
                // 만약 방문하지 않은 곳이라면 해당 지점과 인접한 곳의 같은 색깔을 찾고 방문(탐색)
                DFS();
                
                // 영역 개수 늘리기
                normal++;
            }
        }
        
        System.out.println(normal);
        
        // 적록색약의 경우
        int colorWeak = 0;
        // 방문 표시 초기화
        visited = new int[N][N];
        
        // 모든 지점을 탐색했는지 확인한다.
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                // 이미 방문한 곳이라면 continue
                if(visited[i][j] == 1) continue;

                // 탐색해야할 색깔 설정
                searchType = arr[i][j];
                // 방문표시
                visited[i][j] = 1;
                
                stack.push(new Node(i, j));
                
                // 만약 방문하지 않은 곳이라면 해당 지점과 인접한 곳의 색깔여부를 판단하며 방문(탐색)
                DFS2();
                // 영역 개수 늘리기
                colorWeak++;
            }
        }
        
        System.out.println(colorWeak);
    }

    private static void DFS2() {
        while(!stack.isEmpty()) {
            Node curNode = (Node) stack.pop();
            for(int i=0; i<4; i++) {
                // 다음 탐색 위치
                int next_x = curNode.x + dx[i];
                int next_y = curNode.y + dy[i];
                
                // 기본적으로 배열 내에서 탐색
                if(next_x < 0 || next_y < 0) continue;
                if(next_x >= N  || next_y >= N) continue;
                
                // 찾고자 하는 색깔이 파랑(B)이면 완전히 동일해야 한다.
                if(searchType == 'B' && arr[next_x][next_y] != 'B') continue;
                // 찾고자 하는 색깔이 'R' 또는 'G'이면 'B'만 아니면 된다.
                if(searchType != 'B' && arr[next_x][next_y] == 'B') continue;
                // 이미 방문한 곳이면 continue
                if(visited[next_x][next_y] == 1) continue;
                
                // 이동가능한 배열 범위내에서 인접한 동일한  색깔이면서 방문하지 않은 곳이면 DFS탐색 이어가기
                stack.push(new Node(next_x, next_y));
                // 방문표시
                visited[next_x][next_y] = 1;
            }
        }
    }

    private static void DFS() {
        
        while(!stack.isEmpty()) {
            Node curNode = (Node) stack.pop();
            for(int i=0; i<4; i++) {
                // 다음 탐색 위치
                int next_x = curNode.x + dx[i];
                int next_y = curNode.y + dy[i];
                
                // 기본적으로 배열 내에서 탐색
                if(next_x < 0 || next_y < 0) continue;
                if(next_x >= N  || next_y >= N) continue;
                
                // 찾는 색깔이 아니면 continue
                if(arr[next_x][next_y] != searchType) continue;
                // 이미 방문한 곳이면 continue
                if(visited[next_x][next_y] == 1) continue;
                
                // 이동가능한 배열 범위내에서 인접한 동일한  색깔이면서 방문하지 않은 곳이면 DFS탐색 이어가기
                stack.push(new Node(next_x, next_y));
                // 방문표시
                visited[next_x][next_y] = 1;
            }
        }
    }
}

class Node{
    int x, y;
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}