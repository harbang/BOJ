// BOJ 9251 LCS
// https://octorbirth.tistory.com/96

/*
ACAYKP
CAPCAK

4
*/

import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		char[] A = sc.next().toCharArray();
		char[] B = sc.next().toCharArray();
		
        int[][] LCS = new int[A.length+1][B.length+1];
        
        for(int i=1; i<=A.length; i++) {
        	for(int j=1; j<=B.length; j++) {
        		
        		if(A[i-1] == B[j-1]) {
        			LCS[i][j] = LCS[i-1][j-1] + 1;
        		}
        		else {
        			LCS[i][j] = Math.max(LCS[i-1][j], LCS[i][j-1]);
        		}
        	}
        }
        
        System.out.println(LCS[A.length][B.length]);
	}
}