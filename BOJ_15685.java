// https://www.acmicpc.net/problem/15685
// BOJ 15685 드래곤 커브

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.next());
        // 0 <= x,y <= 100
        // 칸이 최대 100칸이므로 좌표 자체는 101개가 되는 셈이다.
        int[][] arr = new int[102][102];
        
        
        
        for(int i=0; i<N; i++) {
            Curve curve = new Curve(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
            int d = Integer.parseInt(sc.next());
            int g = Integer.parseInt(sc.next());
            
            // 꼭지점 표시
            arr[curve.x][curve.y] = 1;
            
            // 0 <= g <= 10 이므로 0세대를 우선적으로 한 번 진행
            curve.changePosition(d);
            arr[curve.x][curve.y] = 1;
            
            // 시작 방향을 list에 넣는다.
            List<Integer> list = new LinkedList<>();
            list.add(d);
            
            // 현재 드래곤 커브의 1세대 ~ g세대까지 수행
            for(int j=1; j<=g; j++) {
                
                // 현재 리스트에 쌓여있는 만큼 진행(뒤에서 원소를 탐색)
                int current_size = list.size()-1;
                for(int k=current_size; k>=0 ; k--) {
                    
                    // 이전 단계에서 진행했던 방향들을 하나씩 탐색해서 현재 지점(끝점)에서 그려간다.
                    int next_direction = (list.get(k)+1) % 4;
                    curve.changePosition(next_direction);
                    arr[curve.x][curve.y] = 1;
                    // 진행했던 방향을 리스트에 저장해서 다음 g세대에 사용
                    list.add(next_direction);
                }
            }
            
        }
        
        int answer = 0;
        for(int y=0; y<102; y++) {
            for(int x=0; x<102; x++) {
                if(arr[x][y] == 1) {
                    // 배열의 초과하지 않는 범위 내에서 4 꼭지점이 드래곤 커브되었는지 확인
                    if(x+1 < 102 && y+1 < 102) {
                        if(arr[x+1][y] == 1 && arr[x][y+1] == 1 && arr[x+1][y+1] == 1) {
                            answer++;
                        }
                    }
                }
            }            
        }
        System.out.println(answer);
    }
}

class Curve{
    int x, y;
    
    public Curve(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 문제조건상 격자(배열의 크기)를 벗어나는 경우는 고려하지 않는다.
    public void changePosition(int d) {
        if(d == 0) {
            x++;
        }else if(d == 1) {
            y--;
        }else if(d == 2) {
            x--;
        }else { // d == 3
            y++;
        }
    }
}