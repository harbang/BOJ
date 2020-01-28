// BOJ_1786 찾기
// 문제: https://www.acmicpc.net/problem/1786
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		char[] text = br.readLine().toCharArray();
		char[] pattern = br.readLine().toCharArray();
		
		List<Integer> answer = KMP(text, pattern);
		
		bw.write(answer.size() + "\n");
		for(int i=0; i<answer.size(); i++) {
			bw.write(answer.get(i) + "\n");
		}

		bw.flush(); bw.close();
	}

	private static List<Integer> KMP(char[] text, char[] pattern) {
		int[] table = makeTable(pattern);
		int textSize = text.length;
		int patternSize = pattern.length;
		
		// 매칭되는 인덱스 위치
		List<Integer> matchingIndexArr = new ArrayList<Integer>(); 
		
		int j = 0;
		for(int i=0; i<textSize; i++) {
			while(j > 0 && text[i] != pattern[j]) {
				j = table[j-1];
			}
			if(text[i] == pattern[j]) {
				// 패턴 매칭 성공
				if(j == patternSize - 1) {
					// 매칭되는 인덱스 저장
					matchingIndexArr.add(i - patternSize + 2);
					j = table[j];
				}
				else {
					j++;
				}
			}
		}
		return matchingIndexArr;
	}

	private static int[] makeTable(char[] pattern) {
		int patternSize = pattern.length;
		int[] table = new int[patternSize];
		
		int j = 0;
		for(int i=1; i<patternSize; i++) {
			while(j>0 && pattern[i] != pattern[j]) {
				j = table[j-1];
			}
			if(pattern[i] == pattern[j]) {
				table[i] = ++j;
			}
		}
		return table;
	}
}