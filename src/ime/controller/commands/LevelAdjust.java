package ime.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The LevelAdjust class represents an image manipulation command that adjusts the levels of an
 * input image. It implements the ImageProcessorCommand interface and defines methods to construct,
 * apply, and execute level adjustment on an input image.
 */
public class LevelAdjust implements ImageProcessorCommand {

  private final String[] args;

  /**
   * Constructs a LevelAdjust command with the specified input image name and destination
   * image name.
   *
   * @param args The arguments to perform the specified level adjustment operation.
   */
  public LevelAdjust(String[] args) {
    this.args = args;
  }

  /**
   * Creates and returns a LevelAdjust command based on the input provided through an array
   * of arguments.
   *
   * @param args Arguments provided by the user to perform the level adjustment operation.
   * @return A LevelAdjust command with the specified input and destination image names.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (!(args.length == Command.LEVEL_ADJUST.requiredArgs() || args.length == 7)) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.LEVEL_ADJUST));
    }
    return new LevelAdjust(args);
  }

  /**
   * Executes the LevelAdjust command by adjusting the levels of the
   * input image and saving the result to the destination image using the provided ImageProcessor.
   *
   * @param p The ExtendedImageProcessor used to process the level adjustment command.
   * @throws IOException If an I/O error occurs during level adjustment processing.
   */
  @Override
  public void process(ExtendedImageProcessor p) throws IOException {
    p.levelsAdjust(args);
  }
}

