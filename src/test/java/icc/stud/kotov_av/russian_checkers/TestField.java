package icc.stud.kotov_av.russian_checkers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestField {

    @Test
    public void testWrite() {
        Field tFd = new Field();

        assertEquals(-2, tFd.writeCell(0, 0, Cell.EMPTY));
        assertEquals(0, tFd.writeCell(0, 1, Cell.CHECKER_BLACK));
    }
}