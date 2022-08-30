package pl.coderslab.charity.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnDisplayTest {

    @Test
    void fiveElementsIntoTwoColumns() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        ColumnDisplay<Integer> columnDisplay = new ColumnDisplay<>(list, 2);
        List<List<Integer>> expected = List.of(List.of(1, 2), List.of(3, 4), List.of(5));
        assertEquals(expected, columnDisplay.getRows());
    }

    @Test
    void fourElementsIntoOneColumn() {
        List<Character> list = List.of('A', 'B', 'C', 'D');
        ColumnDisplay<Character> columnDisplay = new ColumnDisplay<>(list, 1);
        List<List<Character>> expected = List.of(List.of('A'), List.of('B'), List.of('C'), List.of('D'));
        assertEquals(expected, columnDisplay.getRows());
    }

    @Test
    void shouldThrowException_ForColumnsPerRowLessThanOne() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        assertThrows(IllegalArgumentException.class, () -> new ColumnDisplay<Integer>(list, 0));
    }
}