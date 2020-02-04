// [BOJ] 백준 17281 ⚾
// https://www.acmicpc.net/problem/17281

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int N;
    static int[][] arr;
    static List<Integer> wait;
    static boolean[] visited;
    static int answer = 0;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        // 각 이닝별  1~9번 선수 예상 결과
        arr = new int[N+1][10];
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=9; j++) {
                arr[i][j] = Integer.parseInt(sc.next());
            }
        }
        
        wait = new ArrayList<>();
        // 1번 선수는 순서가 정해져있으므로 생략
        for(int i=2; i<=9; i++) {
            wait.add(i);
        }
        
        // 중복순열을 체크하기 위함
        visited = new boolean[8];
        List<Integer> player = new ArrayList<>();
        
        // 2~9번 선수 랜덤 배치 (모든 배치 고려)
        permutation(player);
        
        System.out.println(answer);
    }


    private static void permutation(List<Integer> player) {
        if(player.size() == 8) {
            // 리스트 깊은 복사
            List<Integer> playerList = new ArrayList<>();
            playerList.addAll(player);
            // 1번 선수는 4번 타자로 지정
            playerList.add(3, 1);
            gameStart(playerList);
            return;
        }
        
        
        for(int i=0; i<8; i++) {
            if(!visited[i]) {
                player.add(wait.get(i));
                visited[i] = true;
                permutation(player);
                player.remove(player.size() - 1);
                visited[i] = false;
            }
        }
        
    }

    private static void gameStart(List<Integer> player) {
        
        int round = 1; // 1라운드 부터 게임 진행
        int score = 0; // 점수
        int[] base = new int[3]; // 1~3루
        int outCnt = 0; // 아웃 횟수
        int idx = 0; // 타자
        
        while(true) {
            
            // 해당 이닝의 타자의 결과로 게임 진행
            int batter = player.get(idx);
            switch (arr[round][batter]) {
            /*
            안타: 1
            2루타: 2
            3루타: 3
            홈런: 4
            아웃: 0
            */
            case 1:
                // 3루에 있던 주자를 홈으로
                if(base[2] == 1) {
                    score++; base[2] = 0;
                }
                // 2루에 주자가 있었다면 2루로
                if(base[1] == 1) {
                    base[2] = 1; base[1] = 0;
                }
                // 1루에 주자가 있었다면 1루로
                if(base[0] == 1) {
                    base[1] = 1; base[0] = 0;
                }
                // 타자를 1루로 진출
                base[0] = 1;
                break;
            case 2:
                // 3루에 있던 주자를 홈으로
                if(base[2] == 1) {
                    score++; base[2] = 0;
                }
                // 2루에 주자가 있었다면 홈으로
                if(base[1] == 1) {
                    score++; base[1] = 0;
                }
                // 1루에 주자가 있었다면 3루로
                if(base[0] == 1) {
                    base[2] = 1; base[0] = 0;
                }
                // 타자를 2루로 진출
                base[1] = 1;
                break;
            case 3: //
                // 3루에 있던 주자를 홈으로
                if(base[2] == 1) {
                    score++; base[2] = 0;
                }
                // 2루에 주자가 있었다면 홈으로
                if(base[1] == 1) {
                    score++; base[1] = 0;
                }
                // 1루에 주자가 있었다면 홈으로
                if(base[0] == 1) {
                    score++; base[0] = 0;
                }
                // 타자를 3루로 진출
                base[2] = 1;
                break;
                
            case 4: // 홈런
                // 1~3루에 있는 주자들을 홈까지 진루
                score = score + base[0] + base[1] + base[2] + 1;
                base[0] = 0; base[1] = 0; base[2] = 0;
                break;
                
            case 0: // 아웃
                outCnt++;
                // 삼진 아웃인 경우 다음 라운드(이닝) 진행
                if(outCnt == 3) {
                    round++;
                    // 모든 라운드가 진행되었으면 최종 점수 확인
                    if(round > N) {
                        answer = (answer < score) ? score : answer;
                        return;
                    }
                    // 1~3루에 있던 주자들 초기화
                    base[0] = 0; base[1] = 0; base[2] = 0;
                    // 아웃 초기화
                    outCnt = 0;
                }
                break;
                
            default:
                break;
            }
            // 어떤 결과든 상관없이 타자 순번으로 유지
            // 1~9번 타자로 순환되도록
            idx = (idx + 1) % 9;
        }
    }


    
}