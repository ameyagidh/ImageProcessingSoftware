package ime.model;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for View model.
 */
public class ViewModelImplTest {

  private ViewModel model;

  private StringBuilder sb;

  @Before
  public void setUp() throws Exception {
    ExtendedImageProcessor mockModel;

    sb = new StringBuilder();
    mockModel = new MockModel(sb);
    model = new ViewModelImpl(mockModel);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetImageFail() throws IllegalStateException {
    model.getImage();
  }

  @Test
  public void testGetImage() throws IllegalStateException, IOException {
    model.processImage("temp");
    assertEquals(sb.toString(), "Command: temp\n");
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
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }

    @Override
    public void redComponent(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }

    @Override
    public void blueComponent(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }

    @Override
    public void greenComponent(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }

    @Override
    public void lumaGreyscale(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }

    @Override
    public void valueGreyscale(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }

    @Override
    public void intensityGreyscale(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
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
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }

    @Override
    public void sharpen(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }


    @Override
    public void colorCorrect(String[] args) {
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
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
      sb.append("Command: ").append(String.join(" ",args)).append("\n");
    }
  }
}
