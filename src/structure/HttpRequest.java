package structure;

import java.util.Map;

import com.google.gson.*;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Send http request in different http request method with custom param and get return json data.
 *
 * <p>This class is extending from {@link Request} class.
 *
 * <p>This class should be working along with {@link HttpBody}, since the getters and setters of
 * {@code requestBody}, {@code requestCookie}, {@code responseBody}, {@code responseCookie} are
 * accept/return {@code HttpBody} type object.
 *
 * @author Cloudy Young
 * @see <a
 *     href="https://www.baeldung.com/httpurlconnection-post">https://www.baeldung.com/httpurlconnection-post</a>
 * @see <a
 *     href="http://alex-public-doc.s3.amazonaws.com/json_simple-1.1/index.html">http://alex-public-doc.s3.amazonaws.com/json_simple-1.1/index.html</a>
 * @see <a
 *     href="https://www.tutorialspoint.com/json_simple/json_simple_quick_guide.htm">https://www.tutorialspoint.com/json_simple/json_simple_quick_guide.htm</a>
 * @see <a
 *     href="https://github.com/CloudyYoung/Kanban-Server/wiki">https://github.com/CloudyYoung/Kanban-Server/wiki</a>
 * @since 1.0
 * @version 2.1
 */
public final class HttpRequest extends Request {

  /** The base URL of the instance for request */
  private String baseUrl = "https://kanban.proj.meonc.studio/api";

  /** The request URL of the instance for request */
  private String requestUrl;

  /**
   * The request method of the instance for request, should always be one of the following: {@code
   * GET}, {@code POST}, {@code PUT}, {@code DELETE}, {@code HEAD}, {@code OPTIONS}
   */
  private String requestMethod;

  /** The request cookie of the instance for request */
  private String requestCookie;

  /** The request body of the instance for request, should always be {@code JSON} format */
  private String requestBody;

  /**
   * The response status code of the instance after requested, should always be between {@code 100}
   * and {@code 599}
   */
  private int responseStatusCode;

  /** The response message of the instance after requested */
  private String responseMessage;

  /** The response cookie of the instance after requested */
  private String responseCookie;

  /** The response body of the instance after requested, should always be {@code JSON} format */
  private String responseBody;

  /**
   * Constructor of {@code HttpRequest}, provde url in {@code String}, param in {@code Object} and
   * method in {@code String}
   *
   * @param url the request url in {@code String}
   * @param param the request body in {@code Object}, preferably in {@code HttpBody}
   * @param method the request method in {@code String}
   */
  public HttpRequest(String url, Object param, String method) {
    this.setRequestUrl(url);
    this.setRequestBody(param);
    this.setRequestMethod(method);
  }

  /**
   * Constructor of {@code HttpRequest}, provde url in {@code String} and method in {@code String}
   *
   * @param url the request url in {@code String}
   * @param method the request method in {@code String}
   */
  public HttpRequest(String url, String method) {
    this.setRequestUrl(url);
    this.setRequestMethod(method);
  }

  /** Default constructor of {@code HttpRequest} */
  public HttpRequest() {
    this.setRequestMethod("GET");
  }

  /**
   * Returns the given cookie in {@code HttpBody} type.
   *
   * @param cookie the cookie in {@code String}
   * @return the cookie object in {@code HttpBody}
   * @version 2.1
   */
  private HttpBody cookieStringToObject(String cookie) {
    HttpBody ret = new HttpBody();
    String[] list = cookie.split("; ");
    for (String each : list) {
      String[] pair = each.split("=");
      if (pair.length >= 2) {
        String key = pair[0];
        String value = pair[1];
        ret.put(key.trim(), value.trim());
      }
    }
    return ret;
  }

  /**
   * Returns the given cookie in {@code String} type.
   *
   * @param cookie the cookie in {@code Map}
   * @return the cookie in {@code String}
   * @version 2.1
   */
  private String cookieObjectToString(Map<?, ?> cookie) {
    String ret = "";
    HttpBody body = new HttpBody(cookie);
    for (Object each : body.keySet()) {
      String key = (String) each;
      String value = body.getString(key);
      ret += key.trim() + "=" + value.trim() + "; ";
    }
    return ret;
  }

  /**
   * Sets request method of the instance.
   *
   * @param method the request method in {@code String}, default value is GET, accpetable values
   *     are: GET, POST, PUT, DELETE, HEAD, PATCH and OPTIONS, otherwise: set to default.
   * @see #getRequestMethod()
   * @version 2.1
   */
  public void setRequestMethod(String method) {
    if (method.equals("GET")
        || method.equalsIgnoreCase("POST")
        || method.equalsIgnoreCase("PUT")
        || method.equalsIgnoreCase("DELETE")
        || method.equalsIgnoreCase("HEAD")
        // || method.equalsIgnoreCase("PATCH") // Doesnt support
        || method.equalsIgnoreCase("OPTIONS")) {
      this.requestMethod = method.toUpperCase();
    } else {
      this.requestMethod = "GET";
    }
  }

  /**
   * Returns the request method of the instance.
   *
   * @return the request method
   * @see #setRequestMethod(String)
   */
  public String getRequestMethod() {
    return this.requestMethod;
  }

  /**
   * Sets the request body of the instance.
   *
   * @param param the request body in {@code Object}, preferably in {@code HttpBody}
   * @see #getRequestBody()
   * @see #getRequestBodyString()
   * @see #hasRequestBody()
   * @version 2.1
   */
  public void setRequestBody(Object param) {
    this.requestBody =
        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(param);
  };

  /**
   * Returns a boolean to represent whether the instance has or should have request body.
   *
   * @return {@code true} if it has or should have {@code false} otherwise
   * @see #setRequestBody(Object)
   * @see #getRequestBody()
   * @see #getRequestBodyString()
   */
  public boolean hasRequestBody() {
    return (this.requestBody != null
        && !this.getRequestMethod().equals("GET")
        && !this.getRequestMethod().equals("DELETE")
        && !this.getRequestMethod().equals("HEAD"));
    // GET, DELETE, HEAD methods should not have request body, so return false regardless
  }

  /**
   * Returns the request body of the instance.
   *
   * @return request body in {@code HttpBody}
   * @see #getRequestBodyString()
   * @see #setRequestBody(Object)
   * @see #hasRequestBody()
   * @version 2.1
   */
  public HttpBody getRequestBody() {
    return new Gson().fromJson(this.requestBody, HttpBody.class);
  }

  /**
   * Returns the request body of the instance.
   *
   * @return the request body in {@code String}
   * @see #getRequestBody()
   * @see #setRequestBody(Object)
   * @see #hasRequestBody()
   */
  public String getRequestBodyString() {
    return this.requestBody;
  }

  /**
   * Sets the request url of the instance.
   *
   * @param url the request url in {@code String}
   */
  public void setRequestUrl(String url) {
    this.requestUrl = this.baseUrl + url;
  }

  /**
   * Returns the request url of the instance.
   *
   * @return the request url in {@code String}
   */
  public String getRequestUrl() {
    return this.requestUrl;
  }

  /**
   * Sets the base url of the instance.
   *
   * @param baseUrl the base url in {@code String}
   */
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl + "/";
  }

  /**
   * Returns the base url of the instance.
   *
   * @return the base url in {@code String}
   */
  public String getBaseUrl() {
    return this.baseUrl;
  }

  /** 
   * {@inheritDoc}
   * @return {@inheritDoc}
   */
  public String getResponseBodyString() {
    return this.responseBody;
  }

  /**
   * Returns the response status code of the instance.
   *
   * @return the status code in {@code int}
   */
  public int getResponseStatusCode() {
    return this.responseStatusCode;
  }

  /**
   * Sets the request cookie of the instance.
   *
   * @param cookie the cookie to be set in {@code Map}
   * @version 2.1
   */
  public void setRequestCookie(Map<?, ?> cookie) {
    this.requestCookie = this.cookieObjectToString(cookie);
  }

  /**
   * Returns the request cookie of the instance.
   *
   * @return the request cookie in {@code HttpBody}
   * @version 2.1
   */
  public HttpBody getRequestCookie() {
    return this.cookieStringToObject(this.requestCookie);
  }

  /**
   * Returns the request cookie of the instance.
   *
   * @return the cookie in {@code String}
   */
  public String getRequestCookieString() {
    return this.requestCookie;
  }

  /** 
   * {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public HttpBody getResponseBody() {
    return new HttpBody(new Gson().fromJson(this.responseBody, Map.class));
  }

  /**
   * Returns the response message of the instance.
   *
   * @return the response message in {@code String}
   */
  public String getResponseMessage() {
    return this.responseMessage;
  }

  /**
   * Returns the response cookie of the instance.
   *
   * @return the reponse cookie in {@code HttpBody}
   * @version 2.1
   */
  public HttpBody getResponseCookie() {
    return this.cookieStringToObject(this.responseCookie);
  }

  /**
   * Returns the response cookie string of the instance.
   *
   * @return the reponse cookie in {@code String}
   * @version 2.1
   */
  public String getResponseCookieString() {
    return this.responseCookie;
  }

  /**
   * Sets the response body of the instance from {@code String}.
   *
   * @param res the response body in {@code String}
   */
  protected void setResponseBodyString(String res) {
    this.responseBody = res;
  }

  /**
   * {@inheritDoc}
   *
   * @param body {@inheritDoc}
   * @version 2.1
   */
  @Override
  protected void setResponseBody(Map<?, ?> body) {
    this.responseBody = new Gson().toJson(body);
  }

  /**
   * Sets the response status code of the instance.
   *
   * @param statusCode the status code to set in {@code int}
   */
  private void setResponseStatusCode(int statusCode) {
    this.responseStatusCode = statusCode;
  }

  /**
   * Sets the response message of the instance.
   *
   * @param message the message to set in {@code String}
   */
  private void setResponseMessage(String message) {
    this.responseMessage = message;
  }

  /**
   * Sets the response cookie by string of the instance.
   *
   * @param cookie the cookie to set in {@code String}
   */
  private void setResponseCookieString(String cookie) {
    this.responseCookie = cookie;
  }

  /** {@inheritDoc} */
  public String toString() {
    return "HttpRequest ("
        + "isSucceeded: "
        + this.isSucceeded()
        + ", isExcepted: "
        + this.isExcepted()
        + ", requestUrl: "
        + this.requestUrl
        + ", requestMethod: "
        + this.getRequestMethod()
        + ", requestCookie: "
        + this.getRequestCookieString()
        + ", requestBody: "
        + this.getRequestBodyString()
        + ", responseStatusCode: "
        + this.getResponseStatusCode()
        + ", responseMessage: "
        + this.getResponseMessage()
        + ", responseCookie: "
        + this.getResponseCookieString()
        + ", responseBody: "
        + this.getResponseBodyString()
        + ")";
  }

  /**
   * Sends the request.
   *
   * @return {@code true} if the request sent successfully, specifically if the response status code
   *     is between {@code 200} and {@code 300}. {@code false} otherwise.
   */
  public boolean send() {

    try {
      // Construct url request object
      URL url = new URL(this.getRequestUrl());
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(this.getRequestMethod());
      connection.setRequestProperty("Cookie", this.getRequestCookieString());
      connection.setRequestProperty("Content-Language", "en-US");
      connection.setRequestProperty("Content-Type", "application/json; utf-8");
      connection.setRequestProperty("Accept", "application/json");
      connection.setUseCaches(false);
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setInstanceFollowRedirects(true);
      connection.setConnectTimeout(6000);
      connection.connect();

      // Put request Json object
      if (this.hasRequestBody()) { // only write request body if has request param
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(this.getRequestBodyString());
        writer.flush();
      }

      // Get response, for any status code
      BufferedReader br;
      if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      } else {
        br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
      }
      StringBuilder response = new StringBuilder();
      String responseLine = null;
      while ((responseLine = br.readLine()) != null) {
        response.append(responseLine.trim());
      }

      // Store result
      this.setResponseBodyString(response.toString());
      this.setResponseStatusCode(connection.getResponseCode());
      this.setResponseMessage(connection.getResponseMessage());
      if (connection.getHeaderField("Set-Cookie") != null) { // if there're new cookies
        this.setResponseCookieString(connection.getHeaderField("Set-Cookie"));
      }

      if (connection.getResponseCode() >= 400 && connection.getResponseCode() <= 599) {
        this.setSucceeded(false);
      } else {
        this.setSucceeded(true);
      }
      this.setExcepted(false);

      connection.disconnect();

    } catch (Exception e) {
      this.setSucceeded(false);
      this.setExcepted(true);
      // e.printStackTrace();
      return false;
    }

    return true;
  }
}
