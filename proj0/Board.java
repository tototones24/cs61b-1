public class Board {
    private Piece[][] board;
    private Piece selectedPiece;
    private boolean madeMove;
    private int selectedX;
    private int selectedY;

    public Board(boolean shouldBeEmpty){
        board = new Piece[8][8];
        selectedPiece = null;
        madeMove = false;
        if (!shouldBeEmpty){
            for (int i = 0; i < 4; i++){
                //fire and water pawns
                board[i*2][0] = new Piece(true, this, i*2, 0, "pawn");
                board[1 + i*2][7] = new Piece(false, this,  1 + i*2, 7, "pawn");

                //fire and water shields
                board[1 + i*2][1] = new Piece(true, this,  1 + i*2,1, "shield");
                board[i*2][6] = new Piece(false, this,  i*2,6, "shield");

                //fire and water bombs
                board[i*2][2] = new Piece(true, this,  i*2, 3, "bomb");
                board[1 + i*2][5] = new Piece(false, this,  1 + i*2,5, "bomb");
            }
        }
    }

    private void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  StdDrawPlus.setPenColor(StdDrawPlus.RED);
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                if (selectedPiece != null && i == selectedX && j == selectedY)
                    StdDrawPlus.filledSquare(i + .5, j + .5, .5);

                if (board[i][j] != null) {
                    Piece p = board[i][j];
                    String img = "img/";
                    if (p.isBomb()){
                        img += "bomb";
                    }
                    else if (p.isShield()){
                        img += "shield";
                    }
                    else {
                        img += "pawn";
                    }

                    if (p.isFire()){
                        img += "-fire";
                    }
                    else {
                        img += "-water";
                    }

                    if (p.isKing()){
                        img += "-crowned";
                    }

                    img += ".png";

                    StdDrawPlus.picture(i + .5, j + .5, img, 1, 1);
                }
            }
        }
    }

    public void place(Piece p, int x, int y){
        if (x > 7 | x < 0 | y > 7 | y < 0){
            return;
        }
        board[x][y] = p;
    }

    public Piece pieceAt(int x, int y){
        if (x > 7 | x < 0 | y > 7 | y < 0){
            return null;
        }
        return board[x][y];
    }

    private boolean validMove(int xi, int yi, int xf, int yf){
        if (xf > 7 | xf < 0 | yf > 7 | yf < 0){
            return false;
        }
        //handle king
        /*this part is wrong because you can capture

          if (board[xf][yf] != null){
          return false;
          }
          if (Math.abs(xi - xf) != 1){
          return false;
          }

          if (board[xi][yi].isKing()){
          if (Math.abs(yi - yf) != 1){
          return false;
          }
          }
          else {
          return yf == (yi + 1);
          }
          */
        return true;
    }

    public boolean canSelect(int x, int y){
        //todo: fix if you can select opponent's piece
        if (pieceAt(x,y) != null){
            return selectedPiece != null | !madeMove;
        }
        if (selectedPiece != null && !madeMove && validMove(selectedX, selectedY, x, y)){
            return true;
        }
        return selectedPiece.hasCaptured() && validMove(selectedX, selectedY, x, y);
    }

    public void select(int x, int y){
        Piece p = pieceAt(x,y);
        if (p != null){
            selectedPiece = p;
            selectedX = x;
            selectedY = y;
        }
    }

    public boolean canEndTurn(){
        return madeMove;
    }

    public void endTurn(){
        selectedPiece = null;
        madeMove = false;
    }

    public static void main(String[] args){
        int N = 8;
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);
        Board board = new Board(false);

        /** Monitors for mouse presses. Wherever the mouse is pressed,
          a new piece appears. */
        while(true) {
            board.drawBoard();
            if (StdDrawPlus.mousePressed()) {
                int x = (int) StdDrawPlus.mouseX();
                int y = (int) StdDrawPlus.mouseY();
                board.select(x,y);
            }            
            if (selectedPiece == null)
                continue;
            if (StdDrawPlus.mousePressed()) {
                int x2 = (int) StdDrawPlus.mouseX();
                int y2 = (int) StdDrawPlus.mouseY();
                selectedPiece.move(x2,y2);
            }            

            StdDrawPlus.show(100);
        }
    }
}
