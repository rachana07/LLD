import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String args[]){
        Game game = new Game();
        game.initializeGame();
        game.startGame();
    }

}

enum PieceType {
    X,
    O
}
class Piece{

    PieceType pieceType;
    public Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }
}


class PieceX extends Piece{

    public PieceX(PieceType pieceType) {
        super(pieceType);
    }
}

class PieceO extends Piece{

    public PieceO(PieceType pieceType) {
        super(pieceType);
    }
}

class Board{
    int size;
    Piece[][] board;
    Board(int size){
        this.size = size;
        board = new Piece[size][size];
    }

    public boolean addPiece(int row, int col, Piece piece){
        if(board[row][col] !=null){
            return false;
        }

        board[row][col] = piece;
        return true;
    }

    public boolean checkWinner(int row, int col, Piece piece){
        boolean win = true;
        for(int i=0;i<size;i++){
            if(board[row][i] == null || board[row][i].getPieceType() !=piece.getPieceType()){
                   win = false;
                   break;
            }

        }

        if (win) return true;

        win = true;
        for(int i=0;i<size;i++){
            if(board[i][col] == null || board[i][col].getPieceType() !=piece.getPieceType()){
                   win = false;
                   break;
            }
        }

        if(win) return true;

        if(row == col){
            win = true;
           for(int i=0;i<size;i++){
               if(board[i][i] == null || board[i][i].getPieceType() !=piece.getPieceType()){
                   win = false;
                   break;
               }
           }
            if (win) return true;
        }


        if(row+col == size-1){
            win = true;
            for(int i=0;i<size;i++){
                if(board[i][size-i-1]==null || board[i][size-i-1].getPieceType() != piece.getPieceType()){
                    win = false;
                    break;
                }
            }

            if (win) return true;
        }

        return false;

    }

    public boolean isBoardFull(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j]==null){
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] == null ? "_" : board[i][j].getPieceType());
                System.out.print(" ");
            }
            System.out.println();
        }
    }



}

class Player{
    String name;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    Piece piece;
    public Player(String name, Piece piece){
        this.name = name;
        this.piece = piece;
    }
}

class Game{
    Deque<Player> players;
    Board board;

    public void initializeGame(){
        players = new LinkedList<>();
        PieceX playingPieceX = new PieceX(PieceType.X);
        PieceO playingPieceO = new PieceO(PieceType.O);

        Player player1 = new Player("player1", playingPieceX);
        Player player2 = new Player("player2", playingPieceO);

        players.add(player1);
        players.add(player2);

        board = new Board(3);
    }

    public void startGame(){
        boolean noWinner = true;
        Scanner scanner = new Scanner(System.in);

        while(noWinner){
            board.printBoard();
            Player currPlayer = players.poll();
            System.out.println(currPlayer.name + "s turn");
            System.out.println("enter row");
            int row = scanner.nextInt();
            System.out.println("enter col");
            int col = scanner.nextInt();

            if(board.addPiece(row,col,currPlayer.getPiece())){
                if(board.checkWinner(row,col,currPlayer.getPiece())){
                    System.out.println("Player"+currPlayer.name + "wins");
                    noWinner = false;
                }else{
                    players.add(currPlayer);
                }
            }else{
                System.out.println("wrong move");
                players.addFirst(currPlayer);
            }

            if(players.size() == 2 && board.isBoardFull()){
                System.out.println("It's a tie");
                noWinner = false;
            }
        }
        scanner.close();

    }


}