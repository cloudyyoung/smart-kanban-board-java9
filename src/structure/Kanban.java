package structure;

import java.util.HashMap;

public class Kanban extends Node {

  public Kanban(HashMap<String, ?> obj) {
    super(obj);
  }

  public static void main(String[] args) {
    HashMap<String, String> param = new HashMap<String, String>();
    param.put("username", "test");
    param.put("password", "test");
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

    Kanban kanban = new Kanban((HashMap<String, ?>) req2.getResponseBody());
  }
}
