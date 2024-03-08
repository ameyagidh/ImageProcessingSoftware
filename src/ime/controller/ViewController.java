package ime.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.stream.Stream;

import ime.controller.commands.BlueComponent;
import ime.controller.commands.Blur;
import ime.controller.commands.Brighten;
import ime.controller.commands.ColorCorrect;
import ime.controller.commands.Compress;
import ime.controller.commands.GreenComponent;
import ime.controller.commands.Histogram;
import ime.controller.commands.HorizontalFlip;
import ime.controller.commands.ImageProcessorCommand;
import ime.controller.commands.IntensityComponent;
import ime.controller.commands.LevelAdjust;
import ime.controller.commands.Load;
import ime.controller.commands.LumaComponent;
import ime.controller.commands.RGBCombine;
import ime.controller.commands.RGBSplit;
import ime.controller.commands.RedComponent;
import ime.controller.commands.Save;
import ime.controller.commands.Sepia;
import ime.controller.commands.Sharpen;
import ime.controller.commands.ValueComponent;
import ime.controller.commands.VerticalFlip;
import ime.controller.enums.Command;
import ime.model.ExtendedImageProcessor;
import ime.view.IView;

/**
 * Controller class responsible for managing image processing features and coordinating
 * interactions between the user interface (view) and the
 * underlying {@code ExtendedImageProcessor}.
 *
 * <p>The class implements the {@code Features} interface, providing methods for loading images
 * and displaying results. It utilizes an {@code ExtendedImageProcessor} for image processing
 * operations and updates an associated view accordingly.</p>
 *
 * <p>Internally, the controller maintains information about the current image, split view,
 * and histogram to facilitate feature updates and view refreshing.</p>
 */
public class ViewController implements Features {

  private final ExtendedImageProcessor processor;
  private final String currentImage;
  private final String splitView;
  private final String histogram;
  private final IView view;

  /**
   * Constructs a {@code ViewController} with the specified {@code ExtendedImageProcessor} and view.
   * Initializes information about the current image, split view, and histogram.
   *
   * @param processor The {@code ExtendedImageProcessor} used for image processing operations.
   * @param view      The associated view for displaying processed images and features.
   */
  public ViewController(ExtendedImageProcessor processor, IView view) {
    this.processor = processor;
    this.view = view;
    view.addFeatures(this);
    this.currentImage = "current-image";
    this.splitView = "split-image";
    this.histogram = "histogram";
  }

  /**
   * Refreshes the view by updating the current image and histogram display.
   *
   * @throws IOException If an I/O error occurs during the view refresh.
   */
  private void refreshView() throws IOException {
    view.refreshView(currentImage, histogram);
  }

  /**
   * Processes the given command using the underlying {@code ExtendedImageProcessor},
   * updates the histogram, and refreshes the view.
   *
   * @param command The image processing command to be executed.
   * @throws IOException If an I/O error occurs during command processing or view refresh.
   */
  private void processCommand(ImageProcessorCommand command) throws IOException {
    try {
      command.process(processor);
      ImageProcessorCommand histogramCmd = new Histogram(currentImage, histogram);
      histogramCmd.process(processor);
      refreshView();
    } catch (IllegalArgumentException | InputMismatchException e) {
      view.showErrorMessage(e.getMessage());
    }
  }

  /**
   * Loads an image from the specified filepath and assigns it the given imageName.
   *
   * @param filepath  The filepath of the image to be loaded.
   * @param imageName The name to be assigned to the loaded image.
   * @throws IOException If an I/O error occurs during image loading or view refresh.
   */
  private void load(String filepath, String imageName) throws IOException {
    processCommand(new Load(filepath, imageName));
  }

  @Override
  public void load(String filepath) throws IOException {
    load(filepath, currentImage);
  }

  /**
   * Saves the current image to the specified filepath with the given imageName.
   *
   * @param filepath  The filepath where the image will be saved.
   * @param imageName The name to be assigned to the saved image.
   * @throws IOException If an I/O error occurs during image saving.
   */
  private void save(String filepath, String imageName) throws IOException {
    ImageProcessorCommand save = new Save(filepath, imageName);
    save.process(processor);
  }

  @Override
  public void save(String filepath) throws IOException {
    this.save(filepath, currentImage);
  }

  @Override
  public void brighten(int scale) throws IOException {
    processCommand(new Brighten(scale, currentImage, currentImage));
  }

  @Override
  public void verticalFlip() throws IOException {
    processCommand(new VerticalFlip(currentImage, currentImage));
  }

  @Override
  public void horizontalFlip() throws IOException {
    processCommand(new HorizontalFlip(currentImage, currentImage));
  }

  @Override
  public void colorCorrect() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new ColorCorrect(args));
  }

  @Override
  public void levelAdjust(int b, int m, int w) throws IOException {
    String[] args = {String.valueOf(b), String.valueOf(m), String.valueOf(w),
            currentImage, currentImage};
    processCommand(new LevelAdjust(args));
  }

  @Override
  public void compress(double percentage) throws IOException {
    processCommand(new Compress(percentage, currentImage, currentImage));
  }

  @Override
  public void redComponent() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new RedComponent(args));
  }

  @Override
  public void blueComponent() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new BlueComponent(args));
  }

  @Override
  public void greenComponent() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new GreenComponent(args));
  }

  @Override
  public void lumaGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new LumaComponent(args));
  }

  @Override
  public void valueGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new ValueComponent(args));
  }

  @Override
  public void intensityGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new IntensityComponent(args));
  }

  @Override
  public void blur() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new Blur(args));
  }

  @Override
  public void sharpen() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new Sharpen(args));
  }

  @Override
  public void sepia() throws IOException {
    String[] args = {currentImage, currentImage};
    processCommand(new Sepia(args));
  }

  /**
   * Internal method for loading an image from the specified filepath and processing it
   * using the underlying {@code ExtendedImageProcessor}.
   *
   * @param filepath  The filepath of the image to be loaded.
   * @param imageName The name to be assigned to the loaded image.
   * @throws IOException If an I/O error occurs during image loading or processing.
   */
  private void loadInternally(String filepath, String imageName) throws IOException {
    new Load(filepath, imageName).process(processor);
  }

  @Override
  public void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile)
          throws IOException {
    String red = "red-" + currentImage;
    String green = "green-" + currentImage;
    String blue = "blue-" + currentImage;

    loadInternally(redImageFile, red);
    loadInternally(greenImageFile, green);
    loadInternally(blueImageFile, blue);

    processCommand(new RGBCombine(currentImage, red, green, blue));
  }

  @Override
  public void split(Command command, String[] splitArgs) throws IOException {
    String[] basicArgs = {currentImage, splitView};
    String[] args = Stream.of(basicArgs, splitArgs).flatMap(Stream::of)
            .toArray(String[]::new);
    ImageProcessorCommand cmd;
    switch (command) {
      case BLUR:
        cmd = new Blur(args);
        break;
      case SHARPEN:
        cmd = new Sharpen(args);
        break;
      case SEPIA:
        cmd = new Sepia(args);
        break;
      case LUMA_COMPONENT:
        cmd = new LumaComponent(args);
        break;
      case INTENSITY_COMPONENT:
        cmd = new IntensityComponent(args);
        break;
      case COLOR_CORRECT:
        cmd = new ColorCorrect(args);
        break;
      case VALUE_COMPONENT:
        cmd = new ValueComponent(args);
        break;
      case LEVEL_ADJUST:
        args = new String[]{splitArgs[0], splitArgs[1], splitArgs[2], currentImage, splitView,
                splitArgs[3], splitArgs[4]};
        cmd = new LevelAdjust(args);
        break;
      default:
        throw new IllegalArgumentException("Invalid Split view operation");
    }
    cmd.process(processor);
    view.splitView(splitView);
  }

  @Override
  public void reloadImage() throws IOException {
    this.refreshView();
  }

  @Override
  public void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath)
          throws IOException {
    String red = currentImage + "-red";
    String green = currentImage + "-green";
    String blue = currentImage + "-blue";

    // Splitting the image into RGB images.
    ImageProcessorCommand rgbSplit = new RGBSplit(currentImage, red, green, blue);
    rgbSplit.process(processor);

    // Save the files.
    save(redFilePath, red);
    save(greenFilePath, green);
    save(blueFilePath, blue);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

}
