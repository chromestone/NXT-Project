Code currently only recommended for Python 3+. 

Note that the code provided has been tested to run with Python 2.7 as well, but the required libraries may have problem.

So far I've tested NXT Python 2.2.2 on Mac with LightBlue with Python 2.7 which resulted in one syntax error and another runtime error. Luckily these were fixable. (I found the error and deleted the code lol)

Note: Current robot configuration is hard coded. It is a script though so it is recommend that you change where I’ve commented in all CAPS.

Here is the protocol:

For turning:
lr:<l_power>:<r_power>|

For both wheels:
a:<power>|

where power is [0, 100]
