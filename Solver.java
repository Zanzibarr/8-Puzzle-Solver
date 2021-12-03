import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Solver {

    private static String goalBoard;
    public static int n;

    public static class BoardComparator implements Comparator<Board> {
        public int compare(Board b1, Board b2) {
            return b1.fCost() - b2.fCost();
        }
    }

    public static String setNullBoard() {

        StringBuilder strBuild = new StringBuilder();

        for (int i = 0; i < n * n; i++) {
            strBuild.append(0);
            strBuild.append(" ");
        }

        return strBuild.toString();

    }

    public static void setGoalBoard() {

        StringBuilder strBuild = new StringBuilder();

        for (int i = 0; i < n * n - 1; i++) {
            strBuild.append(i + 1);
            strBuild.append(" ");
        }
        strBuild.append(0);
        strBuild.append(" ");

        goalBoard = strBuild.toString();

    }

    public static void main(String[] args) throws IOException {

        FileReader file = new FileReader("board.txt");
        BufferedReader fileReader = new BufferedReader(file);

        n = Integer.parseInt(fileReader.readLine());
        String rt = fileReader.readLine();

        fileReader.close();
        file.close();

        /**/long start = System.nanoTime();
        
        int moves = solve(rt);

        /**/long finish = System.nanoTime();
        /**/System.out.println((double)(finish - start) / 1000000000l+" seconds");

        System.out.println(moves+" moves");

    }

    public static int solve(String root) {

        Board board = new Board(root + " ");
        board.root();

        PriorityQueue<Board> nextBoards = new PriorityQueue<>(new BoardComparator());
        HashMap<String, String> visited = new HashMap<>();

        while (!board.toString().equals(goalBoard)) {

            Board[] children = board.children();

            int end = children.length;
            for (int i = 0; i < end; i++) {

                if (!visited.containsKey(children[i].toString()))
                    nextBoards.add(children[i]);

            }

            visited.put(board.toString(), board.father());
            board = nextBoards.poll();

        }

        visited.put(board.toString(), board.father());
        String outBuilder = board.toString();

        int steps = board.gCost();
        String[] output = new String[steps + 1];
        for (int i = steps; i >= 0; i--) {

            output[i] = outBuilder;
            outBuilder = visited.get(outBuilder);

        }

        for (int i = 0; i <= steps; i++)
            System.out.println(output[i]);

        return steps;

    }

}
