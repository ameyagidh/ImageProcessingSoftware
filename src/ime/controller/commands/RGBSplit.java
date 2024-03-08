package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The RGBSplit class represents an image manipulation command that splits an input RGB image
 * into its three color channels: red, green, and blue, and saves them as separate images.
 * It utilizes the 'rgbSplit' operation from an ImageProcessor.
 */
public class RGBSplit implements ImageProcessorCommand {

  private final String imgName;
  private final String destRedImg;
  private final String destGreenImg;
  private final String destBlueImg;

  /**
   * Constructs an RGBSplit command with the specified input image name and destination image
   * names for the red, green, and blue channels.
   *
   * @param imgName      The name of the input RGB image to be split.
   * @param destRedImg   The name of the destination image for the red channel.
   * @param destGreenImg The name of the destination image for the green channel.
   * @param destBlueImg  The name of the destination image for the blue channel.
   */
  public RGBSplit(String imgName, String destRedImg, String destGreenImg, String destBlueImg) {
    this.imgName = imgName;
    this.destRedImg = destRedImg;
    this.destGreenImg = destGreenImg;
    this.destBlueImg = destBlueImg;
  }

  /**
   * Creates and returns an RGBSplit command based on the input provided through a Scanner.
   *
   * @param args The args used to read the input parameters for the command.
   * @return An RGBSplit command with the specified input and destination image names for
   *         the RGB channels.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (args.length != Command.RGB_SPLIT.requiredArgs()) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.RGB_SPLIT));
    }
    String imgName = args[0];
    String destRedImg = args[1];
    String destGreenImg = args[2];
    String destBlueImg = args[3];

    return new RGBSplit(imgName, destRedImg, destGreenImg, destBlueImg);
  }

  /**
   * Executes the RGBSplit command by splitting the input RGB image into its red, green, and
   * blue channels and saving them as separate images with the specified destination names.
   *
   * @param p The ImageProcessor used to process the command.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.rgbSplit(imgName, destRedImg, destGreenImg, destBlueImg);
  }
}
