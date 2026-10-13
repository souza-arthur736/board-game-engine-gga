import model.Game;
import ui.ConsoleUI;

import java.util.Scanner;

/**
 * Ponto de entrada do programa. Só cria o jogo e inicia a interface.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome do Jogador 1 (X): ");
        String name1 = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        System.out.print("Nome do Jogador 2 (O): ");
        String name2 = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

        if (name1.isEmpty()) name1 = "Jogador 1";
        if (name2.isEmpty()) name2 = "Jogador 2";

        Game game = Game.createTicTacToe(name1, name2);
        new ConsoleUI(game, scanner).run();

        scanner.close();
    }
}
