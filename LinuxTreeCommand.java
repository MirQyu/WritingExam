package bishi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LinuxTreeCommand {
	
	public static class Node implements Comparable<Node> {
		String name;
		ArrayList<Node> child;
		int lineNum;
		public Node(String n, int num) {
			name = n;
			lineNum = num;
			child = new ArrayList<>();
		}
		@Override
		public int compareTo(Node o) {
			
			return name.compareTo(o.name);
		}
		
		
	}
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        Node[] node = new Node[N];
        
        int start = -1; // 根节点的索引编号
        
        for (int i = 0; i < N; i++) {
        	String s = in.next();
        	int p_num = in.nextInt();
        	
        	node[i] = new Node(s, i);
        	if (p_num != -1) {
        		node[p_num].child.add(node[i]);
        	}
        	else {
        		start = i;
        	}
        }
        //
        System.out.println(node[start].name);
        ArrayList<Boolean> space = new ArrayList<>();
        print(node[start], space);
        
    }
	
	// 遍历当前节点的孩子， 深度搜索
	public static void print(Node node, ArrayList<Boolean> space) {
		ArrayList<Node> child = node.child;
		Collections.sort(child);
		for (int i = 0; i < child.size(); i++) {
			
			for (int j = 0; j < space.size(); j++) {
				if (space.get(j)) {
					System.out.print("    ");
				}
				else {
					System.out.print("|   ");
				}
			}
			
			if (i != child.size() - 1) {
				System.out.print("|-- ");
				space.add(false);
			}
			else {
				
				System.out.print("`-- ");
				space.add(true);
			}
			System.out.println(child.get(i).name);
			
			print(child.get(i), space);
			space.remove(space.size() - 1);
		}
	}
}
