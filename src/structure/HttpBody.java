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

  public HttpBody() {
  }
  
  public HttpBody(Map<?, ?> toCopy) {
    super(toCopy);
  }

  public HttpBody(List<?> toCopy){
    this.list(toCopy);
    this.isList = true;
  }

  public HttpBody(boolean isList){
    this.isList = isList;
  }

  private Object parse(Object obj){
    if(obj instanceof HttpBody){
      return obj;
    }else if(obj instanceof Map){
      System.out.println("Map");
      HttpBody body = new HttpBody();
      for(Map.Entry<?, ?> each : ((Map<?, ?>)obj).entrySet()){
        body.put(each.getKey(), this.parse(each.getValue()));
      }
      return body;
    }else if(obj instanceof List){
      System.out.println("List");
      HttpBody body = new HttpBody(true);
      for(Object each : (List<?>) obj){
        body.put(this.parse(each));
      }
      return body;
    }else{
      return obj;
    }
  }


  private void list(Iterable<?> list){
    for(Object each : list){
      this.put(this.size(), this.parse(each));
    }
  }

  private void listReindex(){
    if(this.isList){
      Collection<Object> collect = this.values();
      this.clear();
      this.list(collect);
    }
  }

  @Override
  public HttpBody put(Object key, Object value) {
    if(this.isList){
      super.put(this.size(), this.parse(value));
    }else{
      super.put(key, this.parse(value));
    }
    return this;
  }

  public HttpBody put(Object value){
    if(this.isList){
      this.put(this.size(), this.parse(value));
    }
    return this;
  }
  

  @Override
  public Object get(Object key) {
    return super.get(key);
  }

  @Override
  public HttpBody remove(Object key){
    if(this.isList){
      Integer index = this.parseInt(key);
      if(index != null) super.remove(index); this.listReindex();
    }else{
      super.remove(key);
    }
    return this;
  }

  @Override
  public boolean remove(Object key, Object value){
    if(this.isList){
      Object index = this.parseInt(key);
      if(index != null){
        super.remove(index, value);
        this.listReindex();
        return true;
      }else{
        return false;
      }
    }else{
      return super.remove(key, value);
    }
  }


  // Syntactic sugar
  public boolean hasValue(Object value){
    return super.containsValue(value);
  }

  // Syntactic sugar
  public boolean hasKey(Object key){
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

  public String toString(){
    if(this.isList){
      return this.values().toString();
    }else{
      return super.toString();
    }
  }


  

  public static void main(String[] args) {
    // HashMap<String, String> param = new HashMap<String, String>();
    // param.put("username", "111");
    // param.put("password", "222");
    // HttpBody body = new HttpBody(param);
    // body.put("body", body);
    // System.out.println(body);
    // System.out.println(body.getInt("password2"));
    // System.out.println(body.hasKey("password"));

    // ArrayList<Object> list = new ArrayList<Object>();
    // list.add("111");
    // list.add("aaa");
    // list.add(787);
    // list.add("333");
    // list.add(body);
    // HttpBody bod2 = new HttpBody(list);
    // System.out.println(bod2);

    HttpBody bod3 = new HttpBody();
    HttpBody bod4 = new HttpBody(true);
    bod4.put(111).put(999);
    bod3.put("obj", bod4);
    System.out.println(bod3);
    System.out.println(bod3.get("obj") instanceof HttpBody);

  }


}
