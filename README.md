# Game Center
###### CPSC 353 - Jonathan Bahm, Brandon Fabre, Raha Pirzadeh

This project allows users to host a game server complete with turn-based games like Hangman, Rock Paper Scissors, and Tic Tac Toe, and invite friends to connect for a simple yet fun two-player experience.

### Javadocs
To view the javadocs for this project which has complete documentation on all classes, enter the *javadocs* directory and open index.html in a new browser tab.

### How to build
Navigate to the game-center directory and build all .java files with
```
docker build -t game-center .
```

### Server/Host
In the same command prompt, run
```
java -cp /classFiles; Server {PORT}
```
**Make sure that whatever port the host uses is open via port-forwarding.**

### Clients
Then, in another command prompt, run Client.java with
```
java -cp /classFiles; Client {HOSTNAME} {PORT}
```
Open a separate command prompt and run another instance of Client.java, if you'd like to support both players of the game.
Once both players have connected, the host (whoever is running Server.java) must choose a game mode for the game to start. Then, play away!

### Notices:
* Server is overloaded with error messages if one client disconnects before Server is closed
* Dockerfile only builds the necessary java files, but does not run Client or Server

### References:
* https://stackoverflow.com/questions/24305830/java-auto-increment-id
* https://programming.guide/java/remove-trailing-comma-from-comma-separated-string.html
* https://github.com/ChapmanCPSC353/mtchat
* https://cs.lmu.edu/~ray/notes/javanetexamples/
