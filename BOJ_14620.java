// [BOJ] 백준 14620 꽃길
// 문제: https://www.acmicpc.net/problem/14620
// 풀이: https://octorbirth.tistory.com/113

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static int N;
    static int[][] board;
    static List<Seed> list;
    static List<Seed> seeds;
    static int answer = Integer.MAX_VALUE;
    
    static class Seed{
        int ID;
        int x, y;
        public Seed(int x, int y, int ID) {
            this.x = x;
            this.y = y;
            this.ID = ID;
        }
        
        @Override
        public String toString() {
            return "[" + ID + "]";
        }
        
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        board = new int[N][N];
        int ID = 1;
        list = new ArrayList<>();
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                board[i][j] = Integer.parseInt(sc.next());
                // 꽃을 심을 수 있는 영역이라면
                if(i > 0 && i < N-1 && j > 0 && j < N-1) {
                    list.add(new Seed(i, j, ID++));
                }
            }
        }
        
        // 겹치는 것과 상관 없이 씨앗 3개 선택 (조합)
        seeds = new ArrayList<>();
        comb(0);
        
        System.out.println(answer);
    }


    private static void comb(int idx) {
        // 3개의 씨앗이 선택되었다면
        if(seeds.size() == 3) {
            plantCost(seeds);
            return;
        }
        
        if(idx >= list.size()) return;
        
        seeds.add(list.remove(idx));
        comb(idx);
        
        list.add(idx, seeds.remove(seeds.size() -1));
        comb(idx+1);
        
    }


    private static void plantCost(List<Seed> seeds) {
        // 만개되었을 때, 3개의 꽃이 겹치는지 확인
        for(int i=0; i<seeds.size(); i++) {
            Seed A = seeds.get(i);
            for(int j=0; j<seeds.size(); j++) {
                // 같은 씨앗 위치는 continue
                if(i == j) continue;
                Seed B = seeds.get(j);
                if(!isOk(A, B)) return;
            }
        }
        
        // 3개의 씨앗에 대한 비용을 계산한다.
        int cost = 0;
        for(int i=0; i<seeds.size(); i++) {
            Seed s = seeds.get(i);
            
            cost += board[s.x][s.y];
            cost += board[s.x-1][s.y]; cost += board[s.x][s.y-1];
            cost += board[s.x+1][s.y]; cost += board[s.x][s.y+1];
        }
        
        answer = Math.min(cost, answer);
    }


    private static boolean isOk(Seed A, Seed B) {
        if(Math.abs(A.x - B.x) + Math.abs(A.y - B.y) <= 2) return false;
        return true;
    }
}