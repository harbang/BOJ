// [BOJ] 백준 17142 연구소 3
// 문제: https://www.acmicpc.net/problem/17142

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {


    static int N, M;
    static int[][] sourceMap;
    static List<Vertex> virusList;
    static public int[] dx = { 1, -1, 0, 0 };
    static public int[] dy = { 0, 0, -1, 1 };
    // 임의의 큰 수
    static public int INF = Integer.MAX_VALUE;
    static public int answer = INF;
    // 처음 주어진 map에서 빈 칸 개수
    static public int sourceBlankCnt = 0;
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        sourceMap = new int[N][N];
        virusList = new ArrayList<Vertex>();
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                sourceMap[i][j] = Integer.parseInt(st.nextToken());
                // 바이러스인 경우
                if (sourceMap[i][j] == 2) virusList.add(new Vertex(i, j));
                // 빈 칸인 경우
                else if(sourceMap[i][j] == 0) sourceBlankCnt++;
                
            }
        }


        // 활성 바이러스 지정
        setVirus(0, 0);
        
        // 모든 BFS의 탐색이 끝났는데도 문제 요구조건을 만족하지 못했다면
        answer = (answer == INF) ? -1:answer;
        System.out.println(answer);
    }
    
    /*
      0 : 빈칸
      1 : 벽
      2 : 비활성 바이러스
      3 : 활성 바이러스
     */
    public static void setVirus(int idx, int cnt) {
        if(cnt == M) {
            BFS();
            return;
        }
        
        if(idx >= virusList.size()) {
            return;
        }
        
        for(int i=idx; i<virusList.size(); i++) {
            Vertex virus = virusList.get(i);
            sourceMap[virus.x][virus.y] = 3;
            setVirus(i+1, cnt+1);
            sourceMap[virus.x][virus.y] = 2;
        }
    }


    private static void BFS() {
        // BFS 탐색을 위함
        Queue<Vertex> queue = new LinkedList<Vertex>();
        // 방문 표시용
        boolean[][] visited = new boolean[N][N];
        // 원본 sourceMap 복사
        int[][] map = new int[N][N];
        for(int i=0; i<N; i++) {
            map[i] = Arrays.copyOf(sourceMap[i], sourceMap[i].length);
            for(int j=0; j<N; j++) {
                // 활성화된 바이러스인 경우
                if(map[i][j] == 3) {
                    Vertex virus = new Vertex(i, j);
                    queue.offer(virus);
                    // 시작 지점을 방문표시
                    visited[virus.x][virus.y] = true;
                }
            }
        }
        
        
        int runTime = 0;
        // 빈칸 개수
        int blankCnt = sourceBlankCnt;
        // BFS
        while(!queue.isEmpty()) {
            
            // 빈 칸이 있는지 확인
            if(blankCnt == 0) break;
            
            // 현재 탐색이 이전 기록보다 더 크다면 종료
            if(answer != INF && answer < runTime) {
                return;
            }
            
            // 매 초마다 queue에 담겨 있는 바이러스 만큼만 복제 시작
            int qSize = queue.size();
            while(qSize-- > 0) {
                Vertex virus = queue.poll();


                for(int i=0; i<4; i++) {
                    int nX = virus.x + dx[i];
                    int nY = virus.y + dy[i];
                    
                    // 범위내에서
                    if(0 <= nX && nX < N && 0 <= nY && nY < N) {
                        // 방문하지 않은 곳이면서 벽이 아닌 곳.
                        if(!visited[nX][nY] && map[nX][nY] != 1) {
                            // 빈 칸 개수 감소
                            if(map[nX][nY] == 0) blankCnt--;
                            // 방문 표시
                            visited[nX][nY] = true;
                            // 빈 칸이든, 비활성 상태의 바이러스이든 활성상태 바이러스로 변경
                            map[nX][nY] = 3;
                            // BFS 탐색 계속
                            queue.offer(new Vertex(nX, nY));
                        }
                    }
                }
            }
            
            // 1초 경과 (매초마다 상태를 확인할 수 있다.)
            runTime++;
        }
        // BFS가 끝났을 때, 바이러스가 완전히 복제되었는지를 판단
        if(blankCnt == 0) {
            answer = Math.min(runTime, answer);
        }
    }
    
    static class Vertex {
        int x, y;
        
        public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}