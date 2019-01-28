package test2;

import java.util.Queue;
import javafx.event.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import test2.Config;
import test2.Node;

public class GamePane extends Canvas {
	private Image wall;

	private static MyQueue q = new MyQueue();// ���������·��
	private static MyStack s = new MyStack();// ��¼�˶��켣

	private Image left[] = new Image[4];
	private Image right[] = new Image[4];
	private Image up[] = new Image[4];
	private Image down[] = new Image[4];

	private int direct = 0; // С�˵��ߵķ���
	private int index = 0;// 1 2 3 4 ��������
	private int row = 0;// С�����ڵ���
	private int col = 0;// С�����ڵ���

	private boolean flag = true; // �����Զ���Ϸ���߳�

	private GraphicsContext gc = this.getGraphicsContext2D();

	static int offset[][] = { { 1, 0 }, // ����
			{ -1, 0 }, // ����
			{ 0, -1 }, // ����
			{ 0, 1 }, // ����

	};

	private static int ROWS;
	private static int COLS;

	static Node[][] path;// ·���洢

	private static Node startPos = new Node();// ���
	private static Node endPos = new Node();

	public GamePane() {
		this.startGame(0,1);
	}

	public void startGame(int game,int n) {
		Config.mapSelect(n);
		ROWS = Config.map.length;
		COLS = Config.map[0].length;
		path = new Node[ROWS][COLS];
		
		this.setWidth(Config.SWIDTH);
		this.setHeight(Config.SHEIGHT);
		
		gc.clearRect(0, 0, Config.SHEIGHT, Config.SWIDTH);
		
		this.fillBlock();

		this.initImage();
		this.initMap();

		if (game == 0) {
			s = new MyStack();// ֹͣ�Զ���Ϸ
			this.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent e) {
					switch (e.getCode()) {
					case LEFT:
						direct = Config.LEFT;
						break;
					case RIGHT:
						direct = Config.RIGHT;
						break;
					case UP:
						direct = Config.UP;
						break;
					case DOWN:
						direct = Config.DOWN;
						break;
					}
					playGame();
				}
			});
		} else if (game == 1) {
			this.solvemaze();// ����·��
			Runnable auto = new autoPlay();
			Thread thread1 = new Thread(auto);
			thread1.start();
		}

	}

	class autoPlay implements Runnable {
		@Override
		public void run() {
			try {
				for (int i = s.size() - 1; i >= 0; i--) {
					if (s.isEmpty()) {
						break;
					}
					direct = s.data.get(i).direct;
					playGame();
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// ��ʼ����Ϸ�е�ͼƬ
	private void initImage() {
		wall = new Image("res/wall.png");
		for (int i = 0; i < 4; i++) {
			left[i] = new Image("res/left" + i + ".png");
			right[i] = new Image("res/right" + i + ".png");
			up[i] = new Image("res/up" + i + ".png");
			down[i] = new Image("res/down" + i + ".png");
		}
	}

	// ��ʼ����ͼ
	private void initMap() {
		for (int i = 0; i < Config.map.length; i++) {
			for (int j = 0; j < Config.map[i].length; j++) {
				// ������
				if (Config.map[i][j] == Config.ENTER) {
					startPos.row = i;
					startPos.col = j;

					col = startPos.col;
					row = startPos.row;
				}
				// ����յ�
				if (Config.map[i][j] == Config.EXIT) {
					endPos.row = i;
					endPos.col = j;

					gc.setFill(Color.RED);
					gc.fillRect(j * Config.WIDTH, i * Config.WIDTH, Config.WIDTH, Config.WIDTH);
					gc.setFill(Color.YELLOW);
					gc.fillOval(j * Config.WIDTH + 15, i * Config.WIDTH + 20, 10, 10);
					gc.fillOval(j * Config.WIDTH + 35, i * Config.WIDTH + 20, 10, 10);
				}
				if (Config.map[i][j] == Config.WALL) {
					gc.drawImage(wall, Config.WIDTH * j, Config.WIDTH * i, Config.WIDTH, Config.WIDTH);
				}
			}
		}
		initEntry();
	}

	// ��С�����
	private void initEntry() {
		int n = Config.map.length;
		if (Config.map[row][col] == Config.ENTER) {
			if (col - 1 > 0 && Config.map[row][col - 1] == Config.ROAD) {
				direct = Config.LEFT;
				// С��Ӧ������
				gc.drawImage(left[0], col * Config.WIDTH, row * Config.WIDTH);
			} else if (col + 1 < n && Config.map[row][col + 1] == Config.ROAD) {
				direct = Config.RIGHT;
				gc.drawImage(right[0], col * Config.WIDTH, row * Config.WIDTH);
			} else if (row - 1 > 0 && Config.map[row - 1][col] == Config.ROAD) {
				direct = Config.UP;
				gc.drawImage(up[0], col * Config.WIDTH, row * Config.WIDTH);
			} else if (row + 1 < n && Config.map[row + 1][col] == Config.ROAD) {
				direct = Config.DOWN;
				gc.drawImage(down[0], col * Config.WIDTH, row * Config.WIDTH);
			}
		}

	}

	private static void solvemaze() {// Dijkstra�����·��
		Node rest = new Node();
		int[][] visited = new int[ROWS][COLS];
		// �����ʼ��
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				visited[i][j] = 0;
				path[i][j] = rest;
			}
		}

		visited[startPos.row][startPos.col] = 1;
		path[startPos.row][startPos.col] = rest;
		startPos.direct = 0;
		q.push(startPos);

		int rowNum, colNum;

		while (!q.isEmpty()) {
			int nextrow, nextcol;
			Node pos = q.front();
			q.pop();
			rowNum = pos.row;
			colNum = pos.col;

			for (int i = 0; i < 4; i++) {

				nextrow = rowNum + offset[i][0];
				nextcol = colNum + offset[i][1];

				if (nextrow >= 0 && nextrow < ROWS && nextcol >= 0 && nextcol < COLS && visited[nextrow][nextcol] == 0
						&& Config.map[nextrow][nextcol] >= 1) {
					Node data = new Node();
					data.col = nextcol;
					data.row = nextrow;
					data.direct = i;
					q.push(data);
					path[nextrow][nextcol] = new Node(rowNum, colNum, i);// ��¼ǰ��
					visited[nextrow][nextcol] = 1;// ���Ϊ�ѷ���
				}
			}

		}
		getmaze(new Node(endPos.row, endPos.col, path[endPos.row][endPos.col].direct));// ���յ㿪ʼ������

	}

	// ������������·���ó��˶��켣
	// ���յ㿪ʼ���ŵ���
	private static void getmaze(Node p) {

		int lastrow = path[p.getRow()][p.getCol()].getRow();
		int lastcol = path[p.getRow()][p.getCol()].getCol();
		int lastdirect = path[p.getRow()][p.getCol()].getDirect();

		Node ps = new Node(lastrow, lastcol, lastdirect);

		s.push(ps);

		if (Config.map[ps.row][ps.col] == 2) {
			return;
		}
		getmaze(ps);

	}

	// ��Ϸ����
	public void playGame() {
		if (index == 3) {
			index = 0;
		}

		if (direct == Config.LEFT) {
			// ����
			if (col - 1 > 0 && Config.map[row][col - 1] == Config.ROAD) {
				gc.drawImage(left[index], (col - 1) * Config.WIDTH, row * Config.WIDTH);
				fillBlock();
				index++;
				col--;
			}
		} else if (direct == Config.RIGHT) {
			if (col + 1 < 10 && Config.map[row][col + 1] == Config.ROAD) {
				gc.drawImage(right[index], (col + 1) * Config.WIDTH, row * Config.WIDTH);
				fillBlock();
				index++;
				col++;
			}
		} else if (direct == Config.UP) {
			if (row - 1 > 0 && Config.map[row - 1][col] == Config.ROAD) {
				gc.drawImage(up[index], col * Config.WIDTH, (row - 1) * Config.WIDTH);
				fillBlock();
				index++;
				row--;
			}
		} else if (direct == Config.DOWN) {
			if (row + 1 < 10 && Config.map[row + 1][col] == Config.ROAD) {
				gc.drawImage(down[index], col * Config.WIDTH, (row + 1) * Config.WIDTH);
				fillBlock();
				index++;
				row++;
			}
		}

	}

	private void fillBlock() {
		//gc.setFill(Color.WHITE);
		gc.clearRect(col * Config.WIDTH, row * Config.WIDTH, Config.WIDTH, Config.WIDTH);
	}

}
