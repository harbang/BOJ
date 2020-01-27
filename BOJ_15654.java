// [BOJ] 백준 15654 N과 M (5)
// 문제: https://www.acmicpc.net/problem/15654
// 풀이: https://octorbirth.tistory.com/63

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	static List<Integer> list;
	static boolean[] visited;
	static int N, M;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());
        
        list = new ArrayList<>();
        
        for(int i=0; i<N; i++) {
        	list.add(Integer.parseInt(sc.next()));
        }
        
        // 중복순열을 체크하기 위함
        visited = new boolean[N];
        // 문제에서 요구한 오름차순으로 출력하기 위해 정렬 해둔다.
        Collections.sort(list);
        
        
        List<Integer> answer = new ArrayList<>();
        
        // 0번째 인덱스부터 선택하거나 안하거나
        permutation(answer); 
    }

    
	private static void permutation(List<Integer> answer) {
		// 문제에서 요구한 개수만큼 뽑았다면 출력
		if(answer.size() == M) {
			for(int i=0; i<answer.size(); i++) {
				System.out.print(answer.get(i) + " ");
			}
			System.out.println();
			return;
		}
		
		for(int i=0; i<N; i++) {
			// 아직 뽑지 않은 원소라면
			if(!visited[i]) {
				answer.add(list.get(i));
				visited[i] = true;
				
				permutation(answer);
				
				answer.remove(answer.size()-1);
				visited[i] = false;
			}
				
		}
	}
}