package structure;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Node {
  private int id;
  private int parentId;
  private int grandparentId;
  private String title;
  private String note;
  private String type;
  private ArrayList<Node> nodes = new ArrayList<Node>();
  private HashMap<Integer, Integer> index = new HashMap<Integer, Integer>();

  private final HashMap<String, Integer> TYPES =
      (HashMap<String, Integer>)
          Map.of(
              "Kanban", 1,
              "Board", 2,
              "Column", 3,
              "Event", 4);

  public Node(int id, String title, String note) {
    this.id = id;
    this.title = title;
    this.note = note;
    this.type = this.getClass().getName();
  }

  public Node() {
    this.type = this.getClass().getName();
  }

  private String getTypeByLevel(String type, int level) {
    int lvl = TYPES.get(type);
    lvl += level;

    String ret = "";
    Iterator<?> keysItr = this.TYPES.keySet().iterator();

    while (keysItr.hasNext()) {
      String key = (String)keysItr.next();
      int value = (int)this.TYPES.get(key);

      if(value == lvl){
        ret = key;
      }
    }

    return ret;
  }

  public int getId() {
    return this.id;
  }

  public int getParentId() {
    return this.parentId;
  }

  public int getGrandparentId() {
    return this.grandparentId;
  }

  public void setId(int aId) {
    this.id = aId;
  }

  public void setParentId(int aParentId) {
    this.parentId = aParentId;
  }

  public void setGrandparentId(int aGrandparentId) {
    this.grandparentId = aGrandparentId;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String aTitle) {
    this.title = aTitle;
  }

  public String getParentType() {
    return this.getParentType(this.type);
  }

  public String getParentType(String aType) {
    return this.getParentType(aType, -1);
  }

  public String getParentType(String aType, int aLevel) {
    return this.getTypeByLevel(aType, Math.abs(aLevel) * -1);
  }

  public String getChildType() {
    return this.getChildType(this.type);
  }

  public String getChildType(String aType) {
    return this.getChildType(aType);
  }

  public String getChildType(String aType, int aLevel) {
    return this.getTypeByLevel(aType, Math.abs(aLevel));
  }

  public String toString() {
    return "Node (id: " + this.id + ", title: " + this.title + ", note: " + this.note + ")";
  }

  // adds a node
  public Node addNode(Node aNode) {
    this.nodes.add(aNode);
    this.index.put(aNode.getId(), this.nodes.size() - 1);
    return aNode;
  }

  // removes a node
  public void removeNode(int id) {
    int index = this.index.get(id);
    this.index.remove(id);
    this.nodes.remove(index);
    this.remapIndex(id);
  }

  private void remapIndex(int startFrom) {
    int current = startFrom;
    for (Node each : this.nodes.subList(startFrom, this.nodes.size())) {
      this.index.replace(each.getId(), current);
      current++;
    }
  }
}
