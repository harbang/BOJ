// [BOJ] 백준 1181 단어 정렬

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        
        // Collection을 이용하여 정렬 처리할 예정
        List<String> list = new ArrayList<>();
        for(int i=0; i < n; i++) {
            list.add(sc.next());
        }
        
        // 문자열 길이에 대해 1차 정렬
        // 문자열 길이가 동일할 때, 알파벳 순서대로 2차 정렬
        Collections.sort(list, new ChainedComparator(
                new LengthComparator(),
                new AlphabeticalComparator()
        ));
        
        // 중복 제거
        // n >= 1이므로 일단 출력
        System.out.println(list.get(0));
        for(int i=1; i<list.size(); i++) {
            // 이전 문자열과 동일하지 않다면
            if(!list.get(i-1).equals(list.get(i))) {
                System.out.println(list.get(i));
            }
        }
    }
}

class LengthComparator implements Comparator<String>{   
    @Override
    public int compare(String o1, String o2) {
        
        // 글자의 길이가 짧은게 앞쪽으로
        return o1.length() - o2.length();
    }
}

class AlphabeticalComparator implements Comparator<String>{   
    @Override
    public int compare(String o1, String o2) {
        
        // 알파벳 순으로 abc
        return o1.toString().compareTo(o2.toString());
    }
}

class ChainedComparator implements Comparator<String> {
    
    private List<Comparator<String>> listComparators;
    @SafeVarargs
    public ChainedComparator(Comparator<String>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }
    @Override
    public int compare(String o1, String o2) {
        for (Comparator<String> comparator : listComparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}