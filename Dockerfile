FROM openjdk:14

COPY . /usr/src/game-center/
WORKDIR /usr/src/game-center/

RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/networking/Server.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/networking/Client.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/networking/ClientListener.java

RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/games/Hangman.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/games/RPS.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/games/TicTacToe.java

RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/helpers/Turn.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/helpers/Player.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/helpers/Game.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/helpers/FileIO.java
RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/helpers/StringManipulation.java