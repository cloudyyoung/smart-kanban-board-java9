package structure;

import java.util.Map;

import com.google.gson.*;

public final class StructureRequest extends Request {

  private Object instance;
  private String responseBody;

  public StructureRequest() {}

  public StructureRequest(Object instance) {
    this.instance = instance;
  }

  public StructureRequest(boolean succeeded, boolean excepted) {
    this.setSucceeded(succeeded);
    this.setExcepted(excepted);
  }

  public StructureRequest(boolean succeeded, boolean excepted, Object instance) {
    this.setSucceeded(succeeded);
    this.setExcepted(excepted);
    this.instance = instance;
  }

  @Override
  public void setExcepted(boolean is) {
    super.setExcepted(is);
    if (is) {
      HttpBody body = StructureRequest.getErrorTemplate();
      this.responseBody = body.toString();
    }
  }

  public Object getInstance() {
    return this.instance;
  }

  public void setInstance(Object instance) {
    this.instance = instance;
  }

  public void setResponseBody(Map<?, ?> param) {
    this.responseBody =
        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(param);
  }

  // If succeeded return whats added else null
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

  public HttpBody setErrorMessage(String message) {
    HttpBody body = new Gson().fromJson(this.responseBody, HttpBody.class);
    HttpBody error = body.getHttpBody("error");
    error.put("message", message);
    this.responseBody = body.toString();
    return body;
  }

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
