package ime.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import ime.controller.commands.BlueComponent;
import ime.controller.commands.Blur;
import ime.controller.commands.Brighten;
import ime.controller.commands.ColorCorrect;
import ime.controller.commands.Compress;
import ime.controller.commands.GreenComponent;
import ime.controller.commands.Histogram;
import ime.controller.commands.HorizontalFlip;
import ime.controller.commands.ImageProcessorCommand;
import ime.controller.commands.IntensityComponent;
import ime.controller.commands.LevelAdjust;
import ime.controller.commands.Load;
import ime.controller.commands.LumaComponent;
import ime.controller.commands.RGBCombine;
import ime.controller.commands.RGBSplit;
import ime.controller.commands.RedComponent;
import ime.controller.commands.RunScript;
import ime.controller.commands.Save;
import ime.controller.commands.Sepia;
import ime.controller.commands.Sharpen;
import ime.controller.commands.ValueComponent;
import ime.controller.commands.VerticalFlip;
import ime.controller.enums.Command;
import ime.controller.utils.ScriptParser;
import ime.model.ExtendedImageProcessor;

/**
 * Responsible for handling image manipulation commands provided through user input.
 * It utilizes a Scanner to read commands, maps these commands to specific ImageProcessorCommand
 * functions, and applies them to an ImageProcessor object. The available commands and their
 * associated functions are defined in the getImageProcessorCommand() method.
 */
public class ImageController implements ImageControllerInterface {
  protected Readable in;
  protected Appendable out;

  /**
   * Constructor for ImageController.
   *
   * @param in  A Readable source for user input.
   * @param out An Appendable destination for program output.
   */
  public ImageController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  /**
   * This method creates and populates a map of commands to their corresponding
   * ImageProcessorCommand functions.
   *
   * @return A map containing command strings and associated functions.
   */
  protected static Map<String, Function<String[],
          ImageProcessorCommand>> getImageProcessorCommand() {
    Map<String, Function<String[], ImageProcessorCommand>> knownCommands = new HashMap<>();

    knownCommands.put(Command.BLUE_COMPONENT.command(), BlueComponent::apply);
    knownCommands.put(Command.LUMA_COMPONENT.command(), LumaComponent::apply);
    knownCommands.put(Command.RED_COMPONENT.command(), RedComponent::apply);
    knownCommands.put(Command.GREEN_COMPONENT.command(), GreenComponent::apply);
    knownCommands.put(Command.SAVE.command(), Save::apply);
    knownCommands.put(Command.LOAD.command(), Load::apply);
    knownCommands.put(Command.INTENSITY_COMPONENT.command(), IntensityComponent::apply);
    knownCommands.put(Command.VALUE_COMPONENT.command(), ValueComponent::apply);
    knownCommands.put(Command.SEPIA.command(), Sepia::apply);
    knownCommands.put(Command.SHARPEN.command(), Sharpen::apply);
    knownCommands.put(Command.BRIGHTEN.command(), Brighten::apply);
    knownCommands.put(Command.HORIZONTAL_FLIP.command(), HorizontalFlip::apply);
    knownCommands.put(Command.VERTICAL_FLIP.command(), VerticalFlip::apply);
    knownCommands.put(Command.RUN_SCRIPT.command(), RunScript::apply);
    knownCommands.put(Command.BLUR.command(), Blur::apply);
    knownCommands.put(Command.RGB_COMBINE.command(), RGBCombine::apply);
    knownCommands.put(Command.RGB_SPLIT.command(), RGBSplit::apply);
    knownCommands.put(Command.LEVEL_ADJUST.command(), LevelAdjust::apply);
    knownCommands.put(Command.COLOR_CORRECT.command(), ColorCorrect::apply);
    knownCommands.put(Command.HISTOGRAM.command(), Histogram::apply);
    knownCommands.put(Command.COMPRESS.command(), Compress::apply);

    return knownCommands;
  }

  /**
   * The execute() method reads user input, processes image manipulation commands,
   * and applies them to an ImageProcessor object until the user exits.
   */
  @Override
  public void execute(ExtendedImageProcessor imageProcessor) throws IOException {
    Objects.requireNonNull(imageProcessor);
    Scanner scan = new Scanner(in);

    Map<String, Function<String[], ImageProcessorCommand>> knownCommands
            = getImageProcessorCommand();

    while (scan.hasNextLine()) {
      try {
        ImageProcessorCommand c;
        String input = scan.nextLine();

        if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
          return;
        }

        String[] parsedScript = ScriptParser.parseScript(input);

        // Every script has at least 2 arguments including command.
        if (parsedScript.length < 2) {
          throw new InputMismatchException("Every script command must have " +
                  "2 arguments including command");
        }

        String command = parsedScript[0];
        Function<String[], ImageProcessorCommand> cmd =
                knownCommands.getOrDefault(command, null);

        String[] args = new String[parsedScript.length - 1];
        System.arraycopy(parsedScript, 1, args, 0, parsedScript.length - 1);

        if (cmd == null) {
          throw new IllegalArgumentException("Invalid command: " + input);
        } else {
          c = cmd.apply(args);
          c.process(imageProcessor);
          out.append("Command performed: ").append(input).append("\n");
        }
      } catch (IOException | IllegalArgumentException | InputMismatchException e) {
        this.out.append(e.getMessage()).append("\n");
      }
    }
  }
}
