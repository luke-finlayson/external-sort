import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CreateRuns 
{
    public static void main(String[] args) 
    {
        // Extract max heap size from args
        int maxHeap;
        try {
            maxHeap = Integer.parseInt(args[0]);
        }
        catch (Exception e) {
            System.err.println("Invalid arguments - using default max heap size of 31");
            maxHeap = 0;
        }

        // Create stream reader to read from standard input
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        MyMinHeap heap = new MyMinHeap(maxHeap);

        try
        {
            // Fill heap with initial data
            String line = reader.readLine();
            for (int i = 0; i < heap.capacity(); i++) {              
                if (line == null) {
                    break;
                }

                heap.insert(line);
                line = reader.readLine();
            }

            // Read from the rest of the input file
            while (heap.peek() != null) {
                // Output the smallest value in the minheap
                String previous = heap.replace(line);
                System.out.println(previous);

                if (smallest(line, previous)) {
                    heap.shrink();
                }

                if (line == null) {
                    heap.capacity--;
                }

                line = reader.readLine();

                if (heap.capacity() <= 0) {
                    heap.resetCapacity();
                    System.out.println();
                }
            }
        }
        catch (Exception e) 
        {
            System.out.println(heap);
            System.err.println(e);
        }
    }

    private static boolean smallest(String value1, String value2) {
        if (value1 == null) {
            return false;
        }
        if (value2 == null) {
            return true;
        }

        return value1.compareTo(value2) < 0;
    }
}
