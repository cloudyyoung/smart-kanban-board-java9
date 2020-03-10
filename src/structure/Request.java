package structure;

public interface Request {
  public boolean isExcepted();

  public boolean isSucceeded();

  public HttpBody getResponseBody();

  public String getResponseBodyString();
}
