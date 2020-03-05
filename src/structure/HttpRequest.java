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

@SuppressWarnings("unchecked")

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
  private Object requestCookie; // should be JSONObject
  private Object requestBody; // should be JSONObject/JSONArray

  /** All attributes for response */
  private boolean succeed;

  private int responseStatusCode;
  private String responseMessage;
  private Object responseCookie; // should be JSONObject
  private Object responseBody; // should be JSONObject/JSONArray

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
    this.requestBody = this.objectToJsonObject(param);
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
  public Map<?, ?> getRequestBody() {
    return (Map<?, ?>) this.jsonObjectToObject(this.requestBody);
  }

  /**
   * Get the request body of the instance, in string
   *
   * @return request body in String
   */
  public String getRequestBodyString() {
    return this.jsonToString(this.requestBody);
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
  public String getResponseString() {
    return this.jsonToString(this.responseBody);
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
  public void setRequestCookie(Object cookie) {
    if (cookie instanceof Map) { // requestCookie: should always be Map
      this.requestCookie = this.objectToJsonObject(cookie);
    } else {
      this.requestCookie = null;
    }
  }

  /**
   * Get the request cookie of the instance
   *
   * @return the request cookir in Map
   */
  public Map<?, ?> getRequestCookie() {
    return (Map<?, ?>) this.jsonObjectToMap((JSONObject) this.requestCookie);
  }

  /**
   * Get the request cookie of the instance in String
   *
   * @return cookie string, will be in format of: key=value; key=value; key=value;
   */
  public String getRequestCookieByString() {
    String ret = "";
    if (this.requestCookie != null && this.requestCookie instanceof JSONObject) {
      JSONObject obj = (JSONObject) this.requestCookie;
      for (Object each : obj.keySet()) {
        String key = (String) each;
        String value = (String) obj.get(key);
        ret += key.trim() + "=" + value.trim() + "; ";
      }
    }
    return ret;
  }

  /**
   * Get the response body of the instance
   *
   * @return the response body in Map
   */
  public Map<?, ?> getResponseBody() {
    return (Map<?, ?>) this.jsonObjectToObject(this.responseBody);
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
  public Map<?, ?> getResponseCookie() {
    return (Map<?, ?>) this.jsonObjectToObject(this.responseCookie);
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
    this.responseBody = this.stringToJson(res);
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

  /**
   * Set succeed status of the instance
   *
   * @param is the succeed status to set
   */
  private void setSucceed(boolean is) {
    this.succeed = is;
  }

  /**
   * Convert Map ot List object to JsonObject, null will be returned if type not match
   *
   * @param obj the object to be converted
   * @return the converted object
   */
  private Object objectToJsonObject(Object obj) {
    if (obj instanceof Map) {
      return this.mapToJsonObject((Map<?, ?>) obj);
    } else if (obj instanceof List) {
      return this.listToJsonArray((List<?>) obj);
    } else {
      return null;
    }
  }

  /**
   * Convert JsonObject to Map or List, null will be returned if type not match
   *
   * @param obj the object to be converted
   * @return the converted object
   */
  private Object jsonObjectToObject(Object obj) {
    if (obj instanceof JSONObject) {
      return this.jsonObjectToMap((JSONObject) obj);
    } else if (obj instanceof JSONArray) {
      return this.listToJsonArray((JSONArray) obj);
    } else {
      return null;
    }
  }

  /**
   * Convert JsonObject or JSONArray to String, null will be returned if type not match
   *
   * @param obj the object to be converted
   * @return the converted String
   */
  private String jsonToString(Object obj) {
    if (obj instanceof JSONObject) {
      return ((JSONObject) obj).toJSONString();
    } else if (obj instanceof JSONArray) {
      return ((JSONArray) obj).toJSONString();
    } else {
      return null;
    }
  }

  /**
   * Convert String to JsonObject or JSONArray, null will be returned if type not match
   *
   * @param str the String to be converted
   * @return the converted object
   */
  private Object stringToJson(String str) {
    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(str);
      return obj;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Convert JSONArray to List
   *
   * @param json the JSONArray to be converted
   * @return the converted List
   */
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

  /**
   * Convert List to JSONArray
   *
   * @param list the List to be converted
   * @return the converted JSONArray
   */
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

  /**
   * Convert JSONObject to Map
   *
   * @param json the JSONObject to be converted
   * @return the converte Map
   */
  private Map<?, ?> jsonObjectToMap(JSONObject json) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Iterator<?> keysItr = ((JSONObject) json).keySet().iterator();

    while (keysItr.hasNext()) {
      Object key = keysItr.next();
      Object value = json.get(key);

      if (value instanceof JSONArray) {
        value = this.jsonArrayToList((JSONArray) value);
      } else if (value instanceof JSONObject) {
        value = this.jsonObjectToMap((JSONObject) value);
      }

      map.put(key, value);
    }
    return map;
  }

  /**
   * Convert Map to JSONObject
   *
   * @param map the Map to be converted
   * @return the converted JSONObject
   */
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

    HashMap<?, ?> responseCookie = (HashMap<?, ?>) req.getResponseCookie();

    HashMap<String, String> cookie = new HashMap<String, String>();
    cookie.put("PHPSESSID", (String) responseCookie.get("PHPSESSID"));
    System.out.println(cookie);

    HttpRequest req2 = new HttpRequest();
    req2.setRequestUrl("/kanban");
    req2.setRequestMethod("GET");
    req2.setRequestCookie(cookie);
    boolean ret2 = req2.send();

    System.out.println(ret2);
    System.out.println("succeed?" + req2.isSucceed());
    System.out.println(req2.getResponseStatusCode());
    System.out.println("RES::" + req2.getResponseBody());
  }
}
