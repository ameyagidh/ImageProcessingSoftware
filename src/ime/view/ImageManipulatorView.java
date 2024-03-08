package ime.view;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.stream.Stream;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ime.controller.Features;
import ime.controller.enums.Command;
import ime.model.ViewModel;
import ime.utils.MessageUtil;

/**
 * Graphical user interface (GUI) class representing an image manipulator view.
 *
 * <p>This class extends the {@code JFrame} to create a window for displaying and interacting with
 * image manipulation features. It implements the {@code IView} interface to ensure compatibility
 * with the application's view system.</p>
 *
 * <p>The {@code ImageManipulatorView} serves as the user interface for image manipulation,
 * providing a platform for users to visualize and engage with various image processing operations.
 * </p>
 */
public class ImageManipulatorView extends JFrame implements IView {

  private final ViewModel model;
  private final String RED_COMPONENT = "Red";
  private final String GREEN_COMPONENT = "Green";
  private final String BLUE_COMPONENT = "Blue";
  private final String VALUE_COMPONENT = "Value";
  private final String INTENSITY_COMPONENT = "Intensity";
  private final String LUMA_COMPONENT = "Luma";
  private final String BLUR = "Blur";
  private final String SHARPEN = "Sharpen";

  // View elements
  private final JPanel imagePanel;
  private final JLabel imageLabel;

  private final JLabel histogramLabel;
  private final JMenuItem openItem;
  private final JMenuItem saveItem;
  private final JMenuItem exitItem;
  private final JButton sepiaButton;
  private final JButton rgbSplitButton;
  private final JButton rgbCombineButton;
  private final JButton compressButton;
  private final JButton colorCorrectButton;
  private final JButton levelAdjustButton;
  private final JButton horizontalFlipButton;
  private final JButton verticalFlipButton;
  private final JButton brightenButton;
  private final FileNameExtensionFilter filter;
  private final JComboBox<String> filterTypes;

  private final JComboBox<String> greyscaleTypes;
  private final JButton filterTypesButton;
  private final JButton componentExecuteButton;
  private final JButton greyscaleExecuteButton;
  private final JComboBox<String> componentTypes;
  private final JTextField brightnessValue;
  private final JTextField compressValue;
  private final JTextField blackLevelAdjustValue;
  private final JTextField midLevelAdjustValue;
  private final JTextField whiteLevelAdjustValue;
  private final JPanel mainPanel;
  private String selectedComponent;
  private String selectedFilter;
  private String selectedGreyscale;
  private final JButton splitPreviewButton;
  private final JTextField splitPreviewPercentageValue;
  private final JPanel splitPreviewPanel;
  private final JLabel splitPreviewOperation;

  private final JButton applyFilterButton;
  private final JButton cancelFilterButton;
  private Command currectCommand;

  /**
   * Constructs an {@code ImageManipulatorView} with the specified caption, associated
   * view model and UI components.
   *
   * @param caption The caption/title of the image manipulator view.
   * @param model   The {@code ViewModel} associated with the view for managing image-related
   *                operations.
   */
  public ImageManipulatorView(String caption, ViewModel model) {
    super(caption);
    this.model = model;

    filter = new FileNameExtensionFilter("JPG, PNG, & PPM Images",
            "jpg", "png", "ppm");

    // View components
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    openItem = new JMenuItem("Open...");
    saveItem = new JMenuItem("Save As");
    exitItem = new JMenuItem("Exit");

    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.add(exitItem);

    menuBar.add(fileMenu);

    setJMenuBar(menuBar);

    // Create main panel with GridBagLayout
    mainPanel = new JPanel(new GridBagLayout());

    // Create mainScreen with GridBagLayout
    JPanel mainScreen = new JPanel(new GridBagLayout());

    // Add mainScreen to main panel with GridBagConstraints.
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new java.awt.Insets(5, 2, 5, 2);

    c.gridx = 0;
    c.gridy = 1;
    c.weighty = 0.95;
    mainPanel.add(mainScreen, c);

    // Create leftScreen with GridBagLayout.
    JPanel leftScreen = new JPanel(new GridBagLayout());

    // Create rightScreen with GridBagLayout.
    JPanel rightScreen = new JPanel(new GridBagLayout());

    GridBagConstraints leftConstraints = new GridBagConstraints();
    leftConstraints.gridx = 0;
    leftConstraints.gridy = 0;
    leftConstraints.weightx = 0.2;
    leftConstraints.weighty = 1;
    leftConstraints.fill = GridBagConstraints.BOTH;

    // Set the layout for the leftScreen JPanel
    leftScreen.setLayout(new BoxLayout(leftScreen, BoxLayout.Y_AXIS));

    JPanel operationPanel = new JPanel();
    operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));

    splitPreviewPanel = new JPanel(new GridBagLayout());
    splitPreviewPanel.setBorder(BorderFactory.createTitledBorder("Split preview"));
    GridBagConstraints splitPreviewPanelConstraints = new GridBagConstraints();
    splitPreviewPanelConstraints.anchor = GridBagConstraints.CENTER;
    splitPreviewPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    splitPreviewPanelConstraints.weightx = 1;
    splitPreviewPanelConstraints.weighty = 1;
    splitPreviewPanelConstraints.insets = new Insets(3, 3, 3, 3);

    splitPreviewPercentageValue = new JTextField("0", 3);
    splitPreviewPanelConstraints.gridx = 0;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(splitPreviewPercentageValue, splitPreviewPanelConstraints);

    splitPreviewButton = new JButton("View");
    splitPreviewButton.setToolTipText("Apply split");
    splitPreviewPanelConstraints.gridx = 1;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(splitPreviewButton, splitPreviewPanelConstraints);

    applyFilterButton = new JButton("Apply");
    applyFilterButton.setToolTipText("Apply the current operation on entire image.");
    splitPreviewPanelConstraints.gridx = 2;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(applyFilterButton, splitPreviewPanelConstraints);

    cancelFilterButton = new JButton("Cancel");
    cancelFilterButton.setToolTipText("Cancel the current operation.");
    splitPreviewPanelConstraints.gridx = 3;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(cancelFilterButton, splitPreviewPanelConstraints);

    splitPreviewOperation = new JLabel("Current operation: ");
    splitPreviewPanelConstraints.gridx = 0;
    splitPreviewPanelConstraints.gridy = 1;
    splitPreviewPanel.add(splitPreviewOperation, splitPreviewPanelConstraints);

    operationPanel.add(splitPreviewPanel);

    JPanel colorTransformAndFilterPanel = new JPanel();
    colorTransformAndFilterPanel.setBorder(
            BorderFactory.createTitledBorder("Color transform & Filters"));

    JPanel colorTransformAndFilterControlsPanel = new JPanel();
    colorTransformAndFilterControlsPanel.setLayout(new GridBagLayout());

    GridBagConstraints controlsPanelConstraints = new GridBagConstraints();
    controlsPanelConstraints.anchor = GridBagConstraints.CENTER;
    controlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    controlsPanelConstraints.weightx = 1;
    controlsPanelConstraints.weighty = 1;
    controlsPanelConstraints.insets = new Insets(3, 3, 3, 3);

    JLabel greyscaleTypesLabel = new JLabel("Greyscale:");
    controlsPanelConstraints.gridx = 0;
    controlsPanelConstraints.gridy = 0;
    colorTransformAndFilterControlsPanel.add(greyscaleTypesLabel, controlsPanelConstraints);

    greyscaleTypes = new JComboBox<>(new String[]{"Select Greyscale",
        LUMA_COMPONENT, INTENSITY_COMPONENT, VALUE_COMPONENT});
    greyscaleTypes.setToolTipText("Select Greyscale");

    greyscaleTypes.addActionListener(e -> this.selectedGreyscale =
            (String) greyscaleTypes.getSelectedItem());

    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 0;
    colorTransformAndFilterControlsPanel.add(greyscaleTypes, controlsPanelConstraints);

    greyscaleExecuteButton = new JButton("Execute");
    greyscaleExecuteButton.setToolTipText("Execute the selected greyscale type operation");
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 0;
    colorTransformAndFilterControlsPanel.add(greyscaleExecuteButton, controlsPanelConstraints);

    JLabel componentTypesLabel = new JLabel("Component:");
    controlsPanelConstraints.gridx = 0;
    controlsPanelConstraints.gridy = 1;
    colorTransformAndFilterControlsPanel.add(componentTypesLabel, controlsPanelConstraints);

    componentTypes = new JComboBox<>(new String[]{"Select Component",
        RED_COMPONENT, GREEN_COMPONENT, BLUE_COMPONENT});
    componentTypes.setToolTipText("Select Component");

    componentTypes.addActionListener(e -> this.selectedComponent =
            (String) componentTypes.getSelectedItem());

    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 1;
    colorTransformAndFilterControlsPanel.add(componentTypes, controlsPanelConstraints);

    componentExecuteButton = new JButton("Execute");
    componentExecuteButton.setToolTipText("Execute the selected component type operation");
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 1;
    colorTransformAndFilterControlsPanel.add(componentExecuteButton, controlsPanelConstraints);

    JLabel filterTypesLabel = new JLabel("Filter:");
    controlsPanelConstraints.gridx = 0;
    controlsPanelConstraints.gridy = 2;
    colorTransformAndFilterControlsPanel.add(filterTypesLabel, controlsPanelConstraints);

    filterTypes = new JComboBox<>(new String[]{"Select Filter", BLUR, SHARPEN});
    filterTypes.setToolTipText("Select Filter");

    filterTypes.addActionListener(e -> this.selectedFilter =
            (String) filterTypes.getSelectedItem());

    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 2;
    colorTransformAndFilterControlsPanel.add(filterTypes, controlsPanelConstraints);

    filterTypesButton = new JButton("Execute");
    filterTypesButton.setToolTipText("Execute the selected filter operation");
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 2;
    colorTransformAndFilterControlsPanel.add(filterTypesButton, controlsPanelConstraints);

    // Add the color transformation and filter panel to left screen
    colorTransformAndFilterPanel.add(colorTransformAndFilterControlsPanel);

    operationPanel.add(colorTransformAndFilterPanel, BorderLayout.CENTER);

    JPanel basicOperationsPanel = new JPanel(new GridBagLayout());
    basicOperationsPanel.setBorder(BorderFactory.createTitledBorder("Basic Operations"));
    GridBagConstraints basicOperationsPanelConstraints = new GridBagConstraints();
    basicOperationsPanelConstraints.anchor = GridBagConstraints.CENTER;
    basicOperationsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    basicOperationsPanelConstraints.weightx = 1;
    basicOperationsPanelConstraints.weighty = 1;
    basicOperationsPanelConstraints.insets = new Insets(3, 3, 3, 3);

    brightnessValue = new JTextField("0", 3);
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 1;
    basicOperationsPanel.add(brightnessValue, basicOperationsPanelConstraints);

    brightenButton = new JButton("Adjust Brightness");
    brightenButton.setToolTipText("Apply brightness");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 1;
    basicOperationsPanel.add(brightenButton, basicOperationsPanelConstraints);

    compressValue = new JTextField("0", 3);
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 2;
    basicOperationsPanel.add(compressValue, basicOperationsPanelConstraints);

    compressButton = new JButton("Compress");
    compressButton.setToolTipText("Compress image");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 2;
    basicOperationsPanel.add(compressButton, basicOperationsPanelConstraints);

    rgbSplitButton = new JButton("RGB Split");
    rgbSplitButton.setToolTipText("Split the RGB Channels");
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 3;
    basicOperationsPanel.add(rgbSplitButton, basicOperationsPanelConstraints);

    rgbCombineButton = new JButton("RGB Combine");
    rgbCombineButton.setToolTipText("Combine the RGB Channels");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 3;
    basicOperationsPanel.add(rgbCombineButton, basicOperationsPanelConstraints);

    horizontalFlipButton = new JButton("Horizontal Flip");
    horizontalFlipButton.setToolTipText("Apply horizontal flip");
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 4;
    basicOperationsPanel.add(horizontalFlipButton, basicOperationsPanelConstraints);

    verticalFlipButton = new JButton("Vertical Flip");
    verticalFlipButton.setToolTipText("Apply vertical flip");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 4;
    basicOperationsPanel.add(verticalFlipButton, basicOperationsPanelConstraints);

    sepiaButton = new JButton("Sepia");
    sepiaButton.setToolTipText("Apply Sepia Filter");
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 5;
    basicOperationsPanel.add(sepiaButton, basicOperationsPanelConstraints);

    colorCorrectButton = new JButton("Color correct");
    colorCorrectButton.setToolTipText("Apply Color correction");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 5;
    basicOperationsPanel.add(colorCorrectButton, basicOperationsPanelConstraints);
    operationPanel.add(basicOperationsPanel);

    JPanel levelAdjustPanel = new JPanel(new GridBagLayout());
    levelAdjustPanel.setBorder(BorderFactory.createTitledBorder("Level adjust operation"));
    GridBagConstraints levelAdjustPanelConstraints = new GridBagConstraints();
    levelAdjustPanelConstraints.anchor = GridBagConstraints.CENTER;
    levelAdjustPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    levelAdjustPanelConstraints.weightx = 1;
    levelAdjustPanelConstraints.weighty = 1;
    levelAdjustPanelConstraints.insets = new Insets(3, 3, 3, 3);

    blackLevelAdjustValue = new JTextField("Enter black", 3);
    controlsPanelConstraints.gridx = 1;
    controlsPanelConstraints.gridy = 0;
    levelAdjustPanel.add(blackLevelAdjustValue, levelAdjustPanelConstraints);

    midLevelAdjustValue = new JTextField("Enter mid", 3);
    controlsPanelConstraints.gridx = 2;
    controlsPanelConstraints.gridy = 0;
    levelAdjustPanel.add(midLevelAdjustValue, levelAdjustPanelConstraints);

    whiteLevelAdjustValue = new JTextField("Enter white", 3);
    controlsPanelConstraints.gridx = 3;
    controlsPanelConstraints.gridy = 0;
    levelAdjustPanel.add(whiteLevelAdjustValue, levelAdjustPanelConstraints);

    levelAdjustButton = new JButton("Level adjust");
    levelAdjustButton.setToolTipText("Apply level adjust");
    controlsPanelConstraints.gridx = 4;
    controlsPanelConstraints.gridy = 0;
    levelAdjustPanel.add(levelAdjustButton, levelAdjustPanelConstraints);

    operationPanel.add(levelAdjustPanel, BorderLayout.AFTER_LAST_LINE);

    this.enableOperationButtons(false);
    this.enableSplitPreview(false);

    leftScreen.add(operationPanel);

    JScrollPane operationPanelScroller = new JScrollPane(operationPanel);
    operationPanelScroller
            .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    leftScreen.add(operationPanelScroller, BorderLayout.CENTER);

    histogramLabel = new JLabel();

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setSize(400, 500);
    histogramPanel.add(histogramLabel);
    histogramPanel.setVisible(true);

    leftScreen.add(histogramPanel);

    mainScreen.add(leftScreen, leftConstraints);

    GridBagConstraints rightConstraints = new GridBagConstraints();
    rightConstraints.gridx = 1;
    rightConstraints.gridy = 0;
    rightConstraints.weightx = 0.8;
    rightConstraints.weighty = 1;
    rightConstraints.fill = GridBagConstraints.BOTH;

    rightScreen.setLayout(new BorderLayout());

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image preview"));

    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Add the imagePanel to the center of the rightScreen
    rightScreen.add(scroller, BorderLayout.CENTER);

    // Main image
    imageLabel = new JLabel("Please load an image\n" + " File > Open…");
    imagePanel.add(imageLabel);

    mainScreen.add(rightScreen, rightConstraints);

    // Add main panel to frame
    add(mainPanel);

    // Set frame properties
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setPreferredSize(new Dimension(1500, 800));
    setMinimumSize(new Dimension(1200, 500));
    pack();
    setVisible(true);
  }

  private void enableSplitPreview(boolean enable) {
    splitPreviewPanel.setVisible(enable);
    splitPreviewPercentageValue.setEnabled(enable);
    splitPreviewButton.setEnabled(enable);
    applyFilterButton.setEnabled(enable);
    splitPreviewOperation.setEnabled(enable);
  }

  private void enableOperationButtons(boolean enable) {
    brightenButton.setEnabled(enable);
    brightnessValue.setEnabled(enable);
    horizontalFlipButton.setEnabled(enable);
    verticalFlipButton.setEnabled(enable);
    greyscaleTypes.setEnabled(enable);
    greyscaleExecuteButton.setEnabled(enable);
    filterTypes.setEnabled(enable);
    filterTypesButton.setEnabled(enable);
    sepiaButton.setEnabled(enable);
    rgbSplitButton.setEnabled(enable);
    rgbCombineButton.setEnabled(enable);
    componentTypes.setEnabled(enable);
    colorCorrectButton.setEnabled(enable);
    levelAdjustButton.setEnabled(enable);
    blackLevelAdjustValue.setEnabled(enable);
    midLevelAdjustValue.setEnabled(enable);
    whiteLevelAdjustValue.setEnabled(enable);
    componentExecuteButton.setEnabled(enable);
    compressValue.setEnabled(enable);
    compressButton.setEnabled(enable);

    // Save will work after loading the image
    saveItem.setEnabled(enable);
  }

  @Override
  public void refreshView(String imageName, String histogram) {
    try {
      model.processImage(imageName);
      Image bufferedImage = model.getImage();
      // Reset the current content of the label
      imageLabel.setText("");
      imageLabel.setIcon(new ImageIcon(bufferedImage));

      // process the histogram
      model.processImage(histogram);
      Image histogramBufferedImage = model.getImage();
      this.histogramLabel.setIcon(new ImageIcon(histogramBufferedImage));

    } catch (IOException | InputMismatchException e) {
      showErrorMessage("Unable to display the image. Please provide a valid file.");
    }
  }

  @Override
  public void splitView(String splitImageName) {
    try {
      model.processImage(splitImageName);
      Image bufferedImage = model.getImage();
      // Reset the current content of the label
      imageLabel.setText("");
      imageLabel.setIcon(new ImageIcon(bufferedImage));

    } catch (IOException | InputMismatchException e) {
      showErrorMessage("Unable to display the split view. Please provide a valid file.");
    }
  }

  /**
   * Performs the image manipulation operation based on the selected component type.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void component(Features features) {
    try {
      switch (selectedComponent) {
        case RED_COMPONENT:
          features.redComponent();
          break;
        case BLUE_COMPONENT:
          features.blueComponent();
          break;
        case GREEN_COMPONENT:
          features.greenComponent();
          break;
        default:
          throw new InputMismatchException();
      }
      componentTypes.setSelectedItem("Select Component");
    } catch (IOException | InputMismatchException | NullPointerException e) {
      showErrorMessage("Please select a valid Component");
      componentTypes.setSelectedItem("Select Component");
    }
  }

  /**
   * Performs the image manipulation operation based on the selected filter type.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void filter(Features features) {
    try {
      switch (selectedFilter) {
        case BLUR:
          features.blur();
          break;
        case SHARPEN:
          features.sharpen();
          break;
        default:
          throw new InputMismatchException();
      }
      filterTypes.setSelectedItem("Select Filter");
    } catch (IOException | InputMismatchException | NullPointerException e) {
      showErrorMessage("Please select a valid filter");
      filterTypes.setSelectedItem("Select Filter");
    }
  }

  /**
   * Loads an image using a file chooser dialog and triggers the corresponding operation.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void loadImage(Features features) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();

      try {
        features.load(f.getPath());
        imagePanel.setBorder(BorderFactory.createTitledBorder(f.getPath()));

        // Enabling all the buttons for operations
        this.enableOperationButtons(true);

        displaySuccessMessage(mainPanel, "Successfully loaded the image.");
      } catch (IOException | InputMismatchException e) {
        showErrorMessage("Please provide the valid file in input!");
      }
    }
  }

  /**
   * A utility method that displays a file chooser dialog for loading files
   * and allows setting a default filename.
   *
   * @param defaultFilename The default filename to be pre-selected in the file chooser.
   * @return The absolute path of the selected file if chosen; otherwise, an empty string.
   */
  private String openFilePathSelector(String defaultFilename) {
    // Create a file chooser dialog
    JFileChooser fileChooser = new JFileChooser();

    // Set a file filter if needed (replace 'filter' with the actual file filter)
    fileChooser.setFileFilter(filter);

    // Set the default file name
    File defaultFile = new File(defaultFilename);
    fileChooser.setSelectedFile(defaultFile);

    int result = fileChooser.showOpenDialog(this);
    // Check if the user selected a file
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  /**
   * Combines the red, green, and blue component images into a single RGB image.
   * This operation requires three images representing the red, green, and blue components.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void rgbCombine(Features features) {
    String message = "This operation requires 3 images which are red, green and "
            + "blue component images respectively that you want to combine.";
    JOptionPane.showMessageDialog(mainPanel, message, "RGB Combine",
            JOptionPane.INFORMATION_MESSAGE);

    String filepathRed;
    String filepathGreen = "";
    String filepathBlue = "";

    filepathRed = openFilePathSelector("red-split.png");
    if (!filepathRed.isEmpty()) {
      filepathGreen = openFilePathSelector("green-split.png");
      if (!filepathGreen.isEmpty()) {
        filepathBlue = openFilePathSelector("blue-split.png");
      }
    }

    //performing rgbCombine
    if (!filepathRed.isEmpty() && !filepathGreen.isEmpty() && !filepathBlue.isEmpty()) {
      try {
        features.rgbCombine(filepathRed, filepathGreen, filepathBlue);
      } catch (IOException | InputMismatchException e) {
        showErrorMessage(MessageUtil.getInvalidFilePathErrorMessage());
      }
    } else {
      showErrorMessage(MessageUtil.getInvalidFilePathErrorMessage());
    }
  }

  /**
   * Opens a file saver dialog with the provided default filename and returns the selected
   * filepath.
   *
   * @param defaultFilename The default filename for the file saver dialog.
   * @return The selected filepath from the file saver dialog.
   */
  private String openFileSaver(String defaultFilename) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(filter);

    // Setting the default file name
    File defaultFile = new File(defaultFilename);
    fileChooser.setSelectedFile(defaultFile);
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  /**
   * Splits the image into red, green, and blue components and saves them at specified locations.
   * This operation requires three locations for the splits to be saved, corresponding to red,
   * green, and blue components.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void rgbSplit(Features features) {
    String message = "This operation requires 3 locations for the splits to "
            + "be saved, for red, green and blue splits respectively.";
    JOptionPane.showMessageDialog(mainPanel, message, "RGB Split",
            JOptionPane.INFORMATION_MESSAGE);

    String filepathRed;
    String filepathGreen = "";
    String filepathBlue = "";

    filepathRed = openFileSaver("red-split.png");
    if (!filepathRed.isEmpty()) {
      filepathGreen = openFileSaver("green-split.png");
      if (!filepathGreen.isEmpty()) {
        filepathBlue = openFileSaver("blue-split.png");
      }
    }

    if (!filepathRed.isEmpty() && !filepathGreen.isEmpty() && !filepathBlue.isEmpty()) {
      try {
        features.rgbSplit(filepathRed, filepathGreen, filepathBlue);
      } catch (IOException e) {
        showErrorMessage(MessageUtil.getInvalidFilePathErrorMessage());
      } catch (InputMismatchException e) {
        showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
      }
    } else {
      showErrorMessage(MessageUtil.getInvalidFilePathErrorMessage());
    }
  }

  /**
   * Displays a success message dialog with the provided information message.
   *
   * @param parentFrame The parent component for the success message dialog.
   * @param informMessage The information message to display in the success message dialog.
   */
  private void displaySuccessMessage(Component parentFrame, String informMessage) {
    JOptionPane.showMessageDialog(parentFrame, informMessage, "Success",
            JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Displays a file chooser dialog to save the image and invokes the corresponding save
   * operation in the provided {@code Features} object. Shows success or error messages based on
   * the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void saveImage(Features features) {
    final JFileChooser fChooser = new JFileChooser();
    int retValue = fChooser.showSaveDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      //Please validate the file format
      try {
        String path = f.getAbsolutePath();
        features.save(path);
        displaySuccessMessage(mainPanel, "Successfully saved the image.");
      } catch (IOException | InputMismatchException e) {
        showErrorMessage("Unable to save the image. Please provide a valid path and try again");
      }
    }
  }

  /**
   * Applies the sepia filter to the image using the provided {@code Features} object. Shows
   * success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void sepia(Features features) {
    try {
      features.sepia();
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
    }
  }

  /**
   * Applies the selected greyscale transformation to the image using the provided
   * {@code Features} object. Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void greyscale(Features features) {
    try {
      switch (selectedGreyscale) {
        case VALUE_COMPONENT:
          features.valueGreyscale();
          break;
        case INTENSITY_COMPONENT:
          features.intensityGreyscale();
          break;
        case LUMA_COMPONENT:
          features.lumaGreyscale();
          break;
        default:
          throw new InputMismatchException();
      }
      greyscaleTypes.setSelectedItem("Select Greyscale");
    } catch (IOException | InputMismatchException | NullPointerException e) {
      showErrorMessage("Please select a valid Greyscale");
      greyscaleTypes.setSelectedItem("Select Greyscale");
    }
  }

  /**
   * Flips the image horizontally using the provided {@code Features} object.
   * Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void horizontalFlip(Features features) {
    try {
      features.horizontalFlip();
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
    }
  }

  /**
   * Flips the image vertically using the provided {@code Features} object.
   * Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void verticalFlip(Features features) {
    try {
      features.verticalFlip();
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
    }
  }

  /**
   * Adjusts the brightness of the image using the provided {@code Features} object.
   * Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void brighten(Features features) {
    try {
      int value = Integer.parseInt(brightnessValue.getText());
      features.brighten(value);
      brightnessValue.setText("");
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
      brightnessValue.setText("");
    } catch (NumberFormatException e) {
      showErrorMessage("Invalid brighten value. It should be a numeric value");
      brightnessValue.setText("");
    }
  }

  /**
   * Compresses the image based on the specified compression value using the provided
   * {@code Features} object. Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void compress(Features features) {
    try {
      double value = Double.parseDouble(compressValue.getText());
      features.compress(value);
      compressValue.setText("");
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
      compressValue.setText("");
    } catch (NumberFormatException e) {
      showErrorMessage("Invalid compress value. It should be a numeric value");
      compressValue.setText("");
    }
  }

  /**
   * Corrects the color of the image using the provided {@code Features} object.
   * Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void colorCorrect(Features features) {
    try {
      features.colorCorrect();
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
    }
  }

  /**
   * Adjusts the black, mid, and white levels of the image using the provided {@code Features}
   * object. Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void levelAdjust(Features features) {
    try {
      int black = Integer.parseInt(blackLevelAdjustValue.getText());
      int mid = Integer.parseInt(midLevelAdjustValue.getText());
      int white = Integer.parseInt(whiteLevelAdjustValue.getText());
      features.levelAdjust(black, mid, white);
      blackLevelAdjustValue.setText("");
      midLevelAdjustValue.setText("");
      whiteLevelAdjustValue.setText("");
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
      blackLevelAdjustValue.setText("");
      midLevelAdjustValue.setText("");
      whiteLevelAdjustValue.setText("");
    } catch (NumberFormatException e) {
      showErrorMessage("Invalid black, mid or white value. It should be a numeric value");
      blackLevelAdjustValue.setText("");
      midLevelAdjustValue.setText("");
      whiteLevelAdjustValue.setText("");
    }
  }

  /**
   * Previews the selected filter operation using the provided {@code Features} object.
   * Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void previewFilter(Features features) {
    try {
      switch (selectedFilter) {
        case BLUR:
          this.previewSplit(Command.BLUR, features);
          break;
        case SHARPEN:
          this.previewSplit(Command.SHARPEN, features);
          break;
        default:
          throw new InputMismatchException();
      }
      //filterTypes.setSelectedItem("Select Filter");
    } catch (InputMismatchException | NullPointerException e) {
      showErrorMessage("Please select a valid filter");
      filterTypes.setSelectedItem(" Select Filter ");
    }
  }

  /**
   * Previews the selected greyscale operation using the provided {@code Features} object.
   * Shows success or error messages based on the result.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void previewGreyscale(Features features) {
    try {
      switch (selectedGreyscale) {
        case VALUE_COMPONENT:
          this.previewSplit(Command.VALUE_COMPONENT, features);
          break;
        case INTENSITY_COMPONENT:
          this.previewSplit(Command.INTENSITY_COMPONENT, features);
          break;
        case LUMA_COMPONENT:
          this.previewSplit(Command.LUMA_COMPONENT, features);
          break;
        default:
          throw new InputMismatchException();
      }
      //greyscaleTypes.setSelectedItem("Select Greyscale");
    } catch (InputMismatchException | NullPointerException e) {
      showErrorMessage("Please select a valid Greyscale");
      greyscaleTypes.setSelectedItem("Select Greyscale");
    }
  }

  /**
   * Initiates the split preview operation based on the specified filter command. Sets up the
   * user interface for split view, gathers necessary input, and calls the corresponding method in
   * the provided {@code Features} object.
   *
   * @param command  The filter command for split preview.
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void previewSplit(Command command, Features features) {
    this.enableOperationButtons(false);
    this.enableSplitPreview(true);

    double value = 0;
    try {
      value = Double.parseDouble(splitPreviewPercentageValue.getText());
    } catch (NumberFormatException e) {
      showErrorMessage("Invalid Percentage value. It should be a numeric value");
    }

    try {
      String[] args = new String[]{"split", String.valueOf(value)};
      this.currectCommand = command;
      splitPreviewOperation.setText("Current Operation: " + command.command());
      switch (command) {
        case BLUR:
          features.split(Command.BLUR, args);
          break;
        case SHARPEN:
          features.split(Command.SHARPEN, args);
          break;
        case SEPIA:
          features.split(Command.SEPIA, args);
          break;
        case LUMA_COMPONENT:
          features.split(Command.LUMA_COMPONENT, args);
          break;
        case INTENSITY_COMPONENT:
          features.split(Command.INTENSITY_COMPONENT, args);
          break;
        case COLOR_CORRECT:
          features.split(Command.COLOR_CORRECT, args);
          break;
        case VALUE_COMPONENT:
          features.split(Command.VALUE_COMPONENT, args);
          break;
        case LEVEL_ADJUST:
          try {
            int black = Integer.parseInt(blackLevelAdjustValue.getText());
            int mid = Integer.parseInt(midLevelAdjustValue.getText());
            int white = Integer.parseInt(whiteLevelAdjustValue.getText());
            String[] leveAdjustArgs = new String[]{String.valueOf(black), String.valueOf(mid),
                    String.valueOf(white)};
            String[] finalArgs = Stream.of(leveAdjustArgs, args).flatMap(Stream::of)
                    .toArray(String[]::new);
            features.split(Command.LEVEL_ADJUST, finalArgs);
          } catch (NumberFormatException e) {
            showErrorMessage("Invalid black, mid or white value. It should be a numeric value");
            blackLevelAdjustValue.setText("");
            midLevelAdjustValue.setText("");
            whiteLevelAdjustValue.setText("");
            this.enableOperationButtons(true);
            this.enableSplitPreview(false);
          }
          break;
        default:
          throw new InputMismatchException("Invalid Split view operation");
      }
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
      this.enableOperationButtons(true);
      this.enableSplitPreview(false);
    }
  }

  /**
   * Applies the current filter operation based on the last split preview command. Calls the
   * corresponding method in the provided {@code Features} object, then resets relevant flags and
   * controls.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void applyFilter(Features features) {
    try {
      switch (currectCommand) {
        case BLUR:
        case SHARPEN:
          this.filter(features);
          break;
        case SEPIA:
          this.sepia(features);
          break;
        case LUMA_COMPONENT:
        case VALUE_COMPONENT:
        case INTENSITY_COMPONENT:
          this.greyscale(features);
          break;
        case COLOR_CORRECT:
          this.colorCorrect(features);
          break;
        case LEVEL_ADJUST:
          this.levelAdjust(features);
          break;
        default:
          throw new InputMismatchException("Invalid Split view operation");
      }
      this.enableOperationButtons(true);
      this.enableSplitPreview(false);
    } catch (InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
    } catch (NumberFormatException e) {
      showErrorMessage("Invalid Percentage value. It should be a numeric value");
    }
  }

  /**
   * Cancels the ongoing filter operation. Resets relevant flags, enables operation buttons, and
   * sets default values for filter and greyscale types. Additionally, reloads the original image.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void cancelFilter(Features features) {
    try {
      this.currectCommand = null;
      this.enableOperationButtons(true);
      this.enableSplitPreview(false);

      //set defaults
      filterTypes.setSelectedItem("Select Filter");
      greyscaleTypes.setSelectedItem("Select Greyscale");
      features.reloadImage();
    } catch (IOException | InputMismatchException e) {
      showErrorMessage(MessageUtil.getPerformOperationErrorMessage());
    }
  }

  /**
   * Updates the split preview based on the current filter command. Calls the corresponding method
   * in the provided {@code Features} object.
   *
   * @param features The {@code Features} interface providing image manipulation operations.
   */
  private void updateSplitPreview(Features features) {
    this.previewSplit(currectCommand, features);
  }

  @Override
  public void addFeatures(Features features) {
    rgbCombineButton.addActionListener(event -> rgbCombine(features));

    componentExecuteButton.addActionListener(event -> component(features));

    filterTypesButton.addActionListener(event -> previewFilter(features));

    horizontalFlipButton.addActionListener(event -> horizontalFlip(features));

    verticalFlipButton.addActionListener(event -> verticalFlip(features));

    brightenButton.addActionListener(event -> brighten(features));

    rgbSplitButton.addActionListener(event -> rgbSplit(features));

    exitItem.addActionListener(event -> features.exitProgram());

    openItem.addActionListener(event -> loadImage(features));

    saveItem.addActionListener(event -> saveImage(features));

    sepiaButton.addActionListener(event -> previewSplit(Command.SEPIA, features));

    compressButton.addActionListener(event -> compress(features));

    levelAdjustButton.addActionListener(event -> previewSplit(Command.LEVEL_ADJUST, features));

    colorCorrectButton.addActionListener(event -> previewSplit(Command.COLOR_CORRECT, features));

    greyscaleExecuteButton.addActionListener(event -> previewGreyscale(features));

    splitPreviewButton.addActionListener(event -> updateSplitPreview(features));

    applyFilterButton.addActionListener(event -> applyFilter(features));

    cancelFilterButton.addActionListener(event -> cancelFilter(features));
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(mainPanel, error, "Error occurred",
            JOptionPane.ERROR_MESSAGE);
  }
}
