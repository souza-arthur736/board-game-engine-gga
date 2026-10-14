package ui;

import exception.InvalidMoveException;
import exception.InvalidPositionException;
import model.Board;
import model.Game;
import model.GameState;
import model.Move;
import model.Piece;
import model.Player;
import model.Position;

import java.util.Scanner;

/**
 * Interface de terminal*/
public class ConsoleUI {

    private final Game game;
    private final Scanner scanner;

    public ConsoleUI(Game game, Scanner scanner) {
        this.game = game;
        this.scanner = scanner;
    }

    /** Roda a partida inteira até ela terminar. */
    public void run() {
        printHeader();

        while (!game.isFinished()) {
            printBoard();
            Player player = game.getCurrentPlayer();
            System.out.println("\n" + player.getName() + " (" + player.getPiece() + ")");

            Position position = readPosition();
            if (position == null) { // entrada acabou (ex: Ctrl+D)
                System.out.println("\nEntrada encerrada. Fim do programa.");
                return;
            }

            try {
                game.play(new Move(player, position));
            } catch (InvalidMoveException | InvalidPositionException e) {
                System.out.println("\nJogada inválida: " + e.getMessage());
            }
        }

        printBoard();
        printResult();
    }

    private void printHeader() {
        System.out.println("================================");
        System.out.println("          BOARD ENGINE");
        System.out.println("================================");
        System.out.println("Jogo: " + game.getName());
    }

    private void printBoard() {
        Board board = game.getBoard();
        System.out.println();

        // números das colunas
        System.out.print("  ");
        for (int c = 1; c <= board.getColumns(); c++) {
            System.out.print("  " + c + " ");
        }
        System.out.println();

        String separator = "  +" + "---+".repeat(board.getColumns());
        System.out.println(separator);

        for (int r = 0; r < board.getRows(); r++) {
            System.out.print((r + 1) + " |");
            for (int c = 0; c < board.getColumns(); c++) {
                Piece piece = board.getPieceAt(r, c);
                char symbol = (piece == null) ? ' ' : piece.getSymbol();
                System.out.print(" " + symbol + " |");
            }
            System.out.println();
            System.out.println(separator);
        }
    }

    private void printResult() {
        System.out.println();
        if (game.getState() == GameState.WON) {
            Player winner = game.getWinner();
            System.out.println("Fim de jogo! " + winner.getName()
                    + " (" + winner.getPiece() + ") venceu!");
        } else {
            System.out.println("Fim de jogo! Deu empate (velha)!");
        }
    }

    /** Lê linha e coluna do usuário. Retorna null se não houver mais entrada. */
    private Position readPosition() {
        Integer row = readNumber("Informe a linha: ");
        if (row == null) {
            return null;
        }
        Integer column = readNumber("Informe a coluna: ");
        if (column == null) {
            return null;
        }
        // o usuário digita de 1 a N, mas internamente começa em 0
        return new Position(row - 1, column - 1);
    }

    /** Pede um número até o usuário digitar algo válido. */
    private Integer readNumber(String message) {
        while (true) {
            System.out.print(message);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String text = scanner.nextLine().trim();
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
