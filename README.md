# O-an-quan-game


"O an quan" is a traditional Vietnamese board game, which is commonly played by children.

## Game's setup
### Game's board
![image](https://user-images.githubusercontent.com/66937471/220591100-ee9c3cac-da5b-425f-bc8e-de4904f5f342.png)

The game is set up like above.

The game's board is drawn with a rectangle which is divided into 10 squares, each side has
5 symmetrical fields, which contain small stones. On the two sides of the rectangle are two
semicircles which contain big stone. The square fields are called **folk fields** and the two
semi-circular fields are called **king fields**.

### Game's figures

Stones:




• Small stone = 1 point
![image](https://user-images.githubusercontent.com/66937471/220591458-f9006387-fd14-4d34-9f87-829d61a54a6b.png)

• Big stone = 10 small stones = 10 point
![image](https://user-images.githubusercontent.com/66937471/220591499-216340c6-4344-4132-8fb6-04baf69f1fb3.png)


### Game's players

Number of players is 2. Each player plays on 1 side of the board and is allowed to play only with the stones on the fields of their side.

## Game's rules

**Goal of the game**

The winner is the one who wins more stones (points) at the end of the game.

**Rules of scattering and winning stones**

1.	Each player in their turn moves the game's stones to take as many stones for themselves as possible.
2.  When it is the player's turn, the player uses all the stones of one square on his/her side to scatter. The stones can be scattered clockwise or counterclockwise (left or right ).
3.  In case there are no stones left on player's side to choose from, 5 stones from player's resources will be placed on the fields with 1 stone each field.
4. When spreading the last piece, the players proceed as follows depending on the situation:

  *	**Continuing scattering**: If the next field has stones, and is not king's field, then use all these stones to continue scattering in the chosen direction.(5),(6)
  *	**Gaining stones**: If the next field is an empty field followed by a field with stones, then the player can take all the stones in this field, and further fields which are separated by empty fields. (7)
      Human player plays on the first position from the left, in the left direction, and gains 6 stones .
      
  ![image](https://user-images.githubusercontent.com/66937471/220592551-fac67dbd-9412-428f-b538-9b7d720915f7.png)
      ![image](https://user-images.githubusercontent.com/66937471/220592585-6589569f-ade9-4fe4-bc10-ddad186b8d02.png)


   *	**Losing turn**	If the next field is king field. For example: human player plays stones from the first position from the left, in the right direction.
      ![image](https://user-images.githubusercontent.com/66937471/220592625-024b2f20-e3af-444e-90f8-2ad25bd6683f.png)
      ![image](https://user-images.githubusercontent.com/66937471/220592707-58b0e215-fa63-4160-98f7-01c93620b4a7.png)
      
   At the end, the stone scattering must stop in front of king field.
 
   *	or next fields are >=2 empty fields, or player has won some stones, then player loses his turn. (8)
      
**Game over**

The game ends when: (9)
* There are no more stones in both king fields or:
* When a player does not have enough resources to place stones on the board to play.

Picture to clarify the game’s rules.

![image](https://user-images.githubusercontent.com/66937471/220592971-4849b07c-5587-40fd-b1e3-faecb499451d.png)

## Run the game with Processing

Run MainServer class and MainClient class to run the program. Program only starts when both threads run. Notice the following: 
*	Human's turn is first.
*	Clicking on the **direction** and then the **stone field** of choice to take turn.
*	Play when "Human's turn" appears on the screen. Otherwise, when it is computer's turn, "Computer's turn" text appears on the screen.
*	Computer will play automatic after human's turn.

## Run the game on Jshell

Game is played automatic by computers:

![image](https://user-images.githubusercontent.com/66937471/220593507-11efaa64-eac5-461c-81dc-de27a07dea48.png)

Run game of human versus computer. Player 2: human, Player 1: computer. Human plays first. Computer plays random move.

## Language

Programming language: Java JDK 19 [Download](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)

## Library

•	Processing: [Download](https://processing.org/download)

## Sources
[1] T. T. Hoang, M. T. Dong, and X. V. Nguyen, ‘Thesis report O an quan game’. Jul. 2016. [Online].( Available: https://123docz.net/document/3974969-bao-cao-mon-tri-tue-nhan-tao-game-o-an-quan.htm )

[2] Technische Hochschule Mittelhessen, Prof. M. Weigel 'Lecture codes: VW10 - Pong.java' . 2022
















