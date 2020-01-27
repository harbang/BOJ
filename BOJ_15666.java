// [BOJ] 백준 15666 N과 M(12)
// 문제: https://www.acmicpc.net/problem/15666
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
    static Map<String, String> map;
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
        map = new HashMap<String, String>();
        List<Integer> answer = new ArrayList<>();
        
        combination(answer, 0);
        log.flush();
    }

	private static void combination(List<Integer> answer, int idx) throws IOException {
		// M개 뽑았다면 출력
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
		
		// 더 이상 뽑을 원소가 없다면 종료
		if(idx >= list.size()) return;
		
        answer.add(list.get(idx));
        combination(answer, idx);
        
        answer.remove(answer.size()-1);
        combination(answer, idx + 1);
		
	}
}