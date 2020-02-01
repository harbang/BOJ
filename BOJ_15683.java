// [BOJ] 백준 15683 감시
// 풀이: https://www.acmicpc.net/problem/15683

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static int N, M;
    static int[][] office;
    static List<CCTV> cctv_list;
    static int answer = -1;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        N = Integer.parseInt(sc.next());
        M = Integer.parseInt(sc.next());
        
        office = new int[N][M];
        cctv_list = new ArrayList<>();
        
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                office[i][j] = Integer.parseInt(sc.next());
                // CCTV인 경우
                if(office[i][j] >=1 && office[i][j] <= 5) {
                    
                    // 무슨 종류의 CCTV인지 객체 생성
                    CCTV cctv = new CCTV(office[i][j]);
                    // CCTV 위치(좌표) 저장
                    cctv.setPosition(i,j);
                    cctv.setRotateCnt();
                    cctv_list.add(cctv);
                }
            }
        }
        
        // 리스트에 저장된 첫번째 리스트부터 경우의 수를 찾아간다.
        DFS(0);
        
        // 정답 출력
        System.out.println(answer);
    }

    private static void DFS(int idx) {
        // 리스트에 담겨있는 CCTV 방향설정이 정해졌다면 종료
        if(idx >= cctv_list.size()) {
            
            // CCTV들의 설정된 방향으로 감시영역 확인하기 (CCTV 주입)
            Machine machine = new Machine(cctv_list);
            
            // 사무실에 대한 CCTV 감시영역을 파악한다.
            int[][] copy_office = copyArray(office);
             
            // 사무실에 대한 정보를 주입
            machine.run(copy_office);
            
            int temp = machine.blindSpot();
            if(answer == -1) answer = temp;
            else answer = Math.min(answer, temp);
            
            return;
        }
        // idx번째 CCTV의 방향설정
        CCTV cctv = cctv_list.get(idx);
        // idx번째 CCTV의 의미있는 회전횟수 만큼 방향을 설정한다.
        for(int dir = 1; dir <= cctv.rotateCnt; dir++) {
            
            // 방향을 설정하고 재귀탐색으로 다음 CCTV 방향들을 설정한다.
            cctv.direction = dir;
            DFS(idx+1);
        }
        
    }
    // 얕은 배열 복사
    private static int[][] copyArray(int[][] office) {
        int[][] copy = new int[N][M];
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                copy[i][j] = office[i][j];
            }
        }
        return copy;
    }
}

class Machine{
    // 방향이 설정된 cctv 리스트
    List<CCTV> list;
    // 사무실의 초기 상태를 저장한다.
    static int[][] office;
    // 사무실의 크기
    static int N, M;
    
    // machine에 cctv 목록을 주입
    public Machine(List<CCTV> cctv_list) {
        // 생성자를 통해서 만들었기 때문에 굳이 static을 선언해주지 않아도 메소드간 공유가 가능(?)
        list = cctv_list;
    }

    // 리스트에 들어있는 각 CCTV를 동작시킨다.
    public void run(int[][] office) {
        this.office = office;
        N = office.length;
        M = office[0].length;
        
        for(int i=0; i<list.size(); i++) {
            CCTV cctv = list.get(i);
            detect(cctv);
        }
    }

    private static void detect(CCTV cctv) {
        // CCTV의 종류와 바라보는 방향(회전한 횟수)에 따라 감시영역이 다르다.
        // 우선 CCTV의 위치정보를 저장한다.(일종의 cctv 감시영역 시작지점)
        int x = cctv.x;
        int y = cctv.y;
        
        // 각 종류별 CCTV에 대한 처리를 함수화
        if(cctv.type == 1) {
            if(cctv.direction == 1) detect_detail(x, y, 0, 1);
            else if(cctv.direction == 2) detect_detail(x, y, 1, 0);
            else if(cctv.direction == 3) detect_detail(x, y, 0, -1);
            else if(cctv.direction == 4) detect_detail(x, y, -1, 0);
        }
        else if(cctv.type == 2) {
            if(cctv.direction == 1) {
                detect_detail(x, y, 0, 1);
                detect_detail(x, y, 0, -1);
            }else if(cctv.direction == 2) {
                detect_detail(x, y, -1, 0);
                detect_detail(x, y, 1, 0);
            }
        }
        else if(cctv.type == 3){
            if(cctv.direction == 1) {
                detect_detail(x, y, -1, 0);
                detect_detail(x, y, 0, 1);
            }else if(cctv.direction == 2) {
                detect_detail(x, y, 0, 1);
                detect_detail(x, y, 1, 0);
            }else if(cctv.direction == 3) {
                detect_detail(x, y, 1, 0);
                detect_detail(x, y, 0, -1);
            }else if(cctv.direction == 4) {
                detect_detail(x, y, 0, -1);
                detect_detail(x, y, -1, 0);
            }
        }   
        else if(cctv.type == 4){
            if(cctv.direction == 1) {
                detect_detail(x, y, -1, 0);
                detect_detail(x, y, 0, 1);
                detect_detail(x, y, 0, -1);
            }else if(cctv.direction == 2) {
                detect_detail(x, y, 0, 1);
                detect_detail(x, y, 1, 0);
                detect_detail(x, y, -1, 0);
            }else if(cctv.direction == 3) {
                detect_detail(x, y, 1, 0);
                detect_detail(x, y, 0, -1);
                detect_detail(x, y, 0, 1);
            }else if(cctv.direction == 4) {
                detect_detail(x, y, 0, -1);
                detect_detail(x, y, -1, 0);
                detect_detail(x, y, 1, 0);
            }
        }
        else if(cctv.type == 5) {
          detect_detail(x, y, -1, 0);
          detect_detail(x, y, 1, 0);
          detect_detail(x, y, 0, -1);
          detect_detail(x, y, 0, 1);
        }
    }
    // 카메라의 방향에 따른 감시영역
    public static void detect_detail(int x, int y, int dir_x, int dir_y) {
        
        // 해당 방향의 다음 지점
        int next_x = x + dir_x; int next_y = y + dir_y;
        
        // 배열의 범위를 벗어나거나 벽을 만나는지 확인
        if(!isAvailable(next_x, next_y)) return;
        
        // 다른 CCTV인 경우 감지 표시 없이 바로 재귀탐색을 이어간다.지점으로 넘어간다.
        if(isCCTV(next_x, next_y)) {
            detect_detail(next_x, next_y, dir_x, dir_y);
            return;
        }
        // 배열의 범위가 벗어나지 않고 CCTV가 있지도 않고, 벽도 아니라면 감시영역으로 표시
        office[next_x][next_y] = -1;
        detect_detail(next_x, next_y, dir_x, dir_y);
    }

    // 배열의 범위를 벗어나거나 벽을 만나는지 확인
    private static boolean isAvailable(int x, int y) {
        if(x < 0 || y < 0) return false;
        if(x >= N || y >= M) return false;
        
        // 벽인 경우
        if(office[x][y] == 6) return false;
        
        return true;
    }
    
    // 해당 지점이 CCTV인지 확인
    private static boolean isCCTV(int x, int y) {
        if(office[x][y] >= 1 && office[x][y] <= 5) return true;
        return false;
    }
    
    public int blindSpot() {
        int total = 0;
        for(int x=0; x<N; x++) {
            for(int y=0; y<M; y++) {
                if(office[x][y] == 0) total++;
            }
        }
        return total;
    }
}

class CCTV {
    // CCTV 좌표
    int x, y;
    // CCTV 종류
    int type;
    // CCTV 방향(편의상 1~4)
    int direction;
    // 의미있는 회전 개수
    int rotateCnt;
    
    public CCTV(int type) {
        this.type = type;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setRotateCnt() {
        // CCTV 종류가 1,3,4는 회전방향마다 감시영역이 다르다.
        if(type == 1 || type == 3 || type == 4) rotateCnt = 4;
        else if(type == 2) rotateCnt = 2;
        else if(type == 5) rotateCnt = 1;
    }
    
}