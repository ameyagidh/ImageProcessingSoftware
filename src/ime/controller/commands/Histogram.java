package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The Histogram class represents an image manipulation command that generates a histogram for the
 * input image. It implements the ImageProcessorCommand interface and defines methods to construct,
 * apply, and execute histogram generation on an input image.
 */
public class Histogram implements ImageProcessorCommand {
  private final String imgName;
  private final String destImgName;

  /**
   * Constructs a Histogram command with the specified input image name and destination image
   * name.
   *
   * @param imgName     The name of the input image.
   * @param destImgName The name of the destination image where the histogram will be saved.
   */
  public Histogram(String imgName, String destImgName) {
    this.imgName = imgName;
    this.destImgName = destImgName;
  }

  /**
   * Creates and returns a Histogram command based on the input provided through an array of
   * arguments. Throws an InputMismatchException if the number of arguments is not valid for the
   * histogram generation operation.
   *
   * @param args Arguments provided by the user to perform the histogram generation operation.
   * @return A Histogram command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the histogram
   *                                generation operation.
   */
  public static ImageProcessorCommand apply(String[] args) throws InputMismatchException {
    if (args.length != Command.HISTOGRAM.requiredArgs()) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.HISTOGRAM));
    }
    String imgName = args[0];
    String destImgName = args[1];

    return new Histogram(imgName, destImgName);
  }

  /**
   * Executes the Histogram command by generating a histogram for the
   * input image and saving the result to the destination image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the histogram generation command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.histogram(imgName, destImgName);
  }
}
