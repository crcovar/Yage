ProcessingEngine
================

This is the source code to the Game Engine I am building for N.C. State's CSC481 class. If you are in the class don't use it, build your own.

The main game loop is in ProcessingSketch.java so run that class file and you should be good to go.

Controls
--------
* Move left: A
* Move right: D
* Toggle replay: T
* Jump: space

How To Play
-----------
Race to the finish!

Other Information
-----------------
When you complete a level you will get to view a replay of your playthrough. You can toggle the speed between 1/2x, 1x, and 2x. After that the next level in the game will load.

To modify the game simply modify any of the files in games/csc481 (or add you're own files to the directory). For any folder in the games directory the file that shares it's name serves as a directory listing of sorts, determining which levels get loaded in what order. If that file is missing the levels will be loaded into the game in the order of Java's choosing. Details for building your own levels can be found in the comments of the levels provided.

Due to lack of menu system you cannot, as of yet, create your own game to be available along side csc481.

Known Bugs
----------
* Replay only runs for the first level. For each level after that replay is skipped
* Remote Visualizer doesn't update Player objects in the replays
* Exiting ProcessingSketch causes exception to be thrown in Remote Visualizer