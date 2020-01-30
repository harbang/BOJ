// [BOJ] 백준 3190 뱀
// 문제: https://www.acmicpc.net/problem/3190

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 2 <= N <= 100
        int N = Integer.parseInt(sc.nextLine());
        
        int[][] arr = new int[N][N];
        
        int appleCnt = Integer.parseInt(sc.nextLine());
        for(int i=0; i < appleCnt; i++) {
            int x = Integer.parseInt(sc.next());
            int y = Integer.parseInt(sc.next());
            
            arr[x-1][y-1] = 1;
        }
        
        int guide = Integer.parseInt(sc.next());
        
        int[] elapsedTime = new int[guide];
        String[] direction = new String[guide];
        
        for (int i=0; i<guide; i++) {
            elapsedTime[i] = Integer.parseInt(sc.next());
            direction[i] = sc.next();
        }
        
        // 뱀이 진행되는 정도에 따라 몸통이 있는 Tile에는 '-1'이라는 값을 통해 인식한다.
        int result = 0;
        int x=0, y=0;
        
        // 출발은 오른쪽 방향으로 시작
        Queue<Integer> tailX = new LinkedList<>();
        Queue<Integer> tailY = new LinkedList<>();
        if(arr[x][++y] == 1) {
            // 뱀의 꼬리를 표시하고 저장한다.
            arr[x][y-1] = -1;
            tailX.add(x);
            tailY.add(y-1);
        }
        
        // 일단 현재 뱀의 머리가 있는 부분도 뱀의 영역으로 취급
        arr[x][y] = -1;
        tailX.add(x);
        tailY.add(y);
        result++;
        
        
        String directionMode = "E"; // 동쪽(오른쪽)
        int idx = 0; // 남은 방향지침서 개수를 파악하기 위함
        
        while(true) {
            // 현재 경과된 시간에서 방향을 전환해야 되는지 확인한다.
            // (단, 주어진 방향지시을 모두 수행되었는지 (error)체크를 한다.)
            if(idx < guide && elapsedTime[idx] == result) { // 방향전환 지시사항이 있다면
                directionMode = checkDriection(directionMode, direction[idx]);
                idx++;
            }
            
            // 주어진 방향 지침서를 확인한 후 다음 x,y 좌표값을 구한다.
            if (directionMode.equals("E")){
                y++;
            }else if(directionMode.equals("W")) {
                y--;
            }else if(directionMode.equals("S")){
                x++;
            }else if(directionMode.equals("N")) {
                x--;
            }
            
            // 먼저 다음 진행방향이 벽이나 뱀의 몸통(꼬리) 부분인지 확인한다.
            if( x < 0 || x >= N || y < 0 || y >=N || arr[x][y] == -1) {
                result++;
                break;
            }
            
            // 사과가 있는 tile인지 아닌지를 판단한다.
            if(arr[x][y] == 0) { // 사과가 없다면
                // 기존의 뱀의 길이는 변화가 없기에 가장 마지막 꼬리영역이었던 부분을 변화시킨다.
                int tail_x = tailX.poll();
                int tail_y = tailY.poll();
                arr[tail_x][tail_y] = 0;
            }
            
            // 현재 도착한 tile을 우선 뱀의 영역으로 표시하고 저장
            arr[x][y] = -1;
            tailX.add(x);
            tailY.add(y);
            
            result++;
        }
        
        System.out.println(result);
        
    }


    
    private static String checkDriection(String curDirection, String direction) {
        String directionMode = "";
        if(curDirection.equals("E")) {
            if(direction.equals("L")) {
                directionMode = "N";
            }else {
                directionMode = "S";
            }
            
        }else if(curDirection.equals("W")) {
            if(direction.equals("L")) {
                directionMode = "S";
            }else {
                directionMode = "N";
            }
        }else if(curDirection.equals("S")) {
            if(direction.equals("L")) {
                directionMode = "E";
            }else {
                directionMode = "W";
            }
        }else if(curDirection.equals("N")) {
            if(direction.equals("L")) {
                directionMode = "W";
            }else {
                directionMode = "E";
            }
        }
        return directionMode;
    }
}