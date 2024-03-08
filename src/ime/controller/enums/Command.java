package ime.controller.enums;

/**
 * The Command enum defines a set of image manipulation commands, each associated with a
 * command string used for user input and processing. These commands represent various
 * image processing operations that can be performed on images.
 */
public enum Command {
  RED_COMPONENT("red-component", 2),
  BLUE_COMPONENT("blue-component", 2),
  SAVE("save", 2),
  GREEN_COMPONENT("green-component", 2),
  LOAD("load", 2),
  VALUE_COMPONENT("value-component", 2),
  SEPIA("sepia", 2),
  SHARPEN("sharpen", 2),
  BRIGHTEN("brighten", 3),
  RGB_COMBINE("rgb-combine", 4),
  RGB_SPLIT("rgb-split", 4),
  HORIZONTAL_FLIP("horizontal-flip", 2),
  BLUR("blur", 2),
  VERTICAL_FLIP("vertical-flip", 2),
  RUN_SCRIPT("run", 1),
  LUMA_COMPONENT("luma-component", 2),
  INTENSITY_COMPONENT("intensity-component", 2),
  COLOR_CORRECT("color-correct", 2),
  LEVEL_ADJUST("levels-adjust", 5),
  HISTOGRAM("histogram", 2),
  COMPRESS("compress", 3);

  private final String command;

  private final int requiredArgs;

  /**
   * Constructs a Command enum value with the specified command string.
   *
   * @param command The command string associated with the enum value.
   * @param requiredArgs required number of arguments to perform this command.
   */
  private Command(String command, int requiredArgs) {
    this.command = command;
    this.requiredArgs = requiredArgs;
  }

  /**
   * Get the command string associated with this enum value.
   *
   * @return The command string.
   */
  public String command() {
    return command;
  }

  /**
   * Get the required arguments string associated with this enum value.
   *
   * @return The number of required args.
   */
  public int requiredArgs() {
    return requiredArgs;
  }
}
