package structure;

import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
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

  private String baseUrl = "http://kanban.proj.meonc.studio/api";
  private String requestUrl;
  private HashMap<String, Object> requestParameter;
  private String requestMethod;
  private HashMap<String, Object> requestCookie;

  private int responseStatusCode;
  private String responseMessage;
  private HashMap<String, Object> response;
  private HashMap<String, Object> responseCookie;

  public HttpRequest(String url, HashMap<String, Object> param, String method) {
    this.setRequestUrl(url);
    this.setRequestParam(param);
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

  public void setRequestParam(HashMap<String, Object> paramMap) {
    this.requestParameter = new HashMap<String, Object>(paramMap);
  };

  public HashMap<String, Object> getRequestParam() {
    return new HashMap<String, Object>(this.requestParameter);
  }

  public String getRequestParamString() {
    return new String(new Gson().toJson(this.requestParameter));
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

  public HashMap<String, Object> getResult() {
    return this.response;
  }

  public String getResponseString() {
    return new String(new Gson().toJson(this.requestParameter));
  }

  public int getResponseStatusCode() {
    return this.responseStatusCode;
  }

  public void setRequestCookie(HashMap<String, Object> cookie) {
    this.requestCookie = new HashMap<String, Object>(cookie);
  }

  public HashMap<String, Object> getRequestCookie() {
    return new HashMap<String, Object>(this.requestCookie);
  }

  public String getRequestCookieByString() {
    String ret = "";
    if (this.requestCookie != null) {
      for (Map.Entry<String, Object> each : this.requestCookie.entrySet()) {
        ret += each.getKey() + "=" + each.getValue() + ";";
      }
    }
    return ret;
  }

  public HashMap<String, Object> getResponse() {
    return new HashMap<String, Object>(this.response);
  }

  public Object getResponseToObject(Class aClass){
    return new Gson().fromJson(new Gson().toJson(this.response), aClass);
  }

  public String getResponseMessage(){
    return this.responseMessage;
  }

  public HashMap<String, Object> getResponseCookie(){
    return new HashMap<String, Object>(this.responseCookie);
  }


  private void setResponseByString(String res) {
    Gson gson = new Gson();
    this.response = gson.fromJson(res, HashMap.class);
  }

  private void setResponseStatusCode(int statusCode) {
    this.responseStatusCode = statusCode;
  }

  private void setResponseMessage(String message) {
    this.responseMessage = message;
  }

  private void setResponseCookieByString(String cookie) {
    String[] list = cookie.split(";");
    HashMap<String, Object> map = new HashMap<String, Object>();
    for (String each : list) {
      String[] pair = each.split("=");
      if (pair.length >= 2) {
        map.put(pair[0], pair[1]);
      }
    }
    this.responseCookie = map;
  }

  public boolean send() {

    try {
      // Contruct url request object
      URL req = new URL(this.getRequestUrl());
      HttpURLConnection connection = (HttpURLConnection) req.openConnection();
      connection.setRequestMethod(requestMethod.toUpperCase());
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
      OutputStream os = connection.getOutputStream();
      byte[] input = this.getRequestParamString().getBytes("utf-8");
      os.write(input, 0, input.length);

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
      try{
        this.setResponseCookieByString(connection.getHeaderField("Set-Cookie"));
      }catch(Exception e){
        this.setResponseCookieByString("");
      }
      

      connection.disconnect();

    } catch (Exception e) {
      System.out.println(e.toString());
      return false;
    }

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
    HashMap<String, Object> param = new HashMap<String, Object>();
    param.put("username", "cloudy");
    param.put("password", "cloudy");
    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/users/authentication");
    req.setRequestMethod("PUT");
    req.setRequestParam(param);
    req.send();
    System.out.println(req.getResponseStatusCode());
    System.out.println(req.getResponse());
    User user = (User)req.getResponseToObject(User.class);
    System.out.println(user.getClass());
    System.out.println(req.getResponseCookie());
  }
}
