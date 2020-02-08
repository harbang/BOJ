// [BOJ] 백준 5430 AC
// https://www.acmicpc.net/problem/5430

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int T = Integer.parseInt(sc.next());
        
        for(int i=0; i<T; i++) {
            String p = sc.next();
            int n = Integer.parseInt(sc.next());
            String X = sc.next();  
            X = X.replace("[", ",").replace("]", ",");
            String[] arrX = X.split(",");
            
            DQueue DQ = new DQueue();
            
            // arr[0]은 제외
            for(int j=1; j<arrX.length; j++) {
                DQ.insertRear(Integer.parseInt(arrX[j]));
            }
            
            // 초기 상태에서 뒤집어진 상태여부
            boolean isReverse = false;
            
            // 비어있는 값에서 삭제라면 error 출력 후 종료해야 한다.
            boolean isError = false;
            for(int j=0; j<p.length(); j++) {
                String opr = p.substring(j, j+1);
                // 뒤집는 경우라면
                if(opr.equals("R")) {
                    isReverse = !isReverse;
                    continue;
                }
                
                // 삭제할 때 뒤집어진 상태와 에러 발생여부를 확인해야한다.
                if(DQ.remove(isReverse)) {
                    isError = true;
                    break;
                }
            }
            
            if(isError) {
                System.out.println("error");
            }else {
                DQ.printDQ(isReverse);
            }
            
            
        }
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
    
    // 비어있는지 확인
    boolean isEmpty() {
        return (front == null || rear == null);
    }
    
    // 뒤집어진 여부에 따라 앞에서 삭제할지, 뒤에서 삭제할지 결정한다.
    boolean remove(boolean isReverse) {
        
        if(isEmpty()) {
            return true;
        }
        // 초기상태에서 뒤집어진 상태라면
        if(isReverse) {
            if(rear.left == null) { // 마지막 노드라면..
                rear = null;
                front = null;
            }else {
                rear = rear.left; // 앞쪽으로 이동
                rear.right = null;
            }
            
        }else { // 초기 상태에서 뒤집어지지 않은 상태라면
            if(front.right == null) { // 마지막 노드라면..
                rear = null;
                front = null;
            }else {
                front = front.right; // 뒤쪽으로 이동
                front.left = null;
            }
        }
        
        // 에러가 나지 않았다면
        return false;
    }
    
    // 뒤집어진 상태에 따라 앞에서부터 출력할지 뒤에서부터 출력할지 결정한다.
    void printDQ(boolean isReverse) {
        
        System.out.print("[");
        DQNode temp;
        if(isReverse) {
            temp = rear;    
            while(temp != null) {
                System.out.print(temp.data);
                // 다음원소가 존재한다면 콤마(,) 출력
                if(temp.left != null) {
                    System.out.print(",");
                }
                
                // 다음원소가 있든 없든 우선 앞으로 이동
                temp = temp.left;
            }
        }else { // 앞에서(front) 부터 출력하겠다.
            temp = front;    
            while(temp != null) {
                System.out.print(temp.data);
                // 다음원소가 존재한다면 콤마(,) 출력
                if(temp.right != null) {
                    System.out.print(",");
                }
                
                // 다음원소가 있든 없든 우선 뒤로 이동
                temp = temp.right;
                
            }
        }
        // 다음 Test case를 위해 개행문자 포함!
        System.out.println("]");
    }
}