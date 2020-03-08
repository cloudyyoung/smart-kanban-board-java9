package structure;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
class HttpBody extends HashMap<Object, Object> {

  private static final long serialVersionUID = 795022394347180417L;
  private boolean isList = false;

  public HttpBody() {}

  public HttpBody(Map<?, ?> toCopy) {
    super(toCopy);
  }

  public HttpBody(List<?> toCopy) {
    this.list(toCopy);
    this.isList = true;
  }

  public HttpBody(boolean isList) {
    this.isList = isList;
  }

  private void list(Iterable<?> list) {
    for (Object each : list) {
      this.put(this.size(), each);
    }
  }

  private void listReindex() {
    if (this.isList) {
      Collection<Object> collect = this.values();
      this.clear();
      this.list(collect);
    }
  }

  @Override
  public Object put(Object key, Object value) {
    if (this.isList) {
      super.put(this.size(), value);
    } else {
      super.put(key, value);
    }
    return this;
  }

  public Object put(Object value) {
    if (this.isList) {
      this.put(this.size(), value);
    }
    return this;
  }

  @Override
  public Object get(Object key) {
    return super.get(key);
  }

  @Override
  public Object remove(Object key) {
    if (this.isList) {
      Integer index = this.parseInt(key);
      if (index != null) super.remove(index);
      this.listReindex();
    } else {
      super.remove(key);
    }
    return this;
  }

  @Override
  public boolean remove(Object key, Object value) {
    if (this.isList) {
      Object index = this.parseInt(key);
      if (index != null) {
        super.remove(index, value);
        this.listReindex();
        return true;
      } else {
        return false;
      }
    } else {
      return super.remove(key, value);
    }
  }

  // Syntactic sugar
  public boolean hasValue(Object value) {
    return super.containsValue(value);
  }

  // Syntactic sugar
  public boolean hasKey(Object key) {
    return super.containsKey(key);
  }

  public Integer getInt(Object key) {
    return this.parseInt(this.get(key));
  }

  public Boolean getBoolean(Object key) {
    return this.parseBoolean(this.get(key));
  }

  public String getString(Object key) {
    return this.parseString(this.get(key));
  }

  private Integer parseInt(Object obj) {
    try {
      return Integer.parseInt(obj.toString() + "");
    } catch (Throwable e) {
      return null;
    }
  }

  private Boolean parseBoolean(Object obj) {
    return Boolean.parseBoolean(obj.toString() + "");
  }

  private String parseString(Object obj) {
    try {
      return obj.toString();
    } catch (Throwable e) {
      return null;
    }
  }

  public String toString() {
    if (this.isList) {
      return this.values().toString();
    } else {
      return super.toString();
    }
  }

  public static void main(String[] args) {
    HashMap<String, String> param = new HashMap<String, String>();
    param.put("username", "111");
    param.put("password", "222");
    HttpBody body = new HttpBody(param);
    body.put("body", body);
    System.out.println(body);
    System.out.println(body.getInt("password2"));
    System.out.println(body.hasKey("password"));

    ArrayList<Object> list = new ArrayList<Object>();
    list.add("111");
    list.add("aaa");
    list.add(787);
    list.add("333");
    list.add(body);
    HttpBody bod2 = new HttpBody(list);
    System.out.println(bod2);
  }
}
