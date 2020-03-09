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
    this.setColorLocal(color);
  }

  public void setColorLocal(String color) {
    this.color = color;
  }

  public HttpRequest setColor(String color) {
    HttpRequest req = this.set("color", color);
    if(req.isSucceed()){
      this.setColorLocal(color);
    }
    return req;
  }

  public String getColor() {
    return this.color;
  }

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
