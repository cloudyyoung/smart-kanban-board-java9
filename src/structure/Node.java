package structure;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Constructor;

@SuppressWarnings("unchecked")
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
  private ArrayList<Node> nodes = new ArrayList<Node>();
  private HashMap<Integer, Integer> index = new HashMap<Integer, Integer>();

  /**
   * creates a "dictionary" so it will be easier to determine the parent class of each main class ie
   * if the user wants to add something to column we can use this hash map to identify that the
   * parent of column is board, and board's parent is kanban
   *
   * <p>this will also be used to assign type on lines 74-90 (getTypebyLevel method)
   */
  private final HashMap<String, Integer> TYPES =
      new HashMap<String, Integer>() {
        private static final long serialVersionUID = 3312582702053699017L;

        {
          put("Kanban", 0);
          put("Board", 1);
          put("Column", 2);
          put("Event", 3);
        }
      };

  /**
   * constructor for node
   *
   * @param id as an int
   * @param title as a string
   * @param note as a string
   */
  public Node(int id, String title, String note) {
    this.id = id;
    this.title = title;
    this.note = note;
  }

  public Node(HashMap<String, ?> obj) {
    if (!this.getType().equals("Kanban")) {
      this.id = ((Long) obj.get("id")).intValue();
      this.title = (String) obj.get("title");
      this.note = (String) obj.get("note");
    }
    if (obj != null) {
      this.extractChildrenNodes(obj);
    }
    System.out.println(this);
  }

  private void extractChildrenNodes(HashMap<String, ?> obj) {
    String childType = Node.typeLower(Node.typePlural(this.getChildType()));
    Object value = obj.get(childType);
    if (value == null || value instanceof ArrayList == false) {
      return;
    }
    ArrayList<HashMap<String, ?>> list = (ArrayList<HashMap<String, ?>>) value;
    for (HashMap<String, ?> each2 : list) {
      try {
        String type = Node.typeClass(childType);
        Class<?> cls = Class.forName(type);
        Constructor<?> constructor = cls.getConstructor(HashMap.class);
        Object objNew = constructor.newInstance(each2);
        if (objNew instanceof Node) {
          this.nodes.add((Node) objNew);
        }
      } catch (Exception e) {
        // e.printStackTrace();
        // fail silently
      }
    }
  }

  /**
   * assigns type using the hashmap above
   *
   * @see lines 34 - 44
   * @param type as a string
   * @param level as an int
   * @return ret as a string which again can vary depending on the hashmap above
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

  /**
   * gets the users id
   *
   * @return the user's id as an int
   */
  public int getId() {
    return this.id;
  }

  /**
   * gets the parentid
   *
   * @return the parentId as an int
   */
  public int getParentId() {
    return this.parentId;
  }

  /**
   * gets the grandparentsId
   *
   * @return the grandparentsId as an int
   */
  public int getGrandparentId() {
    return this.grandparentId;
  }

  /**
   * sets the id
   *
   * @param aId as an int
   */
  public void setId(int aId) {
    this.id = aId;
  }

  /**
   * sets the parent id
   *
   * @param aParentId as an int
   */
  public void setParentId(int aParentId) {
    this.parentId = aParentId;
  }

  /**
   * sets the grand parent id
   *
   * @param aGrandparentId as an int
   */
  public void setGrandparentId(int aGrandparentId) {
    this.grandparentId = aGrandparentId;
  }

  /**
   * gets the title
   *
   * @return the title as a string this is the title of the of the columns on the board
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * sets the title
   *
   * @param aTitle as a string
   */
  public void setTitle(String aTitle) {
    this.title = aTitle;
  }

  /**
   * gets the parent type
   *
   * @return the objects current type
   */
  public String getParentType() {
    return this.getParentType(this.getType());
  }

  /**
   * gets the parent type with the type name
   *
   * @param aType as a string this is the parent's type
   * @return the parent's type calculated using hash map above more in-depth reading of the parent
   *     type as it also returns the type with its name
   */
  public String getParentType(String aType) {
    return this.getParentType(aType, -1);
  }

  /**
   * gets the parent type with the type name and the level
   *
   * @param aType - the parent's type as a string
   * @param aLevel - the parent's level as an int
   * @return the parent's type with name and level
   */
  public String getParentType(String aType, int aLevel) {
    return this.getTypeByLevel(aType, Math.abs(aLevel) * -1);
  }

  /** @return */
  public String getChildType() {
    return this.getChildType(this.getType());
  }

  /**
   * @param aType
   * @return
   */
  public String getChildType(String aType) {
    return this.getChildType(aType, 1);
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
    return this.getType()
        + " (id: "
        + this.id
        + ", title: \""
        + this.title
        + "\", note: \""
        + this.note
        + "\")";
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
   * @param id as an int this is the node's id
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

  public String getType() {
    String str = this.getClass().getName();
    return str.substring(str.lastIndexOf(".") + 1);
  }

  public static String typeClass(String type) {
    return "structure." + Node.typeProper(Node.typeSingular(type));
  }

  public static String typePlural(String type) {
    return type.endsWith("s") || type.length() <= 0 ? type : type + "s";
  }

  public static String typeSingular(String type) {
    return type.endsWith("s") && type.length() > 0 ? type.substring(0, type.length() - 1) : type;
  }

  public static String typeProper(String type) {
    return type.substring(0, 1).toUpperCase() + type.toLowerCase().substring(1);
  }

  public static String typeLower(String type) {
    return type.toLowerCase();
  }

  public static String typeUpper(String type) {
    return type.toUpperCase();
  }
}
