[BOJ] 백준 14501 퇴사
https://www.acmicpc.net/problem/14501

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
            // 해당일자의 상담 소요시간이 퇴사 이후가 되서는 안된다.
            // 퇴사기간내 가능한(종료되는) 상담 중에서 해당 일자의 상담을 진행할지 말지 선택
            if(i + (time[i]-1) < N) {
                
                // 퇴사이전까지는 상담이 종료되지만 배열 크기상(수식상) 퇴사 이후의 dp를 고려하게 되므로 배제
                int temp = 0;
                if(i + time[i] < N) temp = dp[i + time[i]];
                
                dp[i] = Math.max(dp[i+1], pay[i] + temp);
                
            }else {
                // 퇴사 이후에도 진행되어야 한다면 dp[i+1]값을 그대로 가진다.
                dp[i] = dp[i+1];
            }
        }

        System.out.println(dp[0]);
            
    }
}