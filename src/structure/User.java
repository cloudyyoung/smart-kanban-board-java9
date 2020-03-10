package structure;

/**
 * this function stores the users username and password for use in the terminal class and for the
 * authentication process later on in this class
 */
public class User {
  /**
   * username: stores the users username as a string id: gives the user an id so their information
   * can be saved for the authentication process sessionId: users current session id so the user can
   * return to the page they were previously on if they left password: stores the users password as
   * a string current: the current signed in user
   */
  public static User current;

  private String username;
  private int id;
  private String sessionId;
  private String password;
  private boolean authenticated;

  /**
   * resets the 4 variables listed above so a new user can login or so an account doesn't just stay
   * open or logged in
   */
  public User() {
    this.setUsername("");
    this.setSessionId("");
    this.setPassword("");
  }

  /**
   * gets username
   *
   * @return username as a string this is the user's username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * gets Id
   *
   * @return id as an int this is the user's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * gets SessonId
   *
   * @return Sessonid as a string this is the account's session id
   */
  public String getSessionId() {
    return this.sessionId;
  }

  /**
   * sets the username
   *
   * @param aUsername as a string will be the user's user name
   */
  public void setUsername(String aUsername) {
    this.username = aUsername;
  }

  /**
   * sets the password
   *
   * @param aPassword as a string will be the user's password
   */
  public void setPassword(String aPassword) {
    this.password = aPassword;
  }

  /**
   * get the password
   *
   * @return password as string
   */
  private String getPassword() {
    return this.password;
  }

  /**
   * current user is authenticated or not
   *
   * @return true is authenticated and false if not
   */
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  /**
   * sets the id
   *
   * @param aId as a int this is the accounts Id number
   */
  public void setId(Integer aId) {
    this.id = aId;
  }

  /**
   * sets the sessionId
   *
   * @param aSessionId as a string this is the user's current session id
   */
  public void setSessionId(String aSessionId) {
    this.sessionId = aSessionId;
  }

  /**
   * To string
   *
   * @return a detailed string of the instance
   */
  public String toString() {
    return "User (username: "
        + this.getUsername()
        + ", password: "
        + this.getPassword()
        + ", id: "
        + this.getId()
        + ", sessionId: "
        + this.getSessionId()
        + ")";
  }

  /**
   * authenticates the account that is trying to be logged into
   *
   * @return boolean if user name and password are correct
   */
  public Result authenticate() {
    return this.authenticate(this.getUsername(), this.getPassword());
  }

  /**
   * authenticates the account that is trying to be logged into
   *
   * @return boolean if user name and password are correct
   * @param aUsername is the user's entered user name
   * @param aPassword is the user's entered password
   */
  public Result authenticate(String aUsername, String aPassword) {
    this.setUsername(aUsername);
    this.setPassword(aPassword);

    HttpBody param = new HttpBody();
    param.put("username", this.getUsername());
    param.put("password", this.getPassword());

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/users/authentication");
    req.setRequestMethod("PUT");
    req.setRequestBody(param);
    req.send();

    // System.out.println(req.getResponseStatusCode());
    // System.out.println(req.getResponseBody());

    if (req.isSucceeded()) {
      HttpBody res = req.getResponseBody();
      HttpBody cookie = req.getResponseCookie();
      this.setId(res.getInt("id"));
      this.setSessionId(cookie.getString("PHPSESSID"));
      this.authenticated = true;
      User.current = this;
    }

    Result res = new Result();
    res.add(req);
    return res;
  }


  public static void main(String[] args) {
    User user = new User();
    user.authenticate("cloudy", "cloudy");
    System.out.println(user);

    Kanban.fetch();
    System.out.println(Kanban.current);
  }
}
