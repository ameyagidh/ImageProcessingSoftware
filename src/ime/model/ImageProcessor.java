package ime.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The ImageProcessor interface represents the core functionality for processing and manipulating
 * images. It defines methods for loading, saving, transforming, and modifying images using
 * various image processing operations.
 */
public interface ImageProcessor {

  /**
   * Loads an RGBImage into the image processor with the given name.
   *
   * @param imgName     The name under which the image is loaded.
   * @param inputStream The inputStream to be loaded.
   */
  void load(String imgName, InputStream inputStream);

  /**
   * Saves the current state of an image with the given name as an ImageModel.
   *
   * @param imgName The name of the image to be saved.
   * @return An outputStream representing the saved image.
   */
  OutputStream save(String imgName) throws IOException;

  /**
   * Transforms an image using to sepia and saves the result as
   * a new image.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void sepia(String[] args);

  /**
   * Converts an input image to the red component.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void redComponent(String[] args);

  /**
   * Converts an input image to the blue component.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void blueComponent(String[] args);

  /**
   * Converts an input image to the green component.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void greenComponent(String[] args);

  /**
   * Converts an input image to greyscale using the luma component.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void lumaGreyscale(String[] args);

  /**
   * Converts an input image to greyscale using the value component.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void valueGreyscale(String[] args);

  /**
   * Converts an input image to greyscale using the intensity component.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void intensityGreyscale(String[] args);


  /**
   * Brightens an image by adding an increment value to each pixel's color components.
   *
   * @param imgName     The name of the input image to be brightened.
   * @param destImgName The name of the destination image where the brightened image will be saved.
   * @param increment   The increment value for brightening the image.
   */
  void brighten(String imgName, String destImgName, int increment);

  /**
   * Applies a blur filter with a specified kernel to the input image, resulting in a modified
   * image.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void blur(String[] args);

  /**
   * Applies a sharpen filter with a specified kernel to the input image, resulting in a modified
   * image.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void sharpen(String[] args);

  /**
   * Performs a horizontal flip on the input image and saves the flipped image as a new image.
   *
   * @param imgName     The name of the input image to be horizontally flipped.
   * @param destImgName The name of the destination image where the flipped image will be saved.
   */
  void horizontalFlip(String imgName, String destImgName);

  /**
   * Performs a vertical flip on the input image and saves the flipped image as a new image.
   *
   * @param imgName     The name of the input image to be vertically flipped.
   * @param destImgName The name of the destination image where the flipped image will be saved.
   */
  void verticalFlip(String imgName, String destImgName);

  /**
   * Splits an RGB image into its red, green, and blue components and saves them as separate images.
   *
   * @param imgName            The name of the input RGB image to be split.
   * @param destRedImgName     The name of the destination image for the red component.
   * @param destGreenImageName The name of the destination image for the green component.
   * @param destBlueImgName    The name of the destination image for the blue component.
   */
  void rgbSplit(String imgName, String destRedImgName, String destGreenImageName,
                String destBlueImgName);

  /**
   * Combines red, green, and blue images into a single RGB image and saves it as a new image.
   *
   * @param redImgName     The name of the red image to combine.
   * @param greenImageName The name of the green image to combine.
   * @param blueImgName    The name of the blue image to combine.
   * @param destImgName    The name of the destination RGB image where the combined image will
   *                       be saved.
   */
  void rgbCombine(String redImgName, String greenImageName, String blueImgName, String destImgName);

}
