package pl.coderslab.charity.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ColumnDisplay<T> {

    private List<List<T>> rows;

    public ColumnDisplay(Collection<? extends T> collection, int columnsPerRow) {
        if(columnsPerRow <= 0) throw new IllegalArgumentException("ColumnDisplay Requires at least one column per row");
        rows = new ArrayList<>(Math.ceilDiv(collection.size(), columnsPerRow));
        Iterator<? extends T> iterator = collection.iterator();
        while (iterator.hasNext()){
            rows.add(addRow(iterator, columnsPerRow));
        }
    }

    private List<T> addRow(Iterator<? extends T> it, int columns){
        List<T> columnElements = new ArrayList<>();
        while (it.hasNext() && columns > 0){
            columnElements.add(it.next());
            columns--;
        }
        return columnElements;
    }

    public List<List<T>> getRows() {
        return rows;
    }
}
