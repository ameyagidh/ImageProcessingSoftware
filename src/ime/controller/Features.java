package ime.controller;

import java.io.IOException;

import ime.controller.enums.Command;

/**
 * The {@code Features} interface defines a set of image processing and manipulation operations.
 * Implementing classes should provide concrete implementations for each method to perform
 * specific image-related functionalities.
 */
public interface Features {

  /**
   * Loads an image from the specified file path.
   *
   * @param filepath The file path of the image to be loaded.
   * @throws IOException If an I/O error occurs during the image loading process.
   */
  void load(String filepath) throws IOException;

  /**
   * Saves the current image to the specified file path.
   *
   * @param filepath The file path where the image will be saved.
   * @throws IOException If an I/O error occurs during the image saving process.
   */
  void save(String filepath) throws IOException;

  /**
   * Converts the input image to the red component.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void redComponent() throws IOException;

  /**
   * Converts the input image to the blue component.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void blueComponent() throws IOException;

  /**
   * Converts the input image to the green component.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void greenComponent() throws IOException;

  /**
   * Converts the input image to greyscale using the luma component.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void lumaGreyscale() throws IOException;

  /**
   * Converts the input image to greyscale using the value component.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void valueGreyscale() throws IOException;

  /**
   * Converts the input image to greyscale using the intensity component.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void intensityGreyscale() throws IOException;

  /**
   * Applies a blur effect to the image.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void blur() throws IOException;

  /**
   * Applies a sharpening effect to the image.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void sharpen() throws IOException;

  /**
   * Applies a sepia tone effect to the image.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void sepia() throws IOException;

  /**
   * Adjusts the brightness of the image.
   *
   * @param scale The scale factor for brightness adjustment.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void brighten(int scale) throws IOException;

  /**
   * Flips the image vertically.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void verticalFlip() throws IOException;

  /**
   * Flips the image horizontally.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void horizontalFlip() throws IOException;

  /**
   * Performs color correction on the image.
   *
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void colorCorrect() throws IOException;

  /**
   * Adjusts levels (brightness, midtones, and white balance) of the image.
   *
   * @param b The brightness adjustment level.
   * @param m The midtones adjustment level.
   * @param w The white balance adjustment level.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void levelAdjust(int b, int m, int w) throws IOException;

  /**
   * Compresses the image by the specified percentage.
   *
   * @param percentage The percentage by which to compress the image.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void compress(double percentage) throws IOException;

  /**
   * Splits the RGB channels of the image and saves them as separate files.
   *
   * @param redFilePath   The file path for the red channel image.
   * @param greenFilePath The file path for the green channel image.
   * @param blueFilePath  The file path for the blue channel image.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath) throws IOException;

  /**
   * Combines separate RGB channel images into a single color image.
   *
   * @param redImageFile   The file path for the red channel image.
   * @param greenImageFile The file path for the green channel image.
   * @param blueImageFile  The file path for the blue channel image.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile)
          throws IOException;

  /**
   * Performs a specific image processing operation based on the provided command and arguments.
   *
   * @param command The command specifying the operation to be performed.
   * @param args    Additional arguments for the operation.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void split(Command command, String[] args) throws IOException;

  /**
   * Reloads the original image, discarding any modifications.
   *
   * @throws IOException If an I/O error occurs during the image reloading process.
   */
  void reloadImage() throws IOException;

  /**
   * Exits the program.
   */
  void exitProgram();
}
