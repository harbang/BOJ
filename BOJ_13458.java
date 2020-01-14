// https://www.acmicpc.net/problem/13458
// BOJ 13458 시험 감독

import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        
        BigInteger[] arrNum = new BigInteger[T];
        for(int i=0; i<T; i++) {
            arrNum[i] = sc.nextBigInteger();
        }
        BigInteger mainNum = sc.nextBigInteger();
        BigInteger subNum = sc.nextBigInteger();
        
        BigInteger result = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger zero = new BigInteger("0");
        

        int idx = 0;
        while(true) {
            arrNum[idx] = arrNum[idx].subtract(mainNum);
            result = result.add(one);

            if(arrNum[idx].compareTo(zero) >= 1) {
                if(arrNum[idx].remainder(subNum).equals(zero)) {
                    result = result.add((arrNum[idx].divide(subNum)));
                }
                else {
                    result = result.add(  (arrNum[idx].divide(subNum)).add(one)  );
                }
                arrNum[idx] = zero;
            }

            if(arrNum[idx].compareTo(zero) <= 0) {
                idx++;
                if(idx == arrNum.length)
                    break;
            }
            
        }

        System.out.println(result);
    }
}