// [BOJ] 백준 9466 텀 프로젝트
// https://www.acmicpc.net/problem/9466

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
                // 방문한적이 있는 곳이라면 이미 팀 판단여부가 끝났다면 pass
                // visitied[]는 매 DFS마다 초기화되서 시작하게 해놓았다.
                if(checked[j]) {
                    continue;
                }
                // 스택 비워주기
                stack.clear();
                DFS(j);
            }
            // 정답출력
            System.out.println(answer);
        }
    }
    
    private static void DFS(int curPosition) {
        // 이미 싸이클이 확인된 지점과 선택(희망)한다면 그동안 쌓인 경로들을 팀을 형성할 수 없는
        // 헛된 희망을 하고 있는 곳이므로 결과값에 합한다.
        if(checked[curPosition]) {
            answer += stack.size();
            // 팀 가능 여부를 불가능으로 끝났다고 표시
            for(int i=0; i<stack.size(); i++) {
                checked[stack.get(i)] = true;
            }
            return;
        }
        // checked[curPosition]=fasle이면서
        // 이미 방문한 곳에 도달 했다면 싸이클이 형성이 어디구간인지 확인해야 한다.
        if(visitied[curPosition]) {
            // 우선 팀 가능여부 판단이 끝났으므로 체크한다.
            for(int i=0; i<stack.size(); i++) {
                checked[stack.get(i)] = true;
            }
            // 싸이클을 형성하고 있는 원소를 무엇인지 구하는 것이 아니므로 개수만 파악한다.
            // 스택에서 일일이 찾기보다는 map형태로 바로 찾는 것도 좋은 방법일 것이다.
            for(int i=0; i<stack.size(); i++) {
                if(stack.get(i) == curPosition) {
                    answer += i;
                    return;
                }
            }
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