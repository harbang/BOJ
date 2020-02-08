// [BOJ] 백준 1205 등수 구하기
// https://www.acmicpc.net/problem/1205

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
    
        int N = Integer.parseInt(sc.next());
        int X = Integer.parseInt(sc.next());
        int P = Integer.parseInt(sc.next());
        
        int[] rankList = new int[P];
        
        for(int i=0; i<N; i++) {
            rankList[i] = Integer.parseInt(sc.next());
        }
        
        // 애초에 랭킹 리스트에 점수가 없었던 경우
        if(N == 0) {
            System.out.println("1");
            return;
        }
        
        for(int i=0; i<N; i++) {
            
            // 기존의 랭킹 리스트에 중간에 진입할 수 있는지 확인한다.
            // (만약 꽉찬 리스트라면 반드시 기존리스트에서 어딘가 진입해야 랭커가 된다.)
            if (rankList[i] <= X) {
                
                // 기존의 랭킹리스트에 있는 점수와 같을 때, 중간위치에 있는 등수라면 문제가 없지만
                // 2번째 Test Case처럼 랭킹이 꽉 차있으며 마지막 등수의 점수와 동일한지를 확인한다.
                if(rankList[i] == X && N == P) {
                    
                    boolean flag = true;
                    // 꽉찬 랭킹리스트에서 제일 낮은 점수인지를 확인한다.
                    // 랭킹 리스트에 뒤쪽에 남아 있는 점수 중에서 낮은 점수가 존재한다면..
                    // 2번째 Test Case말고도 가령 꽉 차 있는 리스트 [3 3 3]이면서 X = 3인 경우에도 진입을 하지 못한다.
                    for(int temp = i+1; temp < N; temp++) {
                        if(rankList[i] > rankList[temp]) {
                            flag = false;
                            break;
                        }
                    }
                    
                    if(flag) { // Test Case 2번 같은 경우도 이에 해당된다.
                        System.out.println("-1");
                        return;
                    }
                    
                }
                // 랭킹은 '1'등 부터이니 인덱스 + 1
                System.out.println(i+1);
                return;
            }
            
        }
        
        // 꽉찬 리스트가 아닌 기존 리스트에 (중간)진입하지 못한 경우
        // 맨 마지막 랭킹 점수가 된다.
        if(N < P) {
            System.out.println(N+1);
            return;
        }
        
        // 그 외의 경우는 어떤 이유든 랭킹 진입에 실패한 경우
        System.out.println("-1");
    }
}