package structure;

import java.util.ArrayList;
import java.util.Collection;
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
    // HttpBody board = new HttpBody();

    Board board =
        new Board(
            1,
            "Today",
            Time.monthName(Time.currentMonth())
                + " "
                + Time.currentDay()
                + ", "
                + Time.currentYear(),
            "#fd79a8");

    Column todo = new Column(2, "To Do", "jimjimsjimshtodo");
    Column inprogress = new Column(3, "In Progress", "");
    Column done = new Column(4, "Done", "");
    board.addNode(todo);
    board.addNode(inprogress);
    board.addNode(done);
    this.addNode(board);
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
        System.out.println(req2.getResponseBody());
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
    Node TodayBoard = kanban.getChildrenNodes().get(0);
    for (Node node : kanban.getChildrenNodes()) {
      Board board = (Board) node;
      if (board.getId() == 1) {
        TodayBoard = board;
      }
    }
    Node todo = TodayBoard.getChildrenNodes().get(0);
    Node inprogress = TodayBoard.getChildrenNodes().get(1);
    Node done = TodayBoard.getChildrenNodes().get(2);

    // store the node of all event
    ArrayList<Event> arr_priority = new ArrayList<Event>();
    // store all events with id to node
    HashMap<Integer, Event> map_todo = new HashMap<Integer, Event>();

    // All todo
    for (Node board : kanban.getChildrenNodes()) {
      if (board.getId() >= 100) {
        // Board
        Collection<Node> columns = board.getChildrenNodes();

        for (Node node : columns) {
          Column column = (Column) node;
          if (column.getPreset() == 0) {
            // todo
            for (Node event : column.getChildrenNodes()) {
              map_todo.put(event.getId(), (Event) event);
            }
          } else if (column.getPreset() == 1) {
            // in process
            for (Node node_event : column.getChildrenNodes()) {
              Event event = (Event) node_event;
              if (!event.isExpired()) {
                inprogress.addNode(event);
              }
            }
          } else {
            // done
            for (Node node_event : column.getChildrenNodes()) {
              Event event = (Event) node_event;
              if (!event.isExpired()) {
                done.addNode(event);
              }
            }
          }
        }
      }
    }
    // sort all todo and add into the Today
    arr_priority = sortEventPriority(map_todo);
    for (Event event : arr_priority) {
      todo.addNode(event);
    }

    System.out.println("\nTODAY------");
    // System.out.println(TodayBoard);
    System.out.println("");
    System.out.println(todo);
    System.out.println("");
    System.out.println(inprogress);
    System.out.println("");
    System.out.println(done);
  }

  /**
   * Help function of {@code generateToday()} in {@code Kanban}
   *
   * @param map {@code HashMap}, with (Id, Event), map of all the todo event
   * @return an ArrayList in sorted priority order
   */
  public static ArrayList<Event> sortEventPriority(HashMap<Integer, Event> map) {
    System.out.println("map==========");
    System.out.println(map);

    ArrayList<Event> ret = new ArrayList<Event>();
    // copy map
    HashMap<Integer, Event> copymap = new HashMap<Integer, Event>();
    copymap.putAll(map);
    while (copymap.size() > 0) {
      // set the max tp be the first event in the map
      Map.Entry<Integer, Event> entry = copymap.entrySet().iterator().next();
      Event max_event = entry.getValue();
      int max = getPriority(max_event);
      for (Event event : copymap.values()) {
        // weight parameter here
        int priority = getPriority(event);
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
  /*
   * Algorithm of get the priority value
   */
  public static Integer getPriority(Event event) {
    int timeDifferenceInHours = event.getDueDate().intValue() / 3600;
    int importancePriority = event.getImportanceLevel() * (timeDifferenceInHours / 24);
    int priority = timeDifferenceInHours - importancePriority;
    System.out.println(priority);
    return priority;
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
