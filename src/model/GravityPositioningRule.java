package model;

import exception.InvalidMoveException;
import exception.InvalidPositionException;

public class GravityPositioningRule implements MoveRule {

    @Override
    public void place(Board board, Piece piece, Position requested)
            throws InvalidPositionException, InvalidMoveException {
        int column = requested.getColumn();
        if (column < 0 || column >= board.getColumns()) {
            throw new InvalidPositionException("Jogada invalida.");
        }
        for (int row = board.getRows() - 1; row >= 0; row--) {
            if (board.getPieceAt(row, column) == null) {
                board.placePiece(piece, new Position(row, column));
                return;
            }
        }
        throw new InvalidMoveException("Jogada invalida.");
    }

    @Override
    public boolean requiresRow() {
        return false;
    }
}
