package test2;

import java.util.Stack;
import javafx.scene.layout.VBox;


public class Allpath extends VBox {
	public static String list;
	private static int count;// ����·������
	
	private static MyStack s = new MyStack();

	private static Node startPos = new Node();// ���
	private static Node endPos = new Node();
	
	Allpath(){
		list = "All path as follow:\n";
		count=1;
		//�ҳ��ڣ����
		for (int i = 0; i < Config.map.length; i++) {
			for (int j = 0; j < Config.map[i].length; j++) {
				// ������
				if (Config.map[i][j] == Config.ENTER) {
					startPos.row = i;
					startPos.col = j;

				}
				// ����յ�
				if (Config.map[i][j] == Config.EXIT) {
					endPos.row = i;
					endPos.col = j;
				}
			}
		}
		allmaze(Config.map,startPos.row,startPos.col);
	}
	public static void print() {

		int i;

		list = list + "��" + count + "��·��Ϊ:\n";
		for (i = 0; i < s.size(); i++) {
			list = list + "(" + s.data.get(i).row + "," + s.data.get(i).col+ "," + s.data.get(i).direct + ")";
			if ((i + 1) % 10 == 0)
				list = list + "\n";
		}
		list = list + "\n";
		count++;
		
	}

	static int offset[][] = { 
			{ 1, 0 }, // ����
			{ -1, 0 }, // ����
			{ 0, -1 }, // ����
			{ 0, 1 }, // ����

	};

	public static void allmaze(int[][] maze, int row, int col) {
		Node pos = new Node();
		int old, nextR, nextC;

		if (row == endPos.row && col == endPos.col) {
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

				allmaze(Config.map, nextR, nextC);

				if (!s.isEmpty()) {
					s.pop();
				}
			}
			maze[row][col] = old;
		}

	}
}
