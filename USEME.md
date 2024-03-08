## Graphical User interface of the application and functionalities.

#### To run the GUI

* Open terminal/cmd at the location of jar file : `res` ->
  `ImageManipulator.jar`<br>
* Run `java -jar ImageManipulator.jar`

The above step opens graphical user interface for image processing and manipulation.

#### Method 2. To run the project in IDE or code.

* In `src` -> `ime` -> `controller`
* Open file `SimpleImageController.java`
* Run the `public static void main(String[] args)` method

### Features

- Load and save image files in JPG, PNG, and PPM formats.
- Basic image manipulation operations such as adjusting brightness, sepia, compression, color corrections, 
horizontal and vertical flips.
- RGB split and combine operations.
- Color Transformation and Filter operations, such as greyscale conversion, component extraction, and filter such as blur, sharpen.
- Level adjust operation for adjusting the level of the image with black, mid and white values.
- Displaying live histogram of the currently shown image.

### Usage

1. To load an image, click on File > Open, select an image file from the file dialog, and click
   Open.
2. The loaded image will be displayed in the right panel, and its histogram in the left panel at the bottom.
3. To manipulate the image, use the buttons in the Basic Operations panel, or the dropdowns in
   the Color transform and filter panel or level adjust operation panel.
4. To save the manipulated image, click on File > Save As, and choose a destination and format for
   the file.
5. To exit the application, click on File > Exit.

Please refer the screenshot below for more details.

![Screenshot](https://i.imgur.com/XamcPbv.png)

#### Image View Panel

The right-side image view panel in our application dynamically displays the loaded image and 
continuously updates in real-time as users make edits.

#### Basic Operations and Level adjust operations

![Basic Operations & Level Adjust](https://i.imgur.com/DYbf30l.png)
The basic operations panel facilitates image editing through fundamental operations. The brightness adjustment value should be entered numerically into the text box. 
Clicking the button brightens (for positive values) or darkens (for negative values) the image accordingly. 
Horizontal and vertical flips are executed by clicking their respective buttons. 
The panel also supports additional operations such as sepia and color correction.
Users can enter the desired compression percentage in the text field. Clicking the button initiates 
the compression operation, reducing the image file size based on the specified percentage.

Furthermore, the RGB Split and RGB Combine functionalities enable users to split an image or 
combine three images of the same dimensions. For splitting, users input three distinct file locations 
to specify where to save the split images. Conversely, for combining, users must select three different 
file types for merging. Failure to comply with these requirements will result in an error.

The Level Adjust panel provides a dedicated space for fine-tuning the levels of the input image. Users can input specific values for black, mid, and white in their respective text fields. 
Upon entering these values, users can click the "Level Adjust" button to execute the adjustment operation. 
This operation modifies the black, mid, and white levels of the image, allowing users to customize the visual balance according to their preferences.

#### Image Histogram Panel

The histogram panel, situated at the bottom-left of our application, provides a real-time display 
of the image's live histogram.

#### Color Transform and filter operations

![Color transform and filter Operations](https://i.imgur.com/YpvIUBQ.png)

The color transformation and filter operations panel supports the greyscale variants (Value, Luma,
Intensity), components (Red, Green, Blue) and filters (blur and sharpen). To apply operations, choose the options from the
dropdown menu and click the execute button for respective operations.

#### Split preview

![Split preview](https://i.imgur.com/9qooOQe.png)

Certain operations support a split preview feature. When performing an operation with split preview capability, the split preview panel becomes visible in the top-left corner. 
Users can input the percentage of the image to be split and click the "View" button to preview the applied operation. 
The user has the option to either apply the operation to the entire image using the "Apply" button or cancel the current operation, reverting the image back to its original state.

###### Supported operations by split preview
* Blur
* Sharpen
* Sepia
* Greyscale - Luma, Value, Intensity
* Color correction
* Level Adjustment

## Commands that are supported by our application and its usage.

### To run the script file in command-line

* Open terminal/cmd at the location of jar file : `res` ->
  `ImageManipulator.jar`<br>
* Run `java -jar ImageManipulator.jar -file scriptV1.txt`

The above step runs all the commands that are supported by the applications and operates
on the paris image and saves all the resulting images in the res folder.

### Running the project interactively using text commands.

#### Method 1. To run the project from jar file.

* open terminal/cmd at location where the jar file is present : `res` ->
  `ImageManipulator.jar`<br>
* Run `java -jar ImageManipulator.jar -text`

##### Command to run a set of commands in a file.

```
run scriptV1.txt
```

When executing the script mentioned earlier, all commands are executed, mirroring the previous 
demonstration. However, this time, the script runs interactively within the program.

##### Commands to `load` a file.

```
load paris.png paris
```

##### Commands to `save` a file.

```
save paris.png paris-png
```

##### Commands to `rgb-split` the file `paris.png`.And save the file in any of the supportedformats.

```
rgb-split paris-ppm paris-red paris-green paris-blue
save paris-green-split.jpg paris-green
```

##### Commands to `rgb-combine` the three red, green, and blue image. And save the file in any of the supported formats.

```
rgb-combine paris-combine paris-red paris-green paris-blue
save paris-combine.png paris-tint
```

##### Commands to `brighten` the file `paris.ppm`. And save the file in any of the supported formats.

```
brighten 50 paris paris-brighten
save paris-brighten.png paris-brighten
```

```
brighten -50 paris paris-darken
save paris-darken.png paris-darken
```

##### Commands to `greyscale` the file `paris.png` into all component. And save the file in any of the supported formats.

```
value-component paris paris-value
save paris-value.png paris-value

luma-component paris paris-luma
save paris-luma.jpg paris-luma

intensity-component paris paris-intensity
save paris-intensity.png paris-intensity

```

##### Commands to apply `component` (Red, Green and Blue) the file `paris.png`. And save the file in any of the supported formats.

```

red-component paris paris-red
save paris-red.ppm paris-red

green-component paris paris-green
save paris-green.png paris-green

blue-component paris paris-blue
save paris-blue.jpg paris-blue
```

##### Commands to `flip` (Both vertically and horizontally) the file `paris.png`. And save the file in any of the supported formats.

```
horizontal-flip paris paris-horizontal
save paris-horizontal.png paris-horizontal

vertical-flip paris-horizontal paris-horizontal-vertical
save paris-horizontal-vertical.png paris-horizontal-vertical

vertical-flip paris paris-vertical
save paris-vertical.jpg paris-vertical
```

##### Commands to `blur` the file `paris.png`. And save the file in any of the supported formats.

```
blur paris paris-blur
save paris.ppm paris-blur
```

##### Commands to `sharpen` the file `paris.png`. And save the file in any of the supported formats.

```
sharpen paris paris-sharpen
save paris-sharpen.png paris-sharpen
```

##### Commands to apply color transformations to create `sepia-tone` of the file `paris.jpg`. And save the file in any of the supported formats.

```
sepia paris paris-sepia
save paris-sepia.jpg paris-sepia
```

##### Commands to `compress` the file `paris.png`. And save the file in any of the supported formats.

```
compress 50 paris paris-50-compress
compress 90 paris paris-90-compress
save paris-50-compress.png paris-50-compress
save paris-90-compress.png paris-90-compress
```

##### Commands to `color-correct` the file `paris.png`. And save the file in any of the supported formats.

```
color-correct paris paris-cc
save paris-cc.png paris-cc
```


##### Commands to `levels-adjust` the file `paris.png`. And save the file in any of the supported formats.

```
levels-adjust 20 120 255 paris paris-adjust-1
levels-adjust 30 100 240 paris paris-adjust-2
save paris-adjust-1.png paris-adjust-1
save paris-adjust-2.png paris-adjust-2
```

##### Commands to perform `split-view-operations` the file `paris.png`. And save the file in any of the supported formats.

```
sepia paris paris-sepia-split split 50
sharpen paris paris-sharpen-split split 30
save paris-sepia-split.png paris-sepia-split
save paris-sharpen-split.png paris-sharpen-split
```

##### Commands to get histogram of every image generated from `paris.jpg`. And save the file in any of the supported formats.

```
histogram paris paris-histo
save paris-histo.png paris-histo

histogram paris-cc paris-cc-histo
save paris-cc-histo.png paris-cc-histo

histogram paris-adjust-1 paris-adjust-1-histo
save paris-adjust-1-histo.png paris-adjust-1-histo
```

