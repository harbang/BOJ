// [BOJ] 백준 2056 작업
// https://www.acmicpc.net/problem/2056

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static int N;
    static int[] indegree, workingTime, cost;
    static ArrayList<ArrayList<Integer>> adList;
    static Queue<Integer> queue;
    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        
        // 그래프를 인접 리스트로 표현
        adList = new ArrayList<>();
        adList.add(new ArrayList<>());
        
        for(int i=0; i<N; i++) {
            adList.add(new ArrayList<>());
        }
        
        // 작업시간
        workingTime  = new int[N+1];
        // 진입차수
        indegree = new int[N+1];
        // 건물간의 우선순위를 생각한 비용들
        cost = new int[N+1];
        
        // 작업시간과 정점간 간선을 연결(우선 작업이 무엇인지)
        for(int i=1; i<=N; i++) {
            workingTime[i] = Integer.parseInt(sc.next());
            
            int edgeCnt = Integer.parseInt(sc.next());
            while(edgeCnt-- > 0) {
                int head = Integer.parseInt(sc.next());
                adList.get(head).add(i);
                // 진입 차수 개수도 같이 counting
                indegree[i]++;
            }
        }
        
        queue = new LinkedList<>();
        topological_sort();
        
        int answer = 0;
        for(int i=1; i<=N; i++) {
            answer = answer > cost[i] ? answer: cost[i];
        }
        System.out.println(answer);
    }

    private static void topological_sort() {
        // 처음 선행작업이 필요없는 작업번호를 찾는다.(문제상 1번 작업만 그것에 해당된다.)
        for(int i=1; i<=N; i++) {
            if(indegree[i] == 0) {
                cost[i] = workingTime[i];
                queue.add(i);
            }
        }
        
        // queue에 있는 '작업' 한개를 꺼내 연결되어 있는 작업들이 무엇인지 확인한다.
        for(int i=1; i<=N; i++) {
            int curNode = queue.poll();
            
            ArrayList<Integer> curNodeList = adList.get(curNode);
            while(!curNodeList.isEmpty()) {
                int target = curNodeList.remove(0);
                indegree[target]--;
                
                // 문제에서 요구한 해당 작업이 완료되기 위한 최소시간을 비교하며 도출한다.
                cost[target] = Math.max(cost[target], workingTime[target] + cost[curNode] );
                
                // indegree 개수가 새롭게 '0'이 되면 queue에 넣는다.
                if(indegree[target] == 0) {
                    queue.add(target);
                }
            }
        }
    }
}