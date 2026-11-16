import exception.InvalidConfigurationException;
import loader.GameLoader;
import model.Game;
import ui.ConsoleUI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path gamesDir = Paths.get("games");

        if (!Files.exists(gamesDir) || !Files.isDirectory(gamesDir)) {
            System.out.println("Erro ao iniciar a Engine: diretório 'games/' não encontrado.");
            return;
        }

        List<Path> jsonFiles;
        try (Stream<Path> paths = Files.list(gamesDir)) {
            jsonFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".json"))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Erro ao listar os arquivos de configuração na pasta 'games/'.");
            return;
        }

        if (jsonFiles.isEmpty()) {
            System.out.println("Nenhum arquivo de configuração (.json) encontrado na pasta 'games/'.");
            return;
        }

        ConsoleUI ui = new ConsoleUI();
        List<String> fileNames = jsonFiles.stream()
                .map(p -> p.getFileName().toString())
                .collect(Collectors.toList());

        int selectedIndex = ui.selectGame(fileNames);
        Path selectedFile = jsonFiles.get(selectedIndex);

        GameLoader loader = new GameLoader();
        try {
            Game game = loader.loadFromFile(selectedFile);
            ui.start(game);
        } catch (InvalidConfigurationException e) {
            System.out.println("\nErro ao carregar o jogo:");
            System.out.println(e.getMessage());
        }
    }
}
