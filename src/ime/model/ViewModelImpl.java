package ime.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Implementation of the {@code ViewModel} interface, responsible for managing image processing
 * and providing access to the processed image.
 *
 * <p>The class uses an {@code ExtendedImageProcessor} for image processing operations.
 * It processes an image using the {@code processImage} method, allowing subsequent access to
 * the processed image using the {@code getImage} method.</p>
 *
 * <p>The processed image is represented as an instance of the {@code Image} interface.</p>
 *
 * <p>This class also includes an internal check, {@code isImageReady}, to ensure that other
 * methods are called only after the image has been processed using the {@code processImage}
 * method. If the image is not ready, an {@code IllegalStateException} is thrown, indicating that
 * the {@code processImage} method should be called first.</p>
 *
 * <p>Note: The image processing is performed by the underlying {@code ExtendedImageProcessor}
 * implementation, and this class is designed to provide a high-level interface for interacting
 * with the processed image.</p>
 */
public class ViewModelImpl implements ViewModel {

  private final ExtendedImageProcessor processor;
  private Image image;

  private boolean imageReady;

  /**
   * Constructs a {@code ViewModelImpl} with the specified {@code ExtendedImageProcessor}.
   * The constructed object manages image processing and provides access to the processed image.
   * The initial state is set to indicate that the processed image is not yet ready.
   *
   * @param processor The {@code ExtendedImageProcessor} used for image processing operations.
   */
  public ViewModelImpl(ExtendedImageProcessor processor) {
    this.processor = processor;
    this.imageReady = false;
  }

  /**
   * Processes the specified image using the underlying {@code ExtendedImageProcessor}.
   * After processing, the image becomes accessible through the {@code getImage} method.
   *
   * @param imageName The name of the image to be processed.
   * @throws IOException If an I/O error occurs during the image processing.
   */
  @Override
  public void processImage(String imageName) throws IOException {
    OutputStream outputStream = this.processor.save(imageName);

    String data = outputStream.toString();
    Scanner sc = new Scanner(data);

    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    WritableRaster raster = image.getRaster();

    int[] pixel = new int[3]; // RGB components
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        pixel[0] = sc.nextInt();   // red component
        pixel[1] = sc.nextInt();   // green component
        pixel[2] = sc.nextInt();   // blue component
        // setPixels
        raster.setPixel(y, x, pixel);
      }
    }
    this.image = image;
    this.imageReady = true;
  }

  /**
   * Checks if the image processing is complete and the processed image is ready for access.
   * This method is typically used internally to ensure that other methods are called only
   * after the image has been processed using the {@code processImage} method.
   *
   * @throws IllegalStateException If the image is not ready, indicating that the
   *                               {@code processImage} method should be called first.
   */
  private void isImageReady() {
    if (!this.imageReady) {
      throw new IllegalStateException("Call the processImage method to process the image " +
              "before accessing other methods.");
    }
  }


  /**
   * Retrieves the processed image.
   *
   * @return The processed image as an instance of the {@code Image} interface.
   * @throws IllegalStateException If the image is not ready, indicating that the
   *                               {@code processImage} method should be called first.
   */
  @Override
  public Image getImage() {
    this.isImageReady();
    return this.image;
  }

}
