package structure;

import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.annotations.*;

/**
 * The {@code Event} class, extends from {@code Node}.
 *
 * @since 1.0
 * @version 2.1
 */
public class Event extends Node {

  @SerializedName("importance_level")
  @Expose private int importanceLevel;
  // importanceLevel range from 0 to 3, which 0 is not important
  // and 3 is super important
  @SerializedName("due_date")
  @Expose private Long dueDate;
  // dueDate is the timeStamp
  @Expose private Long duration;
  // Duration store in timeStamp form (second)
  @SerializedName("last_generated_date")
  @Expose private Long lastGeneratedDate;
  // last generated date store in timeStamp form (second)


  /**
   * Constructor of {@code Event}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Event(
      String title,
      String note,
      Long dueDate,
      Long duration,
      int importanceLevel,
      Node parent) {
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
    this.setlastGeneratedDate(obj.getLong("last_generated_date"));
  }

  protected void setDuration(Long duration) {
    this.duration = duration;
  }

  public Result setDurationRequest(Long duration) {
    Result res = new Result();
    if(!this.isExisting()){
      this.setDuration(duration);
      
      StructureRequest req = new StructureRequest(true, false, this);
      res.add(req);
    }else{
      HttpRequest req = this.update("duration", duration);
      res.add(req);

      if (req.isSucceeded()) {
        this.setDuration(duration);
      }
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

  public Long getlastGeneratedDate() {
    return this.lastGeneratedDate;
  }

  public Long getlastGeneratedDateValue() {
    return (this.lastGeneratedDate == null) ? Long.MAX_VALUE : this.lastGeneratedDate;
  }

  private void setImportanceLevel(int importance) {
    this.importanceLevel = importance;
  }

  private void setlastGeneratedDate(Long date) {
    this.lastGeneratedDate = date;
  }

  public Result setImportanceLevelRequest(int importance) {
    Result res = new Result();
    if(!this.isExisting()){
      this.setImportanceLevel(importance);
      
      StructureRequest req = new StructureRequest(true, false, this);
      res.add(req);
    }else{
      HttpRequest req = this.update("importance_level", importance);
      res.add(req);

      if (req.isSucceeded()) {
        this.setImportanceLevel(importance);
      }
    }
    return res;
  }

  public Integer getImportanceLevel() {
    return this.importanceLevel;
  }

  protected void setDueDate(Long dueDate) {
    this.dueDate = dueDate;
  }

  public Result setDueDateRequest(Long dueDate) {
    Result res = new Result();
    HttpRequest req = this.update("due_date", dueDate);
    res.add(req);

    if (req.isSucceeded()) {
      this.setDueDate(dueDate);
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
  public boolean beforeAndOnGeneratedToday () {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    Long currentDayInSecond = c.getTimeInMillis() / 1_000;
    Long enderTimeInSecond = currentDayInSecond + 86400;
    return this.getlastGeneratedDateValue() < enderTimeInSecond;
  }

  public boolean onGeneratedToday () {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    Long currentDayInSecond = c.getTimeInMillis() / 1_000;
    Long enderTimeInSecond = currentDayInSecond + 86400;
    System.out.println("currentDayInSecond" + currentDayInSecond);
    System.out.println("getlastGeneratedDateValue" + this.getlastGeneratedDateValue());
    System.out.println(this.getlastGeneratedDateValue() < enderTimeInSecond && this.getlastGeneratedDateValue() > currentDayInSecond);
    return this.getlastGeneratedDateValue()< enderTimeInSecond && this.getlastGeneratedDateValue() > currentDayInSecond;
  }

  public void updateGeneratedDate() {
    Calendar c = Calendar.getInstance();
    // System.out.println(c.getTimeInMillis() / 1_000);
    this.setlastGeneratedDate(c.getTimeInMillis() / 1_000);
    this.setlastGeneratedDateRequest(c.getTimeInMillis() / 1_000);
    // System.out.println();
  }

  public Result setlastGeneratedDateRequest(Long lastGeneratedDate) {
    Result res = new Result();
    HttpRequest req = this.update("last_generated_date", lastGeneratedDate);
    res.add(req);

    if (req.isSucceeded()) {
      this.setDueDate(dueDate);
    }
    return res;
  }
}
