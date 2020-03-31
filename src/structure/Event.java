package structure;

import java.util.*;

/**
 * The {@code Event} class, extends from {@code Node}.
 *
 * @since 1.0
 * @version 2.1
 */
public class Event extends Node {
  /**
   * Constructor of {@code Event}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  String eventName;

  int importanceLevel;
  // importanceLevel range from 0 to 3, which 0 is not important and 3 is super important
  Long dueDate;
  // dueDate is the timeStamp
  Long duration;
  // Duration store in timeStamp form (millissecond)

  public Event(HttpBody obj, String eventName, Long dueDate, Long duration, int importanceLevel) {
    super(obj);
    this.eventName = eventName;
    this.duration = duration;
    this.dueDate = dueDate;
    this.importanceLevel = importanceLevel;
  }

  public static void main(String[] args) {
    Calendar c = Calendar.getInstance();
    // c.set(2012, 12, 11, 13, 15);
    Long dueDate = 12345677123456l;

    // c.setTimeInMillis(dueDate);
    // c.set(2012, 0, 11, 13, 15);
    Long duration = 60000l;
    c.setTimeInMillis(dueDate);
    System.out.print(c.getTime());

    c.setTimeInMillis(dueDate + duration);
    System.out.print(c.getTime());
    // System.out.print(c.getTimeInMillis());
  }
}
