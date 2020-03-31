package structure;

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

    Board board = new Board(1, "Today", "Everyday todo lists111", "#fd79a8");

    Column todo = new Column(2, "Today", "Everyday todo lists");
    Column inprogress = new Column(3, "Inprogress", "Everyday todo lists");
    Column done = new Column(4, "Done", "Everyday todo lists");
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
        Kanban.current = new Kanban(req2.getResponseBody());
      }
      res.add(req2);
    }
    return res;
  }

  // public static void main(String[] args) {
  //   Kanban kb = new Kanban();
  //   HttpBody hb = kb.today.getChildrenNodes();
  //   System.out.print(hb);
  //   Collection v = hb.values();
  //   System.out.print(v);
  //   v.forEach(i -> {
  //     System.out.print(i);
  //   });
  // }

  public static void main(String[] args) {
    User user = new User();
    user.authenticate("cloudy", "cloudy");
    System.out.println(user);

    Kanban.checkout();

    System.out.println(Kanban.current);
  }
}
