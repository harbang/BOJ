// [BOJ] 백준 1012 유기농 배추
// https://www.acmicpc.net/problem/1012

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int T = Integer.parseInt(sc.nextLine());
        List<Integer> answer = new ArrayList<>();
        
        for(int idx=0; idx<T ; idx++) {
            
            int M = Integer.parseInt(sc.next());
            int N = Integer.parseInt(sc.next());
            int K = Integer.parseInt(sc.next());
            
            int arr[][] = new int[M][N];
            
            
            for(int i=0; i<K; i++) {
                int x = Integer.parseInt(sc.next());
                int y = Integer.parseInt(sc.next());
                arr[x][y] = 1;
            }
            
            int count = 0;
            
            for(int i=0; i<M; i++) {
                for(int j=0; j<N; j++) {
                    
                    // 각각의 좌표에 대해서 배추가 있는지 확인한다. - '1'
                    if (arr[i][j] == 1) { // 배추가 존재한다면 해당 지점을 기점으로 인접한 배추의 좌표의 값들을 '0'으로 바꾼다.
                        count++;
                        earthWorm(i,j,arr);
                        
                    }
                }
            }
            answer.add(count);
        }
        
        for(int idx=0; idx<T; idx++) {
            System.out.println(answer.get(idx));
        }
        
    }
    
    public static void earthWorm(int x, int y, int[][] arr) {
        arr[x][y] = 0;
        
        // 현재 좌표 왼쪽
        if(x-1 >= 0 && arr[x-1][y] == 1) {
            arr[x-1][y] = 0;
            earthWorm(x-1,y,arr);
        }
        
        // 현재 좌표 오른쪽
        if(x+1 < arr.length && arr[x+1][y] == 1) {
            arr[x+1][y] = 0;
            earthWorm(x+1,y,arr);
        }
        
        // 현재 좌표 아래쪽
        if(y-1 >= 0 && arr[x][y-1] == 1) {
            arr[x][y-1] = 0;
            earthWorm(x,y-1,arr);
        }
        
        // 현재 좌표 위쪽
        if(y+1 < arr[x].length && arr[x][y+1] == 1) {
            arr[x][y+1] = 0;
            earthWorm(x,y+1,arr);
        }
    }
}