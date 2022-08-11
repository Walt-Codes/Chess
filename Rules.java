public class Rules {
    public static boolean isLegalMove(int[] square1, int[] square2, ChessBoard board) {
        ChessPiece piece = board.pieceAt(square1);
        int color = piece.getColor();
        int sign = piece.getSign();
        int x = square1[0];
        int y = square1[1];
        int w = square2[0];
        int z = square2[1];
        if (w < 8 && w > -1) {
            if (z < 8 && z > -1) {

                if (piece.canTake(board.pieceAt(square2))){

                    if (board.isLineOpen(square1, square2)) {
                        boolean b = Math.abs(x - w) == Math.abs(y - z);
                        boolean r = (x - w == 0)||(y - z == 0);
                        switch (sign) {
                            case 1 -> {
                                if (w == x && y + color == z) {
                                    return board.pieceAt(square2) == null;
                                }
                                if (w == x && y + (2 * color) == z && !piece.hasMoved()) {
                                    return board.pieceAt(square2) == null;
                                }
                                if ((Math.abs(x - w) == 1) && (z - y) == color) {
                                    return board.pieceAt(square2) != null;
                                }
                                return false;
                            }
                            case 2 -> {
                                return b;
                            }
                            case 3 -> {
                                return (Math.abs(x - w) == 2
                                        && Math.abs(y - z) == 1)
                                        || (Math.abs(x - w) == 1
                                        && Math.abs(y - z) == 2);
                            }
                            case 4 -> {
                                return r;
                            }
                            case 5 -> {
                                return r || b;
                            }
                            case 6 -> {
                                return (Math.abs(x - w) == 1 ||
                                        Math.abs(y - z) == 1) ;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
