
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @param <V>   {@inheritDoc}
 * @param <Key> {@inheritDoc}
 */
public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {
    /**
     * {@inheritDoc}
     */

    private ArrayList<Entry<Key, V>> minHeap;
    private HashMap<V, Integer> mapping;

    public BinaryMinHeapImpl() {
        minHeap = new ArrayList<Entry<Key, V>>();
        mapping = new HashMap<V, Integer>();
    }

    @Override
    public int size() {
        return minHeap.size();
    }

    @Override
    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return mapping.containsKey(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        if (key == null || containsValue(value)) {
            throw new IllegalArgumentException();
        }
        minHeap.add(new Entry<Key, V>(key, value));
        mapping.put(value, minHeap.size() - 1);
        int count = minHeap.size() - 1;
        while (count > 0 && minHeap.get(count).key.compareTo(minHeap.get((count) / 2).key) < 0) {
            Entry<Key, V> parentEntry = minHeap.get(count / 2);
            minHeap.set(count / 2, minHeap.get(count));
            mapping.replace(minHeap.get(count / 2).value, count / 2);
            minHeap.set(count, parentEntry);
            mapping.replace(parentEntry.value, count);
            count = count / 2;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!containsValue(value)) {
            throw new NoSuchElementException();
        }
        if (newKey == null || (newKey.compareTo(minHeap.get(mapping.get(value)).key) > 0)) {
            throw new IllegalArgumentException();
        }
        minHeap.set(mapping.get(value), new Entry<Key, V>(newKey, value));
        int count = mapping.get(value);
        while (count > 0 && (minHeap.get(count / 2).key).compareTo(minHeap.get(count).key) > 0) {
            Entry<Key, V> parentEntry = minHeap.get(count / 2);
            minHeap.set(count / 2, minHeap.get(count));
            mapping.replace(minHeap.get(count / 2).value, count / 2);
            minHeap.set(count, parentEntry);
            mapping.replace(parentEntry.value, count);
            count = count / 2;
        }
        {

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return minHeap.get(0);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Entry<Key, V> minEntry = minHeap.get(0);
            minHeap.set(0, minHeap.get(minHeap.size() - 1));
            minHeap.remove(minHeap.size() - 1);
            mapping.remove(minEntry.value);
            minHeapify(0);
            return minEntry;
        }
    }

    void minHeapify(int index) {
        if (minHeap.size() == 1) {
            mapping.replace(minHeap.get(0).value, 0);
        }
        int l = 2 * index;
        int r = 2 * index + 1;

        int smallest = index;

        if (l < minHeap.size() && minHeap.get(l).key.compareTo(minHeap.get(index).key) < 0) {
            smallest = l;
        }
        if (r < minHeap.size() && minHeap.get(r).key.compareTo(minHeap.get(smallest).key) < 0) {
            smallest = r;
        }

        if (smallest != index) {
            Entry<Key, V> indexEntry = minHeap.get(index);
            minHeap.set(index, minHeap.get(smallest));
            mapping.replace(minHeap.get(index).value, index);
            minHeap.set(smallest, indexEntry);
            mapping.replace(minHeap.get(smallest).value, smallest);
            minHeapify(smallest);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        HashSet<V> values = new HashSet<V>();
        for (int i = 0; i < minHeap.size(); i++) {
            values.add(minHeap.get(i).value);
        }
        return values;
    }
}