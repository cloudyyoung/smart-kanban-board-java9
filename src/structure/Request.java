package structure;

public interface Request {
  public boolean isExcepted();

  public boolean isSucceed();

  public HttpBody getResponseBody();

  public String getResponseBodyString();
}
