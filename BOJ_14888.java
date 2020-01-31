// [BOJ] 백준 14888 연산자 끼워넣기
// 문제: https://www.acmicpc.net/problem/14888

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Main {
    
    static List<String> operatorList;
    static List<Integer> operand;
    static int min =Integer.MAX_VALUE, max=Integer.MIN_VALUE;
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new  InputStreamReader(System.in));
           BufferedWriter bw = new BufferedWriter(new  OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        
        // 피연산자를 저장해둔다.
        StringTokenizer st = new StringTokenizer(br.readLine());
        operand = new ArrayList<>();
        for(int i=0; i<N; i++) {
            operand.add(Integer.parseInt(st.nextToken()));
        }
        
        int[] operator = new int[4];
        operatorList = new ArrayList<>();
        
        // 입력받은 연산자개수 만큼 해당 연산자를 우선 리스트에 보관한다.
        String[] opr = {"+", "-", "*", "/"};
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<4; i++) {
            operator[i] = Integer.parseInt(st.nextToken());
            while(operator[i]-- > 0) {
                // 개수만큼 해당 되는 연산자 기호를 넣는다.
                operatorList.add(opr[i]);
            }
        }
        // 순열을 위한 방문 표시
        boolean[] visited = new boolean[operatorList.size()];
        List<String> list = new ArrayList<>();
        
        // 연산자를 순열로 재구성하고 계산결과를 구한다.
        permutation(list, visited);
        
        // 정답 출력
        bw.write(max + "\n");
        bw.write(min + "\n");
        bw.flush(); bw.close();
    }

    private static void permutation(List<String> list, boolean[] visited) {
        if(list.size() == operatorList.size()) {
            
            int result = calculate(list);
            min = min > result ? result : min;
            max = max < result ? result : max;
            return;
        }
        
        for(int i=0; i<operatorList.size(); i++) {
            // 아직 사용하지 원소라면
            if(!visited[i]) {
                // 방문 표시
                visited[i] = true; list.add(operatorList.get(i));
                permutation(list, visited);                
                visited[i] = false; list.remove(list.size()-1);
            }
        }
        
    }

    private static int calculate(List<String> list) {
        // 제일 처음 피연산자로 초기화 해둔다.
        int result = operand.get(0);
        // 연산자와 피연산자의 개수가 맞지 않은 경우는 없으므로 차례대로 연산의 겨로가를 구한다.
        for(int i=1; i<operand.size(); i++) {
            int target = operand.get(i);
            String operator = list.get(i-1);
            if(operator.equals("+")) result += target;
            else if(operator.equals("-")) result -= target;
            else if(operator.equals("*")) result *= target;
            else if(operator.equals("/")) {
                if(result < 0) { // 분자가 음수인 경우
                    result = Math.abs(result) / target;
                    result *= -1;
                }else {
                    result = result / target;
                }
            }
        }
        return result;
    }
}