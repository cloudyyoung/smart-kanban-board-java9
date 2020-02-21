package structure;

import java.util.HashMap;
import com.google.gson.*;

public class Request {

  public static String request(String url, String method, HashMap<String, String> parameters) {
    URL req = new URL(url);
    connection = (HttpURLConnection) req.openConnection();
    connection.setRequestMethod(method.toUpperCase());
    connection.setRequestProperty("Content-Language", "en-US");
    connection.setUseCaches(false);
    connection.setDoOutput(true);

    Gson json = new Gson();
    json.toJson(parameters);

    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
    out.writeBytes(json);
    out.flush();
    out.close();

    return out;
  }
}
