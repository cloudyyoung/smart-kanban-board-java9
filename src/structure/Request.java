package structure;

import java.util.Map;

/**
 * The abtract class for all {@code Request} type class.
 *
 * @author Cloudy Young
 * @since 2.0
 * @version 4.0
 */
public abstract class Request {

  /** A boolean to indicate whether the instance is successfully requested. */
  private boolean succeeded;

  /** A boolean to indicate whether the instance has exceptions occured. */
  private boolean excepted;

  /** A boolean to indicate whether the instance is failed on request. */
  private boolean failed;

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
   * Returns a boolean to indicate whether the instance is failed on request
   *
   * @version 4.0
   * @return a boolean to indicate whether the instance is failed on request
   * @see #setFailed(boolean)
   * @see #isFailed()
   */
  public boolean isFailed() {
    return this.failed;
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
   * Sets the failed status to the specified boolean
   *
   * @param is the specified boolean
   * @see #isFailed()
   */
  protected void setFailed(boolean is) {
    this.failed = is;
  }

  /**
   * Returns the response body error of the instance.
   * 
   * @return the error body in {@code HttpBody}
   */
  public HttpBody getResponseBodyError() {
    return this.getResponseBody().getHttpBody("error");
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
   * @version 2.1
   */
  public abstract String getResponseBodyString();

  /**
   * Sets the response body of the instance.
   *
   * @param body the response body in {@code Map}, preferably in {@code HttpBody}
   * @see #setResponseBody(Map)
   * @version 2.1
   */
  protected abstract void setResponseBody(Map<?, ?> body);
}
