package structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            "#fd79a8");

    Column todo = new Column(1, "To Do", "jimjimsjimshtodo");
    Column inprogress = new Column(2, "In Progress", "");
    Column done = new Column(3, "Done", "");
    today.addNode(todo);
    today.addNode(inprogress);
    today.addNode(done);
    this.addNode(today);
  }

  /** Default constructor of {@code Kanban}. */
  public Kanban() {}

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

    // store the node of all event
    ArrayList<Event> arr_priority = new ArrayList<Event>();
    // store all events with id to node
    HashMap<Integer, Event> map_todo = new HashMap<Integer, Event>();

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
    // sort all todo and add into the Today
    // arr_priority = sortEventPriority(map_todo);
    // for (Event event : arr_priority) {
    //   todo.addNode(event);
    // }

    // System.out.println("\nTODAY------");
    // // System.out.println(TodayBoard);
    // System.out.println("");
    // System.out.println(todo);
    // System.out.println("");
    // System.out.println(inprogress);
    // System.out.println("");
    // System.out.println(done);
  }

  /**
   * Help function of {@code generateToday()} in {@code Kanban}
   *
   * @param map {@code HashMap}, with (Id, Event), map of all the todo event
   * @return an ArrayList in sorted priority order
   */
  private static ArrayList<Event> sortEventPriority(HashMap<Integer, Event> map) {
    // System.out.println("map==========");
    // System.out.println(map);

    ArrayList<Event> ret = new ArrayList<Event>();
    // copy map
    HashMap<Integer, Event> copymap = new HashMap<Integer, Event>();
    copymap.putAll(map);
    while (copymap.size() > 0) {
      // set the max tp be the first event in the map
      Map.Entry<Integer, Event> entry = copymap.entrySet().iterator().next();
      Event max_event = entry.getValue();
      int max = max_event.getPriority();
      for (Event event : copymap.values()) {
        // weight parameter here
        int priority = event.getPriority();
        if (priority < max) {
          max = priority;
          max_event = event;
        }
      }
      ret.add(max_event);
      copymap.remove(max_event.getId());
    }
    return ret;
  }

  public static void main(String[] args) {
    User user = new User();
    user.authenticate("cloudy", "cloudy");
    System.out.println(user);

    Kanban.checkout();

    // System.out.println(Kanban.current);

    System.out.println("\ngenerateToday---");
    Kanban.current.generateToday();
  }
}
