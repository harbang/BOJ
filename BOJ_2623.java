// [BOJ] 백준 2623 음악프로그램
// https://www.acmicpc.net/problem/2623

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static boolean[] finished, visited;
    static Stack<Integer> stack;
    static int answer;
    static ArrayList<ArrayList<Integer>> adList;
    static final int SUCCESS = 1;
    static final int IMPOSSIBLE = 2;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 정점의 개수
        int N = Integer.parseInt(sc.next());
        
        // 인접 리스트로 구현
        adList = new ArrayList<>();
        adList.add(new ArrayList<>());
        for(int i=1; i<=N; i++) adList.add(new ArrayList<>());
        
        // 보조 PD의 수
        int M = Integer.parseInt(sc.next());
        
        for(int i=1; i<=M; i++) {
            int singerCnt = Integer.parseInt(sc.next());
            
            // 맡은 출연 가수가 0개이거나 1개이면 의미가 없다.
            if(singerCnt <= 1 ) continue;
            
            // 우선 제일 처음 할 가수를 입력 받는다.
            int vertex = Integer.parseInt(sc.next());
            int afterVertex;
            // 주어진 개수에 맞게 마지막 간선 연결을 제외하고 간선을 잇는다.
            for(int j=1; j<=singerCnt-2; j++) {
                afterVertex = Integer.parseInt(sc.next());
                adList.get(vertex).add(afterVertex);
                vertex = afterVertex;
            }
            // 마지막 간선을 잇는다.
            afterVertex = Integer.parseInt(sc.next());
            adList.get(vertex).add(afterVertex);
        }
        
        visited = new boolean[N+1];
        finished = new boolean[N+1];
        stack = new Stack<>();
        
        answer = SUCCESS;
        for(int i=1; i<=N; i++) {
            // 위상정렬 완료된 정점이라면 pass
            if(finished[i]) continue;
            // 아직 완료되지 않은 정점이라면 DFS를 통해 탐색
            DFS(i);
            if(answer == IMPOSSIBLE) {
                System.out.println("0");
                return;
            }
        }
        // 정답 출력
        while(!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    private static void DFS(int v) {
        // 이번 DFS에서 이미 방문한 곳이면 사이클이 형성된 것
        if(visited[v]) {
            answer = IMPOSSIBLE;
            return;
        }
        // DFS 탐색 도중 이미 위상정렬 배치가 완료된 정점이라면 pass
        if(finished[v]) return;
        
        // 현재 방문한 곳 표시
        visited[v] = true;
        
        // 현재 정점과 연결된 모든 간선에 대하여 DFS를 하여야 한다.
        while(!adList.get(v).isEmpty()) {
            int next_v = adList.get(v).remove(0);
            DFS(next_v);
        }
        // 더이상 DFS할 연결된 정점이 없다면 스택에 넣어준다.
        stack.push(v);
        finished[v] = true;
        // 다음 DFS할 때 사이클 여부를 확인하기 위해 visited를 초기화 시켜둔다.
        visited[v] = false;
    }
}