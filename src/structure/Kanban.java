package structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Constructor;

@SuppressWarnings("unchecked")

public class Kanban {

    private ArrayList<Node> boards = new ArrayList<Node>();

    public void fetchAll(){
        HttpRequest req = new HttpRequest();
        req.setRequestUrl("/kanban");
        req.setRequestMethod("GET");
        boolean res = req.send();
        if(res){
            this.convertKanban((HashMap<String, ?>)req.getRequestBody());
        }
    }

    private void convertKanban(HashMap<String, ?> obj){
        for(HashMap.Entry<String, ?> each : obj.entrySet()){
            String key = each.getKey();
            Object value = each.getValue();
            if(value instanceof ArrayList){
                ArrayList<HashMap<String, ?>> list = (ArrayList<HashMap<String, ?>>)value;
                for(HashMap<String, ?> each2 : list){
                    try{
                        String type = Node.typeToClass(key);
                        Class<?> cls = Class.forName(type);
                        Constructor<?> constructor = cls.getConstructor(HashMap.class);
                        System.out.println(constructor);
                        Object objNew = constructor.newInstance(each2);
                        System.out.println(objNew.toString());
                    }catch(Exception e){
                        e.printStackTrace();
                        // fail silently
                    }
                }
            }
        }
    }

    public static void main(String[] args){
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

        Kanban kanban = new Kanban();
        kanban.convertKanban((HashMap<String, ?>)req2.getResponseBody());
    }

}
