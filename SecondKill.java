package bishi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SecondKill {
	
	HashMap<Integer, Goods> repos = new HashMap<>();
	ArrayList<KillGoods> activity = new ArrayList<>();
	HashMap<Integer, List<KillGoods>> start_map = new HashMap<>();
	HashMap<Integer, List<KillGoods>> end_map = new HashMap<>();
	int timeStamp = 0;
	
	public static class Goods {
		int id;
		int popu;
		int quantity;
		
		public Goods(int i, int p, int q) {
			id = i;
			popu = p;
			quantity = q;
		}
	}
	
	public static class KillGoods extends Goods {

		int start, end;
		boolean status;
		int lastSellTime;
		int activityId;
		
		public KillGoods(int actId, int id, int p, int q, int st, int ed) {
			super(id, p, q);
			activityId = actId;
			start = st;
			end = ed;
			status = false;
			lastSellTime = -1;
		}
		
		
	}
	
	public void drive() {
		Scanner input = new Scanner(System.in);
		int n, m;
		n = input.nextInt();
		m = input.nextInt();
		
		for (int i = 0; i < n; i++) {
			int id = input.nextInt();
			int pop = input.nextInt();
			int quan = input.nextInt();
			
			Goods gs = new Goods(id, pop, quan);
			repos.put(id, gs);
		}
		
		for (int i = 0; i < m; i++) {
			timeStamp = input.nextInt();
			endActivity();
			startActivity();
			String token = input.next();
			
			if (token.equals("add")) {
				int start = input.nextInt();
				int end = input.nextInt();
				int id = input.nextInt();
				int limitQuantity = input.nextInt();
				int res = addActivity(start, end, id, limitQuantity);
				System.out.println(res);
			}
			else if (token.equals("buy")) {
				int a_id = input.nextInt();
				int quantity = input.nextInt();
				int res = buyGoods(a_id, quantity);
				System.out.println(res);
			}
			else if (token.equals("list")) {
				List<Integer> res = getActivityList();
				for (int j = 0 ; j < res.size(); j++) {
					System.out.print(res.get(j));
					if (j != res.size() - 1) {
						System.out.print(" ");
					}
				}
				System.out.println();
			}
		}
		
	}
	
	
	
	private void endActivity() {
		// TODO Auto-generated method stub
		List<KillGoods> list = end_map.get(timeStamp);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).status = false;
			}
		}
	}



	private void startActivity() {
		// TODO Auto-generated method stub
		List<KillGoods> prepare = start_map.get(timeStamp);
		if (prepare != null) {
			for (int i = 0; i < prepare.size(); i++) {
				KillGoods kgs = prepare.get(i);
				kgs.status = true;
				
				List<KillGoods> arrange = end_map.get(kgs.end);
				if (arrange == null) {
					arrange = new ArrayList<>();
					end_map.put(kgs.end, arrange);
				}
				arrange.add(kgs);
			}
		}
	}



	public int addActivity(int startTime, int endTime, int goodsId, int limitQuantity) {
		
		Goods goods = repos.get(goodsId);
		if (goods == null || limitQuantity > goods.quantity) {
			return -1;
		}
		
		goods.quantity -= limitQuantity;
		
		int activity_id = activity.size();
		KillGoods kgs = new KillGoods(activity_id, 
				goodsId, goods.popu, limitQuantity, startTime, endTime);
		
		activity.add(kgs);
		
		List<KillGoods> list = start_map.get(startTime);
		if (list == null) {
			list = new ArrayList<KillGoods>();
			start_map.put(startTime, list);
		}
		list.add(kgs);
		
		return activity_id;
	}
	
	public int buyGoods(int acty_id, int quantity) {
		
		KillGoods kgs = activity.get(acty_id);
		if (!kgs.status || quantity > kgs.quantity) {
			return -1;
		}
		
		kgs.quantity -= quantity;
		kgs.lastSellTime = timeStamp;
		return 0;
	}
	
	public ArrayList<Integer> getActivityList() {
		ArrayList<Integer> res = new ArrayList<>();
		ArrayList<KillGoods> list1 = new ArrayList<>();
		ArrayList<KillGoods> list2 = new ArrayList<>();
		ArrayList<KillGoods> list3 = new ArrayList<>();
		
		for (int i = 0; i < activity.size(); i++) {
			KillGoods kgs = activity.get(i);
			if (kgs.status) {
				if (kgs.quantity != 0) {
					list1.add(kgs);
				}
				else {
					list2.add(kgs);
				}
			}
			else if(kgs.start > timeStamp){
				list3.add(kgs);
			}
		}
		
		Collections.sort(list1, new Comparator<KillGoods>() {

			@Override
			public int compare(KillGoods o1, KillGoods o2) {
				if (o1.popu != o2.popu) {
					return o2.popu - o1.popu;
				}
				
				return o1.id - o2.id;
			}
			
		});
		
		Collections.sort(list2, new Comparator<KillGoods>() {

			@Override
			public int compare(KillGoods o1, KillGoods o2) {
				if (o1.lastSellTime != o2.lastSellTime) {
					return o2.lastSellTime - o1.lastSellTime;
				}
				
				if (o1.popu != o2.popu) {
					return o2.popu - o1.popu;
				}
				
				return o1.id - o2.id;
			}
		});
		
		Collections.sort(list3, new Comparator<KillGoods>() {
			@Override
			public int compare(KillGoods o1, KillGoods o2) {
				if (o1.start != o2.start) {
					return o1.start - o2.start;
				}
				
				if (o1.popu != o2.popu) {
					return o2.popu - o1.popu;
				}
				
				return o1.id - o2.id;
			}
		});
		
		traverse(list1, res);
		traverse(list2, res);
		traverse(list3, res);
		
		return res;
	}
	
	public static void traverse(ArrayList<KillGoods> list, ArrayList<Integer> res) {
		for (int i = 0; i < list.size(); i++) {
			KillGoods kgs = list.get(i);
			res.add(kgs.activityId);
		}
	}
	
	public static void main(String[] args) {
		
		SecondKill secondKill = new SecondKill();
		secondKill.drive();
	}
}
