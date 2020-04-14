package structure;

public final class NodeTypeUtils {

  public static final String typeClass(String str) {
    return "structure." + typeProper(typeSingular(str));
  }

  /**
   * Returns a specified type in a format of Java class. Such as {@code structure.Node}.
   *
   * @param type a specified type
   * @return a string of a specified type in a format of Java class
   */
  public static final String typeUrl(String str) {
    return typeLower(typePlural(str));
  }

  public static final String typeId(String str){
    return typeLower(typeSingular(str));
  }

  /**
   * Returns a specified type in a plural format
   *
   * @param str a specified type
   * @return a string of a specified type in a plural format
   */
  private static final String typePlural(String str) {
    return str.endsWith("s") || str.length() <= 0 ? str : str + "s";
  }

  /**
   * Returns a specified type in a singular format.
   *
   * @param str a specified type
   * @return a string of a specified type in a singular format
   */
  private static final String typeSingular(String str) {
    return str.endsWith("s") && str.length() > 0 ? str.substring(0, str.length() - 1) : str;
  }

  /**
   * Returns a specified type in a proper-case format.
   *
   * @param str a specified type
   * @return a string of a specified type in a proper-case format
   */
  private static final String typeProper(String str) {
    return str.substring(0, 1).toUpperCase() + str.toLowerCase().substring(1);
  }

  /**
   * Returns a specified type in a lower-case format.
   *
   * @param str a specified type
   * @return a string of a specified type in a lower-case format
   */
  private static final String typeLower(String str) {
    return str.toLowerCase();
  }
}
