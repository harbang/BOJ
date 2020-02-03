
// [BOJ] 백준 17070 파이프 옮기기 1
// https://www.acmicpc.net/problem/17070

import java.util.Scanner;

public class Main {
    
    static int[][] board;
    static int N, answer = 0;
    // pipe 상태(가로, 세로, 대각선)
    static final int HORIZONTAL = 1;
    static final int VERTICAL = 2;
    static final int DIAGONAL = 3;
    
    static class Pipe{
        // 파이프의 끝 지점만 좌표로 사용
        int x, y;
        int state;
        
        public Pipe(int x, int y, int state) {
            this.x = x;
            this.y = y;
            this.state = state;
        }


        public void move() {
            // Pipe 끝 부분 도착지점 확인
            if(x == N && y == N) {
                answer++;
                return;
            }
            
            if(state == HORIZONTAL) {
                // 가로 이동
                if(y < N && board[x][y+1] != 1) {
                    y++;
                    move();
                    y--;    
                }
                // 대각선 이동
                if( x < N && y < N &&
                        board[x+1][y] != 1 &&
                        board[x][y+1] != 1 &&
                        board[x+1][y+1] != 1
                    ) {
                    x++; y++; state = DIAGONAL;
                    move();
                    x--; y--; state = HORIZONTAL;    
                }
            }
            else if (state == VERTICAL) {
                // 세로 이동
                if(x < N && board[x+1][y] != 1) {
                    x++;
                    move();
                    x--;    
                }
                
                // 대각선 이동
                if( x < N && y < N &&
                        board[x+1][y] != 1 &&
                        board[x][y+1] != 1 &&
                        board[x+1][y+1] != 1
                    ) {
                    x++; y++; state = DIAGONAL;
                    move();
                    x--; y--; state = VERTICAL;
                }
                
            }
            else if (state == DIAGONAL) {
                // 가로 이동
                if(y < N && board[x][y+1] != 1) {
                    y++; state = HORIZONTAL;
                    move();
                    y--; state = DIAGONAL;    
                }
                
                // 세로 이동
                if(x < N && board[x+1][y] != 1) {
                    x++; state = VERTICAL;
                    move();
                    x--; state = DIAGONAL;


                }
                                
                // 대각선 이동
                if( x < N && y < N &&
                        board[x+1][y] != 1 &&
                        board[x][y+1] != 1 &&
                        board[x+1][y+1] != 1
                    ) {
                    x++; y++;
                    move();
                    x--; y--;    
                }
                 
            }
        }
        
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        N = Integer.parseInt(sc.next());
        
        board = new int[N+1][N+1];
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=N; j++) {
                board[i][j] = Integer.parseInt(sc.next());
            }
        } // end of input
        
        // 파이프 끝지점을 기준으로 생성
        Pipe pipe = new Pipe(1, 2, HORIZONTAL);
        
        pipe.move();
        
        System.out.println(answer);
    }
}