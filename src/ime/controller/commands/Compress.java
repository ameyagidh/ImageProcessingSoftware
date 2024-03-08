package ime.controller.commands;

import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The {@code Compress} class represents a command for compressing an image by a specified
 * percentage.
 *
 * <p>This class implements the {@link ImageProcessorCommand} interface, providing a method to
 * process the compression operation on an image.</p>
 */
public class Compress implements ImageProcessorCommand {
  private final double percentage;
  private final String imgName;
  private final String destImgName;

  /**
   * Constructs a {@code Compress} command with the specified compression percentage,
   * input image name, and destination image name.
   *
   * @param percentage  The compression percentage to apply (e.g., 20 for 20% compression).
   * @param imgName     The name of the input image to be compressed.
   * @param destImgName The name of the destination image after compression.
   */
  public Compress(double percentage, String imgName, String destImgName) {
    this.percentage = percentage;
    this.imgName = imgName;
    this.destImgName = destImgName;
  }

  /**
   * A method to create a {@code Compress} command from command line arguments.
   *
   * @param args The command line arguments, including compression percentage, input image name,
   *             and destination image name.
   * @return A new {@code Compress} command based on the provided arguments.
   * @throws InputMismatchException If the number of arguments is incorrect.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (args.length != Command.COMPRESS.requiredArgs()) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.COMPRESS));
    }

    double percentage = 0d;
    try {
      percentage = Double.parseDouble(args[0]);
    } catch (NumberFormatException e) {
      throw new InputMismatchException("Invalid argument for percentage");
    }

    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Compression percentage must be between 0 and 100");
    }
    String imgName = args[1];
    String destImgName = args[2];

    return new Compress(percentage, imgName, destImgName);
  }

  /**
   * Processes the compression operation on an {@link ExtendedImageProcessor}.
   *
   * @param p The {@code ExtendedImageProcessor} on which the compression operation will be applied.
   */
  @Override
  public void process(ExtendedImageProcessor p) {
    p.compress(percentage, imgName, destImgName);
  }

}
