// [BOJ] 백준 3665 최종 순위
// https://www.acmicpc.net/problem/3665

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static int n;
    static char[][] graph;
    static int[] indegree;
    static final int SUCCEESS = 0;
    static final int IMPOSSIBLE = 1;
    static final int NOT_DETERMINED = 2;
    static Queue<Integer> queue;
    static List<Integer> list;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int T = Integer.parseInt(sc.next());
        
        while(T-- > 0) {
            // 팀의 개수
            n = Integer.parseInt(sc.next());
            
            // 인덱스를 '1'부터 제어
            graph = new char[n+1][n+1];
            indegree = new int[n+1];
            // 작년 정보를 입력받는다.(순위별로)
            for(int i=1; i<=n; i++) {
                int team = Integer.parseInt(sc.next());
                for(int j=1; j<=n; j++) {
                    if(graph[team][j] == '\u0000') graph[team][j] = 'O';
                    if(graph[j][team] == '\u0000') graph[j][team] = 'X';
                }
                // 자기자신을 순환하지는 않는다.
                graph[team][team] = 'X';
                
                indegree[team] = i-1;
            }
            
            // 바뀐 등수의 개수
            int m = Integer.parseInt(sc.next());
            for(int i=1; i<=m; i++) {
                int x = Integer.parseInt(sc.next());
                int y = Integer.parseInt(sc.next());
                
                changeState(x,y);
            }
            
            queue = new LinkedList<>();
            list = new ArrayList<>();
            
            // 위상 정렬
            int answer = topological_sort();
            
            // 정답 출력
            sayAnswer(answer);
        }
    }
    
    private static void sayAnswer(int answer) {
        if(answer == SUCCEESS) {
            while(!list.isEmpty()) {
                System.out.print(list.remove(0) + " ");
            }
            System.out.println();
        }else if(answer == IMPOSSIBLE) {
            System.out.println("IMPOSSIBLE");
        }else if(answer == NOT_DETERMINED) {
            System.out.println("?");
        }
    }

    private static int topological_sort() {
        // 주어진 정점의 개수만큼 진행한다.
        for(int i=1; i<=n; i++) {
            // 자기자신으로 들어오는 간선의 개수가 0개인 정점을 찾는다.
            if(indegree[i] == 0) {
                queue.add(i);
            }
        }
            
        // 정점의 개수만큼 반복한다.
        for(int i=1; i<=n; i++) {
            /*Queue에 저장된 정점이 2개 이상이면 그들간의 우선순위를 알 수 없다.
              (동시에 여러개의 원소가 진입한 상태이다.)    
              (일반적인 위상정렬이라면 어떤 것이든 상관없지만 이 문제는 그렇지 않다.)*/
            if(queue.size() >= 2) {
                return NOT_DETERMINED;
            }else if(queue.size() == 0) {
                // 진행과정에서 사이클이 형성되었다는 것이다.
                return IMPOSSIBLE;
            }
            
            int curNode = queue.poll();
            // 나중에 정상일 때 순위를 출력하기 위해 저장해둔다.
            list.add(curNode);

            // 모든 원소에 대해서 연결된 정점을 확인하다.
            for(int y=1; y<=n; y++) {
                // 간선이 존재한다면 연결을 끊어주고 indegree 개수도 줄여준다.
                if(graph[curNode][y] == 'O') {
                    graph[curNode][y] = 'X';
                    indegree[y]--;
                    // 그 과정에서 자기자신에게 들어오는 간선의 개수가 0개이면 queue에 넣어준다.
                    if(indegree[y] == 0) {
                        queue.add(y);
                    }
                }
            }
        }
        return SUCCEESS;
    }
    private static void changeState(int x, int y) {
        if(graph[x][y] == 'O') {
            graph[x][y] = 'X';
            graph[y][x] = 'O';
            indegree[x]++;
            indegree[y]--;
        }else {
            graph[x][y] = 'O';
            graph[y][x] = 'X';
            indegree[x]--;
            indegree[y]++;
        }
    }
}