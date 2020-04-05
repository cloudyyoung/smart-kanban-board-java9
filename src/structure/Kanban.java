package structure;

import java.util.ArrayList;

/**
 * The {@code Kanban} class, extends from {@code Node}.
 *
 * <p>The instance should contains {@code Board} object as children nodes.
 *
 * @since 1.0
 * @version 2.1
 */
public class Kanban extends Node {

  /** The current Kanban object */
  public static Kanban current;

  /**
   * Constructor of {@code Kanban}
   *
   * @param obj the {@code HttpBody} object to map
   */
  public Kanban(HttpBody obj) {
    super(obj);

    Board today =
        new Board(
            1,
            "Today",
            Time.monthName(Time.currentMonth())
                + " "
                + Time.currentDay()
                + ", "
                + Time.currentYear(),
            "#fd79a8",
            this);

    new Column(1, "To Do", "jimjimsjimshtodo", today);
    new Column(2, "In Progress", "", today);
    new Column(3, "Done", "", today);
  }

  /**
   * Check out the {@code Kanban} data of current {@code User} from the server.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @return the result object of this action in {@code Result}
   * @since 2.0
   * @version 2.0
   */
  public static Result checkout() {
    Result res = new Result();
    if (User.current == null) {
      StructureRequest req2 = new StructureRequest(false, true);
      req2.setErrorMessage("User not authenticated");
      res.add(req2);
    } else {
      HttpBody cookie = new HttpBody();
      cookie.put("PHPSESSID", User.current.getSessionId());

      HttpRequest req2 = new HttpRequest();
      req2.setRequestUrl("/kanban");
      req2.setRequestMethod("GET");
      req2.setRequestCookie(cookie);
      req2.send();

      if (req2.isSucceeded()) {
        Kanban.current = new Kanban(req2.getResponseBody());
      }
      res.add(req2);
    }
    return res;
  }

  /*
   * Today
   */
  public void generateToday() {
    Kanban kanban = Kanban.current;
    // create node refer to todayboard
    Node todayBoard = kanban.getNode(1);
    Node todo = todayBoard.getNode(1);
    Node inprogress = todayBoard.getNode(2);
    Node done = todayBoard.getNode(3);

    // All todo
    for (Node board : kanban.getChildrenNodes()) {
      if (board.getId() >= 100) {
        for (Node node : board.getChildrenNodes()) {
          Column column = (Column) node;
          for (Node event : column.getChildrenNodes()) {
            if (column.getPreset() == Column.TO_DO) {
              todo.addNode(event);
            } else if (column.getPreset() == Column.IN_PROGRESS) {
              inprogress.addNode(event);
            } else {
              if (!((Event) event).isExpired()) done.addNode(event);
            }
          }
        }
      }
    }
  }

  /*
   * Search
   *
   */
  public ArrayList<Event> searchByName(String name) {
    ArrayList<Event> ret = new ArrayList<Event>();
    Kanban kanban = Kanban.current;

    for (Node board : kanban.getChildrenNodes()) {
      // excluded Today
      if (board.getId() >= 100) {
        for (Node column : board.getChildrenNodes()) {
          for (Node node_event : column.getChildrenNodes()) {
            Event event = (Event) node_event;
            if (event.getTitle().indexOf(name) != -1
                || event.getNote().indexOf(name) != -1
                || Integer.toString(event.getId()).indexOf(name) != -1) {
              ret.add(event);
            }
          }
        }
      }
    }
    return ret;
  }

  public static void main(String[] args) {
    User user = new User();
    user.authenticate("cloudy", "cloudy");
    // System.out.println((user);

    Kanban.checkout();

    System.out.println(Kanban.current);

    // System.out.println(("\ngenerateToday---");
    // Kanban.current.generateToday();
  }
}
