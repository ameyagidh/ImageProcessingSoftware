package ime.controller.helpers.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The ImageHelper interface defines a contract for classes that provide utility
 * methods for reading and saving image files in various formats. It includes methods
 * for reading images in specific formats and for saving images to specific file paths.
 */
public interface ImageHelper {

  /**
   * Reads an image file in a specific format and returns an RGBImage representation
   * of the image.
   *
   * @param filepath The path to the image file to be read.
   * @return A inputStream object representing the image.
   * @throws IOException If an I/O error occurs during the reading process.
   */
  InputStream readImage(String filepath) throws IOException;

  /**
   * Saves an ImageModel as an image file at the specified file path.
   *
   * @param outputStream The outputStream to be saved as an image.
   * @param filepath     The path where the image will be saved.
   * @throws IOException If an I/O error occurs during the saving process.
   */
  void saveImage(OutputStream outputStream, String filepath) throws IOException;
}
