// [BOJ] 백준 15663 N과 M(9)
// 문제: https://www.acmicpc.net/problem/15663
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
    static List<String> temp;
    static Map<String, String> map;
    static boolean[] visited;
    static int N, M;
    static BufferedWriter log;
    
    public static void main(String[] args) throws IOException{
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
        temp = new ArrayList<>();
        // 뽑은 원소인지 파악하기 위함
        visited = new boolean[N];
        
        permutation(temp);
        
        log.flush();
    }
    
    private static void permutation(List<String> temp) throws IOException{
        if(temp.size() == M) {
                            // 문자열로 처리
            String str = "";
            for(int i=0; i < M; i++) {
                str += temp.get(i);
            }
            
            // 기존에 없는 조합이라면
            if(map.get(str) == null) {
            	// 정답 출력 
            	for(int i=0; i<temp.size(); i++) {
            		log.write(temp.get(i) + " ");
            	}
            	log.write("\n");
            	map.put(str, str);
            }
            
            return;
        }
        
        for(int i=0; i<N; i++) {
            // 아직 뽑은적이 없다면
            if(!visited[i]) {
                
                visited[i] = true;
                temp.add(String.valueOf(list.get(i)));
                
                permutation(temp);
                
                // 다음 순열 구성을 위해 초기화
                temp.remove(temp.size()-1);
                visited[i] = false;
            }
        }
    }
}