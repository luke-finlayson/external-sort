import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * A simple helper class to provide common tools used by all classes
 */
public class Util {
  // Prevent instances of utilities class being created
  private Util() {}

  /**
   * Returns true if value1 is smaller than value2
   */
  public static boolean smallest(String value1, String value2) {
    if (value1 == null) {
        return false;
    }
    if (value2 == null) {
        return true;
    }

    return value1.compareTo(value2) < 0;
  }

  /**
   * Creates/opens a file with a buffered writer and the given filename
   * @param filename The filename of the file to create/open
   * @return Returns the buffered writer (or null if there was an exception)
   */
  public static BufferedWriter createFile(String filename) {
    try {
      // Create/open the file with the given filename and use it to create a buffered writer
      FileWriter file = new FileWriter(filename);
      BufferedWriter writer = new BufferedWriter(file);

      return writer;
    }
    catch (Exception e) {
      System.err.println(e);

      return null;
    }
  }

  /**
   * Returns a string formatted to represent temporary files created for sorting
   * @param file The name to include in the filename
   * @return Returns a string in the format [file].temp
   */
  public static String getFilename(int name) {
    return name + ".temp";
  }

  /**
   * Safely closes each of the given files
   * @param files The files to close
   */
  public static void closeFiles(BufferedWriter[] files) {
    for (BufferedWriter file : files) {
      try {
        file.close();
      }
      catch (Exception e) {
        System.err.println(e);
      }
    }
  }

  /**
   * Creates buffered writers for a given number of files.
   * Files are numbered starting from 0 - in the format set by getFilename()
   * @param numReaders The number of files to create
   * @return Returns the array of buffered writers
   */
  public static BufferedWriter[] createFiles(int numReaders) {
    // Create collection of output files to write to
    BufferedWriter[] outputs = new BufferedWriter[numReaders];

    try {
      // Initialise a file writer for each 'file' in the outputs array
      for (int i = 0; i < outputs.length; i++) {
        BufferedWriter writer = Util.createFile(Util.getFilename(i));
        outputs[i] = writer;
      }

      return outputs;
    } 
    catch (Exception e) {
      System.err.println(e);
    }

    // Return null if anyuthing goes wrong
    return null;
  }
}
