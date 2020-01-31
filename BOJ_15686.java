// [BOJ] 백준 15686 치킨배달
// 문제: https://www.acmicpc.net/problem/15686

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
    static int N, M;
    static int sum, answer = Integer.MAX_VALUE;
    static List<Node> cList, hList;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
        
		// 치킨 집 리스트
        cList = new ArrayList<>();
        // 일반 집 리스트
        hList = new ArrayList<>();
        
        /*
         0 : 빈 칸
         1 : 집
         2 : 치킨 집
        */
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {

				int temp = Integer.parseInt(st.nextToken());
				// 일반 집인 경우
				if (temp == 1) {
					hList.add(new Node(i, j));
				}
				// 치킨집이라면 리스트에 저장
				else if (temp == 2) {
					cList.add(new Node(i, j));
				}
			}
		}
        
        
        // 폐업되지 않은 치킨집의 위치를 알아보기 위해 수학의 '조합'이용
        List<Node> selectedChickenHouse = new ArrayList<>();
        // 리스트에서 뽑기 시작할 인덱스 전달
        combination(0, selectedChickenHouse);
        
        bw.write(answer + "\n");

		bw.flush();
		bw.close();
    }

    private static void combination(int idx, List<Node> selectedChickenHouse) {
        // 살아남은 치킨 집 개수 M개를 만족했다면
        if(selectedChickenHouse.size() == M) {
            // 살아남은 치킨집으로 도시를 재구성한다.
            reorganizeCity(selectedChickenHouse);
            return;
        }
        // 만족하는 M 만큼의 치킨집 조합에 실패했다면 종료
        if(idx >= cList.size()) {
            return;
        }
        
        // 리스트에서 해당 위치의  치킨집을 살리든가 말든가 둘 중 하나        
        // 뽑은 경우
        selectedChickenHouse.add(cList.get(idx));
        combination(idx + 1, selectedChickenHouse);
        
        // 뽑지 않은 경우, 위에서 넣었던것을 무효화시키고 다시 뽑는다. 다음 원소로 재귀진행
        // 넣고 빼야 조합이 오름차순으로 진행된다. 물론 이 문제는 굳이 그렇게 할 필요까지는 없다.
        selectedChickenHouse.remove(selectedChickenHouse.size() - 1);
        combination(idx + 1, selectedChickenHouse);
    }


    private static void reorganizeCity(List<Node> selectedChickenHouse) {
        // 현재 상태에서의 도시 치킨 거리를 초기화
        sum = 0;

        // 일반집에서 가장 가까운 치킨집까지의 거리를 구해서 '도시의 치킨 거리에 더해준다.'
        for(int i=0; i<hList.size(); i++) {
            Node house = hList.get(i);
            
            // 일반 집과 치킨집까지의 거리
            int minDistance = Integer.MAX_VALUE;
            
            // 가장 가까운 치킨집까지의 거리를 도출
            for(int j=0; j<selectedChickenHouse.size(); j++) {
                Node chickenHouse = selectedChickenHouse.get(j);
                minDistance = Math.min(minDistance, distance(house, chickenHouse));
            }
            // 가장 최소의 거리를 합한다.
            sum += minDistance;
        }
        // 기존값과 비교하여 최솟값으로 변경
        answer = Math.min(answer, sum);
    }

    private static int distance(Node A, Node B) {
        return Math.abs(A.x - B.x) + Math.abs(A.y - B.y);
    }
}

class Node{
    int x,y;
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }    
}