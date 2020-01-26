// [BOJ] 백준 7569 토마토
// 문제: https://www.acmicpc.net/problem/7569
// 풀이: https://octorbirth.tistory.com/108

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Vertex{
    int x, y, h;
    int elapsedTime;
    
    public Vertex(int x, int y, int h, int t) {
        this.x = x;
        this.y = y;
        this.h = h;
        elapsedTime = t;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int M = Integer.parseInt(sc.next());
        int N = Integer.parseInt(sc.next());
        int H = Integer.parseInt(sc.next());
        
        int[][][] tomato = new int[N+1][M+1][H+1];
        
        Queue<Vertex> queue = new LinkedList<>();
        
        
        for(int k=1; k<=H; k++) {
            for(int i=1; i<=N; i++) {
                for(int j=1; j<=M; j++) {
                    tomato[i][j][k] = Integer.parseInt(sc.next());;
                    if(tomato[i][j][k] == 1) {
                        // 초기의 출발점들을 queue에 저장
                        queue.add(new Vertex(i, j, k, 0));
                    }    
                }
            }
        }
        
        // 동(오른쪽), 서(왼쪽), 남(아래), 북(위), 윗층, 아랫층
        int[] rowMove = {0, 0, 1 , -1, 0, 0};
        int[] colMove = {1, -1, 0 ,0, 0, 0};
        int[] heightMove = {0, 0, 0, 0, 1, -1};
        
        // 기존의 모두 익었다면 '0'이며 최대로 걸린 시간은 탐색과정에서 증가시킨다.
        int maxElapsedTime = 0;
        
        // 익은 토마토에서 가능한 영향(?)이 끝날 때까지
        while(!queue.isEmpty()) {
            
            // queue에 담겨있는 토마토 한개를 꺼낸다.
            Vertex curTomato = queue.poll();
            
            for(int i=0; i<6; i++) {
                // 동서남북, 윗층, 아랫층으로 다음 이동(?)할 좌표를 차례대로 받는다.
                int next_x = curTomato.x + rowMove[i];
                int next_y = curTomato.y + colMove[i];
                int next_h = curTomato.h + heightMove[i];
                
                // 꺼낸 위치의 토마토에서 상자(배열)을 벗어나지 않는 범위에서
                if(next_x > 0 && next_y > 0 && next_x <= N && next_y <= M && next_h > 0 & next_h <= H) {
                    // 안 익은 토마토가 있는지 확인하고 해당 안 익은 토마토를 익히고, queue에 저장한다.
                    if(tomato[next_x][next_y][next_h] == 0) {
                        tomato[next_x][next_y][next_h] = 1;
                        queue.add(new Vertex(next_x, next_y, next_h, curTomato.elapsedTime + 1));
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
                for(int k=1; k<=H; k++) {
                    if(tomato[i][j][k] == 0) {
                        isClear = false;
                        break;
                    }    
                }
            }
        }
        
        if(!isClear) System.out.println("-1");
        else System.out.println(maxElapsedTime);
    }
}