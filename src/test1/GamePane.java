package test1;

import javafx.event.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GamePane extends Canvas {
	private Image wall;

	private Image left[] = new Image[4];
	private Image right[] = new Image[4];
	private Image up[] = new Image[4];
	private Image down[] = new Image[4];

	private int direct = 0; // С�˵��ߵķ���
	private int index = 0;// 1 2 3 4 ��������
	private int row = 0;// С�����ڵ���
	private int col = 0;// С�����ڵ���

	private GraphicsContext gc = this.getGraphicsContext2D();

	public GamePane() {
		this.setWidth(Config.SWIDTH);
		this.setHeight(Config.SHEIGHT);

		this.initImage();
		this.initMap();

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
				// ����������Ϊ��row��col��
				if (Config.map[i][j] == Config.ENTER) {
					row = i;
					col = j;
				}
				if (Config.map[i][j] == Config.EXIT) {
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
	
	// ��Ϸ����
	private void playGame() {
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
		gc.setFill(Color.WHITE);
		gc.fillRect(col * Config.WIDTH, row * Config.WIDTH, Config.WIDTH, Config.WIDTH);

	}

}
