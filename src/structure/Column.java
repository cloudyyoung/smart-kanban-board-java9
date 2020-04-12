package structure;

/**
 * The {@code Column} class, extends from {@code Node}.
 *
 * <p>The instance should contains {@code Event} object as children nodes.
 *
 * @since 1.0
 * @version 2.1
 */
public final class Column extends Node {

  public static final int TO_DO = 0;
  public static final int IN_PROGRESS = 1;
  public static final int DONE = 2;

  /**
   * Constructor of {@code Column}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  private int preset;

  public Column(HttpBody obj) {
    super(obj);
    this.setPreset(obj.getInt("preset"));
  }

  /**
   * Constructor of {@code Column}, provide title, note and color.
   *
   * @param title The title in {@code String}
   * @param note The note in {@code String}
   * @param columnId THe id in {@code String}
   */
  public Column(final String title, final String note, final Node parent) {
    super(title, note, parent);
  }

  protected void setPreset(int preset) {
    this.preset = preset;
  }

  public int getPreset() {
    return this.preset;
  }

  public boolean hasEnoughTime(Event eventNext) {
    Long totalTime = 25_200L;
    Long timeAccumulator = 0L;
    for (Node node : this.getNodes()) {
      final Event event = (Event) node;
      timeAccumulator += event.getDuration();
    }
    return (eventNext.getDuration() + timeAccumulator) <= totalTime ? true : false;
  }

  @Override
  public String toString() {
    return this.getType()
        + " (id: "
        + this.getId()
        + ", title: \""
        + this.getTitle()
        + "\", note: \""
        + this.getNote()
        + "\", preset: "
        + this.getPreset()
        + ", nodes: "
        + this.getNodes().toString()
        + "\")";
  }
}
