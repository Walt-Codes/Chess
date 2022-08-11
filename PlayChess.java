import java.util.ArrayList;
import java.util.Scanner;

public class PlayChess {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        ChessBoard board = new ChessBoard();
        ArrayList<ChessPiece> whitePieces = new ArrayList<>();
        whitePieces.add(new King(1));
        ArrayList<ChessPiece> blackPieces = new ArrayList<>();
        blackPieces.add(new King(-1));
        startingPosition(board);
        board.updatePlayerPieces(whitePieces);
        board.updatePlayerPieces(blackPieces);
        displayBoard(board);
        boolean validMove;
        for (int i = 0; i < 100; i++){
            validMove = false;
            while(!validMove) {
                validMove = getMove(board, in, whitePieces);
                if(!validMove){
                    System.out.println("Illegal move!\n");
                }
            }
            board.updatePlayerPieces(blackPieces);
            displayBoard(board);
            if(board.isCheckMate(blackPieces)){
                System.out.println("White wins!");
                break;
            }
            validMove = false;
            while(!validMove) {
                validMove = getMove(board, in, blackPieces);
                if(!validMove){
                    System.out.println("Illegal move!\n");
                }
            }
            displayBoard(board);
            board.updatePlayerPieces(whitePieces);
            if(board.isCheckMate(whitePieces)){
                System.out.println("Black Wins!");
                i = 800;
            }
        }
        displayBoard(board);
    }

    public static void displayBoard(ChessBoard board){
        ChessPiece piece;
        System.out.print("  ");
        for (int i = 97; i < 105; i++){
            System.out.printf("%6c", (char)i);
        }
        System.out.println("\n     ----- ----- ----- ----- -----" +
                " ----- ----- -----");
        for (int i = 7; i >=0 ; i--){
            System.out.print(i+1+"|");
            for (int j = 0; j < 8; j++){
                int[] loc = {j, i};
                piece = board.pieceAt(loc);
                if (piece == null) {
                    System.out.printf("%6d", 0);
                }
                else{System.out.printf("%6d", piece.getSign() * piece.getColor());}
            }
            System.out.println("\n");
        }
    }
    public static void startingPosition(ChessBoard board){
        board.add(new Pawn(1),"a2");
        board.add(new Pawn(1),"b2");
        board.add(new Pawn(1),"c2");
        board.add(new Pawn(1),"d2");
        board.add(new Pawn(1),"e2");
        board.add(new Pawn(1),"f2");
        board.add(new Pawn(1),"g2");
        board.add(new Pawn(1),"h2");
        board.add(new Pawn(-1),"a7");
        board.add(new Pawn(-1),"b7");
        board.add(new Pawn(-1),"c7");
        board.add(new Pawn(-1),"d7");
        board.add(new Pawn(-1),"e7");
        board.add(new Pawn(-1),"f7");
        board.add(new Pawn(-1),"g7");
        board.add(new Pawn(-1),"h7");
        board.add(new Rook(1),"a1");
        board.add(new Rook(1),"h1");
        board.add(new Knight(1),"b1");
        board.add(new Knight(1),"g1");
        board.add(new Bishop(1),"c1");
        board.add(new Bishop(1),"f1");
        board.add(new Queen(1),"d1");
        board.add(new Rook(-1),"a8");
        board.add(new Rook(-1),"h8");
        board.add(new Knight(-1),"b8");
        board.add(new Knight(-1),"g8");
        board.add(new Bishop(-1),"c8");
        board.add(new Bishop(-1),"f8");
        board.add(new Queen(-1),"d8");
        board.add(new King(1),"e1");
        board.add(new King(-1), "e8");

    }


    public static boolean getMove(ChessBoard board, Scanner in, ArrayList<ChessPiece> playerPieces){
        int colorNum = playerPieces.get(0).getColor();
        // gets color from piece array. assigns string accordingly
        String color = (colorNum == 1)? "white" : "black";
        System.out.printf("%s's move: \n", color);
        System.out.print("Square from: ");
        String square1 = in.nextLine();
        System.out.print("Square to: ");
        String square2 = in.nextLine();
        ChessPiece temp2 = board.pieceAt(square2);
        ChessPiece temp1 = board.pieceAt(square1);
        boolean moved = false;
        if(playerPieces.contains(board.pieceAt(square1))){
            moved = board.move(square1, square2);
        }
        if(moved && board.isCheck(colorNum)){
            System.out.println("it's check");
            board.add(temp1, square1);
            board.add(temp2, square2);
            moved = false;
        }
        return moved;
    }


}
