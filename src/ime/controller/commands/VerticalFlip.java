package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The VerticalFlip class represents an image manipulation command that flips an input
 * image vertically. It implements the ImageProcessorCommand interface and defines methods
 * to construct, apply, and execute the vertical flip operation.
 */
public class VerticalFlip implements ImageProcessorCommand {

  private final String imgName;
  private final String destImgName;

  /**
   * Constructs a VerticalFlip command with the specified input image name and destination
   * image name.
   *
   * @param imgName     The name of the input image that will be vertically flipped.
   * @param destImgName The name of the destination image where the vertically flipped image
   *                    will be saved.
   */
  public VerticalFlip(String imgName, String destImgName) {
    this.imgName = imgName;
    this.destImgName = destImgName;
  }

  /**
   * Creates and returns a VerticalFlip command based on the input provided through an array of
   * arguments. Throws an InputMismatchException if the number of arguments is not valid for the
   * vertical flip operation.
   *
   * @param args Arguments provided by the user to perform the vertical flip operation.
   * @return A VerticalFlip command with the specified input and destination image names.
   * @throws InputMismatchException If the number of arguments is invalid for the vertical flip
   *                                operation.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (args.length != Command.VERTICAL_FLIP.requiredArgs()) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.VERTICAL_FLIP));
    }
    String imgName = args[0];
    String destImgName = args[1];
    return new VerticalFlip(imgName, destImgName);
  }

  /**
   * Executes the VerticalFlip command by flipping the input image vertically and saving the
   * result to the specified destination image.
   *
   * @param p The ExtendedImageProcessor used to process the vertical flip command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.verticalFlip(imgName, destImgName);
  }
}
