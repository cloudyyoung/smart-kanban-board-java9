package structure;

import java.util.Map;
import java.util.List;

class HttpBody {

  private Object object;

  public HttpBody(Object object) {
    if (object instanceof Map || object instanceof List) {
      this.object = object;
    }
  }

  public Object get(Object key) {
    try {
      if (this.object instanceof Map) {
        return ((Map<?, ?>) this.object).get(key);
      } else if (this.object instanceof List) {
        return ((List<?>) this.object).get(this.parseInt(key));
      } else {
        return null;
      }
    } catch (Throwable e) {
      return null;
    }
  }

  public int getInt(Object key) {
    return this.parseInt(this.get(key));
  }

  public boolean getBoolean(Object key) {
    return this.parseBoolean(this.get(key));
  }

  public String getString(Object key) {
    return this.get(key).toString();
  }

  private int parseInt(Object obj) {
    try {
      return Integer.parseInt(obj.toString() + "");
    } catch (Throwable e) {
      return Integer.MIN_VALUE;
    }
  }

  private boolean parseBoolean(Object obj) {
    return Boolean.parseBoolean(obj.toString() + "");
  }
}
