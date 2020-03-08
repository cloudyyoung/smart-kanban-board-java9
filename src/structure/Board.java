package structure;

public class Board extends Node {

  /** Color of the board, in HEX */
  private String color;

  public Board(HttpBody obj) {
    super(obj);
    this.color = obj.getString("color");
  }

  public Board(int id, String title, String note, String color) {
    super(id, title, note);
    this.color = color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return this.color;
  }
}
