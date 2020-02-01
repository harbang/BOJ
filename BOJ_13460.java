// [BOJ] 백준 13460 구슬 탈출 2
// https://www.acmicpc.net/problem/13460

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {    
     
     static int N, M;
     static char[][] board;
     static Queue<Bead> queue;
     static int[] dx, dy;
     static int hx, hy, answer;
     static boolean isRedOk, isBlueOk, isSuccess = false;
     
    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());
        
        board = new char[N][M];
        Bead bead = new Bead();
        
        for(int i=0; i<N; i++) {
          String str = sc.next();
          for(int j=0; j<M; j++) {
              board[i][j] = str.charAt(j);
              // 빨간 구슬인 경우
              if(str.charAt(j) == 'R') bead.setRed(i, j);
              // 파란 구슬인 경우
              else if(str.charAt(j) == 'B')   bead.setBlue(i, j);
              // 구멍인 경우
              else if(str.charAt(j) == 'O') {
                   hx = i; hy = j;
              }
          }
        }
        
        queue = new LinkedList<>();
        
        // 이동방향(상하좌우, 거리 자체는 갈 수 있는 끝까지   가야한다.(기울이는 것이기 때문에))
        dx = new int[] {-1, 1, 0, 0};
        dy = new int[] {0, 0, -1, 1};
        
        // 처음 시작하므로 이전에 진행했던 방향이 없다는   의미
        bead.preDirection = -1;
        // 문제 특성상 한번의 기울기는 필요하다.
        bead.moveCnt = 1;
        // 처음 입력받은 구슬의 위치에서 이동가능한   범위내에서 BFS를 통해 이동한다.
        queue.add(bead);
        
        BFS();
        
        // 모든 BFS 탐색 이후에도 판정나지 않았다면 결국   시도횟수를 초과하여 실패한것이다.
        if(!isSuccess) System.out.println("-1");
    }
    private static void BFS() {
          while(!queue.isEmpty()) {
            
            // 현재 구슬의 위치를 확인한다.
            Bead curBead = queue.poll();
            // 만약 현재 탐색하고는 기울기 횟수가 10번을   초과했으면 이번 차례의 탐색은 종료
            if(curBead.moveCnt > 10) {
                 continue;
            }
            
            // 4가지 방향을 설정한다.
            for(int i=0; i<4; i++) {
              
                // 기존에 진행했던 방향이나   돌아가는(회귀하는) 방향은 무시한다.
                if(curBead.checkDirection(i)) {
                     continue;
                }
              
                // 기울인 방향으로 갈 수 있는 곳까지  쭈욱  이동한다.
                // 먼저 빨간 구슬의 경우를 이동을   고려한다.
                int rx = curBead.rx, ry =  curBead.ry;
                 
                // 진행과정에 있어서 파란구슬이  존재했다면  사실상 쭈욱 미끄러진 곳에서는 파란구슬이 위치할 수밖에 없다.
                // 그렇기에 빨간구슬은 끝위치에서 바로 직전  위치에 있어야 한다.
                boolean isPassedBlue = false;
                 
                while(true) {
                     rx = rx + dx[i];  ry = ry + dy[i];
                     // 배열의 크기를 초과하는 경우는  없고  벽인지 아닌지를 확인해야 한다.
                     // 벽이라면 진행했던 방향의 직전  위치로 이동한다.
                     if(board[rx][ry] ==  '#') {
                          rx = rx - dx[i];  ry = ry -  dy[i];
                          break;
                     }
                     // 빨간 구슬이 구멍에 빠진경우
                     if(rx == hx && ry ==  hy) {
                          isRedOk = true;
                     }
                     
                     // 이동과정에서 파란구슬이 있었다면   최종 도착지점에서 직전에 위치하여야 한다.
                     if(curBead.bx == rx &&  curBead.by  == ry) {
                         isPassedBlue = true;
                     }
                }
                if(isPassedBlue) {
                    // 도착지점에서 직전 위치로 이동
                   rx = rx - dx[i];  ry = ry - dy[i];
                }
                 
                // 바로 파란 구슬의 이동하여 마치   빨강구슬과 파랑구슬을 동시에 움직이는 것처럼 한다.
                // (실제 빨간 구슬의 이동이 먼저 끝난  것이다.)
                int bx = curBead.bx, by =  curBead.by;
                 
                // 기울인 방향으로 쭈욱 미끄러진다.
                while(true) {
                     bx = bx + dx[i];  by = by + dy[i];
                     
                     // 배열의 크기를 초과하는 경우는  없고  벽인지 아닌지를 확인해야 한다.
                     if(board[bx][by] ==  '#') {
                          bx = bx - dx[i];  by = by -  dy[i];
                          break;
                     }
                     
                     // 진행과정에서 파란 구슬이 구멍에   빠진 경우
                     if(bx == hx && by ==  hy) {
                          isBlueOk = true;
                     }
                }
                // 최종적으로 미끄러진 곳에 빨간구슬이   위치한다면 물리적으로 같이 있을 수 없기에 직전 위치로 이동
                // 빨간공이 없다면 파란공이 미끄러져   도착한 지점을 그대로 둔다.
                if(bx == rx && by ==  ry) {
                     // 진행방향에서 직전 위치로 이동
                     bx = bx - dx[i];  by = by - dy[i];
                }
                
                // 두 공의 위치 변화가 없다면 다른 방향으로 시도한다.
                if(rx == curBead.rx && ry == curBead.ry
                        && bx == curBead.bx && by ==  curBead.by) continue;
                
                
                // 우선 빨간공이 구멍을 통해 빠져나오면   계속해서 탐색해야 할지 판단해줘야 한다.
                if(isRedOk) {
                     // 만약에 파란공도 같이 구멍을 통해   빠져나왔다면
                   if(isBlueOk) {
                     // 값을 초기화해주고 남아 있는 BFS   탐색을 하게 해준다.
                     // 남아 있는 탐색에서 성공을  기원하는  셈이다.
                     isBlueOk = false;
                     isRedOk = false;
                     continue;
                   }
                   // 빨간공만 구멍에서 빠져 나온경우
                   System.out.println(curBead.moveCnt);
                   // BFS 탐색과정에서 판정이 끝났다고 표시
                   isSuccess = true;
                   return;
                }
                // 파란 공만 빠져 나온경우도 해당 BFS탐색은 계속하지 않고 다른 BFS탐색을 위해 초기화만 해준다.
                if(isBlueOk) {
                     isBlueOk = false;
                     continue;
                }
                
                
                Bead movedBead = new Bead();
                movedBead.setRed(rx, ry);
                movedBead.setBlue(bx, by);
                // 진행한 방향을 저장
                movedBead.preDirection = i;
                // 다음 기울기 횟수 저장
                movedBead.moveCnt = curBead.moveCnt +  1;
                
                queue.add(movedBead);
                
            } // end of for문
        }
     }
}
class Bead{
    // 빨간 구슬의 위치
    int rx, ry;
    // 파란 구슬의 위치
    int bx, by;
    // 이전에 진행했던 방향을 저장하는 변수
    int preDirection;
    // 기울인 횟수
    int moveCnt;
    public void setRed(int x, int y) {
        rx = x; ry = y;
    }
     public void setBlue(int x, int y) {
        bx = x; by = y;
    }
     
    public boolean checkDirection(int curDirection) {
         // 기존에 진행했던 방향과 같은 경우
         if(preDirection == curDirection) return true;
         // 이전에 왔었던 방향으로 다시 돌아가려는 경우
         if(preDirection == 0 && curDirection == 1)   return true;
         else if(preDirection == 1 && curDirection == 0)   return  true;
         else if(preDirection == 2 && curDirection == 3)   return  true;
         else if(preDirection == 3 && curDirection == 2)   return  true;
         
         return false;
    }
     
}