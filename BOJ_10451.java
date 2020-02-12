// [BOJ] 백준 10451 순열 사이클
// https://www.acmicpc.net/problem/10451

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    
    static ArrayList<ArrayList<Integer>> adList;
    static boolean[] visitied, checked;
    static Stack<Integer> stack;
    static int answer;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = Integer.parseInt(sc.next());
        
        for(int i=0; i<T; i++) {
            int N = Integer.parseInt(sc.next());
            
            adList = new ArrayList<>();
            // arrayList 구조상 시작인덱스는 0이므로 dummy data로 한개 넣는다.
            adList.add(new ArrayList<>());
            
            // 1~N까지의 시작 노드를 adList에 넣는다.
            for(int j=1; j<=N; j++) {
                adList.add(new ArrayList<>());
                
                // 문제에서 입력한 노드 연결을 입력 받는다.
                int target = Integer.parseInt(sc.next());
                // 방금 넣은 노드의 연결하고자 대상이 된다.
                adList.get(j).add(target);
            }
            
            visitied = new boolean[N+1];
            checked = new boolean[N+1];
            stack = new Stack<>();
            answer = 0;
            
            for(int j=1; j<=N; j++) {
                // 방문한적이 있는 곳이라면 이미 사이클 판단여부가 끝났다면 pass
                if(checked[j]) continue;
                // 혹시 모를 스택 비워주기
                stack.clear();
                DFS(j);
            }
            
            System.out.println(answer);
        }
    }
    
    private static void DFS(int curPosition) {
        // 이미 싸이클이 확인된 지점과 선택(희망)한다면 그동안 쌓인 경로들을 사이클을 형성할 수 없는
        // 헛된 희망을 하고 있는 곳이므로 탐색을 종료하고 사이클 확인 유무를 마친 것으로 체크
        if(checked[curPosition]) {
            // 사이클 가능 여부를 불가능으로 끝났다고 표시
            for(int i=0; i<stack.size(); i++) {
                checked[stack.get(i)] = true;
            }
            return;
        }
        // checked[curPosition]=fasle이면서
        // 이미 방문한 곳에 도달 했다면 싸이클이 형성이 어딘가에는 생성되었다는 것이다.
        if(visitied[curPosition]) {
            // 우선 사이클 가능여부 판단이 끝났으므로 체크한다.
            for(int i=0; i<stack.size(); i++) {
                checked[stack.get(i)] = true;
            }
            // 분명 특정구간에 사이클이 1개 형성된 것이다.
            answer++;
            return;
        }
        // DFS 진행의 방문표시
        visitied[curPosition] = true;
        // 사이클 경로를 알기위한 방문한 노드 저장
        stack.push(curPosition);
        DFS(adList.get(curPosition).get(0));
        // 다음 DFS 탐색을 위해 초기화 해놓음
        visitied[curPosition] = false;
    }
}