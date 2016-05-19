# NXT Project 

Integrating Java, which gets inputs (i.e. controllers), with Python through TCP and Python with NXT through Bluetooth.
Inputs (i.e. controllers) -> Java <-- TCP --> Python <-- Bluetooth --> NXT (Brick) 

Python runs the TCP server:

    (Note: Only Python 2.7 currently supported)  

    Dependencies: 

        [NXT-Python:](https://github.com/Eelviny/nxt-python)

        (IMPORTANT: other libraries needed are listed on the above GitHub page's readme)


Java runs the TCP client: 

    Dependencies: 

        [libGDX:](http://libgdx.badlogicgames.com)
        
        [Note: A COMPILED JAR FOR CONTROLLER INPUT [i.e. XBox Controller] IS AVAILABLE HERE:] 
        
        (https://www.dropbox.com/s/4jykgd9wgxueub9/tcpclient.zip)
