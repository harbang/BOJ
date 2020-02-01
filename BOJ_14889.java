// [BOJ] 백준 14889 스타트와 링크
// 문제: https://www.acmicpc.net/problem/14889

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static int[] people;
    static int N, answer = Integer.MAX_VALUE;
    static int[][] S;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        people = new int[N+1];
        S = new int[N+1][N+1];
        
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=N; j++) {
                S[i][j] = Integer.parseInt(sc.next());
            }
        }
        
        // 1 ~ N번까지 번호 부여
        for(int i=1; i<=N; i++) {
            people[i] = i;
        }
        
        List<Integer> team1 = new ArrayList<>();
        // 1번 사람을 우선 team1에 소속시킨다.
        team1.add(people[1]);
        
        // 2번 사람부터 team1에 소속시킬지 말지
        // 중복없는 조합을 구한다.(DFS)
        combination(team1, 2);
        
        System.out.println(answer);
    }
    
    // idx번째 사람을 소속시킨든지 말든지 둘 중 하나
    private static void combination(List<Integer> team1, int idx) {
        if(team1.size() == N / 2) {
            // 팀이 절반으로 나눠졌으면...
            int team1_S = 0;
            int team2_S = 0;
            for(int i=1; i<=N; i++) {
                for(int j=1; j<=N; j++) {
                    if(team1.contains(i) && team1.contains(j)) {
                        team1_S += S[i][j];
                    }else if(!team1.contains(i) && !team1.contains(j)) {
                        team2_S += S[i][j];
                    }
                }
            }
            
            answer = Math.min(answer, Math.abs(team1_S - team2_S));

            return;
        }
        
        // 더 이상 팀원 가입시킬 멤버가 없으면 종료
        if(idx > N) return;
        
        // idx번째 사람을 team1에 소속 시킨 경우
        team1.add(people[idx]);
        combination(team1, idx+1);
        
        // idx번째 사람을 team1에 소속 소속시키지 않은 경우
        
        team1.remove(team1.size()-1);
        combination(team1, idx+1);
    }
}