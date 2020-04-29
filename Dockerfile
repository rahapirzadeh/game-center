FROM openjdk:11

COPY . /usr/src/game-center/
WORKDIR /usr/src/game-center/

RUN javac -d /usr/src/game-center/classFiles /usr/src/game-center/networking/Server.java
CMD ["java", "-cp", "/usr/src/game-center/classFiles/", "networking.Server"]