package structure;

import java.util.Map;

/** 
 * The abtract class for all {@code Request} type class. 
 * @author Cloudy Young
 * @since Kanban 2.0
 * @version 2.0
 */
public abstract class Request {

  /** A boolean to indicate whether the instance is successfully requested. */
  private boolean succeeded;

  /** A boolean to indicate whether the instance has exceptions occured. */
  private boolean excepted;

  /**
   * Returns a boolean to indicate whether the instance has exceptions occured.
   *
   * @return a boolean to indicate whether the instance has exceptions occured
   * @see #setSucceeded(boolean)
   * @see #isSucceeded()
   */
  public boolean isExcepted() {
    return this.excepted;
  }

  /**
   * Returns a boolean to indicate whether the instance is successfully requested
   *
   * @return a boolean to indicate whether the instance is successfully requested
   * @see #setExcepted(boolean)
   * @see #isExcepted()
   */
  public boolean isSucceeded() {
    return this.succeeded;
  }

  /**
   * Sets the succeeded status to the specified boolean
   *
   * @param is the specified boolean
   * @see #isSucceeded()
   */
  protected void setSucceeded(boolean is) {
    this.succeeded = is;
  }

  /**
   * Sets the excepted status to the specified boolean
   *
   * @param is the specified boolean
   * @see #isExcepted()
   */
  protected void setExcepted(boolean is) {
    this.excepted = is;
  }

  /**
   * Returns the response body of the instance.
   *
   * @return the response body in {@code HttpBody}
   * @see #setResponseBody(Map)
   */
  public abstract HttpBody getResponseBody();

  /**
   * Returns the response body of the instance.
   *
   * @return the response body in {@code String}
   */
  public abstract String getResponseBodyString();

  /**
   * Sets the response body of the instance.
   *
   * @param body the response body in {@code Map}, preferably in {@code HttpBody}
   * @see #setResponseBody(Map)
   */
  protected abstract void setResponseBody(Map<?, ?> body);
}
