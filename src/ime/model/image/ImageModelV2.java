package ime.model.image;

/**
 * This interface represents an extended version (V2) of an image processing model, building upon
 * the base {@link ImageModel}. It introduces additional methods for advanced image manipulation
 * and enhancement.
 *
 * <p>The methods defined in this interface allow for compression, color correction, and adjustment
 * of levels in an image.</p>
 *
 * <p>Implementing classes should provide concrete implementations for these methods to perform the
 * specified image processing operations.</p>
 *
 */
public interface ImageModelV2 extends ImageModel {

  /**
   * Compresses the image by the specified percentage.
   *
   * @param percentage The percentage by which to compress the image.
   *                   A value of 0.0 means no compression, while 1.0 means 100% compression
   *                   (i.e., a completely compressed image).
   * @return A new instance of {@code ImageModel} representing the compressed image.
   */
  ImageModel compress(double percentage);

  /**
   * Performs color correction on the image.
   *
   * @return A new instance of {@code ImageModel} representing the color-corrected image.
   */
  ImageModel colorCorrect();

  /**
   * Adjusts the levels of the image based on the provided parameters.
   *
   * @param black The value for the black point (shadows).
   * @param mid The value for the midpoint (midtones).
   * @param white The value for the white point (highlights).
   * @return A new instance of {@code ImageModel} representing the image with adjusted levels.
   */
  ImageModel levelsAdjust(int black, int mid, int white);
}
