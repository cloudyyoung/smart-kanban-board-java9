package structure;

import com.google.gson.annotations.*;

public class Board extends Node {

  /** Color of the board, in HEX. */
  @Expose private String color;

  /**
   * Constructor of {@code Board}, provide {@code HttpBody}.
   *
   * @param object the {@code HttpBody} for initialization
   */
  public Board(HttpBody obj) {
    super(obj);
    this.color = obj.getString("color");
  }

  /**
   * Constructor of Board, provide title, note and color.
   *
   * @param title The title in {@code String}
   * @param note The note in {@code String}
   * @param color The color in {@code String}
   */
  public Board(String title, String note, String color) {
    super(title, note);
    this.setColorLocal(color);
  }

  /**
   * Set the color in local storage.
   *
   * @param color The color in {@code String}
   */
  public void setColorLocal(String color) {
    this.color = color;
  }

  /**
   * Set the color.
   *
   * @param color The color in {@code String}
   */
  public HttpRequest setColor(String color) {
    HttpRequest req = this.set("color", color);
    if (req.isSucceeded()) {
      this.setColorLocal(color);
    }
    return req;
  }

  /**
   * Get the color.
   *
   * @return color in {@code String}
   */
  public String getColor() {
    return this.color;
  }

  /**
   * A string that "textually represents" this object.
   *
   * @return text
   */
  public String toString() {
    return this.getType()
        + " (id: "
        + this.getId()
        + ", title: \""
        + this.getTitle()
        + "\", note: \""
        + "\", color: "
        + this.getColor()
        + this.getNote()
        + ", nodes: "
        + this.getNodes().toString()
        + "\")";
  }
}
