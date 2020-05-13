# Game Center
## CPSC 353 - Jonathan Bahm, Brandon Fabre, Raha Pirzadeh

### How to run
To run Server.java build and run the Dockerfile with
```
docker build -t game-center .
docker run game-center
```

Then, in two separate command prompts, compile and run Client.java with
```
cd networking/
javac Client.java
java Client
```
(Open separate command prompt and repeat steps)

Enter *localhost* for the server address to connect to

### References:
* https://stackoverflow.com/questions/24305830/java-auto-increment-id
* https://programming.guide/java/remove-trailing-comma-from-comma-separated-string.html
* https://github.com/ChapmanCPSC353/mtchat
* https://cs.lmu.edu/~ray/notes/javanetexamples/
