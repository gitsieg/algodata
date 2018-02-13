package kaos.conways;

/**
 *
 * @author Rockass
 */
class Celle {

    private boolean alive;
    private int row, col;
    private World w;

    public Celle(World w, int row, int col, boolean alive) {
        this.w = w;
        this.row = row;
        this.col = col;
        this.alive = alive;
    }

    public boolean isAlive() {
        return this.alive;
    }

    
    // Må gjøre noe arbeid her.
    public void setNextGeneration() {
        int living = checkNeighbors();
        if (living < 2) {
        } else if (living >= 2 && living < 4 && alive) {
        } else if (living > 3) {
            alive = false;
        } else if (living == 3) {
            alive = true;
        }
    }
    
    public void lives() {
        alive = true;
    }
    
    public void dies() {
        alive = false;
    }
    

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Alle celler sjekker naboer i denne rekkefølgen - Øst - Sør-Øst - Sør -
     * Sør-Vest - Vest - Nord-Vest - Nord - Nord-Øst Oppsett skal være korrekt,
     * og riktige kall blir utført på rett test
     *
     * @return living
     */
    public int checkNeighbors() {
        int living = 0;
        // Tester på posisjoner som må spesialbehandles for å gjøre verden til en "globe".
        if (row == 0 && col == 0) {
            living = topLeftNeighbors();
        } else if (row == 0 && col == w.getCols() - 1) {
            living = topRightNeighbors();
        } else if (row == w.getRows() - 1 && col == 0) {
            living = botLeftNeighbors();

        } else if (row == w.getRows() - 1 && col == w.getCols() - 1) {
            living = botRightNeighbors();

        } else if (row == 0) {
            living = topRowNeighbors();

        } else if (row == w.getRows() - 1) {
            living = botRowNeighbors();

        } else if (col == 0) {
            living = leftColNeighbors();

        } else if (col == w.getCols() - 1) {
            living = rightColNeighbors();

        } else {
            living = centeredWorldNeighbors();
        }

        return living;
    }

    // Skal være done
    private int centeredWorldNeighbors() {
        int living = 0;
        if (w.prev[row][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col + 1].isAlive()) {
            living++;
        }
        return living;
    }

    // Skal være korrekt
    private int topRowNeighbors() {
        int living = 0;
        if (w.prev[row][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][col + 1].isAlive()) {
            living++;
        }
        return living;
    }

    // Skal være korrekt
    private int botRowNeighbors() {
        int living = 0;
        if (w.prev[row][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[0][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[0][col].isAlive()) {
            living++;
        }
        if (w.prev[0][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col + 1].isAlive()) {
            living++;
        }
        return living;
    }

    // Skal være korrekt
    private int leftColNeighbors() {
        int living = 0;
        if (w.prev[row][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col + 1].isAlive()) {
            living++;
        }
        return living;
    }

    // Skal være korrekt
    private int rightColNeighbors() {
        int living = 0;
        if (w.prev[row][0].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][0].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][0].isAlive()) {
            living++;
        }
        return living;
    }

    // Skal være korrekt
    private int topLeftNeighbors() {
        int living = 0;
        if (w.prev[row][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][col + 1].isAlive()) {
            living++;
        }
        return living;
    }

    // Skal være korrekt
    private int topRightNeighbors() {
        int living = 0;
        if (w.prev[row][0].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][0].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row + 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][0].isAlive()) {
            living++;
        }
        return living;
    }

    // Skal være korrekt
    private int botLeftNeighbors() {
        int living = 0;
        if (w.prev[row][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[0][col + 1].isAlive()) {
            living++;
        }
        if (w.prev[0][col].isAlive()) {
            living++;
        }
        if (w.prev[0][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[w.getRows() - 1][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][w.getCols() - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col + 1].isAlive()) {
            living++;
        }
        return living;
    }

    private int botRightNeighbors() {
        int living = 0;
        if (w.prev[row][0].isAlive()) {
            living++;
        }
        if (w.prev[0][0].isAlive()) {
            living++;
        }
        if (w.prev[0][col].isAlive()) {
            living++;
        }
        if (w.prev[0][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col - 1].isAlive()) {
            living++;
        }
        if (w.prev[row - 1][col].isAlive()) {
            living++;
        }
        if (w.prev[row-1][0].isAlive()) {
            living++;
        }
        return living;
    }
}
