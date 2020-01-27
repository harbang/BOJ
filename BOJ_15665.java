// [BOJ] 백준 15665 N과 M(11)
// 문제: https://www.acmicpc.net/problem/15665
// 풀이: https://octorbirth.tistory.com/63
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	
	static int N, M;
	static List<Integer> list;
	static BufferedWriter log;
	static Map<String, String> map;
	
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
        map = new HashMap<String, String>();
        permutation(answer); 
        
        log.flush();
    }
    
	private static void permutation(List<Integer> answer) throws IOException{
		if(answer.size() == M) {
            String str = "";
            for(int i=0; i < M; i++) {
                str += answer.get(i);
            }
            // 기존에 없는 결과이면
            if(map.get(str) == null) {
    			for(int i=0; i < answer.size(); i++) {
    				log.write(answer.get(i) + " ");
    			}
    			log.write("\n");
    			map.put(str, str);
            }

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