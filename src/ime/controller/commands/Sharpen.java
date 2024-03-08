package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The Sharpen class represents an image manipulation command that applies a sharpening filter to
 * an input image. It implements the ImageProcessorCommand interface and defines methods to
 * construct, apply, and execute the sharpening filter.
 */
public class Sharpen implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a Sharpen command with the specified input image name and destination image name.
   *
   * @param args The arguments to perform the sharpening filter on the input image.
   */
  public Sharpen(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a Sharpen command based on the input provided through an array of
   * arguments. Throws an InputMismatchException if the number of arguments is not valid for the
   * sharpening filter operation.
   *
   * @param args Arguments provided by the user to perform the sharpening filter operation.
   * @return A Sharpen command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the sharpening
   *                                filter operation.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (!(args.length == Command.SHARPEN.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.SHARPEN));
    }

    return new Sharpen(args);
  }

  /**
   * Executes the Sharpen command by applying a sharpening filter to the input image and saving
   * the result to the specified destination image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the sharpening filter command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.sharpen(args);
  }
}
