package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The Sepia class represents an image manipulation command that applies a Sepia tone effect to an
 * input image. It implements the ImageProcessorCommand interface and defines methods to construct,
 * apply, and execute the Sepia effect.
 */
public class Sepia implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a Sepia command with the specified input image name and destination image name.
   *
   * @param args The arguments to perform the Sepia effect on the input image.
   */
  public Sepia(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a Sepia command based on the input provided through an array of arguments.
   * Throws an InputMismatchException if the number of arguments is not valid for the Sepia effect
   * operation.
   *
   * @param args Arguments provided by the user to perform the Sepia effect operation.
   * @return A Sepia command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the Sepia effect
   *                                operation.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (!(args.length == Command.SEPIA.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.SEPIA));
    }

    return new Sepia(args);
  }

  /**
   * Executes the Sepia command by applying the Sepia tone effect to the input image and saving
   * the result to the specified destination image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the Sepia effect command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.sepia(args);
  }
}
