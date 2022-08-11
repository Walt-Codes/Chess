public abstract class ChessPiece {

    private final int color;

    private int sign = 0;

    private boolean moved;

    public ChessPiece(int color) {
        this.color = color;
        moved = false;
    }
    public boolean canTake(ChessPiece piece){
        if (piece == null) return true;
        return piece.getColor() != color;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void move(){
        moved = true;
    }

    public int getColor() {
        return color;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
       this.sign = sign;
    }

}
