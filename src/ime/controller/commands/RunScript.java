package ime.controller.commands;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.InputMismatchException;

import ime.controller.ImageController;
import ime.controller.ImageControllerInterface;
import ime.controller.enums.Command;
import ime.controller.helpers.file.FileHelper;
import ime.controller.helpers.file.FileHelperImpl;
import ime.model.ExtendedImageProcessor;
import ime.utils.MessageUtil;

/**
 * The RunScript class represents an image manipulation command that executes a sequence of
 * image manipulation commands defined in a script file. It implements the ImageProcessorCommand
 * interface and defines methods to construct, apply, and execute the script execution.
 */
public class RunScript implements ImageProcessorCommand {

  private final String filepath;

  /**
   * Constructs a RunScript command with the specified file path to the script.
   *
   * @param filepath The path to the script file that will be executed.
   */
  public RunScript(String filepath) {
    this.filepath = filepath;
  }

  /**
   * Creates and returns a RunScript command based on the input provided through an array of
   * arguments.
   *
   * @param args Arguments provided by the user to perform the script execution operation.
   * @return A RunScript command with the specified file path to the script.
   */
  public static ImageProcessorCommand apply(String[] args) {
    if (args.length != Command.RUN_SCRIPT.requiredArgs()) {
      throw new InputMismatchException(
              MessageUtil.getInvalidNumberOfArgsMessage(Command.RUN_SCRIPT));
    }
    String filepath = args[0];
    return new RunScript(filepath);
  }

  /**
   * Executes the RunScript command, triggering the execution of a sequence of image manipulation
   * commands defined in the specified script file.
   *
   * @param p The ExtendedImageProcessor used to process the script execution command.
   * @throws IOException If an I/O error occurs during script execution.
   */
  @Override
  public void process(ExtendedImageProcessor p) throws IOException {
    FileHelper fileHelper = new FileHelperImpl();
    String fileContents = fileHelper.readFile(filepath);

    Reader in = new StringReader(fileContents);
    ImageControllerInterface imageController = new ImageController(in, System.out);
    imageController.execute(p);
  }
}
