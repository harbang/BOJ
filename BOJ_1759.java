// [BOJ] 백준 1759 암호 만들기
// https://www.acmicpc.net/problem/1759

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static List<String> list, candidateList;
    static int L ;
    static final String[] VOWEL = {"a", "e", "i", "o", "u"};
    
    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        L = Integer.parseInt(sc.next());
        int C = Integer.parseInt(sc.next());
        
        list = new ArrayList<>();
        while(C-- > 0) {
            list.add(sc.next());
        }
        
        Collections.sort(list);
        
        candidateList = new ArrayList<>();
        
        for(int i=0; i<list.size(); i++) {
            // 시작하는 문자열로 부터 길이 L을 가질 수 없다면 break
            // 지금까지 탐색한 문자열 중 만족하는 정답 코드를 출력한다.
            if(list.size() - i < L) break;
            
            // 현재 한 문자를 기준으로 주어진 문자열만큼 탐색하며 채워간다.
            String str = list.get(i);
            DFS(str, i+1);
        }
        
        while(!candidateList.isEmpty()) {
            String target = candidateList.remove(0);
            // 최소 한 개의 모음과 최소 두 개의 자음으로 구성되어 있는지 확인한다.
            if(checkStr(target)) System.out.println(target);
            
        }
    }

    private static boolean checkStr(String str) {
        // 모음 개수
        int vowelCnt = 0;
        // 자음 개수
        int consonantCnt = 0;
        
        for(int i=0; i<str.length(); i++) {
            // 비교할 문자열 추출
            String target = str.substring(i, i+1);
            boolean isVowel = false;
            // 모음인지 아닌지 확인
            for(int j=0; j<VOWEL.length; j++) {
                if(VOWEL[j].equals(target)) {
                    isVowel = true;
                    break;
                }
            }
            // 모음이면 모음의 개수를 올린다.
            if(isVowel) {
                vowelCnt++;
            }else {
                consonantCnt++;
            }
        }
        
        // 현재 한 문자를 기준으로 주어진 문자열만큼 탐색하며 채워간다.
        if(vowelCnt >= 1 && consonantCnt>= 2) {
            return true;
        }
        return false;
    }

    private static void DFS(String str, int idx) {
        
        // 해당 문자열의 길이를 만족한다면 우선 후보군에 넣는다.
        // 아직 최소의 한개의 모음과 최소 2개의 자음으로 구성되어 있는지 확인하지 않았다.
        if(str.length() == L ) {
            candidateList.add(str);
            return;
        }
        
        // 더 이상 탐색할 문자열이 없다면 종료
        // 문제에서 찾는 문자열 길이를 만족시키지 못하고 종료되는 case이다.
        if(idx >= list.size()) return;
        
        // 다음 인덱스를 포함하거나 포함하지 않거나 둘중 하나로 해서 탐색
        // 다음 문자열을 포함하고 DFS 탐색
        DFS(str.concat(list.get(idx)), idx+1);
        // 다음 문자열을 포함하지 않고 DFS 탐색
        DFS(str, idx+1);
    }
}