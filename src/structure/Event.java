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
    if (obj.getLong("duration") != null) {
      this.duration = obj.getLong("duration");
    } else {
      this.duration = 0l;
    }
    if (obj.getLong("due_date") != null) {
      this.dueDate = obj.getLong("due_date");
    } else {
      this.dueDate = 0l;
    }
    if (obj.getLong("importance_level") != null) {
      this.importanceLevel = obj.getInt("importance_level");
    } else {
      this.importanceLevel = 0;
    }
  }

  public Long getDuration() {
    return this.duration;
  }

  public int getDurationInMinutes() {
    return (int) (this.duration / 60000);
  }

  public Integer getImportanceLevel() {
    return this.importanceLevel;
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

  /**
   * Returns a boolean to represent if the event is over due.
   * 
   * @return a boolean to represent if the event is over due.
   */
  public boolean isExpired() {
    Calendar c = Calendar.getInstance();
    // System.out.println(c.getTimeInMillis() / 1000);
    // System.out.println(this.getDueDate());
    if (this.getDueDate() > c.getTimeInMillis() / 1000) {
      return false;
    }
    return true;
  }

  /**
   * Returns an int of weight to represent the event priority.
   * @return an int of weight to represent the event priority.
   */
  public Integer getPriority(){
    int timeDifferenceInHours = this.getDueDate().intValue() / 3600;
    int importancePriority = this.getImportanceLevel() * (timeDifferenceInHours / 24);
    int priority = timeDifferenceInHours - importancePriority;
    return priority;
  }

  public static void main(String[] args) {
    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    // c.set(2012, 12, 11, 13, 15);
    Long dueDate = 1585670400l * 1000;
    c.setTimeZone(TimeZone.getTimeZone("UTC"));

    c.setTimeInMillis(0);
    System.out.println(c.getTime());

    dueDate = 1585670400l * 1000;
    c.setTimeInMillis(dueDate);
    System.out.println(c.getTime());
    // c.set(2012, 0, 11, 13, 15);
    // Long duration = 60000l;
    // c.setTimeInMillis(dueDate);
    // System.out.print(c.getTime());

    /// c.setTimeInMillis(dueDate + duration);
    // System.out.print(c.getTime());
  }
}
