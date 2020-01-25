// [BOJ] 백준 16236 아기상어
// https://www.acmicpc.net/problem/16236

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/*
4
4 3 2 1
0 0 0 0
0 0 9 0
1 2 3 4

>> 14
*/

public class Main {
    static int N;
    static int board[][];
    static int answer = 0;
    static Fish shark;
    static PriorityQueue<Fish> pq;
    
    // for BFS
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};
    static boolean[][] visited;
    static Queue<Vertex> queue;
    static class Fish implements Comparable<Fish>{
        int x, y;
        int size;
        int exp;
        public Fish(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            exp = 0;
        }

        @Override
        public int compareTo(Fish target) {
            // 위쪽에 있는 것이 앞으로 오도록
            if(this.x < target.x) return -1;
            else if(this.x > target.x) return 1;
            else {
                // 동일한 x 좌표일 때, 왼쪽에 있는 것이 앞쪽에 오도록
                if(this.y < target.y) return -1;
                else return 1;
            }
        }

        public void eatFish(Fish fish) {
            // 상어 이동
            this.x = fish.x;
            this.y = fish.y;
            board[fish.x][fish.y] = 0;
            
            if(++this.exp == this.size) {
                // 크기 Up
                this.size++;
                // 경험치 초기화
                this.exp = 0;
            }
        }
    }
    static class Vertex {
        int x, y;
        // 상어가 있는 곳에서의 거리
        int distance;
        public Vertex(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
        
    }
     
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        board = new int[N][N];
        
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                board[i][j] = Integer.parseInt(sc.next());
                // 상어인 경우
                if(board[i][j] == 9) {
                    shark = new Fish(i, j, 2);
                    // 상어는 애초에 board에서 표시 X
                    board[i][j] = 0;
                }
            }
        } // end of input data
        
        BFS();
        
        System.out.println(answer);
    }


    private static void BFS() {
        visited = new boolean[N][N];
        queue = new LinkedList<>();
        pq = new PriorityQueue<>();
        // 가장 가까운 물고기들을 찾는 것이므로 임시 최소거리 설정
        int minDistance = Integer.MAX_VALUE;
        
        // 현재 상어의 위치에서 start
        queue.add(new Vertex(shark.x, shark.y, 0));
        
        while(!queue.isEmpty()) {
            Vertex v = queue.poll();
            
            // 상하좌우 방향
            for(int i=0; i<4; i++) {
                int nx = v.x + dx[i];
                int ny = v.y + dy[i];
                
                if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                
                // 방문한 곳인 경우
                if(visited[nx][ny]) continue;
                
                // 상어보다 큰 물고기의 경우 지나갈 수 없다.
                if(board[nx][ny] > shark.size) continue;
                
                // 최소거리의 물고기보다 먼 곳은 탐색 불필요
                if(minDistance < v.distance + 1) continue;
                
                // 상어의 크기 보다 작은 물고기가 발견된다면
                if(board[nx][ny] > 0 && board[nx][ny] < shark.size) {
                    // 최소거리 설정
                    minDistance = v.distance + 1;
                    // 최소거리에 있는 물고기들을 집어넣는다.
                    pq.add(new Fish(nx, ny, board[nx][ny]));
                    // 방문 표시
                    visited[nx][ny] = true;
                    // 이후 탐색은 최소거리 이상이므로 무의미
                    continue;
                }
                
                // 방문표시
                visited[nx][ny] = true;
                // 최소거리의 물고기를 찾기 위한 BFS 계속 진행
                queue.add(new Vertex(nx, ny, v.distance + 1));
            }
        }
        
        // BFS가 끝난 후, 잡을 수 있는 물고기가 있다면
        if(!pq.isEmpty()) {
            // 가장 우선 순위가 높은 물고기를 먹으로 상어가 이동            
            shark.eatFish(pq.poll());
            
            // 먹을 물고기까지의 BFS를 이용했던 최소 거리를 더해준다.
            answer += minDistance;
            
            // 다음 target을 차기 위한 재귀
            BFS();
        }
    }
}