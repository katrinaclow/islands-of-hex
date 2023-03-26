package islands.backend;

/**
 * Class to help translate from 2D array indexes to 1D array index
 */
public class Helper {
    /**
     * Takes in the gamesize, row and column of the tile to be played, and translates it into it's index for a flat
     * array
     *
     * @param row the row the tile is in
     * @param col the column the tile is in
     * @param gameSize the square size of the game
     * @return index of the tile piece as an integer
     */
    public static int currentTile(int row, int col, int gameSize) {
        return row*gameSize+col;
    }

    /**
     * Takes in the gamesize, row and column of the tile to be played, and outputs the 6 possible array indexes that
     * would be touching the hexagon piece.
     *
     * @param row the row the tile is in
     * @param col the column the tile is in
     * @param gameSize the square size of the game
     * @return an integer array with the 6 indexes of the potential neighbouring tiles of the current tile played.
     */
    public static int[] tilesToSearch(int row, int col, int gameSize) {
        int currentTile = row*gameSize+col;
        return new int[]{currentTile+1, currentTile-1, currentTile+gameSize, currentTile-gameSize, currentTile+gameSize+1, currentTile-gameSize-1};
    }

}