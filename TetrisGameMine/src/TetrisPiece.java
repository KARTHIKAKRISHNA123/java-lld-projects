import java.util.*;

public class TetrisPiece {
    private static final char FILLED_CELL = '#';
    private static final char EMPTY_CELL = '.';

    private static final char[][][] TETROMINOS = {
            //I Piece (long Straight Piece)
            {
                    {FILLED_CELL, FILLED_CELL, FILLED_CELL, FILLED_CELL}
            },
            //0 Piece (square piece)
            {
                    {FILLED_CELL, FILLED_CELL},
                    {FILLED_CELL, FILLED_CELL}
            },
            //T Piece (T Shaped Piece)
            {
                    {EMPTY_CELL, FILLED_CELL, EMPTY_CELL},
                    {FILLED_CELL, FILLED_CELL, FILLED_CELL}
            },
            //S Piece
            {
                    {EMPTY_CELL, FILLED_CELL, FILLED_CELL},
                    {FILLED_CELL, FILLED_CELL, EMPTY_CELL}
            },
            //Z Piece
            {
                    {FILLED_CELL, FILLED_CELL, EMPTY_CELL},
                    {EMPTY_CELL, FILLED_CELL, FILLED_CELL}
            },
            //J Piece
            {
                    {FILLED_CELL, EMPTY_CELL, EMPTY_CELL},
                    {FILLED_CELL, FILLED_CELL, FILLED_CELL}
            },
            //L Piece
            {
                    {EMPTY_CELL, EMPTY_CELL, FILLED_CELL},
                    {FILLED_CELL, FILLED_CELL, FILLED_CELL}
            }


    };

    private char[][] shape;

    private Random random;

    public TetrisPiece() {
        random = new Random();
        shape = TETROMINOS[random.nextInt(TETROMINOS.length)];
    }

    public char[][] getShape() {
        return shape;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length;
    }

    public char[][] getRotatedShape() {
        int width = getWidth();
        int height = getHeight();

        char[][] rotated = new char[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                rotated[j][height - 1 -i] = shape[i][j];
            }
        }
        return rotated;
    }

    public void rotate() {
        shape = getRotatedShape();

    }
}