package reversi;

public class ReversiBoard {
	private static final int BOARD_SIZE = 8;// マジックナンバーを避けて定数として定義する。（”private static final”はこのクラスのみで全インスタンス共通の変更できない定数）
	private ReversiCell[][] board;
	private static final int INITIAL_WHITE_TOP_LEFT_X = 3, INITIAL_WHITE_TOP_LEFT_Y = 3;// XとYで行列を表す
	private static final int INITIAL_WHITE_BOTTOM_RIGHT_X = 4, INITIAL_WHITE_BOTTOM_RIGHT_Y = 4;
	private static final int INITIAL_BLACK_TOP_RIGHT_X = 3, INITIAL_BLACK_TOP_RIGHT_Y = 4;
	private static final int INITIAL_BLACK_BOTTOM_LEFT_X = 4, INITIAL_BLACK_BOTTOM_LEFT_Y = 3;

	public ReversiCell[][] getBoard() {
		return board;
	}

	public ReversiBoard() {
		board = new ReversiCell[BOARD_SIZE][BOARD_SIZE];
		initializeBoard();
	}

	private void initializeBoard() {
		initializeEmptyBoard();// 盤の初期化メソッド
		initializeInitialStones();// オセロの初期配置
	}

	// 盤の初期化
	private void initializeEmptyBoard() {
		for (int stonesInLine_i = 0; stonesInLine_i < BOARD_SIZE; stonesInLine_i++) {
			for (int stonesInLine_j = 0; stonesInLine_j < BOARD_SIZE; stonesInLine_j++) {
				board[stonesInLine_i][stonesInLine_j] = ReversiCell.EMPTY;
			}
		}
	}

	// オセロの初期配置
	private void initializeInitialStones() {
		board[INITIAL_WHITE_TOP_LEFT_X][INITIAL_WHITE_TOP_LEFT_Y] = ReversiCell.WHITE;
		board[INITIAL_WHITE_BOTTOM_RIGHT_X][INITIAL_WHITE_BOTTOM_RIGHT_Y] = ReversiCell.WHITE;
		board[INITIAL_BLACK_TOP_RIGHT_X][INITIAL_BLACK_TOP_RIGHT_Y] = ReversiCell.BLACK;
		board[INITIAL_BLACK_BOTTOM_LEFT_X][INITIAL_BLACK_BOTTOM_LEFT_Y] = ReversiCell.BLACK;
	}

	// 盤の表示
	public void displayBoard() {// 盤全体の表示を管理。ほかのメソッドを呼び出して表示する
		displayColumnNumbers();// 各列数字（1～8）の表示
		displayHorizontalLine();// 横列枠線の表示
		for (int displayCol_i = 0; displayCol_i < BOARD_SIZE; displayCol_i++) {// 各行が表示されていく
			displayRow(displayCol_i);// 行内右方向へ順番に表示を下まで繰り返していく
		}
		displayHorizontalLine();// 横列枠線の表示
		displayColumnNumbers();// 各列数字（1～8）の表示
	}

	// 列番号を表示
	private void displayColumnNumbers() {
		System.out.println("    1 2 3 4 5 6 7 8");
	}

	// 水平線を表示
	private void displayHorizontalLine() {
		System.out.println("   -----------------");
	}

	// 行を表示
	private void displayRow(int rowIndex) {// 各行の表示を管理個別のセルを表示する
		System.out.print((rowIndex + 1) + " | ");// 行番号（1始まり）を表示
		for (int displayRow_j = 0; displayRow_j < BOARD_SIZE; displayRow_j++) {// 繰り返し処理で１行ごとに表示
			displayCell(rowIndex, displayRow_j);// セル表示のメソッド呼び出し
		}
		System.out.println("| " + (rowIndex + 1));// 行番号（1始まり）を右端に表示
	}

	// セル内の表示
	private void displayCell(int row, int col) {
		switch (board[row][col]) {
		case EMPTY -> System.out.print(". ");
		case WHITE -> System.out.print("○ ");
		case BLACK -> System.out.print("● ");
		}
	}
}
