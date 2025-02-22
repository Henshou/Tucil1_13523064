import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class InOut {
    public static void readInput(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]);
            int M = Integer.parseInt(firstLine[1]);
            int P = Integer.parseInt(firstLine[2]);

            br.readLine();

            List<char[][]> shapes = new ArrayList<>();
            char currAlph = '\0';
            List<String> currLine = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                char firstChar = line.trim().charAt(0);
                if (Character.isLetter(firstChar) && firstChar != currAlph) {
                    if (!currLine.isEmpty()) {
                        char[][] shapeMatrix = new char[currLine.size()][];
                        for (int i = 0; i < currLine.size(); i++) {
                            shapeMatrix[i] = currLine.get(i).toCharArray();
                        }
                        shapes.add(shapeMatrix);
                        currLine.clear();
                    }
                    currAlph = firstChar;
                }
                currLine.add(line);
            }

            if (!currLine.isEmpty()) {
                char[][] shapeMatrix = new char[currLine.size()][];
                for (int i = 0; i < currLine.size(); i++) {
                    shapeMatrix[i] = currLine.get(i).toCharArray();
                }

                shapes.add(shapeMatrix);
            }

            Piece[] pieces = new Piece[shapes.size()];
            for (int i = 0; i < shapes.size(); i++) {
                pieces[i] = matrixToCoordinates(shapes.get(i));
            }

            long start = System.currentTimeMillis(); // Start timing
            Solver solver = new Solver(N, M, pieces);
            boolean solvePuzzle = solver.solvePuzzle(0,0);
            long end = System.currentTimeMillis();
            long timeTaken = end - start;

            if (solvePuzzle) {
                solver.printBoard();
                Main.setResults(solver.getBoard(), solver.getIteration(), timeTaken);
            } else {
                char[][] emptyBoard = new char[N][M];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        emptyBoard[i][j] = ' ';
                    }
                }
                Main.setResults(emptyBoard, solver.getIteration(), timeTaken);
                System.out.println("No Solution!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Piece matrixToCoordinates(char[][] shape) {
        char alphabet = '\0';
        ArrayList<int[]> coordinates = new ArrayList<>();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (Character.isLetter(shape[i][j])) {
                    if (alphabet == '\0') {
                        alphabet = shape[i][j];
                    }
                    coordinates.add(new int[]{i, j});
                }
            }
        }

        return new Piece(alphabet, coordinates);
    }

    public static void saveResult(String filename, char[][] board, int iterations, long timeTaken) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (char[] row : board) {
                for (char cell : row) {
                    writer.write(cell + " ");
                }
                writer.newLine();
            }
            writer.write("\nWaktu pencarian: " + timeTaken + " ms\n");
            writer.write("\nBanyak kasus yang ditinjau: " + iterations + "\n");
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }

    public static void saveAsImage(JPanel boardPanel, String filePath) {
        int width = boardPanel.getWidth();
        int height = boardPanel.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = image.createGraphics();
        boardPanel.paint(graph);
        graph.dispose();

        try {
            File file = new File(filePath);
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving image: " + e.getMessage());
        }
    }
}