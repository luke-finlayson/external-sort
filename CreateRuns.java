import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Luke Finlayson - 1557835
 * 
 * Reads lines of text in from standard input, 
 * sorting them into runs using a heap of a given size and outputting to standard output
 * 
 * Usage: java CreateRuns [heapSize]
 */
public class CreateRuns {
    public static void main(String[] args) {
        // Creating a timer object to time how long creating the runs takes
        Timer timer = new Timer();
        timer.start();

        // Extract max heap size from args
        int maxHeap = 31;
        try {
            maxHeap = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.println("Invalid arguments - using default max heap size of 31");
        }

        // Create stream reader to read from standard input
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        MyMinHeap heap = new MyMinHeap(maxHeap);
        int numRuns = 0;

        try {
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
            while (heap.peek().toString() != null) {
                // Output the smallest value in the minheap
                String previous = heap.peek().toString();
                System.out.println(previous);

                // If the line we are about to insert into the heap is smaller than the previous line,
                // then move to the end of the heap and ignore for now
                if (line == null || Utils.smallest(line, previous).equals(line)) {
                    heap.shrink(line);
                } else {
                    heap.replace(line);
                }

                // When the usable heap has been depleted - reset capacity and start a new run
                if (heap.capacity() <= 0) {
                    heap.resetCapacity();
                    numRuns++;
                }

                // Read in the next line
                line = reader.readLine();
            }

            // Output the time taken once all runs have been created
            timer.end();
            timer.setLabel("Run creation (" + numRuns + " runs)");
            System.err.println(timer);
        } catch (Exception e) {
            System.err.println("Create runs Error");
            System.err.println(e);
        }
    }
}
