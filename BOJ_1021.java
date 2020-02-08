// [BOJ] 백준 1021 회전하는 큐
// https://www.acmicpc.net/problem/1021

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 1 <= N <= 50
        int N = Integer.parseInt(sc.next());
        // 1 <= M <= N
        int M = Integer.parseInt(sc.next());
        
        Queue<Integer> queue = new LinkedList<>();
        // 뽑고자 하는 위치(번호)를 입력 받는다.
        for(int i=0; i<M; i++) {
            queue.add(Integer.parseInt(sc.next()));
         }
        
        // N만큼 덱 원소를 구성한다.
        DQueue DQ = new DQueue();
        for(int i=1; i<=N; i++) {
            DQ.insertRear(i);
        }
        
        int answer = 0;
        while(!queue.isEmpty()) {
            // 뽑고자 하는 번호
            int target = queue.poll();
            
            // 뽑고자 하는 번호의 위치
            // 덱의 내부는 진행되면서 위치가 바뀌기 때문에 그때그때 찾아가야 한다.
            int targetIdx = DQ.searchTarget(target);
            
            
            // 2번연산(왼쪽으로 이동)과 3번연산(오른쪽으로 이동) 중 어떤 것이 나은지 판단한다.
            int opr_2 = targetIdx - 1; // 3번연산을 하였을 때 횟수
            // 제일 뒤로 이동한 후 1번더 이동하여 제일 앞으로 와야 한다.
            int opr_3 = DQ.size() - targetIdx + 1; // 3번연산을 하였을 때 횟수
            
            // 2번연산을 하는 것이 좀 더 최소로 한다면...
            if(opr_2 < opr_3) {
                for(int i=0; i<opr_2; i++) {
                    int val = DQ.deleteFront();
                    DQ.insertRear(val);
                }
                // for문을 돌면서 일일이 answer++할 필요 없다.
                answer = answer + opr_2;
            }
            else { // 3번 연산을 최소로 한다면..
                for(int i=0; i<opr_3; i++) {
                    int val = DQ.deleteRear();
                    DQ.insertFront(val);
                }
                answer = answer + opr_3;
            }
            // 2번연산을 하였든, 3번 연산을 하였든 마지막에는 1번연산으로 번호를 뽑는다.
            // 그래야 다음 숫자를 뽑을 때 덱에 반영된다!
            DQ.remove();
        }
        
        System.out.println(answer);
        
    }
}

class DQNode{
    
    int data;
    DQNode left;
    DQNode right;
    
    public DQNode(int data) {
        this.data = data;
    }
}
class DQueue{
    DQNode front;
    DQNode rear;
    
    // 앞으로 삽입
    void insertFront(int data) {
        DQNode node = new DQNode(data);
        // 비어있다면(처음 넣는 단계라면)
        if(isEmpty()) {
            front = node;
            rear = node;
            return;
        }
        // 제일 앞에 있던 노드는 뒤로 밀려나야 한다.
        node.right = front;
        front.left = node;
        front = node;
    }
    


    // 뒤로 삽입
    void insertRear(int data) {
        DQNode node = new DQNode(data);
        // 비어있다면(처음 넣는 단계라면)
        if(isEmpty()) {
            front = node;
            rear = node;
            return;
        }
        // 제일 뒤에 있던 노드가 앞으로 밀려난다.
        node.left = rear;
        rear.right = node;
        rear = node;
    }
    
    // 앞의 원소를 반환
    int deleteFront() {
        // 현재(기존) front의 데이터를 반환해야 한다.
        int val = front.data;
        // 만약 마지막 노드였다면 모든 연결을 끊는다.
        if(front.right == null) {
            front = null;
            rear = null;
        }else {
            // 오른쪽에서 왼쪽으로의 연결을 끊는다.
            front.right.left = null;
            // front를 오른쪽(뒤)으로 옮긴다.
            front = front.right;
            // 기존 front에서 왼쪽에서 오른쪽 연결은 존재하지만 활용하지 않으므로 상관없다.
        }
        return val;
    }
    
    // 뒤의 원소를 반환
    int deleteRear() {
        // 현재(기존) rear의 데이터를 반환해야 한다.
        int val = rear.data;
        // 만약 마지막 노드였다면 모든 연결을 끊는다.
        if(rear.left == null) {
            front = null;
            rear = null;
        }else {
            // 왼쪽 노드에서 오쪽으로의 연결을 끊는다.
            rear.left.right = null;
            // rear를 왼쪽(앞)으로 옮긴다.
            rear = rear.left;
            /*
             * 위의 두 줄의 코드는 아래와 같이 표현할 수도 있다.
             * rear = rear.left;
             * rear.right = null;
             * 즉, 이동한 다음 연결을 끊은 것이다.
             */
        }
        return val;
    }
    
    // 앞의 원소를 제거(문제의 1번 연산에 해당)
    public void remove() {
        if(front.right == null) {
            front = null;
            rear = null;
        }else {
            front = front.right;
            front.left = null;
        }
    }
    
    // 비어있는지 확인
    boolean isEmpty() {
        return (front == null || rear == null);
    }
    
    // 진행하는 과정에서 뽑고자 하는 번호의 위치들을 탐색
    public int searchTarget(int target) {
        DQNode temp = front;
        // 배열도 아니므로 인덱스를 '1'번부터 하겠다.
        int idx = 1;
        while(temp != null) {
            if(temp.data == target) {
                break;
            }
            // 뒤쪽으로 이동
            idx++;
            temp = temp.right;
        }
        return idx;
    }
    
    // 현재 덱의 크기(제일 뒤의 인덱스를 구하고자 하는 것이다.)
    public int size() {
        DQNode temp = front;
        // 배열도 아니므로 인덱스를 '1'번부터 하겠다.
        int idx = 0;
        while(temp != null) {
            // 뒤쪽으로 이동
            idx++;
            temp = temp.right;
        }
        return idx;
    }
}