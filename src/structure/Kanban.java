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
  private static Kanban current;

  private Board today;
  private Column todayToDo;
  private Column todayInProgress;
  private Column todayDone;

  /**
   * Constructor of {@code Kanban}
   *
   * @param obj the {@code HttpBody} object to map
   */
  protected Kanban(HttpBody obj) {
    super(obj);

    this.today =
        new Board(
            "Today",
            TimeUtils.currentMonthName()
                + " "
                + TimeUtils.currentDay()
                + ", "
                + TimeUtils.currentYear(),
            "#fd79a8",
            this);

    this.todayToDo = new Column("To Do", "jimjimsjimshtodo", 0, today);
    this.todayInProgress = new Column("In Progress", "", 1, today);
    this.todayDone = new Column("Done", "", 2, today);
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
    if (User.getCurrent() == null) {
      StructureRequest req2 = new StructureRequest(false, true);
      req2.setErrorMessage("User not authenticated");
      res.add(req2);
    } else {
      HttpBody cookie = new HttpBody();
      cookie.put("PHPSESSID", User.getCurrent().getSessionId());

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

    this.todayToDo.clearNodes();
    this.todayInProgress.clearNodes();
    this.todayDone.clearNodes();

    // All todo
    for (Node board : this.getNodes()) {
      if (board.getId() >= 100) {
        for (Node node : board.getNodes()) {
          Column column = (Column) node;
          for (Node event : column.getNodes()) {
            if (column.getPreset() == Column.TO_DO) {
              this.todayToDo.addNode(event);
            } else if (column.getPreset() == Column.IN_PROGRESS) {
              this.todayInProgress.addNode(event);
            } else {
              if (!((Event) event).isOverdue()) this.todayDone.addNode(event);
            }
          }
        }
      }
    }
  }

  public static Kanban getCurrent() {
    return Kanban.current;
  }

  /*
   * Search
   *
   */
  public ArrayList<Node> search(String name) {
    ArrayList<Node> ret = new ArrayList<Node>();
    Kanban kanban = Kanban.current;

    for (Node board : kanban.getNodes()) {
      // excluded Today
      if (board.getId() >= 100) {
        for (Node column : board.getNodes()) {
          for (Node nodeEvent : column.getNodes()) {
            Event event = (Event) nodeEvent;
            if (event.getTitle().toLowerCase().indexOf(name.toLowerCase()) != -1
                || event.getNote().toLowerCase().indexOf(name.toLowerCase()) != -1
                || Integer.toString(event.getId()).indexOf(name) != -1) {
              ret.add(event);
            }
          }
        }
      }
    }
    return ret;
  }
}
