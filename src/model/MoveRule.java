package model;

import exception.InvalidMoveException;
import exception.InvalidPositionException;

public interface MoveRule {

    void place(Board board, Piece piece, Position requested)
            throws InvalidPositionException, InvalidMoveException;

    boolean requiresRow();
}
