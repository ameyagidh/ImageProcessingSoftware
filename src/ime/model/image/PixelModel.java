package ime.model.image;

/**
 * Represents a model for RGB pixel values.
 * This interface provides methods to retrieve individual RGB color components of a pixel.
 * Implementing classes should define how these RGB values are stored and retrieved.
 */
public interface PixelModel {

  /**
   * Retrieves the red component value of the pixel.
   *
   * @return An integer value between 0 and 255 representing the red component of the pixel.
   */
  int getR();

  /**
   * Retrieves the green component value of the pixel.
   *
   * @return An integer value between 0 and 255 representing the green component of the pixel.
   */
  int getG();

  /**
   * Retrieves the blue component value of the pixel.
   *
   * @return An integer value between 0 and 255 representing the blue component of the pixel.
   */
  int getB();

  /**
   * Sets the red component of the RGB color.
   *
   * @param value The new value for the red component (0 to 255).
   *              Values outside this range may be clamped or produce unexpected results.
   */
  void setRed(int value);

  /**
   * Sets the green component of the RGB color.
   *
   * @param value The new value for the green component (0 to 255).
   *              Values outside this range may be clamped or produce unexpected results.
   */
  void setGreen(int value);

  /**
   * Sets the blue component of the RGB color.
   *
   * @param value The new value for the blue component (0 to 255).
   *              Values outside this range may be clamped or produce unexpected results.
   */
  void setBlue(int value);


}
