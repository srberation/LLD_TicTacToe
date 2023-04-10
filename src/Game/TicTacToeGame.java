package Game;

import Model.*;
import com.sun.tools.javac.util.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    /**
     * Moving player turn vice therefore deque
     */
    Deque<Player> players;
    Board gameBoard;

    TicTacToeGame(){
        initializeGame();
    }
    public void initializeGame(){
        players = new LinkedList<>();
        PieceX cross = new PieceX();
        Player player1 = new Player("Shakur",cross);

        PieceO zero = new PieceO();
        Player player2 = new Player("2Pac",zero);

        players.add(player1);
        players.add(player2);

        //Init Board
        gameBoard = new Board(3);
    }

    public String startGame(){
        boolean noWinner = true;
        while(noWinner){

            /**
             * Who ever is there in first pos in the deque, that person has the turn , so remove him and add back to the queue
             */
            Player playerTurn = players.removeFirst();

            /**
             * Get the free space from the board
             */
            gameBoard.printBoard();
            List<Pair<Integer,Integer>> freeSpace = gameBoard.getFreeCells();
            if(freeSpace.isEmpty()){
                noWinner = false;
                continue;
            }

            /**
             * Read users input
             */
            System.out.println("Player:"+ playerTurn.name + "Enter row,col:");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int row = Integer.valueOf(values[0]);
            int col = Integer.valueOf(values[1]);

            /**
             * Place the piece
             */
            boolean pieceAddedProperly = gameBoard.addPiece(row,col,playerTurn.playingPiece);
            if(!pieceAddedProperly){
                /**
                 * player should insert the piece at valid place
                 */
                System.out.println("Incorrect pos ch osen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn) ;
            boolean winner = isThereWinner(row, col, playerTurn.playingPiece.pieceType);
            if (winner) return playerTurn.name;
        }
        return "tie";
    }

    private boolean isThereWinner(int row, int col, PieceType pieceType) {
        boolean rowMatch = true;
        boolean colMatch = true;
        boolean diagonalMatch = true;
        boolean antidiagonalMatch = true;

        for(int i = 0; i< gameBoard.size;i++){
            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].pieceType != pieceType) rowMatch = false;
        }

        for(int i = 0; i< gameBoard.size;i++){
            if(gameBoard.board[i][col] == null || gameBoard.board[i][col].pieceType != pieceType) colMatch = false;
        }

        for(int i =0,j=0; i< gameBoard.size; i++,j++){
            if(gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) diagonalMatch = false;
        }

        for(int i =0,j= gameBoard.size-1; i< gameBoard.size; i++,j--){
            if(gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) antidiagonalMatch = false;
        }

        return rowMatch||colMatch||diagonalMatch||antidiagonalMatch ;
    }


}
