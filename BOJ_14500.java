// [BOJ] 백준 14500 테트로미노
// https://www.acmicpc.net/problem/14500

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    
    static int[][] paper;
    static boolean[][] visited;
    static final int TETROMINO = 4;
    static int N, M;
    // 상하좌우 좌표
    static int[] dx = {-1, 1, 0, 0}, dy={0, 0, -1, 1};
    static int answer;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        paper = new int[N][M];
        
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        visited = new boolean[N][M];
        
        // 모든 좌표에 대해 가능한 도형을 확인
        for(int i=0; i<N; i++) {
            for(int j=0;j<M;j++) {
                Node node = new Node(i, j);
                visited[i][j] = true;
                // 시작 자체가 깊이는 '1'
                node.depth = 1;
                // DFS 탐색을 하면서 바로 수들의 합을 구한다.
                node.sum = paper[i][j];
                
                // 'ㅏ'이외의 도형에 대한 결과 처리
                DFS(node);
                // 'ㅏ'에 대한 회전 및 대칭형태에 대한 결과 구하기
                specialShapeCheck(node);
                visited[i][j] = false;
            }
        }
        // 정답 출력
        System.out.println(answer);
        
        
    }
    private static void DFS(Node curNode) {
        // 탐색 깊이가 4로 테트로미노를 형성했다면...
        if(curNode.depth == TETROMINO) {
            answer = Math.max(answer, curNode.sum);
            return;
        }
        
        // 상하좌우로 이동
        for(int i=0; i<4; i++) {
            int next_x = curNode.x + dx[i];
            int next_y = curNode.y + dy[i];
            
            // 배열의 범위를 벗어나면 무효
            if(next_x < 0 || next_y < 0) continue;
            if(next_x >= N || next_y >= M) continue;
            
            
            // 재방문하려는 방향은 제외
            if(visited[next_x][next_y]) continue;
            
            // 해당 지점을 방문해서 그곳에서 또 DFS 탐색
            visited[next_x][next_y] = true;
            
            Node nextNode = new Node(next_x, next_y);
            nextNode.depth = curNode.depth + 1;
            // DFS 탐색되어가는 수를 더한다.
            nextNode.sum = curNode.sum + paper[next_x][next_y];
            DFS(nextNode);
            
            // 다른 DFS할 때 방문표시를 사용하기 위해 초기화
            visited[next_x][next_y] = false;
        }
    }
    
    private static void specialShapeCheck(Node middle) {
        // 함수가 호출된 순간 중앙좌표는 확보 된 것이다.
        
        for(int i=0; i<4; i++) {
            // 중앙점의 값으로 초기화
            int total = paper[middle.x][middle.y];
            // 튀어나온 좌표
            int bulge_x = middle.x + dx[i];
            int bulge_y = middle.y + dy[i];
            
            if(bulge_x < 0 || bulge_y < 0 ) continue;
            if(bulge_x >= N || bulge_y >= M) continue;
            total += paper[bulge_x][bulge_y];
            // 'ㅗ', 'ㅜ'인 경우
            if(i < 2) {
                // 배열 범위를 초과하면 continue
                if(middle.y - 1 < 0 || middle.y + 1 >= M ) continue;
                total += paper[middle.x][middle.y - 1]; total += paper[middle.x][middle.y + 1];
                answer = Math.max(answer, total);
            }
            // 'ㅓ', 'ㅜ'인 경우
            else {
                if(middle.x - 1 < 0 || middle.x + 1 >= N ) continue;
                total += paper[middle.x-1][middle.y]; total += paper[middle.x+1][middle.y];
                answer = Math.max(answer, total);
            }
        }        
    }
}

class Node{
    int x, y;
    int depth, sum;
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}