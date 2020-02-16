// [BOJ] 백준 1516 게임개발
// https://www.acmicpc.net/problem/1516

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    
    static ArrayList<ArrayList<Integer>> adList;
    static int N;
    static int[] indegree, buildTime, answer;
    static Queue<Integer> queue;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 정점의 개수
        N = Integer.parseInt(sc.next());
        indegree = new int[N+1];
        buildTime = new int[N+1];
        
        // 인접 리스트로 구현
        adList = new ArrayList<>();
        adList.add(new ArrayList<>());
                
        // 1~N까지의 각 정점을 리스트에 삽입
        for(int i=1; i<=N; i++) {
            adList.add(new ArrayList<>());
        }
        
        for(int i=1; i<=N; i++) {
            buildTime[i] = Integer.parseInt(sc.next());
            
            // 해당 건물을 짓기전에 미리 지어져야 하는 건물이 있다면
            int vertex = Integer.parseInt(sc.next());
            while(vertex != -1) {
                adList.get(vertex).add(i);
                indegree[i]++;
                vertex= Integer.parseInt(sc.next());
            }
            
        }
        answer = new int[N+1];
        queue = new LinkedList<>();
        // 위상정렬
        topological_sort();
        
        // 정답 출력
        for(int i=1; i<=N; i++) {
            System.out.println(answer[i]);
        }
        
    }

    private static void topological_sort() {
        for(int i=1; i<=N; i++) {
            if(indegree[i] == 0) {
                queue.add(i);
                answer[i] = buildTime[i];
            }
        }
        
        for(int i=1; i<=N; i++) {
            int pre = queue.poll();
            ArrayList<Integer> curNodeList = adList.get(pre);
            // 연결된 지점을 찾는다.
            while(!curNodeList.isEmpty()) {
                int current = curNodeList.remove(0);
                answer[current] = Math.max(answer[pre] + buildTime[current], answer[current]);
                
                // 진입 차수 갱신
                indegree[current]--;
                if(indegree[current] == 0) {
                    queue.add(current);
                }
            }
        }
    }
    
}