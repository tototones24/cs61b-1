public class Piece {
    private boolean isFire;
    private boolean isKing;
    private boolean hasCaptured;
    private Board board;
    private String type;

    public Piece(boolean isFire, Board b, int x, int y, String type){
        this.isFire = isFire;
        this.isKing = false;
        this.board = b;
        this.type = type;
    }

    public boolean isFire(){
        return isFire;
    }

    public int side(){
        if (isFire){
            return 0;
        }
        return 1;
    }

    public boolean isKing(){
        return isKing;
    }

    public boolean isBomb(){
        return type.equals("bomb");
    }

    public boolean isShield(){
        return type.equals("shield");
    }

    public void move(int x, int y){

    }

    public boolean hasCaptured(){
        return hasCaptured;
    }

    public void doneCapturing(){
        hasCaptured = false;
    }

}
