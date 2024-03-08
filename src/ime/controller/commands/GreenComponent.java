package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The GreenComponent class represents an image manipulation command that extracts the green
 * component from the input image. It implements the ImageProcessorCommand interface and defines
 * methods to construct, apply, and execute the extraction of the green component on an input image.
 */
public class GreenComponent implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a GreenComponent command with the specified input image name and destination
   * image name.
   *
   * @param args The arguments to perform the specified green component extraction operation.
   */
  public GreenComponent(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a GreenComponent command based on the input provided through an array
   * of arguments.
   * Throws an InputMismatchException if the number of arguments is not valid for the green
   * component extraction operation.
   *
   * @param args Arguments provided by the user to perform the green component extraction operation.
   * @return A GreenComponent command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the green component
   *                                extraction operation.
   */
  public static ImageProcessorCommand apply(String[] args) throws InputMismatchException {
    if (!(args.length == Command.GREEN_COMPONENT.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.GREEN_COMPONENT));
    }
    return new GreenComponent(args);
  }

  /**
   * Executes the GreenComponent command by extracting the green component
   * on the input image and saving the result to the destination
   * image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the green component extraction command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.greenComponent(args);
  }
}
