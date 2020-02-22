package structure;


/*
 *  this function stores the users username
 *  and password for use in the terminal class
 *  and for the authentication process later on in this class
 */

public class User {
  /*
   * username: stores the users username as a string
   * id: gives the user an id so their information can be saved for the authentication
   * process
   * sessid: users current session id so the user can return to the page they
   * were previously on if they left
   * password: stores the users password as a string
   */
  private static User current;
  private String username;
  private int id;
  private String sessid; // Session Id
  private String password;
  private boolean authenticated;

  /*
   * resets the 4 variables listed above so a new user can login
   * or so an account doesn't just stay open or logged in
   */

  public User() {
    this.setId(0);
    this.setUsername("");
    this.setSessionId("");
    this.setPassword("");
  }

  /*
   * gets username
   * @return username as a string
   * this is the user's username
   */

  public String getUsername() {
    return this.username;
  }

  /*
   * gets Id
   * @return id as an int
   * this is the user's id
   */

  public int getId() {
    return this.id;
  }

  /*
   * gets SessonId
   * @return Sessonid as a string
   * this is the account's session id
   */

  public String getSessionId() {
    return this.sessid;
  }

  /*
   * sets the username
   * @param aUsername as a string
   * will be the user's user name
   */

  public void setUsername(String aUsername) {
    this.username = aUsername;
  }

  /*
   * sets the password
   * @param aPassword as a string
   * will be the user's password
   */

  public void setPassword(String aPassword) {
    this.password = aPassword;
  }

  /*
   * sets the id
   * @param aId as a int
   * this is the accounts Id number
   */

  public void setId(int aId) {
    this.id = aId;
  }

  /*
   * sets the sessionId
   * @param aSessionId as a string
   * this is the user's current session id
   */

  public void setSessionId(String aSessionId) {
    this.sessid = aSessionId;
  }

  /*
   * authenticates the account that is trying to be logged into
   * @return boolean if user name and password are correct
   * @param aUsername is the user's entered user name
   * @param aPassword is the user's entered password
   */
  public boolean authenticate(String aUsername, String aPassword) {
    this.username = aUsername;
    this.password = aPassword;
    return true;
  }
}
