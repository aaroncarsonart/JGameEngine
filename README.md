# JGameEngine
This is a game engine written in Java, designed to be efficient, cross platform, and extensible.  The initial application will be a 2D, top-down 16 bit rpg game.

I have worked hard creating a consistent graphics experience, and the performance can be seen by the output produced in the terminal by `TpsCounter`.  The game uses the `javax.Swing` libraries to try to access the underlying hardware graphics pipeline.  The primary speedups were achieved using `BufferedImages` to create my game graphics layers, but drawing them all to a `VolatileImage` before scaling that image to the display.  This saw immense speedup, with 577 redraws of the scaled composite image when updating the composite with 60 redraws a second of the 3 graphics layers at a maximized window.  (1500 redraws are achieved when leaving the pixel ratio at 1:1 and drawing the 512 x 288 image directly, unscaled). 