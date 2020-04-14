package structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.google.gson.*;

/**
 * The class {@code User} instance represents an Account which stores the users informations and
 * provides needed methods to interact.
 *
 * @since 1.0
 * @version 2.1
 */
public class User {

  /** The current signed-in user instance. */
  private static User current;

  /** The username of the account. */
  private String username;

  /** The user id of the account. */
  private int id;

  /** The session id of the account. For the server requests to be proceeded properly. */
  private String sessionId;

  /** The password of the account. */
  private String password;

  /**
   * The authentication status of the account. {@code true} represents the instance user is
   * authenticated with the server successfully. {@code false} otherwise.
   */
  private boolean authenticated;

  /** A boolean to indicate if the account is existing on the server. */
  private boolean existing = false;

  /** Default constructor of {@code User}. */
  public User() {}

  /**
   * Returns the username of the account.
   *
   * @return the username of the account
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns the user id of the account.
   *
   * @return the user id of the account
   */
  public int getId() {
    return this.id;
  }

  /**
   * Returns the session id of the account.
   *
   * @return the session id of the account
   */
  public String getSessionId() {
    return this.sessionId;
  }

  /**
   * Sets the username of the account.
   *
   * @param username the username to be set
   */
  private void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets the password of the account.
   *
   * @param password the password to be set
   */
  private void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the password of the account.
   *
   * @return the password of the account
   */
  private String getPassword() {
    return this.password;
  }

  /**
   * Return the authentication status of the account.
   *
   * @return {@code true} if the instance user is authenticated with the server successfully. {@code
   *     false} otherwise.
   */
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  /**
   * Sets the user id of the account.
   *
   * @param id the user id to be set
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Sets the session id of the account.
   *
   * @param sessionId the session id to be set
   */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  /** {@inheritDoc} */
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
   * Authenticates the user account with the server.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @param username the username of the account
   * @param password the pssword of the account
   * @return the result object of this action
   * @since 1.0
   * @version 2.1
   */
  public Result signInRequest(String username, String password) {
    Result res = new Result();

    this.setUsername(username);
    this.setPassword(password);

    // Request server to sign in
    HttpBody param = new HttpBody();
    param.put("username", this.getUsername());
    param.put("password", this.getPassword());

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/users/authentication");
    req.setRequestMethod("PUT");
    req.setRequestBody(param);
    req.send();

    res.add(req);

    if (req.isSucceeded()) {
      HttpBody response = req.getResponseBody();
      HttpBody cookie = req.getResponseCookie();

      this.setId(response.getInt("id"));
      this.setSessionId(cookie.getString("PHPSESSID"));
      this.existing = true;

      // Sign in locally
      this.authenticated = true;
      User.current = this;
    }

    return res;
  }

  public Result signInLocalRequest() {

    HttpBody body = User.readLocalFile();

    String username = body.getString("username");
    String password = body.getString("password");

    return this.signInRequest(username, password);
  }

  /**
   * Returns the existing status of the instance account.
   *
   * @return the boolean to represents the account existing status
   * @since 2.0
   * @version 2.1
   */
  public boolean isExisting() {
    return this.existing;
  }

  public Result signOut() {
    this.authenticated = false;
    User.current = null;

    StructureRequest req = new StructureRequest(true, false, this);
    Result res = new Result();
    res.add(req);
    File myObj = new File("temp.meonc");
    myObj.delete();
    return res;
  }

  private static void writeLocalFile(Map<?, ?> body) {
    try {
      FileWriter myWriter = new FileWriter("temp.meonc");
      String str = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(body);
      myWriter.write(EncrytionUtils.encrypt(str, "secret"));
      myWriter.close();
    } catch (IOException e) {
      // System.out.println("An error occurred.");
      // e.printStackTrace();
      // Fail silently
    }
  }

  private static HttpBody readLocalFile() {
    int ch;
    FileReader fr = null;
    String encryptStr = "";
    try {
      fr = new FileReader("temp.meonc");

      // read from FileReader till the end of file
      while ((ch = fr.read()) != -1) encryptStr += (char) ch;

      // close the file
      fr.close();
    } catch (FileNotFoundException e) {
      // System.out.println("File not found");
      // Fail silently
    } catch (IOException e) {
      // System.out.println("IO Exception");
      // Fail silently
    }

    String str = EncrytionUtils.decrypt(encryptStr, "secret");
    return new HttpBody(new Gson().fromJson(str, Map.class));
  }

  /**
   * Registers the instance account in the server.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @param username the username of the account
   * @param password the password of the account
   * @param secQues the security question of the account
   * @param secAns the security answer of the the account
   * @return the result object of this action
   * @since 2.0
   * @version 2.1
   */
  public Result signUpRequest(String username, String password, String secQues, String secAns) {
    Result res = new Result();

    this.setUsername(username);
    this.setPassword(password);

    HttpBody param = new HttpBody();
    param.put("username", this.getUsername());
    param.put("password", this.getPassword());
    param.put("security_question", secQues);
    param.put("security_answer", secAns);

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/users/");
    req.setRequestMethod("POST");
    req.setRequestBody(param);
    req.send();

    res.add(req);

    if (req.isSucceeded()) {
      this.signInRequest(username, password);
    }

    return res;
  }

  public static Result authenticationRequest(String username, String password) {
    User user = new User();
    Result res = user.signInRequest(username, password);
    if (res.isSucceeded()) {
      User.current = user;
    }
    return res;
  }

  public static Result registrationRequest(
      String username, String password, String secQues, String secAns) {
    User user = new User();
    return user.signUpRequest(username, password, secQues, secAns);
  }

  public static User getCurrent() {
    return User.current;
  }

  public static void main(String[] args) throws IOException {
    writeLocalFile(new HttpBody());

    System.out.println(readLocalFile());
    User user = new User();
    user.signOut();
  }
}
