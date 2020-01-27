// [BOJ] 백준 15664 N과 M (10)
// 문제: https://www.acmicpc.net/problem/15664
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
	
	static List<Integer> list;
	static Map<String, String> map;
	static BufferedWriter log;
	static int N, M;
	
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        log = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());
        
        // 숫자를 저장할 리스트
        list = new ArrayList<>();
        
        for(int i=0; i<N; i++) {
        	list.add(Integer.parseInt(sc.next()));
        }
        
        // 문제에서 요구한 오름차순으로 출력하기 위해 정렬 해둔다.
        Collections.sort(list);
        
        // 순열 구성된 숫자들의 리스트
        List<Integer> answer = new ArrayList<>();
        map = new HashMap<String, String>();
        // (중복없는) 조합리스트와 '0'번째 부터 뽑을지 안 뽑을지 재귀적으로 탐색
        combination(answer, 0); 
        log.flush();
    }

    
	private static void combination(List<Integer> answer, int idx) throws IOException{
		// 문제에서 요구한 개수만큼 뽑았다면 출력
		if(answer.size() == M) {
			
            String str = "";
            for(int i=0; i < M; i++) {
                str += answer.get(i);
            }
            // 기존에 없는 조합이라면
            if(map.get(str) == null) {
            	// 정답 출력 
            	for(int i=0; i<answer.size(); i++) {
            		log.write(answer.get(i) + " ");
            	}
            	log.write("\n");
            	map.put(str, str);
            }
			return;
		}
		// 더 이상 뽑을 원소가 없다면 종료
		if(idx >= N) { 
			return;
		}
		
		// idx번째 원소를 뽑는 경우
		answer.add(list.get(idx));
		combination(answer, idx + 1);
		
		// 위에서 넣은 원소를 다시 제거한다.
		// 넣었다 빼야 문제에서 요구한 오름차순 탐색이 가능한다.
		answer.remove(answer.size()-1);
		// idx번째 원소를 뽑지 않은 경우
		combination(answer, idx + 1);
	}
}