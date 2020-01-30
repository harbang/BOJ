// [BOJ] 백준 17143 낚시왕
// 문제: https://www.acmicpc.net/problem/17143

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static int R, C, M, answer;
    static List<Shark> sharkList;
    // map에는 살아 있는 상어의 ID만 저장
    static int[][] map, temp;
    // 문제조건에 맞게 인덱스 = 1부터 조정 (상하좌우)
    static int[] dx = {0, -1, 1, 0, 0};
    static int[] dy = {0, 0, 0, 1, -1};
    
    // 상어 상태
    static public final int LIVE = 1;
    static public final int DEAD = 2;
    static public final int CAUGHT = 3;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        R = Integer.parseInt(sc.next());
        C = Integer.parseInt(sc.next());
        map = new int[R+1][C+1];
        
        // 상어 개수
        M = Integer.parseInt(sc.next());
        sharkList = new ArrayList<Shark>();
        
        for(int id=0; id<M; id++) {
            Shark shark = new Shark(
                            id, // List에 담긴 인덱스가 곧 ID
                            Integer.parseInt(sc.next()), // x
                            Integer.parseInt(sc.next()), // y
                            Integer.parseInt(sc.next()), // 속력
                            Integer.parseInt(sc.next()), // 방향
                            Integer.parseInt(sc.next()),  // 크기
                            LIVE // 물고기 상태
                        );
            // 배열 원소 초기값이  0이므로 상어 ID + 1로
            // 격자에 상어 존재 유무 파악
            map[shark.x][shark.y] = (id+1);
            
            // 속력 수치를 조정하여 이동 횟수를 재조정
            shark.reMakeSpeed(R, C);
            sharkList.add(shark);    
        }


        for(int c=1; c<=C; c++) {
            // 오른쪽으로 이동하며 가까운 상어 낚시
            fishing(c);
            // 상어 이동
            moveShark();
        }
        
        System.out.println(answer);
    }


    private static void fishing(int c) {
        for(int r=1; r<=R; r++) {
            // 제일 가까운 상어를 발견하면
            if(map[r][c] != 0) {
                Shark targetShark = sharkList.get(map[r][c]-1);
                // 물고기 크기 확인
                answer += targetShark.size;
                // 물고기 상태 변화
                targetShark.state = CAUGHT;
                // 격자 비워주기
                map[r][c] = 0;
                // 한 마리만 잡을 수 있으므로 종료
                return;
            }
        }
    }
    
    private static void moveShark() {
        // 모든 상어가 이동이 끝났을 때를 고려해주기 임시 격자 이용
        temp = new int[R+1][C+1];
        // 살아남은 상어들을 한마리씩 이동시킨다.
        for(int idx=0; idx<sharkList.size(); idx++) {
            Shark shark = sharkList.get(idx);
            // 죽거나 잡힌 상어의 경우
            if(shark.state == DEAD || shark.state == CAUGHT) continue;
            
            for(int s=0; s<shark.speed; s++) {
                shark.x = shark.x + dx[shark.dir];
                shark.y = shark.y + dy[shark.dir];
                
                // 격자 범위를 벗어나면 반대방향 전환 후 이동.
                if(shark.x <= 0 || shark.y <= 0 || shark.x > R || shark.y > C) {
                    shark.changeDir();
                    
                    // 방향전환으로 위치 조정.
                    // R, C >= 2이므로 speed가 0이 아닌 이상 제자리 걸음은 없다.
                    // 이동 후 방향전환되면서  제자리로 올 수는 있다.
                    shark.x = shark.x + (2 * dx[shark.dir]);
                    shark.y = shark.y + (2 * dy[shark.dir]);
                }
            }
            
            // 임시 격자에서는 이동이 끝난 상어들만 존재하므로
            // 아직 이동 전 상태인 상어들은 존재 X
            if(temp[shark.x][shark.y] != 0) {
                Shark target = sharkList.get(temp[shark.x][shark.y]-1);
                // 굴러온 상어가 기존 상어를 이긴 경우
                if(shark.size > target.size) {
                    target.state = DEAD;
                    temp[shark.x][shark.y] = (idx+1);
                }else {
                    shark.state = DEAD;
                }
            }
            // 이동된 위치에 상어가 존재하지 않는다면
            else {
                temp[shark.x][shark.y] = (idx+1);
            }            
        }
        
        // 모든 상어의 이동과 자리싸움이 끝난 후 격자 갱신
        // 이차원 배열은 깊은 복사를 하기 위해 함수 별도 구현
        map = deepCopy(temp);
    }


    private static int[][] deepCopy(int[][] src) {
        if(src == null) return null;
        int[][] dest = new int[src.length][src[0].length];
         
        for(int i=0; i<src.length; i++){
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
         
        return dest;
    }
}


class Shark {
    int ID, x, y;
    int speed, dir, size;
    // 상어 상태
    int state;
    
    public Shark(int ID, int x, int y, int s, int d, int z, int state) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        speed = s; // 속력
        dir = d; // 방향
        size = z; // 크기
        this.state = state;
    }
    
    public void changeDir() {
        if(dir == 1) dir = 2;
        else if(dir == 2) dir = 1;
        else if(dir == 3) dir = 4;
        else dir = 3;
    }

    // R,C 크기가 다르므로 방향에 맞게 조정 
    public void reMakeSpeed(int R, int C) {
        if(dir == 1 || dir == 2) {
            speed = speed % (2 * (R-1));
        }else {
            speed = speed % (2 * (C-1));
        }
    }
}