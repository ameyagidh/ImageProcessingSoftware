package ime.controller.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ScriptParser class provides utility methods for parsing scripts.
 * It includes a method to split a script into individual parts, taking into
 * consideration quoted strings and non-whitespace sequences.
 */
public class ScriptParser {

  /**
   * Parses the input script into an array of individual parts.
   * The script is split based on spaces, considering quoted strings (single or double)
   * as single entities and preserving their content.
   *
   * @param input The input script to be parsed.
   * @return An array of strings representing the individual parts of the script.
   */
  public static String[] parseScript(String input) {
    String patternString = "'([^']+)'|\"([^\"]+)\"|(\\S+)";
    Pattern pattern = Pattern.compile(patternString);
    Matcher matcher = pattern.matcher(input);

    int count = 0;
    while (matcher.find()) {
      count++;
    }

    String[] parts = new String[count];
    matcher.reset();

    int index = 0;
    while (matcher.find()) {
      parts[index++] = matcher.group(1) != null
              ? matcher.group(1) : matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
    }

    return parts;
  }

}
