package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The RGBCombine class represents an image manipulation command that combines red, green, and blue
 * component images to create an RGB image. It implements the ImageProcessorCommand interface
 * and defines methods to construct, apply, and execute RGB image combination.
 */
public class RGBCombine implements ImageProcessorCommand {

  private final String destImgName;
  private final String redImg;
  private final String greenImg;
  private final String blueImg;

  /**
   * Constructs an RGBCombine command with the specified names of the destination image and
   * component images.
   *
   * @param destImgName The name of the destination image where the RGB composite will be saved.
   * @param redImg      The name of the red component image.
   * @param greenImg    The name of the green component image.
   * @param blueImg     The name of the blue component image.
   */
  public RGBCombine(String destImgName, String redImg, String greenImg, String blueImg) {
    this.destImgName = destImgName;
    this.redImg = redImg;
    this.greenImg = greenImg;
    this.blueImg = blueImg;
  }

  /**
   * Creates and returns an RGBCombine command based on the input provided through an array of
   * arguments.
   *
   * @param args Arguments provided by the user to perform the RGB image combination operation.
   * @return An RGBCombine command with the specified destination image and component image names.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (args.length != Command.RGB_COMBINE.requiredArgs()) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.RGB_COMBINE));
    }
    String destImgName = args[0];
    String redImg = args[1];
    String greenImg = args[2];
    String blueImg = args[3];

    return new RGBCombine(destImgName, redImg, greenImg, blueImg);
  }

  /**
   * Executes the RGBCombine command by combining the red, green, and blue component images to
   * create an RGB image and saving the result to the destination image using the provided
   * ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the RGB image combination command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.rgbCombine(redImg, greenImg, blueImg, destImgName);
  }
}
