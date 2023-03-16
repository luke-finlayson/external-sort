import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.Buffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class MergeRuns {
  public static void main(String[] args) {
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

      // Open the initial input and output files
      BufferedReader[] inputs = getInputs(filenames);
      BufferedWriter[] outputs = getOutputs(filenames);
      int[] mergeResult;

      // Loop until only one run remains
      do {
        // Merge inputs into outputs
        mergeResult = merge(inputs, outputs);
        
        // Switch inputs and outputs
        filenames = Utils.flipArray(filenames);
        inputs = getInputs(filenames);
        outputs = getOutputs(filenames);
      } 
      while (mergeResult[1] > 1);

      String outputFile = Utils.getFilename(mergeResult[0] + numFiles);
      BufferedReader output = Utils.openFile(outputFile);
      
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

      String previous = "";
      boolean empty = false;

      while (!empty) {
        String smallest = Utils.smallest(lines[0], lines[1]);

        if (smallest.compareTo(previous) < 0) {
          current = (current + 1) % outputs.length;
          run++;
        }
        previous = smallest;

        outputs[current].write(smallest);
        outputs[current].newLine();

        // Loop through head lines and determine which file to read from next
        // We start from the end of the array to work with the default returned from Utils.smallest
        boolean lineRead = false;
        for (int i = lines.length / 2; i > 0; i--) {
          if (smallest.equals(lines[i])) {
            lines[i] = inputs[i].readLine();
            lineRead = true;
          }
        }

        // Read from first file if no other lines were read
        if (!lineRead) {
          lines[0] = inputs[0].readLine();
        }

        // Check if there are still input lines to process
        empty = true;
        for (String line : lines) {
          if (line != null) {
            empty = false;
          }
        }
      }

      Utils.closeFiles(outputs);
      Utils.closeFiles(inputs);

      return new int[] { current, run };
    }
    catch (Exception e) {
      System.err.println("Merging Error");
      System.err.println(e);
      System.err.println("\t on run " + run);

      return new int[] { -1, run};
    }
  }
}
