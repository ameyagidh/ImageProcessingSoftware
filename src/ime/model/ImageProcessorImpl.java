package ime.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import ime.model.image.ImageModel;
import ime.model.image.PixelModel;
import ime.model.image.RGBImage;
import ime.model.image.RGBPixel;
import ime.utils.ImageProcessorUtil;

/**
 * The ImageProcessorImpl class implements the ImageProcessor interface, providing the core
 * functionality for processing and manipulating images. It manages a collection of images and
 * offers methods for loading, saving, transforming, and modifying these images.
 */
public class ImageProcessorImpl implements ImageProcessor {

  private final Map<String, ImageModel> images;

  /**
   * Constructs a new ImageProcessorImpl instance with an empty map to store images.
   */
  public ImageProcessorImpl() {
    this.images = new HashMap<>();
  }

  @Override
  public void load(String imgName, InputStream inputStream) {
    Scanner sc = new Scanner(inputStream);
    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();

    if (height < 0 || width < 0 || max < 0) {
      throw new IllegalArgumentException("Invalid file");
    }

    RGBPixel[][] pixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        if (r < 0 || g < 0 || b < 0) {
          throw new IllegalArgumentException("Invalid file.");
        }
        pixels[i][j] = new RGBPixel(r, g, b);

      }
    }
    this.putImage(imgName, new RGBImage(height, width, pixels));
  }

  @Override
  public OutputStream save(String imgName) throws IOException {
    ImageModel imageModel = this.getImage(imgName);
    StringBuilder sb = new StringBuilder();
    int width = imageModel.getWidth();
    int height = imageModel.getHeight();
    int max = imageModel.getMaxValue();
    sb.append(width).append(" ").append(height).append(System.lineSeparator());
    sb.append(max).append(System.lineSeparator());
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sb.append(imageModel.getPixelValues(i, j).getR()).append(System.lineSeparator());
        sb.append(imageModel.getPixelValues(i, j).getG()).append(System.lineSeparator());
        sb.append(imageModel.getPixelValues(i, j).getB()).append(System.lineSeparator());
      }
    }
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    outputStream.write(sb.toString().getBytes());
    return outputStream;
  }

  @Override
  public void sepia(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    double[][] transformer = ImageProcessorUtil.SEPIA_TRANSFORMER;
    Function<PixelModel, PixelModel> transformerFunc = pixel -> {
      int r = pixel.getR();
      int g = pixel.getG();
      int b = pixel.getB();

      int transformedR = applyTransformation(transformer[0], r, g, b);
      int transformedG = applyTransformation(transformer[1], r, g, b);
      int transformedB = applyTransformation(transformer[2], r, g, b);

      return new RGBPixel(transformedR, transformedG, transformedB);
    };

    ImageModel currentImage = this.getImage(imgName);
    ImageModel filteredImage = currentImage.applyTransform(transformerFunc);

    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Sepia.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[3]);
      filteredImage = this.split(currentImage, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);
  }

  @Override
  public void redComponent(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    Function<PixelModel, PixelModel> transformerFunc = p -> new RGBPixel(p.getR(), 0, 0);
    this.putImage(destImgName, this.getImage(imgName).applyTransform(transformerFunc));
  }

  @Override
  public void blueComponent(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    Function<PixelModel, PixelModel> blueTransform = p -> new RGBPixel(0, 0, p.getB());
    this.putImage(destImgName, this.getImage(imgName).applyTransform(blueTransform));
  }

  @Override
  public void greenComponent(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    Function<PixelModel, PixelModel> greenTransform = p -> new RGBPixel(0, p.getG(), 0);
    this.putImage(destImgName, this.getImage(imgName).applyTransform(greenTransform));
  }

  @Override
  public void lumaGreyscale(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    Function<PixelModel, PixelModel> lumaTransform = p -> {
      int weightedSumPixelVal = (int) (0.2126 * p.getR() + 0.7152 * p.getG() + 0.0722 * p.getB());
      return new RGBPixel(weightedSumPixelVal, weightedSumPixelVal, weightedSumPixelVal);
    };

    ImageModel currentImage = this.getImage(imgName);
    ImageModel filteredImage = currentImage.applyTransform(lumaTransform);

    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Luma.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[3]);
      filteredImage = this.split(currentImage, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);
  }

  @Override
  public void valueGreyscale(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    Function<PixelModel, PixelModel> valueTransform = p -> {
      int maxPixelVal = Math.max(p.getR(), Math.max(p.getB(), p.getG()));
      return new RGBPixel(maxPixelVal, maxPixelVal, maxPixelVal);
    };

    ImageModel currentImage = this.getImage(imgName);
    ImageModel filteredImage = currentImage.applyTransform(valueTransform);

    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Value.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[3]);
      filteredImage = this.split(currentImage, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);
  }

  @Override
  public void intensityGreyscale(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    Function<PixelModel, PixelModel> intensityTransform = p -> {
      int avgPixelVal = (p.getR() + p.getG() + p.getB()) / 3;
      return new RGBPixel(avgPixelVal, avgPixelVal, avgPixelVal);
    };

    ImageModel currentImage = this.getImage(imgName);
    ImageModel filteredImage = currentImage.applyTransform(intensityTransform);

    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for intensity.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[3]);
      filteredImage = this.split(currentImage, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);
  }

  @Override
  public void brighten(String imgName, String destImgName, int increment) {
    Function<PixelModel, PixelModel> brightenTransform = p -> {
      int r = p.getR() + increment;
      int g = p.getG() + increment;
      int b = p.getB() + increment;

      return new RGBPixel(r, g, b);
    };
    this.putImage(destImgName, this.getImage(imgName).applyTransform(brightenTransform));
  }

  @Override
  public void sharpen(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    double[][] kernel = ImageProcessorUtil.SHARPEN_KERNEL;
    if (validateKernel(kernel)) {
      throw new IllegalArgumentException("Invalid kernel! Please provide the valid kernel "
              + "with odd dimensions. (ex: 3*3, 5*5)");
    }

    ImageModel currentImage = this.getImage(imgName);
    ImageModel filteredImage = currentImage.filter(kernel);

    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Sharpen.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[3]);
      filteredImage = this.split(currentImage, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);

  }

  @Override
  public void blur(String[] args) {
    String imgName = args[0];
    String destImgName = args[1];
    double[][] kernel = ImageProcessorUtil.BLUR_KERNEL;
    if (validateKernel(kernel)) {
      throw new IllegalArgumentException("Invalid kernel! Please provide the valid kernel "
              + "with odd dimensions. (ex: 3*3, 5*5)");
    }

    ImageModel currentImage = this.getImage(imgName);
    ImageModel filteredImage = currentImage.filter(kernel);

    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Blur.");
      }
      float widthPercentage = ImageProcessorUtil.getWidthPercentage(args[3]);
      filteredImage = this.split(currentImage, filteredImage, widthPercentage);
    }

    this.putImage(destImgName, filteredImage);
  }

  @Override
  public void horizontalFlip(String imgName, String destImgName) {
    this.putImage(destImgName, this.getImage(imgName).horizontalFlip());
  }

  @Override
  public void verticalFlip(String imgName, String destImgName) {
    this.putImage(destImgName, this.getImage(imgName).verticalFlip());
  }

  @Override
  public void rgbSplit(String imgName, String destRedImgName, String destGreenImageName,
                       String destBlueImgName) {
    this.redComponent(new String[]{imgName, destRedImgName});
    this.greenComponent(new String[]{imgName, destGreenImageName});
    this.blueComponent(new String[]{imgName, destBlueImgName});
  }

  @Override
  public void rgbCombine(String redImgName, String greenImageName, String blueImgName,
                         String destImgName) {
    ImageModel red = this.getImage(redImgName);
    ImageModel green = this.getImage(greenImageName);
    ImageModel blue = this.getImage(blueImgName);

    if (red.getHeight() == green.getHeight() && blue.getHeight() == red.getHeight()
            && red.getWidth() == blue.getWidth() && red.getWidth() == green.getWidth()) {

      RGBPixel[][] pixelResults = new RGBPixel[red.getHeight()][red.getWidth()];

      for (int i = 0; i < red.getHeight(); i++) {
        for (int j = 0; j < red.getWidth(); j++) {
          pixelResults[i][j] = new RGBPixel(red.getPixelValues(i, j).getR(),
                  green.getPixelValues(i, j).getG(),
                  blue.getPixelValues(i, j).getB());
        }
      }

      ImageModel combinedImage = new RGBImage(red.getHeight(), red.getWidth(), pixelResults);
      this.putImage(destImgName, combinedImage);
    } else {
      throw new IllegalArgumentException("Images do not have the same dimension.");
    }
  }

  ImageModel getImage(String imgName) throws IllegalArgumentException {
    if (this.images.get(imgName) == null) {
      throw new IllegalArgumentException("Image Not Found: " + imgName);
    }
    return this.images.get(imgName);
  }

  void putImage(String imgName, ImageModel image) throws IllegalArgumentException {
    this.images.put(imgName, image);
  }

  /**
   * Applies a color transformation to the given RGB values using a specified transform row.
   *
   * @param transformRow An array of three doubles representing the transformation coefficients
   *                     for the RGB values.
   * @param r            The red value of the pixel.
   * @param g            The green value of the pixel.
   * @param b            The blue value of the pixel.
   * @return The transformed color value, capped at a maximum of 255.
   */
  private int applyTransformation(double[] transformRow, int r, int g, int b) {
    return (int) Math.min(255, Math.round(transformRow[0] * r + transformRow[1] * g
            + transformRow[2] * b));
  }

  /**
   * This protected method splits two images based on a specified width percentage.
   * It combines pixels from the filtered image up to a certain percentage of the width,
   * and the remaining pixels are taken from the current image.
   *
   * @param current         The original image to be split.
   * @param filtered        The filtered image used for blending up to a certain width percentage.
   * @param widthPercentage The percentage of the width up to which pixels are taken from the
   *                        filtered image.
   * @return An ImageModel representing the result of splitting and combining the two images.
   */
  protected ImageModel split(ImageModel current, ImageModel filtered, float widthPercentage) {
    int percentageWidth = (int) (current.getWidth() * (widthPercentage / 100));
    RGBPixel[][] splitImagePixels = new RGBPixel[current.getHeight()][current.getWidth()];

    for (int i = 0; i < current.getHeight(); i++) {
      for (int j = 0; j < current.getWidth(); j++) {
        if (j <= percentageWidth) {
          splitImagePixels[i][j] = new RGBPixel(filtered.getPixelValues(i, j).getR(),
                  filtered.getPixelValues(i, j).getG(),
                  filtered.getPixelValues(i, j).getB());
        } else {
          splitImagePixels[i][j] = new RGBPixel(current.getPixelValues(i, j).getR(),
                  current.getPixelValues(i, j).getG(),
                  current.getPixelValues(i, j).getB());
        }
      }
    }

    return new RGBImage(current.getHeight(), current.getWidth(), splitImagePixels);
  }

  /**
   * To validate the Kernel for filters.
   * It should be of odd dimension for the operation.
   *
   * @param kernel a 2D matrix representing the kernel that will be used for filtering.
   * @return returns true if the kernel is valid otherwise false.
   */
  private boolean validateKernel(double[][] kernel) {
    int l = kernel.length;
    if (l % 2 == 1) {
      for (double[] doubles : kernel) {
        if (doubles.length != l) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

}
