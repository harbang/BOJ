// [BOJ] 백준 12851 숨바꼭질 2
// https://www.acmicpc.net/problem/12851

import java.util.Iterator;
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

        result = Math.abs(N - K);
        
        // 방문 여부 표시 0 <= N,K <= 100,000
        boolean[] visited = new boolean[100001];
        Queue<Subin> queue = new LinkedList<>();
        Queue<Subin> resultQueue = new LinkedList<>();
        queue.add(new Subin(0, N));
        
        while(!queue.isEmpty()) {
            
            Subin current = queue.poll();
            
            // K지점에 도착했다면
            if(current.position == K) {
                resultQueue.add(current);
                if(result > current.elapsedTime) {
                    result = current.elapsedTime;
                }
            }
            
            // pop한 뒤 방문 표시하는 것은 동일하다.
            visited[current.position] = true;

            // 방문여부에 따라 Queue에 넣을지 말지 결정한다.
            // 현재 지점에 대해서 -1,+1,*2
            if(current.position-1 >= 0 && !visited[current.position -1]) {
                queue.add(new Subin(current.elapsedTime + 1, current.position - 1));    
            }
            if(current.position+1 <= 100000 && !visited[current.position +1]) {
                queue.add(new Subin(current.elapsedTime + 1, current.position + 1));    
            }
            if(current.position*2 <= 100000 && !visited[current.position *2]) {
                queue.add(new Subin(current.elapsedTime + 1, current.position * 2));    
            }
        }
        
        System.out.println(result);

        int resultCnt = 0;
        Iterator<Subin> it = resultQueue.iterator();
        while(it.hasNext()) {
            Subin subin = it.next();    
            if(subin.elapsedTime == result) {
                resultCnt++;
            }
        }
        System.out.println(resultCnt);
    }
}