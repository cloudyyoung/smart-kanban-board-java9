package structure;

/**
 * The Node type utilities class.
 *
 * @author Cloudy Young
 * @version 4.0
 */
public final class NodeTypeUtils {

  /**
   * Returns the given string in class form.
   *
   * @param str the string to be parsed
   * @return the string parsed
   */
  public static final String typeClass(String str) {
    if(str == null) return "";
    return "structure." + typeProper(typeSingular(str));
  }

  /**
   * Returns the given string in url form.
   *
   * @param str the string to be parsed
   * @return the string parsed
   */
  public static final String typeUrl(String str) {
    if(str == null) return "";
    return typeLower(typePlural(str));
  }

  /**
   * Returns the given string in id form.
   *
   * @param str the string to be parsed
   * @return the string parsed
   */
  public static final String typeId(String str) {
    if(str == null) return "";
    return typeLower(typeSingular(str));
  }

  /**
   * Returns a specified string in a plural format
   *
   * @param str the string to be parsed
   * @return the string parsed
   */
  private static final String typePlural(String str) {
    return str.endsWith("s") || str.length() <= 0 ? str : str + "s";
  }

  /**
   * Returns a specified string in a singular format.
   *
   * @param str the string to be parsed
   * @return the string parsed
   */
  private static final String typeSingular(String str) {
    return str.endsWith("s") && str.length() > 0 ? str.substring(0, str.length() - 1) : str;
  }

  /**
   * Returns a specified string in a proper-case format.
   *
   * @param str the string to be parsed
   * @return the string parsed
   */
  private static final String typeProper(String str) {
    return str.substring(0, 1).toUpperCase() + str.toLowerCase().substring(1);
  }

  /**
   * Returns a specified string in a lower-case format.
   *
   * @param str the string to be parsed
   * @return the string parsed
   */
  private static final String typeLower(String str) {
    return str.toLowerCase();
  }
}
