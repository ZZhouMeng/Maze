package test2;

import java.util.LinkedList;

public class MyQueue {
	LinkedList<Node> data = new LinkedList<Node>();

	public boolean isEmpty() {
		if (data.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int size() {
		return data.size();
	}

	public void push(Node e) {
		data.addLast(e);
	}

	public void pop() {
		data.removeFirst();
	}

	public Node front() {
		return data.getFirst();
	}

}
