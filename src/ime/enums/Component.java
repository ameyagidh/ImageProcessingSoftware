package ime.enums;

/**
 * The Component enum represents different color components or color spaces that can be used
 * in image processing and manipulation. Each constant in this enum corresponds to a specific
 * component or color space, such as RGB (Red, Green, Blue), YUV (Luma and Chroma), or other
 * color-related attributes.
 */
public enum Component {
  RED,       // Represents the Red color component in RGB
  GREEN,     // Represents the Green color component in RGB
  BLUE,      // Represents the Blue color component in RGB
  VALUE,     // Represents the Value (brightness) component
  INTENSITY, // Represents the Intensity (luminance) component
  LUMA      // Represents the Luma component in YUV or YCbCr color spaces
}
