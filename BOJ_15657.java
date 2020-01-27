  // [BOJ] 백준 15657 N과 M (8)
// 문제: https://www.acmicpc.net/problem/15657
// 풀이: https://octorbirth.tistory.com/63

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
    static int N, M;
    static List<Integer> list;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());
        
        list = new ArrayList<>();
        
        for(int i=0; i<N; i++) {
            list.add(Integer.parseInt(sc.next()));
        }
        
        Collections.sort(list);
        
        List<Integer> answer = new ArrayList<>();
        
        combination(answer, 0);
    }

	private static void combination(List<Integer> answer, int idx) {
		// M개 뽑았다면 출력
		if(answer.size() == M) {
            for(int i=0; i<answer.size(); i++) {
                System.out.print(answer.get(i) + " ");
            }
            System.out.println();
            return;
		}
		
		// 더 이상 뽑을 원소가 없다면 종료
		if(idx >= list.size()) return;
		
        answer.add(list.get(idx));
        combination(answer, idx);
        
        answer.remove(answer.size()-1);
        combination(answer, idx + 1);
		
	}
}