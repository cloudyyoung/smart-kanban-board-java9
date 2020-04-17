package structure;

import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.annotations.*;

/**
 * The {@code Event} class, extends from {@code Node}.
 *
 * @author Cloudy Young, Jimschenchen
 * @since 1.0
 * @version 4.0
 */
public class Event extends Node {

  /**
   * The importance level of the event in {@code int}. It is ranged from 0 to 3, from the least to
   * the most important. It is exposed to Gson, can be both serialized and deserialized. It will be
   * serialized as {@code importance_level}.
   */
  @SerializedName("importance_level")
  @Expose
  private int importanceLevel;

  /**
   * The due date of the event timestamp, in seconds, in {@code Long}. It is exposed to Gson, can be
   * both serialized and deserialized. It will be serialized as {@code due_date}.
   */
  @SerializedName("due_date")
  @Expose
  private Long dueDate;

  /**
   * The duration of the event in seconds, in {@code Long}. It is exposed to Gson, can be both
   * serialized and deserialized.
   */
  @Expose private Long duration;

  /**
   * The date of this event appears in Today board last time timestamp, in seconds, in {@code Long}.
   * It is exposed to Gson, can be both serialized and deserialized. It will be serialized as {@code
   * last_geenrated_date}.
   */
  @SerializedName("last_generated_date")
  @Expose
  private Long lastGeneratedDate;

  /**
   * Constructor of {@code Event}, provide {@code HttpBody}.
   *
   * @version 4.0
   * @param title the title of the event
   * @param note the note of the event
   * @param dueDate the due date in timestamp of the event
   * @param duration the duration of the event
   * @param importanceLevel the importance level of the event
   * @param parent the parent node of the event
   */
  public Event(
      String title, String note, Long dueDate, Long duration, int importanceLevel, Node parent) {
    super(title, note, parent);
    this.setDuration(duration);
    this.setDueDateRequest(dueDate);
    this.setImportanceLevel(importanceLevel);
  }

  /**
   * Constructor of {@code Event}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Event(HttpBody obj) {
    super(obj);
    this.setDuration(obj.getLong("duration"));
    this.setDueDate(obj.getLong("due_date"));
    this.setImportanceLevel(obj.getInt("importance_level"));
    this.setLastGeneratedDate(obj.getLong("last_generated_date"));
  }

  /**
   * Sets the duration of the event
   *
   * @version 4.0
   * @param duration the duration in {@code Long}
   */
  protected void setDuration(Long duration) {
    this.duration = duration;
  }

  /**
   * Sets the duration of the event.
   *
   * @version 4.0
   * @param duration the duration in {@code Long}
   * @return the {@code Result} instance of this action
   */
  public Result setDurationRequest(Long duration) {
    Result res = new Result();
    if (!this.isExisting()) {
      this.setDuration(duration);

      StructureRequest req = new StructureRequest(true, false, false, this);
      res.add(req);
    } else {
      HttpRequest req = this.update("duration", duration);
      res.add(req);

      if (req.isSucceeded()) {
        this.setDuration(duration);
      }
    }
    return res;
  }

  /**
   * Returns the duration of the event
   *
   * @version 4.0
   * @return the duration in {@code Long}, could be {@code null}
   */
  public Long getDuration() {
    return this.duration;
  }

  /**
   * Returns the duration of the event
   *
   * @version 4.0
   * @return the duration in {@code Long}, returns {@code 0} if {@code null}
   */
  public Long getDurationValue() {
    return (this.duration == null) ? 0L : this.duration;
  }

  /**
   * Returns the duration of the event
   *
   * @version 4.0
   * @return the duration in minutes, in {@code int}
   */
  public int getDurationInMinutes() {
    return (int) (this.duration / 60_000);
  }

  /**
   * Returns the last time the event appears in Today.
   *
   * @version 4.0
   * @return the timestamp of the date in {@code Long}, could be {@code null}
   */
  public Long getLastGeneratedDate() {
    return this.lastGeneratedDate;
  }

  /**
   * Returns the last time the event appears in Today.
   *
   * @version 4.0
   * @return the timestamp of the date in {@code Long}, returns {@code 0} if {@code null}
   */
  public Long getLastGeneratedDateValue() {
    return (this.lastGeneratedDate == null) ? Long.MAX_VALUE : this.lastGeneratedDate;
  }

  /**
   * Sets the importance level of the event.
   *
   * @version 4.0
   * @param importance the level in {@code int}
   */
  private void setImportanceLevel(int importance) {
    this.importanceLevel = importance;
  }

  /**
   * Sets the last time the event appears in Today.
   *
   * @version 4.0
   * @param date the timestamp in {@code Long}
   */
  private void setLastGeneratedDate(Long date) {
    this.lastGeneratedDate = date;
  }

  /**
   * Sets the importance level of the event.
   *
   * @version 4.0
   * @param importance the level in {@code int}
   * @return the {@code Result} instance of this action
   */
  public Result setImportanceLevelRequest(int importance) {
    Result res = new Result();
    if (!this.isExisting()) {
      this.setImportanceLevel(importance);

      StructureRequest req = new StructureRequest(true, false, false, this);
      res.add(req);
    } else {
      HttpRequest req = this.update("importance_level", importance);
      res.add(req);

      if (req.isSucceeded()) {
        this.setImportanceLevel(importance);
      }
    }
    return res;
  }

  /**
   * Returns the importance level of the event
   *
   * @version 4.0
   * @return the importance level in {@code int}
   */
  public Integer getImportanceLevel() {
    return this.importanceLevel;
  }

  /**
   * Sets the due date of the event.
   *
   * @version 4.0
   * @param dueDate the due date timestamp, in seconds, in {@code Long}
   */
  protected void setDueDate(Long dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * Sets the due date timestamp
   *
   * @version 4.0
   * @param dueDate the due date timestamp, in seconds, in {@code Long}
   * @return the {@code Result} instance of this action
   */
  public Result setDueDateRequest(Long dueDate) {
    Result res = new Result();
    HttpRequest req = this.update("due_date", dueDate);
    res.add(req);

    if (req.isSucceeded()) {
      this.setDueDate(dueDate);
    }
    return res;
  }

  /**
   * Returns the due date of the event.
   *
   * @version 4.0
   * @return the due date timestamp, in seconds, in {@code Long}, could be {@code null}
   */
  public Long getDueDate() {
    return this.dueDate;
  }

  /**
   * Returns the due date of the event.
   *
   * @version 4.0
   * @return the due date timestamp in {@code Long}, returns {@code 0} if {@code null}
   */
  public Long getDueDateValue() {
    return (this.dueDate == null) ? Long.MAX_VALUE : this.dueDate;
  }

  /**
   * Returns the due date of the event, in {@code String}
   *
   * @version 4.0
   * @return the due date formatted string in {@code String}
   */
  public String getDueDateString() {
    Date date = new Date(this.getDueDateValue() * 1000);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(date);
  }

  /** {@inheritDoc} */
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
        + this.getNodes().toString()
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
    Long dueDateInLong = this.getDueDateValue() / 3_600;
    int hourWeight = dueDateInLong.intValue();
    int importanceWeight = this.getImportanceLevel() * (hourWeight / 24);
    return hourWeight - importanceWeight;
  }

  /**
   * Return an boolean of weahter this event is early than the end of today
   *
   * @return an boolean of weahter this event is early than the end of today
   */
  public boolean isBeforeGeneratedToday() {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    Long currentDayStart = c.getTimeInMillis() / 1_000; // In seconds
    return this.getLastGeneratedDateValue() < currentDayStart;
  }

  /**
   * Returns if the event appears on Today board
   *
   * @version 4.0
   * @return true if the event appears on Today board, false otherwise
   */
  public boolean isOnGeneratedToday() {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    Long currentDayStart = c.getTimeInMillis() / 1_000; // In seconds
    Long currentDayEnd = currentDayStart + 86_400; // In seconds
    System.out.println("currentDayInSecond" + currentDayStart);
    System.out.println("getlastGeneratedDateValue" + this.getLastGeneratedDateValue());
    System.out.println(
        this.getLastGeneratedDateValue() < currentDayEnd
            && this.getLastGeneratedDateValue() > currentDayStart);
    return this.getLastGeneratedDateValue() < currentDayEnd
        && this.getLastGeneratedDateValue() > currentDayStart;
  }

  /**
   * Sets the the last time the event appears in Today to today.
   *
   * @version 4.0
   * @return the {@code Result} instance of this action
   */
  public Result setLastGeneratedDateRequest() {
    Calendar c = Calendar.getInstance();
    Long lastGeneratedDate = c.getTimeInMillis() / 1_000; // In seconds
    Result res = new Result();
    HttpRequest req = this.update("last_generated_date", lastGeneratedDate);
    res.add(req);

    if (req.isSucceeded()) {
      this.setLastGeneratedDate(lastGeneratedDate);
    }
    return res;
  }
}
