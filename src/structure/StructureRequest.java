package structure;

import java.util.Map;

import com.google.gson.*;

/**
 * The class for storing requests within models.
 *
 * <p>This class is extending from {@link Request} class.
 *
 * @author Cloudy Young
 * @since 2.0
 * @version 2.0
 */
public final class StructureRequest extends Request {

  /** The related instance of the instance */
  private Object instance;

  /** The response body of the instance */
  private String responseBody;

  /** Default constructor of {@code StructureRequests}. */
  public StructureRequest() {}

  /**
   * Constructor of {@code StructureRequests}, provide instance.
   *
   * @param instance the related instance of the instance
   */
  public StructureRequest(Object instance) {
    this.instance = instance;
  }

  /**
   * Constructor of {@code StructureRequests}, provide suuceeded and excepted.
   *
   * @param succeeded the boolean to represent whether the instance is successfully requested
   * @param excepted the boolean to represent whether the instance has exceptions occured
   */
  public StructureRequest(boolean succeeded, boolean excepted) {
    this.setSucceeded(succeeded);
    this.setExcepted(excepted);
  }

  /**
   * Constructor of {@code StructureRequests}, provide succeeded, excepted and related object.
   *
   * @param succeeded the boolean to represent whether the instance is successfully requested
   * @param excepted the boolean to represent whether the instance has exceptions occured
   * @param instance the related instance of the instance
   */
  public StructureRequest(boolean succeeded, boolean excepted, Object instance) {
    this.setSucceeded(succeeded);
    this.setExcepted(excepted);
    this.instance = instance;
  }

  /**
   * {@inheritDoc}
   *
   * @param is the specified boolean
   * @see isExcepted()
   */
  @Override
  public void setExcepted(boolean is) {
    super.setExcepted(is);
    if (is) {
      HttpBody body = StructureRequest.getErrorTemplate();
      this.responseBody = body.toString();
    }
  }

  /**
   * Returns the related instance of the instance.
   *
   * @return the related instance
   */
  public Object getInstance() {
    return this.instance;
  }

  /**
   * Sets the related instance of the instance.
   *
   * @param instance the related instance
   */
  public void setInstance(Object instance) {
    this.instance = instance;
  }

  /**
   * {@inheritDoc}
   *
   * @param param the response body in {@code Map}, preferably in {@code HttpBody}
   * @see #setResponseBody(Map)
   */
  @Override
  public void setResponseBody(Map<?, ?> param) {
    this.responseBody =
        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(param);
  }

  /**
   * Adds new pair to the response body of the instance.
   *
   * @param key the key
   * @param value the value
   * @return {@code HttpBody} the object added if succeeded. {@code null} otherwise.
   */
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

  /**
   * Sets the error message to the response body of the instance.
   *
   * @param message the specified error message to set
   * @return {@code HttpBody} the message added
   */
  public HttpBody setErrorMessage(String message) {
    HttpBody body = new Gson().fromJson(this.responseBody, HttpBody.class);
    HttpBody error = body.getHttpBody("error");
    error.put("message", message);
    this.responseBody = body.toString();
    return body;
  }

  /** {@inheritDoc} */
  public HttpBody getResponseBody() {
    return new Gson().fromJson(this.responseBody, HttpBody.class);
  }

  /** {@inheritDoc} */
  public String getResponseBodyString() {
    return this.responseBody;
  }

  /**
   * Returns the related instance in {@code String}
   *
   * @return the related instance in {@code String}
   */
  private String getInstanceString() {
    if (this.instance != null) {
      return this.instance.toString();
    } else {
      return null;
    }
  }

  /** {@inheritDoc} */
  public String toString() {
    return "StructureRequest ("
        + "instance: "
        + this.getInstanceString()
        + ", responseBody: "
        + this.getResponseBodyString()
        + ")";
  }

  /**
   * Returns the request error template
   *
   * @return the request error template
   */
  private static HttpBody getErrorTemplate() {
    HttpBody error = new HttpBody();
    error.put("code", 400);
    error.put("message", "");
    error.put("details", null);
    HttpBody body = new HttpBody();
    body.put("error", error);
    return body;
  }
}
