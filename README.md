# DemotiSoup

DemotiSoup is a project still work in progress. The idea is that a user can control demotica in a generic way. 
This can be achieved by so called 'modules'. A module can be something like a RGB light, a camera or something else. 
Basically anything for which there is a need to control remotely, or retrieve data from.

As a starter, I am working on the RGB light module. Currently I am experimenting with code for the Wipy to control a LED strip.
The Wipy will register at the server (DemotiServer project). The user immediately sees the module in his screen, given by the server, and is now able to control it.(DemotiApp project).


I will build a small framework in which modules can define events of their own, and register to others. This way all of the devices can communicate with each other. This can be configured by the user in the app on his/her phone. An example: If Camera detecs moving Then change color of RGB led to red.

Anyone who would like to contribute, is welcome.
