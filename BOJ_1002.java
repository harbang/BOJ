import java.util.Scanner;

class Circle{
    private int x;
    private int y;
    private int r;
    
    public Circle(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }    
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int T = Integer.parseInt(sc.next());
        
        Circle[] circles1 = new Circle[T];
        Circle[] circles2 = new Circle[T];
        
        for(int i=0; i<T; i++) {
            int x1 = Integer.parseInt(sc.next());
            int y1 = Integer.parseInt(sc.next());
            int r1 = Integer.parseInt(sc.next());
            circles1[i] = new Circle(x1, y1);
            circles1[i].setR(r1);
            
            int x2 = Integer.parseInt(sc.next());
            int y2 = Integer.parseInt(sc.next());
            int r2 = Integer.parseInt(sc.next());
            circles2[i] = new Circle(x2, y2);
            circles2[i].setR(r2);
        }
        
        for(int i=0; i<T; i++) {
            
            if(circles2[i].getX() == circles1[i].getX() &&
                    circles2[i].getY() == circles1[i].getY() &&
                    circles2[i].getR() == circles1[i].getR()) {
                    
                    // 두 원이 완전 일치(원의 중심이 동일하며 반지름도 동일)
                    System.out.println("-1");
                
            }
            else {
                int dx = circles2[i].getX()-circles1[i].getX();
                int dy = circles2[i].getY()-circles1[i].getY();
                
                double d = Math.sqrt(dx*dx + dy*dy);
                double sumRadius = circles2[i].getR() + circles1[i].getR();
                
                if(d == sumRadius) { // 두 원이 한 군데에서 교차하는 경우
                    System.out.println("1");
                }else if(d < sumRadius){
                    if(circles2[i].getR() > d + circles1[i].getR()) {
                        // 원1이 원2에 내부에 위치한 경우
                        System.out.println("0");
                    }else if(circles1[i].getR() > d + circles2[i].getR()) {
                        // 원2가 원1에 내부에 위치한 경우
                        System.out.println("0");
                    }else if(circles2[i].getR() == d + circles1[i].getR()) {
                        // 원1이 원2의 내부에서 내접하고 있는 경우
                        System.out.println("1");
                    }else if(circles1[i].getR() == d + circles2[i].getR()) {
                        // 원2가 원1의 내부에서 내접하고 있는 경우
                        System.out.println("1");
                    }else { 
                        // 두 원이 두 군데에서 교차하는 경우
                        System.out.println("2");                        
                    }
                }else { 
                    // 두 원이 멀리 떨어져 있는 경우
                    System.out.println("0");
                }
            }
        }
    }
}