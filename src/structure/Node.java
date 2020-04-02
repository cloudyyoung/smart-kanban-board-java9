package structure;

import java.util.*;

import com.google.gson.annotations.*;

import java.lang.reflect.Constructor;

/**
 * The {@code Node} class.
 *
 * @since 1.0
 * @version 2.1
 */
public abstract class Node {

  public static final int SORT_BY_ID = 0;
  public static final int SORT_BY_PRIORITY = 5;
  public static final int ORDER_BY_ASC = 0;
  public static final int ORDER_BY_DESC = 1;

  /**
   * The id of the instance, provided by the server. It is exposed to Gson, cannot be serialized and
   * can be deserialized.
   */
  @Expose(serialize = false, deserialize = true)
  private Integer id;

  /** The title of the instance. It is exposed to Gson, can be both serialized and deserialized. */
  @Expose private String title;

  /**
   * The note(description) of the instance. It is exposed to Gson, can be both serialized and
   * deserialized.
   */
  @Expose private String note;

  /**
   * The parent id of the instance. It is exposed to Gson, can be both serialized and deserialized.
   */
  @Expose private Integer parentId;

  /**
   * The boolean to indicate whether this instance is on the server. {@code false} inidicates this
   * instance is only existing in local storage.
   */
  private boolean existing = false;

  /** The parent object of the instance. */
  private Node parent;

  /** The children {@code Node} object of the instance. */
  private LinkedHashMap<Integer, Node> nodes = new LinkedHashMap<Integer, Node>();

  /**
   * The dictionary of the {@code Node} hierarchy. It is used to identify the parent or children
   * type.
   *
   * @see #getTypeByLevel(String, int)
   */
  private final HashMap<String, Integer> TYPE_DICTIONARY =
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
   * Constructor of {@code Node}, provide id, title and note.
   *
   * @param id the id in {@code int}
   * @param title the title in {@code String}
   * @param note the note in {@code String}
   * @param parent the parent node in {@code Node}
   */
  public Node(Integer id, String title, String note, Node parent) {
    this.setId(id);
    this.setTitleLocal(title);
    this.setNoteLocal(note);
    this.setParentLocal(parent);
  }

  /** Default constructor of {@code Kanban}. */
  public Node() {}

  /**
   * Constructor of {@code Node}, provide object to map.
   *
   * @param obj the object to map in {@code HttpBody}
   */
  public Node(HttpBody obj) {
    if (!this.getType().equals("Kanban")) {
      this.setId(obj.getInt("id"));
      this.setTitleLocal(obj.getString("title"));
      this.setNoteLocal(obj.getString("note"));
      this.existing = true;
    }
    if (this.getChildType() != null) {
      this.extractChildrenNodes(obj);
    }
  }

  /**
   * Sets the children {@code Nodes} for the instance.
   *
   * @param obj the object to map in {@code HttpBody}
   */
  private void extractChildrenNodes(HttpBody obj) {
    String childType = Node.typeLower(Node.typePlural(this.getChildType()));
    HttpBody value = obj.getList(childType);
    Collection<Object> list = value.values();
    for (Object each2 : list) {
      try {
        String type = Node.typeClass(childType);
        Class<?> cls = Class.forName(type);
        Constructor<?> constructor = cls.getConstructor(HttpBody.class);
        Object objNew = constructor.newInstance(each2);
        if (objNew instanceof Node) {
          Node nodeNew = (Node) objNew;
          nodeNew.setParentLocal(this);
        }
      } catch (Exception e) {
        // e.printStackTrace();
        // e.getCause();
        // fail silently
      }
    }
  }

  /**
   * Returns if the instance is existing in the server.
   *
   * @return {@code true} if the instance is existing in the server. {@code false} if the instance
   *     is only existing in the local storage.
   */
  public boolean isExisting() {
    return this.existing;
  }

  /**
   * Returns the type by specified type and level by using the dictionary {@link #TYPE_DICTIONARY}.
   *
   * @see #TYPE_DICTIONARY
   * @param type the type to look up
   * @param level the number of levels to look up
   * @return the looked up type. {@code null} if there is any invalidation.
   */
  private String getTypeByLevel(String type, int level) {
    int lvl = TYPE_DICTIONARY.get(type);
    lvl += level;

    String ret = null;
    Iterator<?> keysItr = this.TYPE_DICTIONARY.keySet().iterator();

    while (keysItr.hasNext()) {
      String key = (String) keysItr.next();
      int value = (int) this.TYPE_DICTIONARY.get(key);

      if (value == lvl) {
        ret = key;
      }
    }

    return ret;
  }

  /**
   * Returns the users id.
   *
   * @return the id of the instance
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Returns the parentid.
   *
   * @return the parent object of the instance
   */
  public Node getParent() {
    return this.parent;
  }

  /**
   * Sets the id.
   *
   * @param id the id of the instance
   */
  private void setId(Integer id) {
    this.id = id;
  }

  /**
   * Sets the parent {@code Node}, in local storage.
   *
   * @param parent the parent node of the instance
   * @return the strcuture request of the action
   */
  protected StructureRequest setParentLocal(Node parent) {
    
    if (parent != null) {
      // Remove self from old parent
      if (this.getParent() != null) {
        // this.getParent().removeNode(this);
      System.out.println(this.getParent());
    }

      // Set new parent
      this.parent = parent;
      this.parentId = this.parent.getId();
      this.getParent().addNode(this);
    } else {
      this.parent = null;
      this.parentId = null;
    }

    StructureRequest req = new StructureRequest(true, false, this);
    return req;
  }

  /**
   * Sets the parent {@code Node} of the instance.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @param parent the parent {@code Node} of the instance
   * @return the result object of this action
   */
  public Result setParent(Node parent) {
    Result res = new Result();
    if (this instanceof Event) {
      String parentType = Node.typeLower(Node.typePlural(this.getParentType()));
      HttpRequest req = this.set(parentType + "_id", parent.getId() + "");
      res.add(req);

      if (req.isSucceeded()) {
        StructureRequest req2 = this.setParentLocal(parent);
        res.add(req2);
      }
    } else {
      StructureRequest req2 = new StructureRequest(false, true, this);
      req2.setErrorMessage("Instance can only be type of Event");
      res.add(req2);
    }
    return null;
  }

  /**
   * Returns the title of the instance.
   *
   * @return the title of the instance
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Sets the title of the instance, in local storage.
   *
   * @param title the title of the instance
   * @return the strcuture request of the action
   */
  public StructureRequest setTitleLocal(String title) {
    this.title = title;

    StructureRequest req = new StructureRequest(true, false, this);
    return req;
  }

  /**
   * Sets the title of the instance.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @param title the title of the instance
   * @return the result object of this action
   */
  public Result setTitle(String title) {
    Result res = new Result();
    HttpRequest req = this.set("title", title);
    res.add(req);

    if (req.isSucceeded()) {
      StructureRequest req2 = this.setTitleLocal(title);
      res.add(req2);
    }
    return res;
  }

  /**
   * Sets the note of the instance, in local storage.
   *
   * @param note the note of the instance
   * @return the strcuture request of the action
   */
  public StructureRequest setNoteLocal(String note) {
    this.note = note;

    StructureRequest req = new StructureRequest(true, false, this);
    return req;
  }

  /**
   * Sets the note of the instance.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @param note the note of the instance
   * @return the result object of this action
   */
  public Result setNote(String note) {
    Result res = new Result();
    HttpRequest req = this.set("note", note);
    res.add(req);

    if (req.isSucceeded()) {
      StructureRequest req2 = this.setNoteLocal(note);
      res.add(req2);
    }
    return res;
  }

  /**
   * Returns the note of the instance.
   *
   * @return the note of the instance
   */
  public String getNote() {
    return this.note;
  }

  /**
   * Sets the specified key and value, in the server.
   *
   * @param key the key of the property
   * @param value the value of the property
   * @return the http request of this action, of sending the request to the server
   */
  protected HttpRequest set(String key, String value) {
    HttpBody body = new HttpBody();
    body.put(key, value);

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/" + Node.typeLower(Node.typePlural(this.getType())) + "/" + this.getId());
    req.setRequestMethod("PUT");
    req.setRequestBody(body);
    req.setRequestCookie(Node.getRequestCookie());
    req.send();
    // System.out.println(req.getRequestBodyString());
    // System.out.println(req.getResponseBodyString());
    return req;
  }

  /**
   * Returns the parent type of the instance.
   *
   * <p>The type of the instance will be used as specified type. Number {@code 1} will be used as
   * specified number of levels.
   *
   * @return the parent type of the instance
   */
  public String getParentType() {
    return this.getParentType(this.getType());
  }

  /**
   * Returns the parent type, provide a specified type name.
   *
   * <p>Number {@code 1} will be used as specified number of levels.
   *
   * @param type the specified parent type
   * @return the parent type of the instance. {@code null} if there is any invalidation.
   */
  public String getParentType(String type) {
    return this.getParentType(type, -1);
  }

  /**
   * Returns the parent type, provide a specified type name and number of levels.
   *
   * @param type the specified parent type
   * @param level the specified number of levels
   * @return the parent type of the instance. {@code null} if there is any invalidation.
   */
  public String getParentType(String type, int level) {
    return this.getTypeByLevel(type, Math.abs(level) * -1);
  }

  /**
   * Returns the child type.
   *
   * <p>The type of the instance will be used as specified type. Number {@code 1} will be used as
   * specified number of levels.
   *
   * @return the child type of the instance. {@code null} if there is any invalidation.
   */
  public String getChildType() {
    return this.getChildType(this.getType());
  }

  /**
   * Returns the child type, provide a specified type.
   *
   * <p>Number {@code 1} will be used as specified number of levels.
   *
   * @param type the specified type
   * @return the child type of the instance. {@code null} if there is any invalidation.
   */
  public String getChildType(String type) {
    return this.getChildType(type, 1);
  }

  /**
   * Returns the child type, provide a specified type and number of levels.
   *
   * @param type the specified type
   * @param level the specified number of levels
   * @return the child type of the instance. {@code null} if there is any invalidation.
   */
  public String getChildType(String type, int level) {
    return this.getTypeByLevel(type, Math.abs(level));
  }

  /** {@inheritDoc} */
  public String toString() {
    return this.getType()
        + " (id: "
        + this.getId()
        + ", title: \""
        + this.getTitle()
        + "\", note: \""
        + this.getNote()
        + "\", nodes: "
        + this.getChildrenNodes().toString()
        + "\")";
  }

  /**
   * Returns the request cookie for <b>sending all requests validly</b>.
   *
   * <p>Generally, the server, specifically for PHP, the requests are identified by the {@code
   * PHPSESSID} to determine whether they are valid (whether from a signed in user).
   *
   * @return the request cookie object
   */
  private static HttpBody getRequestCookie() {
    HttpBody cookie = new HttpBody();
    cookie.put("PHPSESSID", User.current.getSessionId());
    return cookie;
  }

  /**
   * Creates the instance on the server.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @return the result object of this action
   */
  public Result add() {
    Result res = new Result();

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/" + Node.typeLower(Node.typePlural(this.getType())));
    req.setRequestMethod("POST");
    req.setRequestBody(this);
    req.setRequestCookie(Node.getRequestCookie());
    req.send();
    res.add(req);

    if (req.isSucceeded()) {
      Node parent = this.getParent();
      StructureRequest req2 = parent.addNode(this);
      res.add(req2);
    }

    return res;
  }

  /**
   * Removes the instance on the server.
   *
   * <p>This is an <i>action</i> for controllers.
   *
   * @return the result object of this action
   */
  public Result remove() {
    Result res = new Result();

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/" + Node.typeLower(Node.typePlural(this.getType())) + "/" + this.getId());
    req.setRequestMethod("DELETE");
    req.setRequestCookie(Node.getRequestCookie());
    req.send();
    res.add(req);

    if (req.isSucceeded()) {
      Node parent = this.getParent();
      StructureRequest req2 = parent.removeNode(this.getId());
      res.add(req2);
    }

    return res;
  }

  /**
   * Adds a child node of the instance, in the local storage.
   *
   * @param node the {@code Node} object to be added
   * @return the structure request of this action
   */
  public StructureRequest addNode(Node nodeAdded) {
    if (nodeAdded != null) {
      this.nodes.put(nodeAdded.getId(), nodeAdded);
      if (nodeAdded.getParent() != this) nodeAdded.setParent(this);
      return new StructureRequest(true, false, this);
    } else {
      return new StructureRequest(false, true, this);
    }
  }

  /**
   * Removes a child node of the instance, in the local storage.
   *
   * @param id the id of the {@code Node} object to be removed
   * @return the strcuture request of the action
   */
  public StructureRequest removeNode(int id) {
    boolean succeeded = this.nodes.remove(id) != null;
    return new StructureRequest(succeeded, !succeeded, this);
  }

  /**
   * Removes a child node of the instance, in the local storage.
   *
   * @param node the node instance of the {@code Node} object to be removed
   * @return the strcuture request of the action
   */
  public StructureRequest removeNode(Node node) {
    return this.removeNode(node.getId());
  }

  /**
   * Returns the node of the instance maps to the specified id.
   *
   * @param id the specified id
   * @return the node obejct maps to the specified id
   */
  public Node getNode(int id) {
    return this.nodes.get(id);
  }

  /**
   * Returns all the children nodes of the instance.
   *
   * @return a {@code Collection} of all the children nodes
   */
  public ArrayList<Node> getChildrenNodes() {
    return this.getChildrenNodes(Node.SORT_BY_ID, Node.ORDER_BY_ASC);
  }

  public ArrayList<Node> getChildrenNodes(int sortBy, int order) {
    ArrayList<Node> list = new ArrayList<Node>(this.nodes.values());
    Collections.sort(
        list,
        new Comparator<Node>() {
          @Override
          public int compare(Node entry1, Node entry2) {
            if (entry1 instanceof Event
                && entry2 instanceof Event
                && (sortBy == Node.SORT_BY_PRIORITY)) {
              return ((Event) entry1).getPriority() - ((Event) entry2).getPriority();
            }
            return entry1.getId() - entry2.getId();
          }
        });
    if (order == Node.ORDER_BY_DESC) Collections.reverse(list);
    return list;
  }

  /**
   * Returns the type of the instance
   *
   * @return the type of the instance
   */
  public String getType() {
    return this.getClass().getSimpleName();
  }

  /**
   * Returns a specified type in a format of Java class. Such as {@code structure.Node}.
   *
   * @param type a specified type
   * @return a string of a specified type in a format of Java class
   */
  public static String typeClass(String type) {
    return "structure." + Node.typeProper(Node.typeSingular(type));
  }

  /**
   * Returns a specified type in a plural format
   *
   * @param type a specified type
   * @return a string of a specified type in a plural format
   */
  public static String typePlural(String type) {
    return type.endsWith("s") || type.length() <= 0 ? type : type + "s";
  }

  /**
   * Returns a specified type in a singular format.
   *
   * @param type a specified type
   * @return a string of a specified type in a singular format
   */
  public static String typeSingular(String type) {
    return type.endsWith("s") && type.length() > 0 ? type.substring(0, type.length() - 1) : type;
  }

  /**
   * Returns a specified type in a proper-case format.
   *
   * @param type a specified type
   * @return a string of a specified type in a proper-case format
   */
  public static String typeProper(String type) {
    return type.substring(0, 1).toUpperCase() + type.toLowerCase().substring(1);
  }

  /**
   * Returns a specified type in a lower-case format.
   *
   * @param type a specified type
   * @return a string of a specified type in a lower-case format
   */
  public static String typeLower(String type) {
    return type.toLowerCase();
  }

  /**
   * Returns a specified type in a upper-case format.
   *
   * @param type a specified type
   * @return a string of a specified type in a upper-case format
   */
  public static String typeUpper(String type) {
    return type.toUpperCase();
  }

  // test
  public LinkedHashMap<Integer, Node> getNodes() {
    return this.nodes;
  }

  public static void main(String[] args) {
    User user = new User();
    user.authenticate("cloudy", "cloudy");
    // System.out.println((user);
    Kanban.checkout();
    // System.out.println((Kanban.current);

    Node node = Kanban.current.getNode(50);
    // System.out.println((node);
    node.setTitle("new title");
    // System.out.println((node);

    // Kanban kanban = new Kanban();
    // Board aNode = new Board("new Node2 cloudyyyyyy", "", "#00b0f0");
    // kanban.addNode(aNode);
  }
}
