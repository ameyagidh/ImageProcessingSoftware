package ime.controller.helpers.image;

/**
 * Represents a factory for creating {@link ImageHelper} instances based on the provided file path.
 *
 * <p>This interface provides a method to obtain an appropriate {@code ImageHelper} instance
 * suitable for processing or manipulating images based on the specified file path.
 * Implementations should determine the correct {@code ImageHelper} to return based on
 * the type, format to the image file path provided.</p>
 */
public interface ImageHelperFactory {

  /**
   * Returns an {@link ImageHelper} instance suitable for the given file path.
   *
   * @param filepath the path of the image file for which an {@code ImageHelper} is needed.
   * @return an {@code ImageHelper} instance appropriate for the specified image file path.
   */
  ImageHelper getImageHelper(String filepath);
}
