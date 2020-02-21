package structure;

import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.google.gson.*;

/**
 * Send http request in different http request method with custom param and get return json data.
 *
 * @author Cloudy Young
 * @see https://www.baeldung.com/httpurlconnection-post
 * @version 1.0
 * @since 2020-02-20
 */
public class HttpRequest {


  private String baseUrl = "https://kanban.proj.meonc.studio/api";
  private String requestUrl;
  private HashMap<Object, Object> requestBody;
  private String requestMethod;
  private HashMap<Object, Object> requestCookie;

  private boolean succeed;
  private int responseStatusCode;
  private String responseMessage;
  private HashMap<Object, Object> responseBody;
  private HashMap<Object, Object> responseCookie;

  public HttpRequest(String url, HashMap<Object, Object> param, String method) {
    this.setRequestUrl(url);
    this.setRequestBody(param);
    this.setRequestMethod(method);
  }

  public HttpRequest(String url, String method) {
    this.setRequestUrl(url);
    this.setRequestMethod(method);
  }

  public HttpRequest() {
    this.setRequestMethod("GET");
  }

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

  public String getRequestMethod() {
    return this.requestMethod;
  }

  public void setRequestBody(HashMap<Object, Object> paramMap) {
    if (paramMap == null) {
      return;
    }
    this.requestBody = new HashMap<Object, Object>(paramMap);
  };

  public void setRequestBodyByObject(Object obj) {
    Gson gson = new Gson();
    String json = gson.toJson(obj);
    this.requestBody = gson.fromJson(json, HashMap.class);
  }

  public boolean hasRequestBody() {
    return (this.requestBody != null
        && !this.getRequestMethod().equals("GET")
        && !this.getRequestMethod().equals("DELETE")
        && !this.getRequestMethod().equals("HEAD"));
    // GET, DELETE, HEAD methods should not have request body, so return false regardless of actual
    // this.requestBody
  }

  public HashMap<Object, Object> getRequestBody() {
    if (this.requestBody == null) {
      return null;
    }
    return new HashMap<Object, Object>(this.requestBody);
  }

  public String getRequestBodyString() {
    return new String(new Gson().toJson(this.requestBody));
  }

  public void setRequestUrl(String url) {
    this.requestUrl = this.baseUrl + url;
  }

  public String getRequestUrl() {
    return this.requestUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl + "/";
  }

  public String getBaseUrl() {
    return this.baseUrl;
  }

  public HashMap<Object, Object> getResult() {
    return this.responseBody;
  }

  public String getResponseString() {
    return new String(new Gson().toJson(this.requestBody));
  }

  public int getResponseStatusCode() {
    return this.responseStatusCode;
  }

  public void setRequestCookie(HashMap<Object, Object> cookie) {
    if (cookie == null) {
      return;
    }
    this.requestCookie = new HashMap<Object, Object>(cookie);
  }

  public HashMap<Object, Object> getRequestCookie() {
    if (this.requestCookie == null) {
      return null;
    }
    return new HashMap<Object, Object>(this.requestCookie);
  }

  public String getRequestCookieByString() {
    String ret = "";
    if (this.requestCookie != null) {
      for (Map.Entry<Object, Object> each : this.requestCookie.entrySet()) {
        ret += each.getKey() + "=" + each.getValue() + ";";
      }
    }
    return ret;
  }

  public HashMap<Object, Object> getResponseBody() {
    if (this.responseBody == null) {
      return null;
    }
    return new HashMap<Object, Object>(this.responseBody);
  }

  public Object getResponseByObject(Class aClass) {
    return new Gson().fromJson(new Gson().toJson(this.responseBody), aClass);
  }

  public String getResponseMessage() {
    return this.responseMessage;
  }

  public HashMap<Object, Object> getResponseCookie() {
    if (this.responseCookie == null) {
      return null;
    }
    return new HashMap<Object, Object>(this.responseCookie);
  }

  public boolean isSucceed(){
    return this.succeed;
  }

  private void setResponseByString(String res) {
    Gson gson = new Gson();
    this.responseBody = gson.fromJson(res, HashMap.class);
  }

  private void setResponseStatusCode(int statusCode) {
    this.responseStatusCode = statusCode;
  }

  private void setResponseMessage(String message) {
    this.responseMessage = message;
  }

  private void setResponseCookieByString(String cookie) {
    String[] list = cookie.split(";");
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    for (String each : list) {
      String[] pair = each.split("=");
      if (pair.length >= 2) {
        map.put(pair[0], pair[1]);
      }
    }
    this.responseCookie = map;
  }

  private void setSucceed(boolean is){
    this.succeed = is;
  }

  public boolean send() {

    try {
      // Contruct url request object
      URL req = new URL(this.getRequestUrl());
      HttpURLConnection connection = (HttpURLConnection) req.openConnection();
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

      // Get response
      BufferedReader br;
      // Get response for any status code
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
      this.setResponseByString(response.toString());
      this.setResponseStatusCode(connection.getResponseCode());
      this.setResponseMessage(connection.getResponseMessage());
      try {
        this.setResponseCookieByString(connection.getHeaderField("Set-Cookie"));
      } catch (Exception e) {
        this.setResponseCookieByString("");
      }

      connection.disconnect();

    } catch (Exception e) {
      this.setSucceed(false);
      return false;
    }

    this.setSucceed(true);
    return true;
  }

  public static void main(String[] args) {
    // HttpRequest req = new HttpRequest();
    // req.setRequestUrl("/users");
    // System.out.println(req.getRequestUrl());
    // req.setRequestMethod("GET");
    // boolean res = req.send();
    // System.out.println(res);
    // System.out.println(req.getResponseStatusCode());
    // System.out.println(req.getResponse().get("error"));
    HashMap<Object, Object> param = new HashMap<Object, Object>();
    param.put("username", "cloudy");
    param.put("password", "cloudy");
    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/users/authentication");
    req.setRequestMethod("PUT");
    req.setRequestBody(param);
    req.send();
    System.out.println(req.getResponseStatusCode());
    System.out.println(req.getResponseBody());

    User user = (User) req.getResponseByObject(User.class);
    System.out.println(user.getClass());
    System.out.println(req.getResponseCookie());

    HashMap<Object, Object> cookie = new HashMap<Object, Object>();
    cookie.put("PHPSESSID", user.getSessionId());
    System.out.println(cookie);

    HashMap<Object, Object> param2 = new HashMap<Object, Object>();
    param2.put("title", "java new");

    HttpRequest req2 = new HttpRequest();
    req2.setRequestUrl("/boards");
    req2.setRequestMethod("POST");
    req2.setRequestBody(param2);
    req2.setRequestCookie(cookie);
    boolean ret2 = req2.send();

    System.out.println(ret2);
    System.out.println(req2.getResponseBody());
    System.out.println(req2.getResponseStatusCode());
    System.out.println(req2.getRequestMethod());
    System.out.println(req2.getRequestUrl());
  }
}
