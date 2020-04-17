package structure;

import java.util.*;

import com.google.gson.annotations.*;

import java.lang.reflect.Constructor;

/**
 * The {@code Node} class.
 *
 * @author Cloudy Young, Jimschenchen, Benjamin Wood
 * @since 1.0
 * @version 4.0
 */
public abstract class Node {

  /** Static variable representing a sorting reference id */
  public static final int SORT_BY_ID = 0;

  /** Static variable representing a sorting reference priority */
  public static final int SORT_BY_PRIORITY = 5;

  /** Static variable representing a ordering reference ascending */
  public static final int ORDER_BY_ASC = 0;

  /** Static variable representing a ordering reference descending */
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
  @SerializedName("parent_id")
  @Expose
  private Integer parentId;

  /**
   * The boolean to indicate whether this instance is on the server. {@code false} inidicates this
   * instance is only existing in local storage.
   */
  private boolean existing = false;

  /** The parent object of the instance. */
  private Node parent;

  /** The children {@code Node} object of the instance. */
  private LinkedHashMap<Integer, Node> nodes = new LinkedHashMap<Integer, Node>();

  private int idAutoIncrement = 1;

  private boolean specialized = false;

  /**
   * The dictionary of the {@code Node} hierarchy. It is used to identify the parent or children
   * type.
   *
   * @see #getTypeByLevel(String, int)
   */
  private static final HashMap<String, Integer> TYPE_DICTIONARY =
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
   * Constructor of {@code Node}, provide title, note and parent.
   *
   * @param title the title in {@code String}
   * @param note the note in {@code String}
   * @param parent the parent node in {@code Node}
   */
  protected Node(String title, String note, Node parent) {
    int id = parent == null ? -1 : parent.idAutoIncrement++;
    this.setId(id);
    this.setTitle(title);
    this.setNote(note);
    this.setParent(parent);
  }

  /**
   * Constructor of {@code Node}, provide object to map.
   *
   * @param obj the object to map in {@code HttpBody}
   */
  protected Node(HttpBody obj) {
    this.setId(obj.getInt("id"));
    this.existing = true;

    if (this instanceof Kanban == false) {
      this.setTitle(obj.getString("title"));
      this.setNote(obj.getString("note"));
      this.existing = true;
    }
    this.extractChildrenNodes(obj);
  }

  /**
   * Sets the children {@code Nodes} for the instance.
   *
   * @param obj the object to map in {@code HttpBody}
   */
  private final void extractChildrenNodes(HttpBody obj) {
    if (this.getChildType() == null) {
      return;
    }

    String childType = NodeTypeUtils.typeUrl(this.getChildType());
    HttpBody value = obj.getList(childType);
    Collection<Object> list = value.values();
    for (Object each2 : list) {
      try {
        String type = NodeTypeUtils.typeClass(childType);
        Class<?> cls = Class.forName(type);
        Constructor<?> constructor = cls.getConstructor(HttpBody.class);
        Object objNew = constructor.newInstance(each2);
        if (objNew instanceof Node) {
          Node nodeNew = (Node) objNew;
          nodeNew.setParent(this);
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
  public final boolean isExisting() {
    return this.existing;
  }

  private final void setExisting(boolean is) {
    this.existing = is;
  }

  /**
   * Returns the type by specified type and level by using the dictionary {@link #TYPE_DICTIONARY}.
   *
   * @see #TYPE_DICTIONARY
   * @param type the type to look up
   * @param level the number of levels to look up
   * @return the looked up type. {@code null} if there is any invalidation.
   */
  private final String getTypeByLevel(String type, int level) {
    int lvl = TYPE_DICTIONARY.get(type);
    lvl += level;

    String ret = null;
    Iterator<?> keysItr = Node.TYPE_DICTIONARY.keySet().iterator();

    while (keysItr.hasNext()) {
      String key = (String) keysItr.next();
      int value = (int) Node.TYPE_DICTIONARY.get(key);

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
  public final Integer getId() {
    return this.id;
  }

  /**
   * Returns the parentid.
   *
   * @return the parent object of the instance
   */
  public final Node getParent() {
    return this.parent;
  }

  /**
   * Sets the id.
   *
   * @param id the id of the instance
   */
  private final void setId(Integer id) {
    this.id = id;
  }

  /**
   * Sets the parent {@code Node}, in local storage.
   *
   * @param parent the parent node of the instance
   */
  protected final void setParent(Node parent) {
    if (parent != null) {
      if (!parent.isSpecialized()) {
        // Remove self from old
        if (this.getParent() != null && this.getParent() != parent) {
          this.getParent().removeNode(this);
        }
        // Set new parent
        this.parent = parent;
        this.parentId = this.parent.getId();
      }

      if (this.isExisting() && !parent.isSpecialized()) {
        this.getParent().addNode(this);
      } else if (parent.isSpecialized()) {
        parent.addNode(this);
      }
    } else {
      // Remove parent
      this.parent = null;
      this.parentId = null;
    }
  }

  /**
   * Sets the parent {@code Node} of the instance.
   *
   * @param parent the parent {@code Node} of the instance
   * @return the result object of this action
   */
  public final Result setParentRequest(Node parent) {
    Result res = new Result();
    if (!this.isExisting()) {
      this.setParent(parent);
      StructureRequest req2 = new StructureRequest(true, false, false, this);
      res.add(req2);
    } else if (this instanceof Event) {
      String parentType = NodeTypeUtils.typeId(this.getParentType());
      HttpRequest req = this.update(parentType + "_id", parent.getId());
      res.add(req);

      if (req.isSucceeded()) {
        this.setParent(parent);
        StructureRequest req2 = new StructureRequest(true, false, false, this);
        res.add(req2);
      }
    } else {
      StructureRequest req2 = new StructureRequest(false, false, true, this);
      req2.setErrorMessage("Instance can only be type of Event");
      res.add(req2);
    }
    return res;
  }

  /**
   * Returns the title of the instance.
   *
   * @return the title of the instance
   */
  public final String getTitle() {
    return this.title == null ? "" : this.title;
  }

  /**
   * Sets the title of the instance, in local storage.
   *
   * @param title the title of the instance
   */
  protected final void setTitle(String title) {
    this.title = title;
  }

  /**
   * Sets the title of the instance.
   *
   * @param title the title of the instance
   * @return the result object of this action
   */
  public final Result setTitleRequest(String title) {
    Result res = new Result();
    if (!this.isExisting()) {
      this.setTitle(title);

      StructureRequest req = new StructureRequest(true, false, false, this);
      res.add(req);
    } else {
      HttpRequest req = this.update("title", title);
      res.add(req);

      if (req.isSucceeded()) {
        this.setTitle(title);

        StructureRequest req2 = new StructureRequest(true, false, false, this);
        res.add(req2);
      }
    }

    return res;
  }

  /**
   * Sets the note of the instance, in local storage.
   *
   * @param note the note of the instance
   */
  protected final void setNote(String note) {
    this.note = note;
  }

  /**
   * Sets the note of the instance.
   *
   * @param note the note of the instance
   * @return the result object of this action
   */
  public final Result setNoteRequest(String note) {
    Result res = new Result();
    if (!this.isExisting()) {
      this.setNote(note);

      StructureRequest req = new StructureRequest(true, false, false, this);
      res.add(req);
    } else {
      HttpRequest req = this.update("note", note);
      res.add(req);

      if (req.isSucceeded()) {
        this.setNote(note);

        StructureRequest req2 = new StructureRequest(true, false, false, this);
        res.add(req2);
      }
    }
    return res;
  }

  /**
   * Returns the note of the instance.
   *
   * @return the note of the instance
   */
  public final String getNote() {
    return this.note == null ? "" : this.note;
  }

  /**
   * Sets the specified key and value, in the server.
   *
   * @param key the key of the property
   * @param value the value of the property
   * @return the http request of this action, of sending the request to the server
   */
  protected final HttpRequest update(Object key, Object value) {
    HttpBody body = new HttpBody();
    body.put(key, value);

    HttpRequest req = new HttpRequest();
    req.setRequestUrl("/" + NodeTypeUtils.typeUrl(this.getType()) + "/" + this.getId());
    req.setRequestMethod("PUT");
    req.setRequestBody(body);
    req.setRequestCookie(this.getRequestCookie());
    req.send();
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
  protected final String getParentType() {
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
  protected final String getParentType(String type) {
    return this.getParentType(type, -1);
  }

  /**
   * Returns the parent type, provide a specified type name and number of levels.
   *
   * @param type the specified parent type
   * @param level the specified number of levels
   * @return the parent type of the instance. {@code null} if there is any invalidation.
   */
  protected final String getParentType(String type, int level) {
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
  protected final String getChildType() {
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
  protected final String getChildType(String type) {
    return this.getChildType(type, 1);
  }

  /**
   * Returns the child type, provide a specified type and number of levels.
   *
   * @param type the specified type
   * @param level the specified number of levels
   * @return the child type of the instance. {@code null} if there is any invalidation.
   */
  protected final String getChildType(String type, int level) {
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
        + this.getNodes().toString()
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
  private final HttpBody getRequestCookie() {
    HttpBody cookie = new HttpBody();
    cookie.put("PHPSESSID", User.getCurrent().getSessionId());
    return cookie;
  }

  /**
   * Creates the instance on the server.
   *
   * @return the result object of this action
   */
  public Result createRequest() {
    Result res = new Result();

    if (this.isExisting()) {
      StructureRequest req2 = new StructureRequest(false, false, true, this);
      req2.setErrorMessage("Event is already exisiting");
      res.add(req2);
    } else {
      HttpBody body = new HttpBody(this);
      body.put(NodeTypeUtils.typeId(this.getParentType()) + "_id", body.getInt("parent_id"));
      body.remove("parent_id");

      HttpRequest req = new HttpRequest();
      req.setRequestUrl("/" + NodeTypeUtils.typeUrl(this.getType()));
      req.setRequestMethod("POST");
      req.setRequestBody(body);
      req.setRequestCookie(this.getRequestCookie());
      req.send();
      res.add(req);

      if (req.isSucceeded()) {
        this.setId(req.getResponseBody().getInt("id"));
        this.getParent().addNode(this);
        this.setParent(this.getParent());
        this.setExisting(true);
        this.extractChildrenNodes(req.getResponseBody());

        StructureRequest req2 = new StructureRequest(true, false, false, this);
        res.add(req2);
      }
    }
    return res;
  }

  /**
   * Removes the instance on the server.
   *
   * @return the result object of this action
   */
  public Result deleteRequest() {
    Result res = new Result();

    if (!this.isExisting()) {
      StructureRequest req2 = new StructureRequest(false, false, true, this);
      req2.setErrorMessage("Node is not exisiting");
      res.add(req2);
    } else {

      HttpRequest req = new HttpRequest();
      req.setRequestUrl("/" + NodeTypeUtils.typeUrl(this.getType()) + "/" + this.getId());
      req.setRequestMethod("DELETE");
      req.setRequestCookie(this.getRequestCookie());
      req.send();
      res.add(req);

      if (req.isSucceeded()) {
        this.getParent().removeNode(this);
        this.setParent(null);

        StructureRequest req2 = new StructureRequest(true, false, false, this);
        res.add(req2);
      }
    }
    return res;
  }

  /**
   * Adds a child node of the instance, in the local storage.
   *
   * @param nodeToAdd the {@code Node} object to be added
   */
  private final void addNode(Node nodeToAdd) {
    this.nodes.put(nodeToAdd.getId(), nodeToAdd);
  }

  /**
   * Removes a child node of the instance, in the local storage.
   *
   * @param id the id of the {@code Node} object to be removed
   */
  private final void removeNode(int id) {
    this.nodes.remove(id);
  }

  /**
   * Removes a child node of the instance, in the local storage.
   *
   * @param node the node instance of the {@code Node} object to be removed
   */
  protected final void removeNode(Node node) {
    this.removeNode(node.getId());
  }

  /**
   * Returns the node of the instance maps to the specified id.
   *
   * @param id the specified id
   * @return the node obejct maps to the specified id
   */
  public final Node getNode(int id) {
    return this.nodes.get(id);
  }

  /**
   * Clears all the child node of the instance.
   *
   * @version 4.0
   */
  protected final void clearNodes() {
    this.nodes.clear();
  }

  /**
   * Returns all the children nodes of the instance.
   *
   * @version 4.0
   * @return a {@code Collection} of all the children nodes
   */
  public final ArrayList<Node> getNodes() {
    return this.getNodes(Node.SORT_BY_ID, Node.ORDER_BY_ASC);
  }

  /**
   * Returns all the children nodes of the instance, sorted and ordered by the given parameters.
   *
   * @version 4.0
   * @param sortBy the attribute to sort
   * @param orderBy the mode to order
   * @return a {@code Collection} of all the children nodes
   */
  public final ArrayList<Node> getNodes(int sortBy, int orderBy) {
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
    if (orderBy == Node.ORDER_BY_DESC) Collections.reverse(list);
    return list;
  }

  /**
   * Returns the type of the instance
   *
   * @return the type of the instance
   */
  public final String getType() {
    return this.getClass().getSimpleName();
  }

  /**
   * Returns a boolean to indicate whether this node is a specialized node
   *
   * @version 4.0
   * @return a boolean to indicate whether this node is a specialized node
   */
  public final boolean isSpecialized() {
    return this.specialized;
  }

  /**
   * Sets this node as specialized node.
   *
   * @version 4.0
   * @param is a boolean to indicate whether this node is a specialized node
   */
  protected final void setSpecialized(boolean is) {
    this.specialized = is;
    this.existing = is;
    this.setParent(this.parent);
  }
}
