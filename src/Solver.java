import java.util.ArrayList;

public class Solver {
    private final int N, M;
    private char[][] board;
    private Piece[] pieces;
    private int iteration;

    public Solver (int N, int M, Piece[] pieces) {
        this.N = N;
        this.M = M;
        this.pieces = pieces;
        this.board = new char[N][M];
        this.iteration = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public int getIteration() {
        return iteration;
    }

    public char[][] getBoard() {
        char[][] copyBoard = new char[N][M];
        for (int i = 0; i < N; i++) {
            System.arraycopy(board[i], 0, copyBoard[i], 0, M);
        }
        return copyBoard;
    }

    public boolean solvePuzzle(int startX, int startY) {
//        System.out.println("\nTrying position " + startX + "," + startY);
//        printBoard();

        iteration++;
        boolean allPiecesPlaced = true;
        for (Piece piece : pieces) {
            if (piece != null) {
                allPiecesPlaced = false;
                break;
            }
        }

        if (allPiecesPlaced) {
            return isSolved();
        }
        if (isSolved()) return true;


        int nextX = startX, nextY = startY + 1;
        if (nextY >= M) {
            nextX = startX + 1;
            nextY = 0;
        }

        if (board[startX][startY] != ' ') {
            if (startX < N - 1 || nextY < M-1) {
                return solvePuzzle(nextX, nextY);
            } else {
                return isSolved();
            }
        }


        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == null) continue;

            Piece currentPiece = pieces[i];
            PieceList listPiece = new PieceList();
            listPiece.makePieceList(currentPiece);

            for (Piece[] pieceVariation : listPiece.pieceList) {
                for (Piece availPiece : pieceVariation) {
                    if (canPlace(availPiece, startX, startY)) {
                        placePiece(availPiece, startX, startY);
                        Piece temp = pieces[i];
                        pieces[i] = null;

                        if (startX < N - 1 || nextY < M) {
                            if (solvePuzzle(nextX, nextY)) {
                                return true;
                            }
                        } else {
                            if (isSolved()) {
                                return true;
                            }
                        }

//
                        removePiece(availPiece, startX, startY);
                        pieces[i] = temp;
//
                    }
                }
            }
        }

        return false;
    }

    private boolean canPlace (Piece piece, int startX, int startY) {
        for (int[] coordinate : piece.coordinates) {
            if (startX + coordinate[0] >= N || startY + coordinate[1] >= M || board[startX + coordinate[0]][startY + coordinate[1]] != ' ') return false;
        }
        return true;
    }

    private void placePiece(Piece piece, int startX, int startY) {
        for (int[] coordinate : piece.coordinates) {
            board[startX + coordinate[0]][startY + coordinate[1]] = piece.alphabet;
        }
    }

    private void removePiece (Piece piece, int startX, int startY) {
        for (int[] coordinate : piece.coordinates) {
            board[startX + coordinate[0]][startY + coordinate[1]] = ' ';
        }
    }

    private boolean isSolved() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}