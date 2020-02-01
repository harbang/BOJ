// [BOJ] 백준 14499 주사위 굴리기
// https://www.acmicpc.net/problem/14499

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.next());
        int M = Integer.parseInt(sc.next());
        
        // 지도(게임판) 입력 받기
        int[][] arr = new int[N][M];

        // 시작 위치 입력 받기
        Dice dice = new Dice();
        dice.setPosition(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
        
        // 명령 개수 입력받기
        int K = Integer.parseInt(sc.next());
        
        // 각 칸의 숫자 입력 받기
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                arr[i][j] = Integer.parseInt(sc.next());
            }
        }
                
        for(int i=0; i<K; i++) {
            // 방향 입력받기
            int direction = Integer.parseInt(sc.next());
            
            // 주사위 이동
            if(dice.move(direction, N, M)) continue;
            
            
            // 이동되어진 지도(게임판)의 칸이 0인지 확인
            if(arr[dice.x][dice.y] == 0) {
                // 주사위 뒷면의 숫자를 복사하여 해당 칸에 복사
                arr[dice.x][dice.y] = dice.surface[6].curNum;
            }else {
                // 해당 칸의  숫자를 주사위 아랫면에 복사하고 0으로 전환
                dice.surface[6].curNum = arr[dice.x][dice.y];
                arr[dice.x][dice.y] = 0;
            }
            
            System.out.println(dice.surface[1].curNum);
            
        }

    }
}

class Dice{
    int[] dx, dy;
    int x,y;
    
    // 이동되면서 주사위의 면들이 어딜 향하고 있는지 표시하기 위함
    // (surface[1] 윗면, surface[3]이 오른쪽을 향한다고 가정)
    Surface[] surface;
    
    public Dice() {
        // 동(1), 서(2), 남(4), 북(3)
        // 편의상 인덱스 0번째를 넣어둠
        dx = new int[]{0, 0, 0, -1, 1};
        dy = new int[]{0, 1, -1, 0, 0};
        
        // 초기의 1~6면을 나타낸다.
        surface = new Surface[7];
        for(int i=1; i<=6; i++) {
            surface[i]  = new Surface(i);
        }
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

    }
    
    public boolean move(int direction, int N, int M) {
        int next_x = x + dx[direction];
        int next_y = y + dy[direction];
        
        // 범위를 벗어나면 무효!
        if(next_x < 0 || next_y < 0) return true;
        if(next_x >= N || next_y >= M) return true;
        
        // 좌표이동
        setPosition(next_x,next_y);
        
        // 굴리면서 바뀐 방향으로 주사위면을 재설정
        surface = setSuface(direction);
        return false;
    }

    public Surface[] setSuface(int direction) {
        Surface[] temp = new Surface[7];
        if(direction == 1) { // 동쪽
            temp[1] = surface[4]; temp[2] = surface[2]; temp[3] = surface[1];
            temp[4] = surface[6]; temp[5] = surface[5]; temp[6] = surface[3];
        }else if(direction == 2) { // 서쪽
            temp[1] = surface[3]; temp[2] = surface[2]; temp[3] = surface[6];
            temp[4] = surface[1]; temp[5] = surface[5]; temp[6] = surface[4];
        }else if(direction == 3) { // 북쪽
            temp[1] = surface[5]; temp[2] = surface[1]; temp[3] = surface[3];
            temp[4] = surface[4]; temp[5] = surface[6]; temp[6] = surface[2];
        }else if(direction == 4) { // 남쪽
            temp[1] = surface[2]; temp[2] = surface[6]; temp[3] = surface[3];
            temp[4] = surface[4]; temp[5] = surface[1]; temp[6] = surface[5];
        }
        return temp;
    }
}

class Surface{
    // 제일 처음 시작했을 때 놓인 주사위면들이 향했던 방향에 번호를 부여
    // ('1' 윗면 '3'이 오른쪽을 향한다고 가정)
    int initVal;
    // 가지고 있는 숫자
    int curNum;
    
    public Surface(int val) {
        initVal = val;
        // 처음에는 0으로 초기화
        curNum = 0;
    }
}