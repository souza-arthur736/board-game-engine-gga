package loader;

import exception.InvalidConfigurationException;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameLoader {

    public Game loadFromFile(Path filePath) throws InvalidConfigurationException {
        String content;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            throw new InvalidConfigurationException("Não foi possível ler o arquivo de configuração: " + filePath.getFileName());
        }

        
        String gameName = extractString(content, "\"game\"\\s*:\\s*\"([^\"]+)\"");
        if (gameName == null || gameName.isBlank()) {
            throw new InvalidConfigurationException("O nome do jogo ('game') não foi definido no arquivo.");
        }

        
        int rows = extractInt(content, "\"rows\"\\s*:\\s*(-?\\d+)");
        int columns = extractInt(content, "\"columns\"\\s*:\\s*(-?\\d+)");
        if (rows <= 0 || columns <= 0) {
            throw new InvalidConfigurationException("Dimensões inválidas para o tabuleiro (" + rows + "x" + columns + "). Linhas e colunas devem ser maiores que zero.");
        }
        Board board = new Board(rows, columns);

        
        int playerCount = extractInt(content, "\"count\"\\s*:\\s*(-?\\d+)");
        List<String> symbols = extractStringArray(content, "\"symbols\"\\s*:\\s*\\[([^\\]]+)\\]");
        if (playerCount < 2 || symbols.size() < playerCount) {
            throw new InvalidConfigurationException("Configuração de jogadores inválida. São necessários pelo menos 2 jogadores e símbolos correspondentes.");
        }

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            String sym = symbols.get(i).trim();
            if (sym.isEmpty()) {
                throw new InvalidConfigurationException("Símbolo vazio encontrado para o Jogador " + (i + 1) + ".");
            }
            players.add(new Player("Jogador " + (i + 1), new Piece(sym.charAt(0))));
        }

        
        String placementType = extractString(content, "\"placement\"\\s*:\\s*\"([^\"]+)\"");
        MoveRule moveRule = createMoveRule(placementType);

        
        String winType = extractString(content, "\"type\"\\s*:\\s*\"([^\"]+)\"");
        int winQuantity = extractInt(content, "\"quantity\"\\s*:\\s*(-?\\d+)");
        WinCondition winCondition = createWinCondition(winType, winQuantity, rows, columns);

        return new Game(gameName, board, players, moveRule, winCondition);
    }

    private MoveRule createMoveRule(String placementType) throws InvalidConfigurationException {
        if (placementType == null) {
            throw new InvalidConfigurationException("Regra de posicionamento ('placement') não informada.");
        }
        return switch (placementType.toUpperCase()) {
            case "FREE_POSITIONING" -> new FreePositioningRule();
            case "GRAVITY_POSITIONING" -> new GravityPositioningRule();
            default -> throw new InvalidConfigurationException("Regra de posicionamento '" + placementType + "' não reconhecida pela Engine.");
        };
    }

    private WinCondition createWinCondition(String winType, int quantity, int rows, int cols) throws InvalidConfigurationException {
        if (winType == null) {
            throw new InvalidConfigurationException("Condição de vitória ('type') não informada.");
        }
        if (quantity <= 1 || quantity > Math.max(rows, cols)) {
            throw new InvalidConfigurationException("Quantidade para vitória (" + quantity + ") incompatível com o tabuleiro.");
        }
        return switch (winType.toUpperCase()) {
            case "ALIGNMENT" -> new LineWinCondition(quantity);
            default -> throw new InvalidConfigurationException("Regra de vitória '" + winType + "' não reconhecida pela Engine.");
        };
    }

    private String extractString(String json, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }

    private int extractInt(String json, String regex) throws InvalidConfigurationException {
        Matcher matcher = Pattern.compile(regex).matcher(json);
        if (!matcher.find()) {
            throw new InvalidConfigurationException("Parâmetro numérico obrigatório ausente ou mal formatado no JSON.");
        }
        return Integer.parseInt(matcher.group(1));
    }

    private List<String> extractStringArray(String json, String regex) {
        List<String> items = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(json);
        if (matcher.find()) {
            String rawArray = matcher.group(1);
            Matcher itemMatcher = Pattern.compile("\"([^\"]+)\"").matcher(rawArray);
            while (itemMatcher.find()) {
                items.add(itemMatcher.group(1));
            }
        }
        return items;
    }
}
