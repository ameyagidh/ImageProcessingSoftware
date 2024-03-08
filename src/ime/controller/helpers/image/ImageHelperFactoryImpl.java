package ime.controller.helpers.image;

import ime.controller.utils.ImageUtil;

/**
 * Concrete implementation of the {@link ImageHelperFactory} interface.
 * Provides a mechanism to return appropriate {@link ImageHelper} instances based
 * on file extensions.
 */
public class ImageHelperFactoryImpl implements ImageHelperFactory {
  @Override
  public ImageHelper getImageHelper(String filepath) {
    String ext = ImageUtil.getExtension(filepath);
    ImageHelper imageHelper;
    switch (ext) {
      case "ppm":
        imageHelper = new PPMImageHelper();
        break;
      case "jpg":
      case "png":
      case "jpe g":
        imageHelper = new GenericImageHelper();
        break;
      default:
        throw new IllegalArgumentException("Unsupported format: " + ext);
    }
    return imageHelper;

  }
}
