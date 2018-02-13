package kaos.conways;

/**
 *
 * @author Rockass
 */
public class ConwaysGame {

    public static void main(String[] args) {
        ConwaysGame cg = new ConwaysGame();
    }
    private World w;

    public ConwaysGame() {
        generateWorld();
        w.genCellsInWorld();
        printLastGeneration();
        setGlider();
//        setExploder();  // Shit's not working properly

        for (int i = 0; i < 50; i++) {
            printLastGeneration();
            w.setNextGen();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("hei");
            }
        }
    }

    public void generateWorld() {
        w = new World(11, 11);
    }

    public void setGlider() {
        w.prev[1][2].lives();
        w.prev[2][3].lives();
        w.prev[3][3].lives();
        w.prev[3][2].lives();
        w.prev[3][1].lives();
    }

    public void setExploder() {
        w.prev[4][5].lives();
        w.prev[5][4].lives();
        w.prev[5][5].lives();
        w.prev[5][6].lives();
        w.prev[6][4].lives();
        w.prev[6][6].lives();
        w.prev[7][5].lives();
    }

    public void printLastGeneration() {
        String ut = "";
        for (int i = 0; i < w.getRows(); i++) {
            for (int j = 0; j < w.getCols(); j++) {
                if (w.prev[i][j].isAlive()) {
                    ut += "X ";
                } else {
                    ut += "- ";
                }
            }
            ut += "\n";
        }

        ut += "\n||||||||||||||||||||||||||||||||||||||||||||||||";
        System.out.println(ut);
    }
}
