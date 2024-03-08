package ime.model;

/**
 * The ExtendedImageProcessor interface represents the new functionality for manipulating
 * images. It defines methods for color correct, compress, histogram, and adjusting levels using
 * various image processing operations.
 */
public interface ExtendedImageProcessor extends ImageProcessor {

  /**
   * Performs color correction on the input image, adjusting the colors based on the split
   * percentage.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void colorCorrect(String[] args);

  /**
   * Applies compression to the input image with the specified compression percentage.
   *
   * @param percentage  The compression percentage to be applied (e.g., 10 for 10% compression).
   * @param imgName     The name of the input image.
   * @param destImgName The name of the destination image where the compressed result will be saved.
   */
  void compress(double percentage, String imgName, String destImgName);

  /**
   * Generates a histogram for the input image and saves it to the destination image.
   *
   * @param imgName     The name of the input image.
   * @param destImgName The name of the destination image where the histogram will be saved.
   */
  void histogram(String imgName, String destImgName);

  /**
   * Adjusts the levels of the input image based on the provided black, mid, and white values.
   *
   * @param args The args contains the arguments to perform the sepia operation.
   *             be applied
   */
  void levelsAdjust(String[] args);


}
