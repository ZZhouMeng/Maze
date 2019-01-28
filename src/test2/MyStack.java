package test2;

import java.util.ArrayList;


public class MyStack {
	ArrayList<Node> data=new ArrayList<Node>();
	
	public boolean isEmpty() {
		if (data.isEmpty())
			return true;
		else
			return false;
	}
	
	public int size() {
		return data.size();
	}
	
	public void push(Node pos) {
		data.add(pos);
	}
	
	public void pop() {
		data.remove(data.size() - 1);
	}
}
