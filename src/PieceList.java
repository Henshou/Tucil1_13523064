import java.util.ArrayList;

public class PieceList {
    ArrayList<Piece[]> pieceList;

    public PieceList() {
        this.pieceList = new ArrayList<>();
    }

    public Piece[] getVariation(Piece piece) {
        Piece[] variationArray = new Piece[8];

        Piece temp = new Piece(piece.alphabet, copyCoordinates(piece.coordinates));

        for (int i = 0; i < 4; i++) {
            variationArray[i] = new Piece(temp.alphabet, copyCoordinates(temp.coordinates));
            temp.rotatePiece();
        }

        temp.mirrorPiece();
        for (int i = 4; i < 8; i++) {
            variationArray[i] = new Piece(temp.alphabet, copyCoordinates(temp.coordinates));
            temp.rotatePiece();
        }

        return variationArray;
    }

    public void makePieceList(Piece piece) {
        pieceList.add(getVariation(piece));
    }

    private ArrayList<int[]> copyCoordinates(ArrayList<int[]> original) {
        ArrayList<int[]> copiedList = new ArrayList<>();
        for (int[] point : original) {
            copiedList.add(new int[]{point[0], point[1]});
        }
        return copiedList;
    }
}