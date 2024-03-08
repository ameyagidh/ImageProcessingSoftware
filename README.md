# GRIME: Graphical Image Manipulation and Enhancement (PixelPulse)

## Problem statement

This is built on top of [More Image Manipulation and Enhancement](#CS5010-More-image-manipulation-and-enhancement)

New requirements were:

* The graphical user interface (GUI) is built using Java Swing.
* The GUI shows the image that is currently being worked on. The image may be bigger than
  the area allocated to it in your graphical user interface. In this case, the user is able
  to scroll the image. Any changes to the current image as a result of the image operations are
  visible in the GUI.
* The histogram of the visible image is visible as a line chart on the screen at all times. If the
  image is manipulated, the histogram should automatically refresh. The histogram should show the
  red, green, blue and intensity components.
* The user interface exposes all the required features of the program that are: flips, component
  visualization, greyscale, blurring, sharpening , sepia, color correction, level adjustment, and
  compression.
* When saving an image as a **PNG/PPM/JPG**, it saves what the user is currently seeing.
* The user is able to specify suitably the image to be loaded and saved that the user is
  going to process. That is, the program cannot assume a hardcoded file or folder to load and save.
* Any error conditions are suitably displayed to the user as they occur, through pop-up messages.
* The split view functionality for blur, sharpen, sepia, greyscale, color correction and levels
  adjustment allows user to look at a preview before applying the changes, and then if the user is
  satisfied with the results, the user can click on "apply" to make changes in the image.
* The layout of the UI should be reasonable. Things should be in proper proportion, and laid out
  in a reasonable manner.

## New implementations

### View

This application offers a Graphical User Interface (GUI) that empowers users to manipulate and enhance their images. 
In its current version, the application incorporates the following features:

* Loading and saving images in formats PPM, PNG, JPG
* Brighten, Darken, Horizontal Flip, Vertical Flip
* Greyscale, Sepia, Blur, Sharpen, Component extraction
* Level Adjustment, Compression, Color correction
* Live histogram of the image
* A preview available as a split view for the following functionalities: blur, sharpen, sepia,
  greyscale, color correction and levels adjustment

The View component `ImageManipulatorView` is a class that extends the JFrame class from Java Swing and incorporates a variety of Swing components to 
construct the graphical user interface (GUI). Here is a concise overview of the Swing components utilized:

* `JMenuBar`: This component serves as a menu bar, capable of containing menus, each of which can, in turn, contain menu items.
* `JMenu`: Representing a menu component, it can house menu items or even sub-menus.
* `JMenuItem`: Functioning as a clickable menu item, it triggers a specific action upon user interaction.
* `JPanel`: As a container component, it accommodates other components and can utilize a layout manager.
* `GridBagLayout`:  This layout manager organizes components in a grid of cells, each cell having adjustable sizes.
* `BoxLayout`: Offering flexibility, this layout manager organizes components either vertically or horizontally.
* `BorderFactory`: A factory class that simplifies the creation of borders for components.
* `JScrollPane`: This component provides scrollbars for larger components, ensuring visibility within a defined area.
* `JLabel`: As a display component, it presents either text or images.
* `JButton`: A clickable component that triggers a specified action.
* `JTextField`: Enabling user text input, this component allows the entry of textual information.
* `JComboBox`: Facilitating item selection, this component allows users to choose from a list of items.
* `TitledBorder`: This border type includes a title, enhancing the visual representation of a component.
* `Insets`: This class specifies margins for a component, influencing its spacing and layout.

The utilization of `GridBagLayout`, `BoxLayout`, and `BorderLayout` is driven by the objective of crafting a versatile and responsive user interface. 
These layout managers offer flexibility and dynamism, enabling the interface to adapt seamlessly to diverse screen sizes and window dimensions.

The view offers various operations that the controller can invoke, including:

1. Refreshing the screen when new changes are made.
2. Presenting an error message to the user regarding any operation.
3. Granting access to the supported features of our program to the view.

### View Model.

The current design incorporates a ViewModel, leveraging the power of composition through an adapter. 
Now, when the view requires representing updated data, it can directly query the model. 
The new ViewModel serves as an adapter to our main model, offering methods tailored to benefit the view.

This updated ViewModel reads the old model and furnishes processed data as required by the view. 
It introduces a valuable methods—providing image which is essential for the model's presentation.

The extensibility of this implementation is evident, allowing easy extension or the creation of a new adapter 
using its interface to cater to future requirements of the view.

## Design changes

No modifications were made to our existing code; none of the existing code was altered.

### Screenshot of the program with a loaded image

![Screenshot](https://i.imgur.com/XamcPbv.png)

### Updated Class Diagram

![GRIME Class Diagram](https://i.imgur.com/nyE0YXJ.png)

##### All the previously supported methods remain intact, thanks to the principles of Object-Oriented Programming (OOP) and the Model-View-Controller (MVC) pattern.

Read below for the documentation of previous version.

# CS5010-More-image-manipulation-and-enhancement

## Description

This is built on top of [Image manipulation and enhancement](#cs5010-image-manipulation-and-enhancement)

New requirements were:

* **Compress Images:** Support the ability to create a compression version of an image. This must be supported by the 
script command "compress percentage image-name dest-image-name". Percentages between 0 and 100 are considered valid.
* **Histogram:** Support the ability to produce an image that represents the histogram of a given image. The size 
of this image should be 256x256. It should contain the histograms for the red, green and blue channels as line graphs.
* **Color correct:** Support the ability to color-correct an image by aligning the meaningful peaks of its histogram
* **Levels adjust:** Support the ability to adjust levels of an image. This must be supported by the script command 
"levels-adjust b m w image-name dest-image-name" where b, m and w are the three relevant black, mid and white values respectively. These values should be ascending in that order, and should be within 0 and 255 for this command to work correctly.
* **Split view:** Support the ability to specify a vertical line to generate a split view of operations. The operations that must support this are blur, sharpen, sepia, greyscale, color correction and levels adjustment. The script commands for these operations must accommodate an optional parameter for the placement of the splitting line. For example, blur can be done by "blur image-name dest-image-name" or "blur image-name dest-image split p" in that order where 'p' is a percentage of the width (e.g. 50 means place the line halfway through the width of the image). The output image should show only the relevant part suitably transformed, with the original image in the remaining part.
* **File argument:** Support the ability to accept a script file as a command-line option. For example "-file
  name-of-script.txt". If a valid file is provided, the program should run the script and exit. If
  the program is run without any command line options, then it should allow interactive entry of
  script commands as before.
* Retain support for all previous operations and script commands.

We have advanced our implementation in accordance with Object-Oriented Programming (OOP) and Model-View-Controller (MVC) principles, incorporating enhancements based on new feature set. 
The modifications made to our existing code will be elucidated and justified below. All the features are completed.

## New implementations

### Supporting new operations.

* To accommodate the introduction of new operations, we devised an interface named `ExtendedImageProcessor` that extends the existing `ImageProcessor`. This extension encompasses all functionalities present in the original version.
* A corresponding implementation of this interface, named `ExtendedImageProcessorImpl`, was developed. In this process, we extended the `ImageProcessorImpl` class to incorporate the new interface.
* The interface introduced new method signatures, including `color-correct`, `compress`, `histogram`, and `levels-adjust`. These methods require the name of the existing image and specify the name under which the resulting image should be stored.
* We also enhanced the system to support `split view operations` for commands such as `blur`, `sharpen`, `sepia`, `greyscale`, `color correction`, and `levels adjustment`. Users can activate this feature by including the `split` keyword and specifying the percentage of the width in the script.
* We added the new version of our 
* To adapt to these changes, our `ImageController` now accepts this new model as an argument. This adjustment is crucial as the program's interface is evolving, introducing novel operations. This update ensures the smooth integration of these changes into the existing system.

### Model

New classes added :

- `ImageModelV2`: Created this interface to add `compress`, `color-correct` and `levels-adjust` operation on image.
- `RGBImageV2`: This class implements the `ImageModelV2` interface, and it implements
  the logic to preform the compress, color-correct and levels-adjust operations with given parameter value.

## Controller

New classes added :

- `Compress`: We introduced a new class to facilitate the interaction between the controller and the model, given that the provider utilizes a command design pattern.
- `LevelAdjust`: We introduced a new class to facilitate the interaction between the controller and the model, given that the provider utilizes a command design pattern.
- `ColorCorrect`: We introduced a new class to facilitate the interaction between the controller and the model, given that the provider utilizes a command design pattern.
- `Histogram`: We introduced a new class to facilitate the interaction between the controller and the model, given that the provider utilizes a command design pattern.

Changes in old classes :

- `MessageUtil`: Added message for invalid number of arguments.
- `Command`: Added `COMPRESS`, `LEVELADJUST`, `HISTOGRAM` and `COLORCORRECT` enums to perform validation for script command for new operations.
- `ImageController`: Added a new commands to the map and also updated the code to work with the new model `ExtendedImageProcessor`.
- Updated previous classes for `Blur`, `Sharpen`, `LumaComponent`, `IntensityComponent`, `ValueComponent` and `Sepia` to work with split view operations.


## Main class

- `SimpleImageController`: The model has been updated from `ImageProcessor` to `ExtendedImageProcessor`. 
We also added the `getController` method. The controller, based on the provided arguments, will determine the approach for executing the operations.

Read below for the documentation of previous version.

# CS5010-Image-manipulation-and-enhancement

## Description:

This Java-based Image Manipulation Application allows users to perform a variety of operations on
images, including
loading, saving, and applying various image processing techniques. The application provides a
user-friendly interface
for image manipulation and transformation.

## Features:

* **Load Images:** Easily load images in various formats (e.g., PPM, JPEG, PNG) into the
  application.
* **Save Images:** Save the current state of an image as an ImageModel in a specified format.
* **Color Transformations:** Apply color transformation matrices to change the colors of images.
* **Greyscale Conversion:** Convert images to greyscale using different components (e.g., Value,
  Intensity, Luma).
* **Component extraction:** Convert images to a specific component. (e.g., RED,
    GREEN, BLUE).
* **Brightness Adjustment:** Adjust pixel values to brighten or darken images.
* **Filtering:** Apply custom filters to images for effects like blurring, sharpening, and edge
  detection.
* **Flipping:** Perform horizontal or vertical flips on images.
* **RGB Component Splitting:** Split RGB images into their red, green, and blue components.
* **RGB Component Combining:** Combine red, green, and blue images to create a new RGB image.

## Installation:

1. Clone this repository to your local machine:
   `git clone https://github.com/Darshan1510/CS5010-Image-manipulation-and-enhancement.git`

2. Build the project using your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

3. Run the application.

## Design

We're using the MVC Pattern for our design:

1. The **'Model'** is where our data and its related actions live.
2. The **'Controller'** is like a manager; it talks to the user, the model,
   and the view. Our controller uses the *Command Design* to work smoothly.
3. Right now, we don't have a specific 'View'. The user just gives commands,
   and the controller handles it using the *Command design*.

![ime - MVC Design](res/class_diagram.png)

## Package `ime`

This is our main package, that implements the given problem in MVC design.

* Package `model` -> Defines our model, which represents an Image processor that will also store
  the processed images in a session.
* Package `controller` -> contains our controller, which interacts with the model using the
  **Command Design Pattern**.
* Currently, we don't have a dedicated view, but as the application grows and requires more user
  interaction,
  we'll introduce a separate view package.

### Model

Our primary model functions as an Image Processor. It offers various methods to work on multiple
images during
a session and stores them.

* The `ImageProcessorImpl` class is the practical application of the `ImageProcessor` interface,
  ensuring operations align with the problem's requirements and serving as the sole model recognized
  by the controller.
    * This class facilitates all necessary interactions between the user and controller.
    * It represents the primary interface of the model.
    * Additionally, this main model utilizes the `ImageHelper` from the helpers package to manage
      various image
      types and formats for both loading and saving purposes.

#### Package `image`

The `ImageModel` interface defines the properties and methods of an image with customizable pixels
based on different
channels. All image implementations adhere to or extend this interface.

* `RGBPixel` is a class representing a Pixel with red, green, and a blue value it can carry.
    * This class is derived from the `PixelModel` interface. For future pixel variations, like
      ARGBPixel with
      transparency, they can easily branch out from this interface and class.

Such pixels are employed by classes that implement the `ImageModel` interface.

* `ImageModel` interface provides a generic image, represented in terms of pixels. It offers basic
  setters and getters. It offers a range of filters that can be applied on the image.
* The `RGBImage` class showcases an RGB image composed of `RGBPixel` units. Essential attributes
  include dimensions
  (height and width) and the maximum value a pixel component can possess. Additionally, it has the
  capability to apply
  various filters to itself and return the newer version of the image.

### Controller

The controller in our setup adopts the **"Command Design Pattern"**, aligning seamlessly with the
program's needs.

Commands such as load, brighten, red-component, green-component, blue-component, save, run, luma,
value, intensity,
blur, sharpen, rgb-split, rgb-combine and flips (both vertical and horizontal) are treated as
individual commands.
Considering the diverse range of commands, we've established an enum to catalog them.

Each of these commands adheres to the `ImageProcessorCommand` interface.

The controller here, follows **"Command Design Pattern"** as it meets the program requirements
perfectly.

Interface `ImageControllerInterface` and its implementation `ImageController`.

* The `ImageController` serves as our primary controller, implementing
  the `ImageControllerInterface` interface.
  This interface outlines the "execute" method, which initiates the program.

#### Package `commands`

All our commands for the controller following Command Design pattern is defined here.

* Interface `ImageProcessorCommand`, defines the type of the command, it has only one method to
  offer,
  `process` that processes the respective command.

**Following classes each act as a command:**

* `Brighten` - brightens an image with the scale provided.
* `VerticalFlip` - flips the image with the vertical orientation.
* `HorizontalFlip` - flips the image with the horizontal orientation.
* `BlueComponent` - converts an input image to greyscale while preserving the blue component.
* `RedComponent` - converts an input image to greyscale while preserving the red component.
* `GreenComponent` - converts an input image to greyscale while preserving the green component.
* `ValueComponent` - converts an input image to greyscale while applying the maximum value of the
  three component for
  each pixel.
* `LumaComponent` - converts an input image to greyscale while applying the weighted sum using
  `(r'=g'=b'=0.2126r + 0.7152g + 0.0722b)` to each pixel.
* `IntensityComponent` - converts an input image to greyscale while applying the average of the
  three components
  to each pixel.
* `Load` - helps load a new image into the model.
* `Save` - helps save a new image into disk.
* `RGBCombine` - combines red, green, blue channels of 3 different (generally greyscale) images
  respectively.
* `RGBSplit` - Splits an image into its individual red, green, and blue greyscale components and
  stores them with
  the assigned names in memory.
* `Run` - Executes a script containing various commands when given the correct script file path.
* `Blur` - blurs the image using 3*3 kernel.
* `Sharpen` - sharpens the image (sharpens the accentuates edges) using 5*5 kernel.
* `Sepia` - Applies the Sepia color transformation to the image.

### Helpers

A set of tools used by our image manipulation program.
This helper as of now provides the functionality, to read any file, to read an image files of
particular type, which is
easy to extend again.

#### Package `image`

Our program utilizes `ImageHelperFactory` and its implementation `ImageHelperFactoryImpl` to
determine the appropriate
class for image IO operations. The `GenericImageHelper` manages reading and saving in **jpg, jpeg,
and png formats**,
while the `PPMImageHelper` specifically handles reading and saving in ppm format. Both these classes
adhere to the
`ImageHelper` interface.

#### Package `file`

The `FileHelper` interface and its corresponding implementation, `FileHelperImpl`, facilitate file
reading based on
the provided file path.

### Running the program.

Navigate to *src -> ime -> controller ->  `SimpleImageController`*  and execute the
static main method.
This action initializes a `ImageController` object and triggers the program by invoking
the `execute` method of the
controller.

#### Accepted commands by the program.

###### Loads a new image named paris-test.png

```
load res/paris-test.png paris
```

###### Stores the output image under the given name

```
save res/paris-save.png paris
```

###### Boosts the brightness of the loaded paris image by a factor of 50

```
brighten 50 paris paris-brighter
```

###### Stores the output image under the given name

```
save res/paris-brighter-50.png paris-brighter
```

###### Darkens the loaded paris image by a factor of 50

```
brighten -50 paris paris-darken
```

###### Stores the output image under the given name

```
save res/paris-darken-50.png paris-darken
```

###### Vertically flips the paris image

```
vertical-flip paris paris-vertical
```

###### Stores the output image under the given name

```
save res/paris-vertical.png paris-vertical
```

###### Horizontally flips the paris-vertical image

```
horizontal-flip paris-vertical paris-vertical-horizontal
```

###### Stores the output image under the given name

```
save res/paris-vertical-horizontal.png paris-vertical-horizontal
```

###### Generates a component image based on the red component

```
red-component paris paris-red
```

###### Stores the output image under the given name

```
save res/paris-red.png paris-red
```

###### Generates a component image based on the green component

```
green-component paris paris-green
```

###### Stores the output image under the given name

```
save res/paris-green.png paris-green
```

###### Generates a greyscale image based on the blue component

```
blue-component paris paris-blue
```

###### Stores the output image under the given name

```
save res/paris-blue.png paris-blue
```

###### Generates a greyscale image based on the value component

```
value-component paris paris-value
```

###### Stores the output image under the given name

```
save res/paris-value.png paris-value
```

###### Generates a greyscale image based on the luma component

```
luma-component paris paris-luma
```

###### Stores the output image under the given name

```
save res/paris-luma.png paris-luma
```

###### Generates a greyscale image based on the intensity component

```
intensity-component paris paris-intensity
```

###### Stores the output image under the given name

```
save res/paris-intensity.png paris-intensity
```

###### Divides an image into its individual red, green, and blue components.

```
rgb-split paris paris-red paris-green paris-blue
```

###### Stores the output image under the given name

```
save res/paris-split-red.png paris-red
save res/paris-split-green.png paris-green
save res/paris-split-blue.png paris-blue
```

###### Merges three component images using their red, green, and blue components.

```
rgb-combine paris-combined paris-red paris-green paris-blue
```

###### Stores the output image under the given name

```
save res/paris-combined.png paris-combined
```

###### Runs commands from a provided script file path

###### Please provide this script file as command line argument for running a script - res/script-test.txt

Any or all of the above valid commands can execute successfully here.

```
run res/script.txt
```

#### Exception handling

We handle errors by catching any misspelled commands or invalid arguments. When detected, we provide
helpful feedback
to the user.

### Image Citation:

> Paris image
>
> ![Paris](https://i.imgur.com/ByJo3Xz.jpeg)


Author: Darshan Jayeshbhai Shah

Date: November 1, 2023.

Source: [Imgur](https://imgur.com).

Terms of usage: [Imgur terms](https://imgur.com/tos)