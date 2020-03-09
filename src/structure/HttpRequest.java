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
 * Custoizamables: request url, request method, request cookie, request body Public methods: Getters
 * and setters of request attributes, Getters of response attributes, and Send method All Object
 * input and output should be either Map or List, and the all Object private attribute should be
 * either JSONObject or JSONArray The Pipeline: Give Map/List -> Convert to JSONObject/JSONArray and
 * store -> Send and receive String -> Convert String to JSONObject/JSONArray -> Convert from
 * JSONObject/JSONArray -> Get Map/List
 *
 * @author Cloudy Young
 * @see https://www.baeldung.com/httpurlconnection-post
 * @see http://alex-public-doc.s3.amazonaws.com/json_simple-1.1/index.html
 * @see https://www.tutorialspoint.com/json_simple/json_simple_quick_guide.htm
 * @see https://github.com/CloudyYoung/Kanban-Server/wiki
 * @version 1.2
 * @since 2020-02-20
 */
public class HttpRequest {

  /** All attributes for request */
  private String baseUrl = "https://kanban.proj.meonc.studio/api";

  private String requestUrl;
  private String requestMethod;
  private String requestCookie; // should be JSONObject
  private String requestBody; // should be JSONObject/JSONArray

  /** All attributes for response */
  private boolean succeed;

  private int responseStatusCode;
  private String responseMessage;
  private String responseCookie; // should be JSONObject
  private String responseBody; // should be JSONObject/JSONArray

  /** Create a new HttpRequest instance by providing url, param and method */
  public HttpRequest(String url, Object param, String method) {
    this.setRequestUrl(url);
    this.setRequestBody(param);
    this.setRequestMethod(method);
  }

  /** Create a new HttpRequest instance by providing url, method */
  public HttpRequest(String url, String method) {
    this.setRequestUrl(url);
    this.setRequestMethod(method);
  }

  /** Create a new HttpRequest instance by providing nothing */
  public HttpRequest() {
    this.setRequestMethod("GET");
  }

  private HttpBody getCookie(String cookie) {
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

  private String setCookie(Map<?, ?> cookie) {
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
   * Set request method of the instance
   *
   * @param method request method in whether upper or lower case, default value is GET, accpetable
   *     values: GET, POST, PUT, DELETE, HEAD, PATCH and OPTIONS, other values: set to default.
   */
  public void setRequestMethod(String method) {
    if (method.equals("GET")
        || method.equalsIgnoreCase("POST")
        || method.equalsIgnoreCase("PUT")
        || method.equalsIgnoreCase("DELETE")
        || method.equalsIgnoreCase("HEAD")
        || method.equalsIgnoreCase("PATCH")
        || method.equalsIgnoreCase("OPTIONS")) {
      this.requestMethod = method.toUpperCase();
    } else {
      this.requestMethod = "GET";
    }
  }

  /**
   * Get the request method of the instance
   *
   * @return the request method
   */
  public String getRequestMethod() {
    return this.requestMethod;
  }

  /**
   * Set the request body of the instance
   *
   * @param param the request body, should either be Map or List
   */
  public void setRequestBody(Object param) {
    this.requestBody =
        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(param);
  };

  /**
   * Whether the instance has request body
   *
   * @return true if has and false if not has
   */
  public boolean hasRequestBody() {
    return (this.requestBody != null
        && !this.getRequestMethod().equals("GET")
        && !this.getRequestMethod().equals("DELETE")
        && !this.getRequestMethod().equals("HEAD"));
    // GET, DELETE, HEAD methods should not have request body, so return false regardless
  }

  /**
   * Get the request body of the instance
   *
   * @return request body in Map
   */
  public HttpBody getRequestBody() {
    return new Gson().fromJson(this.requestBody, HttpBody.class);
  }

  /**
   * Get the request body of the instance, in string
   *
   * @return request body in String
   */
  public String getRequestBodyString() {
    return this.requestBody;
  }

  /**
   * Set the request url of the instance
   *
   * @param url the request url
   */
  public void setRequestUrl(String url) {
    this.requestUrl = this.baseUrl + url;
  }

  /**
   * Get the request url of the instance
   *
   * @return the request url
   */
  public String getRequestUrl() {
    return this.requestUrl;
  }

  /**
   * Set the base url of the instance The full url would be baseUrl + url
   *
   * @param baseUrl the base url
   */
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl + "/";
  }

  /**
   * Get the base url of the instance
   *
   * @return the base url
   */
  public String getBaseUrl() {
    return this.baseUrl;
  }

  /**
   * Get response body in String of the instance
   *
   * @return response body
   */
  public String getResponseBodyString() {
    return this.responseBody;
  }

  /**
   * Get the response status code of the instance
   *
   * @return status code
   */
  public int getResponseStatusCode() {
    return this.responseStatusCode;
  }

  /**
   * Set the request cookie of the instance
   *
   * @param cookie the cookie in Map, must be Map otherwise is invalid and set to null
   */
  public void setRequestCookie(Map<?, ?> cookie) {
    this.requestCookie = this.setCookie(cookie);
  }

  /**
   * Get the request cookie of the instance
   *
   * @return the request cookie in Map
   */
  public HttpBody getRequestCookie() {
    return this.getCookie(this.requestCookie);
  }

  /**
   * Get the request cookie of the instance in String
   *
   * @return cookie string, will be in format of: key=value; key=value; key=value;
   */
  public String getRequestCookieByString() {
    return this.requestCookie;
  }

  /**
   * Get the response body of the instance
   *
   * @return the response body in Map
   */
  public HttpBody getResponseBody() {
    return new HttpBody(new Gson().fromJson(this.responseBody, Map.class));
  }

  /**
   * Get the response message of the instance
   *
   * @return the response message
   */
  public String getResponseMessage() {
    return this.responseMessage;
  }

  /**
   * Get the response cookie of the instance
   *
   * @return reponse cookie in Map
   */
  public HttpBody getResponseCookie() {
    return this.getCookie(this.responseCookie);
  }

  /**
   * Is this instance's request succeed
   *
   * @return true if succeed and false if not. By defining if succeed, the status code should not be
   *     400~599 and should be no exception occured
   */
  public boolean isSucceed() {
    return this.succeed;
  }

  /**
   * Set the response body by string of the instance
   *
   * @param res the response body in String
   */
  private void setResponseBodyByString(String res) {
    this.responseBody = res;
  }

  /**
   * Set the response status code of the instance
   *
   * @param statusCode the status code to set in int
   */
  private void setResponseStatusCode(int statusCode) {
    this.responseStatusCode = statusCode;
  }

  /**
   * Set the response message of the instance
   *
   * @param message the message to set in String
   */
  private void setResponseMessage(String message) {
    this.responseMessage = message;
  }

  /**
   * Set the response cookie by string of the instance
   *
   * @param cookie the cookie to set in String, it will be converted into JSONObject and store
   */
  private void setResponseCookieByString(String cookie) {
    this.responseCookie = cookie;
  }

  /**
   * Set succeed status of the instance
   *
   * @param is the succeed status to set
   */
  private void setSucceed(boolean is) {
    this.succeed = is;
  }

  /**
   * Send the request of the instance
   *
   * @return true if the request sent successfully and false if not, notice that false indicates
   *     runtime error and exception occured
   */
  public boolean send() {

    try {
      // Construct url request object
      URL url = new URL(this.getRequestUrl());
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(this.getRequestMethod());
      connection.setRequestProperty("Cookie", this.getRequestCookieByString());
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
      this.setResponseBodyByString(response.toString());
      this.setResponseStatusCode(connection.getResponseCode());
      this.setResponseMessage(connection.getResponseMessage());
      if (connection.getHeaderField("Set-Cookie") != null) { // if there're new cookies
        this.setResponseCookieByString(connection.getHeaderField("Set-Cookie"));
      }

      if (connection.getResponseCode() >= 400 && connection.getResponseCode() <= 599) {
        this.setSucceed(false);
      } else {
        this.setSucceed(true);
      }

      connection.disconnect();

    } catch (Exception e) {
      this.setSucceed(false);
      return false;
    }

    return true;
  }
}
