// [BOJ] 백준 7576 토마토
// 문제: https://www.acmicpc.net/problem/7576
// 풀이: https://octorbirth.tistory.com/107

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Vertex{
    int x, y;
    int elapsedTime;
    
    public Vertex(int x, int y, int t) {
        this.x = x;
        this.y = y;
        elapsedTime = t;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int M = Integer.parseInt(sc.next());
        int N = Integer.parseInt(sc.next());
        
        int[][] tomato = new int[N+1][M+1];
        
        Queue<Vertex> queue = new LinkedList<>();
        
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                tomato[i][j] = Integer.parseInt(sc.next());
                if(tomato[i][j] == 1) {
                    // 초기의 출발점들을 queue에 저장
                    queue.add(new Vertex(i, j, 0));
                }
            }
        }
        
        // 동(오른쪽), 서(왼쪽), 남(아래), 북(위)
        int[] rowMove = {0, 0, 1 , -1};
        int[] colMove = {1, -1, 0 ,0};
        
        int maxElapsedTime = 0;
        
        // 익은 모든 토마토처리될 때까지
        while(!queue.isEmpty()) {
            
            Vertex curTomato = queue.poll();
            
            for(int i=0; i<4; i++) {

                int next_x = curTomato.x + rowMove[i];
                int next_y = curTomato.y + colMove[i];
                
                // 꺼낸 위치의 토마토에서 상자(배열)을 벗어나지 않는 범위에서
                if(next_x > 0 && next_y > 0 && next_x <= N && next_y <= M ) {
                    // 안 익은 토마토가 있는지 확인하고 해당 안 익은 토마토를 익히고, queue에 저장한다.
                    if(tomato[next_x][next_y] == 0) {
                        // 방문 표시
                        tomato[next_x][next_y] = 1;
                        queue.add(new Vertex(next_x, next_y, curTomato.elapsedTime + 1));
                        if(maxElapsedTime < curTomato.elapsedTime + 1) {
                            maxElapsedTime = curTomato.elapsedTime + 1;
                        }
                    }
                    
                }
            }
        }
        
        // 토마토 상자에 존재하는 모든 토마토가 익었는지 확인한다.
        boolean isClear = true;
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                if(tomato[i][j] == 0) {
                    isClear = false;
                    break;
                }
            }
        }
        
        if(!isClear) System.out.println("-1");
        else System.out.println(maxElapsedTime);
    }
}