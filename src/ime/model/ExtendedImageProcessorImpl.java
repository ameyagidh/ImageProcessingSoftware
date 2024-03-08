package ime.model;

import java.awt.image.BufferedImage;
import java.util.InputMismatchException;

import ime.model.image.ImageModel;
import ime.model.image.ImageModelV2;
import ime.model.image.RGBImage;
import ime.model.image.RGBImageV2;
import ime.model.image.RGBPixel;
import ime.utils.HistogramGenerator;
import ime.utils.ImageProcessorUtil;

/**
 * Implementation class for the ExtendedImageProcessor interface, extending the base
 * ImageProcessorImpl. It provides additional image processing functionality beyond the
 * standard image processing operations.
 */
public class ExtendedImageProcessorImpl extends ImageProcessorImpl
        implements ExtendedImageProcessor {

  /**
   * Constructs an ExtendedImageProcessorImpl, inheriting from the base ImageProcessorImpl class.
   * This class extends the basic image processing functionality to provide additional features.
   */
  public ExtendedImageProcessorImpl() {
    super();
  }

  @Override
  public void colorCorrect(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    ImageModel sourceImg = this.getImage(imgName);

    ImageModelV2 imageV2 = this.getImageModelV2(sourceImg);
    ImageModel filteredImage = imageV2.colorCorrect();

    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Color correct.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[3]);
      filteredImage = this.split(sourceImg, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);
  }

  @Override
  public void compress(double percentage, String imgName, String destImgName) {
    ImageModel sourceImg = this.getImage(imgName);
    ImageModelV2 imageV2 = this.getImageModelV2(sourceImg);
    this.putImage(destImgName, imageV2.compress(percentage));
  }


  @Override
  public void histogram(String imgName, String destImgName) {
    HistogramGenerator histogramGenerator = new HistogramGenerator();
    ImageModel sourceImg = this.getImage(imgName);
    BufferedImage histogram = histogramGenerator.createHistogram(sourceImg);

    this.putImage(destImgName, this.convertBufferedImageToImageModel(histogram));
  }

  @Override
  public void levelsAdjust(String[] args) {
    int black = 0;
    int mid = 0;
    int white = 0;
    try {
      black = Integer.parseInt(args[0]);
      mid = Integer.parseInt(args[1]);
      white = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
      throw new InputMismatchException("Invalid arguments for black, mid or white.");
    }

    String imgName = args[3];
    String destImgName = args[4];

    if (black < 0 || mid < 0 || white < 0 || white > 255 || black > mid || mid > white) {
      throw new InputMismatchException("b, m and w should be in (0, 255) range.");
    }

    ImageModel currentImage = this.getImage(imgName);
    ImageModelV2 imageV2 = this.getImageModelV2(currentImage);
    ImageModel filteredImage = imageV2.levelsAdjust(black, mid, white);

    if (args.length > 5) {
      String split = args[5];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Levels adjust.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[6]);
      filteredImage = this.split(currentImage, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);
  }

  /**
   * This private method converts a BufferedImage to an ImageModel.
   *
   * @param image The BufferedImage to be converted.
   * @return An ImageModel representing the converted image with RGB pixels.
   */
  private ImageModel convertBufferedImageToImageModel(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    RGBPixel[][] pixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int color = image.getRGB(j, i);
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        pixels[i][j] = new RGBPixel(red, green, blue);
      }
    }

    return new RGBImage(height, width, pixels);
  }

  /**
   * Converts an {@code ImageModel} to {@code ImageModelV2} (specifically, {@code RGBImageV2}).
   *
   * @param image The original {@code ImageModel} to convert.
   * @return A new {@code ImageModelV2} instance with the same dimensions and pixel data.
   */
  public ImageModelV2 getImageModelV2(ImageModel image) {
    return new RGBImageV2(image.getHeight(), image.getWidth(), image.getPixels());
  }

}
