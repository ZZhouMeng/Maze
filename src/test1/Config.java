package test1;

public class Config {
	public static int SWIDTH=660;
	public static int SHEIGHT=660;
	public static int WIDTH=66;
	
	public static int LEFT=1;
	public static int RIGHT=2;
	public static int UP=3;
	public static int DOWN=4;
	
	public static int WALL=0;
	public static int ROAD=1;
	public static int ENTER=2;
	public static int EXIT=3;
	
	//0ǽ 1· 2��� 3����
	public static int[][] map=new int [][] {
		{0,0,0,0,0,0,0,0,0,0},//��0��
		{2,1,1,0,1,1,1,0,1,3},//��1��
		{0,0,1,0,1,1,1,0,1,0},//��2��
		{0,1,1,1,1,0,0,1,1,0},//��3��
		{0,1,0,0,0,1,1,1,1,0},//��4��
		{0,1,0,1,0,1,1,0,1,0},//��5��
		{0,1,0,1,1,1,0,1,1,0},//��6��
		{0,1,0,0,0,1,0,0,1,0},//��7��
		{0,1,1,1,1,1,1,1,1,0},//��8��
		{0,0,0,0,0,0,0,0,0,0}//��9��
	};
}
