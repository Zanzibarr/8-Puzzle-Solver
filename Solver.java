import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Class Solver
 * @author Zanella Matteo   20000139    matteo.zanella.3@studenti.unipd.it
 */
public class Solver {
    
    public static int n;
    static final PriorityQueue<Board> nextBoards = new PriorityQueue<>(new BoardComparator());
    static final HashMap<String, String> visited = new HashMap<>();

    public static class BoardComparator implements Comparator<Board> {
        public int compare(Board b1, Board b2) {
            return b1.fCost() - b2.fCost();
        }
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Missing Input File.");
            System.exit(1);
        }

        FileReader file = new FileReader(args[0]);
        BufferedReader fileReader = new BufferedReader(file);

        n = Integer.parseInt(fileReader.readLine());
        String rt = fileReader.readLine();

        fileReader.close();
        file.close();

        Board board = new Board(rt + " ");
        Board[] children;

        int index;
        byte end;

        while (board.hCost() != 0) {

            children = board.nearby();

            end = (byte)children.length;
            for (index = 0; index < end; index++) {

                if (children[index] == null) break;

                if (!visited.containsKey(children[index].toString()))
                    nextBoards.add(children[index]);

            }

            if (!visited.containsKey(board.toString()))
                visited.put(board.toString(), board.father());
            
            board = nextBoards.poll();

        }

        visited.put(board.toString(), board.father());

        String outBuilder = board.toString();
        int steps = board.gCost();

        String[] output = new String[steps+1];

        for (int i = steps; i >= 0; i--) {
            output[i] = outBuilder;
            outBuilder = visited.get(outBuilder);
        }

        System.out.println(steps);

        for (int i = 0; i < steps + 1; i++) System.out.println(output[i]);

    }

}