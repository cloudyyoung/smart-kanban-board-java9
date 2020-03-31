package structure;

/**
 * The {@code Column} class, extends from {@code Node}.
 *
 * <p>The instance should contains {@code Event} object as children nodes.
 *
 * @since 1.0
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

  /**
   * Constructor of {@code Column}, provide title, note and color.
   *
   * @param title The title in {@code String}
   * @param note The note in {@code String}
   * @param id THe id in {@code String}
   */
  public Column(int id, String title, String note) {
    super(id, title, note);
  }
}
