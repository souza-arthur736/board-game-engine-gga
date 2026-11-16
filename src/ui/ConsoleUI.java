package ui;

import exception.InvalidMoveException;
import exception.InvalidPositionException;
import model.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public int selectGame(List<String> gameFiles) {
        System.out.println("================================");
        System.out.println("          BOARD ENGINE          ");
        System.out.println("================================");
        System.out.println("Selecione o jogo:");
        for (int i = 0; i < gameFiles.size(); i++) {
            System.out.println((i + 1) + " - " + gameFiles.get(i));
        }

        while (true) {
            System.out.print("Opção: ");
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= gameFiles.size()) {
                    return choice - 1;
                }
                System.out.println("Opção inválida. Escolha entre 1 e " + gameFiles.size() + ".");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite apenas números.");
                scanner.nextLine(); // limpa buffer
            }
        }
    }

    public void start(Game game) {
        while (game.getState() == GameState.IN_PROGRESS) {
            printHeader(game);
            printBoard(game.getBoard());

            Player current = game.getCurrentPlayer();
            System.out.println(current.getName() + " (" + current.getSymbol() + ")");

            try {
                System.out.print("Informe a linha: ");
                int row = scanner.nextInt() - 1;
                System.out.print("Informe a coluna: ");
                int col = scanner.nextInt() - 1;

                game.playTurn(new Position(row, col));
            } catch (InputMismatchException e) {
                System.out.println("\n[ERRO] Por favor, informe apenas números inteiros.\n");
                scanner.nextLine();
            } catch (InvalidMoveException | InvalidPositionException e) {
                System.out.println("\n[JOGADA INVÁLIDA] " + e.getMessage() + "\n");
            }
        }

        printHeader(game);
        printBoard(game.getBoard());
        if (game.getState() == GameState.WIN) {
            System.out.println("FIM DE JOGO! Vencedor: " + game.getWinner().getName() + " (" + game.getWinner().getSymbol() + ")");
        } else {
            System.out.println("FIM DE JOGO! A partida terminou em EMPATE.");
        }
    }

    private void printHeader(Game game) {
        System.out.println("\n================================");
        System.out.println("          BOARD ENGINE          ");
        System.out.println("================================");
        System.out.println("Jogo: " + game.getName());
    }

    private void printBoard(Board board) {
        int rows = board.getRows();
        int cols = board.getColumns();

        System.out.print("    ");
        for (int c = 1; c <= cols; c++) {
            System.out.print(c + "   ");
        }
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.print("  +");
            for (int c = 0; c < cols; c++) {
                System.out.print("---+");
            }
            System.out.println();

            System.out.print((r + 1) + " |");
            for (int c = 0; c < cols; c++) {
                Piece p = board.getPieceAt(new Position(r, c));
                char symbol = (p != null) ? p.getSymbol() : ' ';
                System.out.print(" " + symbol + " |");
            }
            System.out.println();
        }

        System.out.print("  +");
        for (int c = 0; c < cols; c++) {
            System.out.print("---+");
        }
        System.out.println();
    }
}
