package test;

public class Main {
	private static MyStack s = new MyStack();

	private static int maze[][] = { 
			{ 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 }, // ��0��
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, // ��1��
			{ 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 }, // ��2��
			{ 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 }, // ��3��
			{ 0, 1, 1, 1, 0, 0, 0, 0, 1, 0 }, // ��4��
			{ 0, 1, 0, 0, 0, 1, 1, 1, 1, 0 }, // ��5��
			{ 0, 1, 1, 1, 1, 1, 0, 0, 1, 0 }, // ��6��
			{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 }, // ��7��
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 }, // ��8��
			{ 0, 2, 0, 0, 0, 0, 0, 0, 0, 0 } // ��9��
	};
	
	static int offset[][] = { 
			{ 0, 1 }, // ����
			{ 1, 0 }, // ����
			{ 0, -1 }, // ����
			{ -1, 0 }// ����
	};
	
	static int count = 1;

	public static void main(String[] args) {
		System.out.println("All path as follow:");
		solvemaze(maze, 9, 1);

	}

	static void print() {
		int i;
		System.out.println("��" + count + "��·��Ϊ��");
		for (i = 0; i < s.size(); i++) {
			System.out.printf("(%d,%d,%d)", s.data.get(i).row, s.data.get(i).col, s.data.get(i).direct);
			
			if ((i + 1) % 5 == 0) {
				System.out.println();
			}
		}
		if ((i + 1) % 5 != 0) {
			System.out.println();
		}
		System.out.println();
		count++;
	}

	private static void solvemaze(int[][] maze, int row, int col) {
		Node pos = new Node();
		int old, nextR, nextC;

		if (row == 0 && col == 1) {
			print();
			return;
		}
		for (int i = 0; i < 4; i++) {
			pos.row = row;
			pos.col = col;
			pos.direct = i;

			old = maze[row][col];
			maze[row][col] = 0;

			nextR = row + offset[i][0];
			nextC = col + offset[i][1];

			if (nextR >= 0 && nextR < 10 && nextC >= 0 && nextC < 10 && maze[nextR][nextC] >= 1) {
				s.push(pos);

				solvemaze(maze, nextR, nextC);

				if (!s.isEmpty()) {
					s.pop();
				}
			}
			maze[row][col] = old;
		}

	}

}
