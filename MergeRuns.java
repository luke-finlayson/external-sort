import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by Luke Finlayson - 1557835
 * 
 * Distributes runs from standard input into a given number of input files - then merge sorts into the same number of output files.
 * Program completes when one of the output files contains the fully sorted data - and outputs contents to standard output.
 * 
 * Usage: java MergeRuns [number of files]
 */
public class MergeRuns {
  public static void main(String[] args) {
    Timer timer = new Timer("Merge Complete");
    timer.start();

    // Try to get the number of input/output files to use from program arguments - otherwise just use 2 files
    int numFiles = 2;
    try {
      numFiles = Integer.parseInt(args[0]);
    }
    catch (Exception e) {
      System.err.println("Merge args Error");
      System.err.println(e);
    }

    // Read from the standard input and 
    DistributeRuns distributer = new DistributeRuns(numFiles);

    // Only continue with sort if file generation was successfull
    if (distributer.generateFiles()) {
      String[] outputNames = new String[numFiles];
      for (int i = 0; i < numFiles; i++) {
        outputNames[i] = Utils.getFilename(i + numFiles);
      }

      // An array containing the input and output filenames - first half inputs, second half outputs
      String[] filenames = Utils.concat(distributer.getFilenames(), outputNames);
      boolean flipped = false;

      // Open the initial input and output files
      BufferedReader[] inputs = getInputs(filenames);
      BufferedWriter[] outputs = getOutputs(filenames);
      int[] mergeResult;
      int passes = 0;

      // Loop until one of the output files contains the sorted contents of the 
      do {
        // Merge inputs into outputs
        mergeResult = merge(inputs, outputs);
        
        // Switch inputs and outputs
        filenames = Utils.flipArray(filenames);
        flipped = !flipped;
        inputs = getInputs(filenames);
        outputs = getOutputs(filenames);

        passes++;
      } 
      while (mergeResult[1] > 1);

      // Determine which output file contains the sorted data
      String outputFile = Utils.getFilename(mergeResult[0] + (flipped ? numFiles : 0));
      BufferedReader output = Utils.openFile(outputFile);
      
      // Output the sorted contents to standard output
      try {
        String line = output.readLine();

        while (line != null) {
          System.out.println(line);
          line = output.readLine();
        }
      }
      catch (Exception e) {
        System.err.println(e);
      }

      // Clean up the temporary files
      try {
        for (String filename : filenames) {
          Path path = FileSystems.getDefault().getPath(filename);
          Files.delete(path);
        }
      }
      catch (Exception e) {
        System.err.println(e);
      }

      // Output the time taken to merge sort the input files
      timer.end();
      timer.setLabel("Merge complete (" + passes + " passes)");
      System.err.println(timer);
    }
  }

  /**
   * Creates a buffered reader for each of the input files
   * @param filenames The array of filenames - first half containing input files
   * @return Returns buffered readers for each of the input files
   */
  public static BufferedReader[] getInputs(String[] filenames) {
    String[] inputNames = Arrays.copyOf(filenames, filenames.length / 2);
    return Utils.openFiles(inputNames);
  }

  /**
   * Creates a buffered writer for each of the output files
   * @param filenames The array of filenames - second half containing the output files
   * @return Returns buffered writers for each of the output files
   */
  public static BufferedWriter[] getOutputs(String[] filenames) {
    int length = filenames.length / 2;
    
    String[] outputNames = new String[length];
    System.arraycopy(filenames, length, outputNames, 0, length);
    
    return Utils.createFiles(outputNames);
  }

  /**
   * Merges runs from two input files into runs spread across two output files.
   * 
   * @param inputs The buffered readers coming from the input files
   * @param outputs The buffered writers pointing to the output files
   * @return Returns an array of ints - where the first element is the index of 
   *         the output file containing the final run, and the second element 
   *         is the number of runs found in the output files
   */
  public static int[] merge(BufferedReader[] inputs, BufferedWriter[] outputs) {
    int current = 0;
    int run = 1;

    try {
      // Read in initial lines
      String[] lines = new String[inputs.length];
      for (int i = 0; i < inputs.length; i++) {
        lines[i] = inputs[i].readLine();
      }

      MyMinHeap heap = new MyMinHeap(lines.length);

      // Load initial lines into the heap and start merging input files
      if (heap.load(lines)) {
        String previous = "";

        while (heap.peek().toString() != null) {
          Node line = heap.peek();

          if (line.toString().compareTo(previous) < 0) {
            current = (current + 1) % outputs.length;
            run++;
          }
          previous = heap.peek().toString();

          outputs[current].write(line.toString());
          outputs[current].newLine();

          // Read in another line from the file that the previous line was from
          int i = line.key;
          lines[i] = inputs[i].readLine();
          heap.replace(lines[i], i);
        }
      }

      Utils.closeFiles(outputs);
      Utils.closeFiles(inputs);

      return new int[] { current, run };
    }
    catch (Exception e) {
      System.err.println("\u001b[31m[Merging Error]\u001b[0m");
      System.err.println(e);
      System.err.println("\t on run " + run);

      return new int[] { -1, run};
    }
  }
}
