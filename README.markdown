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

Client
------
This is a multiplayer mode designed to place the workload on a single machine. Once a player on the server enters a game you can start the client program and join in. At this time only four clients are able to connect to the server.

RemoteViewer
------------
This is a little tool that will simply look in on a game. It's primary use was for testing networking synchronization code. The host you wish to connect the remote visualizer to must be already running before starting the Remote Visualizer. By default it is set up to monitor the localhost, on the engines default port. To have it connect to a remote machine or another port you will need to modify the RemoteVisualizer.java. The Remote Visualizer uses an unmodified subset of the engine for networking, logging, event management, and rendering.

Other Information
-----------------
To modify the game simply modify any of the files in games/csc481 (or add you're own files to the directory). For any folder in the games directory the file that shares it's name serves as a directory listing of sorts, determining which levels get loaded in what order. If that file is missing the levels will be loaded into the game in the order of Java's choosing. Details for building your own levels can be found in the comments of the levels provided.

Known Issues
------------
* Any lag introduced by network connections is maintained after connection drops
* Player names must be unique or another use can connect using the same name and events will mix with each other
* ConcurrentModificationException thrown with connection of fifth client
* Games chosen in Peer-2-Peer mode must have same number of levels
