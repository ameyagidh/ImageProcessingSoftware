package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The ValueComponent class represents an image manipulation command that converts an input
 * image to grayscale by extracting the value (brightness) component. It implements the
 * ImageProcessorCommand interface and defines methods to construct, apply, and execute the
 * value component conversion.
 */
public class ValueComponent implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a ValueComponent command with the specified input image name and destination
   * image name.
   *
   * @param args The arguments to perform the value component conversion on the input image.
   */
  public ValueComponent(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a ValueComponent command based on the input provided through an array of
   * arguments. Throws an InputMismatchException if the number of arguments is not valid for the
   * value component conversion operation.
   *
   * @param args Arguments provided by the user to perform the value component conversion operation.
   * @return A ValueComponent command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the value
   *                                component conversion operation.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (!(args.length == Command.VALUE_COMPONENT.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.VALUE_COMPONENT));
    }

    return new ValueComponent(args);
  }

  /**
   * Executes the ValueComponent command by converting the input image to grayscale based on its
   * value component. The resulting image retains the brightness information while discarding color
   * information.
   *
   * @param p The ExtendedImageProcessor used to process the value component conversion command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.valueGreyscale(args);
  }
}
