// [BOJ] 백준 17822 원판 돌리기
// https://www.acmicpc.net/problem/17822

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {	
	static int N, M, T;
	static int x, d, k;
	static int[][] disk;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static boolean[][] visited;
	static Queue<Node> queue;
	static List<Node> list;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new   InputStreamReader(System.in));
        StringTokenizer st = null;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        
        disk = new int[N+1][M+1];
        for(int i=1; i<=N; i++) {
        	st = new StringTokenizer(br.readLine());
        	for(int j=1; j<=M; j++) {
        		disk[i][j] = Integer.parseInt(st.nextToken());
        	}
        }
        
        while(T-- > 0) { // 회전 정보 개수
        	st = new StringTokenizer(br.readLine());
        	// 회전 원판 (x의 배수)
        	x = Integer.parseInt(st.nextToken());
        	// 회전 방향(시계(0), 반시계(1))
        	d = Integer.parseInt(st.nextToken());
        	// 회전 크기
        	k = Integer.parseInt(st.nextToken());
        	
        	// x 배수에 해당하는 원판들을 독립적으로 회전
        	for(int order=x; order<=N; order = order+x) {
        		rotate(order);
        	}
        	// 인접한 좌표간 동일한 숫자가 있는지 확인
        	if(!findSameNumber()) {
        		// 인접한 곳에 동일한 숫자가 발견되지 않은 경우
        		// 평균에 따른 처리
        		actionByAvg();
        	}
        }
        // 정답출력
        printAnswer();
    }
     
    private static void actionByAvg() {
		double sum = 0;
		double cnt = 0;
		double avg = 0;
		// 평균값 구하기
		for(int r=1 ; r <= N ; ++r) {
			for(int c=1 ; c <= M ; ++c) {
				if(disk[r][c] > 0) {
					sum += disk[r][c];
					cnt++;
				}
			}
		}
		if(cnt == 0) return;
		avg = sum / cnt;
		
		// 평균값을 기준으로 지워지지 않은 숫자에 대한 처리
		for(int r = 1 ; r <= N ; ++r) {
			for(int c = 1 ; c <= M ; ++c) {
				if(disk[r][c] == 0) continue;
				if(disk[r][c] < avg) disk[r][c] += 1;
				else if(disk[r][c] > avg) disk[r][c] -= 1;
			}
		}
	}

	private static boolean findSameNumber() {
		boolean isSameNum = false;
		visited = new boolean[N+1][M+1];
		queue = new LinkedList<Node>();
		// 인접한 좌표간 동일한 숫자들이 몇개 있는지 확인하는 리스트
		list = new ArrayList<Node>();
		// 모든 영역에 대해 BFS 탐색
		for(int r=1 ; r <= N ; r++) {
			for(int c=1 ; c <= M ; c++) {
				// 이미 방문한 곳이거나 제거된 곳이면 continue
				if(disk[r][c] == 0 || visited[r][c]) continue;
				Node current = new Node(r, c);
				list.add(current);
				queue.offer(current);
				
				// 기준값 전달
				BFS(disk[r][c]);
				// BFS결과 인접한 곳에 동일한 숫자가 2개 이상 있다면
				if(list.size() > 1) {
					isSameNum = true;
					for(Node node : list) {
						// 원판에서 숫자 제거
						disk[node.r][node.c] = 0;
					}
				}
				list.clear();
			}
		}
		return isSameNum;
	}

	private static void BFS(int targetNum) {
		while(!queue.isEmpty()) {
			Node current = queue.poll();
			
			for(int i = 0 ; i < 4 ; ++i) {
				int nR = current.r + dr[i];
				int nC = current.c + dc[i];
				
				// 한 행에서 양쪽 끝은 원판에서 인접한 좌표 
				if(nC > M) nC = 1;
				else if(nC < 1) nC = M;
				
				// 범위를 벗어나거나 방문한 곳이면 continue
				if(nR > N || nR < 1 || visited[nR][nC]) continue;
				
				// 인접한 좌표가 같은 숫자인 경우
				if(disk[nR][nC] == targetNum) {
					visited[nR][nC] = true;
					Node next = new Node(nR, nC);
					queue.offer(next);
					list.add(next);
				}
			}
		}
	}

	private static void rotate(int order) {
    	// 주어진 회전 크기만큼 원판 회전시킵니다.
		for(int cnt=1; cnt<=k; cnt++) {
			// 시계 방향
			if(d == 0) { 
		    	int temp = disk[order][M];
		    	for(int i=M; i>1; i--) {
		    		disk[order][i] = disk[order][i-1];
		    	}
		    	disk[order][1] = temp;
	    	}
			// 반시계 방향
	    	else { 
	    		int temp = disk[order][1];
		    	for(int i=1; i<M; i++) {
		    		disk[order][i] = disk[order][i+1];
		    	}
		    	disk[order][M] = temp;
	    	}
		}
	}

	public static void printAnswer() {
		int sum = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=1; i<=N; i++) {
        	for(int j=1; j<=M; j++) {
        		sum += disk[i][j];
        	}
        }
        sb.append(sum);
        System.out.println(sb.toString());
    }
}

class Node{
	int r, c;
	Node(int r, int c){
		this.r = r;
		this.c = c;
	}
}