package ime.model;

import java.awt.Image;
import java.io.IOException;

/**
 * The {@code ViewModel} interface represents a component in a software application
 * responsible for processing images and providing access to the processed image.
 * Classes implementing this interface should define the behavior for image processing
 * and retrieving the processed image.
 */
public interface ViewModel {

  /**
   * Processes the specified image. Implementing classes should define the logic
   * for image processing based on the provided image name.
   *
   * @param imageName The name of the image to be processed.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  void processImage(String imageName) throws IOException;

  /**
   * Retrieves the processed image. Implementing classes should return the processed
   * image, which may have been modified or enhanced during the image processing.
   *
   * @return The processed image.
   */
  Image getImage();
}

