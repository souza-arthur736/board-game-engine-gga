package model;

import exception.InvalidMoveException;
import exception.InvalidPositionException;

/**
 * Tabuleiro e coisas relacionadas ao tabuleiro
 */
public class Board {

    private final int rows;
    private final int columns;
    private final Piece[][] grid;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /** Verifica se está dentro dos limites do tabuleiro. */
    public boolean isInsideBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < rows
                && position.getColumn() >= 0 && position.getColumn() < columns;
    }

    /** Retorna a peça de uma posição (ou null se estiver vazia). */
    public Piece getPieceAt(Position position) throws InvalidPositionException {
        if (!isInsideBoard(position)) {
            throw new InvalidPositionException("A posição está fora do tabuleiro.");
        }
        return grid[position.getRow()][position.getColumn()];
    }

    /** Retorna a peça de uma célula pelos índices (controle de bug). */
    public Piece getPieceAt(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            return null;
        }
        return grid[row][column];
    }

    public boolean isEmpty(Position position) throws InvalidPositionException {
        return getPieceAt(position) == null;
    }

    /** Coloca uma peça no tabuleiro, se a posição for válida e estiver vazia. */
    public void placePiece(Piece piece, Position position)
            throws InvalidPositionException, InvalidMoveException {
        if (!isEmpty(position)) {
            throw new InvalidMoveException("Essa posição já está ocupada.");
        }
        grid[position.getRow()][position.getColumn()] = piece;
    }

    /** O tabuleiro está cheio quando não sobra nenhuma casa vazia. */
    public boolean isFull() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (grid[r][c] == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
