package model;

/**
 * Representa uma posição (linha e coluna) no tabuleiro.
 * Os índices começam em 0 (a conversão de 1..3 para 0..2 é feita na interface).
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
