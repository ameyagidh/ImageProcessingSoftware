package ime.controller.helpers.file;

import java.io.IOException;

/**
 * Interface representing a file helper utility.
 * It provides methods to perform file-related operations.
 */
public interface FileHelper {

  /**
   * Reads the content of a file located at the specified filepath.
   *
   * @param filepath The path to the file that needs to be read.
   * @return The content of the file as a String.
   * @throws IOException If any I/O error occurs during reading the file.
   */
  String readFile(String filepath) throws IOException;
}
