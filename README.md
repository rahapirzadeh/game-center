# Game Center
## CPSC 353 - Jonathan Bahm, Brandon Fabre, Raha Pirzadeh

#### How to run
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

#### Raha
Rock, Paper, Scissors code allows players to..
* Play through multiple rounds
* Switch between turns
* Gain points for wins

Next sprint will include..
* Networking features

Contributions:
* RPS.java
* Portion of networking code

Next sprint will include networking features

#### Jonathan
Right now, tik tac toe
* Displays the board
* Allows player input

Next sprint will include..
* Working win conditions
* Proper networking features

Contributions:
* TicTacToe.java
* Portion of networking code

#### Brandon
Currently, users can..
* Play through multiple rounds
* Receive game statistics
* Gain points for correct guesses

Next sprint should include..
* Two-player functionality
* Help and give up commands
* Increased commenting and code refactoring

Contributions:
* Dockerfile
* Hangman.java and helper files associated with it
* Portion of networking code

References:
* https://stackoverflow.com/questions/24305830/java-auto-increment-id
* https://programming.guide/java/remove-trailing-comma-from-comma-separated-string.html
* https://github.com/ChapmanCPSC353/mtchat
