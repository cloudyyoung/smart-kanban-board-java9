package structure;

import java.util.ArrayList;

/**
 * The class for all <i>action</i> results.
 */
public final class Result {

  /** An {@code ArrayList} which stores all the {@code Request} related to the instance. */
  private ArrayList<Request> list = new ArrayList<Request>();
  
  /** An {@code ArrayList} which stores all the excepted {@code Request} in {@link #list}.  */
  private ArrayList<Request> exceptionList = new ArrayList<Request>();

  /** A boolean to indicate whether any of the related {@code Request} in {@link #list} has exception occured. */
  private boolean excepted = false;

  /** A boolean to indicate whether the all the related {@code Request} in {@link #list} are successfully requested. */
  private boolean succeeded = false;

  /** Default constructor of {@code Result}. */
  public Result() {}

  /**
   * Adds a new related {@code Request} to the {@link list}.
   * @see #list
   * @see #remove(Request)
   */
  public void add(Request add) {
    if (add == null) {
      return;
    }
    this.list.add(add);
    this.checkException();
  }

  /**
   * Removes a existing related {@code Request} in the {@link list}.
   * @param remove the {@code Request} object to be removed
   * @see #list
   * @see #add(Request)
   */
  public void remove(Request remove) {
    this.list.remove(remove);
    this.checkException();
  }

  /**
   * Removes a existing related {@code Request} in the {@link list}.
   * @param remove the index of object to be removed
   * @see #list
   * @see #add(Request)
   */
  public void remove(int index) {
    this.list.remove(index);
    this.checkException();
  }

  /**
   * Checks whether any of the related {@code Request} in {@link #list} has exception occured.
   */
  private void checkException() {
    exceptionList.clear();
    boolean changed = false;
    for (Request each : list) {
      if (each.isExcepted()) {
        this.setExcepted(true);
        this.exceptionList.add(each);
        changed = true;
      }
      if (!each.isSucceeded()) {
        this.setSucceeded(false);
        changed = true;
      }
      if (changed) {
        return;
      }
    }
    this.setExcepted(false);
    this.setSucceeded(true);
  }

  /**
   * Return a boolean to indicate whether any of the related {@code Request} in {@link #list} has exception occured.
   * @return {@code true} if there is at least one related request has exception occured.
   *         {@code false} otherwise.
   * @see #setExcepted(boolean)
   * @see #isSucceeded()
   */
  public boolean isExcepted() {
    return this.excepted;
  }

  /**
   * Returns a boolean to indicate whether the all the related {@code Request} in {@link #list} are successfully requested.
   * @return {@code true} if all the related requests are succeeded.
   *         {@code false} otherwise.
   * @see #setSucceeded(boolean)
   * @see #isExcepted()
   */
  public boolean isSucceeded() {
    return this.succeeded;
  }

  /**
   * Sets {@link #excepted} to indicate whether any of the related {@code Request} in {@link #list} has exception occured.
   * @param is {@code true} if there is at least one related request has exception occured.
   *           {@code false} otherwise.
   */
  private void setExcepted(boolean is) {
    this.excepted = is;
  }

  /**
   * Sets {@link #succeeded} to indicate whether the all the related {@code Request} in {@link #list} are successfully requested.
   * @param is {@code true} if all the related requests are succeeded.
   *           {@code false} otherwise.
   */
  private void setSucceeded(boolean is) {
    this.succeeded = is;
  }

  /**
   * Returns a list of all related requests which have exceptions occured.
   * @return a list of all related requests which have exceptions occured
   */
  public ArrayList<Request> getExceptions() {
    return new ArrayList<Request>(this.exceptionList);
  }

  /**
   * Returns the first element of all related requests which have exceptions occured.
   * @return the first element of all related requests which have exceptions occured.
   */
  public Request getException() {
    if (this.exceptionList.size() > 0) {
      return this.exceptionList.get(0);
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return "Result ("
        + "isSucceeded: "
        + this.isSucceeded()
        + ", isExcepted: "
        + this.isExcepted()
        + ", Request: "
        + this.list.toString()
        + ")";
  }
}
