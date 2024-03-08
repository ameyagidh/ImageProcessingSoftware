package ime.utils;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import ime.model.image.ImageModel;
import ime.model.image.PixelModel;

/**
 * The HistogramGenerator class is responsible for creating and visualizing histograms for
 * the red, green, and blue color channels of an RGB image.
 */
public class HistogramGenerator {

  private final BufferedImage histogramImage;

  /**
   * Constructs a HistogramGenerator and initializes the histogram image with a white background.
   * The grid lines are also drawn on the histogram.
   */
  public HistogramGenerator() {
    this.histogramImage = new BufferedImage(255, 255, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = histogramImage.createGraphics();
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, histogramImage.getWidth(), histogramImage.getHeight());

    // Draw grid lines
    g2d.setColor(Color.GRAY);
    for (int i = 0; i < histogramImage.getWidth(); i += 10) {
      g2d.drawLine(i, 0, i, histogramImage.getHeight());
      g2d.drawLine(0, i, histogramImage.getWidth(), i);
    }
    g2d.dispose();
  }

  /**
   * Creates a histogram image based on the provided RGB image model.
   *
   * @param rgbImage The RGB image model for which the histogram is generated.
   * @return The histogram image.
   */
  public BufferedImage createHistogram(ImageModel rgbImage) {
    int[][] frequencies = getFrequencies(rgbImage);
    int[] reds = frequencies[0];
    int[] greens = frequencies[1];
    int[] blues = frequencies[2];

    int maxFrequency = findMaxFrequency(reds, greens, blues);

    // Draw the histograms
    Graphics2D g = histogramImage.createGraphics();
    drawHistogramLines(g, reds, Color.RED, maxFrequency);
    drawHistogramLines(g, greens, Color.GREEN, maxFrequency);
    drawHistogramLines(g, blues, Color.BLUE, maxFrequency);

    g.dispose();

    return histogramImage;
  }

  /**
   * Draws histogram lines for a given color channel.
   *
   * @param g            The Graphics2D object for drawing.
   * @param frequencies  The array of frequencies for the color channel.
   * @param color        The color of the histogram line.
   * @param maxFrequency The maximum frequency across all color channels.
   */
  private void drawHistogramLines(Graphics2D g, int[] frequencies, Color color, int maxFrequency) {
    g.setColor(color);

    int lastY = histogramImage.getHeight();
    for (int i = 0; i < frequencies.length; i++) {
      int y = histogramImage.getHeight()
              - (frequencies[i] * histogramImage.getHeight() / maxFrequency);
      g.drawLine(i, lastY, i, y);
      lastY = y;
    }
  }

  /**
   * Finds the maximum frequency across multiple arrays.
   *
   * @param arrays Arrays for which the maximum frequency is determined.
   * @return The maximum frequency.
   */
  private static int findMaxFrequency(int[]... arrays) {
    int max = 0;
    for (int[] array : arrays) {
      for (int value : array) {
        if (value > max) {
          max = value;
        }
      }
    }
    return max;
  }

  /**
   * Computes the frequencies of each intensity level for the red, green, and blue color channels
   * in the given RGB image model.
   *
   * @param rgbImage The RGB image model.
   * @return An array of int arrays representing the frequencies for each color channel.
   */
  public static int[][] getFrequencies(ImageModel rgbImage) {
    int[] reds = new int[256];
    int[] greens = new int[256];
    int[] blues = new int[256];

    // Compute the frequency of each intensity level for each color
    for (int i = 0; i < rgbImage.getHeight(); i++) {
      for (int j = 0; j < rgbImage.getWidth(); j++) {
        PixelModel pixel = rgbImage.getPixelValues(i, j);
        reds[pixel.getR()]++;
        greens[pixel.getG()]++;
        blues[pixel.getB()]++;
      }
    }

    return new int[][]{reds, greens, blues};
  }
}
