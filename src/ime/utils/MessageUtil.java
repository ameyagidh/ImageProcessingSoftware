package ime.utils;

import ime.controller.enums.Command;

/**
 * The MessageUtil class provides utility methods for generating messages related to errors,
 * or where ever required.
 */
public class MessageUtil {

  /**
   * Generates a message indicating an invalid number of arguments for a command.
   *
   * @param command The command for which the number of arguments is invalid.
   * @return A formatted message indicating the invalid number of arguments for the command.
   */
  public static String getInvalidNumberOfArgsMessage(Command command) {
    return "Invalid number of Arguments for command: "
            + command.command() + " required Args: "
            + command.requiredArgs();
  }

  /**
   * Retrieves an error message indicating the inability to perform a specific operation.
   * This message is typically displayed when attempting an operation that requires a valid image,
   * and there is currently no valid image loaded.
   *
   * @return A String containing the error message suggesting to try again after loading
   *         a valid image.
   */
  public static String getPerformOperationErrorMessage() {
    return "Unable to perform an operation. "
            + "Please try again after loading a valid image";
  }

  /**
   * Retrieves an error message indicating that a specified operation could not be performed
   * due to an invalid file path. This message is displayed when the operation requires a valid
   * file path, and the provided path is considered invalid.
   *
   * @return A String containing the error message suggesting to try again with a valid file path.
   */
  public static String getInvalidFilePathErrorMessage() {
    return "Unable to perform an operation. Please provide a valid file path";
  }

}
