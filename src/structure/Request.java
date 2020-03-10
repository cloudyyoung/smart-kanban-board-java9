package structure;

public interface Request{
    public boolean isException();
    public boolean isSucceed();
    public HttpBody getResponseBody();
    public String getResponseBodyString();
}