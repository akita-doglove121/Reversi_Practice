package reversi;

import java.util.*;

public class GameSystem {
	private ReversiCell[][] board;
	private Scanner scanner;

	public GameSystem(ReversiBoard reversiBoard, Scanner scanner) {
		this.board = reversiBoard.getBoard();// boardの盤面を取得
		this.scanner = scanner;// プレイヤーからの標準入力
	}

	public int[] getValidInput(ReversiCell currentPlayer) {
		int row = -1, col = -1;
		boolean validInput = false;
		while (!validInput) {
			System.out.println(currentPlayer + "コマを置いてください (行　スペース　列, 1 から指定):");
			if (!scanner.hasNextInt()) {
				System.out.println("無効な入力です。1 から 8 の数字を入力してください。");
				scanner.next(); // 入力をクリア
				continue;
			}
			row = scanner.nextInt();
			if (!scanner.hasNextInt()) {
				System.out.println("無効な入力です。1 から 8 の数字を入力してください。");
				scanner.next(); // 入力をクリア
				continue;
			}
			col = scanner.nextInt();
			// 1～8の範囲内かチェック
			if (row < 1 || row > 8 || col < 1 || col > 8) {
				System.out.println("無効な入力です。1 から 8 の範囲で入力してください。");
				continue;
			}
			// 1ベースを0ベースに変換
			row -= 1;
			col -= 1;
			// 無効な手の場合でもプレイヤーを後退させずに再入力を促す
			if (!isValidMove(row, col, currentPlayer)) {
				System.out.println("その場所にはコマを置けません。もう一度入力してください。");
				continue;
			}
			validInput = true;// 正しい入力がされたらTRUE返す
		}
		return new int[] { row, col }; // 行と列を配列で返す
	}

	// 駒を置いて反転させる
	public boolean placeStone(int row, int col, ReversiCell piece) {
		if (isValidMove(row, col, piece)) {
			board[row][col] = piece; // 駒を置く
			List<int[]> flippablePositions = getFlippablePositions(row, col, piece);
			for (int[] pos : flippablePositions) {
				board[pos[0]][pos[1]] = piece; // 駒を反転させる
			}
			return true;
		}
		return false;
	}

	// 有効な手か確認
	private boolean isValidMove(int row, int col, ReversiCell currentPlayer) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[0].length
				|| board[row][col] != ReversiCell.EMPTY) {
			return false;
		} // 反転可能な場所が１つでもあれば有効
		return !getFlippablePositions(row, col, currentPlayer).isEmpty();
	}

	// 反転可能な位置を取得
	private List<int[]> getFlippablePositions(int row, int col, ReversiCell currentPlayer) {
		List<int[]> positions = new ArrayList<>();
		int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };// オセロを置いた場所から左斜め下から時計回りで周囲1マスずつ判定
		for (int[] dir : directions) {
			int x = row + dir[0], y = col + dir[1];
			List<int[]> tempPositions = new ArrayList<>();
			boolean hasOpponent = false;
			while (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
				if (board[x][y] == ReversiCell.EMPTY)
					break;
				if (board[x][y] == currentPlayer) {
					if (hasOpponent) {
						positions.addAll(tempPositions);
						// System.out.println("反転可能な位置: " + Arrays.toString(tempPositions.get(0))); //
						// デバッグ用
					}
					break;
				}
				hasOpponent = true;// 置けるマスの判定が出来たらさらに周囲１値マスずつ反転できる範囲まで捜索
				tempPositions.add(new int[] { x, y });
				x += dir[0];
				y += dir[1];
			}
		}
		return positions;
	}

	// プレイヤーを切り替える
	public ReversiCell switchPlayer(ReversiCell currentPlayer) {
		return (currentPlayer == ReversiCell.BLACK) ? ReversiCell.WHITE : ReversiCell.BLACK;
	}

	private int blackCount = 0;
	private int whiteCount = 0;

	public int getBlackCount() {
		return this.blackCount;
	}

	public int getWhiteCount() {
		return this.whiteCount;
	}

	// 石のカウントを更新
	private void updateStoneCount() {
		blackCount = 0;
		whiteCount = 0;
		for (ReversiCell[] row : board) {
			for (ReversiCell cell : row) {
				if (cell == ReversiCell.BLACK)
					blackCount++;
				else if (cell == ReversiCell.WHITE)
					whiteCount++;
			}
		}
	}

	//勝者判定
	public ReversiCell checkWinner() {
		updateStoneCount(); // 盤面をスキャンしてカウントを更新
		if (blackCount > whiteCount)
			return ReversiCell.BLACK;
		if (whiteCount > blackCount)
			return ReversiCell.WHITE;
		return ReversiCell.EMPTY; // 引き分け
	}

	// プレイヤーがコマを置けない場合
	public boolean canPlayerMove(ReversiCell player) {
		for (int PlayerMove_i = 0; PlayerMove_i < board.length; PlayerMove_i++) {
			for (int PlayerMove_j = 0; PlayerMove_j < board[PlayerMove_i].length; PlayerMove_j++) {
				if (isValidMove(PlayerMove_i, PlayerMove_j, player)) {
					return true;
				}
			}
		}
		return false;
	}

	// ゲーム終了条件
	public boolean isGameOver() {
		boolean blackCanMove = false;
		boolean whiteCanMove = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == ReversiCell.EMPTY) {
					if (isValidMove(i, j, ReversiCell.BLACK)) {
						blackCanMove = true;
					}
					if (isValidMove(i, j, ReversiCell.WHITE)) {
						whiteCanMove = true;
					}
				}
			}
		}
		// 両方のプレイヤーがコマを置けない場合、ゲーム終了
		return !(blackCanMove || whiteCanMove);
	}
}