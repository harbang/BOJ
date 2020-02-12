// [BOJ] 백준 1931 회의실 배정
// https://www.acmicpc.net/problem/1931

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Conference{
    
    int start;
    int end;
    
    public Conference(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "시작시간: " + start + ", 종료시간: " + end;
    }
    
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.next());
        
        Conference[] arr = new Conference[N];
        
        for(int i=0; i<N; i++) {
            int sTime = Integer.parseInt(sc.next());
            int eTime = Integer.parseInt(sc.next());
            arr[i] = new Conference(sTime, eTime);
        }
        
        Arrays.sort(arr, new Comparator<Conference>() {
            @Override
            public int compare(Conference o1, Conference o2) {
                if(o1.end > o2.end) {
                    return 1;
                }else {
                    return -1;
                }
            }
        });

        
        int idx = 0;
        while(true) {
            
            // 종료시간이 같다면
            if(arr[idx].end == arr[idx+1].end) {
                // 어디까지 종료시간이 같은지 위치 파악!
                int tempEnd = idx+1;
                while(true) {
                    if(tempEnd >= N-1) break;
                    if(arr[tempEnd].end == arr[tempEnd+1].end) {
                        tempEnd++;
                    }else {
                        break;
                    }
                }
                
                // 동일한 종료시간을 지닌 회의에 한해서
                // 시작시간을 기준으로 2차 정렬
                for(int j=tempEnd; j>idx; j--) {
                    for(int k=idx; k < j; k++) {
                        if(arr[k].start > arr[k+1].start) {
                            Conference temp = arr[k];
                            arr[k] = arr[k+1];
                            arr[k+1] = temp;
                        }
                    }
                }
                
                // 해당 구간에서 정렬이 끝났으므로 탐색위치를 옮겨준다.
                idx = tempEnd;
            }
            
            idx++;    
            
            if(idx >= N-1) break;
        }
        
        
        
        int result = 0;
        int priorEndTime = 0;
        idx = 0;
        while(true){
            // 이전 회의 종료시간 후에 끝나는 회의라면 (종료시간자체가 동일해도 된다.)
            if(arr[idx].end >= priorEndTime) {
                // 이전 회의 종료시간 전에 시작하는 회의인지 확인한다.
                if(arr[idx].start >= priorEndTime) { // 최소 끝나자마자 시작하는 회의라면..
                    result++;
                    priorEndTime = arr[idx].end;
                }
            }
            
            idx++;    
            
            if(idx >= N) break;
        }
        // 정답 출력
        System.out.println(result);
    }
}