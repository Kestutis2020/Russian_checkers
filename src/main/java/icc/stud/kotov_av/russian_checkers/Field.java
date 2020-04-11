package icc.stud.kotov_av.russian_checkers;

public class Field {
    private Cell[][] field;
    private int size = 8;
    
    public Field() {
        field = new Cell[size][size];
    }

    public Cell read(int x, int y) {
        return field[x][y];
    }

    public int write(int x, int y, Cell type) {
        if ((x < 0 || x >= size) || (y < 0 || y >= size)) return -1;

        if (field[x][y] == Cell.EMPTY && field[x][y] != Cell.NOT_AVAILABLE) {
            field[x][y] = type;
            return 0;
        } else return -2;
    }

    public int clear(int x, int y) {
	    if ((x < 0 || x >= size) || (y < 0 || y >= size)) return -1;

        if (field[x][y] != Cell.NOT_AVAILABLE) {
            field[x][y] = Cell.EMPTY;
            return 0;
        } else return -2;
    }
}