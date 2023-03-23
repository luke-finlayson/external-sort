import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/**
 * Created by Luke Finlayson - 1557835
 * 
 * A helper class to provide common tools used by the various components of the External Sort program
 */
public class Utils {
  // Prevent instances of utilities class being created
  private Utils() {}

  /**
   * Returns the smaller of the two values - or value2 if they are equal
   */
  public static String smallest(String value1, String value2) {
    if (value1 == null && value2 == null) {
      return null;
    }
    if (value1 == null) {
        return value2;
    }
    if (value2 == null) {
        return value1;
    }

    try {
      if (value1.compareTo(value2) < 0) {
        return value1;
      }
      return value2;
    }
    catch (Exception e) {
      System.err.println("Compare error");
      return "";
    }
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
      System.err.println("Create file Error");
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
        System.err.println("Close files Error");
        System.err.println(e);
      }
    }
  }

  /**
   * Safely close each of the given file readers
   * @param files The file readers to close
   */
  public static void closeFiles(BufferedReader[] files) {
    for (BufferedReader file : files) {
      try {
        file.close();
      }
      catch (Exception e) {
        System.err.println("Close files Error");
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
  public static BufferedWriter[] createFiles(String[] filenames) {
    // Create collection of output files to write to
    BufferedWriter[] outputs = new BufferedWriter[filenames.length];

    try {
      // Initialise a file writer for each 'file' in the outputs array
      for (int i = 0; i < outputs.length; i++) {
        BufferedWriter writer = createFile(filenames[i]);
        outputs[i] = writer;
      }

      return outputs;
    } 
    catch (Exception e) {
      System.err.println("Create files Error");
      System.err.println(e);
      return null;
    }
  }

  /**
   * Atetmpts to open the given file
   * @param filename The filename of the file to open
   * @return Returns the file reader - or null if the file was unable to be opened
   */
  public static BufferedReader openFile(String filename) {
    try {
      FileReader file = new FileReader(filename);
      BufferedReader reader = new BufferedReader(file);

      return reader;
    }
    catch (Exception e) {
      System.err.println("Open file Error");
      System.err.println(e);
      return null;
    }
  }

  /**
   * Open a buffered reader for each of the given files
   * @param filenames The files to open
   * @return Returns the array of file readers - or null if they were unable to be opened
   */
  public static BufferedReader[] openFiles(String[] filenames) {
    BufferedReader[] inputs = new BufferedReader[filenames.length];

    try {
      for (int i = 0; i < inputs.length; i++) {
        BufferedReader reader = openFile(filenames[i]);
        inputs[i] = reader;
      }

      return inputs;
    }
    catch (Exception e) {
      System.err.println("Open files Error");
      System.err.println(e);
      return null;
    }
  }

  /**
   * Splits the array in two and switches the poition of the two halfs
   * @param arr The original array to flip
   * @return The resulting array - where the first half of the original is now the second half and vice versa
   */
  public static String[] flipArray(String[] arr) {
    String[] prevInputs = Arrays.copyOf(arr, arr.length / 2);
    
    String[] newArray = new String[arr.length];
    System.arraycopy(arr, arr.length / 2, newArray, 0, arr.length / 2);
    
    System.arraycopy(prevInputs, 0, newArray, arr.length / 2, arr.length / 2);
    return newArray;
  }
  
  /**
   * Concatanates two arrays into one array
   * @param arr1 The first array - this will be the first half of the new array
   * @param arr2 The second array - this will be the second half of the new array
   * @return The resulting array containing all elements of the input arrays
   */
  public static String[] concat(String[] arr1, String[] arr2) {
    String[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);
    System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
    return result;
  }
}
