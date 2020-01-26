// [BOJ] 백준 8979 올림픽
// 문제: https://www.acmicpc.net/problem/8979
// 풀이: https://octorbirth.tistory.com/106

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int N = Integer.parseInt(sc.next());
		int K = Integer.parseInt(sc.next());
		
		PriorityQueue<Country> pq = new PriorityQueue<>();
		for(int i=1; i<=N; i++) {
			Country country = new Country(
					Integer.parseInt(sc.next()), // 번호
					Integer.parseInt(sc.next()), // 금
					Integer.parseInt(sc.next()), // 은
					Integer.parseInt(sc.next())	 // 동
				);
			pq.offer(country);
		}
		
		// 1위 국가를 먼저 랭킹처리(시작 순위)
		Country preCountry = pq.poll();
		preCountry.rank = 1;
		if(K == preCountry.id) {
			System.out.println(preCountry.rank);
			return;
		}
		
		int rankCnt = 2;
		while(!pq.isEmpty()) {
			Country country = pq.poll();
			// 등수가 같은지 확인
			if(preCountry.gold == country.gold
					&& preCountry.silver == country.silver
					&& preCountry.bronze == country.bronze) {
				country.rank = preCountry.rank;
			}
			else {
				country.rank = rankCnt;
			}
			
			if(country.id == K) {
				System.out.println(country.rank);
				return;
			}
			preCountry = country;
			rankCnt++;
		}
	}
}

class Country implements Comparable<Country> {
	int id, gold, silver, bronze, rank; 
	
	public Country(int id, int gold, int silver, int bronze) {
		this.id = id;
		this.gold = gold;
		this.silver = silver;
		this.bronze = bronze;
	}
	
	@Override
	public int compareTo(Country target) {
		// 높은 숫자가 위로 오도록
		if (this.gold > target.gold)
			return -1;
		else if (this.gold < target.gold)
			return 1;
		else {
			// 은메달 비교
			if(this.silver > target.silver) {
				return -1;
			}
			else if(this.silver < target.silver) {
				return 1;
			}
			else {
				// 동메달 비교
				if(this.bronze > target.bronze) {
					return -1;
				}
				else if(this.bronze < target.bronze) {
					return 1;
				}
				return 1;
			}
		}
	}
	
	@Override
	public String toString() {
		return "[" + id + "] (" + gold + ", " + silver + ", " + bronze + ")";
	}
}