package model;

import exception.InvalidMoveException;
import exception.InvalidPositionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Controla a partida: guarda o tabuleiro, os jogadores, de quem é a vez
 */
public class Game {

    private final String name;
    private final Board board;
    private final List<Player> players;
    private final LineWinCondition winCondition;

    private int currentPlayerIndex;
    private GameState state;
    private Player winner;

    public Game(String name, Board board, List<Player> players, LineWinCondition winCondition, MoveRule moveRule) {
        this.name = name;
        this.board = board;
        this.players = new ArrayList<>(players); // cópia, para proteger a lista original
        this.winCondition = winCondition;
        this.currentPlayerIndex = 0;
        this.state = GameState.IN_PROGRESS;
        this.winner = null;
        this.moveRule = moveRule;
    }

    public static Game createTicTacToe(String nameX, String nameO) {
        List<Player> players = new ArrayList<>();
        players.add(new Player(nameX, new Piece('X')));
        players.add(new Player(nameO, new Piece('O')));
        return new Game("Jogo da Velha", new Board(3, 3), players, new LineWinCondition(3));
    }

    //getters, setters

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getWinner() {
        return winner;
    }

    public GameState getState() {
        return state;
    }
    
    public MoveRule getMoveRule() {
        return moveRule;
    }
    
    public boolean isFinished() {
        return state != GameState.IN_PROGRESS;
    }

    /**
     * Executa uma jogada, valida se a jogada é válida, e depois atualiza o estado
     */
    
    public void play(Position position) throws InvalidMoveException, InvalidPositionException {
        if (isFinished()) {
            throw new InvalidMoveException("A partida já terminou.");
        }

        Player player = getCurrentPlayer();
        Piece piece = player.getPiece();
        board.placePiece(piece, position);

        if (winCondition.hasWon(board, piece)) 
        {
            state = GameState.WON;
            winner = player;
        } else if (board.isFull()) {
            state = GameState.DRAW;
        } else {
            nextTurn();
        }
    }


    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}
