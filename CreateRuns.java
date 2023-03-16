import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CreateRuns 
{
    public static void main(String[] args) 
    {
        // Extract max heap size from args
        int maxHeap = 31;
        try 
        {
            maxHeap = Integer.parseInt(args[0]);
        }
        catch (Exception e) 
        {
            System.err.println("Invalid arguments - using default max heap size of 31");
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

            int runs = 1;
            // Read from the rest of the input file
            while (heap.peek() != null) {
                // Output the smallest value in the minheap
                String previous = heap.peek();
                System.out.println(previous);

                if (line == null || Utils.smallest(line, previous).equals(line)) {
                    heap.shrink(line);
                }
                else {
                    heap.replace(line);
                }

                // If the usable heap has now been depleted - reset to use full heap again
                if (heap.capacity() <= 0) {
                    runs++;
                    heap.resetCapacity();
                }

                // Read in the next line
                line = reader.readLine();
            }

            System.err.println(runs);
        }
        catch (Exception e) 
        {
            System.out.println("Heap dump: " + heap);
            System.err.println("Create runs Error");
            System.err.println(e);
        }
    }
}
