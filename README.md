# Knight Moves
An application to find the shortest path of a knight to any point on a chess board
* This uses a zero indexed board, so the bottom left corner of the board is 0,0

## Prerequisites
* Java 8
* Gradle

## Running the application
* Run using gradle from the root directory of the repo 
* Pass in a board size, start point, and destination point
* `./gradlew run --args='--board_size 100,100 --source 0,0 --dest 45,99'`
* If you would like to make the chess board an infinite size use negative numbers for both the width and height
  * `./gradlew run --args='--board_size -1,-1 --source 0,0 --dest 45,99'`
  * The infinite board still has a limit in the bottom left of 0,0 so x and y cannot be negative

## Run Tests
* `./gradlew test`