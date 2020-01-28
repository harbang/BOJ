// BOJ_1120 문자열
// 문제: https://www.acmicpc.net/problem/1120

import java.util.Scanner;

public class Main {    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        char A[] = sc.next().toCharArray();
        char B[] = sc.next().toCharArray();
        
        int answer = Integer.MAX_VALUE;
        // 문자열 길이 A <= B
        for(int i=0; i<=B.length - A.length; i++) {
        	int cnt = 0;
        	int k = i;
        	for(int j=0; j<A.length; j++) {
        		if(A[j] != B[k+j]) cnt++;
        	}
        	answer = Math.min(cnt, answer);
        }
        System.out.println(answer);
    }
}