package ime.utils;

import java.util.InputMismatchException;

/**
 * The ImageProcessorUtil class provides utility methods and constants for image processing
 * operations.
 */
public class ImageProcessorUtil {

  /**
   * The Sepia transformation matrix for applying a sepia filter to an image.
   */
  public static final double[][] SEPIA_TRANSFORMER = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  };

  /**
   * The sharpening convolution kernel for enhancing image details.
   */
  public static final double[][] SHARPEN_KERNEL = new double[][]{
          {-0.125, -0.125, -0.125, -0.125, -0.125,},
          {-0.125, 0.25, 0.25, 0.25, -0.125,},
          {-0.125, 0.25, 1.00, 0.25, -0.125,},
          {-0.125, 0.25, 0.25, 0.25, -0.125,},
          {-0.125, -0.125, -0.125, -0.125, -0.125,}};

  /**
   * A 3x3 kernel used for blurring an image to reduce noise or smooth details.
   */
  public static final double[][] BLUR_KERNEL = new double[][]{
          {0.0625, 0.125, 0.0625},
          {0.125, 0.25, 0.125},
          {0.0625, 0.125, 0.0625}};

  /**
   * To get the width percentage for the split operation.
   * @param percentage width percentage to split of string type.
   * @return converted percentage in the float.
   */
  public static float getWidthPercentage(String percentage) {
    try {
      float widthPercentage = Float.parseFloat(percentage);

      if (widthPercentage < 0 || widthPercentage > 100) {
        throw new InputMismatchException();
      }
      return widthPercentage;
    } catch (InputMismatchException ex) {
      throw new InputMismatchException("Invalid value of width percentage. " +
              "it should be between 0 to 100");
    }
  }
}
