package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The RedComponent class represents an image manipulation command that extracts the red component
 * from the input image. It implements the ImageProcessorCommand interface and defines methods to
 * construct, apply, and execute the extraction of the red component on an input image.
 */
public class RedComponent implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a RedComponent command with the specified input image name and destination
   * image name.
   *
   * @param args The arguments to perform the specified red component extraction operation.
   */
  public RedComponent(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a RedComponent command based on the input provided through an array of
   * arguments. Throws an InputMismatchException if the number of arguments is not valid for the
   * red component extraction operation.
   *
   * @param args Arguments provided by the user to perform the red component extraction operation.
   * @return A RedComponent command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the red component
   *                                extraction operation.
   */
  public static ImageProcessorCommand apply(String[] args) throws InputMismatchException {
    if (!(args.length == Command.RED_COMPONENT.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.RED_COMPONENT));
    }
    return new RedComponent(args);
  }

  /**
   * Executes the RedComponent command by extracting the red component from the
   * input image and saving the result to the destination image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the red component extraction command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.redComponent(args);
  }
}
