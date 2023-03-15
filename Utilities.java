/**
 * A simple helper class to provide common tools used by all classes
 */
public class Utilities {
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
}
