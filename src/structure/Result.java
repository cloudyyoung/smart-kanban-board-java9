package structure;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The class for all <i>action</i> results.
 *
 * @author Cloudy Young
 * @since 2.0
 * @version 4.0
 */
public final class Result {

  /** An {@code ArrayList} which stores all the {@code Request} related to the instance. */
  private final ArrayList<Request> list = new ArrayList<Request>();

  /** An {@code ArrayList} which stores all the excepted {@code Request} in {@link #list}. */
  private final ArrayList<Request> exceptionList = new ArrayList<Request>();

  /** An {@code ArrayList} which stores all the failed {@code Request} in {@link #list}. */
  private final ArrayList<Request> failList = new ArrayList<Request>();

  /**
   * A boolean to indicate whether any of the related {@code Request} in {@link #list} has exception
   * occured.
   */
  private boolean excepted = false;

  /**
   * A boolean to indicate whether the all the related {@code Request} in {@link #list} are
   * successfully requested.
   */
  private boolean succeeded = false;

  /**
   * A boolean to indicate whether any of the related {@code Request} in {@link #list} has failure
   * occured.
   */
  private boolean failed = false;

  /** Default constructor of {@code Result}. */
  public Result() {}

  /**
   * Adds a new related {@code Request} to the {@link list}.
   *
   * @version 4.0
   * @param add the related request to be added
   * @see #list
   * @see #remove(Request)
   */
  public void add(Request add) {
    if (add == null) {
      return;
    }
    this.list.add(add);
    this.checkout();
  }

  /**
   * Copies and adds all requests from a given {@code Result} instance.
   *
   * @version 4.0
   * @param res the {@code Result} instance 
   */
  public void addAll(Result res) {
    this.list.addAll(res.list);
  }

  /**
   * Adds all given requests.
   *
   * @version 4.0
   * @param res the {@code Result} instance 
   */
  public void addAll(Request... add) {
    for (Request each : add) {
      this.list.add(each);
    }
  }

  /**
   * Adds all requests from given {@code Collection}.
   *
   * @version 4.0
   * @param res the {@code Collection<Request>} instance 
   */
  public void addAll(Collection<Request> add) {
    this.addAll(add);
  }

  /**
   * Removes a existing related {@code Request} in the {@link list}.
   *
   * @param remove the {@code Request} object to be removed
   * @see #list
   * @see #add(Request)
   */
  public void remove(Request remove) {
    this.list.remove(remove);
    this.checkout();
  }

  /**
   * Removes a existing related {@code Request} in the {@link list}.
   *
   * @param index the index of object to be removed
   * @see #list
   * @see #add(Request)
   */
  public void remove(int index) {
    this.list.remove(index);
    this.checkout();
  }

  /** Checks whether any of the related {@code Request} in {@link #list} has exception occured. */
  private void checkout() {
    exceptionList.clear();
    boolean changed = false;
    for (Request each : list) {
      if (each.isExcepted()) {
        this.setExcepted(true);
        this.exceptionList.add(each);
        changed = true;
      } else if (each.isFailed()) {
        this.setFailed(true);
        this.failList.add(each);
        changed = true;
      }

      if (changed) {
        return;
      }
    }
    this.setExcepted(false);
    this.setFailed(false);
    this.setSucceeded(true);
  }

  /**
   * Return a boolean to indicate whether any of the related {@code Request} in {@link #list} has
   * exception occured.
   *
   * @return {@code true} if there is at least one related request has exception occured. {@code
   *     false} otherwise.
   * @see #setExcepted(boolean)
   * @see #isSucceeded()
   */
  public boolean isExcepted() {
    return this.excepted;
  }

  /**
   * Returns a boolean to indicate whether the all the related {@code Request} in {@link #list} are
   * successfully requested.
   *
   * @return {@code true} if all the related requests are succeeded. {@code false} otherwise.
   * @see #setSucceeded(boolean)
   * @see #isExcepted()
   */
  public boolean isSucceeded() {
    return this.succeeded;
  }

  /**
   * Return a boolean to indicate whether any of the related {@code Request} in {@link #list} has
   * failure occured.
   *
   * @version 4.0
   * @return {@code true} if there is at least one related request has failure occured. {@code
   *     false} otherwise.
   * @see #setFailed(boolean)
   * @see #isFailed()
   */
  public boolean isFailed() {
    return this.failed;
  }

  /**
   * Sets {@link #excepted} to indicate whether any of the related {@code Request} in {@link #list}
   * has exception occured.
   *
   * @param is {@code true} if there is at least one related request has exception occured. {@code
   *     false} otherwise.
   */
  private void setExcepted(boolean is) {
    this.excepted = is;
  }

  /**
   * Sets {@link #succeeded} to indicate whether the all the related {@code Request} in {@link
   * #list} are successfully requested.
   *
   * @param is {@code true} if all the related requests are succeeded. {@code false} otherwise.
   */
  private void setSucceeded(boolean is) {
    this.succeeded = is;
  }

  /**
   * Sets {@link #excepted} to indicate whether any of the related {@code Request} in {@link #list}
   * has failure occured.
   *
   * @version 4.0
   * @param is {@code true} if there is at least one related request has failure occured. {@code
   *     false} otherwise.
   */
  private void setFailed(boolean is) {
    this.failed = is;
  }

  /**
   * Returns a list of all related requests which have exceptions occured.
   *
   * @return a list of all related requests which have exceptions occured
   */
  public ArrayList<Request> getExceptions() {
    this.checkout();
    return new ArrayList<Request>(this.exceptionList);
  }

  /**
   * Returns a list of all related requests which have failures occured.
   *
   * @version 4.0
   * @return a list of all related requests which have failures occured
   */
  public ArrayList<Request> getFails() {
    this.checkout();
    return new ArrayList<Request>(this.exceptionList);
  }

  /**
   * Returns the first element of all related requests which have exceptions occured.
   *
   * @return the first element of all related requests which have exceptions occured.
   */
  public Request getException() {
    this.checkout();
    if (this.exceptionList.size() > 0) {
      return this.exceptionList.get(0);
    } else {
      return null;
    }
  }

  /**
   * Returns the first element of all related requests which have failures occured.
   *
   * @version 4.0
   * @return the first element of all related requests which have failures occured.
   */
  public Request getFail() {
    this.checkout();
    if (this.failList.size() > 0) {
      return this.failList.get(0);
    } else {
      return null;
    }
  }

  /**
   * Returns the failure error of the instance.
   * 
   * @version 4.0
   * @return the failure error body in{@code HttpBody}
   */
  public HttpBody getFailError() {
    Request req = this.getFail();
    HttpBody body = null;
    if (req != null) {
      body = req.getResponseBodyError();
    }
    return body;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "Result ("
        + "isSucceeded: "
        + this.isSucceeded()
        + ", isFailed: "
        + this.isFailed()
        + ", isExcepted: "
        + this.isExcepted()
        + ", Request: "
        + this.list.toString()
        + ")";
  }
}
