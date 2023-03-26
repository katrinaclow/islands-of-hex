package islands.backend;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  The {@code WeightedQuickUnionPathCompressionUF} class represents a
 *  union–find data structure.
 *  It supports the <em>union</em> and <em>find</em> operations, along with
 *  methods for determining whether two sites are in the same component
 *  and the total number of components.
 *  <p>
 *  This implementation uses weighted quick union (by size) with full path compression.
 *  The constructor takes &Theta;(<em>n</em>) time, where
 *  <em>n</em> is the number of elements.
 *  The <em>union</em> and <em>find</em> operations take
 *  &Theta;(log <em>n</em>) time in the worst case.
 *  The <em>count</em> operation takes &Theta;(1) time.
 *  Moreover, starting from an empty data structure with <em>n</em> sites,
 *  any intermixed sequence of <em>m</em> <em>union</em> and <em>find</em>
 *  operations takes <em>O</em>(<em>m</em> &alpha;(<em>n</em>)) time,
 *  where &alpha;(<em>n</em>) is the inverse of
 *  <a href = "https://en.wikipedia.org/wiki/Ackermann_function#Inverse">Ackermann's function</a>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class WhiteWeightedQuickUnionUF {
    public static final int EMPTY = -1;
    private final int[] parent; //parent[i] = parent of i
    private final int[] size; //size[i] = number of elements in subtree rooted at i
    private int islands; //number of islands
    ArrayList<Integer> top = new ArrayList<>(); // to hold the parents of tiles in top row
    ArrayList<Integer> bottom = new ArrayList<>(); // to hold the parents of tiles in bottom row

    /**
     * Initializes an empty union-find data structure with n elements 0 through n-1.
     * Initially, each element is in its own set.
     *
     * @param  n the number of elements
     * @throws IllegalArgumentException if n < 0
     */
    public WhiteWeightedQuickUnionUF(int n) {
        islands = 0;
        parent = new int[n];
        size = new int[n];
        Arrays.fill(size, 1);
        Arrays.fill(parent, -1);
    }

    /**
     * Checks whether the index where the tile would like to be played is empty or not.
     * @param tile the index of the tile to be played in the parent array.
     * @return true if the spot is empty, false if not
     */
    public boolean isEmpty(int tile) {
        validate(tile);
        return parent[tile] == EMPTY;
    }

    /**
     * Returns the number of islands.
     *
     * @return the number of islands (between 1 and n)
     */
    public int numberOfIslands() {
        return islands;
    }

    /**
     * Returns the canonical element of the set containing element p
     *
     * @param  p an element
     * @return the canonical element of the set containing p
     * @throws IllegalArgumentException unless 0 <= p < n
     */
    public int find(int p) {
        validate(p);
        int root = p;
        while (root != parent[root])
            root = parent[root];
        while (p != root) {
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return root;
    }

    /**
     * Checks whether any element in the top row is connected to any element in the bottom row by using the list of
     * parents stored in the ArrayLists, seeing if any match between the two.
     *
     * @return true if the top and bottom rows connect, false if not
     */
    public boolean gameOver() {
        if (top.size() > 0 && bottom.size() > 0) {
            for (Integer n : top) {
                if (bottom.contains(n)) return true;
            }
        }
        return false;
    }

    /**
     * validates that p is an existing tile on the board.
     *
     * @param p tile to be validated.
     * @throws IllegalArgumentException if tile is invalid.
     */
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    /**
     * Merges the set containing element p with the set containing element q.
     *
     * @param  p one element
     * @param  q the other element
     * @throws IllegalArgumentException unless both 0 <= p < n and 0 <= q < n
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        islands--;
    }

    /**
     * Processes tile that is being played, checking its neighbouring tiles and preforming the union
     * method if tiles are of the same colour, while keeping track of the player's islands.
     *
     * @param row the row the tile is in
     * @param col the column the tile is in
     * @param sz the square size of the game
     */
    public void processTile(int row, int col, int sz) {
        islands++;
        int currentTile = Helper.currentTile(row, col, sz);
        int[] tilesToSearch = Helper.tilesToSearch(row, col, sz);
        parent[currentTile] = currentTile;
        for (int neighbourTile : tilesToSearch) {
            if (neighbourTile >= 0 && neighbourTile < (parent.length) && !isEmpty(neighbourTile)) {
                union(neighbourTile, currentTile);
            }
        }
        // if tile is in top or bottom row, this adds parent of the tile after processing to corresponding ArrayList
        if (row == 0 && !top.contains(parent[currentTile])) top.add(parent[currentTile]);
        if (row == sz-1 && !bottom.contains(parent[currentTile])) bottom.add(parent[currentTile]);
    }
}
// Copyright © 2000–2022, Robert Sedgewick and Kevin Wayne.
// Last updated: Sat Nov 26 14:06:16 EST 2022.