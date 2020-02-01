// [BOJ] 백준 15684 사다리 조작
// https://www.acmicpc.net/problem/15684

import java.util.Scanner;

public class Main {
    
    static int[][] board;
    static int N, M, H;
    static int maxPlusBridge;
    static int answer = -1;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 열의 개수
        N = Integer.parseInt(sc.next());
        // 초기에 놓여진 사다리 개수
        M = Integer.parseInt(sc.next());
        // 행의 개수
        H = Integer.parseInt(sc.next());
        
        // 1행 1열로 시작하기 위한 인덱스 조정
        board = new int[H+1][N+1];
        
        for(int i=0; i<M; i++) {
            int x = Integer.parseInt(sc.next());
            int y = Integer.parseInt(sc.next());
            // (x,y)에서 (x,y+1) 좌표로 오른쪽으로 이동하는 의미로 '1'
            // (x,y+1)에서 (x,y) 좌표로 왼쪽으로 이동하는 의미로 '2'
            board[x][y] = 1; board[x][y+1] = 2;
        }
        // 추가 가로선 0 ~ 3개 Case 별로 구하기
        for(maxPlusBridge=0; maxPlusBridge<=3; maxPlusBridge++) {
            // 탐색 과정에서 만족한다면 종료
            if(answer != -1) break;
            DFS(1, 1, 0);
        }
        
        System.out.println(answer);
    }




    private static void DFS(int x, int y, int bridgeCnt) {
        // 탐색 과정에서 만족한다면 종료
        if(answer != -1) return;
        
        if(bridgeCnt >= maxPlusBridge) {
            if( isOK() ) answer = bridgeCnt;
            return;
        }

        int j = y;
        for(int i=x; i<=H; i++) {
            while(true) {
                // 왼쪽에서 오른쪽으로 긋기 때문에 마지막 열 앞에까지만 확인
                if(j >= N) {
                    j = 0;
                    break;
                }
                // 사다리를 놓을 수 있는지 확인(연속하거나 인접하지 않도록)
                if(board[i][j] == 0 && board[i][j+1] == 0) {
                    // 해당 구간에 추가 가로선 긋기
                    board[i][j] = 1; board[i][j+1] = 2;
                    DFS(i, j, bridgeCnt + 1);
                    // 해당 구간을 긋지 않고 Skip
                    board[i][j] = 0; board[i][j+1] = 0;
                }
                j++;
            }            
        }
    }


    private static boolean isOK() {
        // 각 열을 출발점으로 시작
        for(int startY=1; startY<=N; startY++) {
            int y = startY;
            // 한 칸씩 아래로 이동
            for(int x=1; x<=H; x++) {
                switch (board[x][y]) {
                // 우측으로 이동
                case 1:
                    y += 1;
                    break;
                // 좌측으로 이동
                case 2:
                    y -= 1;
                    break;
                // 사다리가 없는 경우 (0)
                default:
                    break;
                }
            }
            // 끝지점에 도착했을 출발 했을 때의 세로선상이 동일한지 확인
            if(y != startY) return false;
        }
        return true;
    }
}