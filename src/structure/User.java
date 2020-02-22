package structure;

import java.util.Map;
import java.util.HashMap;

public class User {
  private static User current;

  private String username;
  private int id;
  private String sessid; // Session Id
  private String password;
  private boolean authenticated;

  public User() {
    this.setId(0);
    this.setUsername("");
    this.setSessionId("");
    this.setPassword("");
  }

  public String getUsername() {
    return this.username;
  }

  public int getId() {
    return this.id;
  }

  public String getSessionId() {
    return this.sessid;
  }

  public void setUsername(String aUsername) {
    this.username = aUsername;
  }

  public void setPassword(String aPassword) {
    this.password = aPassword;
  }

  public void setId(int aId) {
    this.id = aId;
  }

  public void setSessionId(String aSessionId) {
    this.sessid = aSessionId;
  }

  public boolean authenticate() {
    HashMap<Object, Object> param = new HashMap<Object, Object>();
    param.put("username", this.username);
    param.put("password", this.password);

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/users/authentication");
    req.setRequestMethod("PUT");
    req.setRequestBody(param);
    req.send();

    System.out.println(req.getResponseStatusCode());
    System.out.println(req.getResponseBody());

    return true;
  }

  public boolean isAuthenticated(){
    return this.authenticated;
  }

  public static User authentication(String username, String password){
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    boolean succeed = user.authenticate();
    if(succeed){
      User.current = user;
    }
    return user;
  }

  public static boolean hasCurrent(){
    return User.current != null && User.current.isAuthenticated();
  }

  public static User getCurrent(){
    return User.current;
  }
}
