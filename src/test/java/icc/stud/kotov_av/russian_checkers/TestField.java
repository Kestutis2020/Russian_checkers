package icc.stud.kotov_av.russian_checkers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestField {

    @Test
    public void testField() {
        Field tFd = new Field();

        try {
            tFd._readCell(-1, 0);
        } catch (Exception ex) {
            assertEquals("out of bounds", ex.getMessage());
        }

        try {
            assertEquals(Cell.NOT_AVAILABLE, tFd._readCell(0, 0));
            assertEquals(Cell.CHECKER_BLACK, tFd._readCell(1, 0));
            assertEquals(Cell.EMPTY, tFd._readCell(3, 0));
            assertEquals(Cell.CHECKER_WHITE, tFd._readCell(5, 0));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWrite() {
        Field tFd = new Field();

        assertEquals(-3, tFd._writeCell(0, 1, Cell.EMPTY));

        assertEquals(-2, tFd._writeCell(0, 0, Cell.CHECKER_BLACK));
        assertEquals(-2, tFd._writeCell(1, 0, Cell.NOT_AVAILABLE));
        
        assertEquals(-1, tFd._writeCell(-1, 1, Cell.EMPTY));
        assertEquals(-1, tFd._writeCell(1, -1, Cell.EMPTY));
        
        assertEquals(0, tFd._writeCell(3, 0, Cell.CHECKER_WHITE));
        assertEquals(0, tFd._writeCell(4, 1, Cell.CHECKER_BLACK));
    }
}