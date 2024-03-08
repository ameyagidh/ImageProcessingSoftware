package ime.controller.utils;

/**
 * Utility class providing image-related helper functions.
 * <p>This class offers various utility methods designed to aid in the processing and manipulation
 * of image files. Being a utility class, it is not intended for instantiation and its methods are
 * static.</p>
 */
public class ImageUtil {

  /**
   * Retrieves the file extension from a given file name.
   * <p>This method extracts and returns the extension of a file, identified by the substring
   * after the last occurrence of a dot ('.'). If no dot is found in the file name, it throws
   * an {@code IllegalArgumentException}.</p>
   *
   * @param fileName the name of the file from which the extension is to be extracted.
   * @return the file extension as a string.
   * @throws IllegalArgumentException if the file name does not contain a dot, indicating
   *                                  no extension.
   */
  public static String getExtension(String fileName) throws IllegalArgumentException {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1) {
      throw new IllegalArgumentException("No extension found");  // No extension found
    }
    return fileName.substring(lastIndexOfDot + 1);
  }
}
