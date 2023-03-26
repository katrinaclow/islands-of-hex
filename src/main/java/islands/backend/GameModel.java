package islands.backend;

/**
 * Class to model the play of the game
 *
 */
public class GameModel {
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    private final int gameSize;
    private final WhiteWeightedQuickUnionUF whiteUF;
    private final BlackWeightedQuickUnionUF blackUF;

    /**
     * Construct a game with given sizexsize and an empty game board, creating an object for both black and white
     * player pieces.
     *
     * @param sz the square size of the board
     */
    public GameModel(int sz) {
        if (sz <= 1) throw new IllegalArgumentException("Game size must be > 1");
        this.gameSize = sz;
        whiteUF = new WhiteWeightedQuickUnionUF(sz*sz);
        blackUF = new BlackWeightedQuickUnionUF(sz*sz);
    }

    /**
     * Can a play be made at position row, col
     *
     * @param row the row in question
     * @param col the col in question
     * @return true if row, col is empty, false o.w.
     * @throws IllegalArgumentException for invalid row and col
     */
    public boolean canPlay(int row, int col) {
        if (row < 0 || row >= gameSize)
            throw new IllegalArgumentException("Selection out of bounds. Row must be >0 and < N-1");
        if (col < 0 || col >= gameSize)
            throw new IllegalArgumentException("Selection out of bounds, Column must be > 0 and < N-1");
        return (whiteUF.isEmpty(Helper.currentTile(row, col, gameSize)) && blackUF.isEmpty(Helper.currentTile(row, col, gameSize)));
    }

    /**
     * play a piece and report if the game is over (true) false, otherwise
     *
     * @param row the row where a piece is played
     * @param col the col where a piece is played
     * @param clr -1 for WHITE and 1 for BLACK
     * @return true if the game is over and false otherwise
     * @throws IllegalArgumentException for invalid row and col
     */
    public boolean makePlay(int row, int col, boolean clr) {
        if (!canPlay(row, col)) {
            throw new IllegalArgumentException("Choose another tile. This one is taken.");
        }
        if (clr == WHITE) {
            whiteUF.processTile(row, col, gameSize);
            return whiteUF.gameOver();
        }
        if (clr == BLACK) {
            blackUF.processTile(row, col, gameSize);
            return blackUF.gameOver();
        }
        return false;
    }

    /**
     * Return the score for white
     *
     * @return white score
     */
    public int whiteScore() { return whiteUF.numberOfIslands(); }

    /**
     * return the score for black
     *
     * @return black score
     */
    public int blackScore() { return blackUF.numberOfIslands(); }


}