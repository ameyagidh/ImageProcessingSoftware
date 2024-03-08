package ime.integration;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import ime.controller.ImageController;
import ime.controller.ImageControllerInterface;
import ime.controller.helpers.image.ImageHelperFactory;
import ime.controller.helpers.image.ImageHelperFactoryImpl;
import ime.model.ExtendedImageProcessor;
import ime.model.ExtendedImageProcessorImpl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Integration testing for the Image processing application.
 */
public class ImageControllerIntegrationTest {

  private ExtendedImageProcessor processor;

  private ImageHelperFactory factory;

  @Before
  public void setup() {
    factory = new ImageHelperFactoryImpl();
    processor = new ExtendedImageProcessorImpl();
  }

  @Test
  public void testColorCorrect() throws IOException {
    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "color-correct paris paris-cc\n" +
            "save test_images/actual/paris-cc.png paris-cc\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);
    String expectedImage = "paris-cc.png";

    try {
      String outputImagePath = "test_images/actual/paris-cc.png";
      String expectedImagePath = "test_images/expected/" + expectedImage;
      InputStream expected = factory.getImageHelper(expectedImagePath)
              .readImage(expectedImagePath);
      InputStream actual = factory.getImageHelper(outputImagePath)
              .readImage(outputImagePath);
      assertTrue(compareInputStreams(expected, actual));

    } catch (Exception e) {
      fail("Specified path has no file.");
    }

  }

  @Test
  public void testCompress() throws IOException {
    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "compress 50 paris paris-50-compress\n" +
            "save test_images/actual/paris-50-compress.png paris-50-compress\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);
    String expectedImage = "paris-50-compress.png";

    try {
      String outputImagePath = "test_images/actual/paris-50-compress.png";
      String expectedImagePath = "test_images/expected/" + expectedImage;
      InputStream expected = factory.getImageHelper(expectedImagePath)
              .readImage(expectedImagePath);
      InputStream actual = factory.getImageHelper(outputImagePath)
              .readImage(outputImagePath);
      assertTrue(compareInputStreams(expected, actual));

    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testLevelAdjust() throws IOException {
    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "levels-adjust 20 100 255 paris paris-adjust-1\n" +
            "save test_images/actual/paris-adjust-1.png paris-adjust-1\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);
    String expectedImage = "paris-adjust-1.png";

    try {
      String outputImagePath = "test_images/actual/paris-adjust-1.png";
      String expectedImagePath = "test_images/expected/" + expectedImage;
      InputStream expected = factory.getImageHelper(expectedImagePath)
              .readImage(expectedImagePath);
      InputStream actual = factory.getImageHelper(outputImagePath)
              .readImage(outputImagePath);
      assertTrue(compareInputStreams(expected, actual));

    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testHistogram() throws IOException {
    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "histogram paris paris-histo\n" +
            "save test_images/actual/paris-histogram.png paris-histo\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);
    String expectedImage = "paris-histogram.png";

    try {
      String outputImagePath = "test_images/actual/paris-histogram.png";
      String expectedImagePath = "test_images/expected/" + expectedImage;
      InputStream expected = factory.getImageHelper(expectedImagePath)
              .readImage(expectedImagePath);
      InputStream actual = factory.getImageHelper(outputImagePath)
              .readImage(outputImagePath);
      assertTrue(compareInputStreams(expected, actual));

    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testSepiaSplitView() throws IOException {
    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "sepia paris paris-sepia-split-30 split 30\n" +
            "save test_images/actual/paris-sepia-split-30.png paris-sepia-split-30\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);
    String expectedImage = "paris-sepia-split-30.png";

    try {
      String outputImagePath = "test_images/actual/paris-sepia-split-30.png";
      String expectedImagePath = "test_images/expected/" + expectedImage;
      InputStream expected = factory.getImageHelper(expectedImagePath)
              .readImage(expectedImagePath);
      InputStream actual = factory.getImageHelper(outputImagePath)
              .readImage(outputImagePath);
      assertTrue(compareInputStreams(expected, actual));

    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testLumaSplitView() throws IOException {
    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "luma-component paris paris-luma-split-70 split 70\n" +
            "save test_images/actual/paris-luma-split-70.png paris-luma-split-70\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);
    String expectedImage = "paris-luma-split-70.png";

    try {
      String outputImagePath = "test_images/actual/paris-luma-split-70.png";
      String expectedImagePath = "test_images/expected/" + expectedImage;
      InputStream expected = factory.getImageHelper(expectedImagePath)
              .readImage(expectedImagePath);
      InputStream actual = factory.getImageHelper(outputImagePath)
              .readImage(outputImagePath);
      assertTrue(compareInputStreams(expected, actual));

    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testSharpenSplitView() throws IOException {
    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "sharpen paris paris-sharpen-split-50 split 50\n" +
            "save test_images/actual/paris-sharpen-split-50.png paris-sharpen-split-50\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);
    String expectedImage = "paris-sharpen-split-50.png";

    try {
      String outputImagePath = "test_images/actual/paris-sharpen-split-50.png";
      String expectedImagePath = "test_images/expected/" + expectedImage;
      InputStream expected = factory.getImageHelper(expectedImagePath)
              .readImage(expectedImagePath);
      InputStream actual = factory.getImageHelper(outputImagePath)
              .readImage(outputImagePath);
      assertTrue(compareInputStreams(expected, actual));

    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testComponentOperations() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-blue.png", "paris-green.png", "paris-red.png");

    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "blue-component paris paris-blue\n" +
            "green-component paris paris-green\n" +
            "red-component paris paris-red\n" +
            "save test_images/actual/paris-blue.png paris-blue\n" +
            "save test_images/actual/paris-green.png paris-green\n" +
            "save test_images/actual/paris-red.png paris-red\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testCompressMultipleTimes() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-50-compress.png", "paris-80-compress.png", "paris-histogram.png");

    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "compress 50 paris paris-50-compress\n" +
            "compress 80 paris paris-80-compress\n" +
            "histogram paris paris-histogram\n" +
            "save test_images/actual/paris-50-compress.png paris-50-compress\n" +
            "save test_images/actual/paris-80-compress.png paris-80-compress\n" +
            "save test_images/actual/paris-histogram.png paris-histogram\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testCompressWith0Percentage() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-0-compress.png", "paris-histogram.png");

    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "compress 0 paris paris-0-compress\n" +
            "histogram paris paris-histogram\n" +
            "save test_images/actual/paris-0-compress.png paris-0-compress\n" +
            "save test_images/actual/paris-histogram.png paris-histogram\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testCompressWith100Percentage() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-100-compress.png", "paris-histogram.png");

    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "compress 100 paris paris-100-compress\n" +
            "histogram paris paris-histogram\n" +
            "save test_images/actual/paris-100-compress.png paris-100-compress\n" +
            "save test_images/actual/paris-histogram.png paris-histogram\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testLevelAdjust2() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-adjust.png", "paris-adjust-compress.png");

    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "levels-adjust 20 100 255 paris paris-adjust\n" +
            "compress 50 paris-adjust paris-adjust-compress\n" +
            "save test_images/actual/paris-adjust.png paris-adjust\n" +
            "save test_images/actual/paris-adjust-compress.png paris-adjust-compress\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testCompressColorCorrectAndLevelAdjust() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-adjust.png", "paris-adjust-compress.png",
                    "paris-adjust-compress-cc.png");

    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "levels-adjust 20 100 255 paris paris-adjust\n" +
            "compress 50 paris-adjust paris-adjust-compress\n" +
            "color-correct paris-adjust-compress paris-adjust-compress-cc\n" +
            "save test_images/actual/paris-adjust.png paris-adjust\n" +
            "save test_images/actual/paris-adjust-compress.png paris-adjust-compress\n" +
            "save test_images/actual/paris-adjust-compress-cc.png paris-adjust-compress-cc\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testColorCorrectAndHistogram() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-cc.png", "paris-cc-histo.png");

    Reader in = new StringReader("load res/paris-small.png paris\n" +
            "color-correct paris paris-cc\n" +
            "histogram paris-cc paris-cc-histo\n" +
            "save test_images/actual/paris-cc.png paris-cc\n" +
            "save test_images/actual/paris-cc-histo.png paris-cc-histo\n"
    );

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  @Test
  public void testRunScriptWithMixedSetOfCommands() throws IOException {

    List<String> expectedImages =
            Arrays.asList("paris-blue.png", "paris-green.png", "paris-value.png",
                    "paris-cc.png", "paris-cc-histo.png", "paris-adjust-1.png",
                    "paris-50-compress.png", "paris-80-compress.png", "paris-histogram.png",
                    "paris-sepia-split-30.png");

    Reader in = new StringReader("run res/test-script.txt\n");

    StringBuffer out = new StringBuffer();
    ImageControllerInterface controller = new ImageController(in, out);
    controller.execute(processor);

    try {
      for (String image : expectedImages) {
        String outputImagePath = "test_images/actual/" + image;
        String expectedImagePath = "test_images/expected/" + image;
        InputStream expected = factory.getImageHelper(expectedImagePath)
                .readImage(expectedImagePath);
        InputStream actual = factory.getImageHelper(outputImagePath)
                .readImage(outputImagePath);
        assertTrue(compareInputStreams(expected, actual));
      }
    } catch (Exception e) {
      fail("Specified path has no file.");
    }
  }

  /**
   * Compare the provided input streams.
   *
   * @param inputStream1 inputStream 1 to compare.
   * @param inputStream2 inputStream 2 to compare.
   * @return true if both are equals and false otherwise.
   * @throws IOException throws IOException.
   */
  private boolean compareInputStreams(InputStream inputStream1, InputStream inputStream2)
          throws IOException {
    byte[] buffer1 = new byte[1024];
    byte[] buffer2 = new byte[1024];

    int bytesRead1;
    int bytesRead2;

    try {
      while ((bytesRead1 = inputStream1.read(buffer1)) != -1) {
        bytesRead2 = inputStream2.read(buffer2);

        if (bytesRead1 != bytesRead2 || !Arrays.equals(buffer1, buffer2)) {
          return false;
        }
      }

      // Check if the second stream has more data
      return inputStream2.read(buffer2) == -1;
    } finally {
      inputStream1.close();
      inputStream2.close();
    }
  }
}
