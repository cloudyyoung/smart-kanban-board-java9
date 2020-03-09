package structure;

import com.google.gson.annotations.*;

public class Board extends Node {

  /** Color of the board, in HEX */
  @Expose private String color;

  public Board(HttpBody obj) {
    super(obj);
    this.color = obj.getString("color");
  }

  public Board(String title, String note, String color) {
    super(title, note);
    this.color = color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return this.color;
  }
}
