public class MyMinHeap {
    public Node[] heapArray;
    /**
     * The next available position in the heap
     */
    private int next;
    /**
     * The maximum size of the heap
     */
    private int maxSize;
    private int capacity;

    /**
     * Implements a replaceable minheap
     * @param capacity The maximum size of the minheap (must be positive)
     */
    public MyMinHeap(int capacity) {
        // Validate capacity and initialise heap array
        this.maxSize = capacity + 1;
        this.capacity = maxSize;
        // Initialise starting position
        next = 1;

        this.heapArray = new Node[this.capacity];
    }

    /**
     * Inserts a new value into the minheap
     * @param value The new value to insert into the heap
     */
    public void insert(String value) {
        insert(value, -1);
    };

    public void insert(String value, int key) {
        // Don't insert if heap is full
        if (next > capacity) {
            System.err.println("Insert Error: Full");
            return;
        }

        heapArray[next] = new Node(value, key);
        upheap(next);
        next++;
    }

    public void replace(String value) {
        replace(value, -1);
    }

    public void replace(String value, int key) {
        heapArray[1] = new Node(value, key);
        downheap();
    }

    /**
     * Get the smallest value in the heap
     * @return The smallest value in the heap
     */
    public Node peek() { return heapArray[1]; }

    public void shrink(String value) {
        shrink(value, -1);
    }

    public void shrink(String value, int key) {
        heapArray[1] = new Node(value, key);
        swap(1, next - 1);
        
        // Shrink the maximum capacity to "ignore" the smaller numbers
        capacity--;
        
        if (next > capacity) {
            next = capacity;
        }
        downheap(1);
    }

    public void resetCapacity() {
        capacity = this.maxSize;
        next = capacity;
        reheap();
    }

    /**
     * Loads multiple values at once
     * @param values
     */
    public boolean load(String[] values) {
        if (values.length > capacity - next) {
            return false;
        }

        for (int i = 0; i < values.length; i++) {
            insert(values[i], i);
        }
        
        return true;
    }

    public void downheap() {
        downheap(1);
    }

    public void downheap(int root) {
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
            downheap(smallest);
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

    /**
     * Reorders the heap back into order
     */
    private void reheap() {
        Node[] temp = heapArray;
        clearHeap();

        for (int i = 1; i < heapArray.length; i++) {
            insert(temp[i].toString(), temp[i].key);
        }
    }

    private void clearHeap() {
        heapArray = new Node[maxSize];
        next = 1;
        capacity = maxSize;
    }

    private void swap(int i, int j) {
        Node temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    /**
     * Returns true if i is smaller than j
     * @param i The index of a value in the heap array
     * @param j The index of a value in the heap array
     */
    private boolean smallest(int i, int j) {
        String value1 = value(i);
        String value2 = value(j);

        try {
            return Utils.smallest(value1, value2).equals(value1);
        }
        catch (Exception e) {
            return true;
        }
    }

    public String toString() {
        String output = "[ ";
        for (int i = 1; i < maxSize; i++) {
            if (capacity != maxSize && i == capacity) {
                output += "| ";
            }
            
            output += value(i);

            if (i != maxSize - 1) {
                output += ", ";
            }
        }
        output += " ]";

        return output;
    }

    private String value(int i) {
        Node node = heapArray[i];

        if (node == null) {
            return "";
        }
        
        return node.toString();
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

    public int length() {
        return next + 1;
    }

    public int capacity() {
        return capacity - 1;
    }
}
