package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The ColorCorrect class represents an image manipulation command that performs color correction.
 * It implements the ImageProcessorCommand interface and defines methods to construct, apply, and
 * execute color correction on an input image.
 */
public class ColorCorrect implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a ColorCorrect command with the specified input image name and destination image
   * name.
   *
   * @param args The arguments to perform the specified color correction operation.
   */
  public ColorCorrect(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a ColorCorrect command based on the input provided through an array of
   * arguments.
   * Throws an InputMismatchException if the number of arguments is not valid for the color
   * correction operation.
   *
   * @param args Arguments provided by the user to perform the color correction operation.
   * @return A ColorCorrect command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the color correction
   *                                operation.
   */
  public static ImageProcessorCommand apply(String[] args) throws InputMismatchException {
    if (!(args.length == Command.COLOR_CORRECT.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.COLOR_CORRECT));
    }
    return new ColorCorrect(args);
  }

  /**
   * Executes the color correction command by applying the color correction operation
   * on the input image and saving the result to the destination image using the provided
   * ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the color correction command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.colorCorrect(args);
  }
}
