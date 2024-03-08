package ime.view;

import java.io.IOException;

import ime.controller.Features;

/**
 * The {@code IView} interface represents a generic view in a software application.
 * Implementing classes should define the behavior for refreshing the view, adding features,
 * and displaying error messages.
 */
public interface IView {

  /**
   * Refreshes the view, typically called after the view is constructed.
   * This method allows updating the display based on changes in the underlying data.
   *
   * @param imageName The name of the image associated with the view.
   * @param histogram The name of the histogram image associated with the view.
   * @throws IOException If an I/O error occurs while refreshing the view.
   */
  void refreshView(String imageName, String histogram) throws IOException;

  /**
   * Splits the current view based on the provided image name. Implementing classes
   * should define the logic for splitting the view, often involving the creation of
   * multiple subviews or partitions.
   *
   * @param splitImageName The name of the image used for splitting the view.
   * @throws IOException If an I/O error occurs during the splitting process.
   */
  void splitView(String splitImageName) throws IOException;

  /**
   * Adds features to the view. Implementing classes should handle the integration
   * of provided features into the view's functionality.
   *
   * @param features Features to be added to the view.
   */
  void addFeatures(Features features);

  /**
   * Displays an error message in the view. This method is typically used to inform
   * the user when a command could not be processed correctly.
   *
   * @param error Represents the error message to be displayed.
   */
  void showErrorMessage(String error);

}

