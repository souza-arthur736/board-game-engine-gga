package exception;

/**
 * Lançada quando uma jogada não respeita as regras do jogo
 * (ex: posição já ocupada, jogador fora da vez, jogo já terminado).
 */
public class InvalidMoveException extends Exception {

    public InvalidMoveException(String message) {
        super(message);
    }
}
