package structure;

import java.util.ArrayList;

/**
 * The {@code Kanban} class, extends from {@code Node}.
 *
 * <p>
 * The instance should contains {@code Board} object as children nodes.
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
  private Board overview;
  private Column overviewToDo;
  private Column overviewInProgress;
  private Column overviewDone;

  /**
   * Constructor of {@code Kanban}
   *
   * @param obj the {@code HttpBody} object to map
   */
  protected Kanban(HttpBody obj) {
    super(obj);

    this.today = new Board("Today",
        TimeUtils.currentMonthName() + " " + TimeUtils.currentDay() + ", " + TimeUtils.currentYear(), "#fd79a8", this);

    this.todayToDo = new Column("To Do", "jimjimsjimshtodo", 0, today);
    this.todayInProgress = new Column("In Progress", "", 1, today);
    this.todayDone = new Column("Done", "", 2, today);

    this.today.setSpecialized(true);
    this.todayToDo.setSpecialized(true);
    this.todayInProgress.setSpecialized(true);
    this.todayDone.setSpecialized(true);

    this.overview = new Board("Overview", "Here is an overview of all your tasks", "#242424", this);
    this.overviewToDo = new Column("To Do", "", 0, overview);
    this.overviewInProgress = new Column("In Progress", "", 0, overview);
    this.overviewDone = new Column("Done", "", 0, overview);

    this.overview.setSpecialized(true);
    this.overviewToDo.setSpecialized(true);
    this.overviewInProgress.setSpecialized(true);
    this.overviewDone.setSpecialized(true);
  }

  /**
   * Check out the {@code Kanban} data of current {@code User} from the server.
   *
   * <p>
   * This is an <i>action</i> for controllers.
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

    Column candidates = new Column("Candidates", "", 0, null); // candidates column, store tasks which may be added into today as new
    candidates.setSpecialized(true);

    // First Loop: Adding all the events
    for (Node board : this.getNodes()) {
      if (board.isSpecialized()) {
        continue;
      }

      for (Node column_node : board.getNodes()) {
        for (Node event_node : column_node.getNodes()) {

          Event event = (Event) event_node;
          Column column = (Column) column_node;

          switch(column.getPreset()){

            case Column.TO_DO: // For To Do list: include the past and present task and store the rest as candicates
              if(event.isOnGeneratedToday() || event.isBeforeGeneratedToday()){
                event.setParent(this.todayToDo);
              }else{
                event.setParent(candidates);
              }

            case Column.IN_PROGRESS: // For in Progress list: include the past and present tasks only
              if(event.isOnGeneratedToday() || event.isBeforeGeneratedToday()){
                event.setParent(this.todayInProgress);
              }
              break;

            case Column.DONE: // For Done list: include only the present tasks
              if(event.isOnGeneratedToday()){
                event.setParent(this.todayDone);
              }

          }
        }
      }
    }

    // Second loop: Add new available events from candicates
    for (Node node : candidates.getNodes(Node.SORT_BY_PRIORITY, Node.ORDER_BY_ASC)) {
      Event event = (Event) node;
      if (this.hasEnoughTime((Event) event)) {
        event.setParent(this.todayToDo);
        event.setLastGeneratedDateRequest();
      }
    }
  }

  private boolean hasEnoughTime(Event eventNext) {
    Long totalTime = Long.valueOf(User.getCurrent().getTodayAvailability()) * 3600;
    Long timeAccumulator = 0l;
    for (Node node : this.todayToDo.getNodes()) {
      final Event event = (Event) node;
      timeAccumulator += event.getDurationValue();
    }
    for (Node node : this.todayInProgress.getNodes()) {
      final Event event = (Event) node;
      timeAccumulator += event.getDurationValue();
    }
    for (Node node : this.todayDone.getNodes()) {
      final Event event = (Event) node;
      timeAccumulator += event.getDurationValue();
    }
    return (eventNext.getDurationValue() + timeAccumulator) <= totalTime ? true : false;
  }

  /*
   * Overview
   */
  public void generateOverview() {

    this.overviewToDo.clearNodes();
    this.overviewInProgress.clearNodes();
    this.overviewDone.clearNodes();

    // All todo
    for (Node board : this.getNodes()) {
      if (board.isSpecialized()) { // Skip specialized boards
        continue;
      }

      for (Node node : board.getNodes()) {
        Column column = (Column) node;
        for (Node event : column.getNodes()) {

          switch(column.getPreset()){
            case Column.TO_DO:
              event.setParent(this.overviewToDo);
              break;

            case Column.IN_PROGRESS:
              event.setParent(this.todayInProgress);
              break;

            case Column.DONE:
              if (!((Event) event).isOverdue()) { // Only include the task when it's not overdue
                event.setParent(this.todayDone);
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
