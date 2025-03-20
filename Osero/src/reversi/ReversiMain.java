package reversi;

import java.util.*;

public class ReversiMain {
	public static void main(String[] args) {
		ReversiBoard board = new ReversiBoard(); // 盤面オブジェクト
		Scanner scanner = new Scanner(System.in);
		GameSystem gameSystem = new GameSystem(board, scanner); // GameSystem に盤面を渡す
		ReversiCell currentPlayer = ReversiCell.BLACK;

		while (true) {
			board.displayBoard(); // 最新の盤面を表示
			// 現在のプレイヤーがコマを置けるかどうか確認
			if (!gameSystem.canPlayerMove(currentPlayer)) {
				System.out.println(currentPlayer + "はコマを置けません。パスします。");
				currentPlayer = gameSystem.switchPlayer(currentPlayer);
				continue;
			}
			// コマを置ける場合にターンを開始
			System.out.println(currentPlayer + "のターンです。");
			// 有力な入力を取得する
			int[] position = gameSystem.getValidInput(currentPlayer);
			int row = position[0];
			int col = position[1];
			// 駒を置いて反転処理を行う
			if (!gameSystem.placeStone(row, col, currentPlayer)) {
				System.out.println("その場所にはコマを置けません。再度入力してください。");
				continue; // 無効な入力があった場合は再度入力を促す
			}
			if (gameSystem.isGameOver()) {// 勝敗のチェック
				board.displayBoard();
				ReversiCell winner = gameSystem.checkWinner();
				if (winner == ReversiCell.EMPTY) {
					System.out.println("引き分けです！");
				} else {
					System.out.println("勝者: " + winner);
				}
				break;
			}
			// プレイヤー交代
			currentPlayer = gameSystem.switchPlayer(currentPlayer);
		}
	}
}
