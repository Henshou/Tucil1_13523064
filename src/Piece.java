import java.util.ArrayList;

public class Piece {
    char alphabet;
    ArrayList<int[]> coordinates;

    public Piece(char alphabet, ArrayList<int[]> coordinates) {
        this.alphabet = alphabet;
        this.coordinates = new ArrayList<>();
        for (int[] point : coordinates) {
            this.coordinates.add(new int[]{point[0], point[1]});
        }
    }

    public void rotatePiece() {
        ArrayList<int[]> rotated = new ArrayList<>();
        for (int[] point : coordinates) {
            int x = point[0];
            int y = point[1];
            rotated.add(new int[]{-y, x});
        }
        this.coordinates = rotated;
        positivePiece();
    }

    public void mirrorPiece() {
        ArrayList<int[]> mirrored = new ArrayList<>();
        for (int[] point : coordinates) {
            int x = point[0];
            int y = point[1];
            mirrored.add(new int[]{-x, y});
        }
        this.coordinates = mirrored;
        positivePiece();
    }

    private void positivePiece() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        for (int[] coordinate : coordinates) {
            if (coordinate[0] < minX) minX = coordinate[0];
            if (coordinate[1] < minY) minY = coordinate[1];
        }

        ArrayList<int[]> positive = new ArrayList<>();
        for (int[] coordinate : coordinates) {
            positive.add(new int[]{coordinate[0] - minX, coordinate[1] - minY});
        }

        this.coordinates = positive;
    }
}