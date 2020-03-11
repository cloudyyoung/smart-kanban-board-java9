package structure;

/**
 * <p>The {@code Kanban} class, extends from {@code Node}.</p>
 * <p>The instance should contains {@code Board} object as children nodes.</p>
 * @since Kanban 1.0
 * @version 2.1
 */
public class Kanban extends Node {

  /** The current Kanban object */
  public static Kanban current;

  /**
   * Constructor of {@code Kanban}
   */
  public Kanban(HttpBody obj) {
    super(obj);
  }

  /** Default constructor of {@code Kanban}. */
  public Kanban() {}

  /**
   * <p>Check out the {@code Kanban} data of current {@code User} from the server.</p>
   * <p>This is an <i>action</i> for controllers.</p>
   * @return the result object of this action in {@code Result}
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
}
