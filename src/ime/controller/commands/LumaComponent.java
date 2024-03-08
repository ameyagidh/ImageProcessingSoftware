package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The LumaComponent class represents an image manipulation command that converts an input
 * image to greyscale while preserving the luma component. It implements the ImageProcessorCommand
 * interface and defines methods to construct, apply, and execute the conversion to luma greyscale
 * on an input image.
 */
public class LumaComponent implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a LumaComponent command with the specified input image name and destination
   * image name.
   *
   * @param args The arguments to perform the specified luma component conversion operation.
   */
  public LumaComponent(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a LumaComponent command based on the input provided through an array of
   * arguments. Throws an InputMismatchException if the number of arguments is not valid for the
   * luma component conversion operation.
   *
   * @param args Arguments provided by the user to perform the luma component conversion operation.
   * @return A LumaComponent command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the luma component
   *                                conversion operation.
   */
  public static ImageProcessorCommand apply(String[] args) throws InputMismatchException {
    if (!(args.length == Command.LUMA_COMPONENT.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.LUMA_COMPONENT));
    }
    return new LumaComponent(args);
  }

  /**
   * Executes the LumaComponent command by converting the input image to greyscale while preserving
   * the luma component and saving the result to the destination image using the provided
   * ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the luma component conversion command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.lumaGreyscale(args);
  }
}
