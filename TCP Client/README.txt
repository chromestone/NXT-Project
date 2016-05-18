Note that if you would like to edit the code in Eclipse (without errors and shenanigans), libGDX has a very weird way of making the project. It seems there is no way to ONLY get the latest jar without creating the latest jar first (please contact me if you figure out how). The best way is perhaps to first create a project with libGDX’s provided setup jar and dragging my source files in.

The code provided currently accepts joystick input. 

The code provided “registers” a controller when it is plugged in (I believe USB only) 

Instructions:
(First time) Run the jar; this will create the config file.
Specify the IP and port number in the config file.
Start up a TCP server on that IP and port number.
Run the jar again.
To get joystick inputs plug a controller in.