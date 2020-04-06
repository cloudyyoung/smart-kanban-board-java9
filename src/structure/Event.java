package structure;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The {@code Event} class, extends from {@code Node}.
 *
 * @since 1.0
 * @version 2.1
 */
public final class Event extends Node {

  private int importanceLevel;
  // importanceLevel range from 0 to 3, which 0 is not important
  // and 3 is super important
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
      final Integer eventId,
      final String title,
      final String note,
      final Long dueDate,
      final Long duration,
      final int importanceLevel,
      final Node parent) {
    super(eventId, title, note, parent);
    this.setDurationLocal(duration);
    this.setDueDateLocal(dueDate);
    this.setImportanceLevelLocal(importanceLevel);
  }

  /**
   * Constructor of {@code Event}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Event(HttpBody obj) {
    super(obj);
    this.setDurationLocal(obj.getLong("duration"));
    this.setDueDateLocal(obj.getLong("due_date"));
    this.setImportanceLevelLocal(obj.getInt("importance_level"));
  }

  private StructureRequest setDurationLocal(Long duration) {
    this.duration = duration;

    return new StructureRequest(true, false, this);
  }

  public Result setDuration(Long duration) {
    final Result res = new Result();
    final HttpRequest req = this.set("duration", duration);
    res.add(req);

    if (req.isSucceeded()) {
      final StructureRequest req2 = this.setDurationLocal(duration);
      res.add(req2);
    }
    return res;
  }

  public Long getDuration() {
    return this.duration;
  }

  public Long getDurationValue() {
    return (this.duration == null) ? 0L : this.duration;
  }

  public int getDurationInMinutes() {
    return (int) (this.duration / 60_000);
  }

  private StructureRequest setImportanceLevelLocal(int importance) {
    this.importanceLevel = importance;

    return new StructureRequest(true, false, this);
  }

  public Result setImportanceLevel(int importance) {
    Result res = new Result();
    HttpRequest req = this.set("importance_level", importance);
    res.add(req);

    if (req.isSucceeded()) {
      StructureRequest req2 = this.setImportanceLevelLocal(importance);
      res.add(req2);
    }
    return res;
  }

  public Integer getImportanceLevel() {
    return this.importanceLevel;
  }

  private StructureRequest setDueDateLocal(Long dueDate) {
    this.dueDate = dueDate;

    return new StructureRequest(true, false, this);
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

  public Long getDueDate() {
    return this.dueDate;
  }

  public Long getDueDateValue() {
    return (this.dueDate == null) ? Long.MAX_VALUE : this.dueDate;
  }

  public String getDueDateString() {
    Date date = new Date(this.getDueDateValue() * 1000);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(date);
  }

  @Override
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
  public boolean isOverdue() {
    boolean isOverdue = false;
    if (this.getDueDate() != null) {
      Calendar calendar = Calendar.getInstance();
      if (this.getDueDateValue() * 1000 <= calendar.getTimeInMillis()) {
        isOverdue = true;
      }
    }
    return isOverdue;
  }

  /**
   * Returns an int of weight to represent the event priority.
   *
   * @return an int of weight to represent the event priority.
   */
  public Integer getPriority() {
    final int hourWeight = this.getDueDateValue().intValue() / 3600;
    final int importanceWeight = this.getImportanceLevel() * (hourWeight / 24);
    return hourWeight - importanceWeight;
  }
}
