// [BOJ] 백준 14501 퇴사
// https://www.acmicpc.net/problem/14501

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.next());
        int[] time = new int[N];
        int[] pay = new int[N];
        
        for(int i=0; i<N; i++) {
            time[i] = Integer.parseInt(sc.next());
            pay[i] = Integer.parseInt(sc.next());
        }
        
        
        int[] dp = new int[N];
        
        // 초기값 설정
        // 마지막날 상담소요시간이 이틀 이상이면 '0'
        // 소요시간이 하루라면 우선은 진행(아직 최대이익인지는 확신할 수는 없다.)
        if(time[N-1] >= 2) {
            dp[N-1] = 0;    
        }else {
            dp[N-1] = pay[N-1];
        }

        for(int i=N-2; i>=0; i--) {
            // 퇴사기간내 가능한 상담 중에서 해당 일자 상담 선택 여부 결정
            if(i + (time[i]-1) < N) {
                int elapsedTime = 0;
                if(i + time[i] < N) {
                	elapsedTime = dp[i + time[i]];
                }
                dp[i] = Math.max(dp[i+1], pay[i] + elapsedTime);
            }
            else { // 퇴사 이후 진행되는 상담인 경우
                dp[i] = dp[i+1];
            }
        }

        System.out.println(dp[0]);
            
    }
}