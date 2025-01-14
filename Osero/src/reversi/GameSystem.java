package reversi;

import java.util.*;

public class GameSystem {
    private ReversiCell[][] board;
    public GameSystem(ReversiBoard reversiBoard) {
        this.board = reversiBoard.getBoard();
    }

    // 駒を置いて反転させる
    public boolean canPlacePiece(int row, int col, ReversiCell piece) {
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
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};//オセロを置いた場所から左斜め下から時計回りで周囲1マスずつ判定
        for (int[] dir : directions) {
            int x = row + dir[0], y = col + dir[1];
            List<int[]> tempPositions = new ArrayList<>();
            boolean hasOpponent = false;

            while (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
                if (board[x][y] == ReversiCell.EMPTY) break;
                if (board[x][y] == currentPlayer) {
                    if (hasOpponent) {
                        positions.addAll(tempPositions);
                    }
                    break;
                }
                hasOpponent = true;//置けるマスの判定が出来たらさらに周囲１値マスずつ反転できる範囲まで捜索
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
        int blackCount = 0, whiteCount = 0;

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
        for (int countStones_i= 0; countStones_i < board.length; countStones_i++) {
            for (int countStones_j = 0; countStones_j < board[countStones_i].length; countStones_j++) 
            {
            	if (board[countStones_i][countStones_j] == ReversiCell.EMPTY) 
            	{
            		if (isValidMove(countStones_i, countStones_j, ReversiCell.BLACK) || isValidMove(countStones_i, countStones_j, ReversiCell.WHITE)) 
            		{
            			return false;
                    }
                }
            }
        }
        return true;
    }
}
