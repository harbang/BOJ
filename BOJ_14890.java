// [BOJ] 백준 14890 경사로
// https://www.acmicpc.net/problem/14890

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.next());
        int L = Integer.parseInt(sc.next());
        
        int[][] map1 = new int[N][N];
        int[][] map2 = new int[N][N];
        
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                map1[i][j] = Integer.parseInt(sc.next());
                map2[j][i] = map1[i][j];
            }
        }
        List<int[][]> list = new ArrayList<>();
        list.add(map1); list.add(map2);
        
        int answer = 0;
        while(!list.isEmpty()) {
            int[][] map = list.remove(0);
            for(int i=0; i<map.length; i++) {
                
                // 길이 되는지 살펴보기 위해 지도(map)를 복사복사
                int[] road = map[i];

                int position = 0;
                // 길을 지나갈 사람을 만든다.
                Person person = new Person(road, position, L);
                
                if(person.go()) answer++;
            }
        }
        // 정답 출력
        System.out.println(answer);
    }
}

class Person{
    int position, L;
    int[] road;
    boolean[] ramp;
    
    public Person(int[] road, int pos, int L) {
        position = pos;
        this.road = road;
        this.L = L;
        // 경사로를 겹치게 놓았는지 확인하기 위한 배열 변수
        this.ramp = new boolean[road.length];
    }

    public boolean go() {
        while(true) {
            // 끝지점에 도착했다면 True 반환
            if(position == road.length-1) return true;
            
            // 다음칸이 높이가 갔다면 1칸 앞으로 나아간다.
            if(road[position] == road[position+1]) position++;
            // 현재 칸과 다음칸의 높이가 다른 경우
            else{
                // 높이치가 2이상이면 경사로를 고민할 필요없이 길이 아니다.
                if(Math.abs(road[position] - road[position+1]) > 1) return false;
                
                // 올라가는 경사로가 필요한 경우이다.
                if(road[position] < road[position+1]) {
                    // 경사로를 놓는 곳에 성공했으면 다음칸으로 이동
                    if(checkUpSlide()) position++;
                    else return false;
                }
                // 내려가는 경사로가 필요한 경우이다.
                else {
                    // 내려가는 경사로를 놓는 곳에 성공했으면 L만큼 전진해도 괜찮다.
                    if(checkDownSlide()) position += L;
                    else return false;
                }
            }
        }
    }
    
    private boolean checkUpSlide() {
        // 올라가는 경사로를 놓을 수 있는지 확인하기 위해서는
        // 현재 사람이 위치한 곳에서부터 경사로 길이 L이전 지점부터 해서 경사로를 놓을 수 있는지 확인한다.
        
        // 배열 범위를 초과하지 않는지 먼저 확인한다.
        int start = position - (L-1);
        if(start < 0) return false;
        
        // 초기 높이 정보를 저장
        int height = road[start];
        // 배열 범위내에 있다면 길이 울퉁불퉁한지 즉, 높이가 일정한지 살펴본다.
        // 올라가는 경사로의 경우에는 이전에 경사로를 만든적이 있는 곳인지 확인해야 한다.
        for(int i=start; i<=position; i++) {
            // 이미 경사로를 놓은 적이 있는 칸이면 겹치므로 False
            if(ramp[i]) return false;
            // 높이가 다른 구간이 있다면 False
            if(height != road[i]) return false;
        }
        return true;
    }
    
    private boolean checkDownSlide() {
        // 내려가는 경사로는 현재 위치의 다음칸에 경사로를 놓는 것이다.
        // 그렇기 때문에 for문의 진행상 기존의 경사로는 없을것이다.
        
        // 배열 범위를 초과하면 길이 L인 경사로를 놓을 수 없다는 것이다.
        int end = position + L;
        if(end >= road.length) return false;
        // 경사로가 놓이는 지점들의 높이가 일정한지 확인한다.
        int height = road[position+1];
        for(int i=position+2; i<=end; i++) {
            if(height != road[i]) return false;
        }
        // 올라가는 경사로는 나중에 내려가는 경사로와 겹칠 수 있으므로
        // 경사로 놓인 정보를 저장한다.
        for(int i=position+1; i<=end; i++) {
            ramp[i] = true;
        }
        return true;
    }
}