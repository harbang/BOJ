// [BOJ] 백준 14891 톱니바퀴
// https://www.acmicpc.net/problem/14891

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);        
        
        Machine machine = new Machine();
        
        // 4개의 톱니바퀴에 대한 정보를 입력받는다.
        for(int i=0; i<4; i++) {
            String str = sc.next();
            
            Gear gear = new Gear();
            for(int j=0; j<str.length(); j++) {
                int teeth = Integer.parseInt(str.substring(j, j+1));
                gear.saw.add(teeth);
            }
            machine.gears.add(gear);
        }
        
        int rotateCnt = Integer.parseInt(sc.next());
        
        // 회전하기
        while(rotateCnt-- > 0) {
            // 회전 톱니바퀴 번호 (리스트는 시작인덱스가 '0'이므로 입력받은 것에서-1을 해줘야 한다.)
            int idx = Integer.parseInt(sc.next()) - 1;
            // 회전 방향(시계 or 반시계 방향)
            int direction = Integer.parseInt(sc.next());
            
            machine.run(idx,direction);
        }
        
        int answer = 0;
        for(int i=0; i<4; i++) {
            
            Gear gear = machine.gears.get(i);
            int target = gear.saw.get(0);
            if(target == 0) continue;
            
            // 2의 i승 형태를 이용
            answer += Math.pow(2, i);
        }
        System.out.println(answer);
    }
}

class Machine{
    // 시계 & 반시계 방향
    static final int FORWARD = 1;
    static final int RERVERSE = -1;
    List<Gear> gears;
    
    public Machine() {
        gears = new ArrayList<>();
    }

    public void run(int idx, int direction) {
        Gear gear = gears.get(idx);
        leftChain(idx, direction);
        rightChain(idx, direction);
        gear.rotate(direction);
    }

    

    private void leftChain(int idx, int direction) {
        // 왼쪽에 영향을 미칠 톱니바퀴가 없으면(제일 왼쪽 톱니바퀴라면) stop
        if(idx <= 0) return;
        
        // 맞닿은 극을 비교한다.
        // 극이 같다면 더 이상의 영향은 없다.
        if(gears.get(idx).saw.get(6) == gears.get(idx-1).saw.get(2)) return;
        // 극이 다르다면 현재 톱니바퀴에 대한 진행방향에 따른 처리를 해줘야 한다.
        else {
            // 현재 바퀴가 시계방향이면 왼쪽 바퀴는 반시계 방향이다.
            if(direction == FORWARD) {
                leftChain(idx-1, RERVERSE);
                // 왼쪽 바퀴도 회전하기 전에 그 다음 왼쪽 바퀴에 영향을 주는지 처리한 뒤에 회전을 한다.
                gears.get(idx-1).rotate(RERVERSE);
            }
            // 현재 바퀴가 반시계방향이면 왼쪽 바퀴는 시계 방향이다.
            else if(direction == RERVERSE) {
                leftChain(idx-1, FORWARD);
                // 왼쪽 바퀴도 회전하기 전에 그 다음 왼쪽 바퀴에 영향을 주는지 처리한 뒤에 회전을 한다.
                gears.get(idx-1).rotate(FORWARD);
            }
        }
    }
    
    private void rightChain(int idx, int direction) {
        // 오른쪽에 영향을 미칠 톱니바퀴가 없으면(제일 오른쪽 톱니바퀴라면) stop
        if(idx >= gears.size()-1) return;
        // 맞닿은 극을 비교한다.
        // 극이 같다면 더 이상의 영향은 없다.
        if(gears.get(idx).saw.get(2) == gears.get(idx+1).saw.get(6)) return;
        // 극이 다르다면 현재 톱니바퀴에 대한 진행방향에 따른 처리를 해줘야 한다.
        else {
            // 현재 바퀴가 시계방향이면 오른쪽 바퀴는 반시계 방향이다.
            if(direction == FORWARD) {
                rightChain(idx+1, RERVERSE);
                // 오른쪽 바퀴도 회전하기 전에 그 다음 오른쪽 바퀴에 영향을 주는지 처리한 뒤에 회전을 한다.
                gears.get(idx+1).rotate(RERVERSE);
            }
            // 현재 바퀴가 반시계방향이면 오른쪽 바퀴는 시계 방향이다.
            else if(direction == RERVERSE) {
                rightChain(idx+1, FORWARD);
                // 오른쪽 바퀴도 회전하기 전에 그 다음 오른쪽 바퀴에 영향을 주는지 처리한 뒤에 회전을 한다.
                gears.get(idx+1).rotate(FORWARD);
            }
        }
    }
}

class Gear{
    // 시계 & 반시계 방향
    static final int FORWARD = 1;
    static final int RERVERSE = -1;
    
    List<Integer> saw;
    public Gear() {
        saw = new ArrayList<>();
    }
    
    
    public void rotate(int direction) {
        // 시계 방향 회전인 경우
        if(direction == FORWARD) {
            // 제일 뒤에 있는 원소가 제일 앞으로 와서 12시 방향의 자리가 된다.
            // 나머지 원소는 자동적으로 밀려난다.
            saw.add(0, saw.remove(saw.size()-1));
        }
        // 반시계 방향 회전인 경우
        else if(direction == RERVERSE) {
            // 제일 앞에 있던 원소를 제일 뒤로 보낸다.
            saw.add(saw.remove(0));
        }
    }
}