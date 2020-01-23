// 백준 10211 Maximum Subarray 문제로 최대 부분 배열을 구하는 문제
// DP 방식 이용했으면 자세한 설명은 다음 주소 참고
// https://octorbirth.tistory.com/94

import java.util.Scanner;

public class Main {    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int T = Integer.parseInt(sc.next());
        while(T-- > 0) {
        	int N = Integer.parseInt(sc.next());;
        	int[] arr = new int[N];
        	for(int i=0; i<N; i++) {
        		arr[i] = Integer.parseInt(sc.next());
        	}
        	System.out.println(findMaximumSubArray(arr));
        }
    }

	private static int findMaximumSubArray(int[] A) {
		int answer = Integer.MIN_VALUE;
		int partialSum = Integer.MIN_VALUE;
		
		for(int i=0; i<A.length; i++) {
			partialSum = Math.max(partialSum, 0) + A[i];
			answer = Math.max(answer, partialSum);
		}
		
		return answer;
	}
}