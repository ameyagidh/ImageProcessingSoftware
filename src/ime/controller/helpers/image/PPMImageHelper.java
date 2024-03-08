package ime.controller.helpers.image;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import ime.controller.helpers.file.FileHelper;
import ime.controller.helpers.file.FileHelperImpl;

/**
 * The PPMImageHelper class provides utility methods for reading and saving
 * PPM (Portable Pixmap Format) image files.
 */
public class PPMImageHelper implements ImageHelper {

  /**
   * Reads a PPM image file and creates an RGBImage representation of the image.
   *
   * @param filepath The path to the PPM image file to be read.
   * @return A inputStream object representing the image.
   * @throws FileNotFoundException If the specified file is not found.
   */
  @Override
  public InputStream readImage(String filepath) throws IOException {
    try {
      FileHelper fileHelper = new FileHelperImpl();
      String file = fileHelper.readFile(filepath);

      Scanner sc = new Scanner(file);
      String token;

      token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: "
                + "plain RAW file should begin with P3");
      }

      StringBuilder builder = new StringBuilder();
      //read the file line by line, and populate a string. This will throw away any comment lines
      while (sc.hasNext()) {
        String s = sc.next();
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      }

      return new ByteArrayInputStream(builder.toString().getBytes());
    } catch (IOException ioe) {
      throw new IOException("Invalid file path");
    }
  }

  /**
   * Saves an RGBImage as a PPM image file.
   *
   * @param outputStream The OutputStream to be saved.
   * @param filepath     The path to the PPM image file where the image will be saved.
   * @throws IOException If an I/O error occurs during the save operation.
   */
  @Override
  public void saveImage(OutputStream outputStream, String filepath) throws IOException {
    try {
      String data = outputStream.toString();
      Scanner sc = new Scanner(data);

      StringBuilder sb = new StringBuilder();

      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      sb.append("P3").append(System.lineSeparator());

      sb.append(width).append(" ").append(height).append(System.lineSeparator());
      sb.append(maxValue).append(System.lineSeparator());

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          sb.append(sc.nextInt()).append(System.lineSeparator());
          sb.append(sc.nextInt()).append(System.lineSeparator());
          sb.append(sc.nextInt()).append(System.lineSeparator());
        }
      }

      FileWriter myWriter = new FileWriter(filepath);
      myWriter.write(sb.toString());
      myWriter.close();
    } catch (IOException e) {
      throw new IOException("Invalid path! please provide the valid path");
    }

  }

}

