package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The IntensityComponent class represents an image manipulation command that converts an input
 * image to grayscale while preserving the intensity component. It utilizes the 'greyscale'
 * operation from an ImageProcessor to perform this transformation.
 */
public class IntensityComponent implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs an IntensityComponent command with the specified input image name and destination
   * image name.
   *
   * @param args The arguments to perform the specified intensity component conversion operation.
   */
  public IntensityComponent(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns an IntensityComponent command based on the input provided through an array
   * of arguments. Throws an InputMismatchException if the number of arguments is not valid for
   * the intensity component conversion operation.
   *
   * @param args Arguments provided by the user to perform the intensity component conversion
   *             operation.
   * @return An IntensityComponent command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the intensity
   *                                component conversion operation.
   */
  public static ImageProcessorCommand apply(String[] args) throws InputMismatchException {
    if (!(args.length == Command.INTENSITY_COMPONENT.requiredArgs() || args.length == 4)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.INTENSITY_COMPONENT));
    }

    return new IntensityComponent(args);
  }

  /**
   * Executes the IntensityComponent command by applying the 'greyscale' operation with
   * the 'Component.INTENSITY' option on the input image and saving the result to the destination
   * image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the intensity component conversion command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.intensityGreyscale(args);
  }
}

