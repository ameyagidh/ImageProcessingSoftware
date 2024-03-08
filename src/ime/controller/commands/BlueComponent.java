package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The BlueComponent class represents an image manipulation command that converts an input image
 * to an output image while preserving the blue component.
 */
public class BlueComponent implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a BlueComponent command with the specified input image name and destination
   * image name.
   *
   * @param args The args to perform the specified operation.
   */
  public BlueComponent(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a BlueComponent command based on the input provided through a Scanner.
   *
   * @param args args provided by the user to perform the operation.
   * @return A BlueComponent command with the specified input and destination image names.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (!(args.length == Command.BLUE_COMPONENT.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.BLUE_COMPONENT));
    }

    return new BlueComponent(args);
  }

  /**
   * executes the BlueComponent command by extracting the blue component with
   * from the input image and saving the result to the destination image.
   *
   * @param p The ImageProcessor used to process the command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.blueComponent(args);
  }
}
