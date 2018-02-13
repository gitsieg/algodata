package kaos.conways;

import java.util.Arrays;

/**
 *
 * @author Rockass
 */
public class World {

    protected int rows, cols;
    protected Celle[][] prev;
    protected Celle[][] next;

    public World(int r, int c) {
        this.rows = r;
        this.cols = c;
        prev = new Celle[rows][cols];
        next = new Celle[rows][cols];
    }

    protected int getRows() {
        return rows;
    }

    protected int getCols() {
        return cols;
    }

    public void setNextGen() {
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                if (prev[i][j].isAlive()) {
                    if (prev[i][j].checkNeighbors() < 2) {
                        next[i][j].dies();
                    } else if (prev[i][j].checkNeighbors() == 2 || prev[i][j].checkNeighbors() == 3) {
                        next[i][j].lives();
                    } else if (prev[i][j].checkNeighbors() > 3) {
                        next[i][j].dies();
                    }
                } else if (prev[i][j].checkNeighbors() == 3) {
                    next[i][j].lives();
                }

            }
        }
        prev = next;
        next = new Celle[cols][rows];
        genNext();
    }

    public void genCellsInWorld() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                prev[i][j] = new Celle(this, i, j, false);
                next[i][j] = new Celle(this, i, j, false);
            }
        }
    }

    public void genNext() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                next[i][j] = new Celle(this, i, j, false);
            }
        }
    }

    public void clearNext() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                next[i][j].dies();
            }
        }
    }
}
