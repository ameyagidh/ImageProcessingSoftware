package ime.model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

import ime.controller.helpers.image.ImageHelperFactory;
import ime.controller.helpers.image.ImageHelperFactoryImpl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * JUnit test cases for the ExtendedImageProcessorImpl.
 */
public class ExtendedImageProcessorImplTest {

  private ExtendedImageProcessorImpl processor;

  private final ImageHelperFactory factory = new ImageHelperFactoryImpl();
  private String filepath;

  @Before
  public void setUp() {
    try {
      filepath = "test_images/paris-test.ppm";
      processor = new ExtendedImageProcessorImpl();
      InputStream inputStream = factory.getImageHelper(filepath).readImage(filepath);
      processor.load("paris", inputStream);
    } catch (Exception e) {
      fail("Failed to instantiate the PPM image");
    }
  }

  @Test(expected = InputMismatchException.class)
  public void testInvalidBMWLevelsAdjust() {
    int b = 10;
    int m = 255;
    int w = 280;
    String[] args = {String.valueOf(b), String.valueOf(m), String.valueOf(w),
                     "paris", "paris-adjust"};
    processor.levelsAdjust(args);
  }

  @Test(expected = InputMismatchException.class)
  public void testInvalidBMWLevelsAdjust1() {
    int b = 100;
    int m = 95;
    int w = 255;
    String[] args = {String.valueOf(b), String.valueOf(m), String.valueOf(w),
                     "paris", "paris-adjust"};
    processor.levelsAdjust(args);
  }

  @Test(expected = InputMismatchException.class)
  public void testInvalidBMWLevelsAdjust3() {
    int m = 95;
    int w = 255;
    String[] args = {"invalid-val", String.valueOf(m), String.valueOf(w),
                     "paris", "paris-adjust"};
    processor.levelsAdjust(args);
  }

  @Test(expected = InputMismatchException.class)
  public void testInvalidBMWLevelsAdjust2() {
    int b = 120;
    int m = 180;
    int w = 120;
    String[] args = {String.valueOf(b), String.valueOf(m), String.valueOf(w),
                     "paris", "paris-adjust"};
    processor.levelsAdjust(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidColorCorrectSplitOperation() {
    String[] args = {"paris", "paris-cc", "Splitting", String.valueOf(50)};
    processor.colorCorrect(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidBlurSplitOperation() {
    String[] args = {"paris", "paris-blur", "Splitting", String.valueOf(50)};
    processor.blur(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidLumaSplitOperation() {
    String[] args = {"paris", "paris-luma", "Splitting", String.valueOf(50)};
    processor.lumaGreyscale(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidSepiaSplitOperation() {
    String[] args = {"paris", "paris-sepia", "Splitting", String.valueOf(50)};
    processor.sepia(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidLevelAdjustSplitOperation() {
    int b = 120;
    int m = 180;
    int w = 120;
    String[] args = {String.valueOf(b), String.valueOf(m), String.valueOf(w),
                     "paris", "paris-adjust", "Splitting", String.valueOf(50)};
    processor.levelsAdjust(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidSharpenSplitOperation() {
    String[] args = {"paris", "paris-sharpen", "Splitting", String.valueOf(50)};
    processor.sharpen(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidSharpenSplitPercentage() {
    String[] args = {"paris", "paris-sharpen", "split", String.valueOf(-50)};
    processor.sharpen(args);
  }

  @Test(expected = InputMismatchException.class)
  public void invalidSepiaSplitPercentage() {
    String[] args = {"paris", "paris-sepia", "split", String.valueOf(150)};
    processor.sharpen(args);
  }

  @Test
  public void testProcessorForAllOperations() throws IOException {
    InputStream inputStream = new ImageHelperFactoryImpl()
            .getImageHelper(filepath).readImage(filepath);

    //load operation
    processor.load("paris", inputStream);
    assertNotNull(processor.getImage("paris"));

    String[] blurArgs = {"paris", "paris-blur"};
    processor.blur(blurArgs);
    assertNotNull(processor.getImage("paris-blur"));

    String[] sharpenArgs = {"paris", "paris-sharpen"};
    processor.sharpen(sharpenArgs);
    assertNotNull(processor.getImage("paris-sharpen"));

    String[] sepiaArgs = {"paris", "paris-sepia"};
    processor.sharpen(sepiaArgs);
    assertNotNull(processor.getImage("paris-sepia"));

    String[] colorCorrectArgs = {"paris", "paris-cc"};
    processor.colorCorrect(colorCorrectArgs);
    assertNotNull(processor.getImage("paris-cc"));

    String[] levelsAdjustment = {String.valueOf(20), String.valueOf(100), String.valueOf(255),
                                 "paris", "paris-adjust"};
    processor.levelsAdjust(levelsAdjustment);
    assertNotNull(processor.getImage("paris-adjust"));

    processor.compress(50, "paris", "paris-50-compress");
    assertNotNull(processor.getImage("paris-50-compress"));

    processor.histogram("paris", "paris-histo");
    assertNotNull(processor.getImage("paris-histo"));
  }

}
