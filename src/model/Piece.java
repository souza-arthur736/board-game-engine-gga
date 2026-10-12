package model;

/**
 * Representa uma peça (marcação) do jogo, como o X ou o O.
 * Depois de criada a peça não muda, por isso só tem getter.
 */
public class Piece {

    private final char symbol;

    public Piece(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
