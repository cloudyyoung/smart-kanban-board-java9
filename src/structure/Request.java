package structure;

public abstract class Request {
  
  private boolean succeeded;
  private boolean excepted;
  
  public boolean isExcepted() {
    return this.excepted;
  }

  public boolean isSucceeded() {
    return this.succeeded;
  }

  public void setSucceeded(boolean is) {
    this.succeeded = is;
  }

  public void setExcepted(boolean is) {
    this.excepted = is;
  }

  public abstract HttpBody getResponseBody();
  protected abstract void setResponseBody(HttpBody body);

}
