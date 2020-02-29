// [BOJ] 백준 17837 새로운 게임 2
// https://www.acmicpc.net/problem/17837

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static final int WHITE = 0;
	static final int RED = 1;
	static final int BLUE = 2;
	static int N, K;
	static int[][] colorMap;
	static Horse[] horse; 
	static ArrayList<Integer>[][] board;
	// → ← ↑ ↓
    static int[] dx = {0, 0, 0, -1, 1};
    static int[] dy = {0, 1, -1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		// 색깔 정보와 각 칸의 말들의 정보를 초기화 
		colorMap = new int[N+1][N+1];
		board = new ArrayList[N+1][N+1];
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				colorMap[i][j] = Integer.parseInt(st.nextToken());
				board[i][j] = new ArrayList<>();
			}
		}
		horse = new Horse[K+1];
		for(int ID=1; ID<=K; ID++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int dir = Integer.parseInt(st.nextToken());
			horse[ID] = new Horse(ID, x, y, dir);
			// 체스판에 말들을 배치
			board[x][y].add(ID);
		}
		
		// 최대 1000번 동안 진행
		for(int turn = 1; turn <= 1000; turn++) {
			// 1~K개의 말들을 순차적으로 움직입니다.
			for(int idx = 1 ; idx <= K ; idx++) {
				// 1000번의 턴 중에 조건을 만족하면 종료.
				if(move(idx)) {
					System.out.println(turn);
					return;
				};
			}
		}
		System.out.println(-1);
	}
	
	private static boolean move(int idx) {
		Horse current = horse[idx];
		
		int nX = current.x + dx[current.dir];
		int nY = current.y + dy[current.dir];
		
		// 체스판을 벗어나거나 파란색 칸인 경우
		if(!isRangeValid(nX, nY)) {
			// 이동하는 말의 방향을 반대로 전환하여 이동하려는 칸 위치를 함께 바꿔준다.
			current.changeDirection();
		    nX = current.x + dx[current.dir];
		    nY = current.y + dy[current.dir];
		}
		

		// 체스판을 벗어나거나 파란색 칸이면 위에서 방향전환 된 것으로 continue
		// 체스판 내에서 빨간색이나 흰색칸으로 이동되는 경우
		if(isRangeValid(nX, nY)) {
			actionByColor(current, nX, nY);
			// 이동된 칸에 올려져 있는 말의 개수 확인 
			if(board[nX][nY].size() >= 4) return true;
		}
		return false;
	}
	
	private static void actionByColor(Horse current, int nX, int nY) {
		// 이동 전 말의 위치
		ArrayList<Integer> from = board[current.x][current.y];
		// 이동 후 말의 위치
		ArrayList<Integer> to = board[nX][nY];
		// 이동하는 말이 위치한 칸에서 놓여져 있는 위치(높이)
		int position = from.indexOf(current.ID);
		// 원소가 변하면서 size()값도 함께 변하므로 변수로 저장
		int fromSize = from.size();
		
		// 흰색 or 빨간색에 따른 처리
		switch(colorMap[nX][nY]) {
		case WHITE:
		    // 이동하는 말 위에 올려져 있는 말들과 함께 이동
		    for(int i = position; i < fromSize; i++){
		    	// 리스트의 원소가 삭제되면서 위치가 당겨져 옵니다.
		        int horseID = from.remove(position);
		        // 해당 말의 좌표 변경
		        horse[horseID].x = nX;
		        horse[horseID].y = nY;
		        to.add(horseID);
		    }
		    break;
		    
		case RED:
			// 이동하는 말 위에 올려져 있는 말들을 뒤집어서 함께 이동 
			for(int i = position; i < fromSize; i++){
		    	// 뒤집어서 다음 위치로 이동해야 하기에 리스트 끝자리에서 삭제
		    	int horseID = from.remove(from.size()-1);
		        // 해당 말의 좌표 변경
		        horse[horseID].x = nX;
		        horse[horseID].y = nY;
		        to.add(horseID);
		    }
		    break;
		}
	}
	
	private static boolean isRangeValid(int nX, int nY) {
		if( nX > N || nX < 1 || nY > N || nY < 1 || colorMap[nX][nY] == BLUE) {
			// 체스판을 벗어나거나 파란색 칸인 경우
			return false;
		}
		return true;
	}
}

class Horse{
	int ID, x, y, dir;
	Horse(int ID, int x, int y, int dir){
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public void changeDirection() {
		// → ← ↑ ↓
		if(dir == 1) dir = 2; 
		else if(dir == 2) dir = 1;
		else if(dir == 3) dir = 4;
		else if(dir == 4) dir = 3;
	}
}