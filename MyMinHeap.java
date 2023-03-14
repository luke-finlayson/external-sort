public class MyMinHeap {
    public final String[] heapArray;
    /**
     * The next available position in the heap
     */
    private int next;
    /**
     * The maximum size of the heap
     */
    public int maxSize;
    public int capacity;

    /**
     * Implements a replaceable minheap
     * @param capacity The maximum size of the minheap (must be positive)
     */
    public MyMinHeap(int capacity) {
        // Validate capacity and initialise heap array
        this.maxSize = (capacity > 0) ? capacity + 1 : 31 + 1;
        this.capacity = maxSize;
        // Initialise starting position
        next = 1;

        this.heapArray = new String[this.capacity];
    }

    /**
     * Inserts a new value into the minheap
     * @param value The new value to insert into the heap
     */
    public void insert(String value) {
        // Don't insert if heap is full
        if (next > capacity) {
            System.err.println("Insert Error: Full");
            return;
        }

        heapArray[next] = value;
        upheap(next);
        next++;
    };

    /**
     * Removes the smallest value from the head of the heap
     */
    public void remove() {
        heapArray[1] = heapArray[next - 1];
        heapArray[next - 1] = null;
        reheap(1);
        next--;
    }

    public String toString() {
        String output = "[ ";
        for (int i = 1; i < maxSize; i++) {
            if (capacity != maxSize && i == capacity) {
                output += "| ";
            }
            
            output += heapArray[i];

            if (i != maxSize - 1) {
                output += ", ";
            }
        }
        output += " ]";

        return output;
    }

    public String replace(String value) {
        String previousValue = heapArray[1];
        
        heapArray[1] = value;
        reheap(1);
        
        return previousValue;
    }

    public int length() {
        return next + 1;
    }
    public int capacity() {
        return capacity - 1;
    }

    /**
     * Get the smallest value in the heap
     * @return The smallest value in the heap
     */
    public String peek() { return heapArray[1]; }

    public void shrink() {
        // Move the smallest value to the end of the heap
        swap(1, next - 1);
        // Shrink the maximum capacity to "ignore" the smaller numbers
        capacity--;
        
        if (next > capacity) {
            next = capacity;
        }

        reheap(1);
    }

    public void resetCapacity() {
        capacity = this.maxSize;
        next = capacity;
        reheap(1);
    }

    public void reheap(int root) {
        int smallest = root;

        int left = left(root);
        int right = right(root);

        // Check if any children are smaller than the current root
        if (left < next && smallest(left, smallest)) {
            smallest = left;
        }
        if (right < next && smallest(right, smallest)) {
            smallest = right;
        }

        // Swap parent with the smallest child and reheap on the child leaf
        if (smallest != root) {
            swap(root, smallest);
            reheap(smallest);
        }
    }

    private void upheap(int child) {
        while (child > 1) {
            int parent = parent(child);

            if (smallest(child, parent)) {
                swap(child, parent);
            }
            
            child = parent;
        }
    }

    private void swap(int i, int j) {
        String temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    /**
     * Returns true if i is smaller than j
     * @param i The index of a value in the heap array
     * @param j The index of a value in the heap array
     */
    private boolean smallest(int i, int j) {
        String value1 = heapArray[i];
        String value2 = heapArray[j];

        if (value1 == null) {
            return false;
        }
        if (value2 == null) {
            return true;
        }

        return value1.compareTo(value2) < 0;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return (2 * i) + 1;
    }

}
