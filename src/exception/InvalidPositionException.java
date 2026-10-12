package exception;

/**
 * Lançada quando uma posição não existe no tabuleiro
 * (ex: linha 5 em um tabuleiro 3x3).
 */
public class InvalidPositionException extends Exception {

    public InvalidPositionException(String message) {
        super(message);
    }
}
