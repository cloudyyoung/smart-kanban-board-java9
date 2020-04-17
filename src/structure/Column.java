package structure;

import com.google.gson.annotations.Expose;

/**
 * The {@code Column} class, extends from {@code Node}.
 *
 * <p>The instance should contains {@code Event} object as children nodes.
 *
 * @author Cloudy Young, Jimschenchen
 * @since 1.0
 * @version 4.0
 */
public final class Column extends Node {

  /** Static variable representing a preset of To Do */
  public static final int TO_DO = 0;

  /** Static variable representing a preset of In Progress */
  public static final int IN_PROGRESS = 1;

  /** Static variable representing a preset of Done */
  public static final int DONE = 2;

  /**
   * Preset of the column, could and only be {@code TO_DO}, {@code IN_PROGRESS} or {@code DONE}. It
   * is exposed to Gson, can be both serialized and deserialized.
   */
  @Expose private int preset;

  /**
   * Constructor of {@code Column}, provide {@code HttpBody}.
   *
   * @param obj the {@code HttpBody} for initialization
   */
  public Column(HttpBody obj) {
    super(obj);
    this.setPreset(obj.getInt("preset"));
  }

  /**
   * Constructor of {@code Column}, provide title, note and color.
   *
   * @version 4.0
   * @param title The title in {@code String}
   * @param note The note in {@code String}
   */
  public Column(final String title, final String note, final int preset, final Node parent) {
    super(title, note, parent);
    this.setPreset(preset);
  }

  /**
   * Sets the preset of the column
   *
   * @version 4.0
   * @param preset the preset of the column
   */
  protected void setPreset(int preset) {
    this.preset = preset;
  }

  /**
   * Sets the color of the board.
   *
   * @version 4.0
   * @param preset the preset of the column
   * @return the {@code Result} instance of this action
   */
  public Result setPresetRequest(int preset) {
    Result res = new Result();
    if (!this.isExisting()) {
      this.setPreset(preset);

      StructureRequest req = new StructureRequest(true, false, false, this);
      res.add(req);
    } else {
      HttpRequest req = this.update("preset", preset);
      if (req.isSucceeded()) {
        this.setPreset(preset);

        StructureRequest req2 = new StructureRequest(true, false, false, this);
        res.add(req2);
      }
      res.add(req);
    }
    return res;
  }

  /**
   * Returns the preset of the column
   *
   * @version 4.0
   * @return the preset of the column
   */
  public int getPreset() {
    return this.preset;
  }

  /**
   * Returns if the column is the only one of its preset in the parent board
   *
   * @version 4.0
   * @return true if the column is the only one of its preset in the parent board, false otherwise
   */
  public boolean isOnlyPreset() {
    int count = 0;
    Board board = (Board) this.getParent();
    for (Node node : board.getNodes()) {
      Column column = (Column) node;
      if (column.getPreset() == this.preset) {
        count += 1;
      }
    }
    return count <= 1;
  }

  /** {@inheritDoc} */
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
