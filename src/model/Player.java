package model;

/**
 * Representa um jogador: tem um nome e a peça que ele usa.
 */
public class Player {

    private final String name;
    private final Piece piece;

    public Player(String name, Piece piece) {
        this.name = name;
        this.piece = piece;
    }

    public String getName() {
        return name;
    }

    public Piece getPiece() {
        return piece;
    }
}
