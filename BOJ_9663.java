// [BOJ] 백준 9663 N-Queen
// https://www.acmicpc.net/problem/9663
import java.util.Scanner;

public class Main {
    
    static int[] col;
    static int N, answer;
    
    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        col = new int[N+1]; // 각 행에서 여왕이 위치한 열이 몇 번째인지 저장할 배열
        
        
        // 우선 첫번째 행에서 여왕들이 모든 열에 놓아보면서 탐색
        for(int j=1; j<=N; j++) {
            // 1번째 행에 j번째 열에 여왕
            col[1] = j;
            
            // 2번째 행에서는 여왕을 어떻게 놓을지 탐색하는 것 (재귀)
            DFS(2);
        }
        
        // 정답 출력
        System.out.println(answer);
    }
    
    private static void DFS(int row) {
        // 만약 N+1 수치에 도달했다면
        // N번째 행까지 1개씩 놓으면서 N개의 여왕을 놓는데 성공
        if(row > N) {
            answer++;
            return;
        }
        // row번째 행에 여왕이 놓일 수 있는 열 위치를 확인한다.
        for(int y=1; y<=N; y++) {
            col[row] = y;
            // 만약 지금까지 상태에서 여왕끼리 서로 공격할 수 없는 위치라면 계속해서 탐색하고
            // 그렇지 않으면 더 이상의 탐색은 의미 없다.(백트래킹)
            if(promising(row)) {
                // 다음 몇 번째 행을 살펴야 할지 인자로 전달한다.
                DFS(row+1);
            }
        }
        
    }

    private static boolean promising(int row) {
        // 1 ~ (row-1)행까지 위치한 여왕말이 공격할 수 있는 위치인지 확인
        for(int x=1; x < row; x++) {
            // 같은 열 혹은 대각선 위치에 있다면 False 반환
            if(col[x] == col[row] || Math.abs(row-x) == Math.abs(col[row]- col[x]))
                return false;
        }
        
        // 놓을 수 있는 위치이면 True 반환
        return true;
    }
}