import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;

/**
 * Created by Luke Finlayson - 1557835
 * 
 * Evenly distributes runs from standard input into a given number of temporary files.
 */
public class DistributeRuns {
  private int numFiles;
  private String[] outputs;

  /**
   * Create a run distributer that will generate k number of files
   * @param numFiles The number of files to generate (k)
   */
  public DistributeRuns(int numFiles) {
    // Default to 2 files if argument is invalid
    this.numFiles = (numFiles < 2) ? 2 : numFiles;

    // Create the filenames to be used when generating input files
    outputs = new String[this.numFiles];

    for (int i = 0; i < this.numFiles; i++) {
      outputs[i] = Utils.getFilename(i);
    }
  }

  /**
   * Read runs from standard input and distributes evenly into k number of files
   * @return Returns true if file generation was successful - false otherwise
   */
  public boolean generateFiles() {
    // Create the file writers for the temporary run storage
    BufferedWriter[] outputWriters = Utils.createFiles(outputs);

    if (outputWriters == null) {
      return false;
    }

    // Create stream reader to read from standard input
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    try {
      String line = reader.readLine();
      String previous = "";
      int file = 0;

      // Continously read lines from input stream
      while (line != null) {
        // Switch files when the end of the run has been reached
        if (Utils.smallest(line, previous).equals(line)) {
          file = (file + 1) % numFiles;
        }

        // Write a line from the run to the file
        outputWriters[file].write(line);
        outputWriters[file].newLine();

        // Read the next line of input
        previous = line;
        line = reader.readLine();
      }
    } catch (Exception e) {
      System.err.println("Generate Error");
      System.err.println(e);
      return false;
    }

    // Close the output files once finished
    Utils.closeFiles(outputWriters);
    return true;
  }

  /**
   * Get the filenames of the files generated
   * @return Returns the filenames of the generated files
   */
  public String[] getFilenames() {
    return outputs;
  }
}
