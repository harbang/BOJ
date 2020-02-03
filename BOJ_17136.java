// [BOJ] 백준 17136 색종이 붙이기
// https://www.acmicpc.net/problem/17136

import java.util.Scanner;

public class Main {

    static int paper[] = { 0, 5, 5, 5, 5, 5 };
    static int board[][];
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        board = new int[11][11];

        // 『1』의 개수
        int num_of_1 = 0;
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                board[i][j] = Integer.parseInt(sc.next());
                if (board[i][j] == 1) num_of_1++;
            }
        } // end of input


        // 처음 주어진 배열에서
        if (num_of_1 == 0) {
            System.out.println(0);
            return;
        }
        
        // 1행부터 영역을 살펴본다.
        solve(1);
        
        if(answer == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(answer);
    }

    private static void solve(int x) {
        for (int r = x; r <= 10; r++) {
            for (int c = 1; c <= 10; c++) {

                // 처리할 『1』 탐색
                if (board[r][c] == 1) {
                    // 해당 영역에 가능한 색종이가 있는지 확인
                    for (int paperSize=1; paperSize<=5; paperSize++) {
                        // 색종이가 남아 있는지 확인
                        if (paper[paperSize] > 0) {
                            // 지정된 색종이 크기 영역이 모두 『1』인지 확인
                            if (checkArea(r, c, paperSize)) {
                                // 해당영역의 숫자를 『1』 → 『0』으로 전환
                                for (int i = r; i < r + paperSize; i++) {
                                    for (int j = c; j < c + paperSize; j++) {
                                        board[i][j] = 0;
                                    }
                                }
                                // 색종이 사용
                                paper[paperSize]--;
                                
                                // 재귀탐색
                                solve(r);

                                // 해당영역의 숫자를 다시 환복 『0』 → 『1』
                                for (int i = r; i < r + paperSize; i++) {
                                    for (int j = c; j < c + paperSize; j++) {
                                        board[i][j] = 1;
                                    }
                                }
                                // 사용했던  색종이 반환
                                paper[paperSize]++;
                            }
                        }
                    }

                    // 1~5번 어떤 종이로도 붙이지 못했다면
                    // 여전히 『1』로 남았을 경우
                    if (board[r][c] == 1) return;
                }
            }
        }
        
        // 모든 영역이 0이 되었으므로,
        // 사용된 색종이 개수 확인
        int paperCnt = 25;
        for (int i = 1; i <= 5; i++) {
            paperCnt -= paper[i];
        }

        answer = answer > paperCnt ? paperCnt : answer;
    }


    private static boolean checkArea(int r, int c, int paperSize) {
        // 범위를 벗어나면
        if(r < 1 || c < 1 || r + paperSize > 11|| c + paperSize > 11) return false;
        
        // 해당 영역이 모두 1인지 확인
        for (int i = r; i < r + paperSize; i++) {
            for (int j = c; j < c + paperSize; j++) {
                if (board[i][j] != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}