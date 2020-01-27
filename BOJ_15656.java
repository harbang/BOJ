// [BOJ] 백준 15657 N과 M (7)
// 문제: https://www.acmicpc.net/problem/15656
// 풀이: https://octorbirth.tistory.com/63
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	static int N, M;
	static List<Integer> list;
	static BufferedWriter log;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        log = new BufferedWriter(new OutputStreamWriter(System.out));
        
        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());
        list = new ArrayList<>();
        
        for(int i=0; i<N; i++) {
        	list.add(Integer.parseInt(sc.next()));
        }
        
        Collections.sort(list);
        
        List<Integer> answer = new ArrayList<>();
        
        permutation(answer); 
        
        log.flush();
    }
    
	private static void permutation(List<Integer> answer) throws IOException{
		if(answer.size() == M) {
			for(int i=0; i < answer.size(); i++) {
				log.write(answer.get(i) + " ");
			}
			log.write("\n");
			return;
		}
		
		// 중복을 제거한 순열과의 차이는 방문표시를 하지 않는 것이다.		
		for(int i=0; i<N; i++) {
			answer.add(list.get(i));
			permutation(answer);
			
			// 방금 넣었던 것을 제거하고 다음 for문을 돌수 있도록
			answer.remove(answer.size()-1);
		}
		
	}
}