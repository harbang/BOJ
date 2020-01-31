// [BOJ] 백준 5373 큐빙
// 문제: https://www.acmicpc.net/problem/5373

import java.io.*;
import java.util.StringTokenizer;
 
class Main {
    static final int U = 0, D = 1, F = 2, B = 3, L = 4, R = 5;
    static char[][][] cube;
    static BufferedReader br;
    static BufferedWriter bw;
    
    public static void main(String[] args) throws IOException{
    	br = new BufferedReader(new  InputStreamReader(System.in));
        bw = new BufferedWriter(new  OutputStreamWriter(System.out));

        char[] colors = {'w','y','r','o','g','b'};
        
        int T = Integer.parseInt(br.readLine());
        
        while(T-- > 0) {
        	// 큐브 초기화
        	init(colors);
        	
        	int N = Integer.parseInt(br.readLine());
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	while(N-- > 0) {
        		String rotateInfo = st.nextToken();
        		// 방향 확인
        		boolean clk = rotateInfo.charAt(1) == '+';
            	switch (rotateInfo.charAt(0)) {
	        		case 'U': rotate(U, L, F, R, B, clk); break;
	        		case 'D': rotate(D, B, R, F, L, clk); break;
	        		case 'F': rotate(F, U, L, D, R, clk); break;
	        		case 'B': rotate(B, R, D, L, U, clk); break;
	        		case 'L': rotate(L, F, U, B, D, clk); break;
	        		case 'R': rotate(R, D, B, U, F, clk); break;
            	}
        	}
        	// 정답 출력
        	printAnswer();
        }
        bw.flush(); bw.close();
    }
    
	private static void rotate(int f, int u, int l, int d, int r, boolean clk) {
		char[][] temp = new char[3][3];
		char[] temp2 = new char[3];
		
		// 시계방향은 1번, 반시계 방향은 3번
		if(clk) {
			for (int i=0; i<3; ++i)
				for (int j=0; j<3; ++j) {
					temp[i][j] = cube[f][2-j][i];
				}
			for (int i=0; i<3; ++i) temp2[i] = cube[u][i][0];
			for (int i=0; i<3; ++i) cube[u][i][0] = cube[l][0][2-i];
			for (int i=0; i<3; ++i) cube[l][0][2-i] = cube[d][2][i];
			for (int i=0; i<3; ++i) cube[d][2][i] = cube[r][2-i][2];
			for (int i=0; i<3; ++i) cube[r][2-i][2] = temp2[i];
		}

        else {
            for (int i=0; i<3; ++i)
                for (int j=0; j<3; ++j) {
                    temp[i][j] = cube[f][j][2-i];
                }
            for (int i=0; i<3; ++i) temp2[i] = cube[u][i][0];
            for (int i=0; i<3; ++i) cube[u][i][0] = cube[r][2-i][2];
            for (int i=0; i<3; ++i) cube[r][2-i][2] = cube[d][2][i];
            for (int i=0; i<3; ++i) cube[d][2][i] = cube[l][0][2-i];
            for (int i=0; i<3; ++i) cube[l][0][2-i] = temp2[i];
        }
		
		cube[f] = temp;
	}

	private static void printAnswer() throws IOException{
    	for (int i=0; i<3; ++i) {
			for (int j=0; j<3; ++j) {
				bw.write(cube[0][j][2-i]);
			}
        	bw.write("\n");
        }
	}

	private static void init(char[] colors) {
        int n = colors.length;
        cube = new char[n][][];
        
        for (int i=0; i<n; ++i) {
            cube[i] = new char[3][3];
            for (int j=0; j<3; ++j){
            	for (int k=0; k<3; ++k) {
            		cube[i][j][k] = colors[i];
            	}
            }
        }
    }  
}