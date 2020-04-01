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

  public Event(String title, String note, Long dueDate, Long duration, int importanceLevel) {
    super(title, note);
    this.duration = duration;
    this.dueDate = dueDate;
    this.importanceLevel = importanceLevel;
  }

  /**
   * Constructor of {@code Event}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Event(HttpBody obj) {
    super(obj);
    this.duration = obj.getLong("duration");
    this.dueDate = obj.getLong("due_date");
    this.importanceLevel = obj.getInt("importance_level");
    // this.dueDate = Long.valueOf(obj.getString("due_date").replace(".0", "")) | 0;
    // this.importanceLevel = Integer.valueOf(obj.getString("importance_level").replace(".0", "")) |
    // 0;
  }

  /**
   * Returns the color of the board.
   *
   * @return color in {@code String}
   */
  public Long getDuration() {
    return this.duration;
  }

  public int getDurationInMinutes() {
    return (int) (this.duration / 60000);
  }

  public Long getImportanceLevel() {
    return this.duration;
  }

  public Long getDueDate() {
    return this.dueDate;
  }

  public String toString() {
    return this.getType()
        + " (id: "
        + this.getId()
        + ", title: \""
        + this.getTitle()
        + "\", note: \""
        + this.getNote()
        + "\", duration: "
        + this.getDuration()
        + ", importanceLevel: "
        + this.getImportanceLevel()
        + ", dueDate: "
        + this.getDueDate()
        + ", nodes: "
        + this.getChildrenNodes().toString()
        + "\")";
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
