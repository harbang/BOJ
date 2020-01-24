// [BOJ] 백준 1874 스택 수열
// 문제: https://www.acmicpc.net/problem/1874
// 풀이: https://octorbirth.tistory.com/101

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int N = Integer.parseInt(sc.next());

        Queue<Integer> arr = new LinkedList<>();
        // 수열 입력
        for (int i = 0; i < N; i++) {
            arr.add(Integer.parseInt(sc.next()));
        }

        // 알고리즘을 구현하는 데 사용한 stack
        Stack<Integer> stack = new Stack<>();
        // 정답을 저장한 queue
        Queue<String> answer = new LinkedList<>();

        int idx = 1;
        // 주어진 모든 수에 대해서 해결될 때까지
        while (!arr.isEmpty()) {
            // 수열에서 원소 1개를 지정
            int target = arr.poll();

            // 아직 stack에 넣은적이 없기에 해당 단계가 나올 때까지 push한다.
            if (target > idx) {
                // 같아질때까지 스택에 push한다.
                while (target != idx) {
                    stack.push(idx);
                    answer.add("+");
                    idx++;
                }
            }
            // 때마치 등장하는 숫자이면
            if (target == idx) {
                // 넣었다가 뺀다.
                answer.add("+");
                answer.add("-");
                // 다음 단계를 진행
                idx++;
                continue;
            }

            // 기존에 나왔던 숫자이므로 스택에 들어 있을 것이다.
            // 스택에서 값을 꺼내 확인해야 한다.
            // 스택안에 top 위치에 있어야 수열을 형성할 수 있다.
            if (target < idx) {
                int popValue = stack.pop();
                if (popValue == target) { // 꺼낸 수간 일치한다면 문제 없지만.
                    answer.add("-");
                } else { // 일치하지 않은 경우 별도의 스택이 존재하지 않기에 만들수 없는 수열에 해당된다.
                    System.out.println("NO");
                    return;
                }
            }
        }

        while (!answer.isEmpty()) {
            System.out.println(answer.poll());
        }

    }
}