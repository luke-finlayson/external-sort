import java.time.Instant;

public class Timer {
  private String label;
  private long startTime;
  private long timeTaken;

  public Timer(String label) {
    this.label = label;
  }

  public void start() {
    startTime = Instant.now().toEpochMilli();
  }

  public void end() {
    long endTime = Instant.now().toEpochMilli();
    timeTaken = endTime - startTime;
  }

  public String toString() {
    return "\u001b[32m[" + label + "]\u001b[0m - " + timeTaken + "ms";
  }
}
