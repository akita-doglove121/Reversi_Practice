package reversi;
import java.util.*;

public class ReversiMain {
    public static void main(String[] args) {
        ReversiBoard board = new ReversiBoard(); // 盤面オブジェクト
        GameSystem gameSystem = new GameSystem(board); // GameSystem に盤面を渡す
        ReversiCell currentPlayer = ReversiCell.BLACK;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 最新の盤面を表示
            board.displayBoard();

            // ユーザーの入力
            System.out.println(currentPlayer + " あなたのターンです。コマを置いてください (行 列, 1 から指定):");
            int row = scanner.nextInt() - 1; // 1 ベースを 0 ベースに変換
            int col = scanner.nextInt() - 1;

            // 無効な手の場合は再入力を促す
            if (!gameSystem.canPlacePiece(row, col, currentPlayer)) {
                System.out.println("そこには置けません。もう一度入力してください。");
                continue;
            }

            // 勝敗のチェック
            if (gameSystem.isGameOver()) {
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

        scanner.close();
    }
}
