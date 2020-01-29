// [BOJ] 17140 백준 이차원 배열과 연산
// 문제: https://www.acmicpc.net/problem/17140

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    
    static int R=3, C=3;
    static int[][] arr;
    static int answer = -1;
    
    static class Vertex implements Comparable<Vertex>{
        int val;
        int cnt;
        
        public Vertex(int val, int cnt) {
            this.val = val;
            this.cnt = cnt;
        }
        
        @Override
        public int compareTo(Vertex target) {
            // 나타난 횟수가  작으면 먼저 오도록
            if(this.cnt < target.cnt) return -1;
            else if (this.cnt > target.cnt) return 1;
            // 나타난 횟수가 동일하면 숫자 크기 순으로
            else return (this.val <= target.val) ? -1 : 1;
            
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int r = Integer.parseInt(sc.next());
        int c = Integer.parseInt(sc.next());
        
        int k = Integer.parseInt(sc.next());
        
        // 100 x 100
        arr = new int[100][100];
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                arr[i][j] = Integer.parseInt(sc.next());
            }
        }
        
        // 100번 시도해서 실패하면 『-1』 출력
        for(int tryCnt = 0; tryCnt <= 100; tryCnt++) {
            if(arr[r-1][c-1] == k) {
                answer = tryCnt;
                break;
            }
            // R 연산
            if(R >= C) rOperation();
            
            
            // C 연산
            else cOperation();


            // 디버깅 시 주석해제
            // printArr();  
            
        }


        System.out.println(answer);        
    }


    private static void rOperation() {
        Map<Integer, Integer> map;
        PriorityQueue<Vertex> pq;
        int[][] temp = new int[100][100];
        int tempC = 0;

        // 모든 행에 대해
        for(int i=0; i<R; i++) {
            map = new HashMap<Integer, Integer>();
            for(int j=0; j<C; j++) {
                // 0 이면 연산 대상 X
                if (arr[i][j] == 0) continue;
                // 등장 횟수 count
                if(map.get(arr[i][j]) == null) map.put(arr[i][j], 1);
                else map.put(arr[i][j], map.get(arr[i][j]) + 1);
            }
            
            pq = new PriorityQueue<>();
            // 등장횟수가 많고, 숫자 크기 순으로 정렬
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                pq.add(new Vertex(entry.getKey(), entry.getValue()));
            }
            
            // tempC 크기 갱신
            tempC = (tempC < pq.size() * 2) ? pq.size() * 2 : tempC;
            if (tempC > 100) tempC = 100;
            
            // 해당 열을 재배치
            int col = 0;
            while(!pq.isEmpty()) {
                Vertex v = pq.poll();
                temp[i][col] = v.val;
                temp[i][col + 1] = v.cnt;
                
                col = col + 2;
                if(col >= 100) {
                    // 100개 이상이면 나머지는 버려준다.
                    break;
                }
            }
            
            pq.clear();
            map.clear();
        }
        
        C = tempC;
        deepCopy(temp);
    }
    
    private static void deepCopy(int[][] temp) {
        arr = new int[100][100];
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                System.arraycopy(temp[i], 0, arr[i], 0, temp[i].length);
            }
        }
        
    }


    private static void cOperation() {
        Map<Integer, Integer> map;
        PriorityQueue<Vertex> pq;
        int[][] temp = new int[100][100];
        int tempR = 0; // 연산 시작 시 열의 개수
        // 모든 행에 대해
        for(int j=0; j<C; j++) {
            map = new HashMap<Integer, Integer>();
            for(int i=0; i<R; i++) {
                // 0 이면 연산 대상 X
                if (arr[i][j] == 0) continue;
                // 등장 횟수 count
                if(map.get(arr[i][j]) == null) map.put(arr[i][j], 1);
                else map.put(arr[i][j], map.get(arr[i][j]) + 1);
            }
            
            pq = new PriorityQueue<>();
            // 등장횟수가 많고, 숫자 크기 순으로 정렬
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                pq.add(new Vertex(entry.getKey(), entry.getValue()));
            }
            
            // tempR 크기 갱신
            tempR = (tempR < pq.size() * 2) ? pq.size() * 2 : tempR;
            if (tempR > 100) tempR = 100;
            
            // 해당 열을 재배치
            int row = 0;
            while(!pq.isEmpty()) {
                Vertex v = pq.poll();
                temp[row][j] = v.val;
                temp[row+1][j] = v.cnt;
                
                row = row + 2;
                if(row >= 100) {
                    // 100개 이상이면 나머지는 버려준다.
                    break;
                }
            }
            
            pq.clear();
            map.clear();
        }
        
        R = tempR;
        deepCopy(temp);
    }
    
    private static void printArr() {
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                System.out.printf("%2d ", arr[i][j]);
            }
            System.out.println();
        }    
    }
}