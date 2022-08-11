import java.awt.*;
import java.util.ArrayList;

/**
 * The ChessBoard class emulates the chess board itself.  It stores an 8x8
 * array of Chess pieces, and controls the piece movement.
 *
 */
public class ChessBoard {
    private int SIZE;
    private ChessPiece[][] board;

    public ChessBoard() {
        SIZE = 8;
        board = new ChessPiece[SIZE][SIZE];
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    private int[] sToNum (String square){
        return new int[]{square.charAt(0) - 97 , square.charAt(1) - 49};
    }
    public void add(ChessPiece piece, String square) {
        int[] arr = sToNum(square);
        board[arr[0]][arr[1]] = piece;
    }
    public void add(ChessPiece piece, int[] square) {
        board[square[0]][square[1]] = piece;
    }

    public void remove(String square) {
        board [sToNum(square)[0]] [sToNum(square)[1]] = null;
    }

    public void remove(int[] square) {
        board [square[0]] [square[1]] = null;
    }

    public ChessPiece pieceAt(String square) {
        return board [sToNum(square)[0]] [sToNum(square)[1]] ;
    }

    public ChessPiece pieceAt(int[] square) {
        return board[square[0]][square[1]];
    }
    public ChessPiece pieceAt(int i, int j){
        return board[i][j];
    }

    public boolean contains(ChessPiece piece){
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                if(board[i][j] != null) {
                    if (board[i][j].equals(piece)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isLineOpen(int[] square1, int[] square2) {
        int rankDif = square2[1] - square1[1];
        int fileDif = square2[0] - square1[0];
        int step;
        int a = Math.max(Math.abs(rankDif), Math.abs(fileDif));
        int b = Math.min(Math.abs(rankDif), Math.abs(fileDif));
        if (b == 0) step = a;
        else {
            int c = a % b;
            while (c != 0) {
                a = b;
                b = c;
                c = a % b;
            }
            step = b;
        }
        for (int i = 1; i < step; i++) {
            if (board[square1[0] + (i * fileDif / step)][square1[1] + (i * rankDif / step)] != null) {
                return false;
            }
        }
        return true;

    }
    public boolean isCheck(int color){
        for(int i =0; i<8; i++){
            for(int j = 0; j<8;j++) {
                int[] loc = {i,j};
                if(pieceAt(loc) != null) {
                    if (Rules.isLegalMove(loc, getKingLoc(color), this)) {
                            return true;
                    }

                }
            }
        }
        return false;
    }
    public boolean move(String square1, String square2) {
        ChessPiece piece = pieceAt(square1);
        if(Rules.isLegalMove(sToNum(square1), sToNum(square2), this)){
            add(piece, square2);
            remove(square1);
            piece.move();
            return true;
        }
        return false;
    }
    public boolean move(int[] square1, int[] square2) {
        ChessPiece piece = pieceAt(square1);
        if(Rules.isLegalMove(square1, square2, this)){
            add(piece, square2);
            remove(square1);
            piece.move();
            return true;
        }
        return false;
    }

    public int[] getPieceLoc(ChessPiece piece){
        for (int i = 0; i<SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != null){
                    if (board[i][j].equals(piece)) {
                        return new int[]{i, j};
                    }
                }
            }

        }
        return null;
    }
    public int[] getKingLoc(int color){
        for (int i = 0; i<SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != null){
                    if (board[i][j].getSign() == 6 && board[i][j].getColor() == color) {
                        return new int[]{i, j};
                    }
                }
            }

        }
        return null;
    }
    public boolean isCheckMate(ArrayList<ChessPiece> pieces){
        int color = pieces.get(0).getColor();
        ArrayList<ChessPiece> givesCheck = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j<8 ;j++) {
                int[] loc = {i,j};
                if(pieceAt(loc) != null) {
                    if (Rules.isLegalMove(loc, getKingLoc(color), this)) {
                        givesCheck.add(pieceAt(loc));
                    }
                }
            }
        }
        if(givesCheck.size()>0) {
            System.out.println("\nCheck!");
            int[] mateFrom = getPieceLoc(givesCheck.get(0));
            int[] kingLoc = getKingLoc(color);
            int[] tryMove = {kingLoc[0], kingLoc[1]};
            ChessPiece temp;
            tryMove[0]++;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            tryMove[1]++;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            tryMove[0]--;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            tryMove[0]--;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            tryMove[1]--;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            tryMove[1]--;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            tryMove[0]++;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            tryMove[0]++;
            if(0 <= tryMove[0] && tryMove[0] <= 7 && 0 <= tryMove[1] && tryMove[1] <= 7) {
                temp = pieceAt(tryMove);
                if (move(kingLoc, tryMove)) {
                    if (!isCheck(color)) return false;
                    add(new King(color), kingLoc);
                    add(temp, tryMove);
                }
            }
            if(givesCheck.size()>1) {
                return true;
            }
            for (ChessPiece piece : pieces){
                if(piece.getSign()!=6) {
                    if (Rules.isLegalMove(getPieceLoc(piece), mateFrom, this)) {
                        return false;
                    }
                }
            }
            if(givesCheck.get(0).getSign() == 3){
                return true;
            }

            if (kingLoc[0] == mateFrom[0]){
                int diff = mateFrom[1]-kingLoc[1];
                int pos = diff/Math.abs(diff);
                for (int i = 1; i < Math.abs(diff); i++){
                    int[] intercept = {kingLoc[0], kingLoc[1] + (i * pos)};
                    for (ChessPiece piece : pieces){
                        if(piece.getSign() != 6) {
                            if (Rules.isLegalMove(getPieceLoc(piece), intercept, this)) {
                                return false;
                            }
                        }
                    }
                }
            }
            if (kingLoc[1] == mateFrom[1]){
                int diff = mateFrom[0]-kingLoc[0];
                int pos = diff/Math.abs(diff);
                for (int i = 1; i < Math.abs(diff); i++){
                    int[] intercept = {kingLoc[0] + (i * pos), kingLoc[1]};
                    for (ChessPiece piece : pieces){
                        if(piece.getSign() != 6) {
                            if (Rules.isLegalMove(getPieceLoc(piece), intercept, this)) {
                                return false;
                            }
                        }
                    }
                }
            }
            if (Math.abs(kingLoc[0]-mateFrom[0]) == Math.abs(kingLoc[1]-mateFrom[1])){
                int rdiff = mateFrom[0]-kingLoc[0];
                int fdiff = mateFrom[1]-kingLoc[1];
                int rpos = rdiff/Math.abs(rdiff);
                int fpos = fdiff/Math.abs(fdiff);
                for (int i = 1; i < Math.abs(rdiff); i++){
                    int[] intercept = {kingLoc[0] + (i * rpos), kingLoc[1] + (i * fpos)};
                    for (ChessPiece piece : pieces){
                        if(piece.getSign() != 6) {
                            if (Rules.isLegalMove(getPieceLoc(piece), intercept, this)) {
                                return false;
                            }

                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void updatePlayerPieces(ArrayList<ChessPiece> pieces)
    {
        int color = pieces.get(0).getColor();
        pieces.clear();

        for (ChessPiece[] arr : board){
            for (ChessPiece piece : arr){
                if (piece!= null){
                    if (piece.getColor()==color){
                        pieces.add(piece);
                    }
                }
            }
        }


    }

}