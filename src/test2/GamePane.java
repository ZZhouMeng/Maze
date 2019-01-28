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

	private static MyQueue q = new MyQueue();// 用于求最短路径
	private static MyStack s = new MyStack();// 记录运动轨迹

	private Image left[] = new Image[4];
	private Image right[] = new Image[4];
	private Image up[] = new Image[4];
	private Image down[] = new Image[4];

	private int direct = 0; // 小人的走的方向
	private int index = 0;// 1 2 3 4 动画索引
	private int row = 0;// 小人所在的行
	private int col = 0;// 小人所在的列

	private boolean flag = true; // 控制自动游戏的线程

	private GraphicsContext gc = this.getGraphicsContext2D();

	static int offset[][] = { { 1, 0 }, // 向下
			{ -1, 0 }, // 向上
			{ 0, -1 }, // 向左
			{ 0, 1 }, // 向右

	};

	private static int ROWS;
	private static int COLS;

	static Node[][] path;// 路径存储

	private static Node startPos = new Node();// 起点
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
			s = new MyStack();// 停止自动游戏
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
			this.solvemaze();// 计算路径
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

	// 初始化游戏中的图片
	private void initImage() {
		wall = new Image("res/wall.png");
		for (int i = 0; i < 4; i++) {
			left[i] = new Image("res/left" + i + ".png");
			right[i] = new Image("res/right" + i + ".png");
			up[i] = new Image("res/up" + i + ".png");
			down[i] = new Image("res/down" + i + ".png");
		}
	}

	// 初始化地图
	private void initMap() {
		for (int i = 0; i < Config.map.length; i++) {
			for (int j = 0; j < Config.map[i].length; j++) {
				// 标记入口
				if (Config.map[i][j] == Config.ENTER) {
					startPos.row = i;
					startPos.col = j;

					col = startPos.col;
					row = startPos.row;
				}
				// 标记终点
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

	// 画小人入口
	private void initEntry() {
		int n = Config.map.length;
		if (Config.map[row][col] == Config.ENTER) {
			if (col - 1 > 0 && Config.map[row][col - 1] == Config.ROAD) {
				direct = Config.LEFT;
				// 小人应该向左
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

	private static void solvemaze() {// Dijkstra求最短路径
		Node rest = new Node();
		int[][] visited = new int[ROWS][COLS];
		// 数组初始化
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
					path[nextrow][nextcol] = new Node(rowNum, colNum, i);// 记录前驱
					visited[nextrow][nextcol] = 1;// 标记为已访问
				}
			}

		}
		getmaze(new Node(endPos.row, endPos.col, path[endPos.row][endPos.col].direct));// 从终点开始往回找

	}

	// 根据所求的最短路径得出运动轨迹
	// 从终点开始倒着递推
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

	// 游戏动作
	public void playGame() {
		if (index == 3) {
			index = 0;
		}

		if (direct == Config.LEFT) {
			// 向左
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
