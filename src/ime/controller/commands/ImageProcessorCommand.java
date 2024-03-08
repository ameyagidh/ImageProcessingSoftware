package ime.controller.commands;

import java.io.IOException;

import ime.model.ExtendedImageProcessor;

/**
 * The ImageProcessorCommand interface defines a contract for classes that represent
 * specific image manipulation commands. Classes implementing this interface should
 * provide an implementation for the process() method, which takes an ImageProcessor
 * object as a parameter and applies the specific image manipulation command to it.
 */
public interface ImageProcessorCommand {

  /**
   * Apply the image manipulation command to the given ImageProcessor object.
   *
   * @param p The ImageProcessor on which the command should be applied.
   */
  void process(ExtendedImageProcessor p) throws IOException;
}
