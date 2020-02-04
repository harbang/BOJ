// [BOJ] 백준 17406 배열 돌리기 4
// https://www.acmicpc.net/problem/17406

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static int[][] arr;
    static List<Rotate> list;
    static List<Rotate> rotateOrder;
    static boolean[] visited;
    static int N, M, K;
    static int answer = Integer.MAX_VALUE;
    
    static class Rotate{
        int ID;
        int r, c, s;
        
        public Rotate(int r, int c, int s, int ID) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.ID = ID;
        }

        @Override
        public String toString() {
            return "[" + ID + "] ";
        }
    }
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());        
        K = Integer.parseInt(sc.next());
        arr = new int[N+1][M+1];
        
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=M; j++) {
                arr[i][j] = Integer.parseInt(sc.next());
            }
        }
        
        list = new ArrayList<>();
        for(int i=0; i<K; i++) {
            list.add(new Rotate(
                    Integer.parseInt(sc.next()), // r
                    Integer.parseInt(sc.next()), // c
                    Integer.parseInt(sc.next()), // s
                    i // ID
                ));
        }
        
        visited = new boolean[K];
        rotateOrder = new ArrayList<>();
        perm();
        
        System.out.println(answer);
    }

    private static void perm() {
        if(rotateOrder.size() == K) {
            rotate(deepCopy(arr), rotateOrder);
            return;
        }
        
        for(int i=0; i<K; i++) {
            // 방문하지 않은 곳이라면
            if(!visited[i]) {
                rotateOrder.add(list.get(i));
                visited[i] = true;
                
                perm();
                
                rotateOrder.remove(rotateOrder.size()-1);
                visited[i] = false;
            }
        }
    }

    private static int[][] deepCopy(int[][] src) {
        int[][] dest = new int[N+1][M+1];
        for(int i=1; i<=N; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
        }
        return dest;
    }

    private static void rotate(int[][] arr, List<Rotate> rotateOrder) {
        // 주어진 회전 순서만큼
        for(int r=0; r<rotateOrder.size(); r++) {
            // 왼쪽 상단의 기준점
            Rotate obj = rotateOrder.get(r);
            int x = obj.r - obj.s;
            int y = obj.c - obj.s;
            
            // 최초 바깥쪽 테두리의 길이
            int len = (obj.s * 2) + 1;
            
            // 우, 하, 좌, 상(감싸는 방향)
            int[] dx = {0, 1, 0, -1};
            int[] dy = {1, 0, -1, 0};
            
            List<Integer> temp = new ArrayList<>();
            while(true) {
                for(int i=0; i<4; i++) {
                    // 할당된 테두리만큼 원소를 리스트에 담는다.
                    for(int l=1; l<len; l++) {
                        x = x + dx[i];
                        y = y + dy[i];
                        temp.add(arr[x][y]);
                    }
                }
                // 한 번 회전환 순서로 배치
                temp.add(0, temp.remove(temp.size()-1));
                
                // 재배치된 숫자를 다시 해당 테두리에 할당
                for(int i=0; i<4; i++) {
                    // 할당된 테두리만큼 원소를 리스트에 담는다.
                    for(int l=1; l<len; l++) {
                        x = x + dx[i];
                        y = y + dy[i];
                        arr[x][y] = temp.remove(0);
                    }
                }
                
                // 테두리 반경 줄여나가기
                len = len - 2;
                // 기준점도 따라서 재설정
                x++; y++;
                // 회전할 필요가 없을 때까지
                // 짝수로 시작했으면 0 / 홀수로 시작했으면 1
                if (len == 0 || len == 1) break;
            }
        }
        // 각각의 회전 순서 중 최솟값
        answer = Math.min(answer, solve(arr));
    }


    // 주어진 회전 순서에서 배열의 최솟값
    private static int solve(int[][] arr) {
        int temp = Integer.MAX_VALUE;
        for(int i=1; i<=N; i++) {
            int sum = 0;
            for(int j=1; j<=M; j++) {
                sum += arr[i][j];
            }
            temp = Math.min(temp, sum);
        }
        return temp;
    }
}