package test2;


public class Config {

	public static int SWIDTH = 660;
	public static int SHEIGHT = 660;
	public static int WIDTH = 66;

	public static int LEFT = 2;
	public static int RIGHT = 3;
	public static int UP = 1;
	public static int DOWN = 0;

	public static int WALL = 0;
	public static int ROAD = 1;
	public static int ENTER = 2;
	public static int EXIT = 3;

	public static int[][] map;
	// 0墙 1路 2入口 3出口
	public static int[][] map3 = new int[][] {
			{ 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 }, // 第0行
			{ 0, 1, 1, 1, 0, 0, 1, 1, 1, 0 }, // 第1行
			{ 0, 1, 0, 0, 0, 0, 1, 0, 1, 0 }, // 第2行
			{ 0, 1, 1, 1, 1, 1, 1, 0, 1, 0 }, // 第3行
			{ 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 }, // 第4行
			{ 0, 1, 1, 1, 0, 0, 1, 0, 1, 0 }, // 第5行
			{ 0, 0, 0, 1, 0, 1, 1, 1, 1, 0 }, // 第6行
			{ 0, 1, 1, 1, 0, 0, 0, 0, 1, 0 }, // 第7行
			{ 0, 1, 0, 1, 1, 1, 1, 1, 1, 0 }, // 第8行
			{ 0, 2, 0, 0, 0, 0, 0, 0, 0, 0 }, // 第9行

	};

	// 0墙 1路 2入口 3出口
	public static int[][] map2 = new int[][] { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 第0行
			{ 2, 1, 1, 0, 1, 1, 1, 0, 1, 3 }, // 第1行
			{ 0, 0, 1, 0, 1, 1, 1, 0, 1, 0 }, // 第2行
			{ 0, 1, 1, 1, 1, 0, 0, 1, 1, 0 }, // 第3行
			{ 0, 1, 0, 0, 0, 1, 1, 1, 1, 0 }, // 第4行
			{ 0, 1, 0, 1, 0, 1, 1, 0, 1, 0 }, // 第5行
			{ 0, 1, 0, 1, 1, 1, 0, 1, 1, 0 }, // 第6行
			{ 0, 1, 0, 0, 0, 1, 0, 0, 1, 0 }, // 第7行
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, // 第8行
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }// 第9行
	};
	// 0墙 1路 2入口 3出口
	public static int[][] map1= new int[][] { 
			{ 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 }, // 第0行
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, // 第1行
			{ 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 }, // 第2行
			{ 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 }, // 第3行
			{ 0, 1, 1, 1, 0, 0, 0, 0, 1, 0 }, // 第4行
			{ 0, 1, 0, 0, 0, 1, 1, 1, 1, 0 }, // 第5行
			{ 0, 1, 1, 1, 1, 1, 0, 0, 1, 0 }, // 第6行
			{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 }, // 第7行
			{ 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 }, // 第8行
			{ 0, 2, 0, 0, 0, 0, 0, 0, 0, 0 } // 第9行
	};
	public static void mapSelect(int n) {
		if(n==1) {
			map=map1;
		}else if(n==2) {
			map=map2;
		}else if(n==3) {
			map=map3;
		}
	}
}
