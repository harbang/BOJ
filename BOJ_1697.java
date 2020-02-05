// [BOJ] 백준 1697 숨바꼭질
// https://www.acmicpc.net/problem/1697

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Subin {
    int elapsedTime;
    int position;
    // Getter, Setter 생략

    public Subin(int t, int p) {
        elapsedTime = t;
        position = p;
    }
}

public class Main {

    public static int result;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = Integer.parseInt(sc.next());
        int K = Integer.parseInt(sc.next());

        // 기본값으로 N과 K의 거리를 할당해둔다.
        result = Math.abs(N - K);
        
        // 방문 여부 표시 0 <= N,K <= 100,000
        boolean[] visited = new boolean[100001];
        Queue<Subin> queue = new LinkedList<>();
        queue.add(new Subin(0, N));
        
        while(!queue.isEmpty()) {
            
            Subin current = queue.poll();
            
            // 배열 범위 내인지 check
            if(current.position < 0 || current.position > 100000) continue;
            // 찾아낸 시간보다 많은 시간이 요구되는 경우
            if(current.elapsedTime > result) continue;
            // K지점에 도착했다면
            if(current.position == K) {
                if(result > current.elapsedTime) {
                    result = current.elapsedTime;
                }
            }
            
            // 이미 방문한 곳이라면 pass
            // 해당 지점에서  -1,+1,*2 이동하므로 어떠한 방법 혹은 경로로 왔던 이미 방문한 곳에서 다시 재탐색할 이유가 없다.
            if(visited[current.position]) continue;
            
            // 방문 표시
            visited[current.position] = true;

            // 현재 지점에 대해서 -1,+1,*2 이동
            queue.add(new Subin(current.elapsedTime + 1, current.position - 1));
            queue.add(new Subin(current.elapsedTime + 1, current.position + 1));
            queue.add(new Subin(current.elapsedTime + 1, current.position * 2));
        }
        System.out.println(result);
    }
}