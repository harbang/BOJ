// [BOJ] 백준 16637 괄호 추가하기
// https://www.acmicpc.net/problem/16637

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {    
    
    static List<Integer> oprList;
    static int answer;
    static String str;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.next());
        str = sc.next();
        
        // 주어진 수식에서 괄호를 구성할 수 있는 개수
        int bracketCnt = (str.length() / 2) / 2;
        
        oprList = new ArrayList<Integer>();
        // 연산자 인덱스
        for(int i=1; i<str.length()-1; i=i+2) {
            oprList.add(i);
        }
        
        // 연산자
        List<String> A = new ArrayList<>();
        // 피연산자
        List<Integer> B = new ArrayList<>();
        
        for(int i=0; i<str.length(); i++) {
            // 홀수번째는 연산자
            if(i % 2 == 1) {
                A.add(str.substring(i, i+1));
            }
            // 짝수번째는 피연산자
            else {
                B.add(Integer.parseInt(str.substring(i, i+1)));
            }
        }
        // 기준값 설정
        answer = calculator(A, B);
        
        // 최대 구성할 수 있는 괄호쌍 개수만큼 성립할 수 있는 수식 완전탐색
        for(int bCnt=1; bCnt<=bracketCnt; bCnt++) {
            List<Integer> operator = new ArrayList<>();
            choiceBracket(bCnt, operator, 0);
        }
        
        System.out.println(answer);
    }


    private static void choiceBracket(int maxBracketCnt, List<Integer> operator, int idx) {
        if(operator.size() >= maxBracketCnt) {
        
            if(operator.size() > 1) {
                for(int i=1; i<operator.size(); i++) {
                    // 괄호가 있는 수식을 구상했을 때, 1*3*5 > (1*(3)*5)가 괄호가 겹치지 않도록
                    if(Math.abs(operator.get(i-1) - operator.get(i)) == 2){
                        return;
                    }
                }
            }
            
            List<Integer> temp = new ArrayList<>();
            // 깊은 복사
            temp.addAll(operator);            
            // 연산자
            List<String> A = new ArrayList<>();
            // 피연산자
            List<Integer> B = new ArrayList<>();
            
            int priority = temp.remove(0);
            
            int i = 0;
            while(true) {
                if(i >= str.length()) {
                    break;
                }
                // 우선순위(괄호)가 있는 연산자라면
                // 계산결과를 구해서 넣는다.
                if(i == priority) {
                    int X, Y;
                    switch (str.substring(i, i+1)) {
                    case "+":
                        X = B.remove(B.size()-1);
                        Y = Integer.parseInt(str.substring(i+1, i+2));
                        B.add(X+Y);
                        break;
                    case "-":
                        X = B.remove(B.size()-1);
                        Y = Integer.parseInt(str.substring(i+1, i+2));
                        B.add(X-Y);    
                        break;
                    case "*":
                        X = B.remove(B.size()-1);
                        Y = Integer.parseInt(str.substring(i+1, i+2));
                        B.add(X*Y);
                        break;    
                    default:
                        break;
                    }
                    // 다음 우선순위가 있는지 확인
                    if(!temp.isEmpty()) {
                        priority = temp.remove(0);
                    }
                    i = i+2;
                    continue;
                }
                // 홀수번째는 연산자
                if(i % 2 == 1) {
                    A.add(str.substring(i, i+1));
                }
                // 짝수번째는 피연산자
                else {
                    B.add(Integer.parseInt(str.substring(i, i+1)));
                }
                i++;
            }

            int tempAns = calculator(A, B);
            answer = (answer < tempAns) ? tempAns : answer;
            return;
        }
        
        // 더 이상 구할 것이 없다면
        if(idx >= oprList.size()) return;
        
        operator.add(oprList.get(idx));
        choiceBracket(maxBracketCnt, operator, idx + 1);
        
        operator.remove(operator.size() - 1);
        choiceBracket(maxBracketCnt, operator, idx + 1);
        
    }


    private static int calculator(List<String> operator, List<Integer> operand) {
        int X, Y;
        while(operator.size() > 0) {
            String opr = operator.remove(0);
            switch (opr) {
            case "+":
                X = operand.remove(0);
                Y = operand.remove(0);
                operand.add(0, X+Y);
                break;
            case "-":
                X = operand.remove(0);
                Y = operand.remove(0);
                operand.add(0, X-Y);                            
                break;
            case "*":
                X = operand.remove(0);
                Y = operand.remove(0);
                operand.add(0, X*Y);
                break;    
            default:
                break;
            }
        }
        
        // 최종값을 반환
        return operand.remove(0);
    }
}