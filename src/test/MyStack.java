package test;

import java.util.ArrayList;

public class MyStack {
	ArrayList<Node> data=new ArrayList<>();
	
	public boolean isEmpty() {
		if(data.size()==0) {
			return true;
		}
		else {
			return false;
		}
	}
	public int size() {
		return data.size();
	}
	public void push(Node e) {
		data.add(e);
	}
	public void pop() {
		data.remove(data.size()-1);
	}

}
