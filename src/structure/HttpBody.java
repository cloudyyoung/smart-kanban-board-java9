package structure;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")

class HttpBody {

  private Object object;

  public HttpBody(Object object) {
    if (object instanceof Map<?, ?> || object instanceof List<?>) {
      this.object = object;
    }
  }

  public Object put(Object value){
    try {
      if (this.object instanceof List) {
        ((List<Object>) this.object).add(value);
      }
      return this;
    } catch (Throwable e) {
      return null;
    }
  }

  public Object put(Object key, Object value){
    try {
      if (this.object instanceof Map) {
        ((Map<Object, Object>) this.object).put(key, value);
      }
      return this;
    } catch (Throwable e) {
      return null;
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

  private String parseString(Object obj){
    try{
      return obj.toString();
    }catch(Throwable e){
      return null;
    }
  }

  public String toString(){
    return this.object.toString();
  }

  public Class<?> getBodyClass(){
    if(this.object == null) return null;
    return this.object.getClass();
  }

  public static void main(String[] args){
    HashMap<String, String> param = new HashMap<String, String>();
    param.put("username", "111");
    param.put("password", "222");
    HttpBody body = new HttpBody(param);
    System.out.println(body);
    System.out.println(body.getInt("password2"));
    System.out.println(body.getBodyClass());
  }

}
