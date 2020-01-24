// BOJ 1958 LCS 3
// 문제: https://www.acmicpc.net/problem/1958
// 풀이: https://octorbirth.tistory.com/99

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		char[] A = sc.next().toCharArray();
		char[] B = sc.next().toCharArray();
		char[] C = sc.next().toCharArray();
		
        int[][][] LCS = new int[A.length+1][B.length+1][C.length+1];
        
        for(int i=1; i<=A.length; i++) {
        	for(int j=1; j<=B.length; j++) {
        		for(int k=1; k<=C.length; k++) {
        			
        			if(A[i-1] == B[j-1] && B[j-1] == C[k-1] && A[i-1] == C[k-1]) {
            			LCS[i][j][k] = LCS[i-1][j-1][k-1] + 1;
            		}
            		else {
            			if (LCS[i - 1][j][k] > LCS[i][j - 1][k] || LCS[i - 1][j][k] > LCS[i][j][k - 1])
            				    LCS[i][j][k] = LCS[i - 1][j][k];

        				else if (LCS[i][j][k - 1] > LCS[i - 1][j][k] || LCS[i][j][k - 1] > LCS[i][j - 1][k])
        				    LCS[i][j][k] = LCS[i][j][k - 1];

        				else 
        				    LCS[i][j][k] = LCS[i][j - 1][k];
            		}	
        		}        		
        	}
        }
        // 정답 출력
        System.out.println(LCS[A.length][B.length][C.length]);
	}
}