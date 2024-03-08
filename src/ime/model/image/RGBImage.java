package ime.model.image;

import java.util.Objects;
import java.util.function.Function;

/**
 * The RGBImage class implements the ImageModel interface and represents an RGB image. It provides
 * methods for image-related operations such as flipping, brightening, filtering, color
 * transformation, and grayscale conversion.
 */
public class RGBImage implements ImageModel {

  private final PixelModel[][] pixels;
  private final int height;
  private final int width;
  private final int maxValue;

  /**
   * Constructs an RGBImage with the specified height, width, and pixel array.
   *
   * @param height The height of the image.
   * @param width  The width of the image.
   * @param pixels The 2D array of RGB pixels.
   * @throws IllegalArgumentException If the pixel array is null or empty.
   */
  public RGBImage(int height, int width, PixelModel[][] pixels) {
    if (pixels == null || pixels.length == 0 || pixels[0].length == 0) {
      throw new IllegalArgumentException("pixels cannot be null or length of the "
              + "pixels must not be zero.");
    }
    this.height = height;
    this.width = width;
    this.pixels = pixels;
    this.maxValue = 255;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Get the maximum pixel value for the image, which is always 255 for RGB images.
   *
   * @return The maximum pixel value (255).
   */
  @Override
  public int getMaxValue() {
    return maxValue;
  }

  @Override
  public PixelModel getPixelValues(int i, int j) {
    try {
      return this.pixels[i][j];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Pixel indices are out of bound!");
    }
  }

  @Override
  public PixelModel[][] getPixels() {
    return this.pixels;
  }

  @Override
  public ImageModel horizontalFlip() {
    PixelModel[][] result = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j] = this.pixels[i][width - 1 - j];
      }
    }
    return new RGBImage(height, width, result);
  }

  @Override
  public ImageModel verticalFlip() {
    RGBPixel[][] result = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(this.pixels[height - 1 - i], 0, result[i], 0, width);
    }

    return new RGBImage(height, width, result);
  }

  @Override
  public ImageModel filter(double[][] kernel) {
    RGBPixel[][] result = new RGBPixel[height][width];
    int kernelSize = kernel.length;
    int kernelLength = kernelSize / 2;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double redValue = 0;
        double greenValue = 0;
        double blueValue = 0;
        PixelModel rgb;

        for (int k = i - kernelLength, x = 0; k <= i + kernelLength; k++, x++) {
          for (int l = j - kernelLength, y = 0; l <= j + kernelLength; l++, y++) {
            if ((k >= 0 && k < height) && (l >= 0 && l < width)) {
              rgb = this.getPixelValues(k, l);
              redValue += rgb.getR() * kernel[x][y];
              greenValue += rgb.getG() * kernel[x][y];
              blueValue += rgb.getB() * kernel[x][y];
            }
          }
        }

        result[i][j] = new RGBPixel((int) Math.round(redValue), (int) Math.round(greenValue),
                (int) Math.round(blueValue));
      }
    }

    return new RGBImage(height, width, result);
  }

  @Override
  public ImageModel applyTransform(Function<PixelModel, PixelModel> transformFunction) {
    int height = this.pixels.length;
    int width = this.pixels[0].length;
    PixelModel[][] result = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j] = transformFunction.apply(this.pixels[i][j]);
      }
    }

    return new RGBImage(result.length, result[0].length, result);
  }


  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof RGBImage)) {
      return false;
    }

    ImageModel other = (RGBImage) o;

    boolean isEqual =
            this.height == other.getHeight() && this.maxValue == other.getMaxValue()
                    && this.width == other.getWidth();


    if (isEqual) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          isEqual = isEqual && this.getPixelValues(i, j).equals(other.getPixelValues(i, j));
        }
      }
    }

    return isEqual;
  }

  @Override
  public int hashCode() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sb.append(this.pixels[i][j].getR()).append(this.pixels[i][j].getG())
                .append(this.pixels[i][j].getB());
      }
    }
    return Objects.hash(this.height, this.width, sb.toString(), this.maxValue);
  }

}

