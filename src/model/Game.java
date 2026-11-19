package model;

import exception.InvalidMoveException;
import exception.InvalidPositionException;
import java.util.ArrayList;
import java.util.List;

public class Game 
{
    private final String name;
    private final Board board;
    private final List<Player> players;
    private final MoveRule moveRule;
    private final WinCondition winCondition;
    private int currentPlayerIndex;
    private GameState state;
    private Player winner;

    public Game(String name, Board board, List<Player> players, MoveRule moveRule, WinCondition winCondition) 
    {
        this.name = name;
        this.board = board;
        this.players = new ArrayList<>(players);
        this.moveRule = moveRule;
        this.winCondition = winCondition;
        this.currentPlayerIndex = 0;
        this.state = GameState.IN_PROGRESS;
        this.winner = null;
    }

    public void playTurn(Position targetPosition) throws InvalidMoveException, InvalidPositionException 
        {
        if (state != GameState.IN_PROGRESS) {
            throw new InvalidMoveException("A partida já foi encerrada.");
        }

        Player currentPlayer = getCurrentPlayer();
        Piece piece = currentPlayer.getPiece();

        moveRule.place(board, piece, targetPosition);

        if (winCondition.hasWon(board, piece)) {
            this.state = GameState.WON;
            this.winner = currentPlayer;
        } else if (board.isFull()) {
            this.state = GameState.DRAW;
        } else {
            nextTurn();
        }
    }

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public String getName() {
        return name;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public GameState getState() {
        return state;
    }

    public Player getWinner() {
        return winner;
    }
}

