// [BOJ] 백준 17135 캐슬 디펜스
// https://www.acmicpc.net/problem/17135

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static public int M, N, D, answer;
    // 최대한 적을 많이 잡는 배치를 잡아야 하므로
    // 원본 배열 gameMap | 테스트를 진행할 map
    static public int[][] gameMap, map;
    static public boolean[][] visited;
    // 궁전의 위치를 담는 리스트
    static public List<Node> castle;
    // 적 자체는 움직이지 않고 사격 범위만 조정
    static int sR, eR;
    // 아래로 내려가지않으면서 좌,상,우 순으로 (왼쪽이 우선순위가 높도록)
    static int dx[] = { 0, -1, 0 };
    static int dy[] = { -1, 0, 1 };


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // ### 입력 받기 ###
        M = Integer.parseInt(sc.next());
        N = Integer.parseInt(sc.next());
        D = Integer.parseInt(sc.next());

        gameMap = new int[M][N];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                gameMap[i][j] = Integer.parseInt(sc.next());
            }
        }
        // ### 입력 완료 ###

        // 주어진 열 크기에 맞게 성(Castle)을 만든다.
        castle = new ArrayList<Node>();
        for (int i = 0; i < N; i++) {
            castle.add(new Node(M, i));
        }

        // 성에 배치될 궁수들의 리스트
        List<Node> archerList = new ArrayList<>();
        // 어느 배치로 구성할 지 조합 이용
        combination(0, archerList);

        System.out.println(answer);
    }


    private static void combination(int idx, List<Node> archerList) {


        // 3명의 궁수 위치가 선정되었다면
        if (archerList.size() == 3) {
            gameStart(archerList);
            return;
        }
        
        // 만족하는 M 만큼의 치킨집 조합에 실패했다면 종료
        if (idx >= castle.size()) {
            return;
        }


        archerList.add(castle.get(idx));
        combination(idx + 1, archerList);


        // 뽑지 않은 경우, 위에서 넣었던것을 무효화시키고 다시 뽑는다. 다음 원소로 재귀진행
        // 넣고 빼야 조합이 오름차순으로 진행된다. 물론 이 문제는 굳이 그렇게까지 할 필요는 없다.
        archerList.remove(archerList.size() - 1);
        combination(idx + 1, archerList);


    }


    private static void gameStart(List<Node> archerList) {
        List<Node> testArcherList = new ArrayList<>();
        
        // class 객체는 공유되기 때문에 복사해서 시뮬레이션
        // 문제상의 이동은 적들이 한줄씩 내려오지만 궁수만 위로 올라가는 형식으로 구현
        for (int i = 0; i < 3; i++) {
            Node archer = archerList.get(i);
            testArcherList.add(new Node(archer.x, archer.y));
        }
        
        // 각 궁수 배치마다 Reset
        map = mapReset();
        Map<String, Node> target = new HashMap<String, Node>();
        // 해당 궁수 배치의 제거한 적의 수
        int removeCnt = 0;


        // 궁수들의 저격 범위(시작 row와 끝 row 설정)
        for (sR = M - 1; sR >= 0; sR--) {
            // 사격 거리가 D 인 것을 고려
            eR = sR - D + 1;
            // 범위를 벗어나지 않게 재조정
            if (eR < 0) eR = 0;            
            
            // 3명의 궁수에 대해서 가장 가까운 적을 구한다.
            // 문제 정의상 BFS 구현
            for (int i = 0; i < 3; i++) {
                Node archer = testArcherList.get(i);
                // BFS 탐색을 위한 방문표시 초기화
                visited = new boolean[M][N];
                
                Node enemy = BFS(archer);
                
                // 저격할 적이 존재한다면
                // 다른 궁수에 대해서도 동시저격될 수 있으므로 HashMap에 우선 모아둔다.
                if (!(enemy == null)) {
                    target.put(enemy.x + "_" + enemy.y, new Node(enemy.x, enemy.y));
                }
            }
            
            // hashMap에서는 중복처리를 해주므로 제거된 적의 수를 count
            removeCnt += target.size();
            
            // 이동 후에 재저격되지 않도록 지도(map)에서 배제
            Iterator<String> it = target.keySet().iterator();
            while (it.hasNext()) {
                Node n = target.get(it.next());
                // 제거된 적 지도에 표시
                map[n.x][n.y] = 0;
            }
            
            // 다음 진행을 위해 목록 초기화
            target.clear();
            // 궁수들의 위치를 옮겨준다. (한칸씩 위로)
            for (int i = 0; i < 3; i++) {
                testArcherList.get(i).x--;
            }
        }
        
        // 이번 궁수의 배치가 가장 많은 적을 잡았는지 확인
        answer = (answer < removeCnt) ? removeCnt : answer;
    }


    private static Node BFS(Node archer) {
        Queue<Node> queue = new LinkedList<>();
        
        // BFS 시작지점에 적이 있는지 확인 (배치된 궁수에게서 가장 가까운 지점)
        if(map[sR][archer.y] == 1) {
            return new Node(sR, archer.y);
        }
        
        queue.offer(new Node(sR, archer.y));
        Node enemy = null;
        
        while(queue.size() > 0) {
            Node v = queue.poll();
            
            for(int i=0; i<3; i++) {
                int nX = v.x + dx[i];
                int nY = v.y + dy[i];
                
                // 배열 범위를 벗어나는지 확인
                if(nX < eR || nY < 0 || nX > sR || nY >= N) {
                    continue;
                }
                
                // 기존에 방문한 곳이라면
                if(visited[nX][nY]) {
                    continue;
                }
                
                // 사격범위를 벗어나면
                if(distance(archer, new Node(nX, nY)) > D) {
                    continue;
                }
                
                // 적이 존재하면서 유효한 저격범위라면
                if (map[nX][nY] == 1 &&
                        distance(archer, new Node(nX, nY)) <= D) {
                    return new Node(nX, nY);
                }
                
                // 적을 발견하지 못했으면 아직 유효한 사격범위이면
                visited[nX][nY] = true; // 방문 표시
                queue.offer(new Node(nX, nY));
            }
        }
        
        return enemy;
    }


    private static int[][] mapReset() {
        map = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = gameMap[i][j];
            }
        }
        return map;
    }

    private static int distance(Node archer, Node enemy) {
        return Math.abs(archer.x - enemy.x) + Math.abs(archer.y - enemy.y);
    }
}


class Node {
    int x, y;
    int distance;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + (x+1) + ", " + (y+1) + "]";
    }
}