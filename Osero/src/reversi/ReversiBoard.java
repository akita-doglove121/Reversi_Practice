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
		
		for(int stonesInLine_i = 0; stonesInLine_i < 8; stonesInLine_i++) {
			for(int stonesInLine_j = 0; stonesInLine_j < 8; stonesInLine_j++) {
				board[stonesInLine_i][stonesInLine_j] = ReversiCell.EMPTY;
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
        for (int boardArea_i = 0; boardArea_i < 8; boardArea_i++) {
            System.out.print((boardArea_i + 1) + " | "); // 行番号 (1始まり)
            for (int boardArea_j = 0; boardArea_j < 8; boardArea_j++) {
                switch (board[boardArea_i][boardArea_j]) {
                    case EMPTY -> System.out.print(". ");
                    case WHITE -> System.out.print("○ ");
                    case BLACK -> System.out.print("● ");
                }
            }
            System.out.println("| " + (boardArea_i + 1)); // 行番号 (右側, 1始まり)
        }
        System.out.println("   -----------------");
        System.out.println("    1 2 3 4 5 6 7 8"); // 列番号 (下側)
    }
}
