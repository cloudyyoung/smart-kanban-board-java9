package structure;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Node {

  /**
   * int id: the users id -> associated to the user name and password int parentId:????? int
   * grandparentId:???? String title: this will be the titles of each column on the board String
   * note:???? String type: type will be used to determine where the user is in the application
   */
  private int id;

  private int parentId;
  private int grandparentId;
  private String title;
  private String note;
  private String type;
  private ArrayList<Node> nodes = new ArrayList<Node>();
  private HashMap<Integer, Integer> index = new HashMap<Integer, Integer>();

  /**
   * creates a "dictionary" so the cards can be sorted into rows
   *
   * @return the location of the user on the "left" side of the table as a string
   * @return the number associated to the location, or the "right" side as an int
   */
  private final HashMap<String, Integer> TYPES =
      (HashMap<String, Integer>)
          Map.of(
              "Kanban", 1,
              "Board", 2,
              "Column", 3,
              "Event", 4);

  /**
   * @param id
   * @param title
   * @param note
   */
  public Node(int id, String title, String note) {
    this.id = id;
    this.title = title;
    this.note = note;
    this.type = this.getClass().getName();
  }

  /*
   *
   *
   */
  public Node() {
    this.type = this.getClass().getName();
  }

  /**
   * @param type
   * @param level
   * @return
   */
  private String getTypeByLevel(String type, int level) {
    int lvl = TYPES.get(type);
    lvl += level;

    String ret = "";
    Iterator<?> keysItr = this.TYPES.keySet().iterator();

    while (keysItr.hasNext()) {
      String key = (String) keysItr.next();
      int value = (int) this.TYPES.get(key);

      if (value == lvl) {
        ret = key;
      }
    }

    return ret;
  }

  /** @return */
  public int getId() {
    return this.id;
  }

  /** @return */
  public int getParentId() {
    return this.parentId;
  }

  /** @return */
  public int getGrandparentId() {
    return this.grandparentId;
  }

  /** @param aId */
  public void setId(int aId) {
    this.id = aId;
  }

  /** @param aParentId */
  public void setParentId(int aParentId) {
    this.parentId = aParentId;
  }

  /** @param aGrandparentId */
  public void setGrandparentId(int aGrandparentId) {
    this.grandparentId = aGrandparentId;
  }

  /** @return */
  public String getTitle() {
    return this.title;
  }

  /** @param aTitle */
  public void setTitle(String aTitle) {
    this.title = aTitle;
  }

  /** @return */
  public String getParentType() {
    return this.getParentType(this.type);
  }

  /**
   * @param aType
   * @return
   */
  public String getParentType(String aType) {
    return this.getParentType(aType, -1);
  }

  /**
   * @param aType
   * @param aLevel
   * @return
   */
  public String getParentType(String aType, int aLevel) {
    return this.getTypeByLevel(aType, Math.abs(aLevel) * -1);
  }

  /** @return */
  public String getChildType() {
    return this.getChildType(this.type);
  }

  /**
   * @param aType
   * @return
   */
  public String getChildType(String aType) {
    return this.getChildType(aType);
  }

  /**
   * @param aType
   * @param aLevel
   * @return
   */
  public String getChildType(String aType, int aLevel) {
    return this.getTypeByLevel(aType, Math.abs(aLevel));
  }

  /**
   * converts id, title and note to a string for output
   *
   * @return the id, title and note as a combined string
   */
  public String toString() {
    return "Node (id: " + this.id + ", title: " + this.title + ", note: " + this.note + ")";
  }

  /**
   * Adds a node
   *
   * @param aNode
   * @return
   */
  public Node addNode(Node aNode) {
    this.nodes.add(aNode);
    this.index.put(aNode.getId(), this.nodes.size() - 1);
    return aNode;
  }

  /**
   * removes a node
   *
   * @param id
   */
  public void removeNode(int id) {
    int index = this.index.get(id);
    this.index.remove(id);
    this.nodes.remove(index);
    this.remapIndex(id);
  }

  /** @param startFrom */
  private void remapIndex(int startFrom) {
    int current = startFrom;
    for (Node each : this.nodes.subList(startFrom, this.nodes.size())) {
      this.index.replace(each.getId(), current);
      current++;
    }
  }
}
