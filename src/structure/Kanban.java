package structure;

import java.util.HashMap;

public class Kanban extends Node {

  public static Kanban current;

  public Kanban(HashMap<String, ?> obj) {
    super(obj);
  }

  public static void main(String[] args) {
    User user = new User();
    user.authenticate("test", "test");
    user.fetchKanban();
    System.out.println(Kanban.current);
  }
}
