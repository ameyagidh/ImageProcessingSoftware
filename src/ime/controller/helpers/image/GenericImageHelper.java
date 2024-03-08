package ime.controller.helpers.image;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * The GenericImageHelper class provides utility methods for reading and saving image files
 * in a generic format using the Java AWT and BufferedImage libraries. It can read and save
 * images in various formats such as JPG and PNG.
 */
public class GenericImageHelper implements ImageHelper {

  /**
   * Reads an image file in a generic format (e.g., JPG or PNG) and returns an RGBImage
   * representation of the image.
   *
   * @param filepath The path to the image file to be read.
   * @return A inputStream object representing the image.
   * @throws IOException If an I/O error occurs during the reading process or if the file format
   *         is not supported.
   */
  @Override
  public InputStream readImage(String filepath) throws IOException {
    try {
      File file = new File(filepath);

      // Load the image directly from the file
      BufferedImage image = ImageIO.read(file);

      int height = image.getHeight();
      int width = image.getWidth();
      int max = 0;

      StringBuilder imageData = new StringBuilder();
      imageData.append(width).append(" ").append(height).append(System.lineSeparator());

      // Iterate through each pixel of the image
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Color rgb = new Color(image.getRGB(j, i));

          int red = rgb.getRed();
          int green = rgb.getGreen();
          int blue = rgb.getBlue();

          // Update the maximum RGB value
          max = Math.max(max, Math.max(red, Math.max(green, blue)));

          imageData.append(red).append(System.lineSeparator())
                  .append(green).append(System.lineSeparator())
                  .append(blue).append(System.lineSeparator());
        }
      }

      // Append max value after the image dimensions
      imageData.insert(imageData.indexOf(System.lineSeparator()) + 1,
              max + System.lineSeparator());

      return new ByteArrayInputStream(imageData.toString().getBytes());
    } catch (IOException e) {
      throw new IOException("Invalid file path", e);
    }

  }

  /**
   * Saves an ImageModel as an image file in a generic format (e.g., JPG or PNG) at the
   * specified file path.
   *
   * @param outputStream The outputStream to be saved as an image.
   * @param filepath     The path where the image will be saved.
   * @throws IOException If an I/O error occurs during the saving process or if the file
   *                     format is not supported.
   */
  @Override
  public void saveImage(OutputStream outputStream, String filepath) throws IOException {
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
    String[] parts = filepath.split("\\.");
    String fileType = parts[parts.length - 1];
    try {
      File output = new File(filepath);
      ImageIO.write(image, fileType, output);
    } catch (IOException e) {
      throw new IOException("Invalid path! please provide the valid path.");
    }

  }

}
