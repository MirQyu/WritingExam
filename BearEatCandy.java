package bishi;

import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

public class BearEatCandy {
	
	private static class Bear implements Comparable<Bear> {
		int attack;
		int hunger;
		int index;
		
		public Bear(int a, int h, int i) {
			attack = a;
			hunger = h;
			index = i;
		}

		@Override
		public int compareTo(Bear o) {
			
			return o.attack - this.attack;
		}
		
		
	}
	
	//public static void 
	
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        
        //int[] candy = new int[m];
        TreeMap<Integer, Integer> candyMap = new TreeMap<>();
        
        for (int i = 0; i < m; i++) {
        	//candy[i] = in.nextInt();
        	int v = in.nextInt();
        	if (!candyMap.containsKey(v)) {
        		candyMap.put(v, 1);
        	}
        	else {
        		candyMap.put(v, candyMap.get(v) + 1);
        	}
        	
        }
        // small -> big
        //Arrays.sort(candy);
        
        
        
        Bear[] bear = new Bear[n];
        PriorityQueue<Bear> heap = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
        	int attack = in.nextInt();
        	int hunger = in.nextInt();
        	bear[i] = new Bear(attack, hunger, i);
        	heap.offer(bear[i]);
        }
        // big - > small
       // Arrays.sort(bear);
        
        
        
        while (!heap.isEmpty()) {
        	Bear cur = heap.poll();
        	//System.out.println("当前 i = " + i);
        	while (!candyMap.isEmpty()) {
        		Integer candy = candyMap.floorKey(cur.hunger);
        		
        		if (candy == null) {
        			break;
        		}
        		//System.out.println("目前的糖果是 ：" + candy);
        		int times = candyMap.get(candy);
        		if (times == 1) {
        			candyMap.remove(candy);
        		}
        		else {
        			candyMap.put(candy, times - 1);
        		}
        		cur.hunger -= candy;
        	}
        	
        }
        
        for (int i = 0; i < n; i++) {
        	System.out.println(bear[i].hunger);
        }
        
        
    }
}
