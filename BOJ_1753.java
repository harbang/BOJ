// [BOJ] 백준 1753 최단거리
// https://www.acmicpc.net/problem/1753

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int stoi(String S) {return Integer.parseInt(S);}
	static final int INF = 1000000000;
	static int N, M, K;
	static List<List<Node>> adList; // 인접 리스트
	static int[] dist; // 최소 거리 비용 테이블
	static boolean[] visited;
	static PriorityQueue<Node> pq;
	
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new  InputStreamReader(System.in));
        StringTokenizer st = null;
        
        st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken()); // 정점의 개수
        M = stoi(st.nextToken()); // 간선의 개수
        K = stoi(br.readLine()); // 출발점
        
        adList = new ArrayList<>();
        // 인덱스를 1부터 하기 위해 임의로 한 개를 넣어둠
        adList.add(new<Node> ArrayList());
        for(int i=1; i<=N; i++) {
            adList.add(new<Node> ArrayList());
        }
        // dist[] INF로 초기화
        dist = new int[N+1];
        Arrays.fill(dist, INF);
        
        while(M-- > 0) {
            st = new StringTokenizer(br.readLine());
        	int u = stoi(st.nextToken());
        	int v = stoi(st.nextToken());
        	int cost = stoi(st.nextToken());
        	adList.get(u).add(new Node(v, cost));
        }
       
        // 다익스트라 알고리즘
        dijkstra(K);
        
        // 정답출력
        printAnswer();
    }
     
    private static void dijkstra(int start) {
    	pq = new PriorityQueue<>();
    	visited = new boolean[N+1];
    	
    	dist[start] = 0;
    	pq.add(new Node(start, 0));
    	
    	while(!pq.isEmpty()) {
    		Node current =  pq.poll();
    		// 재방문 여부 확인
    		if(visited[current.ID]) continue;
    		visited[current.ID] = true;
    		// 연결된 정점들을 확인 
            for(Node next : adList.get(current.ID)) {
            	// 효율적인 처리를 위해 최소 거리 비용이 갱신되는 경우만 queue에 넣어줍니다.
            	if(dist[next.ID] > dist[current.ID] + next.distance) {
            		// 최소 거리 비용 갱신
                    dist[next.ID] = dist[current.ID] + next.distance;
                    pq.add(new Node(next.ID, dist[next.ID]));
                }
            }
    	}
	}
    
    public static void printAnswer() {
        StringBuilder sb = new StringBuilder();
        for (int i=1; i <= N; i++) {
        	sb.append((dist[i] >= INF ? "INF" : dist[i]));
        	sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}

class Node implements Comparable<Node>{
	int ID;
	int distance;
	
	public Node(int ID, int distance) {
		this.ID = ID;
		this.distance = distance;
	}
	
    @Override
    public int compareTo(Node target) {
          // 작은 거리 비용이 먼저 오도록
          return this.distance - target.distance;
    }
}