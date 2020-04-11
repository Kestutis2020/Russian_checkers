package icc.stud.kotov_av.russian_checkers;

public class Field {
    private Cell[][] field;
    private int size = 8;

    public Field() {
        field = new Cell[size][size];

        boolean isAvailable = false;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < size; y++) {
                if (isAvailable) {
                    field[x][y] = Cell.CHECKER_BLACK;
                } else {
                    field[x][y] = Cell.NOT_AVAILABLE;
                }

                if (y != 7)
                    isAvailable = !isAvailable;
            }
        }

        for (int x = 3; x < 5; x++) {
            for (int y = 0; y < size; y++) {
                if (isAvailable) {
                    field[x][y] = Cell.EMPTY;
                } else {
                    field[x][y] = Cell.NOT_AVAILABLE;
                }

                if (y != 7)
                    isAvailable = !isAvailable;
            }
        }

        for (int x = 5; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (isAvailable) {
                    field[x][y] = Cell.CHECKER_WHITE;
                } else {
                    field[x][y] = Cell.NOT_AVAILABLE;
                }

                if (y != 7)
                    isAvailable = !isAvailable;
            }
        }
    }

    public Cell _readCell(int x, int y) throws Exception {
        if ((x < 0 || x >= size) || (y < 0 || y >= size))
            throw new Exception("out of bounds");

        return field[x][y];
    }

    public int _writeCell(int x, int y, Cell type) {
        if ((x < 0 || x >= size) || (y < 0 || y >= size))
            return -1;

        if (field[x][y] == Cell.NOT_AVAILABLE || type == Cell.NOT_AVAILABLE)
            return -2;

        if (field[x][y] == Cell.EMPTY) {
            field[x][y] = type;
            return 0;
        } else
            return -3;
    }

    public int _clearCell(int x, int y) {
        if ((x < 0 || x >= size) || (y < 0 || y >= size))
            return -1;

        if (field[x][y] != Cell.NOT_AVAILABLE) {
            field[x][y] = Cell.EMPTY;
            return 0;
        } else
            return -2;
    }
}