import java.io.BufferedReader;
import java.io.BufferedWriter;
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
      int run;

      // Loop until only one run remains
      do {
        // Merge inputs into outputs
        run = merge(inputs, outputs);
        
        // Switch inputs and outputs
        filenames = Utils.flipArray(filenames);
        inputs = getInputs(filenames);
        outputs = getOutputs(filenames);

        System.out.println(run);
      } 
      while (run > 1);
    }
  }

  public static BufferedReader[] getInputs(String[] filenames) {
    String[] inputNames = Arrays.copyOf(filenames, filenames.length / 2);
    return Utils.openFiles(inputNames);
  }

  public static BufferedWriter[] getOutputs(String[] filenames) {
    int length = filenames.length / 2;
    
    String[] outputNames = new String[length];
    System.arraycopy(filenames, length, outputNames, 0, length);
    
    return Utils.createFiles(outputNames);
  }

  public static int merge(BufferedReader[] inputs, BufferedWriter[] outputs) {
    try {
      String line1 = inputs[0].readLine();
      String line2 = inputs[1].readLine();
      int current = 0;
      int runs = 1;
      String previous = "";

      while (line1 != null && line2 != null) {
        String smallest = Utils.smallest(line1, line2);

        if (smallest.compareTo(previous) < 0) {
          current = (current + 1) % outputs.length;
          runs++;
        }
        previous = smallest;

        outputs[current].write(smallest);
        outputs[current].newLine();

        if (smallest.equals(line2)) {
          line2 = inputs[1].readLine();
        }
        else {
          line1 = inputs[0].readLine();
        }
      }

      while (line1 != null) {
        outputs[current].write(line1);
        outputs[current].newLine();
        line1 = inputs[0].readLine();
      }

      while (line2 != null) {
        outputs[current].write(line2);
        outputs[current].newLine();
        line2 = inputs[1].readLine();
      }

      Utils.closeFiles(outputs);
      Utils.closeFiles(inputs);

      return runs;
    }
    catch (Exception e) {
      System.err.println("Merging Error");
      System.err.println(e);

      return -1;
    }
  }
}
