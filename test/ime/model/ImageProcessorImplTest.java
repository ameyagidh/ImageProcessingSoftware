package ime.model;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ime.controller.helpers.image.ImageHelperFactory;
import ime.controller.helpers.image.ImageHelperFactoryImpl;
import ime.model.image.ImageModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A Junit test class to test the ImageProcessor.
 */
public class ImageProcessorImplTest {

  private final ImageHelperFactory factory = new ImageHelperFactoryImpl();
  private ImageProcessorImpl processor;
  private String filePath;
  private ImageModel image;

  @Before
  public void setUp() {
    try {
      filePath = "test_images/paris-test.ppm";
      processor = new ImageProcessorImpl();
      InputStream inputStream = factory.getImageHelper(filePath).readImage(filePath);
      processor.load("paris", inputStream);
      image = processor.getImage("paris");
    } catch (Exception e) {
      fail("Failed to instantiate the PPM image");
    }
  }

  @Test
  public void testLoadPPM() {
    int lineWithP3 = 1;
    int lineWithHeightWidth = 2;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      int currentLine = 1;

      while ((line = br.readLine()) != null) {
        if (currentLine == lineWithP3) {
          assertEquals("P3", line);
        } else if (currentLine == lineWithHeightWidth) {
          int width = Integer.parseInt(line.split(" ")[0]);
          int height = Integer.parseInt(line.split(" ")[1]);
          assertEquals(width, processor.getImage("paris").getWidth());
          assertEquals(height, processor.getImage("paris").getHeight());
        }
        currentLine++;
      }

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IOException.class)
  public void testInvalidPPM() throws IOException {
    List<String> invalidPPMFilePaths = new ArrayList<>();
    for (int i = 0; i <= 7; i++) {
      invalidPPMFilePaths.add("test/res/invalid" + i + ".ppm");
    }
    for (String invalidFilePath : invalidPPMFilePaths) {
      factory.getImageHelper(invalidFilePath).readImage(invalidFilePath);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsHeight() {
    image.getPixelValues(image.getHeight(), image.getWidth() - 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsWidth() {
    image.getPixelValues(image.getHeight() - 1, image.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsBoth() {
    image.getPixelValues(image.getHeight(), image.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueOutOfBoundsNegative() {
    image.getPixelValues(-1, -1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetImageFail() {
    processor.getImage("random_image");
  }

  @Test
  public void testLumaComponent() throws IOException {
    filePath = "test/res/paris.jpg";
    processor.load("paris-jpg", factory.getImageHelper(filePath).readImage(filePath));
    ImageModel parisJpg = processor.getImage("paris-jpg");

    filePath = "test/res/paris.png";
    processor.load("paris-png", factory.getImageHelper(filePath).readImage(filePath));
    ImageModel parisPng = processor.getImage("paris-png");

    String[] jpgArgs = {"paris-jpg", "paris-jpg-luma"};
    String[] pngArgs = {"paris-png", "paris-png-luma"};
    String[] args = {"paris", "luma"};
    processor.lumaGreyscale(jpgArgs);
    processor.lumaGreyscale(pngArgs);
    processor.lumaGreyscale(args);

    ImageModel luma = processor.getImage("luma");
    ImageModel lumaPng = processor.getImage("paris-png-luma");
    ImageModel lumaJpg = processor.getImage("paris-jpg-luma");


    assertEquals(image.getHeight(), luma.getHeight());
    assertEquals(image.getWidth(), luma.getWidth());

    assertEquals(parisJpg.getHeight(), lumaJpg.getHeight());
    assertEquals(parisJpg.getWidth(), lumaJpg.getWidth());

    assertEquals(parisPng.getHeight(), lumaPng.getHeight());
    assertEquals(parisPng.getWidth(), lumaPng.getWidth());


    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int lumaExpected =
                (int) Math.round(image.getPixelValues(i, j).getR() * 0.2126
                        + image.getPixelValues(i, j).getG() * 0.7152
                        + image.getPixelValues(i, j).getB() * 0.0722);
        assertEquals(lumaExpected, luma.getPixelValues(i, j).getR());
        assertEquals(lumaExpected, luma.getPixelValues(i, j).getB());
        assertEquals(lumaExpected, luma.getPixelValues(i, j).getG());
      }
    }

    for (int i = 0; i < parisJpg.getHeight(); i++) {
      for (int j = 0; j < parisJpg.getWidth(); j++) {
        int lumaExpected =
                (int) Math.round(parisJpg.getPixelValues(i, j).getR() * 0.2126
                        + parisJpg.getPixelValues(i, j).getG() * 0.7152
                        + parisJpg.getPixelValues(i, j).getB() * 0.0722);
        assertEquals(lumaExpected, lumaJpg.getPixelValues(i, j).getR());
        assertEquals(lumaExpected, lumaJpg.getPixelValues(i, j).getB());
        assertEquals(lumaExpected, lumaJpg.getPixelValues(i, j).getG());
      }
    }

    for (int i = 0; i < parisPng.getHeight(); i++) {
      for (int j = 0; j < parisPng.getWidth(); j++) {
        int lumaExpected =
                (int) Math.round(parisPng.getPixelValues(i, j).getR() * 0.2126
                        + parisPng.getPixelValues(i, j).getG() * 0.7152
                        + parisPng.getPixelValues(i, j).getB() * 0.0722);
        assertEquals(lumaExpected, lumaPng.getPixelValues(i, j).getR());
        assertEquals(lumaExpected, lumaPng.getPixelValues(i, j).getG());
        assertEquals(lumaExpected, lumaPng.getPixelValues(i, j).getB());
      }
    }
  }

  @Test
  public void testIntensityComponent() {
    String[] args = {"paris", "intensity"};
    processor.intensityGreyscale(args);
    ImageModel intensity = processor.getImage("intensity");
    assertEquals(intensity.getHeight(), image.getHeight());
    assertEquals(intensity.getWidth(), image.getWidth());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int averageExpected =
                (image.getPixelValues(i, j).getR() + image.getPixelValues(i, j).getB()
                        + image.getPixelValues(i, j).getG()) / 3;
        assertEquals(averageExpected, intensity.getPixelValues(i, j).getR());
        assertEquals(averageExpected, intensity.getPixelValues(i, j).getB());
        assertEquals(averageExpected, intensity.getPixelValues(i, j).getG());
      }
    }
  }

  @Test
  public void testGetValueGreyscale() {
    String[] args = {"paris", "value"};
    processor.valueGreyscale(args);
    ImageModel result = processor.getImage("value");
    assertEquals(image.getHeight(), result.getHeight());
    assertEquals(image.getWidth(), result.getWidth());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int expected =
                (image.getPixelValues(i, j).getR() + image.getPixelValues(i, j).getB()
                        + image.getPixelValues(i, j).getG()) / 3;
        assertEquals(expected, result.getPixelValues(i, j).getR());
        assertEquals(expected, result.getPixelValues(i, j).getB());
        assertEquals(expected, result.getPixelValues(i, j).getG());
      }
    }
  }


  @Test
  public void testSplitAndCombine() {
    List<ImageModel> splitImages = new ArrayList<>();
    processor.rgbSplit("test", "red", "green",
            "blue");

    ImageModel red = processor.getImage("red");
    ImageModel green = processor.getImage("green");
    ImageModel blue = processor.getImage("blue");

    splitImages.add(red);
    splitImages.add(green);
    splitImages.add(blue);

    for (ImageModel image : splitImages) {
      assertEquals(image.getMaxValue(), this.image.getMaxValue());
      assertEquals(image.getHeight(), this.image.getHeight());
      assertEquals(image.getWidth(), this.image.getWidth());
    }

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(red.getPixelValues(i, j).getR(), image.getPixelValues(i, j).getR());
        assertEquals(red.getPixelValues(i, j).getG(), image.getPixelValues(i, j).getR());
        assertEquals(red.getPixelValues(i, j).getB(), image.getPixelValues(i, j).getR());

        assertEquals(green.getPixelValues(i, j).getR(), image.getPixelValues(i, j).getG());
        assertEquals(green.getPixelValues(i, j).getG(), image.getPixelValues(i, j).getG());
        assertEquals(green.getPixelValues(i, j).getB(), image.getPixelValues(i, j).getG());

        assertEquals(blue.getPixelValues(i, j).getR(), image.getPixelValues(i, j).getB());
        assertEquals(blue.getPixelValues(i, j).getG(), image.getPixelValues(i, j).getB());
        assertEquals(blue.getPixelValues(i, j).getB(), image.getPixelValues(i, j).getB());

      }
    }

    processor.rgbCombine("red", "green", "blue",
            "combined");
    ImageModel combined = processor.getImage("combined");
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(combined.getPixelValues(i, j), image.getPixelValues(i, j));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineThreeIncompatibleImages() {
    String filepath22 = "/res/test/test22.ppm";
    String filepath34 = "/res/test/test34.ppm";
    String filepath42 = "/res/test/test42.ppm";
    try {

      processor.load("test2x2", factory.getImageHelper(filepath22).readImage(filepath22));
      processor.load("test2x3", factory.getImageHelper(filepath34).readImage(filepath34));
      processor.load("test3x2", factory.getImageHelper(filepath42).readImage(filepath42));
    } catch (Exception e) {
      fail("Failed to instantiate the PPM image");
    }
    processor.rgbCombine("test2x2", "test3x4", "test4x2",
            "combined");
  }

  @Test
  public void testVerticalFlip() {
    processor.verticalFlip("paris", "paris-vertical");
    ImageModel vertical = processor.getImage("paris-vertical");

    assertEquals(vertical.getHeight(), image.getHeight());
    assertEquals(vertical.getWidth(), image.getWidth());
    assertEquals(vertical.getMaxValue(), image.getMaxValue());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(vertical.getPixelValues(i, j)
                , image.getPixelValues(image.getHeight() - i - 1, j));
      }
    }

  }

  @Test
  public void testHorizontalFlip() {
    processor.horizontalFlip("paris", "paris-horizontal");
    ImageModel horizontal = processor.getImage("paris-horizontal");

    assertEquals(horizontal.getHeight(), image.getHeight());
    assertEquals(horizontal.getWidth(), image.getWidth());
    assertEquals(horizontal.getMaxValue(), image.getMaxValue());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(horizontal.getPixelValues(i, j)
                , image.getPixelValues(i, image.getWidth() - j - 1));
      }
    }
  }

  @Test
  public void testFlipTwiceSame() {
    processor.verticalFlip("paris", "paris-vertical");
    processor.verticalFlip("paris-vertical", "paris-vertical-twice");
    ImageModel verticalOriginal = processor.getImage("paris-vertical-twice");

    processor.horizontalFlip("paris", "paris-horizontal");
    processor.horizontalFlip("paris-horizontal", "paris-horizontal-twice");
    ImageModel horizontalOriginal = processor.getImage("paris-horizontal-twice");

    assertEquals(verticalOriginal.getHeight(), image.getHeight());
    assertEquals(verticalOriginal.getWidth(), image.getWidth());
    assertEquals(verticalOriginal.getMaxValue(), image.getMaxValue());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(verticalOriginal.getPixelValues(i, j), image.getPixelValues(i, j));
      }
    }

    assertEquals(horizontalOriginal.getHeight(), image.getHeight());
    assertEquals(horizontalOriginal.getWidth(), image.getWidth());
    assertEquals(horizontalOriginal.getMaxValue(), image.getMaxValue());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(horizontalOriginal.getPixelValues(i, j), image.getPixelValues(i, j));
      }
    }
  }

  @Test
  public void testSepia() throws IOException {

    filePath = "test/res/paris.png";
    processor.load("paris-png", factory.getImageHelper(filePath).readImage(filePath));
    ImageModel parisPng = processor.getImage("paris-png");

    filePath = "test/res/sepia-expected.png";
    processor.load("paris-png", factory.getImageHelper(filePath).readImage(filePath));
    ImageModel expectedSepiaPng = processor.getImage("paris-expected-png");

    String[] args = {"paris-png", "paris-sepia-png"};
    processor.sepia(args);

    ImageModel result = processor.getImage("paris-sepia-png");

    assertEquals(expectedSepiaPng.getHeight(), result.getHeight());
    assertEquals(expectedSepiaPng.getWidth(), result.getWidth());
    assertEquals(expectedSepiaPng.getMaxValue(), result.getMaxValue());

    for (int i = 0; i < result.getHeight(); i++) {
      for (int j = 0; j < result.getWidth(); j++) {
        assertEquals(expectedSepiaPng.getPixelValues(i, j), result.getPixelValues(i, j));
      }
    }
  }

}
