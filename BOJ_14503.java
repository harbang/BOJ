// https://www.acmicpc.net/problem/14503
// BOH 14503 로봇 청소기

import java.util.Scanner;
 
public class Main {
     
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
         
        int M = Integer.parseInt(sc.next());
        int N = Integer.parseInt(sc.next());
 
 
        // robot 정보
        int r = Integer.parseInt(sc.next());
        int c = Integer.parseInt(sc.next());
        int d = Integer.parseInt(sc.next());
        Robot robot = new Robot(r, c, d);
         
        int[][] map = new int[M][N];
        for(int i=0; i<M; i++) {
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(sc.next());
            }
        }
 
 
        // 로봇에 매뉴얼 세팅
        robot.setMenual(new Menual());
        // 로봇에 지도 세팅
        robot.setMap(map);
        // 로봇 동작
        robot.run();
    }
}
 
class Robot{
    // 로봇 위치
    int r, c;
    // 방향(d): {0:북쪽(상), 1:동쪽(우), 2:남쪽(하), 3:서쪽(좌)}
    int d;
    int[][] map;
    Menual menual;
     
    public Robot(int r, int c, int d) {
        this.r = r;
        this.c = c;
        this.d = d;
    }
     
    public void setMap(int[][] map) {
        this.map = map;
    }
 
 
    public void setMenual(Menual menual) {
        this.menual = menual;
    }
 
 
    /*
     -1 : 청소한 칸
     0  : 빈 칸
     1  : 벽
     */
    public void run() {
        // 시작지점을 청소
        map[r][c] = -1;
        int result = 1;
         
        while(true) {            
            // 만약 상하좌우 모두 벽이거나 청소한 곳이라면 후진(back)을 해야 한다.
            boolean isBack = true;
             
            // 현재 지점에서 바라보는 방향을 토대로 다음 탐색방향을 정하기
            for(int i=0; i<4; i++) {
                // 왼쪽 방향
                if(menual.checkDireciton(d) == 3) {
                    // 모든 외곽은 벽으로 설정되어 있으니 현재 로봇의 위치를 기준으로 배열을 초과하는 경우는 없다.
                    d = 3; // 로봇의 방향을 바꾼다. (해당 위치로 이동할 지 말지는 다음 분기에서 결정)
                    if(menual.checkClean(map[r][c-1])) {
                        isBack = false; // 후진할 필요가 없다.
                        c--; // 로봇을 이동시킨다.
                        break;
                    }
                }
                // 아래쪽 방향
                else if(menual.checkDireciton(d) == 2) {
                    d = 2;
                    if(menual.checkClean(map[r+1][c])) {
                        isBack = false;
                        r++;
                        break;
                    }
                }
                // 오른쪽 방향
                else if(menual.checkDireciton(d) == 1) {
                    d = 1;
                    if(menual.checkClean(map[r][c+1])) {
                        isBack = false;
                        c++;
                        break;
                    }
                }
                // 위쪽 방향                
                else if(menual.checkDireciton(d) == 0) {
                    d = 0;
                    if(menual.checkClean(map[r-1][c])) {
                        isBack = false;
                        r--;
                        break;
                    }
                }
            }
            // 후진되는 경우라면 동서남북이 벽이거나 청소했다는 것.
            if(isBack) {
                // 이때 후진하는 곳이 벽인지 확인한다.
                // 방향(d): {0:북쪽(위), 1:동쪽(오), 2:남쪽(아), 3:서쪽(왼)}
                if(d == 3) {
                    // 방향이 위쪽이므로 아래쪽 칸을 확인한다.
                    if(map[r][c+1] == 1) {
                        // 벽이라면 더 이상 진행할 수 없으므로 결과값을 출력하고 종료
                        break;
                    }
                    // 후진을 할 수 있다는 것은 기존의 청소를 한 곳이다.
                    // 아래 후진
                    c++;
                }else if(d == 2) {
                    if(map[r-1][c] == 1) {
                        break;
                    }
                    // 위로 후진
                    r--;
                }else if(d == 1) {
                    if(map[r][c-1] == 1) {
                        break;
                    }
                    // 왼쪽 후진
                    c--;
                }else if(d == 0) {
                    if(map[r+1][c] == 1) {
                        break;
                    }
                    // 오른쪽 후진
                    r++;
                }
            }
             
            // 이동된 현재 지점을 청소한 영역(-1) 표시
            if(isBack == false) {
                map[r][c] = -1;
                result++;
            }
        }
        System.out.println(result);
    }
}
 
class Menual{
    // 청소 가능한 곳인지 확인
    boolean checkClean(int target) {
        // 벽이거나 이미 청소한 곳인 경우
        if(target == 1 || target == -1) {
            return false;
        }
        return true;
    }
     
    // 방향 바꾸기
    int checkDireciton(int d) {
        int direction;
        if(d == 0) {
            direction = 3;
        }else if(d == 1) {
            direction = 0;
        }else if(d == 2) {
            direction = 1;
        }else{
            direction = 2;
        }
         
        return direction;
    }
}