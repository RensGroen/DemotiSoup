# DemotiSoup

DemotiSoup is a project still work in progress. The idea is that a user can control demotica in a generic way. 
This can be achieved by so called 'modules'. A module can be something like a RGB light, a camera or something else. 
Basically anything for which there is a need to control remotely, or retrieve data from.

As a starter, I am working on the RGB light module. Currently I am experimenting with code for the Wipy to control a LED strip.
The Wipy will register at the server (DemotiServer project). The user can then add a RGB module in his angular 2 app (DemotiApp project).
The server will give the app all the registered Wipy's (or any other mini computer for that matter) which the user is then allowed to control.

The idea here is that the user only has to install the code in his network, with the necessary devices. No configuration needed after that.

Anyone that would like to contribute, is welcome.
