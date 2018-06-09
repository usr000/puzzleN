# puzzleN
A (very) simple console implementation of Puzzle 15 in Java

Note: Generated board is not necessarily a solvable one. 

To build:
```
mvn clean package
```
To run:
```
java -jar ./target/<>.jar
```

Optionally a specific seed (java long value) can be supplied to start with a specific board. E.g.

```
java -jar ./target/<>.jar --seed 12345
```

To make a move, please enter the value of the Pile.

When game reaches a Winning condition it wil print a message and exit.
