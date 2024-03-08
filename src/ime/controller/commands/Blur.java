package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The Blur class represents an image manipulation command that applies a blur filter to an
 * input image.
 * It utilizes a kernel (a 3x3 matrix) to perform a blur operation on the image, resulting
 * in a smoother, less detailed appearance.
 */
public class Blur implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a Blur command with the specified input image name and destination image name.
   *
   * @param args The args to perform the specified operation.
   */
  public Blur(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a Blur command based on the input provided through a Scanner.
   *
   * @param args args provided by the user to perform the operation.
   * @return A Blur command with the specified input and destination image names.
   */
  public static ImageProcessorCommand apply(String[] args) {

    if (!(args.length == Command.BLUR.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.BLUR));
    }

    return new Blur(args);
  }

  /**
   * Executes the Blur command by applying a blur filter to the input image using a specified
   * kernel and saving the result to the destination image.
   *
   * @param p The ImageProcessor used to process the command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.blur(args);
  }
}
