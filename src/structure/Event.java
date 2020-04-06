package structure;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The {@code Event} class, extends from {@code Node}.
 *
 * @since 1.0
 * @version 2.1
 */
public class Event extends Node {

  private int importanceLevel;
  // importanceLevel range from 0 to 3, which 0 is not important and 3 is super important
  private Long dueDate;
  // dueDate is the timeStamp
  private Long duration;
  // Duration store in timeStamp form (millissecond)

  /**
   * Constructor of {@code Event}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Event(
      Integer id,
      String title,
      String note,
      Long dueDate,
      Long duration,
      int importanceLevel,
      Node parent) {
    super(id, title, note, parent);
    this.setDuration(duration);
    this.setDueDateLocal(dueDate);
    this.setImportanceLevel(importanceLevel);
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
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public Long getDuration() {
    return this.duration;
  }

  public Long getDurationValue() {
    return this.duration != null ? this.duration : 0l;
  }

  public int getDurationInMinutes() {
    return (int) (this.duration / 60000);
  }

  public void setImportanceLevel(int importance) {
    this.importanceLevel = importance;
  }

  public Integer getImportanceLevel() {
    return this.importanceLevel;
  }

  public Long getDueDate() {
    return this.dueDate;
  }

  public Long getDueDateValue() {
    return this.dueDate != null ? this.dueDate : 0l;
  }

  public String getDueDateString() {
    Date date = new Date(this.getDueDateValue() * 1000);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(date);
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
        + ", priority: "
        + this.getPriority()
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
    if (this.getDueDate() == null) return false;
    Calendar c = Calendar.getInstance();
    if (this.getDueDateValue() * 1000 > c.getTimeInMillis()) {
      return false;
    }
    return true;
  }

  /**
   * Returns an int of weight to represent the event priority.
   *
   * @return an int of weight to represent the event priority.
   */
  public Integer getPriority() {
    int timeDifferenceInHours = this.getDueDateValue().intValue() / 3600;
    int importancePriority = this.getImportanceLevel() * (timeDifferenceInHours / 24);
    int priority = timeDifferenceInHours - importancePriority;
    return priority;
  }


  public StructureRequest setDueDateLocal(Long dueDate) {
    this.dueDate = dueDate;

    StructureRequest req = new StructureRequest(true, false, this);
    return req;
  }

  public Result setDueDate(Long dueDate) {
    Result res = new Result();
    HttpRequest req = this.set("due_date", dueDate);
    res.add(req);

    if (req.isSucceeded()) {
      StructureRequest req2 = this.setDueDateLocal(dueDate);
      res.add(req2);
    }
    return res;
  }

  public static void main(String[] args) {
    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    // c.set(2012, 12, 11, 13, 15);
    Long dueDate = 1585670400l * 1000;
    c.setTimeZone(TimeZone.getTimeZone("UTC"));

    c.setTimeInMillis(0);
    // System.out.println(c.getTime());

    dueDate = 1585670400l * 1000;
    c.setTimeInMillis(dueDate);
    // System.out.println(c.getTime());
    // c.set(2012, 0, 11, 13, 15);
    // Long duration = 60000l;
    // c.setTimeInMillis(dueDate);
    // System.out.print(c.getTime());

    /// c.setTimeInMillis(dueDate + duration);
    // System.out.print(c.getTime());
  }
}
