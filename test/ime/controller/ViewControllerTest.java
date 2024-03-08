package ime.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ime.model.ExtendedImageProcessor;
import ime.view.IView;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for ViewController.
 */
public class ViewControllerTest {
  private StringBuilder modelLogger;
  private Features viewController;

  @Before
  public void setUp() {
    ExtendedImageProcessor mockModel;
    modelLogger = new StringBuilder();
    IView mockView = new ViewControllerTest.MockView(new StringBuilder());
    mockModel = new ViewControllerTest.MockModel(modelLogger);
    viewController = new ViewController(mockModel, mockView);
  }

  @Test
  public void testLoad() throws IOException {
    viewController.load("res/paris-small.png");
    assertEquals("Command: current-image\n", modelLogger.toString());
  }

  @Test(expected = IOException.class)
  public void testLoadFail() throws IOException {
    viewController.load("res/invalid-image.jup");
  }

  @Test
  public void testSave() throws IOException {
    viewController.save("res/paris-cc.png");
    assertEquals("Command: current-image\n", modelLogger.toString());
  }

  @Test
  public void testBrighten() throws IOException {
    viewController.brighten(50);
    assertEquals("Command: 50 current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testVerticalFlip() throws IOException {
    viewController.verticalFlip();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    viewController.verticalFlip();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testLumaGreyscale() throws IOException {
    viewController.lumaGreyscale();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testIntensityGreyscale() throws IOException {
    viewController.intensityGreyscale();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testValueGreyscale() throws IOException {
    viewController.valueGreyscale();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testRedComponent() throws IOException {
    viewController.redComponent();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testGreenComponent() throws IOException {
    viewController.greenComponent();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testBlueComponent() throws IOException {
    viewController.blueComponent();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testSepia() throws IOException {
    viewController.sepia();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testBlur() throws IOException {
    viewController.blur();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testSharpen() throws IOException {
    viewController.sharpen();
    assertEquals("Command: current-image current-image\n", modelLogger.toString());
  }

  @Test
  public void testRgbSplit() throws IOException {
    viewController.rgbSplit("res/paris-rgb-split-red.png",
            "res/paris-rgb-split-green.png",
            "res/paris-rgb-split-blue.png");
    assertEquals("Command: current-image current-image-red current-image-green "
            + "current-image-blueInput: current-image-redInput: current-image-greenInput: "
            + "current-image-blue", modelLogger.toString());
  }

  @Test
  public void testRgbCombine() throws IOException {
    viewController.rgbCombine("res/paris-split-red.png",
            "res/paris-split-green.png",
            "res/paris-split-blue.png");
    assertEquals("Input: red-current-image: green-current-image: "
            + "blue-current-image: current-image red-current-image "
            + "green-current-image blue-current-image", modelLogger.toString());
  }

  @Test(expected = IOException.class)
  public void testRgbCombineFailure() throws IOException {
    viewController.rgbCombine("res/invalid-red.png",
            "res/invalid-green.png",
            "res/invalid-blue.png");
  }

  /**
   * Class for mocking the view for the implementation of the controller.
   */
  private static class MockView implements IView {
    private final StringBuilder sb;

    public MockView(StringBuilder sb) {
      this.sb = sb;
    }


    @Override
    public void refreshView(String imageName, String histogram) {
      sb.append("Command: ").append(imageName).append(histogram).append("\n");
    }

    @Override
    public void splitView(String splitImageName) throws IOException {
      sb.append("Command: ").append(splitImageName).append("\n");
    }

    @Override
    public void addFeatures(Features features) {
      sb.append("Command: ").append(features.getClass()).append("\n");
    }

    @Override
    public void showErrorMessage(String errorMessage) {
      sb.append("Command: ").append(errorMessage).append("\n");
    }
  }

  /**
   * Represents a MockModel of the ImageProcessor to test the controller..
   */
  private static class MockModel implements ExtendedImageProcessor {

    private final StringBuilder sb;

    public MockModel(StringBuilder sb) {
      this.sb = sb;
    }

    @Override
    public OutputStream save(String imageName) throws IOException {
      sb.append("Command: ").append(imageName).append("\n");
      // Return does not matter as this is a controller test.
      String newImage = "2 2 183 10 10 15 90 10 12 10 10 15 12 10 10";
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] strBytes = newImage.getBytes(); // Convert string to byte array
      outputStream.write(strBytes);
      return outputStream;
    }

    @Override
    public void sepia(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void redComponent(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void blueComponent(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void greenComponent(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void lumaGreyscale(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void valueGreyscale(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void intensityGreyscale(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void verticalFlip(String image, String result) {
      sb.append("Command: ").append(image).append(" ").append(result).append("\n");
    }

    @Override
    public void load(String imgName, InputStream inputStream) {
      sb.append("Command: ").append(imgName).append("\n");
    }


    @Override
    public void brighten(String imgName, String destImgName, int increment) {
      sb.append("Command: ").append(increment).append(" ").append(imgName).append(" ")
              .append(destImgName).append("\n");
    }

    @Override
    public void horizontalFlip(String image, String result) {
      sb.append("Command: ").append(image).append(" ").append(result).append("\n");
    }


    @Override
    public void rgbSplit(String image, String redResult, String greenResult, String blueResult) {
      sb.append("Command: ").append(image).append(" ").append(redResult).append(" ")
              .append(greenResult).append(" ").append(blueResult).append("\n");

    }

    @Override
    public void rgbCombine(String redImage, String greenImage,
                           String blueImage, String resultImage) {
      sb.append("Command: ").append(resultImage).append(" ").append(redImage).append(" ")
              .append(greenImage).append(" ").append(blueImage).append("\n");
    }

    @Override
    public void blur(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void sharpen(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }


    @Override
    public void colorCorrect(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void compress(double percentage, String imgName, String destImgName) {
      sb.append("Command: ").append(percentage).append(" ").append(imgName).append(" ")
              .append(destImgName).append("\n");
    }

    @Override
    public void histogram(String imgName, String destImgName) {
      sb.append("Command: ").append(imgName).append(" ").append(destImgName).append("\n");
    }

    @Override
    public void levelsAdjust(String[] args) {
      sb.append("Command: ").append(String.join(" ", args)).append("\n");
    }
  }

}
