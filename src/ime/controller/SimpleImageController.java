package ime.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import ime.model.ExtendedImageProcessor;
import ime.model.ExtendedImageProcessorImpl;
import ime.model.ViewModelImpl;
import ime.view.ImageManipulatorView;
import ime.view.IView;

/**
 * The SimpleImageController class serves as a simple entry point for executing
 * image manipulation commands from the command line. It creates an instance of
 * ImageControllerInterface, which is initialized with a standard input reader
 * (InputStreamReader) for user input and the standard output for program output.
 * The controller then executes image manipulation commands by calling the execute()
 * method.
 */
public class SimpleImageController {

  /**
   * The main method creates an ImageControllerInterface instance and executes
   * image manipulation commands using the standard input and output streams.
   *
   * @param args The command-line arguments (not used in this context).
   * @throws IOException If an I/O error occurs during input or output operations.
   */
  public static void main(String[] args) throws IOException {
    ExtendedImageProcessor imageProcessor = new ExtendedImageProcessorImpl();
    ImageControllerInterface controller;
    if (args != null && args.length == 2 && (args[0].equals("-file") || args[0].equals("-f"))) {
      // If the "-file" option is present, create an ImageControllerInterface for script execution.
      Reader in = new StringReader("run " + args[1] + "\nq");
      controller = new ImageController(in, System.out);
      controller.execute(imageProcessor);
    } else if (args != null && args.length == 1 && (args[0].equals("-text")
            || args[0].equals("-t"))) {
      // for interactive command-line input.
      controller = new ImageController(new InputStreamReader(System.in), System.out);
      controller.execute(imageProcessor);
    } else {
      IView view = new ImageManipulatorView("PixelPulse",
              new ViewModelImpl(imageProcessor));
      new ViewController(imageProcessor, view);
    }
  }
}
