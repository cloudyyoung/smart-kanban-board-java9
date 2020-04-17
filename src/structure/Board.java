package structure;

import com.google.gson.annotations.*;

/**
 * The {@code Board} class, extends from {@code Node}.
 *
 * <p>The instance should contains {@code Column} object as children nodes.
 *
 * @since 1.0
 * @version 4.0
 */
public final class Board extends Node {

  /** Color of the board in HEX, eg: #242424. */
  @Expose private String color;

  /**
   * Constructor of {@code Board}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Board(HttpBody obj) {
    super(obj);
    this.color = obj.getString("color");
  }

  /**
   * Constructor of {@code Board}, provide title, note and color.
   *
   * @param title The title in {@code String}
   * @param note The note in {@code String}
   * @param boardId THe id in {@code String}
   */
  public Board(
      final String title,
      final String note,
      final String color,
      final Node parent) {
    super(title, note, parent);
    this.setColor(color);
  }

  protected void createSubColumns() {
    Column col1 = new Column("To Do", "Todo", 0, this);
    Column col2 = new Column("In Progress", "Todo", 1, this);
    Column col3 = new Column("Done", "Todo", 2, this);
    col1.createRequest();
    col2.createRequest();
    col3.createRequest();
  }

  /**
   * Sets the color of the board, in local storage.
   *
   * @param color The color in {@code String}
   */
  protected void setColor(String color) {
    this.color = color;
  }

  /**
   * Sets the color of the board.
   *
   * @param color The color in {@code String}
   * @return the http request of this action
   */
  public Result setColorRequest(String color) {
    Result res = new Result();
    if(!this.isExisting()){
      this.setColor(color);

      StructureRequest req = new StructureRequest(true, false, this);
      res.add(req);
    }else{
      HttpRequest req = this.update("color", color);
      if (req.isSucceeded()) {
        this.setColor(color);
      }
      res.add(req);
    }
    return res;
  }

  /**
   * Returns the color of the board.
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
        + this.getNote()
        + "\", color: "
        + this.getColor()
        + ", nodes: "
        + this.getNodes().toString()
        + "\")";
  }
}
