// [BOJ] 백준 1149 RGB거리
// https://www.acmicpc.net/problem/1149

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());

        int[] R = new int[n];
        int[] G = new int[n];
        int[] B = new int[n];

        for (int i = 0; i < n; i++) {
            R[i] = Integer.parseInt(sc.next());
            G[i] = Integer.parseInt(sc.next());
            B[i] = Integer.parseInt(sc.next());
        }
    // 초기값으로 각각의 색만 선택한 것으로 저장
        int choiceR = R[0], choiceG = G[0], choiceB = B[0];
        
        for (int i = 1; i < n; i++) {

            int cumulativeR = choiceR, cumulativeG = choiceG, cumulativeB = choiceB;

            choiceR = R[i] + minOfValues( cumulativeG , cumulativeB );
            choiceG = G[i] + minOfValues( cumulativeR , cumulativeB );
            choiceB = B[i] + minOfValues( cumulativeR , cumulativeG );
        }

        System.out.println(minOfValues(choiceR, choiceG, choiceB));

    }

    private static int minOfValues( Integer... arr ) {
        int min = 0;
        if (arr.length > 0) {
            min = arr[0];
        }

        for (int i = 1; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }
}