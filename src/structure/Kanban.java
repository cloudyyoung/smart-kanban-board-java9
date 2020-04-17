package structure;

import java.util.ArrayList;

/**
 * The {@code Kanban} class, extends from {@code Node}.
 *
 * <p>The instance should contains {@code Board} object as children nodes.
 *
 * @author Cloudy Young, Jimschenchen
 * @since 1.0
 * @version 4.0
 */
public class Kanban extends Node {

  /** The current Kanban object */
  private static Kanban current;

  /** The Today board instance */
  private Board today;

  /** The Column To Do in Today board instance */
  private Column todayToDo;

  /** The Column In Progress in Today board instance */
  private Column todayInProgress;

  /** The Column Done in Today board instance */
  private Column todayDone;

  /** The Overview board instance */
  private Board overview;

  /** The Column To Do in Overview board instance */
  private Column overviewToDo;

  /** The Column In Progress in Overview board instance */
  private Column overviewInProgress;

  /** The Column Done in Overview board instance */
  private Column overviewDone;

  /**
   * Constructor of {@code Kanban}
   *
   * @version 4.0
   * @param obj the {@code HttpBody} object to map
   */
  protected Kanban(HttpBody obj) {
    super(obj);

    // Creates Today & Overview boards
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

    this.todayToDo = new Column("To Do", "jimjimsjimshtodo", Column.TO_DO, today);
    this.todayInProgress = new Column("In Progress", "", Column.IN_PROGRESS, today);
    this.todayDone = new Column("Done", "", Column.DONE, today);

    this.today.setSpecialized(true);
    this.todayToDo.setSpecialized(true);
    this.todayInProgress.setSpecialized(true);
    this.todayDone.setSpecialized(true);

    this.overview = new Board("Overview", "Here is an overview of all your tasks", "#58b089", this);
    this.overviewToDo = new Column("To Do", "", Column.TO_DO, overview);
    this.overviewInProgress = new Column("In Progress", "", Column.IN_PROGRESS, overview);
    this.overviewDone = new Column("Done", "", Column.DONE, overview);

    this.overview.setSpecialized(true);
    this.overviewToDo.setSpecialized(true);
    this.overviewInProgress.setSpecialized(true);
    this.overviewDone.setSpecialized(true);
  }

  /**
   * Check out the {@code Kanban} data of current {@code User} from the server.
   *
   * @return the result object of this action in {@code Result}
   * @since 2.0
   * @version 2.0
   */
  public static Result checkout() {
    Result res = new Result();
    if (User.getCurrent() == null) {
      StructureRequest req2 = new StructureRequest(false, true, false);
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

  /**
   * Generates the Today board.
   *
   * @version 4.0
   */
  public void generateToday() {

    // Clears all existing events
    this.todayToDo.clearNodes();
    this.todayInProgress.clearNodes();
    this.todayDone.clearNodes();

    // Creates a temporary column for candidates
    // Candidate list stores all events that will be evaluated and added to Today if
    // possible
    Column candidates = new Column("Candidates", "", 0, null);
    candidates.setSpecialized(true);

    // First Loop: Adds all the events
    for (Node board : this.getNodes()) {

      if (board.isSpecialized()) { // Skips for all specialized boards, such as Today and Overview
        continue;
      }

      for (Node column_node : board.getNodes()) {
        for (Node event_node : column_node.getNodes()) {

          Event event = (Event) event_node;
          Column column = (Column) column_node;

          switch (column.getPreset()) {
            case Column
                .TO_DO: // For To Do list: includes the past and present task and store the rest as
              // candicates
              if (event.isOnGeneratedToday()) {
                event.setParent(this.todayToDo);
              } else if (event.isBeforeGeneratedToday()) {
                event.setParent(this.todayToDo);
                event.setLastGeneratedDateRequest();
              } else {
                event.setParent(candidates);
              }
              break;

            case Column
                .IN_PROGRESS: // For in Progress list: includes the past and present tasks only
              if (event.isOnGeneratedToday()) {
                event.setParent(this.todayInProgress);
              } else if (event.isBeforeGeneratedToday()) {
                event.setParent(this.todayInProgress);
                event.setLastGeneratedDateRequest();
              }
              break;

            case Column.DONE: // For Done list: includes only the present tasks
              if (event.isOnGeneratedToday()) {
                event.setParent(this.todayDone);
              }
              break;

            default:
              // Skips the Node
              break;
          }
        }
      }
    }

    // Second loop: Adds new available events from candicates
    for (Node node : candidates.getNodes(Node.SORT_BY_PRIORITY, Node.ORDER_BY_ASC)) {
      Event event = (Event) node;
      if (this.hasEnoughTime((Event) event)) {
        event.setParent(this.todayToDo);
        event.setLastGeneratedDateRequest();
      }
    }
  }

  /**
   * Returns if the Today board has enough tiem for the given event to be added, this is a helper
   * function for {@code generateToday}.
   *
   * @version 4.0
   * @param eventNext the candidate event
   * @return if the Today board has enough tiem for the given event to be added
   */
  private boolean hasEnoughTime(Event eventNext) {
    Long totalTime = Long.valueOf(User.getCurrent().getTodayAvailability()) * 3600;
    Long timeAccumulator = 0L;
    ArrayList<Node> admissions = new ArrayList<Node>();
    admissions.addAll(this.todayToDo.getNodes());
    admissions.addAll(this.todayInProgress.getNodes());
    admissions.addAll(this.todayDone.getNodes());
    for (Node node : admissions) {
      final Event event = (Event) node;
      timeAccumulator += event.getDurationValue();
    }
    return (eventNext.getDurationValue() + timeAccumulator) <= totalTime ? true : false;
  }

  /**
   * Generates the Overview board.
   *
   * @version 4.0
   */
  public void generateOverview() {

    // Clears all existing events
    this.overviewToDo.clearNodes();
    this.overviewInProgress.clearNodes();
    this.overviewDone.clearNodes();

    // All todo
    for (Node board : this.getNodes()) {
      if (board.isSpecialized()) { // Skips specialized boards
        continue;
      }

      for (Node node : board.getNodes()) {
        Column column = (Column) node;
        for (Node event : column.getNodes()) {

          switch (column.getPreset()) {
            case Column.TO_DO:
              event.setParent(this.overviewToDo);
              break;

            case Column.IN_PROGRESS:
              event.setParent(this.overviewInProgress);
              break;

            case Column.DONE:
              if (!((Event) event).isOverdue()) { // Only includes the present tasks
                event.setParent(this.overviewDone);
              }
              break;

            default:
              // Skip the Node
              break;
          }
        }
      }
    }
  }

  /**
   * Returns the current {@code Kanban} board.
   *
   * @version 4.0
   * @return the current {@code Kanban} board instance.
   */
  public static Kanban getCurrent() {
    return Kanban.current;
  }

  /**
   * Returns a list which contains all the events that contains the given key word in their title.
   *
   * @version 4.0
   * @param name The keyword to search
   * @return a list which contains all the events that contains the given key word in their title
   */
  public ArrayList<Node> search(String name) {
    ArrayList<Node> ret = new ArrayList<Node>();
    Kanban kanban = Kanban.current;

    for (Node board : kanban.getNodes()) {
      // excluded Today
      if (!board.isSpecialized()) {
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
