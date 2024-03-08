package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The HorizontalFlip class represents an image manipulation command that flips an input
 * image horizontally. It applies a horizontal flip operation to the image, reversing the order
 * of pixels along the horizontal axis.
 */
public class HorizontalFlip implements ImageProcessorCommand {

  private final String imgName;
  private final String destImgName;

  /**
   * Constructs a HorizontalFlip command with the specified input image name and destination
   * image name.
   *
   * @param imgName     The name of the input image.
   * @param destImgName The name of the destination image where the result will be saved.
   */
  public HorizontalFlip(String imgName, String destImgName) {
    this.imgName = imgName;
    this.destImgName = destImgName;
  }

  /**
   * Creates and returns a HorizontalFlip command based on the input provided through an array
   * of arguments. Throws an InputMismatchException if the number of arguments is not valid for
   * the horizontal flip operation.
   *
   * @param args Arguments provided by the user to perform the horizontal flip operation.
   * @return A HorizontalFlip command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the horizontal flip
   *                                operation.
   */
  public static ImageProcessorCommand apply(String[] args) throws InputMismatchException {
    if (args.length != Command.HORIZONTAL_FLIP.requiredArgs()) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.HORIZONTAL_FLIP));
    }
    String imgName = args[0];
    String destImgName = args[1];

    return new HorizontalFlip(imgName, destImgName);
  }

  /**
   * Executes the HorizontalFlip command by applying a horizontal flip operation to the
   * input image and saving the result to the destination image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the horizontal flip command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.horizontalFlip(imgName, destImgName);
  }
}

