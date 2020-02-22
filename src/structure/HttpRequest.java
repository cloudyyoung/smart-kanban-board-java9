package structure;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Send http request in different http request method with custom param and get return json data.
 *
 * @author Cloudy Young
 * @see https://www.baeldung.com/httpurlconnection-post
 * @see http://alex-public-doc.s3.amazonaws.com/json_simple-1.1/index.html
 * @see https://www.tutorialspoint.com/json_simple/json_simple_quick_guide.htm
 * @version 1.0
 * @since 2020-02-20
 */
public class HttpRequest {

  private String baseUrl = "https://kanban.proj.meonc.studio/api";
  private String requestUrl;
  private String requestMethod;
  private Object requestCookie; // JSONObject
  private Object requestBody; // JSONObject/JSONArray

  private boolean succeed;
  private int responseStatusCode;
  private String responseMessage;
  private Object responseCookie; // JSONObject
  private Object responseBody; // JSONObject/JSONArray

  public HttpRequest(String url, Object param, String method) {
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

  public void setRequestBody(Object paramMap) {
    this.requestBody = this.objectToJsonObject(paramMap);
  };

  public boolean hasRequestBody() {
    return (this.requestBody != null
        && !this.getRequestMethod().equals("GET")
        && !this.getRequestMethod().equals("DELETE")
        && !this.getRequestMethod().equals("HEAD"));
    // GET, DELETE, HEAD methods should not have request body, so return false regardless of actual
    // this.requestBody
  }

  public HashMap<?, ?> getRequestBody() {
    return (HashMap<?, ?>) this.jsonObjectToObject(this.requestBody);
  }

  public String getRequestBodyString() {
    return this.jsonToString(this.requestBody);
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

  public Object getResult() {
    return this.responseBody;
  }

  public String getResponseString() {
    return this.jsonToString(this.responseBody);
  }

  public int getResponseStatusCode() {
    return this.responseStatusCode;
  }

  public void setRequestCookie(Object cookie) {
    if (cookie instanceof Map) { // requestCookie: should always be Map
      this.requestCookie = this.objectToJsonObject(cookie);
    } else {
      this.requestCookie = null;
    }
  }

  public HashMap<?, ?> getRequestCookie() {
    return (HashMap<?, ?>) this.jsonObjectToMap((JSONObject) this.requestCookie);
  }

  public String getRequestCookieByString() {
    String ret = "";
    if (this.requestCookie != null && this.requestCookie instanceof JSONObject) {
      JSONObject obj = (JSONObject) this.requestCookie;
      for (Object each : obj.keySet()) {
        String key = (String) each;
        String value = (String) obj.get(key);
        ret += key.trim() + "=" + value.trim() + ";";
      }
    }
    return ret;
  }

  public Object getResponseBody() {
    return this.jsonObjectToObject(this.responseBody);
  }

  public String getResponseMessage() {
    return this.responseMessage;
  }

  public HashMap<?, ?> getResponseCookie() {
    return (HashMap<?, ?>) this.jsonObjectToObject(this.responseCookie);
  }

  public boolean isSucceed() {
    return this.succeed;
  }

  private void setResponseByString(String res) {
    this.responseBody = this.stringToJson(res);
  }

  private void setResponseStatusCode(int statusCode) {
    this.responseStatusCode = statusCode;
  }

  private void setResponseMessage(String message) {
    this.responseMessage = message;
  }

  private void setResponseCookieByString(String cookie) {
    String[] list = cookie.split(";");
    JSONObject map = new JSONObject();
    for (String each : list) {
      String[] pair = each.split("=");
      if (pair.length >= 2 && !pair[0].equals("path") && !pair[0].equals("expire")) {
        map.put(pair[0], pair[1]);
      }
    }
    this.responseCookie = map;
  }

  private void setSucceed(boolean is) {
    this.succeed = is;
  }

  private Object objectToJsonObject(Object obj) {
    if (obj instanceof Map) {
      return this.mapToJsonObject((Map<?, ?>) obj);
    } else if (obj instanceof List) {
      return this.listToJsonArray((List<?>) obj);
    } else {
      return null;
    }
  }

  private Object jsonObjectToObject(Object obj) {
    if (obj instanceof JSONObject) {
      return this.jsonObjectToMap((JSONObject) obj);
    } else if (obj instanceof JSONArray) {
      return this.listToJsonArray((JSONArray) obj);
    } else {
      return null;
    }
  }

  private String jsonToString(Object obj) {
    if (obj instanceof JSONObject) {
      return ((JSONObject) obj).toJSONString();
    } else if (obj instanceof JSONArray) {
      return ((JSONArray) obj).toJSONString();
    } else {
      return null;
    }
  }

  private Object stringToJson(String str) {
    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(str);
      return obj;
    } catch (Exception e) {
      return null;
    }
  }

  private List<?> jsonArrayToList(JSONArray json) {
    List<Object> list = new ArrayList<Object>();
    for (int i = 0; i < ((JSONArray) json).size(); i++) {
      Object value = ((JSONArray) json).get(i);
      if (value instanceof JSONArray) {
        value = jsonArrayToList((JSONArray) value);
      } else if (value instanceof JSONObject) {
        value = jsonObjectToMap((JSONObject) value);
      }
      list.add(value);
    }
    return list;
  }

  private Object listToJsonArray(List<?> list) {
    JSONArray array = new JSONArray();
    for (int i = 0; i < list.size(); i++) {
      Object value = list.get(i);
      if (value instanceof Map) {
        value = mapToJsonObject((Map<?, ?>) value);
      } else if (value instanceof List) {
        value = listToJsonArray((List<?>) value);
      }
      array.add(value);
    }
    return array;
  }

  private Map<?, ?> jsonObjectToMap(JSONObject json) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Iterator<?> keysItr = ((JSONObject) json).keySet().iterator();

    while (keysItr.hasNext()) {
      Object key = keysItr.next();
      Object value = json.get(key);

      if (value instanceof JSONArray) {
        value = this.jsonArrayToList((JSONArray) value);
      } else if (value instanceof JSONObject){
        value = this.jsonObjectToMap((JSONObject) value);
      }

      map.put(key, value);
    }
    return map;
  }

  private Object mapToJsonObject(Map<?, ?> map) {
    JSONObject obj = new JSONObject();
    Iterator<?> keysItr = map.keySet().iterator();
    while (keysItr.hasNext()) {
      Object key = keysItr.next();
      Object value = map.get(key);

      if (value instanceof Map<?, ?>) {
        value = mapToJsonObject((Map<?, ?>) value);
      } else if (value instanceof List<?>) {
        value = listToJsonArray((List<?>) value);
      }
      obj.put(key, value);
    }
    return obj;
  }

  public boolean send() {

    try {
      // Construct url request object
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

      System.out.println(this.getRequestBodyString());
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
      this.setResponseByString(response.toString());
      this.setResponseStatusCode(connection.getResponseCode());
      this.setResponseMessage(connection.getResponseMessage());
      try {
        this.setResponseCookieByString(connection.getHeaderField("Set-Cookie"));
      } catch (Exception e) {
        this.setResponseCookieByString(null);
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

    HashMap<String, String> param = new HashMap<String, String>();
    param.put("username", "cloudy");
    param.put("password", "cloudy");
    System.out.println(param);

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/users/authentication");
    req.setRequestMethod("PUT");
    req.setRequestBody(param);
    req.send();
    System.out.println(req.getResponseStatusCode());
    System.out.println(req.getResponseBody());

    HashMap<?, ?> responseCookie = req.getResponseCookie();

    HashMap<String, String> cookie = new HashMap<String, String>();
    cookie.put("PHPSESSID", (String)responseCookie.get("PHPSESSID"));
    System.out.println(cookie);


    HttpRequest req2 = new HttpRequest();
    req2.setRequestUrl("/kanban");
    req2.setRequestMethod("GET");
    req2.setRequestCookie(cookie);
    boolean ret2 = req2.send();

    System.out.println(ret2);
    System.out.println(req2.getResponseStatusCode());
    System.out.println(req2.getResponseBody());
  }
}
