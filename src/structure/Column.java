package structure;

/**
 * The {@code Column} class, extends from {@code Node}.
 *
 * <p>The instance should contains {@code Event} object as children nodes.
 *
 * @since Kanban 1.0
 * @version 2.1
 */
public class Column extends Node {
  /**
   * Constructor of {@code Column}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Column(HttpBody obj) {
    super(obj);
  }
}
