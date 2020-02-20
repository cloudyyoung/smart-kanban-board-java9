import java.util.HashMap;

public class Node {
  private int id;
  private int parentId;
  private int grandparentId;
  private String title;
  private String note;
  private String type;

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
    HashMap<String, Integer> types = new HashMap<String, Integer>();
    types.put("Kanban", 1);
    types.put("Board", 2);
    types.put("Column", 3);
    types.put("Event", 4);

    int lvl = types.get(type);
    lvl += level;

    String ret = "";
    
    // NEED IMPLEMENTATION

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
}
