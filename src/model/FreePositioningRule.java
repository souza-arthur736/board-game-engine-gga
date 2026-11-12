package model;

import exception.InvalidMoveException;
import exception.InvalidPositionException;

public class FreePositioningRule implements MoveRule 
{

    @Override
    public void place(Board board, Piece piece, Position requested) throws InvalidPositionException, InvalidMoveException 
    {
        board.placePiece(piece, requested);
    }

    @Override
    public boolean requiresRow() 
    {
        return true;
    }
}