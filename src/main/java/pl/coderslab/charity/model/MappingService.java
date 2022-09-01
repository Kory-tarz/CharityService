package pl.coderslab.charity.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class MappingService {

    public <T> List<List<T>> mapToTable(Collection<? extends T> collection, int columnsPerRow) {
        List<List<T>> table = new ArrayList<>(Math.ceilDiv(collection.size(), columnsPerRow));
        Iterator<? extends T> iterator = collection.iterator();
        while (iterator.hasNext()){
            table.add(createNextRow(iterator, columnsPerRow));
        }
        return table;
    }
    
    private <T> List<T> createNextRow(Iterator<? extends T> it, int elementsToAdd){
        List<T> elements = new ArrayList<>();
        while (it.hasNext() && elementsToAdd > 0){
            elements.add(it.next());
            elementsToAdd--;
        }
        return elements;
    }
}
