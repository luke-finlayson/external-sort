import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/**
 * A simple helper class to provide common tools used by all classes
 */
public class Utils {
  // Prevent instances of utilities class being created
  private Utils() {}

  /**
   * Returns true if value1 is smaller than value2
   */
  public static String smallest(String value1, String value2) {
    if (value1 == null && value2 == null) {
      return "";
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

  public static String[] flipArray(String[] arr) {
    // Copy first half of array into temp. array
    String[] prevInputs = Arrays.copyOf(arr, arr.length / 2);
    
    // Create the new array and copy last half of original array to start of new array
    String[] newArray = new String[arr.length];
    System.arraycopy(arr, arr.length / 2, newArray, 0, arr.length / 2);
    
    // Copy the first half of the original array into the new one
    System.arraycopy(prevInputs, 0, newArray, arr.length / 2, arr.length / 2);
    return newArray;
  }
  
  public static String[] concat(String[] arr1, String[] arr2) {
    // Copy the contents of arr1 into a new array the lenght of both input arrays combined
    String[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);
    // Copy the contents of arr2 into the new array and return
    System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
    return result;
  }
}
