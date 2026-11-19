package model;

public class LineWinCondition implements WinCondition {
    private final int quantity;

    public LineWinCondition(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public boolean hasWon(Board board, Piece piece) {
        // direções: horizontal, vertical, diagonal \ e diagonal /
        int[][] directions = { {0, 1}, {1, 0}, {1, 1}, {1, -1} };

        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getColumns(); c++) {
                for (int[] d : directions) {
                    if (countSequence(board, piece, r, c, d[0], d[1]) >= quantity) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int countSequence(Board board, Piece piece, int row, int col, int dRow, int dCol) {
        int count = 0;
        while (board.getPieceAt(row, col) == piece) {
            count++;
            row += dRow;
            col += dCol;
        }
        return count;
    }
}
