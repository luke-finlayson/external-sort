/**
 * Created by Luke Finlayson - 1557835
 * 
 * Simple Node to represent an element in a heap
 */
public class Node {
  private String value;
  public int key;

  public Node(String value, int key) {
    this.value = value;
    this.key = key;
  }

  /**
   * Return the string value of the node
   */
  public String toString() {
    return value;
  }
}
