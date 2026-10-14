package model;

/**
 * Representa uma posição no tabuleiro
 * conversão 0,1,2 pra 1,2,3 feita na interface
 */
public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
