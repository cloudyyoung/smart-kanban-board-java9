package structure;

public class User {
  private String username;
  private int id;
  private String sessid; // Session Id
  private String password;

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

  public boolean authenticate(String aUsername, String aPassword) {
    this.username = aUsername;
    this.password = aPassword;
    return true;
  }

  public boolean authenticate() {
    return true;
  }
}
