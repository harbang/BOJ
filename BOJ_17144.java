// [BOJ] 백준 17144 미세먼지 안녕
// 문제: https://www.acmicpc.net/problem/17144
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static public int R, C, T;
    static public int[][] A, diffusion;
    static public List<AirCleaner> machines;
    
    // 확산 방향(상하좌우)
    static public int[] dx = {-1, 1, 0, 0};
    static public int[] dy = {0, 0, -1, 1};
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        R = Integer.parseInt(sc.next());
        C = Integer.parseInt(sc.next());
        T = Integer.parseInt(sc.next());
        
        machines = new ArrayList<AirCleaner>();
        
        A = new int[R][C];
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                A[i][j] = Integer.parseInt(sc.next());
                // 공기청정기인 경우 객체 생성
                if(A[i][j] == -1) {
                    machines.add(new AirCleaner(i, j));
                }
            }
        }
        
        // T 초 동안 진행
        while(T-- > 0) {
            // ############
            // 미세먼지 확산
            // ############
            spread();
            

            // 위쪽 공기청정기 작동
            machines.get(0).ccwwork(A);
            // 아래쪽 공기청정기 작동
            machines.get(1).cwwork(A);
            
        }
        
        // 정답출력
        printAnswer();
    }
    
    private static void spread() {
        // 전 구역에 한번에 확산 수치를 계산하기 위한 임시저장소
        diffusion = new int[R][C];
        
        for(int x=0; x<R; x++) {
            for(int y=0; y<C; y++) {
                // 미세먼지가 있다면
                if(A[x][y] > 0) {
                    int cnt = 0;
                    // 확산 가능한 방향 확인
                    for(int i=0; i<4; i++) {
                        int nX = x + dx[i];
                        int nY = y + dy[i];
                        
                        // 배열 범위를 벗어나면
                        if(nX < 0 || nY < 0 || nX >= R || nY >= C) continue;
                        
                        // 공기청정기가 위치한다면
                        if(A[nX][nY] == -1 ) continue;
                        // 다른쪽에서도 유입될 수 있으므로 각 방향에 대해 나중에 한번에 처리
                        diffusion[nX][nY] += (A[x][y] / 5);
                        // 확산가능한 방향 개수
                        cnt++;
                    }
                    // 미세먼지 확산(유출)
                    A[x][y] -= (A[x][y] / 5 * cnt);
                }
            }
        }
        
        // 각 방향에 유입된 미세먼지를 합한다.
        for(int x=0; x<R; x++) {
            for(int y=0; y<C; y++) {
                A[x][y] += diffusion[x][y];
            }
        }
    }


    private static void printAnswer() {
        int answer = 0;
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                // 공기청정기를 제외한 자리의 미세먼지 양 측정
                if(A[i][j] == -1) continue;
                answer += A[i][j];
            }
        }
        System.out.println(answer);
    }


    private static void printState(int[][] arr) {
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[0].length; j++) {
                System.out.printf("%3d ", arr[i][j]);
            }
            System.out.println();
        }
    }
}


class AirCleaner{
    int x, y;
    
    // 시계 방향
    static int[] cw_dx = {0, 1, 0, -1};
    static int[] cw_dy = {1, 0, -1, 0};


    // 반시계 방향
    static int[] ccw_dx = {0, -1, 0, 1};
    static int[] ccw_dy = {1, 0, -1, 0};
    
    public AirCleaner(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // 방향에 해당되는 인자 전달
    public void cwwork(int[][] A) {
        work(A, cw_dx, cw_dy);
    }


    public void ccwwork(int[][] A) {
        work(A, ccw_dx, ccw_dy);
    }


    public void work(int[][] A, int[] dx, int[] dy) {
        List<Integer> list = new ArrayList<>();
        
        // 공기청정기의 위치에서 시작
        int r = x;
        int c = y;
        int i = 0;
        int R = A.length;
        int C = A[0].length;
        
        while(true) {
            int nR = r + dx[i];
            int nC = c + dy[i];
            // 배열 범위를 벗어나면 방향 전환해서 재조정
            if(nR < 0 || nC < 0 || nR >= R || nC >= C) {
                i++;
                nR = r + dx[i];
                nC = c + dy[i];
            }
            
            r = nR; c = nC;
            // 공기청정기 위치이면 break
            if(A[nR][nC] == -1) break;
            // 미세먼지 값을 저장
            list.add(A[nR][nC]);
        }
        
        // 순환된 것처럼 한칸씩 이동 구현
        // 첫번째 원소는 공기청정기에서 미세먼지 없는 공기가 나온 것.
        list.add(0, 0);
        // 마지막 원소는 공기청정기 안으로 들어갔으므로 제거
        list.remove(list.size()-1);
        
        // 순환된 원소 재배치 (r, c 공기청정기 위치)
        i = 0;
        while(true) {
            int nR = r + dx[i];
            int nC = c + dy[i];
            // 배열 범위를 벗어나면 방향 전환해서 재조정
            if(nR < 0 || nC < 0 || nR >= R || nC >= C) {
                i++;
                nR = r + dx[i];
                nC = c + dy[i];
            }
            
            r = nR; c = nC;
            // 공기청정기 위치이면 break
            if(A[nR][nC] == -1) break;
            
            A[nR][nC] = list.remove(0);
        }
    }
}