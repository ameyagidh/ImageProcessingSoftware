package ime.model.image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static ime.utils.HistogramGenerator.getFrequencies;

/**
 * Represents an extended version (V2) of an RGB image with additional image processing
 * capabilities. Extends the base {@link RGBImage} class and implements the {@link ImageModelV2}
 * interface.
 *
 * <p>This class provides a basic implementation of the {@code ImageModelV2} interface,
 * with methods returning {@link ImageModel}. Subclasses should override these methods
 * to provide meaningful implementations for image compression, color correction, and levels
 * adjustment.</p>
 */
public class RGBImageV2 extends RGBImage implements ImageModelV2 {

  /**
   * Constructs an RGBImageV2 with the specified height, width, and pixel array.
   *
   * @param height The height of the image.
   * @param width  The width of the image.
   * @param pixels A 2D array of pixel values representing the image.
   */
  public RGBImageV2(int height, int width, PixelModel[][] pixels) {
    super(height, width, pixels);
  }

  @Override
  public ImageModel compress(double percentage) {
    int width = this.getWidth();
    int height = this.getHeight();

    double[][] reds = new double[height][width];
    double[][] greens = new double[height][width];
    double[][] blues = new double[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        reds[i][j] = this.getPixelValues(i, j).getR();
        greens[i][j] = this.getPixelValues(i, j).getG();
        blues[i][j] = this.getPixelValues(i, j).getB();
      }
    }

    // Unpad each compressed channel to obtain the original size
    double[][][] compressedChannels = this.compressRGBChannels(reds, greens, blues, percentage);
    double[][] processedReds = compressedChannels[0];
    double[][] processedGreens = compressedChannels[1];
    double[][] processedBlues = compressedChannels[2];

    PixelModel[][] compressedPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        compressedPixels[i][j] = new RGBPixel((int) processedReds[i][j],
                (int) processedGreens[i][j], (int) processedBlues[i][j]);
      }
    }

    return new RGBImage(height, width, compressedPixels);
  }

  @Override
  public ImageModel colorCorrect() {
    int[][] frequencies = getFrequencies(this);
    int[] reds = frequencies[0];
    int[] greens = frequencies[1];
    int[] blues = frequencies[2];

    int redPeak = findPeak(reds);
    int greenPeak = findPeak(greens);
    int bluePeak = findPeak(blues);

    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;
    Function<PixelModel, PixelModel> colorCorrect = p -> {
      int updatedR = p.getR() + avgPeak - redPeak;
      int updatedG = p.getG() + avgPeak - greenPeak;
      int updatedB = p.getB() + avgPeak - bluePeak;

      return new RGBPixel(updatedR, updatedG, updatedB);
    };

    return this.applyTransform(colorCorrect);
  }

  @Override
  public ImageModel levelsAdjust(int black, int mid, int white) {
    Function<PixelModel, PixelModel> levelAdjust = p -> {
      int updatedR = this.fittingProcess(black, mid, white, p.getR());
      int updatedG = this.fittingProcess(black, mid, white, p.getG());
      int updatedB = this.fittingProcess(black, mid, white, p.getB());

      return new RGBPixel(updatedR, updatedG, updatedB);
    };

    return this.applyTransform(levelAdjust);
  }

  /**
   * This private method performs a fitting process based on the given parameters.
   * It calculates and returns a fitted value using a quadratic equation.
   *
   * @param black  The black level parameter for the fitting process.
   * @param mid    The mid-level parameter for the fitting process.
   * @param white  The white level parameter for the fitting process.
   * @param signal The input signal value for which the fitting process is performed.
   * @return The fitted value calculated using the quadratic equation.
   */
  private int fittingProcess(int black, int mid, int white, int signal) {
    double calculateA = Math.pow(black, 2) * (mid - white) - black * (Math.pow(mid, 2)
            - Math.pow(white, 2)) + white * Math.pow(mid, 2) - mid * Math.pow(white, 2);

    double calculateAa = -black * (128 - 255) + 128 * white - 255 * mid;

    double calculateAb = Math.pow(black, 2) * (128 - 255) + 255 * Math.pow(mid, 2)
            - 128 * Math.pow(white, 2);


    double calculateAc = Math.pow(black, 2) * (255 * mid - 128 * white)
            - black * (255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2));

    double a = calculateAa / calculateA;

    double b = calculateAb / calculateA;

    double c = calculateAc / calculateA;

    return (int) (a * Math.pow(signal, 2) + b * signal + c);
  }

  /**
   * Finds the peak position in the given histogram within the range [10, 245].
   *
   * @param histogram The histogram array representing the distribution of pixel values.
   * @return The position (value) of the peak within the specified range.
   */
  private int findPeak(int[] histogram) {
    int peakValue = 0;
    int peakPosition = 0;
    for (int i = 10; i <= 245; i++) {
      if (histogram[i] > peakValue) {
        peakValue = histogram[i];
        peakPosition = i;
      }
    }

    return peakPosition;
  }

  /**
   * Compresses a 2D channel array using Haar wavelet transformation and thresholding.
   *
   * @param channel The input channel array.
   * @param percentage The percentage of values to keep during compression.
   * @return The compressed channel array.
   */
  private double[][] compressChannel(double[][] channel, double percentage) {
    int height = channel.length;
    int width = channel[0].length;

    // 2D Haar wavelet transform
    double[][] transformedImage = haarTransform2D(channel, width);

    // Thresholding
    double[] uniqueValues = getUniqueValues(transformedImage);
    double threshold = findThreshold(uniqueValues, percentage);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (Math.abs(transformedImage[i][j]) < threshold) {
          transformedImage[i][j] = 0.0;
        }
      }
    }

    // Inverting the 2D Haar wavelet transform
    transformedImage = inverseHaarTransform2D(transformedImage, width);

    return transformedImage;
  }

  /**
   * Compresses the RGB channels of an image using Haar wavelet transformation and thresholding.
   *
   * <p>The method pads each channel, applies the 2D Haar wavelet transform, performs thresholding
   * based on the specified compression percentage, and then inversely transforms and unpads
   * the channels to reconstruct the compressed image.</p>
   *
   * @param reds The red channel of the original image.
   * @param greens The green channel of the original image.
   * @param blues The blue channel of the original image.
   * @param percentage The compression percentage (between 0 and 100).
   * @return An array of three 2D arrays representing the compressed red, green, and blue channels,
   *         respectively.
   */
  private double[][][] compressRGBChannels(double[][] reds, double[][] greens,
                                                 double[][] blues, double percentage) {
    int width = this.getWidth();
    int height = this.getHeight();

    // Calculate the padded size based on the maximum of width and height
    int paddedSize = padSize(Math.max(width, height));

    // Pad each RGB channel
    double[][] paddedReds = padChannel(reds, paddedSize);
    double[][] paddedGreens = padChannel(greens, paddedSize);
    double[][] paddedBlues = padChannel(blues, paddedSize);

    // Calculate the compression percentage for the Haar wavelet transformation
    double compressionPercentage = percentage / 100;

    // Compress each padded channel
    double[][] compressedReds = compressChannel(paddedReds, compressionPercentage);
    double[][] compressedGreens = compressChannel(paddedGreens, compressionPercentage);
    double[][] compressedBlues = compressChannel(paddedBlues, compressionPercentage);

    // Unpad each compressed channel to obtain the original size
    double[][] unpaddedReds = unpadChannel(compressedReds, height, width);
    double[][] unpaddedGreens = unpadChannel(compressedGreens, height, width);
    double[][] unpaddedBlues = unpadChannel(compressedBlues, height, width);

    // Return the result as a 3D array representing the compressed RGB channels
    return new double[][][]{unpaddedReds, unpaddedGreens, unpaddedBlues};
  }

  /**
   * Pads a 2D channel array to a specified size.
   *
   * @param channel    The original channel array to pad.
   * @param paddedSize The size to which the channel should be padded.
   * @return The padded channel array.
   */
  private double[][] padChannel(double[][] channel, int paddedSize) {
    double[][] paddedChannel = new double[paddedSize][paddedSize];
    for (int i = 0; i < channel.length; i++) {
      System.arraycopy(channel[i], 0, paddedChannel[i], 0, channel[i].length);
    }
    return paddedChannel;
  }

  /**
   * Unpads a padded channel array to its original dimensions.
   *
   * @param paddedImage    The padded channel array to unpad.
   * @param originalHeight The original height of the channel.
   * @param originalWidth  The original width of the channel.
   * @return The unpadded channel array.
   */
  private double[][] unpadChannel(double[][] paddedImage, int originalHeight,
                                        int originalWidth) {
    double[][] originalImage = new double[originalHeight][originalWidth];

    for (int i = 0; i < originalHeight; i++) {
      System.arraycopy(paddedImage[i], 0, originalImage[i], 0, originalWidth);
    }

    return originalImage;
  }

  /**
   * Determines the size to which a channel should be padded (nearest power of two).
   *
   * @param size The original size of the channel.
   * @return The padded size.
   */
  private int padSize(int size) {
    int paddedSize = 1;
    while (paddedSize < size) {
      paddedSize *= 2;
    }
    return paddedSize;
  }

  /**
   * Applies the 2D Haar wavelet transform to an input matrix.
   *
   * @param x The input matrix.
   * @param s The size of the matrix.
   * @return The transformed matrix.
   */
  private double[][] haarTransform2D(double[][] x, int s) {
    int c = s;

    while (c > 1) {
      for (int i = 0; i < s; i++) {
        double[] row = new double[c];
        System.arraycopy(x[i], 0, row, 0, c);
        row = transformSequence1D(row);
        System.arraycopy(row, 0, x[i], 0, c);
      }

      for (int j = 0; j < s; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = x[i][j];
        }
        column = transformSequence1D(column);
        for (int i = 0; i < c; i++) {
          x[i][j] = column[i];
        }
      }

      c = c / 2;
    }

    return x;
  }

  /**
   * Applies the inverse Haar wavelet transform to a transformed sequence.
   *
   * @param s The transformed sequence.
   * @return The inverted sequence.
   */
  private double[][] inverseHaarTransform2D(double[][] x, int s) {
    int c = 2;

    while (c <= s) {
      for (int j = 0; j < s; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = x[i][j];
        }
        column = inverseTransform1D(column);
        for (int i = 0; i < c; i++) {
          x[i][j] = column[i];
        }
      }

      for (int i = 0; i < s; i++) {
        double[] row = new double[c];
        System.arraycopy(x[i], 0, row, 0, c);
        row = inverseTransform1D(row);
        System.arraycopy(row, 0, x[i], 0, c);
      }

      c = c * 2;
    }

    return x;
  }

  /**
   * Applies the Haar wavelet transform to a sequence of values.
   *
   * @param s The input sequence.
   * @return The transformed sequence.
   */
  private double[] transformSequence1D(double[] s) {
    List<Double> avg = new ArrayList<>();
    List<Double> diff = new ArrayList<>();

    for (int i = 0; i < s.length; i += 2) {
      double a = s[i];
      double b = s[i + 1];
      double av = (a + b) / Math.sqrt(2);
      double di = (a - b) / Math.sqrt(2);
      avg.add(av);
      diff.add(di);
    }

    List<Double> result = new ArrayList<>(avg);
    result.addAll(diff);

    return result.stream().mapToDouble(Double::doubleValue).toArray();
  }

  /**
   * Applies the inverse Haar wavelet transform to a transformed sequence.
   *
   * @param s The transformed sequence.
   * @return The inverted sequence.
   */
  private double[] inverseTransform1D(double[] s) {
    double[] avg = Arrays.copyOfRange(s, 0, s.length / 2);
    double[] diff = Arrays.copyOfRange(s, s.length / 2, s.length);

    List<Double> result = new ArrayList<>();
    for (int i = 0, j = 0; i < avg.length; i++, j++) {
      double a = avg[i];
      double b = diff[j];
      double av = (a + b) / Math.sqrt(2);
      double di = (a - b) / Math.sqrt(2);
      result.add(av);
      result.add(di);
    }

    return result.stream().mapToDouble(Double::doubleValue).toArray();
  }

  /**
   * Extracts unique non-zero values from a 2D matrix.
   *
   * @param image The input matrix.
   * @return An array containing unique non-zero values.
   */
  private double[] getUniqueValues(double[][] image) {
    Set<Double> uniqueValues = new HashSet<>();

    for (double[] row : image) {
      for (double value : row) {
        if (value != 0.0) {
          uniqueValues.add(Math.abs(value));
        }
      }
    }

    return uniqueValues.stream().mapToDouble(Double::doubleValue).toArray();
  }

  /**
   * Finds the threshold for channel compression based on a given percentage of unique values.
   *
   * @param values     The array of unique values.
   * @param percentage The percentage of values to keep.
   * @return The calculated threshold.
   */
  private double findThreshold(double[] values, double percentage) {
    int numToReset = (int) (values.length * percentage);
    if (numToReset < 1) {
      return 0.0;
    }

    // Sort the unique values
    Arrays.sort(values);

    return values[numToReset - 1];
  }

}
