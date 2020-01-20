// https://www.acmicpc.net/problem/16234
// BOJ 16234 인구이동

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    
    static int N, L, R, answer = 0;
    static Country[][] country;
    static boolean[][] visited;
    static Queue<Country> queue;
    static List<Integer> avg;
    static boolean isChange;
    // 이동범위 상하좌우
    static int[] dx= {-1,1,0,0}, dy= {0,0,-1,1};
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        L = Integer.parseInt(sc.next());
        R = Integer.parseInt(sc.next());
        
        country = new Country[N][N];
        
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                country[i][j] = new Country(i, j, Integer.parseInt(sc.next()));
            }
        }
        
        queue = new LinkedList<>();
        // 평균값들을 담을 리스트
        avg = new ArrayList<>();
        
        while(true) {
            isChange = false;
            // 연합 번호 부여하기(영역 나누기)
            int union = 0;
            // 방문표시를 초기화해서 모든 영역에 대한 BFS 탐색
            visited = new boolean[N][N];

            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    // 방문하지 않은 곳을 기점으로 BFS 탐색
                    if(!visited[i][j]) {
                        // 방문표시
                        visited[i][j] = true;
                        queue.add(country[i][j]);
                        // 연합지을 수 있는 영역을 BFS로 탐색
                        BFS(union++);
                    }
                }
            }

            // 국가간 이동(연합)이 없었다면 출력하고 종료
            if(!isChange) {
                System.out.println(answer);
                return;
            }
            changeVal();
            answer++;
        }
    }
    private static void changeVal() {
        // 해당 연합번호의 맞는 평균값들로 전환해준다.
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                Country c = country[i][j];
                c.val = avg.get(c.union);
            }
        }
        // 다음 BFS를 위하여 비워져야 한다.
        avg.clear();
    }
    private static void BFS(int union) {
        
        int sum = 0;
        int count = 0;
        
        while(!queue.isEmpty()) {
            
            Country current = queue.poll();
            // 연합번호 부여하기
            current.union = union;
            // 해당 연합의 개수 확인
            count++;
            // 해당 연합의 평균을 구하기 위해 총합을 더한다.
            sum += current.val;

            for(int i=0; i<4; i++) {
                int next_x = current.x + dx[i];
                int next_y = current.y + dy[i];
                
                // 배열범위를 벗어나는지 확인하기
                if(next_x < 0 || next_y < 0) continue;
                if(next_x >= N || next_y >= N) continue;
                
                // 방문여부 확인하기
                if(visited[next_x][next_y]) continue;
                
                // 이 문제의 특별조건이 L,R의 값 사이인지  확인하기
                // 인구 차이 비교하기
                int diff = Math.abs(current.val - country[next_x][next_y].val);
                if(diff < L || diff > R) continue;
                
                // BFS 탐색을 이어갈 수 있도록 방문표시
                visited[next_x][next_y] = true;

                // queue에 해당 지점을 추가
                queue.add(country[next_x][next_y]);
                
                // 국가간 연합이 있었던 것이므로
                isChange = true;  
            }
        }
        
        // 연합된 영역의 평균값을 출력한다.
        // 소수점을 버리므로 int형으로 계산하면 된다.
        // 적어도 1개의 지점에 대한 BFS를 한 것이므로 '0'으로 나누어지는 경우는 없다.
        avg.add(sum / count);
    }
}

class Country{
    int x, y;
    int val;
    int union;
    
    public Country(int x, int y, int v) {
        this.x = x;
        this.y = y;
        val = v;
    }
}