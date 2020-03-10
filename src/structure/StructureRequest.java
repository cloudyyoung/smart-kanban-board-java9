package structure;

import com.google.gson.*;

public final class StructureRequest implements Request {

  private boolean excepted;
  private boolean succeed;
  private Object instance;
  private String responseBody;

  public StructureRequest() {}

  public StructureRequest(Object instance) {
    this.instance = instance;
  }

  public StructureRequest(boolean succeed, boolean excepted){
      this.setSucceed(succeed);
      this.setException(excepted);
  }

  @Override
  public boolean isExcepted() {
    return this.excepted;
  }

  @Override
  public boolean isSucceed() {
    return this.succeed;
  }

  public void setSucceed(boolean is){
    this.succeed = is;
  }

  public void setException(boolean is){
      this.excepted = is;
  }

  public Object getInstance() {
    return this.instance;
  }

  public void setInstance(Object instance) {
    this.instance = instance;
  }

  public void setResponseBody(HttpBody param) {
    this.responseBody =
        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(param);
  }

  // If succeed return whats added else null
  public HttpBody addResponseBody(Object key, Object value) {
    try {
      HttpBody body = this.getResponseBody();
      if (body == null) {
        body = new HttpBody();
      }
      body.put(key, value);
      this.setResponseBody(body);
      return body;
    } catch (Throwable e) {
      return null;
    }
  }

  @Override
  public HttpBody getResponseBody() {
    return new Gson().fromJson(this.responseBody, HttpBody.class);
  }

  public String getResponseBodyString() {
    return this.responseBody;
  }

  private String getInstanceString() {
    if (this.instance != null) {
      return this.instance.toString();
    } else {
      return null;
    }
  }

  public String toString() {
    return "StructureRequest ("
        + "instance: "
        + this.getInstanceString()
        + ", responseBody: "
        + this.getResponseBodyString()
        + ")";
  }

  private static HttpBody getErrorTemplate() {
    HttpBody error = new HttpBody();
    error.put("code", 400);
    error.put("message", "");
    error.put("details", null);
    HttpBody body = new HttpBody();
    body.put("error", error);
    return body;
  }

  public static void main(String[] args) {
    StructureRequest req = new StructureRequest();
    req.addResponseBody("error", "222");
    System.out.println(req);
    System.out.println(StructureRequest.getErrorTemplate());
  }
}
