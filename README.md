# NXT Project 

The purpose of this project is to control a robot with a Xbox controller. However, the input can come from anything and can be translated into self defined commands for the robot.

Integrating Java, which gets inputs (i.e. controllers), with Python through TCP and Python with NXT through Bluetooth.
Inputs (i.e. controllers) -> Java <-- TCP --> Python <-- Bluetooth --> NXT (Brick) 

Python runs the TCP server:

    (Note: Only Python 2.7 currently supported)  

    Dependencies: 

        NXT-Python: https://github.com/Eelviny/nxt-python

        (IMPORTANT: other libraries needed are listed on the above GitHub page's readme)


Java runs the TCP client: 

    Dependencies: 

        libGDX: http://libgdx.badlogicgames.com
        
        (Note: A COMPILED JAR FOR CONTROLLER INPUT [i.e. XBox Controller] IS AVAILABLE HERE: https://www.dropbox.com/s/4jykgd9wgxueub9/tcpclient.zip)
Note: Currently only two motors and their corresponding "commands" are hard coded in. The commands are user defined and can be changed in the TCP server, but the TCP client must be compiled to send such user defined commands as one wishes.

https://en.wikipedia.org/wiki/Lego_Mindstorms_NXT
