package reversi;

import java.util.*;

public class GameSystem {
    private ReversiBoard reversiBoard;  // 未使用の変数
    private ReversiCell[][] board;

    public GameSystem(ReversiBoard reversiBoard) {
        this.reversiBoard = reversiBoard;
        this.board = reversiBoard.getBoard();
    }

    // 駒を置いて反転させる
    public boolean placePiece(int row, int col, ReversiCell piece) { // boolean型はCan~~とかIs~~の変数名に
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
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != ReversiCell.EMPTY) {
            return false;
        }
        return !getFlippablePositions(row, col, currentPlayer).isEmpty();
    }

    // 反転可能な位置を取得
    private List<int[]> getFlippablePositions(int row, int col, ReversiCell currentPlayer) {
        List<int[]> positions = new ArrayList<>();
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] dir : directions) {
            int x = row + dir[0]; // 基本的に変数名は略さないように
            int y = col + dir[1];
            List<int[]> tempPositions = new ArrayList<>();
            boolean hasOpponent = false;

            while (x >= 0
                && x < board.length
                && y >= 0
                && y < board[0].length) {
                if (board[x][y] == ReversiCell.EMPTY) break;
                if (board[x][y] == currentPlayer) {
                    if (hasOpponent) {
                        positions.addAll(tempPositions);
                    }
                    break;
                }
                hasOpponent = true;
                tempPositions.add(new int[]{x, y});
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

    // 勝者を判定
    public ReversiCell checkWinner() {
        int blackCount = 0, whiteCount = 0; // これはフィールドに持っておいた方が後々楽かも

        for (ReversiCell[] row : board) {
            for (ReversiCell cell : row) {
                if (cell == ReversiCell.BLACK) blackCount++;
                else if (cell == ReversiCell.WHITE) whiteCount++;
            }
        }

        if (blackCount > whiteCount) return ReversiCell.BLACK;
        if (whiteCount > blackCount) return ReversiCell.WHITE;
        return ReversiCell.EMPTY; // 引き分け
    }

    // ゲーム終了条件
    public boolean isGameOver() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ReversiCell.EMPTY) {
                    if (isValidMove(i, j, ReversiCell.BLACK) || isValidMove(i, j, ReversiCell.WHITE)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
