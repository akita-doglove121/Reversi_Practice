package reversi;
public class ReversiBoard {
	private ReversiCell[][] board;
	public ReversiBoard() {
		board = new ReversiCell[8][8];
		initialaizeBoard();
	}
	public ReversiCell[][] getBoard(){
		return board;
	}
	
	//盤の初期化
	private void initialaizeBoard() {
		// ここの空行は削除で。あとはタブがスペースとタブになってるところあるからちょっと注意されたし
		for(int i = 0; i < 8; i++) { // 複数回使用するなら基本的に固定値は直接使用しない方がいいかも
			for(int j = 0; j < 8; j++) {
				board[i][j] = ReversiCell.EMPTY;
			}
		}
		//オセロの初期配置
		board[3][3] = ReversiCell.WHITE;
		board[3][4] = ReversiCell.BLACK;
		board[4][3] = ReversiCell.BLACK;
		board[4][4] = ReversiCell.WHITE;
	}
	// 盤の表示
    public void displayBoard() {
        System.out.println("    1 2 3 4 5 6 7 8"); // 列番号
        System.out.println("   -----------------");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " | "); // 行番号 (1始まり)
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case EMPTY -> System.out.print(". ");
                    case WHITE -> System.out.print("○ ");
                    case BLACK -> System.out.print("● ");
                }
            }
            System.out.println("| " + (i + 1)); // 行番号 (右側, 1始まり)
        }
        System.out.println("   -----------------");
        System.out.println("    1 2 3 4 5 6 7 8"); // 列番号 (下側)
    }
}
