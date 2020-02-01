// [BOJ] 백준 14502 연구소
// https://www.acmicpc.net/problem/14502

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int M, N;
    static int[][] arr;
    static int[][] temp;
    static int answer = 0;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());
        arr = new int[N+1][M+1];
        // 임시용도로 복사배열이므로 크기는 동일하게
        temp = new int[N+1][M+1];
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                arr[i][j] = Integer.parseInt(sc.next());
            }
        }
        makeWall(0);
        
        System.out.println(answer);
    }
    
    static void makeWall(int cnt) {
        if(cnt == 3) {
            infection();
            return;
        }
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                if(arr[i][j] == 0) {
                    arr[i][j] = 1; // (cnt+1)번째 벽을 세운 곳
                    makeWall(cnt + 1); // 재귀
                    arr[i][j] = 0;
                }
            }
        }
    }
    
    static void infection() {
        // 상,하,좌,우
        int[] rows = {-1, 1, 0, 0};
        int[] cols = {0, 0, -1, 1};
        
        Queue<Node> queue = new LinkedList<>();
        
        // 최초 전염지점을 큐에 담는다.
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                
                // 각 case 바이러스 전파 확인용 임시 복사배열
                temp[i][j] = arr[i][j];
                
                if(arr[i][j] == 2) { // 바이러스 지점이라면
                    queue.add(new Node(i, j));
                } // 최초의 바이러스 지점은 각 case마다 동일하긴 하지만 BFS를 위해 그때그때 다시 넣어준다.
            }
        }

        // 최초 지점들로 부터 모두 전염시킨다.
        while(!queue.isEmpty()) {
            Node curNode = queue.poll();
            // 상하좌우 확인
            for(int i=0; i<4;i++) {
                int infect_x = curNode.x + rows[i];
                int infect_y = curNode.y + cols[i];
                // 기존에 전염되지 않았고 벽이 아니라면
                if(infect_x < 1 || infect_y < 1) continue;
                if(infect_x > N || infect_y > M) continue;
                
                if(temp[infect_x][infect_y] == 0) {
                    temp[infect_x][infect_y] = 2; // 전염표시
                    queue.add(new Node(infect_x, infect_y));
                }
                
            }
        }
        checkSafetyArea();
    }
    
    private static void checkSafetyArea() {
        int count = 0;
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                if(temp[i][j] == 0) {
                    count++;
                }
            }
        }
        answer = Math.max(answer, count);
    }    
}

class Node{
    int x, y;
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}