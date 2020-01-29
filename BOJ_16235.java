// [BOJ] 백준 16235 나무 재테크
// 문제: https://www.acmicpc.net/problem/16235

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    static public int N, M, K;
    // 겨울에 주어지는 양분, 각 지점에 몇개의 나무가 심어져 있는지 표시하는 지도, 현재 상태의 양분
    static public int[][] A, map, food;
    static public List<ArrayList<Tree>> TreeList;
    
    // 인접한 지점
    static public int[] dx = {-1,1,0,0,-1,-1,1,1};
    static public int[] dy = {0,0,-1,1,-1,1,-1,1};
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 땅의 크기
        N = Integer.parseInt(sc.next());
        // 나무의 개수
        M = Integer.parseInt(sc.next());
        // 시간(year)
        K = Integer.parseInt(sc.next());
        
        // 각 지점에 주어지는 양분 입력
        A = new int[N][N];
        // 각 지점의 고유 ID 부여
        map = new int[N][N];
        // 실제 양분 Gage 표시
        food = new int[N][N];
        int ID = 0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                A[i][j] = Integer.parseInt(sc.next());
                // 해당 지점에 고유의 ID를 부여
                map[i][j] = ID++;
                // 초기 양분 = 5
                food[i][j] = 5;
            }
        }
        
        // 각 지점의 나무들을 표현하기 위해 ArrayList 안에 ArrayList 형태 이용.
        TreeList = new ArrayList<ArrayList<Tree>>();
        for(int i=0; i<N*N; i++) {
            // 각 지점에 비어있는 나무 List를 넣는다.
            TreeList.add(new ArrayList<>());
        }
        for(int i=0; i<M; i++) {
            // 입력받는 나무의 인덱스는 1이므로 0으로 시작되도록 조정
            int x = Integer.parseInt(sc.next()) - 1;
            int y = Integer.parseInt(sc.next()) - 1;
            int age = Integer.parseInt(sc.next());
            
            ID = map[x][y];
            // 해당 지점의 나무 List에 나무 add
            TreeList.get(ID).add(new Tree(x, y, age));
        }
        
        for(int i=0; i<N*N; i++) {
            List<Tree> trees = TreeList.get(i);
            // 입력으로 주어질 때, 나이순으로 입력받는 말이 없으므로
            // 나이를 기준으로 오름차순 정렬
            Collections.sort(trees, new Comparator<Tree>() {
                @Override
                public int compare(Tree t1, Tree t2) {
                    if (t1.age < t2.age) {
                        return -1;
                    } else if (t1.age > t2.age) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
        
        // K년 만큼 반복
        while(K-- > 0) {
            spring();
            summer();
            autumn();
            winter();
        }
        
        // 각 지점별 살아남은 나무의 개수 확인(나이 X)
        printAnswer();
    }

    // 나무는 위치한 칸에서 나이만큼 양분을 먹어서, 나이가 1증가한다.
    // (한 칸에 여러나무가 존재하면 어린 나이의 나무부터 양분을 먹는다.)
    // (해당 지점에 양분이 부족하여, 나이만큼 충분한 양분을 먹지 못하면 나무는 죽는다.)
    private static void spring() {
        for(int i=0; i<N*N; i++) {
            List<Tree> trees = TreeList.get(i);
            // 나무가 존재한다면 양분 먹기 시작.
            if(trees.size() > 0) {
                for(Iterator<Tree> it = trees.iterator();it.hasNext();) {
                      Tree tree = it.next();
                      // 나이만큼 양분을 줄 수 있다면
                      if(tree.age <= food[tree.x][tree.y]) {
                          // 해당 양분 나무 나이만큼 줄이기
                          food[tree.x][tree.y] -= tree.age;
                          // 나무 나이 +1
                          tree.age++;
                      }
                      // 양분을 줄 수 없는 시점이라면 Trees의 나머지 나무는 죽게된다.
                      else {
                         tree.isDead = true;
                      }
                }
            }
        }
    }

    // 이전단계에서 죽은 나무가 양분으로 변한다.
    // (각각의 죽은 나무마다 나이를 2로 나눈 값, 소수점 아래는 버린다.)
    private static void summer() {
        for(int i=0; i<N*N; i++) {
            List<Tree> trees = TreeList.get(i);
            if(trees.size() > 0) {
                for(Iterator<Tree> it = trees.iterator() ; it.hasNext();) {
                      Tree tree = it.next();
                      // 나무가 죽어있다면
                      if(tree.isDead) {
                          // 해당지점에서 양분으로 변화
                          food[tree.x][tree.y] += (int) tree.age / 2;
                          // 양분으로 변한 나무는 trees 리스트안에서 삭제
                          it.remove();
                      }
                }
            }
        }
    }

    // 나무의 나이가 5의 배수이면 배열을 벗어나지 않는 인접한 곳에 나이가 1인 나무를 심는다.
    // (한 곳에서 번식해야 할 나무가 여러개이면 번식이 여러번 될 수 있다.)
    private static void autumn() {
        for(int i=0; i<N*N; i++) {
            List<Tree> trees = TreeList.get(i);
            if(trees.size() > 0) {
                for(Iterator<Tree> it = trees.iterator() ; it.hasNext();) {
                      Tree tree = it.next();
                      // 나무의 나이가 5의 배수라면
                      if(tree.age % 5 == 0) {
                          // 인접한 곳으로 나무 번식
                          for(int j=0; j<8; j++) {
                              int nX = tree.x + dx[j];
                              int nY = tree.y + dy[j];
                              // 배열 범위를 벗어나면 continue
                              if(nX < 0 || nY < 0 || nX >= N || nY >= N) continue;
                              
                              int ID = map[nX][nY];
                              // 해당 지점의 trees에 1살짜리 나무 심기
                              // 제일 어리므로 리스트 앞쪽에 삽입
                              TreeList.get(ID).add(0, new Tree(nX, nY, 1));
                          }
                      }
                }
            }
        }
    }
    
    // 각 지역에 A[r][c]만큼 추가 양분을 준다.
    private static void winter() {
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                food[i][j] = food[i][j] + A[i][j];
            }
        }
    }

    private static void printAnswer() {
        int answer = 0;
        for(int i=0; i<N*N; i++) {
            answer += TreeList.get(i).size();
        }
        System.out.println(answer);
    }
}

class Tree{
    // 위치
    int x, y;
    // 나이
    int age;
    // 나무 상태
    boolean isDead;
    
    public Tree(int x, int y, int age) {
        this.x = x;
        this.y = y;
        this.age = age;
        isDead = false;
    }


    @Override
    public String toString() {
        return "Tree [나이=" + age + " 상태=" + (!isDead) +"]";
    }
}