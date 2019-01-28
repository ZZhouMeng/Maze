package test2;

public class Node {

	int row, col, direct;

	Node() {
		this(-1, -1, -1);
	}

	Node(int row, int col, int direct) {
		this.row = row;
		this.col = col;
		this.direct = direct;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getDirect() {
		return direct;
	}

}
